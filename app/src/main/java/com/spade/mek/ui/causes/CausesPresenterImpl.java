package com.spade.mek.ui.causes;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.utils.ErrorUtils;
import com.spade.mek.utils.PrefUtils;
import com.spade.mek.utils.ShareManager;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class CausesPresenterImpl implements CausesPresenter {

    private Context mContext;
    private CausesView mCausesView;

    public CausesPresenterImpl(Context context) {
        this.mContext = context;

    }

    @Override
    public void setView(CausesView view) {
        mCausesView = view;
    }

    @Override
    public void shareItem(String url) {
        ShareManager.share(url, mContext);
    }

    @Override
    public void getAllCauses(String lang, int page) {
        mCausesView.showCausesLoading();
        ApiHelper.getAllCauses(lang, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allCausesResponse -> {
                    if (allCausesResponse != null) {
                        mCausesView.showAllCauses(allCausesResponse);
                    }
                    mCausesView.hideCausesLoading();
                }, throwable -> {
                    mCausesView.hideCausesLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mCausesView.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }

    @Override
    public void getUrgentCases(String lang) {
        mCausesView.showUrgentCasesLoading();
        ApiHelper.getUrgentCases(lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(urgentCasesResponse -> {
                    if (urgentCasesResponse != null && urgentCasesResponse.getUrgentCases() != null) {
                        mCausesView.showUrgentCases(urgentCasesResponse.getUrgentCases());
                    }
                    mCausesView.hideUrgentCasesLoading();
                }, throwable -> {
                    mCausesView.hideUrgentCasesLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mCausesView.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }

    @Override
    public void filterCauses(String lang, JSONObject jsonObject) {
        mCausesView.showCausesLoading();
        ApiHelper.filterCauses(jsonObject, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allCausesResponse -> {
                    if (allCausesResponse != null) {
                        mCausesView.showAFilteredCauses(allCausesResponse);
                    }
                    mCausesView.hideCausesLoading();
                }, throwable -> {
                    mCausesView.hideCausesLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mCausesView.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }

    @Override
    public void search(String searchKeyWord) {
        mCausesView.showCausesLoading();
        ApiHelper.search(searchKeyWord, PrefUtils.getAppLang(mContext), UrgentCasesPagerAdapter.CAUSE_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResponse -> {
                    mCausesView.hideCausesLoading();
                    mCausesView.showSearchResults(searchResponse);
                }, throwable -> {
                    mCausesView.hideCausesLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mCausesView.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }
}
