package com.spade.mek.ui.products.presenter;

import android.content.Context;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.cart.model.CartItemModel;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.products.view.ProductDetailsView;
import com.spade.mek.utils.PrefUtils;
import com.spade.mek.utils.ShareManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public class ProductDetailsPresenterImpl implements ProductDetailsPresenter {

    private ProductDetailsView productDetailsView;
    private Context mContext;
    private RealmDbHelper realmDbHelper;

    public ProductDetailsPresenterImpl(Context context) {
        mContext = context;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(ProductDetailsView view) {
        productDetailsView = view;

    }

    @Override
    public void shareItem(String url) {
        ShareManager.share(url, mContext);
    }

    @Override
    public void getProductDetails(String appLang, int productId) {
        productDetailsView.showLoading();
        ApiHelper.getProductDetails(appLang, productId, PrefUtils.getUserId(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productDetailsResponse -> {
                    productDetailsView.hideLoading();
                    if (productDetailsResponse != null && productDetailsResponse.getProduct() != null) {
                        productDetailsView.updateUI(productDetailsResponse.getProduct());
                    }
                }, throwable -> {
                    productDetailsView.hideLoading();
                    if (throwable != null) {
                        productDetailsView.onError(throwable.getMessage());
                    }
                });
    }

    @Override
    public void sendAnalytics(String type) {
        Tracker detailsTracker = MekApplication.getDefaultTracker();
        if (type.equals(UrgentCasesPagerAdapter.CAUSE_TYPE)) {
            detailsTracker.setScreenName(mContext.getString(R.string.causes_inner_screen));
        } else {
            detailsTracker.setScreenName(mContext.getString(R.string.products_inner_screen));
        }
        detailsTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void unSubscribeProduct(String productID) {
        productDetailsView.showLoading();
        ApiHelper.unSubscribeFromProduct(productID, PrefUtils.getUserToken(mContext), new ApiHelper.UnSubscriptionCallBacks() {
            @Override
            public void onUnSubscribeSuccess() {
                productDetailsView.hideLoading();
                getProductDetails(PrefUtils.getAppLang(mContext), Integer.parseInt(productID));
            }

            @Override
            public void onUnSubscriptionFailed() {
                productDetailsView.hideLoading();
                productDetailsView.onError(R.string.something_wrong);
            }
        });
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
