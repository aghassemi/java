// This file was auto-generated by the veyron vdl tool.
// Source(s):  service.vdl
package com.veyron2.services.mounttable.gen_impl;

/* Client stub for interface: MountTable. */
public final class MountTableStub implements com.veyron2.services.mounttable.MountTable {
    private static final java.lang.String vdlIfacePathOpt = "com.veyron2.services.mounttable.MountTable";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    
    
    private final com.veyron2.services.mounttable.gen_impl.GlobbableStub globbableStub;
    

    public MountTableStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
        this.globbableStub = new com.veyron2.services.mounttable.gen_impl.GlobbableStub(client, veyronName);
         
    }

    // Methods from interface MountTable.


    
    public void mount(final com.veyron2.ipc.Context context, final java.lang.String Server, final int TTL) throws com.veyron2.ipc.VeyronException {
         mount(context, Server, TTL, null);
    }
    
    public void mount(final com.veyron2.ipc.Context context, final java.lang.String Server, final int TTL, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, MountTableStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ Server, TTL };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "mount", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }

    
    public void unmount(final com.veyron2.ipc.Context context, final java.lang.String Server) throws com.veyron2.ipc.VeyronException {
         unmount(context, Server, null);
    }
    
    public void unmount(final com.veyron2.ipc.Context context, final java.lang.String Server, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, MountTableStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ Server };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "unmount", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }

    
    public com.veyron2.services.mounttable.MountTable.ResolveStepOut resolveStep(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        return resolveStep(context, null);
    }
    
    public com.veyron2.services.mounttable.MountTable.ResolveStepOut resolveStep(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, MountTableStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "resolveStep", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.util.ArrayList<com.veyron2.services.mounttable.types.MountedServer>>() {
                private static final long serialVersionUID = 1L;
            },
            
            new com.google.common.reflect.TypeToken<java.lang.String>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
        
        final com.veyron2.services.mounttable.MountTable.ResolveStepOut ret = new com.veyron2.services.mounttable.MountTable.ResolveStepOut();
            
        ret.servers = (java.util.ArrayList<com.veyron2.services.mounttable.types.MountedServer>)results[0];
            
        ret.suffix = (java.lang.String)results[1];
             
        return ret;
         

         

        
    }




    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.mounttable.types.MountEntry, java.lang.Void> glob(final com.veyron2.ipc.Context context, final java.lang.String pattern) throws com.veyron2.ipc.VeyronException {
        
        return this.globbableStub.glob(context, pattern);
    }
    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.mounttable.types.MountEntry, java.lang.Void> glob(final com.veyron2.ipc.Context context, final java.lang.String pattern, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.globbableStub.glob(context, pattern, veyronOpts);
    }


}
