// This file was auto-generated by the veyron vdl tool.
// Source(s):  service.vdl
package com.veyron2.services.security.access.gen_impl;

public final class ObjectServiceWrapper {

    private final com.veyron2.services.security.access.ObjectService service;




    public ObjectServiceWrapper(final com.veyron2.services.security.access.ObjectService service) {
        this.service = service;
        
        
    }

    /**
     * Returns all tags associated with the provided method or null if the method isn't implemented
     * by this service.
     */
    public java.lang.Object[] getMethodTags(final com.veyron2.ipc.ServerCall call, final java.lang.String method) throws com.veyron2.ipc.VeyronException {
        
        if ("getACL".equals(method)) {
            return new java.lang.Object[] {
                 new com.veyron2.security.Label(4), 
            };
        }
        
        if ("getMethodTags".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("setACL".equals(method)) {
            return new java.lang.Object[] {
                 new com.veyron2.security.Label(4), 
            };
        }
        
        
        throw new com.veyron2.ipc.VeyronException("method: " + method + " not found");
    }

     
    
    public void setACL(final com.veyron2.ipc.ServerCall call, final com.veyron2.services.security.access.ACL acl, final java.lang.String etag) throws com.veyron2.ipc.VeyronException {
         
         this.service.setACL( call , acl, etag  );
    }

    public com.veyron2.services.security.access.Object.GetACLOut getACL(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.getACL( call   );
    }



 

}