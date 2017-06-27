package com.spade.mek.ui.cart.presenter;

import android.content.Context;

import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.cart.model.CartItem;
import com.spade.mek.ui.cart.view.CartView;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public class CartPresenterImpl implements CartPresenter {

    private CartView mCartView;
    private Context mContext;
    private RealmDbHelper realmDbHelper;

    public CartPresenterImpl(Context mContext) {
        this.mContext = mContext;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(CartView view) {
        mCartView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void increaseItemQuantity(CartItem cartItem, int position) {
        realmDbHelper.increaseItemQuantity(cartItem, position);
    }

    @Override
    public void decreaseItemQuantity(CartItem cartItem, int position) {
        realmDbHelper.decreaseItemQuantity(cartItem, position);
    }

    @Override
    public void deleteItem(CartItem cartItem, int position) {
        realmDbHelper.deleteItem(cartItem, position);
    }

    @Override
    public double getTotalCost() {
        return realmDbHelper.getTotalCost(PrefUtils.getUserId(mContext));
    }

    @Override
    public long getItemsCount() {
        return realmDbHelper.getItemsCount(PrefUtils.getUserId(mContext));
    }
}
