// This file was auto-generated by the veyron vdl tool.
// Source: service.vdl
package com.veyron2.services.store;

/**
 * Transactable provides the NewTransaction method.
 */

public interface Transactable  {

    
    

    
    // NewTransaction creates a transaction with the given options.  It returns
// the name of the transaction relative to the receiver's name.  The client must
// rebind to this new name to work with the receiver and its descendants as part
// of the transaction.

    public java.lang.String newTransaction(final com.veyron2.ipc.Context context, final java.util.List<com.veyron2.vdl.Any> Options) throws com.veyron2.ipc.VeyronException;
    public java.lang.String newTransaction(final com.veyron2.ipc.Context context, final java.util.List<com.veyron2.vdl.Any> Options, final com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException;

}
