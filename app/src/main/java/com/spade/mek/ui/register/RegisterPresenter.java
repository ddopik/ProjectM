package com.spade.mek.ui.register;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.login.UserModel;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public interface RegisterPresenter extends BasePresenter<RegisterView> {
    User getUser(String userId);

    void register(UserModel userModel);
}
