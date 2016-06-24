// Copyright 2016 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.android.impl.google.rpc.protocols.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.google.common.base.Splitter;

import org.joda.time.Duration;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import io.v.android.v23.V;
import io.v.v23.context.VContext;

/**
 * Handles bluetooth connection establishment on Android.
 * <p>
 * Used as a helper class for native code which sets up and registers the bluetooth protocol with
 * the vanadium RPC service.
 */
public class BluetoothWithSdp {
    private static final String TAG = "Bluetooth";

    private static final String SDP_NAME = "v23";
    // Generated by UUID5(UUID5(NULL, "v.io"), "_bluetooth_socket_port")
    private static final UUID BASE_SDP_UUID =
            UUID.fromString("0f83a207-7f39-57c4-92f6-bd46039a5540");

    private static final int MAX_PORT = 30;
    private static final int PORT_MASK = 0x31;

    private static final List<Integer> sPorts;

    static {
        sPorts = new ArrayList<>();
        for (int i = 1; i <= MAX_PORT; i++) {
            sPorts.add(i);
        }
        // Shuffle port numbers to prevent peers from using cached SDP records.
        Collections.shuffle(sPorts, new Random(System.currentTimeMillis()));
    }

    private static synchronized int getServerPort(int port) throws IOException {
        if (port == 0) {
            if (sPorts.isEmpty()) {
                throw new IOException("No more ports available");
            }
            port = sPorts.get(0);
        }
        if (!sPorts.remove(new Integer(port))) {
            throw new IOException(String.format("Port %d not available", port));
        }
        return port;
    }

    private static synchronized void putServerPort(int port) {
        if (port > 0 && port <= MAX_PORT && !sPorts.contains(port)) {
            sPorts.add(port);
        }
    }

    private static UUID getSdpUuidFromPort(int port) {
        if (port <= 0 || port > MAX_PORT) {
            throw new IllegalArgumentException(String.format("Illegal port number %d", port));
        }
        return new UUID(
                BASE_SDP_UUID.getMostSignificantBits(),
                BASE_SDP_UUID.getLeastSignificantBits() | (long) port);
    }

    private static String getLocalMacAddress(VContext ctx) {
        // TODO(suharshs): Android has disallowed getting the local address.
        // This is a remaining working hack that gets the local bluetooth address,
        // just to get things working.
        return android.provider.Settings.Secure.getString(
                V.getAndroidContext(ctx).getContentResolver(), "bluetooth_address");
    }

    private static String getMacAddress(VContext ctx, String address) {
        List<String> parts = Splitter.on("/").omitEmptyStrings().splitToList(address);
        switch (parts.size()) {
            case 0:
                throw new IllegalArgumentException(
                        String.format(
                                "Couldn't split bluetooth address \"%s\" using \"/\" separator: "
                                        + "got zero parts!",
                                address));
            case 1:
                return getLocalMacAddress(ctx);
            case 2:
                String macAddress = parts.get(0).toUpperCase();
                if (!BluetoothAdapter.checkBluetoothAddress(macAddress)) {
                    throw new IllegalArgumentException("Invalid bluetooth address: " + address);
                }
                return macAddress;
            default:
                throw new IllegalArgumentException(
                        String.format(
                                "Couldn't parse bluetooth address \"%s\": too many \"/\".",
                                address));
        }
    }

    private static int getPortNumber(String address) {
        List<String> parts = Splitter.on("/").splitToList(address);
        switch (parts.size()) {
            case 0:
                throw new IllegalArgumentException(
                        String.format(
                                "Couldn't split bluetooth address \"%s\" using \"/\" separator: "
                                        + "got zero parts!",
                                address));
            case 1:
            case 2:
                int port = Integer.parseInt((parts.get(parts.size() - 1)));
                if (port < 0 || port > MAX_PORT) {
                    throw new IllegalArgumentException(
                            String.format(
                                    "Illegal port number %q in bluetooth " + "address \"%s\".",
                                    port, address));
                }
                return port;
            default:
                throw new IllegalArgumentException(
                        String.format(
                                "Couldn't parse bluetooth address \"%s\": too many \"/\".",
                                address));
        }
    }

    static Listener listen(VContext ctx, String address) throws Exception {
        String macAddress = getMacAddress(ctx, address);
        int port = getPortNumber(address);
        return new Listener(macAddress, port);
    }

