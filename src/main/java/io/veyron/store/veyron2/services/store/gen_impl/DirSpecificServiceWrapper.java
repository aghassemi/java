// This file was auto-generated by the veyron vdl tool.
// Source(s):  service.vdl
package io.veyron.store.veyron2.services.store.gen_impl;

public final class DirSpecificServiceWrapper {

    private final io.veyron.store.veyron2.services.store.DirSpecificService service;



    
    private final io.veyron.store.veyron2.services.store.gen_impl.SyncGroupServiceWrapper syncGroupWrapper;
    

    public DirSpecificServiceWrapper(final io.veyron.store.veyron2.services.store.DirSpecificService service) {
        this.service = service;
        
        
        this.syncGroupWrapper = new io.veyron.store.veyron2.services.store.gen_impl.SyncGroupServiceWrapper(service);
        
    }

    /**
     * Returns all tags associated with the provided method or null if the method isn't implemented
     * by this service.
     */
    public java.lang.Object[] getMethodTags(final com.veyron2.ipc.ServerCall call, final java.lang.String method) throws com.veyron2.ipc.VeyronException {
        
        if ("getMethodTags".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("getSyncGroupNames".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("make".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        
        try {
            return this.syncGroupWrapper.getMethodTags(call, method);
        } catch (com.veyron2.ipc.VeyronException e) {}  // method not found.
        
        throw new com.veyron2.ipc.VeyronException("method: " + method + " not found");
    }

     
    
    public void make(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         this.service.make( call   );
    }

    public java.util.List<java.lang.String> getSyncGroupNames(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.getSyncGroupNames( call   );
    }




    public void createSyncGroup(final com.veyron2.ipc.ServerCall call, final java.lang.String name, final io.veyron.store.veyron2.services.store.SyncGroupConfig config) throws com.veyron2.ipc.VeyronException {
        
          this.syncGroupWrapper.createSyncGroup(call, name, config);
    }

    public void destroySyncGroup(final com.veyron2.ipc.ServerCall call, final java.lang.String name) throws com.veyron2.ipc.VeyronException {
        
          this.syncGroupWrapper.destroySyncGroup(call, name);
    }

    public void ejectFromSyncGroup(final com.veyron2.ipc.ServerCall call, final java.lang.String name, final java.lang.String member) throws com.veyron2.ipc.VeyronException {
        
          this.syncGroupWrapper.ejectFromSyncGroup(call, name, member);
    }

    public java.util.List<java.lang.String> getMembersOfSyncGroup(final com.veyron2.ipc.ServerCall call, final java.lang.String name) throws com.veyron2.ipc.VeyronException {
        
        return  this.syncGroupWrapper.getMembersOfSyncGroup(call, name);
    }

    public io.veyron.store.veyron2.services.store.SyncGroup.GetSyncGroupConfigOut getSyncGroupConfig(final com.veyron2.ipc.ServerCall call, final java.lang.String name) throws com.veyron2.ipc.VeyronException {
        
        return  this.syncGroupWrapper.getSyncGroupConfig(call, name);
    }

    public void joinSyncGroup(final com.veyron2.ipc.ServerCall call, final java.lang.String name) throws com.veyron2.ipc.VeyronException {
        
          this.syncGroupWrapper.joinSyncGroup(call, name);
    }

    public void leaveSyncGroup(final com.veyron2.ipc.ServerCall call, final java.lang.String name) throws com.veyron2.ipc.VeyronException {
        
          this.syncGroupWrapper.leaveSyncGroup(call, name);
    }

    public void setSyncGroupConfig(final com.veyron2.ipc.ServerCall call, final java.lang.String name, final io.veyron.store.veyron2.services.store.SyncGroupConfig config, final java.lang.String eTag) throws com.veyron2.ipc.VeyronException {
        
          this.syncGroupWrapper.setSyncGroupConfig(call, name, config, eTag);
    }
 

}