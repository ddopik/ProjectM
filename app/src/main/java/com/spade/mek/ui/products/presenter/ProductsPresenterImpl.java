package com.spade.mek.ui.products.presenter;

import android.content.Context;

import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.products.view.ProductsView;
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
                        mProductsView.onError(throwable.getMessage());
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
                        mProductsView.onError(throwable.getMessage());
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
                        mProductsView.onError(throwable.getMessage());
                    }
                });
    }
}
