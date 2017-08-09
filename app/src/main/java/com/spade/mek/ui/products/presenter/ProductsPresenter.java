package com.spade.mek.ui.products.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.products.view.ProductsView;

import org.json.JSONObject;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public interface ProductsPresenter extends BasePresenter<ProductsView> {

    void getAllProducts(String lang, int page);

    void getUrgentCases(String lang);

    void filterProducts(String lang, JSONObject jsonObject);
}
