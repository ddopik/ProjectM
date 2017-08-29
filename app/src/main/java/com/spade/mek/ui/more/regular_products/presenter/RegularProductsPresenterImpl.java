package com.spade.mek.ui.more.regular_products.presenter;

import android.content.Context;

import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.regular_products.view.RegularProductsView;
import com.spade.mek.utils.ShareManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class RegularProductsPresenterImpl implements RegularProductsPresenter {

    private Context mContext;
    private RegularProductsView mRegularProductsView;

    public RegularProductsPresenterImpl(Context context) {
        this.mContext = context;
//        Tracker productsTracker = MekApplication.getDefaultTracker();
//        productsTracker.setScreenName(mContext.getString(R.string.products_screen));
//        productsTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void setView(RegularProductsView view) {
        this.mRegularProductsView = view;
    }

    @Override
    public void shareItem(String url) {
        ShareManager.share(url, mContext);
    }

    @Override
    public void getRegularProducts(String lang, int page) {
        mRegularProductsView.showProductsLoading();
        ApiHelper.getRegularProducts(lang, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allProductsResponse -> {
                    if (allProductsResponse != null) {
                        mRegularProductsView.showRegularProducts(allProductsResponse.getProductsData());
                    }
                    mRegularProductsView.hideProductsLoading();
                }, throwable -> {
                    mRegularProductsView.hideProductsLoading();
                    if (throwable != null) {
                        mRegularProductsView.onError(throwable.getMessage());
                    }
                });
    }

    @Override
    public void getProfileRegularProducts(String lang, String userToken) {
        mRegularProductsView.showProductsLoading();
        ApiHelper.getProfileRegularProducts(lang, userToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allProductsResponse -> {
                    if (allProductsResponse != null) {
                        mRegularProductsView.showRegularProducts(allProductsResponse.getProductsDataProductsList());
                    }
                    mRegularProductsView.hideProductsLoading();
                }, throwable -> {
                    mRegularProductsView.hideProductsLoading();
                    if (throwable != null) {
                        mRegularProductsView.onError(throwable.getMessage());
                    }
                });
    }
}
