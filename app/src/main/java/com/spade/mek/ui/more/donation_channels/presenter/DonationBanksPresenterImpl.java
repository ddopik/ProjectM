package com.spade.mek.ui.more.donation_channels.presenter;

import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.donation_channels.view.DonationBanksView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public class DonationBanksPresenterImpl implements DonationBanksPresenter {

    private DonationBanksView donationBanksView;

    @Override
    public void setView(DonationBanksView view) {
        donationBanksView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void getDonationBanks(String appLang) {
        donationBanksView.showLoading();
        ApiHelper.getBanks(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(banksResponse -> {
                    donationBanksView.hideLoading();
                    if (banksResponse != null && banksResponse.getBankList() != null) {
                        donationBanksView.showBanks(banksResponse.getBankList());
                    } else {
                        donationBanksView.onError(R.string.something_wrong);
                    }

//                    if (banksResponse != null) {
//                        donationBanksView.showBanks(banksResponse.getStoresData().getCities());
//                    }
                }, throwable -> {
                    donationBanksView.hideLoading();
                    donationBanksView.onError(throwable.getLocalizedMessage());
                });
    }
}
