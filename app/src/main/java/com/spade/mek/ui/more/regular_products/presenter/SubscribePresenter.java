package com.spade.mek.ui.more.regular_products.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.more.regular_products.view.SubscribeView;

/**
 * Created by Ayman Abouzeid on 8/22/17.
 */

public interface SubscribePresenter extends BasePresenter<SubscribeView> {

    void subscribeToProduct(Products products, double amount, int quantity, int duration);

    void onSubscribeDialogDismissed();
}
