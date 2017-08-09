package com.spade.mek.ui.login.server_login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.spade.mek.R;
import com.spade.mek.ui.register.RegisterFragment;

/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public class ServerLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        addFragment();
    }

    private void addFragment() {
        RegisterFragment registerFragment = new RegisterFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, registerFragment).commit();
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, ServerLoginActivity.class);
    }
}
