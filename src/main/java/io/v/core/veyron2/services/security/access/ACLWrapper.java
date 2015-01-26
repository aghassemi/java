package io.v.core.veyron2.services.security.access;

import io.v.core.veyron2.VeyronException;
import io.v.core.veyron2.security.Authorizer;
import io.v.core.veyron2.security.VContext;

public class ACLWrapper implements Authorizer {
	private static final String TAG = "Veyron runtime";

	private static native ACLWrapper nativeWrap(ACL acl) throws VeyronException;

	/**
	 * Wraps the provided ACL.
	 *
	 * @param  acl             ACL being wrapped.
	 * @return                 wrapped ACL.
	 * @throws VeyronException if the ACL couldn't be wrapped.
	 */
	public static ACLWrapper wrap(ACL acl) throws VeyronException {
		return nativeWrap(acl);
	}

    private native boolean nativeIncludes(long nativePtr, String[] blessings);
    private native void nativeAuthorize(long nativePtr, VContext ctx);
	private native void nativeFinalize(long nativePtr);

	private long nativePtr;
	private ACL acl;

	private ACLWrapper(long nativePtr, ACL acl) {
		this.nativePtr = nativePtr;
		this.acl = acl;
	}

	/**
	 * Returns true iff the ACL grants access to a principal that presents these blessings.
	 *
	 * @param  blessings blessings we are getting access for.
	 * @return           true iff the ACL grants access to a principal that presents these
	 *                   blessings.
	 */
	public boolean includes(String... blessings) {
		return nativeIncludes(this.nativePtr, blessings);
	}

	/**
	 * Implements {@code Authorizer} where the request is authorized only if the remote blessings
	 * are included in the ACL.
	 *
	 * @param ctx              security context of the request.
	 * @throws VeyronException if the request is not authorized.
	 */
	@Override
	public void authorize(VContext ctx) throws VeyronException {
	    nativeAuthorize(this.nativePtr, ctx);
	}

	/*
	 * Returns the ACL contained in the wrapper.
	 *
	 * @return the ACL contained in the wrapper.
	 */
	public ACL getACL() {
		return this.acl;
	}

	@Override
	protected void finalize() {
		nativeFinalize(this.nativePtr);
	}
}