// This file was auto-generated by the veyron vdl tool.
// Source(s):  node.vdl
package com.veyron2.services.mgmt.node.gen_impl;

public final class ApplicationServiceWrapper {

    private final com.veyron2.services.mgmt.node.ApplicationService service;




    public ApplicationServiceWrapper(final com.veyron2.services.mgmt.node.ApplicationService service) {
        this.service = service;
        
        
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
        
        if ("install".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("refresh".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("restart".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("resume".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("revert".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("start".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("stop".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("suspend".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("uninstall".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("update".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        if ("updateTo".equals(method)) {
            return new java.lang.Object[] {
                
            };
        }
        
        
        throw new com.veyron2.ipc.VeyronException("method: " + method + " not found");
    }

     
    
    public java.lang.String install(final com.veyron2.ipc.ServerCall call, final java.lang.String Name) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.install( call , Name  );
    }

    public void refresh(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         this.service.refresh( call   );
    }

    public void restart(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         this.service.restart( call   );
    }

    public void resume(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         this.service.resume( call   );
    }

    public void revert(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         this.service.revert( call   );
    }

    public java.util.ArrayList<java.lang.String> start(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         return  this.service.start( call   );
    }

    public void stop(final com.veyron2.ipc.ServerCall call, final int Deadline) throws com.veyron2.ipc.VeyronException {
         
         this.service.stop( call , Deadline  );
    }

    public void suspend(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         this.service.suspend( call   );
    }

    public void uninstall(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         this.service.uninstall( call   );
    }

    public void update(final com.veyron2.ipc.ServerCall call) throws com.veyron2.ipc.VeyronException {
         
         this.service.update( call   );
    }

    public void updateTo(final com.veyron2.ipc.ServerCall call, final java.lang.String Name) throws com.veyron2.ipc.VeyronException {
         
         this.service.updateTo( call , Name  );
    }



 

}