// This file was auto-generated by the veyron vdl tool.
// Source(s):  node.vdl
package com.veyron2.services.mgmt.node.gen_impl;

import com.veyron2.ipc.ServerCall;
import com.veyron2.ipc.VeyronException;
import com.veyron2.services.mgmt.binary.Description;
import com.veyron2.services.mgmt.node.Application;
import com.veyron2.services.mgmt.node.ApplicationFactory;
import com.veyron2.services.mgmt.node.ApplicationService;
import com.veyron2.services.mgmt.node.Node;
import com.veyron2.services.mgmt.node.NodeFactory;
import com.veyron2.services.mgmt.node.NodeService;
import java.util.ArrayList;

public class NodeServiceWrapper {

	private final NodeService service;
	private final ApplicationServiceWrapper application;

	public NodeServiceWrapper(NodeService service) {
		this.application = new ApplicationServiceWrapper(service);
		this.service = service;
	}
	/**
	 * Returns all tags associated with the provided method or null if the method isn't implemented
	 * by this service.
	 */
	public Object[] getMethodTags(ServerCall call, String method) throws VeyronException { 
		try {
			return this.application.getMethodTags(call, method);
		} catch (VeyronException e) {}  // method not found.
		if ("describe".equals(method)) {
			return new Object[]{  };
		}
		if ("isRunnable".equals(method)) {
			return new Object[]{  };
		}
		if ("reset".equals(method)) {
			return new Object[]{  };
		}
		throw new VeyronException("method: " + method + " not found");
	}
	// Methods from interface Node.
	public com.veyron2.services.mgmt.node.Description describe(ServerCall call) throws VeyronException { 
		return this.service.describe(call);
	}
	public boolean isRunnable(ServerCall call, Description Description) throws VeyronException { 
		return this.service.isRunnable(call, Description);
	}
	public void reset(ServerCall call, long Deadline) throws VeyronException { 
		this.service.reset(call, Deadline);
	}
	// Methods from sub-interface Application.
	public String install(ServerCall call, String Name) throws VeyronException {
		return this.application.install(call, Name);
	}
	public void refresh(ServerCall call) throws VeyronException {
		this.application.refresh(call);
	}
	public void restart(ServerCall call) throws VeyronException {
		this.application.restart(call);
	}
	public void resume(ServerCall call) throws VeyronException {
		this.application.resume(call);
	}
	public void revert(ServerCall call) throws VeyronException {
		this.application.revert(call);
	}
	public ArrayList<String> start(ServerCall call) throws VeyronException {
		return this.application.start(call);
	}
	public void stop(ServerCall call, long Deadline) throws VeyronException {
		this.application.stop(call, Deadline);
	}
	public void suspend(ServerCall call) throws VeyronException {
		this.application.suspend(call);
	}
	public void uninstall(ServerCall call) throws VeyronException {
		this.application.uninstall(call);
	}
	public void update(ServerCall call) throws VeyronException {
		this.application.update(call);
	}
	public void updateTo(ServerCall call, String Name) throws VeyronException {
		this.application.updateTo(call, Name);
	}
}
