package com.spade.mek.ui.products.presenter;

import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.products.view.ProductDetailsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public class ProductDetailsPresenterImpl implements ProductDetailsPresenter {

    private ProductDetailsView productDetailsView;

    @Override
    public void setView(ProductDetailsView view) {
        productDetailsView = view;

    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void getProductDetails(String appLang, int productId) {
        productDetailsView.showLoading();
        ApiHelper.getProductDetails(appLang, productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productDetailsResponse -> {
                    productDetailsView.hideLoading();
                    if (productDetailsResponse != null && productDetailsResponse.getProduct() != null) {
                        productDetailsView.updateUI(productDetailsResponse.getProduct());
                    }
                }, throwable -> {
                    productDetailsView.hideLoading();
                    if (throwable != null) {
                        productDetailsView.onError(throwable.getMessage());
                    }
                });
    }
}
