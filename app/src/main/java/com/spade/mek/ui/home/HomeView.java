package com.spade.mek.ui.home;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.more.news.model.News;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public interface HomeView extends BaseView {

    void showLatestProducts(List<Products> latestProductsList);

    void showLatestCauses(List<Products> latestCausesList);

    void showUrgentCases(List<Products> urgentCaseList);
    // TODO: 1/29/18 A_M [new Task]
    void showHomeNews(List<News> homeNews);

    void showUrgentCasesLoading();

    void showLatestProductsLoading();

    void showLatestCausesLoading();
    // TODO: 1/29/18 A_M [new Task]
    void showHomeNewsLoading();

    void hideUrgentCasesLoading();

    void hideLatestProductsLoading();

    void hideLatestCausesLoading();
    // TODO: 1/29/18 A_M [new Task]
    void hideHomeNewsLoading();

    void hideLatestProducts();

    void hideLatestCauses();

    void hideUrgentCases();
    // TODO: 1/29/18 A_M [new Task]
    void hideHomeNews();

    void showLatestProducts();

    void showLatestCauses();

    void showUrgentCases();
    // TODO: 1/29/18 A_M [new Task]
    void showHomeNews();
}
