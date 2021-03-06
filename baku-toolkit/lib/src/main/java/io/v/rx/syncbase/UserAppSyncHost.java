// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.rx.syncbase;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

import io.v.baku.toolkit.blessings.BlessingsUtils;
import io.v.baku.toolkit.blessings.ClientUser;
import io.v.impl.google.naming.NamingUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAppSyncHost implements SyncHostLevel {
    private final String mAppName, mSgHostSuffix, mRendezvousSuffix;

    public UserAppSyncHost(final String appName) {
        this(appName, DEFAULT_SG_HOST_SUFFIX, DEFAULT_RENDEZVOUS_SUFFIX);
    }

    public UserAppSyncHost(final Context androidContext) {
        this(androidContext.getPackageName());
    }

    @Override
    public String getSyncgroupHostName(final ClientUser clientUser) {
        return NamingUtil.join(BlessingsUtils.userMount(clientUser.getUsername()),
                mAppName, mSgHostSuffix);
    }

    @Override
    public List<String> getRendezvousTableNames(final ClientUser clientUser) {
        return Arrays.asList(NamingUtil.join(
                BlessingsUtils.userMount(clientUser.getUsername()), mAppName, mRendezvousSuffix));
    }
}
