package com.spade.mek.ui.home;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.home.products.Products;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public interface HomeView extends BaseView {

    void showLatestProducts(List<Products> latestProductsList);

    void showLatestCauses(List<Products> latestCausesList);

    void showUrgentCases(List<Products> urgentCaseList);

    void showUrgentCasesLoading();

    void showLatestProductsLoading();

    void showLatestCausesLoading();

    void hideUrgentCasesLoading();

    void hideLatestProductsLoading();

    void hideLatestCausesLoading();

    void hideLatestProducts();

    void hideLatestCauses();

    void hideUrgentCases();

    void showLatestProducts();

    void showLatestCauses();

    void showUrgentCases();
}
