package com.spade.mek.ui.more.contact_us.view;

import com.spade.mek.base.BaseView;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public interface SendMessageView extends BaseView {

    void showLoading();

    void hideLoading();

    void finish();

    void navigateToConfirmationScreen();
}
