// This file was auto-generated by the veyron vdl tool.
// Source(s):  service.vdl
package com.veyron2.services.store.gen_impl;

/* Client stub for interface: Statable. */
public final class StatableStub implements com.veyron2.services.store.Statable {
    private static final java.lang.String vdlIfacePathOpt = "com.veyron2.services.store.Statable";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    

    public StatableStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
    }

    // Methods from interface Statable.


    
    public com.veyron2.storage.Stat stat(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        return stat(context, null);
    }
    
    public com.veyron2.storage.Stat stat(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, StatableStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "stat", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<com.veyron2.storage.Stat>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (com.veyron2.storage.Stat)results[0];
         

         

        
    }





}
