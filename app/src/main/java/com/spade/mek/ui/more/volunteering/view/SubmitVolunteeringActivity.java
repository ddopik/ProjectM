package com.spade.mek.ui.more.volunteering.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.base.BaseActivity;

/**
 * Created by Ayman Abouzeid on 7/25/17.
 */

public class SubmitVolunteeringActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getString(R.string.volunteering));



        Tracker tracker = MekApplication.getDefaultTracker();
        tracker.setScreenName(getString(R.string.volunteering));
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
        addFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void addFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(SubmitVolunteerFragment.EXTRA_EVENT_ID, getIntent().getStringExtra(SubmitVolunteerFragment.EXTRA_EVENT_ID));
        SubmitVolunteerFragment submitVolunteerFragment = new SubmitVolunteerFragment();
        submitVolunteerFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, submitVolunteerFragment).commit();
    }

    @Override
    protected void addFragment(String title, Fragment fragment) {

    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, SubmitVolunteeringActivity.class);
    }
}
