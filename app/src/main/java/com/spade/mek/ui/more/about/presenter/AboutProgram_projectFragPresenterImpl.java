package com.spade.mek.ui.more.about.presenter;

import com.androidnetworking.error.ANError;
import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.about.view.tabs.AboutProgram_projectFragment;
import com.spade.mek.ui.more.about.view.tabs.AboutProgram_projectFragmentView;
import com.spade.mek.utils.ErrorUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla-maged on 2/12/18.
 */

public class AboutProgram_projectFragPresenterImpl implements AboutProgram_projectFragPresenter {

    AboutProgram_projectFragmentView aboutProgram_projectFragmentView;

    public AboutProgram_projectFragPresenterImpl(AboutProgram_projectFragment aboutProgram_projectFragment)

    {
        this.aboutProgram_projectFragmentView = aboutProgram_projectFragment;

    }

    @Override
    public void getAllProjectAndPrograms(String appLang) {
        aboutProgram_projectFragmentView.showProjectAndProgramsLoading();
        ApiHelper.getProjectsAboutUsData(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aboutUsDataResponse -> {
                    aboutProgram_projectFragmentView.hideProjectAndProgramsLoading();
                    if (aboutUsDataResponse != null) {
                        aboutProgram_projectFragmentView.showProjectAndPrograms(aboutUsDataResponse);
                    } else {
                        aboutProgram_projectFragmentView.onError(R.string.something_wrong);
                    }
                }, throwable -> {
                    aboutProgram_projectFragmentView.hideProjectAndProgramsLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        aboutProgram_projectFragmentView.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }
}
