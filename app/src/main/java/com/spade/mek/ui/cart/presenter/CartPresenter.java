package com.spade.mek.ui.cart.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.cart.model.CartItem;
import com.spade.mek.ui.cart.view.CartView;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public interface CartPresenter extends BasePresenter<CartView> {

    void increaseItemQuantity(CartItem cartItem, int position);

    void decreaseItemQuantity(CartItem cartItem, int position);

    void deleteItem(CartItem cartItem, int position);

    double getTotalCost();

    long getItemsCount();

}
