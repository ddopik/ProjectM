package com.spade.mek.ui.cart.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.cart.model.CartItem;
import com.spade.mek.ui.cart.view.CartView;
import com.spade.mek.utils.ErrorUtils;
import com.spade.mek.utils.PrefUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;

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

    @Override
    public void updateUserCartItems(String userId) {
        realmDbHelper.updateCartItemsWithLoggedInUser(userId).
                subscribeOn(Schedulers.io()).
                subscribe(aBoolean -> {
                    if (aBoolean)
                        mCartView.navigateToUserDataActivity();
                    else
                        mCartView.onError(R.string.something_wrong);
                }, throwable -> {
                    if (throwable != null)
                        mCartView.onError(throwable.getMessage());
                });
    }

    @Override
    public void updateCartItemsData() {
        mCartView.showProgressBar();
        Realm realm = Realm.getDefaultInstance();
        List<CartItem> cartItemList = realm.where(CartItem.class).equalTo("userId", PrefUtils.getUserId(mContext)).findAll();
        List<Integer> itemsIds = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            itemsIds.add(cartItem.getItemId());
        }
        realm.close();

        ApiHelper.getCartItemsData(PrefUtils.getAppLang(mContext), getJsonRequest(itemsIds))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestProductsResponse -> {
                    mCartView.hideProgressBar();
                    if (latestProductsResponse != null && latestProductsResponse.getLatestProductsList() != null
                            && !latestProductsResponse.getLatestProductsList().isEmpty()) {
                        realmDbHelper.updateCartItems(latestProductsResponse.getLatestProductsList(), PrefUtils.getUserId(mContext)).subscribe(aBoolean -> {
                            mCartView.updateUI();
                        });
                    } else {
                        mCartView.updateUI();
                    }
                }, throwable -> {
                    mCartView.hideProgressBar();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mCartView.onError(ErrorUtils.getErrors(anError.getErrorBody()));
                    }
                    mCartView.updateUI();
                });
    }

    @Override
    public RealmList<CartItem> getCartItems(String userId) {
        return realmDbHelper.getCartItems(userId);
    }

    private JSONObject getJsonRequest(List<Integer> itemsIds) {
        JSONArray jsonElements = new JSONArray();
        JSONObject requestJsonObject = null;
        for (int id : itemsIds) {
            jsonElements.put(id);
        }
        try {
            requestJsonObject = new JSONObject();
            requestJsonObject.put("cart", jsonElements);
        } catch (Exception ignored) {
        }
        return requestJsonObject;
    }
}
