package com.spade.mek.ui.more.profile.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.login.UserModel;
import com.spade.mek.ui.more.profile.view.EditProfileView;
import com.spade.mek.utils.ErrorUtils;
import com.spade.mek.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 9/5/17.
 */

public class EditProfilePresenterImpl implements EditProfilePresenter {

    private Context mContext;
    private RealmDbHelper realmDbHelper;
    private EditProfileView editProfileView;

    public EditProfilePresenterImpl(Context mContext) {
        this.mContext = mContext;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(EditProfileView view) {
        editProfileView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void editProfile(UserModel userModel) {
        editProfileView.showLoading();
        ApiHelper.editProfile(userModel, PrefUtils.getUserToken(mContext), PrefUtils.getNotificationToken(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registrationResponse -> {
                    realmDbHelper.saveUser(registrationResponse.getRegistrationResponseData().getUserModel(), PrefUtils.getUserToken(mContext));
                    editProfileView.hideLoading();
                    editProfileView.finish();
                }, throwable -> {
                    editProfileView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        editProfileView.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }

    @Override
    public User getUser(String userId) {
        return realmDbHelper.getUser(userId);
    }
}
