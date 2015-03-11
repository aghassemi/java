// Defines tasks for building the VDL tool and generating VDL files.

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.Exec;
import org.gradle.api.InvalidUserDataException;

class BuildVdlToolTask extends Exec {
    {
        description('Builds the vdl tool')
        commandLine('v23', 'go', 'install', 'v.io/x/ref/cmd/vdl')
    }
}


class VdlPlugin implements Plugin<Project> {
    String inputPath
    String outputPath

    void apply(Project project) {
        project.extensions.create('vdl', VdlConfiguration)
        def buildTask = project.task('buildVdl', type: BuildVdlToolTask)
        def generateTask = project.task('generateVdl', type: Exec) {
            group = "Build"
            description('Generates Java vdl source using the vdl tool')
        }
        def prepareTask = project.task('prepareVdl') {
            doLast {
                generateTask.environment(VDLPATH: project.vdl.inputPath)
                generateTask.commandLine(System.env.VANADIUM_ROOT + '/release/go/bin/vdl', 'generate', '--lang=java', "--java_out_dir=${project.vdl.outputPath}", 'all')
            }
        }
        def removeVdlRootTask = project.task('removeVdlRoot', type: Delete) {
            onlyIf { !project.vdl.generateVdlRoot }
            delete project.vdl.outputPath + '/io/v/v23/vdlroot/'
        }
        prepareTask.dependsOn(buildTask)
        generateTask.dependsOn(prepareTask)
        removeVdlRootTask.dependsOn(generateTask)
        project.clean.delete(project.vdl.outputPath)

        if (project.plugins.hasPlugin('java')) {
            project.compileJava.dependsOn(removeVdlRootTask)
            project.sourceSets.main.java.srcDirs += project.vdl.outputPath
        }

        if (project.plugins.hasPlugin('android')) {
            project.tasks.'preBuild'.dependsOn(removeVdlRootTask)
            project.android.sourceSets.main.java.srcDirs += project.vdl.outputPath
        }
    }
}

class VdlConfiguration {
    String inputPath = null;
    String outputPath = "generated-src/vdl";

    // If true, code generated for the vdlroot vdl package will be emitted.
    // Typically, users will want to leave this set to false as they will
    // already get the vdlroot package by depending on the :lib project.
    // TODO(sjr): talk to toddw about whether we can do this in the vdl tool
    // itself.
    boolean generateVdlRoot = false;
}