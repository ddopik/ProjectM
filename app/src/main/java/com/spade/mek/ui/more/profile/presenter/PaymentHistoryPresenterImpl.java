package com.spade.mek.ui.more.profile.presenter;

import com.androidnetworking.error.ANError;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.profile.view.ProfilePaymentView;
import com.spade.mek.utils.ErrorUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 8/29/17.
 */

public class PaymentHistoryPresenterImpl implements PaymentPresenter {
    private ProfilePaymentView profilePaymentView;

    @Override
    public void setView(ProfilePaymentView view) {
        profilePaymentView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void getPaymentHistory(String appLang, String token) {
        profilePaymentView.showLoading();
        ApiHelper.getPaymentHistory(appLang, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(paymentHistoryResponse -> {
                    profilePaymentView.hideLoading();
                    if (paymentHistoryResponse.getPaymentHistoryData().getPaymentList() != null) {
                        profilePaymentView.showPaymentData(paymentHistoryResponse.getPaymentHistoryData().getPaymentList());
                    }
                }, throwable -> {
                    profilePaymentView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        profilePaymentView.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }
}
