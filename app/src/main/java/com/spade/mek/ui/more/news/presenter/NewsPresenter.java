package com.spade.mek.ui.more.news.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.more.news.view.NewsView;

/**
 * Created by Ayman Abouzeid on 7/11/17.
 */

public interface NewsPresenter extends BasePresenter<NewsView> {
    void getNews(String appLang, int pageNumber);

    void search(String searchKeyWord);
}
