// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.impl.google.rt;

import io.v.v23.Options;
import io.v.v23.OptionDefs;
import io.v.v23.context.VContext;
import io.v.v23.rpc.ListenSpec;
import io.v.v23.namespace.Namespace;
import io.v.v23.security.Principal;
import io.v.v23.verror.VException;

/**
 * VRuntime is an implementation of {@code io.v.v23.VRuntime} that calls to native Go
 * code for most of its functionalities.
 */
public class VRuntime implements io.v.v23.VRuntime {
    private static native VContext nativeInit(int numCpus) throws VException;
    private static native VContext nativeSetNewClient(VContext ctx, Options opts)
            throws VException;
    private static native io.v.v23.rpc.Client nativeGetClient(VContext ctx)
            throws VException;
    private static native io.v.v23.rpc.Server nativeNewServer(VContext ctx) throws VException;
    private static native VContext nativeSetPrincipal(VContext ctx, Principal principal)
            throws VException;
    private static native Principal nativeGetPrincipal(VContext ctx) throws VException;
    private static native VContext nativeSetNewNamespace(VContext ctx, String... roots)
            throws VException;
    private static native Namespace nativeGetNamespace(VContext ctx) throws VException;
    private static native ListenSpec nativeGetListenSpec(VContext ctx) throws VException;

    /**
     * Returns a new runtime instance.
     *
     * @return      a new runtime instance
     */
    public static VRuntime create(Options opts) throws VException {
        int numCpus = opts.has(OptionDefs.RUNTIME_NUM_CPUS)
                ? opts.get(OptionDefs.RUNTIME_NUM_CPUS, Integer.class)
                : 1;
        if (numCpus < 1) {
            numCpus = 1;
        }
        return new VRuntime(nativeInit(numCpus));
    }

    private final VContext ctx;  // non-null

    private VRuntime(VContext ctx) {
        this.ctx = ctx;
    }
    @Override
    public VContext setNewClient(VContext ctx, Options opts) throws VException {
        return nativeSetNewClient(ctx, opts);
    }
    @Override
    public io.v.v23.rpc.Client getClient(VContext ctx) {
        try {
            return nativeGetClient(ctx);
        } catch (VException e) {
            throw new RuntimeException("Couldn't get client", e);
        }
    }
    @Override
    public io.v.v23.rpc.Server newServer(VContext ctx, Options opts) throws VException {
        return nativeNewServer(ctx);
    }
    @Override
    public VContext setPrincipal(VContext ctx, Principal principal) throws VException {
        return nativeSetPrincipal(ctx, principal);
    }
    @Override
    public Principal getPrincipal(VContext ctx) {
        try {
            return nativeGetPrincipal(ctx);
        } catch (VException e) {
            throw new RuntimeException("Couldn't get principal", e);
        }
    }
    @Override
    public VContext setNewNamespace(VContext ctx, String... roots) throws VException {
        return nativeSetNewNamespace(ctx, roots);
    }
    @Override
    public Namespace getNamespace(VContext ctx) {
        try {
            return nativeGetNamespace(ctx);
        } catch (VException e) {
            throw new RuntimeException("Couldn't get namespace", e);
        }
    }
    @Override
    public ListenSpec getListenSpec(VContext ctx) {
        try {
            return nativeGetListenSpec(ctx);
        } catch (VException e) {
            throw new RuntimeException("Couldn't get listen spec: ", e);
        }
    }
    @Override
    public VContext getContext() {
        return this.ctx;
    }
}