package com.spade.mek.ui.more.about.view.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;

import com.spade.mek.ui.more.about.model.AboutUsDataResponse;
import com.spade.mek.ui.more.about.presenter.AboutMisrFragmentPresenter;
import com.spade.mek.ui.more.about.presenter.AboutMisrFragmentPresenterImpl;
import com.spade.mek.utils.PrefUtils;

import java.util.zip.Inflater;

/**
 * Created by abdalla-maged on 2/12/18.
 */


public class AboutMisrFragment extends BaseFragment implements AboutMisrFragmentView {

    private AboutMisrFragmentPresenter aboutMisrFragmentPresenter;
    private View mainView;
    private ProgressBar progressBar;
    private TextView misMainDialog;
    private RecyclerView misrRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainView = inflater.inflate(R.layout.about_misr_fragment_layout, container, false);
        initViews();
        return mainView;

    }

    @Override
    protected void initPresenter() {
        aboutMisrFragmentPresenter = new AboutMisrFragmentPresenterImpl(this);

    }

    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());
        misrRecyclerView = mainView.findViewById(R.id.misr_el_kheir_about_us_recycler_view);
        misMainDialog = mainView.findViewById(R.id.misr_el_kheir_main_dialog);
        progressBar = mainView.findViewById(R.id.progress_bar);

//        aboutMisrFragmentPresenter.getAllAboutUsMisrData(appLang);//todo --> presenter call start
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
    public void showAboutMisr(AboutUsDataResponse aboutUsDataResponse) {
        /// -->implemented by it's presenter to update UI Data based on how the shape of data
        //and view structure...
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
