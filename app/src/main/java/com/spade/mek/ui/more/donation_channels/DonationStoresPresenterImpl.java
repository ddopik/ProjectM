package com.spade.mek.ui.more.donation_channels;

import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public class DonationStoresPresenterImpl implements DonationStoresPresenter {

    private DonationStoresView donationStoresView;

    @Override
    public void setView(DonationStoresView view) {
        donationStoresView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void getDonationStores(String appLang) {
        donationStoresView.showLoading();
        ApiHelper.getStores(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(storesResponse -> {
                    donationStoresView.hideLoading();
                    if (storesResponse != null && storesResponse.getStoresData() != null && storesResponse.getStoresData().getStoresList() != null) {
                        donationStoresView.showStores(storesResponse.getStoresData().getStoresList());
                    } else {
                        donationStoresView.onError(R.string.something_wrong);
                    }
                }, throwable -> {
                    donationStoresView.hideLoading();
                    donationStoresView.onError(throwable.getLocalizedMessage());
                });
    }
}
