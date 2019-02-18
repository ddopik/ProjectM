package com.spade.mek.ui.products.view;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.home.products.Products;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public interface ProductDetailsView extends BaseView {

    void showLoading();

    void hideLoading();

    void updateUI(Products products);
}
