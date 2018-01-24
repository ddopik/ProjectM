package com.spade.mek.ui.more.news.presenter;

import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.more.news.view.NewsDetailsView;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public interface NewsDetailsPresenter extends BasePresenter<NewsDetailsView> {
    void getNewsDetails(String appLang, int newsId);

    void getRelatedNews(String appLang, int newsId);

    boolean isReverse(String appLang);

}
