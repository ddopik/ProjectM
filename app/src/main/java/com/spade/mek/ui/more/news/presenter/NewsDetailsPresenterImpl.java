package com.spade.mek.ui.more.news.presenter;

import android.content.Context;

import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.news.view.NewsDetailsView;
import com.spade.mek.utils.PrefUtils;
import com.spade.mek.utils.ShareManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public class NewsDetailsPresenterImpl implements NewsDetailsPresenter {

    private NewsDetailsView newsDetailsView;
    private Context mContext;

    public NewsDetailsPresenterImpl(Context context) {
        mContext = context;
    }

    @Override
    public void setView(NewsDetailsView view) {
        newsDetailsView = view;
    }

    @Override
    public void shareItem(String url) {
        ShareManager.share(url, mContext);
    }


    @Override
    public void getNewsDetails(String appLang, int newsId) {
        newsDetailsView.showLoading();
        ApiHelper.getNewsDetails(appLang, newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsDetailsResponse -> {
                    newsDetailsView.hideLoading();
                    if (newsDetailsResponse != null && newsDetailsResponse.getNews() != null) {
                        newsDetailsView.updateUI(newsDetailsResponse.getNews());
                    }
                }, throwable -> {
                    newsDetailsView.hideLoading();
                    if (throwable != null) {
                        newsDetailsView.onError(throwable.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void getRelatedNews(String appLang, int newsId) {
        ApiHelper.getRelatedNews(appLang, newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(relatedNewsResponse -> {
                    if (relatedNewsResponse != null && relatedNewsResponse.getRelatedNewsList() != null) {
                        newsDetailsView.showRelatedNews(relatedNewsResponse.getRelatedNewsList());
                    }
                }, throwable -> {
                    if (throwable != null) {
                        newsDetailsView.onError(throwable.getLocalizedMessage());
                    }
                });
    }

    @Override
    public boolean isReverse(String appLang) {
        return !appLang.equals(PrefUtils.ENGLISH_LANG);
    }
}
