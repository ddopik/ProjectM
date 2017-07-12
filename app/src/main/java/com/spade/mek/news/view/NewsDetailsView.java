package com.spade.mek.news.view;

import com.spade.mek.base.BaseView;
import com.spade.mek.news.model.News;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public interface NewsDetailsView extends BaseView {

    void showLoading();

    void hideLoading();

    void updateUI(News news);

    void showRelatedNews(List<News> relatedNews);
}
