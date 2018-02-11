package com.spade.mek.ui.more.about.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.spade.mek.R;
import com.spade.mek.base.BaseActivity;
import com.spade.mek.ui.more.contact_us.view.ContactUsActivity;

/**
 * Created by abdalla-maged on 2/11/18.
 */

public class AboutUsActivity extends BaseActivity {


    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, AboutUsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getString(R.string.about_us));

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
        AboutUsFragment aboutUsFragment = new AboutUsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutUsFragment).commit();
    }

    @Override
    protected void addFragment(String title, Fragment fragment) {

    }
}
