package com.spade.mek.news.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.news.view.NewsView;

/**
 * Created by Ayman Abouzeid on 7/11/17.
 */

public interface NewsPresenter extends BasePresenter<NewsView> {
    void getNews(String appLang, int pageNumber);
}
