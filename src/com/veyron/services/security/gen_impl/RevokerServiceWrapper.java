// This file was auto-generated by the veyron vdl tool.
// Source(s):  revoker.vdl discharger.vdl
package com.veyron.services.security.gen_impl;

import com.veyron.services.security.Discharger;
import com.veyron.services.security.DischargerFactory;
import com.veyron.services.security.DischargerService;
import com.veyron.services.security.RevocationToken;
import com.veyron.services.security.Revoker;
import com.veyron.services.security.RevokerFactory;
import com.veyron.services.security.RevokerService;
import com.veyron2.ipc.ServerCall;
import com.veyron2.ipc.VeyronException;

public class RevokerServiceWrapper {

	private final RevokerService service;

	public RevokerServiceWrapper(RevokerService service) {
		this.service = service;
	}
	/**
	 * Returns all tags associated with the provided method or null if the method isn't implemented
	 * by this service.
	 */
	public Object[] getMethodTags(ServerCall call, String method) throws VeyronException { 
		if ("revoke".equals(method)) {
			return new Object[]{ new com.veyron2.security.Label(2) };
		}
		throw new VeyronException("method: " + method + " not found");
	}
	// Methods from interface Revoker.
	public void revoke(ServerCall call, RevocationToken caveatPreimage) throws VeyronException { 
		this.service.revoke(call, caveatPreimage);
	}
}
