// This file was auto-generated by the veyron vdl tool.
// Source(s):  service.vdl
package io.veyron.store.veyron2.services.store.gen_impl;

/* Client stub for interface: DirOrObject. */
public final class DirOrObjectStub implements io.veyron.store.veyron2.services.store.DirOrObject {
    private static final java.lang.String vdlIfacePathOpt = "veyron.io/store/veyron2/services/store/DirOrObject";
    private final com.veyron2.ipc.Client client;
    private final java.lang.String veyronName;

    
    
    
    private final io.veyron.store.veyron2.services.store.gen_impl.StatableStub statableStub;
    
    
    private final io.veyron.store.veyron2.services.store.gen_impl.TransactableStub transactableStub;
    
    
    private final com.veyron2.services.mounttable.gen_impl.GlobbableStub globbableStub;
    
    
    private final com.veyron2.services.watch.gen_impl.GlobWatcherStub globWatcherStub;
    
    
    private final io.veyron.store.veyron2.services.watch.gen_impl.QueryWatcherStub queryWatcherStub;
    

    public DirOrObjectStub(final com.veyron2.ipc.Client client, final java.lang.String veyronName) {
        this.client = client;
        this.veyronName = veyronName;
        
        
        this.statableStub = new io.veyron.store.veyron2.services.store.gen_impl.StatableStub(client, veyronName);
         
        this.transactableStub = new io.veyron.store.veyron2.services.store.gen_impl.TransactableStub(client, veyronName);
         
        this.globbableStub = new com.veyron2.services.mounttable.gen_impl.GlobbableStub(client, veyronName);
         
        this.globWatcherStub = new com.veyron2.services.watch.gen_impl.GlobWatcherStub(client, veyronName);
         
        this.queryWatcherStub = new io.veyron.store.veyron2.services.watch.gen_impl.QueryWatcherStub(client, veyronName);
         
    }

    // Methods from interface DirOrObject.


    
    public void remove(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
         remove(context, null);
    }
    
    public void remove(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, DirOrObjectStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{  };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "remove", inArgs, veyronOpts);

        // Finish the call.
        
        

        
        final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
        call.finish(resultTypes);
         

        
    }

    
    public com.veyron2.vdl.ClientStream<java.lang.Void,io.veyron.store.veyron2.services.store.QueryResult, java.lang.Void> query(final com.veyron2.ipc.Context context, final io.veyron.store.veyron2.query.Query Q) throws com.veyron2.ipc.VeyronException {
        return query(context, Q, null);
    }
    
    public com.veyron2.vdl.ClientStream<java.lang.Void,io.veyron.store.veyron2.services.store.QueryResult, java.lang.Void> query(final com.veyron2.ipc.Context context, final io.veyron.store.veyron2.query.Query Q, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        // Add VDL path option.
        // NOTE(spetrovic): this option is temporary and will be removed soon after we switch
        // Java to encoding/decoding from vom.Value objects.
        if (veyronOpts == null) veyronOpts = new com.veyron2.Options();
        if (!veyronOpts.has(com.veyron2.OptionDefs.VDL_INTERFACE_PATH)) {
            veyronOpts.set(com.veyron2.OptionDefs.VDL_INTERFACE_PATH, DirOrObjectStub.vdlIfacePathOpt);
        }

        
        // Start the call.
        final java.lang.Object[] inArgs = new java.lang.Object[]{ Q };
        final com.veyron2.ipc.Client.Call call = this.client.startCall(context, this.veyronName, "query", inArgs, veyronOpts);

        // Finish the call.
        
         
        return new com.veyron2.vdl.ClientStream<java.lang.Void, io.veyron.store.veyron2.services.store.QueryResult, java.lang.Void>() {
            @Override
            public void send(final java.lang.Void item) throws com.veyron2.ipc.VeyronException {
                call.send(item);
            }
            @Override
            public io.veyron.store.veyron2.services.store.QueryResult recv() throws java.io.EOFException, com.veyron2.ipc.VeyronException {
                final com.google.common.reflect.TypeToken<?> type = new com.google.common.reflect.TypeToken<io.veyron.store.veyron2.services.store.QueryResult>() {
                    private static final long serialVersionUID = 1L;
                };
                final java.lang.Object result = call.recv(type);
                try {
                    return (io.veyron.store.veyron2.services.store.QueryResult)result;
                } catch (java.lang.ClassCastException e) {
                    throw new com.veyron2.ipc.VeyronException("Unexpected result type: " + result.getClass().getCanonicalName());
                }
            }
            @Override
            public java.lang.Void finish() throws com.veyron2.ipc.VeyronException {
                
                final com.google.common.reflect.TypeToken<?>[] resultTypes = new com.google.common.reflect.TypeToken<?>[]{};
                call.finish(resultTypes);
                return null;
                 
            }
        };
        
    }




    @Override
    public boolean exists(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
        return this.statableStub.exists(context);
    }
    @Override
    public boolean exists(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.statableStub.exists(context, veyronOpts);
    }

    @Override
    public io.veyron.store.veyron2.storage.Stat stat(final com.veyron2.ipc.Context context) throws com.veyron2.ipc.VeyronException {
        
        return this.statableStub.stat(context);
    }
    @Override
    public io.veyron.store.veyron2.storage.Stat stat(final com.veyron2.ipc.Context context, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.statableStub.stat(context, veyronOpts);
    }

    @Override
    public java.lang.String newTransaction(final com.veyron2.ipc.Context context, final java.util.List<com.veyron2.vdl.Any> Options) throws com.veyron2.ipc.VeyronException {
        
        return this.transactableStub.newTransaction(context, Options);
    }
    @Override
    public java.lang.String newTransaction(final com.veyron2.ipc.Context context, final java.util.List<com.veyron2.vdl.Any> Options, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.transactableStub.newTransaction(context, Options, veyronOpts);
    }

    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.watch.types.Change, java.lang.Void> watchQuery(final com.veyron2.ipc.Context context, final io.veyron.store.veyron2.services.watch.types.QueryRequest Req) throws com.veyron2.ipc.VeyronException {
        
        return this.queryWatcherStub.watchQuery(context, Req);
    }
    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.watch.types.Change, java.lang.Void> watchQuery(final com.veyron2.ipc.Context context, final io.veyron.store.veyron2.services.watch.types.QueryRequest Req, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.queryWatcherStub.watchQuery(context, Req, veyronOpts);
    }

    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.mounttable.types.MountEntry, java.lang.Void> glob(final com.veyron2.ipc.Context context, final java.lang.String pattern) throws com.veyron2.ipc.VeyronException {
        
        return this.globbableStub.glob(context, pattern);
    }
    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.mounttable.types.MountEntry, java.lang.Void> glob(final com.veyron2.ipc.Context context, final java.lang.String pattern, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.globbableStub.glob(context, pattern, veyronOpts);
    }

    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.watch.types.Change, java.lang.Void> watchGlob(final com.veyron2.ipc.Context context, final com.veyron2.services.watch.types.GlobRequest Req) throws com.veyron2.ipc.VeyronException {
        
        return this.globWatcherStub.watchGlob(context, Req);
    }
    @Override
    public com.veyron2.vdl.ClientStream<java.lang.Void,com.veyron2.services.watch.types.Change, java.lang.Void> watchGlob(final com.veyron2.ipc.Context context, final com.veyron2.services.watch.types.GlobRequest Req, com.veyron2.Options veyronOpts) throws com.veyron2.ipc.VeyronException {
        
        return  this.globWatcherStub.watchGlob(context, Req, veyronOpts);
    }


}