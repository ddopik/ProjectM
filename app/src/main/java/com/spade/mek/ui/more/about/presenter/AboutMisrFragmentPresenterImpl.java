package com.spade.mek.ui.more.about.presenter;

import com.androidnetworking.error.ANError;
import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.about.view.tabs.AboutMisrFragment;
import com.spade.mek.ui.more.about.view.tabs.AboutMisrFragmentView;
import com.spade.mek.utils.ErrorUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla-maged on 2/12/18.
 */

public class AboutMisrFragmentPresenterImpl implements AboutMisrFragmentPresenter {

    AboutMisrFragmentView aboutMisrFragmentView;

    public AboutMisrFragmentPresenterImpl(AboutMisrFragment aboutMisrFragmentView) {
        this.aboutMisrFragmentView = aboutMisrFragmentView;
    }

    @Override
    public void getAllAboutUsMisrData(String appLang) {
        aboutMisrFragmentView.showAboutMisrLoading();
        ApiHelper.getProjectsAboutUsData(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aboutUsDataResponse -> {
                    aboutMisrFragmentView.hideAboutMisrLoading();
                    if (aboutUsDataResponse != null) {
                        aboutMisrFragmentView.showAboutMisr(aboutUsDataResponse);
                    } else {
                        aboutMisrFragmentView.onError(R.string.something_wrong);
                    }
                }, throwable -> {
                    aboutMisrFragmentView.hideAboutMisrLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        aboutMisrFragmentView.onError(ErrorUtils.getErrors(anError));
                    }
                });

    }
}
