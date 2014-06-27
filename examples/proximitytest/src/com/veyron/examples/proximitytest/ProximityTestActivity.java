package com.veyron.examples.proximitytest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.veryron.examples.proximitytest.R;
import com.veyron2.PauseHandler;
import com.veyron2.ipc.VeyronException;
import com.veyron2.services.proximity.Client;
import com.veyron2.services.proximity.Device;
import com.veyron2.services.proximity.ProximityScanner;
import com.veyron2.services.proximity.scanner.ProximityScannerAndroidService;

/**
 * ProximityTestActivity monitors the list of nearby bluetooth devices and
 * displays various debugging information for each device.
 */
public class ProximityTestActivity extends Activity {
    private static final int REFRESH_PERIOD = 150; // ms

    private ProximityScannerAndroidService serv;
    private PauseHandler handler = new PauseHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxmity_test);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getApplicationContext(), ProximityScannerAndroidService.class);
        startService(intent);
        boolean success = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        if (!success) {
            throw new RuntimeException("Failed to bind to service");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(conn);
        if (isFinishing()) {
            Intent intent = new Intent(getApplicationContext(),
                    ProximityScannerAndroidService.class);
            stopService(intent);
        }
    }

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            ProximityScannerAndroidService.BluetoothTestBinder binder =
            (ProximityScannerAndroidService.BluetoothTestBinder) service;
            serv = binder.getService();
            handler = new PauseHandler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshDevices();
                    handler.postDelayed(this, REFRESH_PERIOD);
                }
            }, REFRESH_PERIOD);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            serv = null;
        }
    };

    public ArrayList<Device> fetchDevices() {
        ProximityScanner ps;
        String endpoint = "/" + serv.endpoint;
        try {
            ps = Client.bindProximityScanner(endpoint);
        } catch (VeyronException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT);
            return new ArrayList<Device>();
        }
        try {
            ArrayList<Device> devices = ps.nearbyDevices(null);
            if (devices == null) {
                // TODO(bprosnitz) Remove this. This is a temporary hack because
                // VDL decodes empty slices into nil.
                devices = new ArrayList<Device>();
            }
            return devices;
        } catch (VeyronException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            ;
            return new ArrayList<Device>();
        }
    }

    public void refreshDevices() {
        ArrayList<Device> devices = fetchDevices();
        // Update the devices view.
        final ListView devicesView = (ListView) findViewById(R.id.devices);
        devicesView.setAdapter(new DeviceAdapter(this, devices));
    }

    private class DeviceAdapter extends BaseAdapter {
        private final Context context;
        private final ArrayList<Device> devices;

        DeviceAdapter(Context context, ArrayList<Device> devices) {
            this.context = context;
            this.devices = devices;
        }

        @Override
        public int getCount() {
            if (devices == null) {
                return 0;
            }
            return devices.size();
        }

        @Override
        public Object getItem(int position) {
            if (devices != null && position >= 0 && position < devices.size()) {
                return devices.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                // If it's not recycled, create a new one.
                textView = new TextView(context);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            } else {
                textView = (TextView) convertView;
            }

            final Device device = (Device) getItem(position);
            if (device != null) {
                String deviceStr = String.format("distance: %s names: %s, mac: %s",
                        device.distance, device.names, device.mAC);
                textView.setText(String.format("distance: %s names: %s, mac: %s", device.distance,
                        device.names, device.mAC));
                if (device.distance != null && Math.abs(Integer.parseInt(device.distance)) < 50) {
                    textView.setTextColor(Color.GREEN);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
            } else {
                textView.setText("UNKNOWN DEVICE");
                textView.setTextColor(Color.RED);
            }
            return textView;
        }
    }
}