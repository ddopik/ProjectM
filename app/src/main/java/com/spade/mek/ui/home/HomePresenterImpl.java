package com.spade.mek.ui.home;

import android.content.Context;

import com.spade.mek.network.ApiHelper;
import com.spade.mek.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class HomePresenterImpl implements HomePresenter {

    private HomeView mHomeView;

    public HomePresenterImpl(HomeView homeView, Context context) {
        setView(homeView);
    }

    @Override
    public void getLatestProducts(String appLang) {
        mHomeView.showLatestProductsLoading();
        ApiHelper.getLatestProducts(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestProductsResponse -> {
                    if (latestProductsResponse != null && latestProductsResponse.getLatestProductsList() != null) {
                        mHomeView.showLatestProducts(latestProductsResponse.getLatestProductsList());
                    }
                    mHomeView.hideLatestProductsLoading();
                }, throwable -> {
                    if (throwable != null) {
                        mHomeView.onError(throwable.getMessage());
                    }
                    mHomeView.hideLatestProductsLoading();
                });
    }

    @Override
    public void getLatestCauses(String appLang) {
        mHomeView.showLatestCausesLoading();
        ApiHelper.getLatestCauses(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestCausesResponse -> {
                    if (latestCausesResponse != null && latestCausesResponse.getProductsList() != null) {
                        mHomeView.showLatestCauses(latestCausesResponse.getProductsList());
                    }
                    mHomeView.hideLatestCausesLoading();
                }, throwable -> {
                    if (throwable != null) {
                        mHomeView.onError(throwable.getMessage());
                    }
                    mHomeView.hideLatestCausesLoading();
                });
    }

    @Override
    public void getUrgentCases(String appLang) {
        mHomeView.showUrgentCasesLoading();
        ApiHelper.getUrgentCases(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(urgentCasesResponse -> {
                    if (urgentCasesResponse != null && urgentCasesResponse.getProductsList() != null) {
                        mHomeView.showUrgentCases(urgentCasesResponse.getProductsList());
                    }
                    mHomeView.hideUrgentCasesLoading();
                }, throwable -> {
                    mHomeView.hideUrgentCasesLoading();
                    if (throwable != null) {
                        mHomeView.onError(throwable.getMessage());
                    }
                });
    }

    @Override
    public boolean isReverse(String appLang) {
        return !appLang.equals(PrefUtils.ENGLISH_LANG);
    }

    @Override
    public void setView(HomeView view) {
        this.mHomeView = view;
    }

    @Override
    public void shareItem(String url) {

    }
}
