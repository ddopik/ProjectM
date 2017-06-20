package com.spade.mek.ui.products.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.products.view.ProductDetailsView;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public interface ProductDetailsPresenter extends BasePresenter<ProductDetailsView> {
    void getProductDetails(String appLang, int productId);
}
