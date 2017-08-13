package com.spade.mek.ui.register;

import android.content.Context;
import android.os.AsyncTask;

import com.spade.mek.network.ApiHelper;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.login.UserModel;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public class RegisterPresenterImpl implements RegisterPresenter {

    private Context mContext;
    private RealmDbHelper realmDbHelper;
    private RegisterView registerView;
    private UserModel userModel;

    public RegisterPresenterImpl(Context mContext) {
        this.mContext = mContext;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(RegisterView view) {
        this.registerView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public User getUser(String userId) {
        return realmDbHelper.getUser(userId);
    }

    @Override
    public void register(UserModel userModel) {
        this.userModel = userModel;
        new CreateJsonRequestObject().execute();
    }

    private void registerUser(JSONObject requestJson) {
        registerView.showLoading();
        ApiHelper.registerUser(requestJson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registrationResponse -> {
                    if (registrationResponse != null && registrationResponse.isSuccess()) {
                        realmDbHelper.saveUser(registrationResponse.getRegistrationResponseData().getUserModel(),
                                registrationResponse.getRegistrationResponseData().getToken());
                        UserModel userModel = registrationResponse.getRegistrationResponseData().getUserModel();
                        realmDbHelper.updateUserData(userModel.getFirstName(), userModel.getLastName(), userModel.getUserPhone(),
                                userModel.getUserEmail(), userModel.getUserAddress(), userModel.getUserId());
                        realmDbHelper.updateCartItemsWithLoggedInUser(userModel.getUserId());

                        PrefUtils.setLoginProvider(mContext, LoginProviders.SERVER_LOGIN.getLoginProviderCode());
                        PrefUtils.setUserID(mContext, userModel.getUserId());
                        PrefUtils.setUserToken(mContext, registrationResponse.getRegistrationResponseData().getToken());

                        registerView.hideLoading();
                        registerView.finish();
                        registerView.navigate();
                    }
                }, throwable -> {
                    if (throwable != null) {
                        registerView.hideLoading();
                        registerView.onError(throwable.getLocalizedMessage());
                    }
                });
    }


    private class CreateJsonRequestObject extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject requestJsonObject = null;
            try {
                requestJsonObject = new JSONObject();
                requestJsonObject.put("first_name", userModel.getFirstName());
                requestJsonObject.put("last_name", userModel.getLastName());
                requestJsonObject.put("email", userModel.getUserEmail());
                requestJsonObject.put("phone", userModel.getUserPhone());
                requestJsonObject.put("address", userModel.getUserAddress());
                requestJsonObject.put("password", userModel.getPassword());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return requestJsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            registerUser(jsonObject);
        }
    }
}
