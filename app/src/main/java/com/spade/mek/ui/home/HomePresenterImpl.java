package com.spade.mek.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;

import com.androidnetworking.error.ANError;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.news.model.AllHomeNewsResponse;
import com.spade.mek.utils.ErrorUtils;
import com.spade.mek.utils.PrefUtils;
import com.spade.mek.utils.ShareManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class HomePresenterImpl implements HomePresenter {

    private HomeView mHomeView;
    private Context mContext;

    public HomePresenterImpl(HomeView homeView, Context context) {
        setView(homeView);
        mContext = context;
    }
    @SuppressLint("CheckResult")
    @Override
    public void getLatestProducts(String appLang) {
        mHomeView.showLatestProductsLoading();
        ApiHelper.getLatestProducts(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestProductsResponse -> {
                    if (latestProductsResponse != null && latestProductsResponse.getLatestProductsList() != null) {
                        mHomeView.showLatestProducts(latestProductsResponse.getLatestProductsList());
                    } else {
                        mHomeView.hideLatestProducts();
                    }
                    mHomeView.hideLatestProductsLoading();
                }, throwable -> {
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mHomeView.onError(ErrorUtils.getErrors(anError));
                    }
                    mHomeView.hideLatestProductsLoading();
                    mHomeView.hideLatestProducts();
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getLatestCauses(String appLang) {
        mHomeView.showLatestCausesLoading();
        ApiHelper.getLatestCauses(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestCausesResponse -> {
                    if (latestCausesResponse != null && latestCausesResponse.getProductsList() != null) {
                        mHomeView.showLatestCauses(latestCausesResponse.getProductsList());
                    } else {
                        mHomeView.hideLatestCauses();
                    }
                    mHomeView.hideLatestCausesLoading();
                }, throwable -> {
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mHomeView.onError(ErrorUtils.getErrors(anError));
                    }
                    mHomeView.hideLatestCausesLoading();
                    mHomeView.hideLatestCauses();
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getUrgentCases(String appLang) {
        mHomeView.showUrgentCasesLoading();
        ApiHelper.getUrgentCases(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(urgentCasesResponse -> {
                    if (urgentCasesResponse != null && urgentCasesResponse.getUrgentCases() != null) {
                        mHomeView.showUrgentCases(urgentCasesResponse.getUrgentCases());
                    } else {
                        mHomeView.hideUrgentCases();
                    }
                    mHomeView.hideUrgentCasesLoading();
                }, throwable -> {
                    mHomeView.hideUrgentCasesLoading();
                    mHomeView.hideUrgentCases();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mHomeView.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }

     @SuppressLint("CheckResult")
     @Override
    public void getHomeNews(String appLang) {
        mHomeView.showHomeNewsLoading();
        ApiHelper.getHomeNews(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(AllHomeNewsResponse -> {
                    if (AllHomeNewsResponse != null && AllHomeNewsResponse.getNewsList() != null) {
                        mHomeView.showHomeNews(AllHomeNewsResponse.getNewsList());
                    } else {
                        mHomeView.hideHomeNews();
                    }
                    mHomeView.hideHomeNewsLoading();
                }, throwable -> {
                    mHomeView.hideHomeNewsLoading();
                    mHomeView.hideHomeNews();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mHomeView.onError(ErrorUtils.getErrors(anError));
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
        ShareManager.share(url, mContext);
    }
}
