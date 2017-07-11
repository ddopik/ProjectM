package com.spade.mek.news.presenter;

import android.content.Context;

import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.news.view.NewsView;
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
                    newsView.onError(throwable.getLocalizedMessage());
                });
    }
}
