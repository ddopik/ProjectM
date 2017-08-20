package com.spade.mek.ui.home;

import android.content.Context;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.network.ApiHelper;
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
        Tracker homeTracker = MekApplication.getDefaultTracker();
        homeTracker.setScreenName(mContext.getString(R.string.home_screen));
        homeTracker.send(new HitBuilders.ScreenViewBuilder().build());
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
                    } else {
                        mHomeView.hideLatestProducts();
                    }
                    mHomeView.hideLatestProductsLoading();
                }, throwable -> {
                    if (throwable != null) {
                        mHomeView.onError(throwable.getMessage());
                    }
                    mHomeView.hideLatestProductsLoading();
                    mHomeView.hideLatestProducts();
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
                    } else {
                        mHomeView.hideLatestCauses();
                    }
                    mHomeView.hideLatestCausesLoading();
                }, throwable -> {
                    if (throwable != null) {
                        mHomeView.onError(throwable.getMessage());
                    }
                    mHomeView.hideLatestCausesLoading();
                    mHomeView.hideLatestCauses();
                });
    }

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
        ShareManager.share(url, mContext);
    }
}
