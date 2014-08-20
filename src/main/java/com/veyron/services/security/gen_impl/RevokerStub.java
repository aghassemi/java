// This file was auto-generated by the veyron vdl tool.
// Source(s):  revoker.vdl
package com.veyron.services.security.gen_impl;

/* Client stub for interface: Revoker. */
public final class RevokerStub implements com.veyron.services.security.Revoker {
    private static final java.lang.String vdlIfacePathOpt = "com.veyron.services.security.Revoker";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    

    public RevokerStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
    }

    // Methods from interface Revoker.


    
    public void revoke(final com.veyron2.ipc.Context context, final com.veyron.services.security.RevocationToken caveatPreimage) throws com.veyron2.ipc.VeyronException {
         revoke(context, caveatPreimage, null);
    }
    
    public void revoke(final com.veyron2.ipc.Context context, final com.veyron.services.security.RevocationToken caveatPreimage, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, RevokerStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ caveatPreimage };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "revoke", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }





}