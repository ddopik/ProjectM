package com.spade.mek.ui.more.about.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.base.BaseView;
import com.spade.mek.ui.more.about.model.AboutUsDataResponse;
import com.spade.mek.ui.more.about.model.AboutUsProjects;
import com.spade.mek.ui.more.about.presenter.AboutProgramPresenter;
import com.spade.mek.ui.more.about.presenter.AboutProgramPresenterImpl;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla-maged on 2/12/18.
 */

public class AboutProgramFragment extends BaseFragment implements AboutProgramView, BaseView {

    private View mainView;
    private AboutProgramPresenter aboutProgram_Presenter;
    private AboutProjectsAdapter aboutProjectsAdapter;
    private ProgressBar progressBar;
    private List<AboutUsProjects> aboutUsProjectItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.about_project_fragment, container, false);
        initViews();
        return mainView;
    }


    @Override
    protected void initPresenter() {
        aboutProgram_Presenter = new AboutProgramPresenterImpl(this);
    }


    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());

        progressBar = mainView.findViewById(R.id.progress_bar);
        RecyclerView AboutItemsRecyclerView = mainView.findViewById(R.id.programs_projects_about_us_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        aboutUsProjectItems = new ArrayList<AboutUsProjects>();

        aboutProjectsAdapter = new AboutProjectsAdapter(getContext(), aboutUsProjectItems, ImageUtils.getDefaultImage(appLang), LinearLayout.VERTICAL);
        AboutItemsRecyclerView.setLayoutManager(layoutManager);
        AboutItemsRecyclerView.setAdapter(aboutProjectsAdapter);

        aboutProgram_Presenter.getAllProjectAndPrograms(appLang);
    }

    @Override
    public void showProjectAndPrograms(AboutUsDataResponse aboutUsDataResponse) {

        if (aboutUsDataResponse.getData().size() != 0) {
            aboutUsProjectItems.addAll(aboutUsDataResponse.getData().get(0).getProjectProjects());
            aboutProjectsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void hideProjectAndPrograms() {

    }

    @Override
    public void showProjectAndProgramsLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProjectAndProgramsLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int resID) {
        Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();
    }


}
