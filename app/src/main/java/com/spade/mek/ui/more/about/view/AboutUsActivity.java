package com.spade.mek.ui.more.about.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.base.BaseActivity;
import com.spade.mek.ui.more.volunteering.view.PagingAdapter;

/**
 * Created by abdalla-maged on 2/11/18.
 */

public class AboutUsActivity extends BaseActivity {


    private View aboutUsView;
    private PagingAdapter pagingAdapter;
    private ViewPager viewPager;

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, AboutUsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_activity);
        initViews();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        overrideFonts(this, aboutUsView);
        setTitle(getString(R.string.about_us));

        Tracker homeTracker = MekApplication.getDefaultTracker();
        homeTracker.setScreenName(getString(R.string.about_us));
        homeTracker.send(new HitBuilders.ScreenViewBuilder().build());

    }

    protected void initViews() {
        aboutUsView = findViewById(R.id.about_us_main_view);
        viewPager = findViewById(R.id.about_us_fragments_viewpager);
        TabLayout tabLayout = findViewById(R.id.about_us_tabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

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

    private void setupViewPager(ViewPager viewPager) {
        pagingAdapter = new PagingAdapter(getSupportFragmentManager());
        pagingAdapter.addFragment(getAboutMisrFragment(), getString(R.string.misr_elkhir_about_tab));
        pagingAdapter.addFragment(getAboutProgram_projectFragment(), getString(R.string.project_program_about_tab));
        viewPager.setAdapter(pagingAdapter);
        viewPager.setOffscreenPageLimit(2);
    }

    private AboutMisrFragment getAboutMisrFragment() {
        AboutMisrFragment aboutMisrFragmentFragment = new AboutMisrFragment();
        return aboutMisrFragmentFragment;
    }

    private AboutProgramFragment getAboutProgram_projectFragment() {
        AboutProgramFragment aboutProgram_Fragment = new AboutProgramFragment();
        return aboutProgram_Fragment;

    }

    @Override
    protected void addFragment() {

    }

    @Override
    protected void addFragment(String title, Fragment fragment) {

    }

}
