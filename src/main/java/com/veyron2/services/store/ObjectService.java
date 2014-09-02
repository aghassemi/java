// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package com.veyron2.services.store;

/**
 * ObjectService is the interface for a value in the store.
 */

@com.veyron2.vdl.VeyronService(serviceWrapper=com.veyron2.services.store.gen_impl.ObjectServiceWrapper.class)
public interface ObjectService extends com.veyron2.services.store.TransactableService, com.veyron2.services.store.StatableService, com.veyron2.services.mounttable.GlobbableService, com.veyron2.services.watch.GlobWatcherService, com.veyron2.services.watch.QueryWatcherService {

    
    // Exists returns true iff the Entry has a value.

    public boolean exists(final com.veyron2.ipc.ServerContext context) throws com.veyron2.ipc.VeyronException;

    
    // Get returns the value for the Object.  The value returned is from the
// most recent mutation of the entry in the Transaction, or from the
// Transaction's snapshot if there is no mutation.

    public com.veyron2.storage.Entry get(final com.veyron2.ipc.ServerContext context) throws com.veyron2.ipc.VeyronException;

    
    // Put modifies the value of the Object.

    public com.veyron2.storage.Stat put(final com.veyron2.ipc.ServerContext context, final com.veyron2.vdl.Any V) throws com.veyron2.ipc.VeyronException;

    
    // Remove removes the Object.

    public void remove(final com.veyron2.ipc.ServerContext context) throws com.veyron2.ipc.VeyronException;

    
    // Query returns the sequence of elements that satisfy the query.

    public void query(final com.veyron2.ipc.ServerContext context, final com.veyron2.query.Query Q, com.veyron2.vdl.Stream<java.lang.Void, com.veyron2.services.store.QueryResult> stream) throws com.veyron2.ipc.VeyronException;

}
