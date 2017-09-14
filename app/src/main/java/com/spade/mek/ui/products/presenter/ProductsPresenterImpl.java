package com.spade.mek.ui.products.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.products.view.ProductsView;
import com.spade.mek.utils.ErrorUtils;
import com.spade.mek.utils.PrefUtils;
import com.spade.mek.utils.ShareManager;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class ProductsPresenterImpl implements ProductsPresenter {

    private Context mContext;
    private ProductsView mProductsView;

    public ProductsPresenterImpl(Context context) {
        this.mContext = context;
        Tracker productsTracker = MekApplication.getDefaultTracker();
        productsTracker.setScreenName(mContext.getString(R.string.products_screen));
        productsTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void setView(ProductsView view) {
        this.mProductsView = view;
    }

    @Override
    public void shareItem(String url) {
        ShareManager.share(url, mContext);
    }

    @Override
    public void getAllProducts(String lang, int page) {
        mProductsView.showProductsLoading();
        ApiHelper.getAllProducts(lang, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allProductsResponse -> {
                    if (allProductsResponse != null) {
                        mProductsView.showAllProducts(allProductsResponse.getProductsData());
                    }
                    mProductsView.hideProductsLoading();
                }, throwable -> {
                    mProductsView.hideProductsLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mProductsView.onError(ErrorUtils.getErrors(anError.getErrorBody()));
                    }
                });
    }

    @Override
    public void getUrgentCases(String lang) {
        mProductsView.showUrgentCasesLoading();
        ApiHelper.getUrgentCases(lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(urgentCasesResponse -> {
                    if (urgentCasesResponse != null && urgentCasesResponse.getUrgentCases() != null) {
                        mProductsView.showUrgentCases(urgentCasesResponse.getUrgentCases());
                    }
                    mProductsView.hideUrgentCasesLoading();
                }, throwable -> {
                    mProductsView.hideUrgentCasesLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mProductsView.onError(ErrorUtils.getErrors(anError.getErrorBody()));
                    }
                });
    }

    @Override
    public void filterProducts(String lang, JSONObject jsonObject) {
        mProductsView.showProductsLoading();
        ApiHelper.filterProducts(jsonObject, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allProductsResponse -> {
                    if (allProductsResponse != null) {
                        mProductsView.showFilteredProducts(allProductsResponse.getProductsData());
                    }
                    mProductsView.hideProductsLoading();
                }, throwable -> {
                    mProductsView.hideProductsLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mProductsView.onError(ErrorUtils.getErrors(anError.getErrorBody()));
                    }
                });
    }

    @Override
    public void search(String searchKeyWord) {
        mProductsView.showProductsLoading();
        ApiHelper.search(searchKeyWord, PrefUtils.getAppLang(mContext), UrgentCasesPagerAdapter.PRODUCT_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResponse -> {
                    mProductsView.hideProductsLoading();
                    mProductsView.showSearchResults(searchResponse);
                }, throwable -> {
                    mProductsView.hideProductsLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mProductsView.onError(ErrorUtils.getErrors(anError.getErrorBody()));
                    }
                });
    }
}
