package com.spade.mek.ui.login.presenter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.login.UserModel;
import com.spade.mek.ui.login.view.LoginView;

/**
 * Created by Ayman Abouzeid on 6/12/17.
 */

public interface LoginPresenter extends BasePresenter<LoginView> {
    void initLoginManagers(FragmentActivity loginActivity);

    void loginWithFacebook(Fragment loginFragment);

    void loginWithGoogle(Fragment fragment);

    void loginAsGuest();

    void serverLogin(UserModel userModel);

    void googleLogout();

    void loginWithGoogle();

    void facebookLogout();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void disconnectGoogleApiClient();
}
