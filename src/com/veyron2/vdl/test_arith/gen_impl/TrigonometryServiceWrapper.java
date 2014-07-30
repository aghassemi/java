// This file was auto-generated by the veyron vdl tool.
// Source(s):  advanced.vdl
package com.veyron2.vdl.test_arith.gen_impl;

public final class TrigonometryServiceWrapper {

    private final com.veyron2.vdl.test_arith.TrigonometryService service;




    public TrigonometryServiceWrapper(final com.veyron2.vdl.test_arith.TrigonometryService service) {
        this.service = service;
        
        
    }

    /**
     * Returns all tags associated with the provided method or null if the method isn't implemented
     * by this service.
     */
    public java.lang.Object[] getMethodTags(final com.veyron2.ipc.ServerCall call, final java.lang.String method) throws com.veyron2.ipc.VeyronException {
        
        if ("cosine".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("getMethodTags".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("sine".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        
        throw new com.veyron2.ipc.VeyronException("method: " + method + " not found");
    }

     
    
    public double sine(final com.veyron2.ipc.ServerCall call, final double angle) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.sine( call , angle  );
    }

    public double cosine(final com.veyron2.ipc.ServerCall call, final double angle) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.cosine( call , angle  );
    }



 

}