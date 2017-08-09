package com.spade.mek.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.spade.mek.R;

/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public class RegisterActivity extends AppCompatActivity {

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
        return new Intent(context, RegisterActivity.class);
    }
}
