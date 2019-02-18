package com.spade.mek.ui.cart.view;

import com.spade.mek.base.BaseView;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public interface CartView extends BaseView {

    void updateUI();

    void showEmptyScreen();

    void hideEmptyScreen();

    void finish();

    void loginSuccess();

    void navigateToUserDataActivity();

    void showProgressBar();

    void hideProgressBar();
}
