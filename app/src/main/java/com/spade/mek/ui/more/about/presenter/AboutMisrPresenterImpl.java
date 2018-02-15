package com.spade.mek.ui.more.about.presenter;

import com.androidnetworking.error.ANError;
import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.about.view.AboutMisrFragment;
import com.spade.mek.ui.more.about.view.AboutMisrView;
import com.spade.mek.utils.ErrorUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla-maged on 2/12/18.
 */

public class AboutMisrPresenterImpl implements AboutMisrPresenter {

    AboutMisrView aboutMisrView;

    public AboutMisrPresenterImpl(AboutMisrFragment aboutMisrFragmentFragmentView) {
        this.aboutMisrView = aboutMisrFragmentFragmentView;
    }

    @Override
    public void getAllAboutUsMisrData(String appLang) {
        aboutMisrView.showAboutMisrLoading();
        ApiHelper.getProjectsAboutUsData(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aboutUsDataResponse -> {
                    aboutMisrView.hideAboutMisrLoading();
                    // -->about misr body always return at first index
                    if (aboutUsDataResponse.getData().get(0).getText() != null) {
                        aboutMisrView.showAboutMisr(aboutUsDataResponse.getData().get(0).getText());
                    } else {
                        aboutMisrView.onError(R.string.something_wrong);
                    }
                }, throwable -> {
                    aboutMisrView.hideAboutMisrLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        aboutMisrView.onError(ErrorUtils.getErrors(anError));
                    }
                });

    }
}
