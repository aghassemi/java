// This file was auto-generated by the veyron vdl tool.
// Source(s):  inspector.vdl
package com.veyron.examples.inspector.gen_impl;

/* Client stub for interface: Inspector. */
public final class InspectorStub implements com.veyron.examples.inspector.Inspector {
    private static final java.lang.String vdlIfacePathOpt = "com.veyron.examples.inspector.Inspector";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    

    public InspectorStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
    }

    // Methods from interface Inspector.


    
    public com.veyron2.vdl.ClientStream<java.lang.Void,java.lang.String, java.lang.Void> ls(final com.veyron2.ipc.Context context, final java.lang.String Glob) throws com.veyron2.ipc.VeyronException {
        return ls(context, Glob, null);
    }
    
    public com.veyron2.vdl.ClientStream<java.lang.Void,java.lang.String, java.lang.Void> ls(final com.veyron2.ipc.Context context, final java.lang.String Glob, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, InspectorStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ Glob };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "ls", inArgs, veyronOpts);

        // Finish the call.
        
         
        return new com.veyron2.vdl.ClientStream<java.lang.Void, java.lang.String, java.lang.Void>() {
            @Override
            public void send(final java.lang.Void item) throws com.veyron2.ipc.VeyronException {
                call.send(item);
            }
            @Override
            public java.lang.String recv() throws java.io.EOFException, com.veyron2.ipc.VeyronException {
                final com.google.common.reflect.TypeToken<?> type = new com.google.common.reflect.TypeToken<java.lang.String>() {
                    private static final long serialVersionUID = 1L;
                };
                final java.lang.Object result = call.recv(type);
                try {
                    return (java.lang.String)result;
                } catch (java.lang.ClassCastException e) {
                    throw new com.veyron2.ipc.VeyronException("Unexpected result type: " + result.getClass().getCanonicalName());
                }
            }
            @Override
            public java.lang.Void finish() throws com.veyron2.ipc.VeyronException {
                
                final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
                call.finish(resultTypes);
                return null;
                 
            }
        };
        
    }

    
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron.examples.inspector.Details, java.lang.Void> lsDetails(final com.veyron2.ipc.Context context, final java.lang.String Glob) throws com.veyron2.ipc.VeyronException {
        return lsDetails(context, Glob, null);
    }
    
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron.examples.inspector.Details, java.lang.Void> lsDetails(final com.veyron2.ipc.Context context, final java.lang.String Glob, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, InspectorStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ Glob };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "lsDetails", inArgs, veyronOpts);

        // Finish the call.
        
         
        return new com.veyron2.vdl.ClientStream<java.lang.Void, com.veyron.examples.inspector.Details, java.lang.Void>() {
            @Override
            public void send(final java.lang.Void item) throws com.veyron2.ipc.VeyronException {
                call.send(item);
            }
            @Override
            public com.veyron.examples.inspector.Details recv() throws java.io.EOFException, com.veyron2.ipc.VeyronException {
                final com.google.common.reflect.TypeToken<?> type = new com.google.common.reflect.TypeToken<com.veyron.examples.inspector.Details>() {
                    private static final long serialVersionUID = 1L;
                };
                final java.lang.Object result = call.recv(type);
                try {
                    return (com.veyron.examples.inspector.Details)result;
                } catch (java.lang.ClassCastException e) {
                    throw new com.veyron2.ipc.VeyronException("Unexpected result type: " + result.getClass().getCanonicalName());
                }
            }
            @Override
            public java.lang.Void finish() throws com.veyron2.ipc.VeyronException {
                
                final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
                call.finish(resultTypes);
                return null;
                 
            }
        };
        
    }





}