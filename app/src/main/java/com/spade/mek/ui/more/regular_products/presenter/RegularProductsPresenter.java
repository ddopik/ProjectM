package com.spade.mek.ui.more.regular_products.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.more.regular_products.view.RegularProductsView;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public interface RegularProductsPresenter extends BasePresenter<RegularProductsView> {

    void getRegularProducts(String lang, int page);

}
