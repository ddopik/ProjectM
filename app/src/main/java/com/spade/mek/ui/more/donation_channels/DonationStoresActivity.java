package com.spade.mek.ui.more.donation_channels;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.spade.mek.R;
import com.spade.mek.base.BaseActivity;
import com.spade.mek.utils.NavigationManager;

/**
 * Created by Ayman Abouzeid on 7/13/17.
 */

public class DonationStoresActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setTitle(getString(R.string.stores));
        addFragment();
    }

    @Override
    protected void addFragment() {
        StoresFragment storesFragment = new StoresFragment();
        NavigationManager.openFragmentAsRoot(R.id.fragment_container, storesFragment, this, DonationStoresActivity.class.getSimpleName());
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, DonationStoresActivity.class);
    }

    @Override
    protected void addFragment(String title, Fragment fragment) {

    }
}
