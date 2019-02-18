package com.spade.mek.ui.cart.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.home.products.Products;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public interface AddToCartPresenter extends BasePresenter {
    void addItemToCart(Products product, int quantity);

    void addItemToCart(Products product, double quantity);

}
