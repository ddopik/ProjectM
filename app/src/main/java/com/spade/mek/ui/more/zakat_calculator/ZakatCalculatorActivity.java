package com.spade.mek.ui.more.zakat_calculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.spade.mek.R;
import com.spade.mek.base.BaseActivity;

/**
 * Created by Ayman Abouzeid on 7/25/17.
 */

public class ZakatCalculatorActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getString(R.string.calculator));
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
        ZakatCalculatorFragment zakatCalculatorFragment = new ZakatCalculatorFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, zakatCalculatorFragment).commit();
    }

    @Override
    protected void addFragment(String title, Fragment fragment) {

    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, ZakatCalculatorActivity.class);
    }
}
