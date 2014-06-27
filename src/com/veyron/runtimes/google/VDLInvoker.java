package com.veyron.runtimes.google;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import com.google.common.collect.HashMultimap;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.veyron2.ipc.ServerCall;
import com.veyron2.ipc.VeyronException;

/**
 * VDLInvoker is a helper class that uses reflection to invoke VDL interface methods for objects
 * that implements those interfaces.  It is required that the provided objects implement exactly one
 * VDL interface.
 */
public class VDLInvoker {
    // A cache of ClassInfo objects, aiming to reduce the cost of expensive reflection operations.
    private static HashMap<Class<?>, ClassInfo> classes = new HashMap<Class<?>, ClassInfo>();

    private final Object obj;
    private final ClassInfo classInfo;
    private final Gson gson;

    /**
     * Creates a new invoker for the given object.
     *
     * @param   obj                      object we're invoking methods on
     * @returns Invoker                  new VDL invoker instance
     * @throws  IllegalArgumentException if the provided object is invalid (either null or doesn't
     *                                   implement exactly one VDL interface)
     */
    public VDLInvoker(Object obj) throws IllegalArgumentException {
        if (obj == null) {
            throw new IllegalArgumentException("Can't create VDLInvoker with a null object.");
        }
        final Class<?> c = obj.getClass();
        ClassInfo cInfo;
        synchronized (VDLInvoker.this) {
            cInfo = VDLInvoker.classes.get(c);
        }
        if (cInfo == null) {
            cInfo = new ClassInfo(c);
            // Note that multiple threads might decide to create a new ClassInfo and insert it
            // into the cache, but that's just wasted work and not a race condition.
            synchronized (VDLInvoker.this) {
                VDLInvoker.classes.put(c, cInfo);
            }
        }
        this.obj = obj;
        this.classInfo = cInfo;
        this.gson = new Gson();
    }

    /**
     * Retrieves the pathname of the VDL interface that the object implements.
     *
     * @return String name of the VDL interface implemented by the object
     */
    public String getInterfacePath() {
        return this.classInfo.vdlInterfacePath;
    }

    /**
     * InvokeReply stores the replies for the {@link #invoke} method.  The replies are JSON-encoded.
     * In addition to replies, this class also stores application error, if any.
     */
    public class InvokeReply {
        public String[] results;  // can be null, e.g., if an error occurred.
        public boolean hasApplicationError = false;
        public String errorID;
        public String errorMsg;
    }

    /**
     * Converts the provided arguments from JSON and invokes the given method
     * using reflection. JSON-encodes the reply. Application errors are returned
     * along with the reply, while any other encountered errors are thrown as
     * exceptions.
     * 
     * @param method                    name of the method to be invoked
     * @param call                      in-flight call information
     * @param inArgs                    JSON encoded arguments to the method
     * @return InvokeReply              JSON-encoded invocation reply and application errors
     * @throws IllegalArgumentException if invalid arguments are passed
     * @throws IllegalAccessException   if a runtime access error occurs
     * @throws InvocationTargetException
     */
    public InvokeReply invoke(String method, ServerCall call, String[] inArgs) throws
    IllegalArgumentException, IllegalAccessException {
        final MethodInfo mInfo = this.classInfo.findMethod(method, inArgs.length);
        if (mInfo == null) {
            throw new IllegalArgumentException(String.format(
                "Couldn't find method %s of length %d in class %s",
                    method, inArgs.length, this.classInfo.c.getCanonicalName()));
        }

        // Decode JSON arguments.
        final Object[] args = this.prepareArgs(mInfo, call, inArgs);

        // Invoke the method and process results.
        final InvokeReply reply = new InvokeReply();
        try {
            final Object result = mInfo.method.invoke(this.obj, args);
            reply.results = this.prepareResults(mInfo, result);
        } catch (InvocationTargetException e) { // The underlying method threw an exception.
            VeyronException ve;
            if ((e.getCause() instanceof VeyronException)) {
                ve = (VeyronException)e.getTargetException();
            } else {
                // Dump the stack trace locally.
                e.getTargetException().printStackTrace();

                ve = new VeyronException(String.format(
                        "Remote invocations of java methods may only throw VeyronException, but call to %s threw %s",
                        method, e.getTargetException().getClass()));
            }
            reply.hasApplicationError = true;
            reply.errorID = ve.getID();
            reply.errorMsg = ve.getMessage();
        }

        return reply;
    }

