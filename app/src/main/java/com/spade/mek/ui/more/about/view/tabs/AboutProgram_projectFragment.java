package com.spade.mek.ui.more.about.view.tabs;

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
import com.spade.mek.ui.more.about.model.AboutUsProjectItem;
import com.spade.mek.ui.more.about.presenter.AboutProgram_projectFragPresenter;
import com.spade.mek.ui.more.about.presenter.AboutProgram_projectFragPresenterImpl;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla-maged on 2/12/18.
 */

public class AboutProgram_projectFragment extends BaseFragment implements AboutProgram_projectFragmentView, BaseView {

    private View mainView;
    private AboutProgram_projectFragPresenter aboutProgram_projectFragPresenter;
    private AboutUsProjectsAdapter aboutUsProjectsAdapter;
    private ProgressBar progressBar;
    private List<AboutUsProjectItem> aboutUsProjectItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.about_program_or_project_Fragment, container, false);
        initViews();
        return mainView;
    }


    @Override
    protected void initPresenter() {
        aboutProgram_projectFragPresenter = new AboutProgram_projectFragPresenterImpl(this);
    }


    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());

        progressBar = mainView.findViewById(R.id.progress_bar);
        RecyclerView AboutItemsRecyclerView = mainView.findViewById(R.id.programs_projects_about_us_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        aboutUsProjectItems = new ArrayList<AboutUsProjectItem>();

        aboutUsProjectsAdapter = new AboutUsProjectsAdapter(getContext(), aboutUsProjectItems, ImageUtils.getDefaultImage(appLang), LinearLayout.VERTICAL);
        AboutItemsRecyclerView.setLayoutManager(layoutManager);
        AboutItemsRecyclerView.setAdapter(aboutUsProjectsAdapter);

        aboutProgram_projectFragPresenter.getAllProjectAndPrograms(appLang); // todo ---> presenter call
    }

    @Override
    public void showProjectAndPrograms(AboutUsDataResponse aboutUsDataResponse) {

        if (aboutUsDataResponse.getAboutUs().getAboutUsProjectItemList() != null) {
            aboutUsProjectItems.addAll(aboutUsDataResponse.getAboutUs().getAboutUsProjectItemList());
            aboutUsProjectsAdapter.notifyDataSetChanged();
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