    static Stream dial(VContext ctx, String address, Duration timeout) throws Exception {
        String macAddress = getMacAddress(ctx, address);
        int port = getPortNumber(address);

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            throw new IOException("BluetoothAdapter not available");
        }
        BluetoothDevice device = adapter.getRemoteDevice(macAddress);

        UUID uuid = getSdpUuidFromPort(port);
        final BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord(uuid);

        Timer timer = null;
        if (timeout.getMillis() != 0) {
            timer = new Timer();
            timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                socket.close();
                            } catch (IOException e) {
                            }
                        }
                    },
                    timeout.getMillis());
        }

        try {
            socket.connect();
        } catch (IOException e) {
            socket.close();
            throw e;
        } finally {
            if (timer != null) {
                timer.cancel();
            }
        }

        // There is no way currently to retrieve the local port number for the
        // connection, but that's probably OK.
        String localAddress = String.format("%s/0", getLocalMacAddress(ctx));
        String remoteAddress = String.format("%s/%d", macAddress, port);
        return new Stream(socket, localAddress, remoteAddress);
    }

    // Listener provides methods for accepting new Bluetooth connections.
    public static class Listener {
        private final String mLocalAddress;

        private int mPort;
        private BluetoothServerSocket mServerSocket;

        private Listener(String macAddress, int port) throws IOException {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            if (adapter == null) {
                throw new IOException("BluetoothAdapter not available");
            }

            mPort = getServerPort(port);
            mLocalAddress = String.format("%s/%d", macAddress, mPort);

            Log.d(TAG, String.format("listening on port %d", mPort));

            try {
                UUID uuid = getSdpUuidFromPort(mPort);
                mServerSocket = adapter.listenUsingInsecureRfcommWithServiceRecord(SDP_NAME, uuid);
            } catch (IOException e) {
                close();
                throw e;
            }
        }

        public Stream accept() throws IOException {
            //  Android developer guide says that unlike TCP/IP, RFCOMM only allows one connected client per
            //  channel at a time: https://developer.android.com/guide/topics/connectivity/bluetooth.html.
            //
            //  TODO(jhahn,suharshs): Is this true?
            try {
                BluetoothSocket socket = mServerSocket.accept();
                // There is no way currently to retrieve the remote end's channel number,
                // but that's probably OK.
                String remoteAddress = String.format("%s/0", socket.getRemoteDevice().getAddress());
                return new Stream(socket, mLocalAddress, remoteAddress);
            } catch (IOException e) {
                close();
                throw e;
            }
        }

        public synchronized void close() throws IOException {
            if (mPort > 0) {
                putServerPort(mPort);
                mPort = 0;
            }
            if (mServerSocket != null) {
                mServerSocket.close();
                mServerSocket = null;
            }
        }

        public String address() {
            return mLocalAddress;
        }

        protected void finalize() {
            try {
                close();
            } catch (IOException e) {
            }
        }
    }

    // Stream provides I/O primitives to read and write over a Bluetooth socket.
    public static class Stream {
        private final BluetoothSocket mSocket;
        private final String mLocalAddress;
        private final String mRemoteAddress;

        private Stream(BluetoothSocket socket, String localAddress, String remoteAddress) {
            mSocket = socket;
            mLocalAddress = localAddress;
            mRemoteAddress = remoteAddress;
        }

        public byte[] read(int n) throws IOException {
            try {
                InputStream in = mSocket.getInputStream();
                byte[] buf = new byte[n];
                int total = 0;
                while (total < n) {
                    int r = in.read(buf, total, n - total);
                    if (r < 0) {
                        break;
                    }
                    total += r;
                }
                return total == n ? buf : Arrays.copyOf(buf, total);
            } catch (IOException e) {
                close();
                throw e;
            }
        }

        public void write(byte[] data) throws IOException {
            try {
                // TODO(jhahn): Do we need to flush for every write?
                OutputStream out = mSocket.getOutputStream();
                out.write(data);
            } catch (IOException e) {
                close();
                throw e;
            }
        }

        public void close() throws IOException {
            mSocket.close();
        }

        public String localAddress() {
            return mLocalAddress;
        }

        public String remoteAddress() {
            return mRemoteAddress;
        }
    }
}