    private Object[] prepareArgs(MethodInfo method, ServerCall call, String[] inArgs)
            throws JsonSyntaxException {
        final Class<?>[] inTypes = method.getInTypes();
        assert inArgs.length == inTypes.length;

        // The first argument is always context, so we add it.
        final int argsLength = inArgs.length + 1;
        final Object[] ret = new Object[argsLength];
        ret[0] = call;
        for (int i = 0; i < inArgs.length; i++) {
            ret[i + 1] = this.gson.fromJson(inArgs[i], inTypes[i]);
        }
        return ret;
    }

    private String[] prepareResults(MethodInfo method, Object result)
            throws IllegalArgumentException, IllegalAccessException {
        // See if the result packs multiple return values for the VDL method.
        try {
            final Class<?> c = Class.forName(String.format("%s$%sOut",
                    this.classInfo.vdlInterfacePath, unCamelCase(method.getName())));
            // ClassNotFoundException not triggered - there are multiple return values.
            if (!result.getClass().equals(c)) {
                throw new IllegalArgumentException(String.format(
                        "Type mismatch for method %s return value, want %s got %s",
                        method.getName(), c.getName(), result.getClass().getName()));
            }
            final Field[] fields = c.getFields();
            final String[] reply = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                reply[i] = this.gson.toJson(fields[i].get(result));
            }
            return reply;
        } catch (ClassNotFoundException e) {
            final String[] reply = new String[1];
            reply[0] = this.gson.toJson(result);
            return reply;
        }
    }

    private static String unCamelCase(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private static class ClassInfo {
        final Class<?> c; // non-null
        final String vdlInterfacePath;  // non-null
        final HashMultimap<String, MethodInfo> methods;  // non-null

        ClassInfo(Class<?> c) throws IllegalArgumentException {
            this.c = c;
            try {
                // NOTE(spetrovic): this is extremely hacky, but is needed until we remove our
                // dependence on knowing the exact VDL interface path.
                this.vdlInterfacePath =
                        c.getDeclaredField("service").getType().getName();
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException(
                        String.format("Class %s must have a field \"service\" that stores the " +
                                "VDL service implementation", c.getName()));
            }

            this.methods = HashMultimap.create();
            final Method[] methodList = c.getMethods();
            for (int i = 0; i < methodList.length; i++) {
                final Method method = methodList[i];
                try {
                    this.methods.put(method.getName(), new MethodInfo(method));
                } catch (IllegalArgumentException e) {
                }  // method not an VDL method.
            }
        }

        MethodInfo findMethod(String method, int numArgs) {
            for (Iterator<MethodInfo> it = this.methods.get(method).iterator(); it.hasNext(); ) {
                final MethodInfo mInfo = it.next();
                if (mInfo.getInTypes().length == numArgs) {
                    return mInfo;
                }
            }
            return null;
        }
    }

    private static class MethodInfo {
        final Method method;  // non-null
        final Class<?> []inTypes;  // non-null
        final Class<?> outType;  // non-null

        MethodInfo(Method method) throws IllegalArgumentException {
            this.method = method;
            this.outType = method.getReturnType();
            // The first argument is always Context, so we remove it.
            final Class<?>[] inTypes = method.getParameterTypes();
            if (inTypes.length == 0) {
                throw new IllegalArgumentException(String.format(
                        "Method %s must have at least one argument (i.e., ServerCall)", method.getName()));
            }
            if (!inTypes[0].getName().equals("com.veyron2.ipc.ServerCall")) {
                throw new IllegalArgumentException(String.format(
                        "Method %s's first argument must of type ServerCall, got: %s",
                        method.getName(), inTypes[0].getName()));
            }
            final int inLength = inTypes.length - 1;
            this.inTypes = new Class<?>[inLength];
            System.arraycopy(inTypes, 1, this.inTypes, 0, inLength);
        }

        String getName() { return this.method.getName(); }

        Class<?>[] getInTypes() { return this.inTypes; }

        @SuppressWarnings("unused")
        Class<?> getOutType() { return this.outType; }
    }
}