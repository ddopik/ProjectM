package com.spade.mek.ui.login;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;
import com.spade.sociallogin.FacebookLoginCallBack;
import com.spade.sociallogin.FacebookLoginManager;
import com.spade.sociallogin.GoogleLoginCallBack;
import com.spade.sociallogin.GoogleLoginManager;
import com.spade.sociallogin.SocialUser;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public class LoginPresenterImpl implements LoginPresenter, GoogleLoginCallBack, FacebookLoginCallBack {

    private LoginView mLoginView;
    //    private CartView cartView;
    private LoginDialogView loginDialogView;
    private GoogleLoginManager mGoogleLoginManager;
    private FacebookLoginManager mFacebookLoginManager;
    private Context mContext;
    private RealmDbHelper realmDbHelper;

    public LoginPresenterImpl(LoginView loginView, Context context) {
        setView(loginView);
        mContext = context;
        realmDbHelper = new RealmDbImpl();
    }

//    public LoginPresenterImpl(CartView cartView, Context context) {
//        mContext = context;
//        realmDbHelper = new RealmDbImpl();
//        this.cartView = cartView;
//    }

    public LoginPresenterImpl(LoginDialogView loginDialogView, Context context) {
        mContext = context;
        realmDbHelper = new RealmDbImpl();
        this.loginDialogView = loginDialogView;
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
    public void serverLogin(JSONObject requestJson) {
        ApiHelper.loginUser(requestJson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registrationResponse -> {
                }, throwable -> {
                });
    }


    @Override
    public void googleLogout() {
        mGoogleLoginManager.logout();
    }

    @Override
    public void facebookLogout() {
        mFacebookLoginManager.logout();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mGoogleLoginManager.onActivityResult(requestCode, data);
        mFacebookLoginManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void disconnectGoogleApiClient() {
        mGoogleLoginManager.disconnectGoogleApi();
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
        PrefUtils.setUserID(mContext, socialUser.getUserId());
        realmDbHelper.saveUser(socialUser);
        if (mLoginView != null) {
            mLoginView.navigateToMainScreen();
        }
//        else if (cartView != null) {
//            cartView.loginSuccess();
//        }
        else if (loginDialogView != null) {
            loginDialogView.loginSuccess();
        }
    }

    @Override
    public void onGoogleLoginFail() {
        if (mLoginView != null)
            mLoginView.onError(R.string.something_wrong);
//        else if (cartView != null) cartView.onError(R.string.something_wrong);
        else if (loginDialogView != null) loginDialogView.onError(R.string.something_wrong);

    }

    @Override
    public void onFacebookLoginSuccess(SocialUser socialUser) {
        PrefUtils.setLoginProvider(mContext, LoginProviders.FACEBOOK.getLoginProviderCode());
        PrefUtils.setUserID(mContext, socialUser.getUserId());
        realmDbHelper.saveUser(socialUser);
        if (mLoginView != null) {
            mLoginView.navigateToMainScreen();
        }
//        else if (cartView != null) {
//            cartView.loginSuccess();
//        }
        else if (loginDialogView != null) {
            loginDialogView.loginSuccess();
        }
    }

    @Override
    public void onFacebookLoginCancel() {

    }

    @Override
    public void onFacebookLoginFail(Exception e) {
        if (mLoginView != null)
            mLoginView.onError(R.string.something_wrong);
//        else if (cartView != null) cartView.onError(R.string.something_wrong);
        else if (loginDialogView != null) loginDialogView.onError(R.string.something_wrong);

    }
}
