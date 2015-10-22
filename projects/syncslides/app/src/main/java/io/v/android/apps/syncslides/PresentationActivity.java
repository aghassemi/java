// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.android.apps.syncslides;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import io.v.android.apps.syncslides.db.DB;
import io.v.android.apps.syncslides.discovery.ParticipantPeer;
import io.v.android.apps.syncslides.misc.Config;
import io.v.android.apps.syncslides.misc.V23Manager;
import io.v.android.apps.syncslides.model.Deck;
import io.v.android.apps.syncslides.model.DeckFactory;
import io.v.android.apps.syncslides.model.Participant;
import io.v.android.apps.syncslides.model.Role;

public class PresentationActivity extends AppCompatActivity {
    private static final String TAG = "PresentationActivity";

    /**
     * The deck to present.
     */
    private Deck mDeck;
    /**
     * The current role of the user.  This value can change during the lifetime
     * of the activity.
     */
    private Role mRole;
    // TODO(kash): Replace this with the presentation id.
    private String mPresentationId = "randomPresentationId1";
    private boolean mSynced;

    /**
     * Once a user clicks 'present' - which happens at some unpredictable time
     * after onStart, the user begins presenting the deck, and the system must
     * advertise the presentation.   Once advertising is started, it doesn't
     * stop until onStop is called.  If the activity is paused, advertising
     * should continue.
     */
    private boolean mShouldBeAdvertising;
    private boolean mIsAdvertising;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        // Initialize the DeckFactory.
        DeckFactory.Singleton.get(getApplicationContext());
        // Immediately initialize V23, possibly sending user to the
        // AccountManager to get blessings.
        V23Manager.Singleton.get().init(getApplicationContext(), this);
        DB.Singleton.get(getApplicationContext()).init(this);
        setContentView(R.layout.activity_presentation);

        mShouldBeAdvertising = false;
        mIsAdvertising = false;
        String deckId;
        if (savedInstanceState == null) {
            Log.d(TAG, "savedInstanceState is null");
            deckId = getIntent().getStringExtra(Deck.B.DECK_ID);
            mRole = (Role) getIntent().getSerializableExtra(
                    Participant.B.PARTICIPANT_ROLE);
            mSynced = true;
        } else {
            Log.d(TAG, "savedInstanceState is NOT null");
            mRole = (Role) savedInstanceState.get(Participant.B.PARTICIPANT_ROLE);
            deckId = savedInstanceState.getString(Deck.B.DECK_ID);
            mSynced = savedInstanceState.getBoolean(Participant.B.PARTICIPANT_SYNCED);
            mShouldBeAdvertising = savedInstanceState.getBoolean(Participant.B.PARTICIPANT_SHOULD_ADV);
            if (mShouldBeAdvertising) {
                Log.d(TAG, "Need to restore advertising");
            }
        }

        Log.d(TAG, "Role = " + mRole);
        mDeck = DB.Singleton.get(getApplicationContext()).getDeck(deckId);
        if (mDeck == null) {
            throw new IllegalArgumentException("Unusable deckId: "+ deckId);
        }
        Log.d(TAG, "Using deck: " + mDeck);

        // TODO(jregan): This appears to be an attempt to avoid fragment
        // re-inflation, possibly the right thing to do is move the code
        // below to another flow step, e.g. onRestoreInstanceState.
        if (savedInstanceState != null) {
            return;
        }

        if (mShouldBeAdvertising) {
            startAdvertising();
        }

        getSupportActionBar().setTitle(mDeck.getTitle());

        // If this is an audience member, we want them to jump straight to the fullscreen view.
        if (mRole == Role.AUDIENCE) {
            showFullscreenSlide(0);
        } else {
            showSlideList();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult");
        if (V23Manager.onActivityResult(
                getApplicationContext(), requestCode, resultCode, data)) {
            Log.d(TAG, "did the v23 result");
            return;
        }
        // Any other activity results would be handled here.
    }

    @Override
    protected void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        Log.d(TAG, "onSaveInstanceState");
        b.putSerializable(Participant.B.PARTICIPANT_ROLE, mRole);
        b.putString(Deck.B.DECK_ID, mDeck.getId());
        b.putBoolean(Participant.B.PARTICIPANT_SYNCED, mSynced);
        b.putBoolean(Participant.B.PARTICIPANT_SHOULD_ADV, mShouldBeAdvertising);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        if (mShouldBeAdvertising) {
            startAdvertising();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        stopAdvertising();
    }

