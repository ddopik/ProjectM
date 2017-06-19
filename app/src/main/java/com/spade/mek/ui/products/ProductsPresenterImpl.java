package com.spade.mek.ui.products;

import android.content.Context;

import com.spade.mek.network.ApiHelper;

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
    public void getAllProducts(String lang, int page) {
        mProductsView.showProductsLoading();
        ApiHelper.getAllProducts(lang, page).subscribe(allProductsResponse -> {
            if (allProductsResponse != null) {
                mProductsView.showAllProducts(allProductsResponse);
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
                    if (urgentCasesResponse != null && urgentCasesResponse.getUrgentCaseList() != null) {
                        mProductsView.showUrgentCases(urgentCasesResponse.getUrgentCaseList());
                    }
                    mProductsView.hideUrgentCasesLoading();
                }, throwable -> {
                    mProductsView.hideUrgentCasesLoading();
                    if (throwable != null) {
                        mProductsView.onError(throwable.getMessage());
                    }
                });
    }
}
