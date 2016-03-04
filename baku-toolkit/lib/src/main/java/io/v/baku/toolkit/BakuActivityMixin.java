// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.baku.toolkit;

import android.app.Activity;
import android.os.Bundle;

import io.v.baku.toolkit.bind.CollectionBinding;
import io.v.baku.toolkit.bind.SyncbaseBinding;
import io.v.baku.toolkit.syncbase.BakuDb;
import io.v.baku.toolkit.syncbase.BakuSyncbase;
import io.v.baku.toolkit.syncbase.BakuTable;
import io.v.rx.syncbase.UserCloudSyncgroup;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import rx.subscriptions.CompositeSubscription;

/**
 * Activity mix-in for activities with distributed UI state. By default, shared state is stored
 * in Syncbase under _app.package.name_/db/ui.
 *
 * Default activity extensions incorporating this mix-in are available:
 *
 * * {@link BakuActivity} (extends {@link Activity})
 * * {@link BakuAppCompatActivity} (extends {@link android.support.v7.app.AppCompatActivity})
 *
 * Since Java doesn't actually support multiple inheritance, clients requiring custom inheritance
 * hierarchies will need to wire in manually, like any of the examples above. Alternatively, this
 * class may be used via pure composition, as detailed at
 * {@link BakuActivityMixin#BakuActivityMixin(Activity, Bundle)}.
 *
 * @see io.v.baku.toolkit
 */
@Accessors(prefix = "m")
@Slf4j
public class BakuActivityMixin<T extends Activity> implements BakuActivityTrait<T> {
    @Getter
    private final VAndroidContextTrait<T> mVAndroidContextTrait;

    @Getter
    private final BakuSyncbase mSyncbase;
    @Getter
    private final BakuDb mSyncbaseDb;
    @Getter
    private final BakuTable mSyncbaseTable;
    @Getter
    private final CompositeSubscription mSubscriptions;

    public BakuActivityMixin(final VAndroidContextTrait<T> vAndroidContextTrait) {
        mVAndroidContextTrait = vAndroidContextTrait;

        mSubscriptions = new CompositeSubscription();
        mSyncbase = new BakuSyncbase(this);

        final String app = getSyncbaseAppName(),
                db = getSyncbaseDbName(),
                t = getSyncbaseTableName();
        log.info("Mapping Syncbase path: {}/{}/{}", app, db, t);
        mSyncbaseDb = mSyncbase.rxApp(app).rxDb(db);
        mSyncbaseTable = mSyncbaseDb.rxTable(t);

        joinInitialSyncGroup();
    }

    /**
     * Convenience constructor for compositional integration. Example usage:
     *
     * ```java
     * public class SampleCompositionActivity extends Activity {
     *     private BakuActivityTrait<SampleCompositionActivity> mBaku;
     *
     *     {@literal @}Override
     *     protected void onCreate(final Bundle savedInstanceState) {
     *         super.onCreate(savedInstanceState);
     *         setContentView(R.layout.activity_hello);
     *
     *         mBaku = new BakuActivityMixin<>(this, savedInstanceState);
     *     }
     *
     *     {@literal @}Override
     *     protected void onDestroy() {
     *         mBaku.close();
     *         super.onDestroy();
     *     }
     * }
     * ```
     */
    public BakuActivityMixin(final T context, final Bundle savedInstanceState) {
        this(VAndroidContextMixin.withDefaults(context, savedInstanceState));
    }

    @Override
    public void close() {
        mSubscriptions.unsubscribe();
        mSyncbase.close();
    }

    protected String getSyncbaseAppName() {
        return mVAndroidContextTrait.getAndroidContext().getPackageName();
    }

    protected String getSyncbaseDbName() {
        return "db";
    }

    public String getSyncbaseTableName() {
        return "ui";
    }

    protected void joinInitialSyncGroup() {
        UserCloudSyncgroup.forActivity(this).join();
    }

    public void onSyncError(final Throwable t) {
        ErrorReporters.getDefaultSyncErrorReporter(mVAndroidContextTrait);
    }

    public <U> SyncbaseBinding.Builder<U> binder() {
        return SyncbaseBinding.<U>builder()
                .activity(this);
    }

    public CollectionBinding.Builder collectionBinder() {
        return CollectionBinding.builder()
                .activity(this);
    }
}
