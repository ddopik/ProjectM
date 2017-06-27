package com.spade.mek.realm;

import android.content.Context;

import com.spade.mek.ui.cart.model.CartItem;
import com.spade.mek.ui.cart.model.CartItemModel;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.login.User;
import com.spade.mek.utils.PrefUtils;
import com.spade.sociallogin.SocialUser;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Ayman Abouzeid on 6/13/17.
 */

public class RealmDbImpl implements RealmDbHelper {
    public RealmDbImpl() {
    }

    @Override
    public void saveUser(SocialUser socialUser) {
        Realm realmInstance = Realm.getDefaultInstance();
        realmInstance.executeTransaction(realm -> {
            User user = realm.createObject(User.class);
            user.setUserEmail(socialUser.getEmailAddress());
            user.setFirstName(socialUser.getName());
            user.setUserId(socialUser.getUserId());
            user.setUserPhoto(socialUser.getUserPhoto());
        });
        realmInstance.close();
    }

    @Override
    public void addCartItem(CartItemModel cartItemModel, Context context) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Number maxCartItemId = realm.where(CartItem.class).max("cartItemId");
        int nextItemId;
        if (maxCartItemId == null) {
            nextItemId = 1;
        } else {
            nextItemId = maxCartItemId.intValue() + 1;
        }
        CartItem cartItem = realm.createObject(CartItem.class, nextItemId);
        cartItem.setUserId(PrefUtils.getUserId(context));
        cartItem.setAmount(cartItemModel.getAmount());
        cartItem.setItemImage(cartItemModel.getItemImage());
        cartItem.setItemPrice(cartItemModel.getItemPrice());
        cartItem.setItemType(cartItemModel.getItemType());
        cartItem.setItemTitle(cartItemModel.getItemTitle());
        cartItem.setItemId(cartItemModel.getItemId());
        cartItem.setMoneyAmount(cartItemModel.getMoneyAmount());
        cartItem.setTotalCost(cartItemModel.getTotalCost());

        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void increaseItemQuantity(CartItem cartItem, int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        if (cartItem.getItemType().equals(UrgentCasesPagerAdapter.CAUSE_TYPE)) {
            cartItem.setMoneyAmount(cartItem.getMoneyAmount() + 1);
            cartItem.setTotalCost(cartItem.getMoneyAmount());
        } else {
            cartItem.setAmount(cartItem.getAmount() + 1);
            cartItem.setTotalCost(cartItem.getAmount() * cartItem.getItemPrice());
        }
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void decreaseItemQuantity(CartItem cartItem, int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        if (cartItem.getItemType().equals(UrgentCasesPagerAdapter.CAUSE_TYPE)) {
            if (cartItem.getMoneyAmount() > 1) {
                cartItem.setMoneyAmount(cartItem.getMoneyAmount() - 1);
                cartItem.setTotalCost(cartItem.getMoneyAmount());
            }
        } else {
            if (cartItem.getAmount() > 1) {
                cartItem.setAmount(cartItem.getAmount() - 1);
                cartItem.setTotalCost(cartItem.getAmount() * cartItem.getItemPrice());
            }
        }
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void deleteItem(CartItem cartItem, int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        cartItem.deleteFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void deleteAllCartItems(String userId) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(CartItem.class).equalTo("userId", userId)
                .findAll().deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void updateUserData(String firstName, String lastName, String phoneNumber,
                               String emailAddress, String address, String userId) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User user = new User();
        user.setUserEmail(emailAddress);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserAddress(address);
        user.setUserPhone(phoneNumber);
        user.setUserId(userId);
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public RealmList<CartItem> getCartItems(String userId) {
        Realm realm = Realm.getDefaultInstance();
        RealmList<CartItem> cartItemList = new RealmList<>();
        cartItemList.addAll(realm.where(CartItem.class).equalTo("userId", userId).findAll());
        return cartItemList;
    }

    @Override
    public double getTotalCost(String userId) {
        Realm realm = Realm.getDefaultInstance();
        double totalCost = realm.where(CartItem.class).equalTo("userId", userId).sum("totalCost").doubleValue();
        realm.close();
        return totalCost;
    }

    @Override
    public long getItemsCount(String userId) {
        Realm realm = Realm.getDefaultInstance();
        long itemsCount = realm.where(CartItem.class).equalTo("userId", userId).count();
        realm.close();
        return itemsCount;
    }

    @Override
    public User getUser(String userId) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(User.class).equalTo("userId", userId).findFirst();
    }


//    @Override
//    public void closeRealmInstance() {
//        realm.close();
//    }
//
//    @Override
//    public void closeRealmInstance(RealmChangeListener realmChangeListener) {
//        if (realmChangeListener != null) {
//            realm.removeChangeListener(realmChangeListener);
//        }
//        realm.close();
//    }
//
//    @Override
//    public Realm getRealmInstance(RealmChangeListener realmChangeListener) {
//        realm = Realm.getDefaultInstance();
//        if (realmChangeListener != null)
//            realm.addChangeListener(realmChangeListener);
//        return realm;
//    }
//
//    @Override
//    public Realm getRealmInstance() {
//        realm = Realm.getDefaultInstance();
//        return realm;
//    }


}
