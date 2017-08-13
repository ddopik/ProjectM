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
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final int REGISTER_TYPE = 1;
    public static final int CHECKOUT_TYPE = 2;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        type = getIntent().getIntExtra(EXTRA_TYPE, REGISTER_TYPE);
        addFragment();
    }

    private void addFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, type);
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, registerFragment).commit();
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }
}
