package com.spade.mek.realm;

import android.content.Context;

import com.spade.mek.ui.cart.model.CartItem;
import com.spade.mek.ui.cart.model.CartItemModel;
import com.spade.mek.ui.login.User;
import com.spade.sociallogin.SocialUser;

import io.realm.RealmList;

/**
 * Created by Ayman Abouzeid on 6/13/17.
 */

public interface RealmDbHelper {
    void saveUser(SocialUser socialUser);

    void addCartItem(CartItemModel cartItemModel, Context context);

    void increaseItemQuantity(CartItem cartItem, int position);

    void decreaseItemQuantity(CartItem cartItem, int position);

    void deleteItem(CartItem cartItem, int position);

    void deleteAllCartItems(String userId);

    void updateUserData(String firstName, String lastName, String phoneNumber, String emailAddress, String address, String userId);


    RealmList<CartItem> getCartItems(String userId);

    double getTotalCost(String userId);

    long getItemsCount(String userId);

    User getUser(String userId);

}
