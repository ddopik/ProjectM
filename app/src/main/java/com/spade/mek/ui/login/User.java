package com.spade.mek.ui.login;

import android.support.annotation.Nullable;

import com.spade.mek.ui.cart.CartItem;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Ayman Abouzeid on 6/13/17.
 */

public class User extends RealmObject {

    private String userId;
    private String userToken;
    private String userEmail;
    private String userName;
    private String userAddress;
    private String userPhoto;
    private String userPhone;
    private RealmList<CartItem> cartItemList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@Nullable String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(@Nullable String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(@Nullable String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public RealmList<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(RealmList<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
