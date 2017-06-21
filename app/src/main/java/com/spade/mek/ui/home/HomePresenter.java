package com.spade.mek.ui.home;

import com.spade.mek.base.BasePresenter;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public interface HomePresenter extends BasePresenter<HomeView> {

    void getLatestProducts(String appLang);

    void getLatestCauses(String appLang);

    void getUrgentCases(String appLang);

    boolean isReverse(String appLang);

}
