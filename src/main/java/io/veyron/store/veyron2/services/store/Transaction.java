// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package io.veyron.store.veyron2.services.store;


public interface Transaction  {

    
    

    
    // Commit commits the changes in the transaction to the store.  The
// operation is atomic, so all mutations are performed, or none.  Returns an
// error if the transaction aborted.

    public void commit(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException;
    public void commit(final com.veyron2.ipc.Context context, final com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException;

    
    

    
    // Abort discards a transaction.  This is an optimization; transactions
// eventually time out and get discarded.  However, live transactions
// consume resources, so if you know that you won't be using a transaction
// anymore, you should discard it explicitly.

    public void abort(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException;
    public void abort(final com.veyron2.ipc.Context context, final com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException;

}