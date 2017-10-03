package com.spade.mek.ui.cart.view;

import com.spade.mek.base.BaseView;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public interface UserDataView extends BaseView {

    void showLoading();

    void hideLoading();

    void finish();

    void navigateToConfirmationScreen();

    void navigateToPayment(String paymentUrl);

    void openUrl(String paymentUrl);

    void showFailedTransactionAlert();
}
