package com.spade.mek.ui.cart.presenter;

import android.content.Context;

import com.spade.mek.base.BaseView;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.cart.model.CartItemModel;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.Products;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class AddToCartPresenterImpl implements AddToCartPresenter {

    private Context mContext;
    private RealmDbHelper realmDbHelper;

    public AddToCartPresenterImpl(Context mContext) {
        this.mContext = mContext;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(BaseView view) {

    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void addItemToCart(Products product, int quantity) {
        addCartItem(product, quantity, 0);
    }

    @Override
    public void addItemToCart(Products product, double quantity) {
        addCartItem(product, 0, quantity);
    }

    private void addCartItem(Products product, int quantity, double moneyAmount) {
        CartItemModel cartItemModel = new CartItemModel();
        cartItemModel.setItemId(product.getProductId());
        cartItemModel.setItemTitle(product.getProductTitle());
        cartItemModel.setItemType(product.getProductType());
        cartItemModel.setItemImage(product.getProductImage());
        cartItemModel.setAmount(quantity);
        cartItemModel.setMoneyAmount(moneyAmount);
        cartItemModel.setItemPrice(product.getProductPrice());
        if (product.getProductType().equals(UrgentCasesPagerAdapter.CAUSE_TYPE)) {
            cartItemModel.setTotalCost(moneyAmount);
        } else {
            cartItemModel.setTotalCost(quantity * product.getProductPrice());
        }
        realmDbHelper.addCartItem(cartItemModel, mContext);
    }
}
