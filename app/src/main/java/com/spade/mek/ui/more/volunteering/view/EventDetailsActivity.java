package com.spade.mek.ui.more.volunteering.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.spade.mek.R;
import com.spade.mek.base.BaseActivity;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.utils.NavigationManager;

/**
 * Created by Ayman Abouzeid on 6/21/17.
 */

public class EventDetailsActivity extends BaseActivity implements ProductDetailsFragment.CartAction {

    public static final String EXTRA_EVENT_DETAILS = "EXTRA_EVENT_DETAILS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setTitle(getString(R.string.volunteering));
        addFragment();
    }

    @Override
    protected void addFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_EVENT_DETAILS, getIntent().getParcelableExtra(EXTRA_EVENT_DETAILS));

        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
        eventDetailsFragment.setArguments(bundle);
        NavigationManager.openFragmentAsRoot(R.id.fragment_container, eventDetailsFragment, this, EventDetailsActivity.class.getSimpleName());
    }

    @Override
    protected void addFragment(String title, Fragment fragment) {

    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, EventDetailsActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCounter();
    }

    @Override
    public void onItemInserted() {
        updateCounter();
    }
}
