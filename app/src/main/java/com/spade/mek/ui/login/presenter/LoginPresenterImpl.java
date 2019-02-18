package com.spade.mek.ui.login.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.androidnetworking.error.ANError;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.login.UserModel;
import com.spade.mek.ui.login.view.LoginDialogView;
import com.spade.mek.ui.login.view.LoginView;
import com.spade.mek.ui.more.MorePresenterImpl;
import com.spade.mek.ui.register.RegistrationResponse;
import com.spade.mek.utils.ErrorUtils;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;
import com.spade.sociallogin.FacebookLoginCallBack;
import com.spade.sociallogin.FacebookLoginManager;
import com.spade.sociallogin.GoogleLoginCallBack;
import com.spade.sociallogin.GoogleLoginManager;
import com.spade.sociallogin.SocialUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public class LoginPresenterImpl implements LoginPresenter, GoogleLoginCallBack, FacebookLoginCallBack {

    private static final String FACEBOOK_TYPE = "Facebook";
    private static final String GOOGLE_TYPE = "Google";

    private LoginView mLoginView;
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
    public void serverLogin(UserModel userModel) {
        mLoginView.showLoading();
        JSONObject requestJsonObject = null;
        try {
            requestJsonObject = new JSONObject();
            requestJsonObject.put("email", userModel.getUserEmail());
            requestJsonObject.put("password", userModel.getPassword());
            requestJsonObject.put("notification_token", PrefUtils.getNotificationToken(mContext));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiHelper.loginUser(requestJsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    if (loginResponse != null) {
                        completeLogin(loginResponse);
                    }
                }, throwable -> {
                    mLoginView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mLoginView.onError(ErrorUtils.getErrors(anError));
                    }
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
//        PrefUtils.setLoginProvider(mContext, LoginProviders.GOOGLE.getLoginProviderCode());
//        PrefUtils.setUserID(mContext, socialUser.getUserId());
//        realmDbHelper.saveUser(socialUser);

        socialLoginUser(socialUser, GOOGLE_TYPE);
//        if (mLoginView != null) {
//            mLoginView.navigateToMainScreen();
//        }
////        else if (cartView != null) {
////            cartView.loginSuccess();
////        }
//        else if (loginDialogView != null) {
//            loginDialogView.loginSuccess();
//        }
    }

    private void socialLoginUser(SocialUser socialUser, String type) {
        JSONObject requestJsonObject = null;
        try {
            requestJsonObject = new JSONObject();
            requestJsonObject.put("name", socialUser.getName());
            requestJsonObject.put("email", socialUser.getEmailAddress());
            requestJsonObject.put("type", type);
            requestJsonObject.put("social_id", socialUser.getUserId());
            requestJsonObject.put("notification_token", PrefUtils.getNotificationToken(mContext));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiHelper.socialLoginUSer(requestJsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registrationResponse -> {
                    if (registrationResponse != null) {
                        completeLogin(registrationResponse);
                    }
                }, throwable -> {
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mLoginView.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }

    private void completeLogin(RegistrationResponse response) {
        realmDbHelper.saveUser(response.getRegistrationResponseData().getUserModel(),
                response.getRegistrationResponseData().getToken());

        UserModel userDataModel = response.getRegistrationResponseData().getUserModel();
        realmDbHelper.updateUserData(userDataModel.getFirstName(), userDataModel.getLastName(), userDataModel.getUserPhone(),
                userDataModel.getUserEmail(), userDataModel.getUserAddress(), userDataModel.getUserId());
        realmDbHelper.updateCartItemsWithLoggedInUser(userDataModel.getUserId()).subscribe(isSuccess -> {
            if (isSuccess) {
                changeLanguage();
                PrefUtils.setLoginProvider(mContext, LoginProviders.SERVER_LOGIN.getLoginProviderCode());
                PrefUtils.setUserID(mContext, userDataModel.getUserId());
                PrefUtils.setUserToken(mContext, response.getRegistrationResponseData().getToken());

                if (mLoginView != null) {
                    mLoginView.hideLoading();
                    mLoginView.finish();
                    mLoginView.navigate();
                } else {
                    loginDialogView.loginSuccess();
                }
            }
        });

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
//        PrefUtils.setLoginProvider(mContext, LoginProviders.FACEBOOK.getLoginProviderCode());
//        PrefUtils.setUserID(mContext, socialUser.getUserId());
//        realmDbHelper.saveUser(socialUser);
        socialLoginUser(socialUser, FACEBOOK_TYPE);
//        if (mLoginView != null) {
//            mLoginView.navigateToMainScreen();
//        } else if (loginDialogView != null) {
//            loginDialogView.loginSuccess();
//        }
    }

    @SuppressWarnings("deprecation")
    public void changeLanguage() {
        Locale locale;
        if (PrefUtils.isLanguageSelected(mContext)) {
            if (PrefUtils.getAppLang(mContext).equals(PrefUtils.ARABIC_LANG)) {
                locale = new Locale(MorePresenterImpl.AR_LANG);
            } else {
                locale = new Locale(MorePresenterImpl.EN_LANG);
            }
            Configuration conf = new Configuration();
            conf.locale = locale;
            mContext.getResources().updateConfiguration(conf, mContext.getResources().getDisplayMetrics());
        } else {
            String deviceLang = Locale.getDefault().getLanguage();
            if (!deviceLang.equals(PrefUtils.ARABIC_LANG)) {
                PrefUtils.setAppLang(mContext, PrefUtils.ENGLISH_LANG);
            } else {
                PrefUtils.setAppLang(mContext, PrefUtils.ARABIC_LANG);
            }
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
