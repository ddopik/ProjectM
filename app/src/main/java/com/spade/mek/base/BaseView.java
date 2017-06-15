package com.spade.mek.base;

/**
 * Created by spade on 6/11/17.
 */

public interface BaseView {

    void showLoading();

    void hideLoading();

    void showMessage(int resId);

    void hideMessage();

    void showMessage(String message);

    void showAlertDialog(String title, String message);

    void onError(String message);

    void onError(int resID);
}
