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
        setContentView(R.layout.fragment_user_data);
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
        UserDataFragment userDataFragment = new UserDataFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, userDataFragment).commit();
    }


    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, UserDataActivity.class);
    }
}