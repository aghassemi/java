// This file was auto-generated by the veyron vdl tool.
// Source: boxes.vdl
package com.veyron.examples.boxes;

/**
 * DrawInterface enables adding a box on another peer
 */

public interface DrawInterface  {

    
    

    
    // Draw is used to send/receive a stream of boxes to another peer

    public com.veyron2.vdl.ClientStream<com.veyron.examples.boxes.Box,com.veyron.examples.boxes.Box, java.lang.Void> draw(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException;
    public com.veyron2.vdl.ClientStream<com.veyron.examples.boxes.Box,com.veyron.examples.boxes.Box, java.lang.Void> draw(final com.veyron2.ipc.Context context, final com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException;

    
    

    
    // SyncBoxes is used to setup a sync service over store to send/receive
// boxes to another peer

    public void syncBoxes(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException;
    public void syncBoxes(final com.veyron2.ipc.Context context, final com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException;

}