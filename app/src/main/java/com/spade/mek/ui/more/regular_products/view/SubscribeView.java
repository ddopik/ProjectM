package com.spade.mek.ui.more.regular_products.view;

import com.spade.mek.base.BaseView;

/**
 * Created by Ayman Abouzeid on 8/22/17.
 */

public interface SubscribeView extends BaseView {

    void showLoading();

    void hideLoading();

    void finish();

    void showConfirmationDialog();
}