    /**
     * Set the system UI to be immersive or not.
     */
    public void setUiImmersive(boolean immersive) {
        if (immersive) {
            getSupportActionBar().hide();
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            getSupportActionBar().show();
            // See the comment at the top of fragment_slide_list.xml for why we don't simply
            // use View.SYSTEM_UI_FLAG_VISIBLE.
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private boolean shouldUseV23() {
        return Config.MtDiscovery.ENABLE && V23Manager.Singleton.get().isBlessed();
    }

    private void startAdvertising() {
        Log.d(TAG, "startAdvertising");
        mShouldBeAdvertising = true;
        if (mIsAdvertising) {
            Log.d(TAG, "Already advertising.");
            return;
        }
        if (shouldUseV23()) {
            V23Manager.Singleton.get().mount(
                    Config.MtDiscovery.makeMountName(mDeck),
                    new ParticipantPeer.Server(mDeck));
            Log.d(TAG, "MT advertising started.");
        } else {
            Log.d(TAG, "No means to start advertising.");
        }
        mIsAdvertising = true;
    }

    private void stopAdvertising() {
        Log.d(TAG, "stopAdvertising");
        if (!mIsAdvertising) {
            Log.d(TAG, "Not advertising.");
            return;
        }
        if (shouldUseV23()) {
            // At the moment, only one service can be mounted, and this call
            // will unmount it if mounted, else do nothing.
            V23Manager.Singleton.get().unMount();
            Log.d(TAG, "MT advertising stopped.");
        } else {
            Log.d(TAG, "No advertising to stop.");
        }
        mIsAdvertising = false;
    }

    /**
     * Starts a live presentation.  The presentation will be advertised to other
     * devices as long as this activity is alive.
     */
    public void startPresentation() {
        DB db = DB.Singleton.get(getApplicationContext());
        db.createPresentation(mDeck.getId(), new DB.Callback<DB.CreatePresentationResult>() {
            @Override
            public void done(DB.CreatePresentationResult startPresentationResult) {
                Log.i(TAG, "Started presentation");
                Toast.makeText(getApplicationContext(), "Started presentation",
                        Toast.LENGTH_SHORT).show();
                startAdvertising();
            }
        });
        mRole = Role.PRESENTER;
        showNavigateFragment(0);
    }

    /**
     * Switches to the fullscreen immersive slide presentation view.
     *
     * @param slideNum the number of the slide to show fullscreen
     */
    public void showFullscreenSlide(int slideNum) {
        FullscreenSlideFragment fragment =
                FullscreenSlideFragment.newInstance(
                        mDeck.getId(), mPresentationId, slideNum, mRole);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    /**
     * Shows the navigate fragment where the user can see the current slide and
     * navigate to other components of the slide presentation.
     *
     * @param slideNum the number of the current slide to show in the fragment
     */
    public void showNavigateFragment(int slideNum) {
        Log.d(TAG, String.valueOf(mSynced));
        NavigateFragment fragment = NavigateFragment.newInstance(
                mDeck.getId(), mPresentationId, slideNum, mRole);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    /**
     * Shows the navigate fragment where the user can see the current slide and
     * navigate to other components of the slide presentation. This version
     * includes an add to the back stack so that the user can back out from the
     * navigate fragment to slide list.
     *
     * @param slideNum the number of the current slide to show in the fragment
     */
    public void showNavigateFragmentWithBackStack(int slideNum) {
        NavigateFragment fragment = NavigateFragment.newInstance(
                mDeck.getId(), mPresentationId, slideNum, mRole);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, fragment)
                .addToBackStack("")
                .commit();
    }

    /**
     * Shows the slide list, where users can see the slides in a presentation
     * and click on one to browse the deck, or press the play FAB to start
     * presenting.
     */
    public void showSlideList() {
        SlideListFragment fragment = SlideListFragment.newInstance(mDeck.getId(), mRole);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    /**
     * Return if the device is synced with the presenter (true if the device is
     * the presenter).
     */
    public boolean getSynced() {
        return mSynced;
    }

    /**
     * Set the device to sync with the presenter.
     */
    public void setSynced() {
        mSynced = true;
    }

    /**
     * Unsync the current device with the presenter.
     */
    public void setUnsynced() {
        mSynced = false;
    }

}
