// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.rx;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.v.v23.rpc.PublisherEntry;
import io.v.v23.rpc.PublisherEntryKey;
import io.v.v23.rpc.PublisherEntryValue;
import io.v.v23.rpc.Server;
import io.v.v23.verror.VException;
import java8.util.Maps;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import rx.Observable;

@UtilityClass
public class RxNamespace {
    @RequiredArgsConstructor
    @AllArgsConstructor
    private static class MountRecord {
        /*
        TODO(rosswang): https://github.com/vanadium/issues/issues/826
        Right now, mount status defaults timestamps for events that have never happened to be some
        time in 1754. As a hack, initialize these to epoch/1970 (or any time after the default).
         */
        public DateTime lastMount = new DateTime(0),
                lastUnmount = new DateTime(0);

        public static MountRecord fromStatus(final PublisherEntry status) {
            return new MountRecord(status.getLastMount(), status.getLastUnmount());
        }
    }

    private static Observable<MountEvent> processStatus(
            final Map<PublisherEntryKey, MountRecord> mountRecords,
            final PublisherEntryKey k, final PublisherEntryValue v) {

        final List<MountEvent> newEvents = new ArrayList<>(2);
        synchronized (mountRecords) {
            final MountRecord record = Maps.computeIfAbsent(mountRecords, k,
                    kk -> new MountRecord());
            if (v.getLastMount() != null && v.getLastMount().isAfter(record.lastMount)) {
                newEvents.add(MountEvent.forStatus(true, k.getName(), k.getServer(),
                        v.getLastMount(), v.getLastMountError()));
            }
            if (v.getLastUnmount() != null && v.getLastUnmount().isAfter(record.lastUnmount)) {
                newEvents.add(MountEvent.forStatus(false, k.getName(), k.getServer(),
                        v.getLastUnmount(), v.getLastUnmountError()));
            }
            record.lastMount = v.getLastMount();
            record.lastUnmount = v.getLastUnmount();
        }
        Collections.sort(newEvents, MountEvent::compareByTimestamp);
        return Observable.from(newEvents);
    }

    private static boolean isAlreadyMounted(final PublisherEntry status) {
        //There are also ambiguous cases; err on the side of false.
        return status.getLastMount().isAfter(status.getLastUnmount()) &&
                status.getLastMountError() == null;
    }

    /**
     * TODO(rosswang): This does not work properly if the name is relative and the namespace root is
     * changed during the life of the server. https://github.com/vanadium/issues/issues/1052
     *
     * @return an {@code Observable} of {@code MountEvent}s. Events including servers come from
     * polling and may or may not include an error. Events without servers are from
     * {@link Server#addName(String)} and may or may not include an error. If a server is already
     * mounted, a backdated mount event is included. The observable completes if and when the server
     * is unmounted.
     */
    public static Observable<MountEvent> mount(final Observable<Server> rxServer,
                                                          final String name) {
        return Observable.switchOnNext(
                rxServer.map(server -> {
                    /*
                    A note on thread safety. The initial status scan occurs on one thread, before
                    any concurrency contention. Subsequent modifications happen on the Rx io
                    scheduler, which by may be on different threads for each poll. Thus, not only
                    does the map need to be thread-safe, but also each mount record. The simplest
                    way to ensure this is just to lock map get/create and record access on a mutex,
                    which might as well be the map itself. This does perform some unnecessary
                    synchronization, but it all occurs on a worker thread on a low-fidelity loop, so
                    it's not worth a more sophisticated lock.

                    A fully correct minimal lock would include a thread-safe map and a read/write
                    lock for each record.
                     */
                    final Map<PublisherEntryKey, MountRecord> mountRecords = new HashMap<>();
                    final PublisherEntry[] mounts = server.getStatus().getPublisherStatus();
                    final List<MountEvent> alreadyMounted = new ArrayList<>(mounts.length);
                    for (final PublisherEntry status : mounts) {
                        if (status.getName().equals(name)) {
                            mountRecords.put(PublisherEntryKey.fromPublisherEntry(status),
                                    MountRecord.fromStatus(status));
                            if (isAlreadyMounted(status)) {
                                alreadyMounted.add(MountEvent.forStatus(true, status.getName(),
                                        status.getServer(), status.getLastMount(),
                                        status.getLastMountError() /* null */));
                            }
                        }
                    }
                    if (alreadyMounted.isEmpty()) {
                        try {
                            server.addName(name);
                        } catch (final VException e) {
                            return Observable.just(MountEvent.forAddNameFailure(name, e));
                        }
                        final MountEvent initial = MountEvent.forAddNameSuccess(name);

                        return RxPublisherState.index(
                                RxPublisherState.poll(server).map(state -> state.filter(
                                        status -> status.getName().equals(name))))
                                .flatMapIterable(Map::entrySet)
                                .concatMap(e -> processStatus(mountRecords,
                                        e.getKey(), e.getValue()))
                                .startWith(initial);
                    } else {
                        return Observable.from(alreadyMounted);
                    }
                }))
                .takeWhile(MountEvent::isMount);
    }
}
