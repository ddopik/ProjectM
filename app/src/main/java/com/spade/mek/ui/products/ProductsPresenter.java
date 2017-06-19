package com.spade.mek.ui.products;

import com.spade.mek.base.BasePresenter;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public interface ProductsPresenter extends BasePresenter<ProductsView> {

    void getAllProducts(String lang, int page);

    void getUrgentCases(String lang);
}
