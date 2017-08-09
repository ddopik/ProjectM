package com.spade.mek.ui.login;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.spade.mek.base.BasePresenter;

import org.json.JSONObject;

/**
 * Created by Ayman Abouzeid on 6/12/17.
 */

public interface LoginPresenter extends BasePresenter<LoginView> {
    void initLoginManagers(FragmentActivity loginActivity);

    void loginWithFacebook(Fragment loginFragment);

    void loginWithGoogle();

    void loginWithGoogle(Fragment fragment);

    void loginAsGuest();

    void serverLogin(JSONObject requestJson);

    void googleLogout();

    void facebookLogout();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void disconnectGoogleApiClient();
}
