package com.spade.mek.ui.more.profile.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.login.UserModel;
import com.spade.mek.ui.more.profile.view.EditProfileView;

/**
 * Created by Ayman Abouzeid on 9/5/17.
 */

public interface EditProfilePresenter extends BasePresenter<EditProfileView> {

    void editProfile(UserModel userModel);

    User getUser(String userId);
}
