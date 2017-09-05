package com.spade.mek.ui.more.news.view;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.home.search.model.NewsSearchResponse;
import com.spade.mek.ui.more.news.model.AllNewsResponse;

/**
 * Created by Ayman Abouzeid on 7/11/17.
 */

public interface NewsView extends BaseView {

    void showNews(AllNewsResponse allNewsResponse);

    void showSearchResults(NewsSearchResponse newsSearchResponse);

    void showLoading();

    void hideLoading();
}
