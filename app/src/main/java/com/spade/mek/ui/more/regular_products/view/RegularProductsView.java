package com.spade.mek.ui.more.regular_products.view;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.products.model.ProductsData;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public interface RegularProductsView extends BaseView {

    void showRegularProducts(ProductsData productsData);

    void showRegularProducts(List<Products> productsList);

    void showProductsLoading();

    void hideProductsLoading();

    void unSubscribeSuccess();

}
