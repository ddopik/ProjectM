package com.spade.mek.ui.home;

import android.view.View;

import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.home.causes.LatestCauses;
import com.spade.mek.ui.home.products.LatestProducts;
import com.spade.mek.utils.PrefUtils;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class HomeFragment extends BaseFragment implements HomeView {
    private HomePresenter homePresenter;
    private View homeView;

    @Override
    protected void initPresenter() {
        homePresenter = new HomePresenterImpl(this, getContext());
    }

    @Override
    protected void initViews() {
        homePresenter.getLatestProducts(PrefUtils.getAppLang(getContext()));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public void hideMessage() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showAlertDialog(String title, String message) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onError(int resID) {

    }

    @Override
    public void showLatestProducts(List<LatestProducts> latestProductsList) {

    }

    @Override
    public void showLatestCauses(List<LatestCauses> latestCausesList) {

    }
}
