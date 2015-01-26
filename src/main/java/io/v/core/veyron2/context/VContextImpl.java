package io.v.core.veyron2.context;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import io.v.core.veyron2.VeyronException;

import java.util.concurrent.CountDownLatch;

public class VContextImpl extends CancelableVContext {
	private static final String TAG = "Veyron runtime";

	private long nativePtr;
	private long nativeCancelPtr;  // zero for non-cancelable contexts.
	// Cached "done()" CountDownLatch, as we're supposed to return the same object on every call.
	private volatile CountDownLatch doneLatch = null;

	private native DateTime nativeDeadline(long nativePtr) throws VeyronException;
	private native CountDownLatch nativeDone(long nativePtr) throws VeyronException;
	private native Object nativeValue(long nativePtr, Object key) throws VeyronException;
	private native CancelableVContext nativeWithCancel(long nativePtr) throws VeyronException;
	private native CancelableVContext nativeWithDeadline(long nativePtr, DateTime deadline)
		throws VeyronException;
	private native CancelableVContext nativeWithTimeout(long nativePtr, Duration timeout)
		throws VeyronException;
	private native VContext nativeWithValue(long nativePtr, Object key, Object value)
		throws VeyronException;
	private native void nativeCancel(long nativeCancelPtr) throws VeyronException;
	private native void nativeFinalize(long nativePtr, long nativeCancelPtr);

	private VContextImpl(long nativePtr, long nativeCancelPtr) {
		this.nativePtr = nativePtr;
		this.nativeCancelPtr = nativeCancelPtr;
	}
	@Override
	public DateTime deadline() {
		try {
				return nativeDeadline(this.nativePtr);
		} catch (VeyronException e) {
				android.util.Log.e(TAG, "Couldn't get deadline: " + e.getMessage());
			return null;
		}
	}
	@Override
	public CountDownLatch done() {
		// NOTE(spetrovic): We may have to lock needlessly if nativeDone() returns a null
		// CountDownLatch, but that's OK for now.
		if (this.doneLatch != null) return this.doneLatch;
		synchronized (this) {
			if (this.doneLatch != null) return this.doneLatch;
			try {
				this.doneLatch = nativeDone(this.nativePtr);
				return this.doneLatch;
			} catch (VeyronException e) {
				android.util.Log.e(TAG, "Couldn't invoke done: " + e.getMessage());
				return null;
			}
		}
	}
	@Override
	public Object value(Object key) {
		try {
			return nativeValue(this.nativePtr, key);
		} catch (VeyronException e) {
			android.util.Log.e(TAG, "Couldn't get value: " + e.getMessage());
			return null;
		}
	}
	@Override
	public CancelableVContext withCancel() {
		try {
			return nativeWithCancel(this.nativePtr);
		} catch (VeyronException e) {
			throw new RuntimeException("Couldn't create cancelable context: " + e.getMessage());
		}
	}
	@Override
	public CancelableVContext withDeadline(DateTime deadline) {
		try {
			return nativeWithDeadline(this.nativePtr, deadline);
		} catch (VeyronException e) {
			throw new RuntimeException("Couldn't create context with deadline: " + e.getMessage());
		}
	}
	@Override
	public CancelableVContext withTimeout(Duration timeout) {
		try {
			return nativeWithTimeout(this.nativePtr, timeout);
		} catch (VeyronException e) {
			throw new RuntimeException("Couldn't create context with timeout: " + e.getMessage());
		}
	}
	@Override
	public io.v.core.veyron2.context.VContext withValue(Object key, Object value) {
		try {
			return nativeWithValue(this.nativePtr, key, value);
		} catch (VeyronException e) {
			throw new RuntimeException("Couldn't create context with data: " + e.getMessage());
		}
	}
	@Override
	public void cancel() {
		try {
			nativeCancel(this.nativeCancelPtr);
		} catch (VeyronException e) {
			android.util.Log.e(TAG, "Couldn't cancel context: " + e.getMessage());
		}
	}
	@Override
	void implementationsOnlyInThisPackage() {}
	@Override
	protected void finalize() {
		nativeFinalize(this.nativePtr, this.nativeCancelPtr);
	}
	private long nativePtr() {
		return this.nativePtr;
	}
}