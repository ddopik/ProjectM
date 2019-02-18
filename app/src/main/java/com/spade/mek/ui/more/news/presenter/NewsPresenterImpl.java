package com.spade.mek.ui.more.news.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.news.view.NewsView;
import com.spade.mek.utils.ErrorUtils;
import com.spade.mek.utils.PrefUtils;
import com.spade.mek.utils.ShareManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 7/11/17.
 */

public class NewsPresenterImpl implements NewsPresenter {
    private NewsView newsView;
    private Context mContext;

    public NewsPresenterImpl(Context mContext) {
        this.mContext = mContext;
        Tracker newsTracker = MekApplication.getDefaultTracker();
        newsTracker.setScreenName(mContext.getString(R.string.news_screen));
        newsTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void setView(NewsView view) {
        newsView = view;
    }

    @Override
    public void shareItem(String url) {
        ShareManager.share(url, mContext);
    }

    @Override
    public void getNews(String appLang, int pageNumber) {
        newsView.showLoading();
        ApiHelper.getAllNews(appLang, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allNewsResponse -> {
                    newsView.hideLoading();
                    if (allNewsResponse != null) {
                        newsView.showNews(allNewsResponse);
                    } else {
                        newsView.onError(R.string.something_wrong);
                    }
                }, throwable -> {
                    newsView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        newsView.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }

    @Override
    public void search(String searchKeyWord) {
        newsView.showLoading();
        ApiHelper.searchNews(searchKeyWord, PrefUtils.getAppLang(mContext), "news")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsSearchResponse -> {
                    newsView.hideLoading();
                    if (newsSearchResponse != null) {
                        newsView.showSearchResults(newsSearchResponse);
                    } else {
                        newsView.onError(R.string.something_wrong);
                    }
                }, throwable -> {
                    newsView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        newsView.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }
}
