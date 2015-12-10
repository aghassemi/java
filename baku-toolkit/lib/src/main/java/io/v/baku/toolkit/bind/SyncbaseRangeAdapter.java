// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.baku.toolkit.bind;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.v.rx.syncbase.RangeWatchBatch;
import io.v.rx.syncbase.RangeWatchEvent;
import io.v.rx.syncbase.RxTable;
import io.v.v23.syncbase.nosql.ChangeType;
import io.v.v23.syncbase.nosql.PrefixRange;
import io.v.v23.syncbase.nosql.RowRange;
import java8.util.function.Function;
import lombok.Getter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

@Accessors(prefix = "m")
public class SyncbaseRangeAdapter<T> extends BaseAdapter implements AutoCloseable {
    private static final String ERR_INCONSISTENT = "Sorted data are inconsistent with map data";

    /**
     * If {@code T} is {@link Comparable}, the default row ordering is natural ordering on row
     * values. Otherwise, the default is natural ordering on row names.
     */
    @Accessors(prefix = "m")
    public static class Builder<T> extends BaseBuilder<Builder<T>> {
        private PrefixRange mPrefix = RowRange.prefix("");
        private Class<T> mType;
        private Ordering<? super RxTable.Row<T>> mOrdering;
        private Func1<String, Boolean> mKeyFilter;
        private ViewAdapter<? super RxTable.Row<T>> mViewAdapter;
        private Context mViewAdapterContext;

        @Getter
        private SyncbaseRangeAdapter<T> mAdapter;

        public Builder<T> prefix(final PrefixRange prefix) {
            mPrefix = prefix;
            return this;
        }

        public Builder<T> prefix(final String prefix) {
            return prefix(RowRange.prefix(prefix));
        }

        /**
         * This setter is minimally typesafe; after setting the {@code type}, clients should
         * probably also update {@code ordering} and {@code viewAdapter}.
         */
        public <U> Builder<U> type(final Class<U> type) {
            @SuppressWarnings("unchecked")
            final Builder<U> casted = (Builder<U>) this;
            casted.mType = type;
            return casted;
        }

        public Builder<T> ordering(final Ordering<? super RxTable.Row<? extends T>> ordering) {
            mOrdering = ordering;
            return this;
        }

        public Builder<T> valueOrdering(final Ordering<? super T> ordering) {
            return ordering(ordering.onResultOf(RxTable.Row::getValue));
        }

        /**
         * For comparable {@code T}, default to natural ordering on values. Otherwise, default to
         * natural ordering on row names.
         */
        private Ordering<? super RxTable.Row<? extends T>> getDefaultOrdering() {
            if (mOrdering == null && Comparable.class.isAssignableFrom(mType)) {
                return Ordering.natural().onResultOf(r -> (Comparable) r.getValue());
            } else {
                return Ordering.natural().onResultOf(RxTable.Row::getRowName);
            }
        }

        public Builder<T> keyFilter(final Func1<String, Boolean> keyFilter) {
            mKeyFilter = keyFilter;
            return this;
        }

        public Builder<T> viewAdapter(final ViewAdapter<? super RxTable.Row<T>> viewAdapter) {
            mViewAdapter = viewAdapter;
            return this;
        }

        public <U> Builder<T> textViewAdapter(final Function<RxTable.Row<T>, U> fn) {
            return viewAdapter(getDefaultViewAdapter(fn));
        }

        public Builder<T> valueAdapter(final ViewAdapter<T> viewAdapter) {
            return viewAdapter(new TransformingViewAdapter<>(viewAdapter, RxTable.Row::getValue));
        }

        private <U> ViewAdapter<? super RxTable.Row<T>> getDefaultViewAdapter(
                final Function<RxTable.Row<T>, U> fn) {
            return new TextViewAdapter<U>(getDefaultViewAdapterContext()).map(fn);
        }

        /**
         * The default view adapter stringizes values.
         */
        private ViewAdapter<? super RxTable.Row<T>> getDefaultViewAdapter() {
            return getDefaultViewAdapter(RxTable.Row::getValue);
        }

        public Builder<T> viewAdapterContext(final Context context) {
            mViewAdapterContext = context;
            return this;
        }

        public Context getDefaultViewAdapterContext() {
            return mViewAdapterContext == null ? mActivity : mViewAdapterContext;
        }

