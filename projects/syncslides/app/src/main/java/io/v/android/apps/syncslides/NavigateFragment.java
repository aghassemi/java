// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.android.apps.syncslides;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Provides both the presenter and audience views for navigating through a presentation.
 * Instantiated by the PresentationActivity along with other views/fragments of the presentation
 * to make transitions between them seamless.
 */
public class NavigateFragment extends Fragment {

    private static final String TAG = "NavigateFragment";
    private static final String DECK_ID_KEY = "deck_id_key";
    private static final String SLIDE_NUM_KEY = "slide_num_key";
    private static final String ROLE_KEY = "role_key";

    private String mDeckId;
    private int mSlideNum;
    private ImageView mPrevThumb;
    private ImageView mNextThumb;
    private ImageView mCurrentSlide;
    private EditText mNotes;
    private DB.Slide[] mSlides;
    private Role mRole;
    private boolean mEditing;

    public enum Role {
        PRESENTER, AUDIENCE
    }

    public static NavigateFragment newInstance(String deckId, int slideNum, Role role) {
        NavigateFragment fragment = new NavigateFragment();
        Bundle args = new Bundle();
        args.putString(DECK_ID_KEY, deckId);
        args.putInt(SLIDE_NUM_KEY, slideNum);
        args.putSerializable(ROLE_KEY, role);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args;
        if (savedInstanceState != null) {
            Log.i(TAG, "restoring from savedInstanceState");
            args = savedInstanceState;
        } else {
            args = getArguments();
        }
        mDeckId = args.getString(DECK_ID_KEY);
        mSlideNum = args.getInt(SLIDE_NUM_KEY);
        mRole = (Role) args.get(ROLE_KEY);

        View rootView = inflater.inflate(R.layout.fragment_navigate, container, false);

        View.OnClickListener previousSlideListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousSlide();
            }
        };
        View arrowBack = rootView.findViewById(R.id.arrow_back);
        arrowBack.setOnClickListener(previousSlideListener);
        mPrevThumb = (ImageView) rootView.findViewById(R.id.prev_thumb);
        mPrevThumb.setOnClickListener(previousSlideListener);

        View.OnClickListener nextSlideListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSlide();
            }
        };
        // Show either the arrowForward or the FAB but not both.
        View arrowForward = rootView.findViewById(R.id.arrow_forward);
        View fabForward = rootView.findViewById(R.id.primary_navigation_fab);
        if (mRole == Role.PRESENTER) {
            arrowForward.setVisibility(View.INVISIBLE);
            fabForward.setOnClickListener(nextSlideListener);
        } else {
            fabForward.setVisibility(View.INVISIBLE);
            arrowForward.setOnClickListener(nextSlideListener);
        }
        mNextThumb = (ImageView) rootView.findViewById(R.id.next_thumb);
        mNextThumb.setOnClickListener(nextSlideListener);
        mCurrentSlide = (ImageView) rootView.findViewById(R.id.slide_current_medium);
        mCurrentSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullscreenSlide();
            }
        });

        mNotes = (EditText) rootView.findViewById(R.id.notes);
        mNotes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mEditing = hasFocus;
                getActivity().invalidateOptionsMenu();
            }
        });
        // The parent of mNotes needs to be focusable in order to clear focus
        // from mNotes when done editing.  We set the attributes in code rather
        // than in XML because it is too easy to add an extra level of layout
        // in XML and forget to add these attributes.
        ViewGroup parent = (ViewGroup) mNotes.getParent();
        parent.setFocusable(true);
        parent.setClickable(true);
        parent.setFocusableInTouchMode(true);

        View slideListIcon = rootView.findViewById(R.id.slide_list);
        slideListIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        DB db = DB.Singleton.get(getActivity().getApplicationContext());
        db.getSlides(mDeckId, new DB.SlidesCallback() {
            @Override
            public void done(DB.Slide[] slides) {
                mSlides = slides;
                updateView();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((PresentationActivity) getActivity()).setUiImmersive(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((PresentationActivity) getActivity()).setUiImmersive(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DECK_ID_KEY, mDeckId);
        outState.putInt(SLIDE_NUM_KEY, mSlideNum);
        outState.putSerializable(ROLE_KEY, mRole);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mEditing) {
            inflater.inflate(R.menu.edit_notes, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                Toast.makeText(getContext(), "Saving notes", Toast.LENGTH_SHORT).show();
                mNotes.clearFocus();
                InputMethodManager inputManager =
                        (InputMethodManager) getContext().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                ((PresentationActivity) getActivity()).setUiImmersive(true);
                return true;
        }
        return false;
    }


    private void fullscreenSlide() {
        // TODO(afergan): Transition to the fullscreen fragment.
        Toast.makeText(getContext(), "Going fullscreen", Toast.LENGTH_SHORT).show();
    }

    /**
     * Advances to the next slide, if there is one, and updates the UI.
     */
    private void nextSlide() {
        if (mSlides == null) {
            // Wait until the slides have loaded before letting the user move around.
            return;
        }
        if (mSlideNum < mSlides.length - 1) {
            mSlideNum++;
            updateView();
        }
    }

    /**
     * Goes back to the previous slide, if there is one, and updates the UI.
     */
    private void previousSlide() {
        if (mSlides == null) {
            // Wait until the slides have loaded before letting the user move around.
            return;
        }
        if (mSlideNum > 0) {
            mSlideNum--;
            updateView();
        }
    }

    private void updateView() {
        if (mSlides == null) {
            // We can't do anything until the slides have loaded.
            return;
        }
        if (mSlideNum > 0) {
            mPrevThumb.setImageBitmap(mSlides[mSlideNum - 1].getImage());
        } else {
            mPrevThumb.setImageDrawable(null);
        }
        mCurrentSlide.setImageBitmap(mSlides[mSlideNum].getImage());
        if (mSlideNum == mSlides.length - 1) {
            mNextThumb.setImageDrawable(null);
        } else {
            mNextThumb.setImageBitmap(mSlides[mSlideNum + 1].getImage());
        }
        if (!mSlides[mSlideNum].getNotes().equals("")) {
            mNotes.setText(mSlides[mSlideNum].getNotes());
        } else {
            mNotes.getText().clear();
        }
    }
}
