// This file was auto-generated by the veyron vdl tool.
// Source(s):  node.vdl
package com.veyron.services.mgmt.node.gen_impl;

/* Client stub for interface: Config. */
public final class ConfigStub implements com.veyron.services.mgmt.node.Config {
    private static final java.lang.String vdlIfacePathOpt = "com.veyron.services.mgmt.node.Config";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    

    public ConfigStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
    }

    // Methods from interface Config.


    
    public void set(final com.veyron2.ipc.Context context, final java.lang.String key, final java.lang.String value) throws com.veyron2.ipc.VeyronException {
         set(context, key, value, null);
    }
    
    public void set(final com.veyron2.ipc.Context context, final java.lang.String key, final java.lang.String value, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, ConfigStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ key, value };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "Glob", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }





}