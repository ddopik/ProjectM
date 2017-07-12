package com.spade.mek.ui.causes;

import android.content.Context;

import com.spade.mek.network.ApiHelper;
import com.spade.mek.utils.ShareManager;

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
                        mCausesView.onError(throwable.getMessage());
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
                    if (urgentCasesResponse != null && urgentCasesResponse.getUrgentCasesData().getProductsList() != null) {
                        mCausesView.showUrgentCases(urgentCasesResponse.getUrgentCasesData().getProductsList());
                    }
                    mCausesView.hideUrgentCasesLoading();
                }, throwable -> {
                    mCausesView.hideUrgentCasesLoading();
                    if (throwable != null) {
                        mCausesView.onError(throwable.getMessage());
                    }
                });
    }
}
