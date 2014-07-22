// This file was auto-generated by the veyron vdl tool.
// Source(s):  build.vdl
package com.veyron2.services.mgmt.build.gen_impl;

import com.google.common.reflect.TypeToken;
import com.veyron2.ipc.ServerCall;
import com.veyron2.ipc.VeyronException;
import com.veyron2.services.mgmt.binary.Description;
import com.veyron2.services.mgmt.build.Build;
import com.veyron2.services.mgmt.build.BuildFactory;
import com.veyron2.services.mgmt.build.BuildService;
import com.veyron2.services.mgmt.build.File;
import com.veyron2.services.mgmt.build.VeyronConsts;
import com.veyron2.vdl.Stream;
import java.util.ArrayList;

public class BuildServiceWrapper {

	private final BuildService service;

	public BuildServiceWrapper(BuildService service) {
		this.service = service;
	}
	/**
	 * Returns all tags associated with the provided method or null if the method isn't implemented
	 * by this service.
	 */
	public Object[] getMethodTags(ServerCall call, String method) throws VeyronException { 
		if ("build".equals(method)) {
			return new Object[]{  };
		}
		if ("describe".equals(method)) {
			return new Object[]{  };
		}
		throw new VeyronException("method: " + method + " not found");
	}
	// Methods from interface Build.
	public ArrayList<Byte> build(ServerCall call) throws VeyronException { 
		final ServerCall serverCall = call;
		final Stream<File,File> stream = new Stream<File,File>() {
			@Override
			public void send(File item) throws VeyronException {
				serverCall.send(item);
			}
			@Override
			public File recv() throws java.io.EOFException, VeyronException {
				final TypeToken<?> type = new TypeToken<File>() {};
				final Object result = serverCall.recv(type);
				try {
					return (File)result;
				} catch (java.lang.ClassCastException e) {
					throw new VeyronException("Unexpected result type: " + result.getClass().getCanonicalName());
				}
			}
		};
		return this.service.build(call, stream);
	}
	public Description describe(ServerCall call, String Name) throws VeyronException { 
		return this.service.describe(call, Name);
	}
}
