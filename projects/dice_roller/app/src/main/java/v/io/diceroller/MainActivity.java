// Copyright 2016 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package v.io.diceroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;
import java.util.Random;

import io.v.syncbase.Collection;
import io.v.syncbase.Database;
import io.v.syncbase.Syncbase;
import io.v.syncbase.WatchChange;
import io.v.syncbase.android.SyncbaseAndroid;
import io.v.syncbase.exception.SyncbaseException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DiceRoller";
    private static final String RESULT_KEY = "result";

    // Note: You can replace CLOUD_NAME and CLOUD_ADMIN with your cloud syncbase
    // name and blessing from https://sb-allocator.v.io
    private static final String CLOUD_NAME =
            "/(dev.v.io:r:vprod:service:mounttabled)@ns.dev.v.io:8101/sb/syncbased-24204641";
    private static final String CLOUD_ADMIN = "dev.v.io:r:allocator:us:x:syncbased-24204641";
    private static final String MOUNT_POINT = "/ns.dev.v.io:8101/tmp/diceroller/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Syncbase.Options options = Syncbase.Options.cloudBuilder(
                    SyncbaseAndroid.defaultRootDir(this), CLOUD_NAME, CLOUD_ADMIN)
                    .setMountPoint(MOUNT_POINT)
                    .build();
            Syncbase.init(options);
        } catch (SyncbaseException e) {
            Log.e(TAG, "Syncbase failed to initialize", e);
        }

        SyncbaseAndroid.login(this, new LoginCallback());
    }

    @Override
    protected void onDestroy() {
        Syncbase.shutdown();
        super.onDestroy();
    }

    private class LoginCallback implements Syncbase.LoginCallback {
        @Override
        public void onSuccess() {
            Log.i(TAG, "Syncbase is ready");

            try {
                final Collection userdata = Syncbase.database().getUserdataCollection();

                // On dice roll, put a random number into the userdata collection under RESULT_KEY.
                final View button = findViewById(R.id.buttonRoll);
                if (button == null) {
                    Log.e(TAG, "Resource not found: " + R.id.buttonRoll);
                    return;
                }
                button.setEnabled(true);
                button.setOnClickListener(new View.OnClickListener() {
                    private Random random = new Random();

                    @Override
                    public void onClick(View v) {
                        int randomNumber = random.nextInt(6) + 1;
                        try {
                            userdata.put(RESULT_KEY, randomNumber);
                        } catch (SyncbaseException e) {
                            Log.e(TAG, "put error", e);
                        }
                    }
                });

                Syncbase.database().addWatchChangeHandler(new Database.WatchChangeHandler() {
                    @Override
                    public void onInitialState(Iterator<WatchChange> values) {
                        onChange(values);
                    }

                    @Override
                    public void onChangeBatch(Iterator<WatchChange> changes) {
                        onChange(changes);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "watch error", e);
                    }

                    private void onChange(Iterator<WatchChange> changes) {
                        while (changes.hasNext()) {
                            WatchChange watchChange = changes.next();
                            Log.i(TAG, "Received watch change: " + watchChange);
                            if (watchChange.getCollectionId().getName().equals(
                                    Syncbase.USERDATA_NAME) &&
                                    watchChange.getEntityType() == WatchChange.EntityType.ROW &&
                                    watchChange.getChangeType() == WatchChange.ChangeType.PUT &&
                                    watchChange.getRowKey().equals(RESULT_KEY)) {
                                try {
                                    updateResult(watchChange.getValue(Integer.class));
                                } catch (SyncbaseException e) {
                                    Log.e(TAG, "watch change error", e);
                                }
                            }
                        }
                    }
                });
            } catch (SyncbaseException e) {
                Log.e(TAG, "Syncbased failed to login", e);
            }
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "LoginCallback: onError", e);
        }
    }

    private void updateResult(final int newValue) {
        Log.i(TAG, "newValue: " + newValue);
        final TextView result = (TextView) findViewById(R.id.textViewResult);
        result.setText(String.valueOf(newValue));
    }
}
