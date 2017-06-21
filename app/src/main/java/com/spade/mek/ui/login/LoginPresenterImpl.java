package com.spade.mek.ui.login;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.spade.mek.R;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;
import com.spade.sociallogin.FacebookLoginCallBack;
import com.spade.sociallogin.FacebookLoginManager;
import com.spade.sociallogin.GoogleLoginCallBack;
import com.spade.sociallogin.GoogleLoginManager;
import com.spade.sociallogin.SocialUser;

/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public class LoginPresenterImpl implements LoginPresenter, GoogleLoginCallBack, FacebookLoginCallBack {

    private LoginView mLoginView;
    private GoogleLoginManager mGoogleLoginManager;
    private FacebookLoginManager mFacebookLoginManager;
    private Context mContext;
    private RealmDbHelper realmDbHelper;

    public LoginPresenterImpl(LoginView loginView, Context context) {
        setView(loginView);
        mContext = context;
        realmDbHelper = new RealmDbImpl();
    }


    @Override
    public void initLoginManagers(FragmentActivity loginActivity) {
        mGoogleLoginManager = new GoogleLoginManager(loginActivity, this);
        mFacebookLoginManager = new FacebookLoginManager(this);
    }

    @Override
    public void loginWithFacebook(Fragment loginFragment) {
        mFacebookLoginManager.loginWithFacebook(loginFragment);
    }

    @Override
    public void loginWithGoogle() {
        mGoogleLoginManager.loginWithGoogle();
    }

    @Override
    public void loginWithGoogle(Fragment fragment) {
        mGoogleLoginManager.loginWithGoogle(fragment);
    }

    @Override
    public void loginAsGuest() {
        mLoginView.navigateToMainScreen();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mGoogleLoginManager.onActivityResult(requestCode, data);
        mFacebookLoginManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setView(LoginView view) {
        mLoginView = view;
    }

    @Override
    public void shareItem(String url) {

    }


    @Override
    public void onGoogleLoginSuccess(SocialUser socialUser) {
        PrefUtils.setLoginProvider(mContext, LoginProviders.GOOGLE.getLoginProviderCode());
        realmDbHelper.saveUser(socialUser);
        mLoginView.navigateToMainScreen();
    }

    @Override
    public void onGoogleLoginFail() {
        mLoginView.onError(R.string.something_wrong);
    }

    @Override
    public void onFacebookLoginSuccess(SocialUser socialUser) {
        PrefUtils.setLoginProvider(mContext, LoginProviders.FACEBOOK.getLoginProviderCode());
        realmDbHelper.saveUser(socialUser);
        mLoginView.navigateToMainScreen();
    }

    @Override
    public void onFacebookLoginCancel() {

    }

    @Override
    public void onFacebookLoginFail(Exception e) {
        mLoginView.onError(e.getMessage());
    }
}
