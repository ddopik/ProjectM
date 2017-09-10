package com.spade.mek.ui.more.profile.presenter;

import android.content.Context;

import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.login.UserModel;
import com.spade.mek.ui.more.profile.view.EditProfileView;
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
        //TODO notification token
        editProfileView.showLoading();
        ApiHelper.editProfile(userModel, PrefUtils.getUserToken(mContext), "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registrationResponse -> {
                    editProfileView.hideLoading();
                    editProfileView.finish();
                }, throwable -> {
                    editProfileView.hideLoading();
                    editProfileView.onError(R.string.something_wrong);
                });
    }

    @Override
    public User getUser(String userId) {
        return realmDbHelper.getUser(userId);
    }
}
