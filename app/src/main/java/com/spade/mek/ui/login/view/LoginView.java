package com.spade.mek.ui.login.view;

import com.spade.mek.base.BaseView;

/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public interface LoginView extends BaseView {
    void showLoading();

    void hideLoading();

    void finish();

    void navigate();

    void navigateToMainScreen();
}
