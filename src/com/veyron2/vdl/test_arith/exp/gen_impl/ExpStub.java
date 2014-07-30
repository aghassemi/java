// This file was auto-generated by the veyron vdl tool.
// Source(s):  exp.vdl
package com.veyron2.vdl.test_arith.exp.gen_impl;

/* Client stub for interface: Exp. */
public final class ExpStub implements com.veyron2.vdl.test_arith.exp.Exp {
    private static final java.lang.String vdlIfacePathOpt = "com.veyron2.vdl.test_arith.exp.Exp";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    

    public ExpStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
    }

    // Methods from interface Exp.


    
    public double exp(final com.veyron2.ipc.Context context, final double x) throws com.veyron2.ipc.VeyronException {
        return exp(context, x, null);
    }
    
    public double exp(final com.veyron2.ipc.Context context, final double x, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, ExpStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ x };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "Glob", inArgs, veyronOpts);

        // Finish the call.
        
        

         
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{
            
            new com.google.common.reflect.TypeToken<java.lang.Double>() {
                private static final long serialVersionUID = 1L;
            },
            
        };
        final java.lang.Object[] results = call.finish(resultTypes);
         
        return (java.lang.Double)results[0];
         

         

        
    }





}