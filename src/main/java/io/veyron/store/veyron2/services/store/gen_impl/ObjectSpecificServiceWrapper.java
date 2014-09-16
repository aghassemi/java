// This file was auto-generated by the veyron vdl tool.
// Source(s):  service.vdl
package io.veyron.store.veyron2.services.store.gen_impl;

public final class ObjectSpecificServiceWrapper {

    private final io.veyron.store.veyron2.services.store.ObjectSpecificService service;




    public ObjectSpecificServiceWrapper(final io.veyron.store.veyron2.services.store.ObjectSpecificService service) {
        this.service = service;
        
        
    }

    /**
     * Returns all tags associated with the provided method or null if the method isn't implemented
     * by this service.
     */
    public java.lang.Object[] getMethodTags(final com.veyron2.ipc.ServerCall call, final java.lang.String method) throws com.veyron2.ipc.VeyronException {
        
        if ("get".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("getMethodTags".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("put".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        
        throw new com.veyron2.ipc.VeyronException("method: " + method + " not found");
    }

     
    
    public io.veyron.store.veyron2.storage.Entry get(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.get( call   );
    }

    public io.veyron.store.veyron2.storage.Stat put(final com.veyron2.ipc.ServerCall call, final com.veyron2.vdl.Any V) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.put( call , V  );
    }



 

}