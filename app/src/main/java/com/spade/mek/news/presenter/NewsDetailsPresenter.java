package com.spade.mek.news.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.news.view.NewsDetailsView;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public interface NewsDetailsPresenter extends BasePresenter<NewsDetailsView> {
    void getNewsDetails(String appLang, int newsId);

    void getRelatedNews(String appLang, int newsId);

    boolean isReverse(String appLang);

}
