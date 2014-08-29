// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package com.veyron2.services.store;

/**
 * Dir is a directory containing Objects and other Dirs.
 */

@com.veyron2.vdl.VeyronService(serviceWrapper=com.veyron2.services.store.gen_impl.DirServiceWrapper.class)
public interface DirService extends com.veyron2.services.store.TransactableService, com.veyron2.services.store.StatableService, com.veyron2.services.mounttable.GlobbableService, com.veyron2.services.watch.GlobWatcherService, com.veyron2.services.watch.QueryWatcherService {

    
    // Exists returns true iff this Dir is present in the Store.

    public boolean exists(final com.veyron2.ipc.ServerContext context) throws com.veyron2.ipc.VeyronException;

    
    // Make creates this directory and any ancestor directories that do not
// exist (i.e. equivalent to Unix's 'mkdir -p').  Make is idempotent.

    public void make(final com.veyron2.ipc.ServerContext context) throws com.veyron2.ipc.VeyronException;

    
    // Remove removes this directory and all of its children, recursively.

    public void remove(final com.veyron2.ipc.ServerContext context) throws com.veyron2.ipc.VeyronException;

    
    // Query returns the sequence of elements that satisfy the query.

    public void query(final com.veyron2.ipc.ServerContext context, final com.veyron2.query.Query Q, com.veyron2.vdl.Stream<java.lang.Void, com.veyron2.services.store.QueryResult> stream) throws com.veyron2.ipc.VeyronException;

}
