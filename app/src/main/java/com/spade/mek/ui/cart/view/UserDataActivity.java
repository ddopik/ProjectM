package com.spade.mek.ui.cart.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.spade.mek.R;

/**
 * Created by Ayman Abouzeid on 6/23/17.
 */

public class UserDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getString(R.string.check_out));
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

    void addFragment() {
        Bundle bundle = new Bundle();
        int donateType = getIntent().getIntExtra(UserDataFragment.EXTRA_DONATE_TYPE, UserDataFragment.EXTRA_PAY_FOR_PRODUCTS);
        bundle.putInt(UserDataFragment.EXTRA_DONATE_TYPE, donateType);
        if (donateType == UserDataFragment.EXTRA_DONATE_ZAKAT) {
            bundle.putDouble(UserDataFragment.EXTRA_ZAKAT_AMOUNT, getIntent().getIntExtra(UserDataFragment.EXTRA_ZAKAT_AMOUNT, 0));
        }
        UserDataFragment userDataFragment = new UserDataFragment();
        userDataFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, userDataFragment).commit();
    }


    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, UserDataActivity.class);
    }
}
