// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.android.v23;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.common.base.Preconditions;

import io.v.android.UiThreadExecutor;
import io.v.android.impl.google.services.gcm.GcmRegistrationService;
import io.v.v23.Options;
import io.v.v23.context.VContext;
import io.v.v23.security.Blessings;
import io.v.v23.security.BlessingStore;
import io.v.v23.security.Constants;
import io.v.v23.security.VPrincipal;
import io.v.v23.security.VSecurity;
import io.v.v23.security.VSigner;
import io.v.v23.verror.VException;

import java.io.File;
import java.security.KeyStore;
import java.security.interfaces.ECPublicKey;

/**
 * The local android environment allowing clients and servers to communicate with one another.
 * The expected usage pattern of this class goes something like this:
 * <p><blockquote><pre>
 * private VContext mBaseContext;
 *
 * @Override
 * protected void onCreate(Bundle savedInstanceState) {
 *     super.onCreate(savedInstanceState);
 *     mBaseContext = V.init(getContext(), opts);
 * }
 *
 * @Override
 * protected void onDestroy() {
 *     super.onDestroy();
 *     mBaseContext.cancel();
 * }
 * </pre></blockquote><p>
 */
public class V extends io.v.v23.V {
    private static native void nativeInitGlobalAndroid(Options opts) throws VException;

    private static volatile VContext globalContext;

    // Initializes the Vanadium Android-specific global state.
    private static VContext initGlobalAndroid(VContext ctx, Options opts) {
        try {
            nativeInitGlobalAndroid(opts);
            return V.withExecutor(ctx, UiThreadExecutor.INSTANCE);
        } catch (VException e) {
            throw new RuntimeException("Couldn't initialize global Android state", e);
        }
    }

    // Initializes the Vanadium Android global state, i.e., state that is shared across all
    // the activities/fragments/services in the process.
    private static VContext initGlobal(Context androidCtx, Options opts) {
        if (globalContext != null) {
            return globalContext;
        }
        synchronized (V.class) {
            if (globalContext != null) {
                return globalContext;
            }
            if (opts == null) opts = new Options();
            VContext ctx = initGlobalShared(opts);
            ctx = initGlobalAndroid(ctx, opts);
            // Set the VException component name to the Android context package name.
            ctx = VException.contextWithComponentName(ctx, androidCtx.getPackageName());
            try {
                // Initialize the principal.
                ctx = V.withPrincipal(ctx, createPrincipal(androidCtx));
            } catch (VException e) {
                throw new RuntimeException("Couldn't setup Vanadium principal", e);
            }
            globalContext = ctx;
            // Start the GCM registration service, which obtains the GCM token for the app
            // (if the app is configured to use GCM).
            // NOTE: this call may lead to a recursive call to V.init() (in a separate thread),
            // so keep this code below the line where 'globalContext' is set, or we may run
            // into an infinite recursion.
            Intent intent = new Intent(androidCtx, GcmRegistrationService.class);
            androidCtx.startService(intent);
            return ctx;
        }
    }

    // Initializes Vanadium state that's local to the invoking activity/service.
    private static VContext initAndroidLocal(VContext ctx, Context androidCtx, Options opts) {
        return ctx.withValue(new AndroidContextKey(), androidCtx);
    }

    /**
     * Initializes the Vanadium environment and creates a new base Vanadium context for the given
     * Android context and the given options.  This method may be called multiple times for
     * an Android context: in each invocation, a different base context will be returned.
     * <p>
     * {@link VContext#cancel() Canceling} the returned context will release all the Vanadium
     * resources associated with the Android context.  Forgetting to cancel the context will
     * therefore result in memory leaks of those resources.
     * <p>
     * See class docs for the expected usage pattern.
     * <p>
     * A caller may pass the following option that specifies the runtime implementation to be used:
     * <p><ul>
     *     <li>{@link io.v.v23.OptionDefs#RUNTIME}</li>
     * </ul><p>
     * <p>
     * If this option isn't provided, the default runtime implementation is used.
     *
     * @param  androidCtx  Android application context
     * @param  opts        options for the default runtime
     * @return             base context
     */
    public static VContext init(Context androidCtx, Options opts) {
        Preconditions.checkArgument(androidCtx != null);
        VContext ctx = initGlobal(androidCtx, opts);
        ctx = initAndroidLocal(ctx, androidCtx, opts);
        return ctx.withCancel();
    }

    /**
     * Initializes the Vanadium environment without options.  See
     * {@link #init(Context,Options)} for more information.
     */
    public static VContext init(Context androidCtx) {
        return V.init(androidCtx, null);
    }

    @Deprecated
    public static VContext init() {
        throw new RuntimeException("Must call Android init with a context.");
    }

    private static VPrincipal createPrincipal(Context ctx) throws VException {
        // Check if the private key has already been generated for this package.
        // (NOTE: Android package names are unique.)
        KeyStore.PrivateKeyEntry keyEntry =
                KeyStoreUtil.getKeyStorePrivateKey(ctx.getPackageName());
        if (keyEntry == null) {
            // Generate a new private key.
            keyEntry = KeyStoreUtil.genKeyStorePrivateKey(ctx, ctx.getPackageName());
        }
        VSigner signer;
        VPrincipal principal;
        try {
            signer = VSecurity.newSigner(
                        keyEntry.getPrivateKey(),
                        (ECPublicKey) keyEntry.getCertificate().getPublicKey());
            principal = VSecurity.newPersistentPrincipal(signer, ctx.getFilesDir().getAbsolutePath());
        } catch (VException e) {
            // If the signature verification fails, it could be that we have old/corrupt
            // information in the key store or data in internal storage is old. So, we
            // delete the old private key associated with the unique package name, delete
            // the internal storage data, and try again.
            Log.w("V", "Retrying principal initialization due to: ", e);
            KeyStoreUtil.deleteKeyStorePrivateKey(ctx.getPackageName());
            keyEntry = KeyStoreUtil.genKeyStorePrivateKey(ctx, ctx.getPackageName());
            // Delete all files in internal storage.
            File dir = ctx.getFilesDir();
            for (File file: dir.listFiles()) file.delete();
            signer = VSecurity.newSigner(
                        keyEntry.getPrivateKey(),
                        (ECPublicKey) keyEntry.getCertificate().getPublicKey());
            principal = VSecurity.newPersistentPrincipal(signer, ctx.getFilesDir().getAbsolutePath());
        }

        // Make sure we have at least one (i.e., self-signed) blessing in the store.
        BlessingStore store = principal.blessingStore();
        if (store.peerBlessings().isEmpty()) {
            Blessings self = principal.blessSelf(ctx.getPackageName());
            store.setDefaultBlessings(self);
            store.set(self, Constants.ALL_PRINCIPALS);
            VSecurity.addToRoots(principal, self);
        }
        return principal;
    }

    /**
     * Returns the Android context attached to the given Vanadium context or {@code null} if no
     * Android context is attached.
     * <p>
     * If the passed-in context is derived from the context returned by {@link #init}, the returned
     * Android context will never be {@code null}.
     */
    public static Context getAndroidContext(VContext ctx) {
        return (Context) ctx.value(new AndroidContextKey());
    }

    private static class AndroidContextKey {
        @Override
        public int hashCode() {
            return 0;
        }
    }

    private V() {}
}
