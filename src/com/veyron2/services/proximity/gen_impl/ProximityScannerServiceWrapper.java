// This file was auto-generated by the veyron vdl tool.
// Source(s):  proximity.vdl
package com.veyron2.services.proximity.gen_impl;

import com.veyron2.ipc.ServerCall;
import com.veyron2.ipc.VeyronException;
import com.veyron2.services.proximity.Device;
import com.veyron2.services.proximity.Proximity;
import com.veyron2.services.proximity.ProximityAnnouncer;
import com.veyron2.services.proximity.ProximityAnnouncerFactory;
import com.veyron2.services.proximity.ProximityAnnouncerService;
import com.veyron2.services.proximity.ProximityFactory;
import com.veyron2.services.proximity.ProximityScanner;
import com.veyron2.services.proximity.ProximityScannerFactory;
import com.veyron2.services.proximity.ProximityScannerService;
import com.veyron2.services.proximity.ProximityService;
import java.util.ArrayList;

public class ProximityScannerServiceWrapper {

	private final ProximityScannerService service;

	public ProximityScannerServiceWrapper(ProximityScannerService service) {
		this.service = service;
	}
	/**
	 * Returns all tags associated with the provided method or null if the method isn't implemented
	 * by this service.
	 */
	public Object[] getMethodTags(ServerCall call, String method) throws VeyronException { 
		if ("nearbyDevices".equals(method)) {
			return new Object[]{ new com.veyron2.security.Label(1) };
		}
		throw new VeyronException("method: " + method + " not found");
	}
	// Methods from interface ProximityScanner.
	public ArrayList<Device> nearbyDevices(ServerCall call) throws VeyronException { 
		return this.service.nearbyDevices(call);
	}
}
