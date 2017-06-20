package com.spade.mek.ui.products.view;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.products.model.AllProductsResponse;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public interface ProductsView extends BaseView {

    void showUrgentCases(List<Products> urgentCaseList);

    void showAllProducts(AllProductsResponse allProductsResponse);

    void showUrgentCasesLoading();

    void hideUrgentCasesLoading();

    void showProductsLoading();

    void hideProductsLoading();

}
