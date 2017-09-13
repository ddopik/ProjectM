package com.spade.mek.realm;

import android.content.Context;

import com.spade.mek.ui.cart.model.CartItem;
import com.spade.mek.ui.cart.model.CartItemModel;
import com.spade.mek.ui.cart.model.OrderDone;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.login.UserModel;
import com.spade.mek.utils.PrefUtils;
import com.spade.sociallogin.SocialUser;

import java.util.List;

import io.reactivex.Observable;
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
        realmInstance.beginTransaction();
        User user = new User();
        user.setUserEmail(socialUser.getEmailAddress());
        user.setFirstName(socialUser.getName());
        user.setUserPhoto(socialUser.getUserPhoto());
        user.setUserId(socialUser.getUserId());
        realmInstance.copyToRealmOrUpdate(user);
        realmInstance.commitTransaction();
        realmInstance.close();
    }

    @Override
    public void saveUser(UserModel userModel, String userToken) {
        Realm realmInstance = Realm.getDefaultInstance();
        realmInstance.beginTransaction();
        User user = new User();
        user.setUserEmail(userModel.getUserEmail());
        user.setFirstName(userModel.getFirstName());
        user.setUserId(userModel.getUserId());
        user.setLastName(userModel.getLastName());
        user.setUserPhone(userModel.getUserPhone());
        user.setUserToken(userToken);
        realmInstance.copyToRealmOrUpdate(user);
        realmInstance.commitTransaction();
        realmInstance.close();
    }

    @Override
    public void addCartItem(CartItemModel cartItemModel, Context context) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        CartItem cartItem = realm.where(CartItem.class).equalTo("itemId", cartItemModel.getItemId()).findFirst();
        if (cartItem != null) {
            if (cartItem.getItemType().equals(UrgentCasesPagerAdapter.CAUSE_TYPE)) {
                cartItem.setMoneyAmount(cartItem.getMoneyAmount() + cartItemModel.getMoneyAmount());
                cartItem.setTotalCost(cartItem.getMoneyAmount());
            } else {
                cartItem.setAmount(cartItem.getAmount() + cartItemModel.getAmount());
                cartItem.setTotalCost(cartItem.getAmount() * cartItem.getItemPrice());
            }
        } else {
            Number maxCartItemId = realm.where(CartItem.class).max("cartItemId");
            int nextItemId;
            if (maxCartItemId == null) {
                nextItemId = 1;
            } else {
                nextItemId = maxCartItemId.intValue() + 1;
            }
            CartItem newCartItem = realm.createObject(CartItem.class, nextItemId);
            newCartItem.setUserId(PrefUtils.getUserId(context));
            newCartItem.setAmount(cartItemModel.getAmount());
            newCartItem.setItemImage(cartItemModel.getItemImage());
            newCartItem.setItemPrice(cartItemModel.getItemPrice());
            newCartItem.setItemType(cartItemModel.getItemType());
            newCartItem.setItemTitle(cartItemModel.getItemTitle());
            newCartItem.setItemId(cartItemModel.getItemId());
            newCartItem.setMoneyAmount(cartItemModel.getMoneyAmount());
            newCartItem.setTotalCost(cartItemModel.getTotalCost());
        }
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
    public void deleteUser(String userId) {
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).equalTo("userId", userId).findFirst();
        realm.beginTransaction();
        user.deleteFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public Observable<Boolean> updateCartItemsWithLoggedInUser(String userId) {
        return Observable.create(e -> {
            Realm realm = Realm.getDefaultInstance();
            RealmList<CartItem> cartItemList = new RealmList<>();
            cartItemList.addAll(realm.where(CartItem.class).equalTo("userId", PrefUtils.GUEST_USER_ID).findAll());
            realm.beginTransaction();
            for (CartItem cartItem : cartItemList) {
                cartItem.setUserId(userId);
            }
            realm.commitTransaction();
            realm.close();

            e.onNext(true);
            e.onComplete();

        });
    }

    @Override
    public RealmList<CartItem> getCartItems(String userId) {
        Realm realm = Realm.getDefaultInstance();
        RealmList<CartItem> cartItemList = new RealmList<>();
        cartItemList.addAll(realm.where(CartItem.class).equalTo("userId", userId).findAll());
        return cartItemList;
    }

    public CartItem getCartItem(Realm realm, String userId, int itemId) {
        CartItem cartItem = realm.where(CartItem.class).equalTo("userId", userId).equalTo("itemId", itemId).findFirst();
        return cartItem;
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
        realm.refresh();
        return realm.where(User.class).equalTo("userId", userId).findFirst();
    }

    @Override
    public void saveOrderDone(String orderID) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        OrderDone orderDone = realm.createObject(OrderDone.class);
        orderDone.setOrderID(orderID);
        orderDone.setSynced(false);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void updateOrderStatus(String orderId, boolean synced) {
        Realm realm = Realm.getDefaultInstance();
        OrderDone orderDone = realm.where(OrderDone.class).equalTo("orderID", orderId).findFirst();
        realm.beginTransaction();
        orderDone.setSynced(synced);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public Observable<Boolean> updateCartItems(List<Products> productsList, String userId) {
        return Observable.create(e -> {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            for (Products products : productsList) {
                CartItem cartItem = getCartItem(realm, userId, products.getProductId());
                cartItem.setItemTitle(products.getProductTitle());
                cartItem.setItemPrice(products.getProductPrice());
                if (products.getProductType().equals(UrgentCasesPagerAdapter.PRODUCT_TYPE)) {
                    cartItem.setTotalCost(products.getProductPrice() * cartItem.getAmount());
                }
            }
            realm.commitTransaction();
            realm.close();
            e.onNext(true);
            e.onComplete();
        });
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
