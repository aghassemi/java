// This file was auto-generated by the veyron vdl tool.
// Source(s):  test_base.vdl
package com.veyron.tools.vrpc.test_base.gen_impl;

public final class TypeTesterServiceWrapper {

    private final com.veyron.tools.vrpc.test_base.TypeTesterService service;




    public TypeTesterServiceWrapper(final com.veyron.tools.vrpc.test_base.TypeTesterService service) {
        this.service = service;
        
        
    }

    /**
     * Returns all tags associated with the provided method or null if the method isn't implemented
     * by this service.
     */
    public java.lang.Object[] getMethodTags(final com.veyron2.ipc.ServerCall call, final java.lang.String method) throws com.veyron2.ipc.VeyronException {
        
        if ("echoBool".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("echoByte".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("echoFloat32".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("echoFloat64".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("echoInt32".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("echoInt64".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("echoString".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("echoUInt32".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("echoUInt64".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("getMethodTags".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("inputArray".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("inputMap".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("inputSlice".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("inputStruct".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("multipleArguments".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("noArguments".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("outputArray".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("outputMap".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("outputSlice".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("outputStruct".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("streamingOutput".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        
        throw new com.veyron2.ipc.VeyronException("method: " + method + " not found");
    }

     
    
    public boolean echoBool(final com.veyron2.ipc.ServerCall call, final boolean I1) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.echoBool( call , I1  );
    }

    public float echoFloat32(final com.veyron2.ipc.ServerCall call, final float I1) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.echoFloat32( call , I1  );
    }

    public double echoFloat64(final com.veyron2.ipc.ServerCall call, final double I1) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.echoFloat64( call , I1  );
    }

    public int echoInt32(final com.veyron2.ipc.ServerCall call, final int I1) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.echoInt32( call , I1  );
    }

    public long echoInt64(final com.veyron2.ipc.ServerCall call, final long I1) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.echoInt64( call , I1  );
    }

    public java.lang.String echoString(final com.veyron2.ipc.ServerCall call, final java.lang.String I1) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.echoString( call , I1  );
    }

    public byte echoByte(final com.veyron2.ipc.ServerCall call, final byte I1) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.echoByte( call , I1  );
    }

    public int echoUInt32(final com.veyron2.ipc.ServerCall call, final int I1) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.echoUInt32( call , I1  );
    }

    public long echoUInt64(final com.veyron2.ipc.ServerCall call, final long I1) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.echoUInt64( call , I1  );
    }

    public void inputArray(final com.veyron2.ipc.ServerCall call, final byte[] I1) throws com.veyron2.ipc.VeyronException {
         
         this.service.inputArray( call , I1  );
    }

    public void inputMap(final com.veyron2.ipc.ServerCall call, final java.util.Map<java.lang.Byte, java.lang.Byte> I1) throws com.veyron2.ipc.VeyronException {
         
         this.service.inputMap( call , I1  );
    }

    public void inputSlice(final com.veyron2.ipc.ServerCall call, final java.util.List<java.lang.Byte> I1) throws com.veyron2.ipc.VeyronException {
         
         this.service.inputSlice( call , I1  );
    }

    public void inputStruct(final com.veyron2.ipc.ServerCall call, final com.veyron.tools.vrpc.test_base.Struct I1) throws com.veyron2.ipc.VeyronException {
         
         this.service.inputStruct( call , I1  );
    }

    public byte[] outputArray(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.outputArray( call   );
    }

    public java.util.Map<java.lang.Byte, java.lang.Byte> outputMap(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.outputMap( call   );
    }

    public java.util.List<java.lang.Byte> outputSlice(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.outputSlice( call   );
    }

    public com.veyron.tools.vrpc.test_base.Struct outputStruct(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.outputStruct( call   );
    }

    public void noArguments(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         this.service.noArguments( call   );
    }

    public com.veyron.tools.vrpc.test_base.TypeTester.MultipleArgumentsOut multipleArguments(final com.veyron2.ipc.ServerCall call, final int I1, final int I2) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.multipleArguments( call , I1, I2  );
    }

    public void streamingOutput(final com.veyron2.ipc.ServerCall call, final int NumStreamItems, final boolean StreamItem) throws com.veyron2.ipc.VeyronException {
        
        final com.veyron2.vdl.Stream<java.lang.Void, java.lang.Boolean> stream = new com.veyron2.vdl.Stream<java.lang.Void, java.lang.Boolean>() {
            @Override
            public void send(java.lang.Void item) throws com.veyron2.ipc.VeyronException {
                call.send(item);
            }
            @Override
            public java.lang.Boolean recv() throws java.io.EOFException, com.veyron2.ipc.VeyronException {
                final com.google.common.reflect.TypeToken<?> type = new com.google.common.reflect.TypeToken< java.lang.Boolean >() {
                    private static final long serialVersionUID = 1L;
                };
                final java.lang.Object result = call.recv(type);
                try {
                    return (java.lang.Boolean)result;
                } catch (java.lang.ClassCastException e) {
                    throw new com.veyron2.ipc.VeyronException("Unexpected result type: " + result.getClass().getCanonicalName());
                }
            }
        };
         
         this.service.streamingOutput( call , NumStreamItems, StreamItem  ,stream  );
    }



 

}
