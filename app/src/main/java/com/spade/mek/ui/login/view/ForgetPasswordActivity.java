package com.spade.mek.ui.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.spade.mek.R;

/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public class ForgetPasswordActivity extends AppCompatActivity implements
        ForgetPasswordFragment.NavigateInterface, ChangePasswordFragment.ChangePasswordInterface {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        setTitle(getString(R.string.forget_password));
        addForgetPasswordFragment();
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

    private void addForgetPasswordFragment() {
        ForgetPasswordFragment forgetPasswordFragment = new ForgetPasswordFragment();
        forgetPasswordFragment.setNavigateInterface(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, forgetPasswordFragment)
                .commit();
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, ForgetPasswordActivity.class);
    }

    @Override
    public void navigateToChangePassword() {
        ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
        changePasswordFragment.setChangePasswordInterface(this);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container, changePasswordFragment).addToBackStack(ChangePasswordFragment.class.getSimpleName()).commit();
    }

    @Override
    public void passwordChanged() {
        Toast.makeText(this, getString(R.string.password_changed_successfully), Toast.LENGTH_LONG).show();
        finish();
    }
}
