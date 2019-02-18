package com.spade.mek.ui.more.about.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;

import com.spade.mek.ui.more.about.presenter.AboutMisrPresenter;
import com.spade.mek.ui.more.about.presenter.AboutMisrPresenterImpl;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by abdalla-maged on 2/12/18.
 */


public class AboutMisrFragment extends BaseFragment implements AboutMisrView {

    private AboutMisrPresenter aboutMisrPresenter;
    private View mainView;
    private ProgressBar progressBar;
    private TextView misMainDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainView = inflater.inflate(R.layout.about_misr_fragment_layout, container, false);
        initViews();
        return mainView;

    }

    @Override
    protected void initPresenter() {
        aboutMisrPresenter = new AboutMisrPresenterImpl(this);

    }

    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());
        misMainDialog = mainView.findViewById(R.id.misr_el_kheir_main_dialog);
        progressBar = mainView.findViewById(R.id.progress_bar);

        aboutMisrPresenter.getAllAboutUsMisrData(appLang);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int resID) {
        Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAboutMisr(String aboutUsDataResponse) {


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            misMainDialog.setText(Html.fromHtml(aboutUsDataResponse, Html.FROM_HTML_MODE_LEGACY));
        } else {
            misMainDialog.setText(Html.fromHtml(aboutUsDataResponse));
        }

    }

    @Override
    public void hideAboutMisr() {

    }

    @Override
    public void showAboutMisrLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAboutMisrLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