        public Builder<T> bindTo(final ListView listView) {
            if (mType == null) {
                throw new IllegalStateException("Missing required type property");
            }

            final Ordering<? super RxTable.Row<T>> ordering = mOrdering == null ?
                    getDefaultOrdering() : mOrdering;
            final ViewAdapter<? super RxTable.Row<T>> viewAdapter = mViewAdapter == null ?
                    getDefaultViewAdapter() : mViewAdapter;

            final Observable<RangeWatchBatch<T>> watch =
                    mRxTable.watch(mPrefix, mKeyFilter, mType);

            mAdapter = new SyncbaseRangeAdapter<>(watch, ordering, viewAdapter, mOnError);
            subscribe(mAdapter.getSubscription());
            listView.setAdapter(mAdapter);
            return this;
        }

        @Override
        public Builder<T> bindTo(final View view) {
            if (view instanceof ListView) {
                return bindTo((ListView) view);
            } else {
                throw new IllegalArgumentException("No default binding for view " + view);
            }
        }
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    private final Map<String, T> mRows = new HashMap<>();
    private List<RxTable.Row<T>> mSorted = new ArrayList<>();
    private final Ordering<? super RxTable.Row<T>> mOrdering;
    private final ViewAdapter<? super RxTable.Row<T>> mViewAdapter;
    private final Action1<Throwable> mOnError;
    @Getter
    private final Subscription mSubscription;

    public SyncbaseRangeAdapter(final Observable<RangeWatchBatch<T>> watch,
                                final Ordering<? super RxTable.Row<T>> ordering,
                                final ViewAdapter<? super RxTable.Row<T>> viewAdapter,
                                final Action1<Throwable> onError) {
        // ensure deterministic ordering by always applying secondary order on row name
        mOrdering = ordering.compound(Ordering.natural().onResultOf(RxTable.Row::getRowName));
        mViewAdapter = viewAdapter;
        mOnError = onError;

        mSubscription = subscribeTo(watch);
    }

    @Override
    public void close() {
        mSubscription.unsubscribe();
    }

    private Subscription subscribeTo(final Observable<RangeWatchBatch<T>> watch) {
        return watch
                .concatMap(RangeWatchBatch::collectChanges)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(events -> {
                            // TODO(rosswang): more efficient updates for larger batches
                            for (final RangeWatchEvent<T> e : events) {
                                if (e.getChangeType() == ChangeType.DELETE_CHANGE) {
                                    removeOne(e.getRow());
                                } else {
                                    updateOne(e.getRow());
                                }
                            }
                            notifyDataSetChanged();
                        },
                        mOnError
                );
    }

    private int findRowForEdit(final String rowName, final T oldValue) {
        final int oldIndex = Collections.binarySearch(mSorted,
                new RxTable.Row<>(rowName, oldValue), mOrdering);
        if (oldIndex < 0) {
            throw new ConcurrentModificationException(ERR_INCONSISTENT);
        } else {
            return oldIndex;
        }
    }

    private void removeOne(final RxTable.Row<T> entry) {
        final T old = mRows.remove(entry.getRowName());
        if (old != null) {
            mSorted.remove(findRowForEdit(entry.getRowName(), old));
        }
    }

    private int insertionIndex(final RxTable.Row<T> entry) {
        final int bs = Collections.binarySearch(mSorted, entry, mOrdering);
        return bs < 0 ? ~bs : bs;
    }

    private void updateOne(final RxTable.Row<T> entry) {
        final T old = mRows.put(entry.getRowName(), entry.getValue());
        if (old == null) {
            mSorted.add(insertionIndex(entry), entry);
        } else {
            final int oldIndex = findRowForEdit(entry.getRowName(), old);
            int newIndex = insertionIndex(entry);

            if (newIndex >= oldIndex) {
                newIndex--;
                for (int i = oldIndex; i < newIndex; i++) {
                    mSorted.set(i, mSorted.get(i + 1));
                }
            } else {
                for (int i = oldIndex; i > newIndex; i--) {
                    mSorted.set(i, mSorted.get(i - 1));
                }
            }
            mSorted.set(newIndex, entry);
        }
    }

    @Override
    public int getCount() {
        return mRows.size();
    }

    @Override
    public T getItem(int position) {
        return mSorted.get(position).getValue();
    }

    /**
     * @return a dummy row ID for the item at the requested position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RxTable.Row<T> entry = mSorted.get(position);
        return mViewAdapter.getView(position, entry, convertView, parent);
    }

    public String getRowName(final int position) {
        return mSorted.get(position).getRowName();
    }

    public int getRowIndex(final String rowName) {
        return Collections.binarySearch(mSorted, new RxTable.Row<>(rowName, mRows.get(rowName)),
                mOrdering);
    }
}
