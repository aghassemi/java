// This file was auto-generated by the veyron vdl tool.
// Source(s):  arith.vdl
package com.veyron2.vdl.test_arith.gen_impl;

/* Client stub for interface: Calculator. */
public final class CalculatorStub implements com.veyron2.vdl.test_arith.Calculator {
    private static final java.lang.String vdlIfacePathOpt = "com.veyron2.vdl.test_arith.Calculator";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    
    
    private final com.veyron2.vdl.test_arith.gen_impl.ArithStub arithStub;
    
    
    private final com.veyron2.vdl.test_arith.gen_impl.TrigonometryStub trigonometryStub;
    
    
    private final com.veyron2.vdl.test_arith.exp.gen_impl.ExpStub expStub;
    
    
    private final com.veyron2.vdl.test_arith.gen_impl.AdvancedMathStub advancedMathStub;
    

    public CalculatorStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
        this.arithStub = new com.veyron2.vdl.test_arith.gen_impl.ArithStub(client, veyronName);
         
        this.trigonometryStub = new com.veyron2.vdl.test_arith.gen_impl.TrigonometryStub(client, veyronName);
         
        this.expStub = new com.veyron2.vdl.test_arith.exp.gen_impl.ExpStub(client, veyronName);
         
        this.advancedMathStub = new com.veyron2.vdl.test_arith.gen_impl.AdvancedMathStub(client, veyronName);
         
    }

    // Methods from interface Calculator.


    
    public void on(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
         on(context, null);
    }
    
    public void on(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, CalculatorStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "Glob", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }

    
    public void off(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
         off(context, null);
    }
    
    public void off(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, CalculatorStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "Glob", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }




    @Override
    public int add(final com.veyron2.ipc.Context context, final int a, final int b) throws com.veyron2.ipc.VeyronException {
        
        return this.arithStub.add(context, a, b);
    }
    @Override
    public int add(final com.veyron2.ipc.Context context, final int a, final int b, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.arithStub.add(context, a, b, veyronOpts);
    }

    @Override
    public com.veyron2.vdl.test_arith.Arith.DivModOut divMod(final com.veyron2.ipc.Context context, final int a, final int b) throws com.veyron2.ipc.VeyronException {
        
        return this.arithStub.divMod(context, a, b);
    }
    @Override
    public com.veyron2.vdl.test_arith.Arith.DivModOut divMod(final com.veyron2.ipc.Context context, final int a, final int b, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.arithStub.divMod(context, a, b, veyronOpts);
    }

    @Override
    public int sub(final com.veyron2.ipc.Context context, final com.veyron2.vdl.test_base.Args args) throws com.veyron2.ipc.VeyronException {
        
        return this.arithStub.sub(context, args);
    }
    @Override
    public int sub(final com.veyron2.ipc.Context context, final com.veyron2.vdl.test_base.Args args, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.arithStub.sub(context, args, veyronOpts);
    }

    @Override
    public void genError(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
         this.arithStub.genError(context);
    }
    @Override
    public void genError(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
          this.arithStub.genError(context, veyronOpts);
    }

    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,java.lang.Integer, java.lang.Void> count(final com.veyron2.ipc.Context context, final int Start) throws com.veyron2.ipc.VeyronException {
        
        return this.arithStub.count(context, Start);
    }
    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,java.lang.Integer, java.lang.Void> count(final com.veyron2.ipc.Context context, final int Start, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.arithStub.count(context, Start, veyronOpts);
    }

    @Override
    public double cosine(final com.veyron2.ipc.Context context, final double angle) throws com.veyron2.ipc.VeyronException {
        
        return this.trigonometryStub.cosine(context, angle);
    }
    @Override
    public double cosine(final com.veyron2.ipc.Context context, final double angle, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.trigonometryStub.cosine(context, angle, veyronOpts);
    }

    @Override
    public double exp(final com.veyron2.ipc.Context context, final double x) throws com.veyron2.ipc.VeyronException {
        
        return this.expStub.exp(context, x);
    }
    @Override
    public double exp(final com.veyron2.ipc.Context context, final double x, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.expStub.exp(context, x, veyronOpts);
    }

    @Override
    public int mul(final com.veyron2.ipc.Context context, final com.veyron2.vdl.test_base.NestedArgs nested) throws com.veyron2.ipc.VeyronException {
        
        return this.arithStub.mul(context, nested);
    }
    @Override
    public int mul(final com.veyron2.ipc.Context context, final com.veyron2.vdl.test_base.NestedArgs nested, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.arithStub.mul(context, nested, veyronOpts);
    }

    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Integer,java.lang.Integer, java.lang.Integer> streamingAdd(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
        return this.arithStub.streamingAdd(context);
    }
    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Integer,java.lang.Integer, java.lang.Integer> streamingAdd(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.arithStub.streamingAdd(context, veyronOpts);
    }

    @Override
    public java.lang.Object quoteAny(final com.veyron2.ipc.Context context, final java.lang.Object a) throws com.veyron2.ipc.VeyronException {
        
        return this.arithStub.quoteAny(context, a);
    }
    @Override
    public java.lang.Object quoteAny(final com.veyron2.ipc.Context context, final java.lang.Object a, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.arithStub.quoteAny(context, a, veyronOpts);
    }

    @Override
    public double sine(final com.veyron2.ipc.Context context, final double angle) throws com.veyron2.ipc.VeyronException {
        
        return this.trigonometryStub.sine(context, angle);
    }
    @Override
    public double sine(final com.veyron2.ipc.Context context, final double angle, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.trigonometryStub.sine(context, angle, veyronOpts);
    }


}