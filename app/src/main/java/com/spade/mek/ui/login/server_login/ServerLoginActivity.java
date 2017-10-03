package com.spade.mek.ui.login.server_login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.spade.mek.R;

import static com.spade.mek.ui.register.RegisterActivity.EXTRA_TYPE;
import static com.spade.mek.ui.register.RegisterActivity.REGISTER_TYPE;

/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public class ServerLoginActivity extends AppCompatActivity {
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        type = getIntent().getIntExtra(EXTRA_TYPE, REGISTER_TYPE);
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


    private void addFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, type);
        ServerLoginFragment serverLoginFragment = new ServerLoginFragment();
        serverLoginFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, serverLoginFragment).commit();
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, ServerLoginActivity.class);
    }
}
