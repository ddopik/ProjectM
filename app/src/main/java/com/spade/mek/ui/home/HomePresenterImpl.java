package com.spade.mek.ui.home;

import android.content.Context;

import com.spade.mek.ApiHelper;

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
        mHomeView.showLoading();
        ApiHelper.getLatestProducts(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestProductsResponse -> {
                    if (latestProductsResponse != null && latestProductsResponse.getLatestProductsList() != null) {
                        mHomeView.showLatestProducts(latestProductsResponse.getLatestProductsList());
                    }
                    mHomeView.hideLoading();
                }, throwable -> {
                    mHomeView.hideLoading();
                    mHomeView.onError(throwable.getMessage());
                });
    }

    @Override
    public void getLatestCauses(String appLang) {
        mHomeView.showLoading();
        ApiHelper.getLatestCauses(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestCausesResponse -> {
                    if (latestCausesResponse != null && latestCausesResponse.getLatestCausesList() != null) {
                        mHomeView.showLatestCauses(latestCausesResponse.getLatestCausesList());
                    }
                    mHomeView.hideLoading();
                }, throwable -> {
                    mHomeView.hideLoading();
                    mHomeView.onError(throwable.getMessage());
                });
    }

    @Override
    public void getUrgentCases(String appLang) {

    }

    @Override
    public void setView(HomeView view) {
        this.mHomeView = view;
    }
}
