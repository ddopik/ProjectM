package com.spade.mek.ui.products;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.home.urgent_cases.UrgentCase;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public interface ProductsView extends BaseView {

    void showUrgentCases(List<UrgentCase> urgentCaseList);

    void showAllProducts(AllProductsResponse allProductsResponse);

    void showUrgentCasesLoading();

    void hideUrgentCasesLoading();

    void showProductsLoading();

    void hideProductsLoading();

}
