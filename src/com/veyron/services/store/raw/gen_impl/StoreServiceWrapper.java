// This file was auto-generated by the veyron vdl tool.
// Source(s):  service.vdl
package com.veyron.services.store.raw.gen_impl;

import com.google.common.reflect.TypeToken;
import com.veyron.services.store.raw.Mutation;
import com.veyron.services.store.raw.Request;
import com.veyron.services.store.raw.Store;
import com.veyron.services.store.raw.StoreFactory;
import com.veyron.services.store.raw.StoreService;
import com.veyron.services.store.raw.VeyronConsts;
import com.veyron2.ipc.ServerCall;
import com.veyron2.ipc.VeyronException;
import com.veyron2.services.watch.ChangeBatch;
import com.veyron2.vdl.Stream;

public class StoreServiceWrapper {

	private final StoreService service;

	public StoreServiceWrapper(StoreService service) {
		this.service = service;
	}
	/**
	 * Returns all tags associated with the provided method or null if the method isn't implemented
	 * by this service.
	 */
	public Object[] getMethodTags(ServerCall call, String method) throws VeyronException { 
		if ("watch".equals(method)) {
			return new Object[]{  };
		}
		if ("putMutations".equals(method)) {
			return new Object[]{  };
		}
		throw new VeyronException("method: " + method + " not found");
	}
	// Methods from interface Store.
	public void watch(ServerCall call, Request Req) throws VeyronException { 
		final ServerCall serverCall = call;
		final Stream<ChangeBatch,Void> stream = new Stream<ChangeBatch,Void>() {
			@Override
			public void send(ChangeBatch item) throws VeyronException {
				serverCall.send(item);
			}
			@Override
			public Void recv() throws java.io.EOFException, VeyronException {
				final TypeToken<?> type = new TypeToken<Void>() {};
				final Object result = serverCall.recv(type);
				try {
					return (Void)result;
				} catch (java.lang.ClassCastException e) {
					throw new VeyronException("Unexpected result type: " + result.getClass().getCanonicalName());
				}
			}
		};
		this.service.watch(call, Req, stream);
	}
	public void putMutations(ServerCall call) throws VeyronException { 
		final ServerCall serverCall = call;
		final Stream<Void,Mutation> stream = new Stream<Void,Mutation>() {
			@Override
			public void send(Void item) throws VeyronException {
				serverCall.send(item);
			}
			@Override
			public Mutation recv() throws java.io.EOFException, VeyronException {
				final TypeToken<?> type = new TypeToken<Mutation>() {};
				final Object result = serverCall.recv(type);
				try {
					return (Mutation)result;
				} catch (java.lang.ClassCastException e) {
					throw new VeyronException("Unexpected result type: " + result.getClass().getCanonicalName());
				}
			}
		};
		this.service.putMutations(call, stream);
	}
}
