package com.spade.mek.news.view;

import com.spade.mek.base.BaseView;
import com.spade.mek.news.model.AllNewsResponse;

/**
 * Created by Ayman Abouzeid on 7/11/17.
 */

public interface NewsView extends BaseView {

    void showNews(AllNewsResponse allNewsResponse);

    void showLoading();

    void hideLoading();
}
