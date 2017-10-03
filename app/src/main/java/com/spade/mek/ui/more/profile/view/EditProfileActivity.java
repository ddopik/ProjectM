package com.spade.mek.ui.more.profile.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.spade.mek.R;
import com.spade.mek.ui.cart.view.CartFragment;

/**
 * Created by Ayman Abouzeid on 6/22/17.
 */

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getString(R.string.edit_profile));
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
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editProfileFragment).commit();
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, EditProfileActivity.class);
    }


}
