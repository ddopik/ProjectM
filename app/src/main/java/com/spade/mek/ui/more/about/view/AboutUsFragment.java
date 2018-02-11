package com.spade.mek.ui.more.about.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.more.about.model.AboutUs;
import com.spade.mek.ui.more.about.presenter.AboutUsPresenter;
import com.spade.mek.ui.more.contact_us.model.ContactUsInfo;

import java.util.List;

/**
 * Created by abdalla-maged on 2/11/18.
 */

public class AboutUsFragment extends BaseFragment implements AboutUsView {

    private AboutUsPresenter aboutUsPresenter;
    private View aboutUsView;
    private List<AboutUs> aboutUsList;
    private ProgressBar progressBar;
    private ScrollView mainScrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aboutUsView = inflater.inflate(R.layout.fragment_about_us, container, false);
        initViews();
        overrideFonts(getContext(), aboutUsView);
        return aboutUsView;
    }

    @Override
    protected void initViews() {

//        progressBar= aboutUsView.findViewById(R.id.main_progress_bar);

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showAboutUsData(ContactUsInfo contactUsInfo) {

    }


}
