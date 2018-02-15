package com.spade.mek.ui.more.about.presenter;

import com.androidnetworking.error.ANError;
import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.about.view.AboutProgramFragment;
import com.spade.mek.ui.more.about.view.AboutProgramView;
import com.spade.mek.utils.ErrorUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla-maged on 2/12/18.
 */

public class AboutProgramPresenterImpl implements AboutProgramPresenter {

    AboutProgramView aboutProgram_View;

    public AboutProgramPresenterImpl(AboutProgramFragment aboutProgram_Fragment)

    {
        this.aboutProgram_View = aboutProgram_Fragment;

    }

    @Override
    public void getAllProjectAndPrograms(String appLang) {
        aboutProgram_View.showProjectAndProgramsLoading();
        ApiHelper.getProjectsAboutUsData(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aboutUsDataResponse -> {
                    aboutProgram_View.hideProjectAndProgramsLoading();
                    if (aboutUsDataResponse != null) {
                        aboutProgram_View.showProjectAndPrograms(aboutUsDataResponse);
                    } else {
                        aboutProgram_View.onError(R.string.something_wrong);
                    }
                }, throwable -> {
                    aboutProgram_View.hideProjectAndProgramsLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        aboutProgram_View.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }
}
