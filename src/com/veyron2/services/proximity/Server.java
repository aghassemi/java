// This file was auto-generated by the veyron vdl tool.
// Source(s):  proximity.vdl
package com.veyron2.services.proximity;

import com.veyron2.ipc.ServerCall;
import com.veyron2.ipc.VeyronException;
import java.util.ArrayList;

public class Server { 
	/* Server stub creation methods for interfaces in file: proximity.vdl. */
	public static Object newProximityAnnouncer(ProximityAnnouncerService service) { 
		return new ProximityAnnouncerStub(service);
	}
	public static Object newProximityScanner(ProximityScannerService service) { 
		return new ProximityScannerStub(service);
	}
	public static Object newProximity(ProximityService service) { 
		final Server.ProximityAnnouncerStub proximityAnnouncerService = (Server.ProximityAnnouncerStub)Server.newProximityAnnouncer(service);
		final Server.ProximityScannerStub proximityScannerService = (Server.ProximityScannerStub)Server.newProximityScanner(service);
		return new ProximityStub(service, proximityAnnouncerService, proximityScannerService);
	}
	
	/* Server stubs for interfaces in file: proximity.vdl. */
	public static class ProximityAnnouncerStub {
		private final ProximityAnnouncerService service;

		ProximityAnnouncerStub(ProximityAnnouncerService service) {
			this.service = service;
		}
		/**
		 * Returns all tags associated with the provided method or null if the method isn't implemented
		 * by this service.
		 */
		@SuppressWarnings("unused")
		public Object[] getMethodTags(ServerCall call, String method) { 
			if (method == "RegisterName") {
				return new Object[]{ 2 };
			}
			if (method == "UnregisterName") {
				return new Object[]{ 2 };
			}
			return null;
		}
		// Methods from interface ProximityAnnouncer.
		public void registerName(ServerCall call, String Name) throws VeyronException { 
			this.service.registerName(call, Name);
		}
		public void unregisterName(ServerCall call, String Name) throws VeyronException { 
			this.service.unregisterName(call, Name);
		}
	}
	public static class ProximityScannerStub {
		private final ProximityScannerService service;

		ProximityScannerStub(ProximityScannerService service) {
			this.service = service;
		}
		/**
		 * Returns all tags associated with the provided method or null if the method isn't implemented
		 * by this service.
		 */
		@SuppressWarnings("unused")
		public Object[] getMethodTags(ServerCall call, String method) { 
			if (method == "NearbyDevices") {
				return new Object[]{ 1 };
			}
			return null;
		}
		// Methods from interface ProximityScanner.
		public ArrayList<Device> nearbyDevices(ServerCall call) throws VeyronException { 
			return this.service.nearbyDevices(call);
		}
	}
	public static class ProximityStub {
		private final ProximityService service;
		private final Server.ProximityAnnouncerStub proximityAnnouncerService;
		private final Server.ProximityScannerStub proximityScannerService;

		ProximityStub(ProximityService service, Server.ProximityAnnouncerStub proximityAnnouncerService, Server.ProximityScannerStub proximityScannerService) {
			this.service = service;
			this.proximityAnnouncerService = proximityAnnouncerService;
			this.proximityScannerService = proximityScannerService;
		}
		/**
		 * Returns all tags associated with the provided method or null if the method isn't implemented
		 * by this service.
		 */
		@SuppressWarnings("unused")
		public Object[] getMethodTags(ServerCall call, String method) { 
			{
				final Object[] tags = this.proximityAnnouncerService.getMethodTags(call, method);
				if (tags != null) return tags;
			}
			{
				final Object[] tags = this.proximityScannerService.getMethodTags(call, method);
				if (tags != null) return tags;
			}
			return null;
		}
		// Methods from interface Proximity.
		// Methods from sub-interface ProximityAnnouncer.
		public void registerName(ServerCall call, String Name) throws VeyronException {
			this.proximityAnnouncerService.registerName(call, Name);
		}
		public void unregisterName(ServerCall call, String Name) throws VeyronException {
			this.proximityAnnouncerService.unregisterName(call, Name);
		}
		// Methods from sub-interface ProximityScanner.
		public ArrayList<Device> nearbyDevices(ServerCall call) throws VeyronException {
			return this.proximityScannerService.nearbyDevices(call);
		}
	}
}
