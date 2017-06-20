package com.spade.mek.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.home.adapters.LatestCausesAdapter;
import com.spade.mek.ui.home.adapters.LatestProductsAdapter;
import com.spade.mek.ui.home.adapters.UrgentCasesPagerAdapter;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class HomeFragment extends BaseFragment implements HomeView {
    private HomePresenter homePresenter;
    private View homeView;
    private LatestCausesAdapter latestCausesAdapter;
    private LatestProductsAdapter latestProductsAdapter;
    //    private UrgentCasesAdapter urgentCasesAdapter;
    private UrgentCasesPagerAdapter urgentCasesPagerAdapter;
    private List<Products> latestCausesList;
    private List<Products> latestProductsList;
    private List<Products> urgentCaseList;
    private ProgressBar latestProductsProgress, latestCausesProgress, urgentCasesProgress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        return homeView;
    }

    @Override
    protected void initPresenter() {
        homePresenter = new HomePresenterImpl(this, getContext());
    }

    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());
        boolean isReverse = homePresenter.isReverse(appLang);
        int defaultImageResId = ImageUtils.getDefaultImage(appLang);

        urgentCasesProgress = (ProgressBar) homeView.findViewById(R.id.urgent_cases_progress_bar);
        latestProductsProgress = (ProgressBar) homeView.findViewById(R.id.latest_products_progress_bar);
        latestCausesProgress = (ProgressBar) homeView.findViewById(R.id.latest_causes_progress_bar);

//        RecyclerView urgentCasesRecycler = (RecyclerView) homeView.findViewById(R.id.urgent_cases_recycler_view);
        ViewPager urgentCasesViewPager = (ViewPager) homeView.findViewById(R.id.urgent_cases_view_pager);
        RecyclerView latestProductsRecycler = (RecyclerView) homeView.findViewById(R.id.latest_products_recycler_view);
        RecyclerView latestCausesRecycler = (RecyclerView) homeView.findViewById(R.id.latest_causes_recycler_view);

        RecyclerView.LayoutManager urgentCasesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, isReverse);
        RecyclerView.LayoutManager latestProductsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, isReverse);
        RecyclerView.LayoutManager latestCausesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, isReverse);

//        urgentCasesRecycler.setLayoutManager(urgentCasesLayoutManager);
        latestProductsRecycler.setLayoutManager(latestProductsLayoutManager);
        latestCausesRecycler.setLayoutManager(latestCausesLayoutManager);

        latestCausesList = new ArrayList<>();
        latestCausesAdapter = new LatestCausesAdapter(getContext(), latestCausesList, defaultImageResId);
        latestCausesRecycler.setAdapter(latestCausesAdapter);

        latestProductsList = new ArrayList<>();
        latestProductsAdapter = new LatestProductsAdapter(getContext(), latestProductsList, defaultImageResId);
        latestProductsRecycler.setAdapter(latestProductsAdapter);

        urgentCaseList = new ArrayList<>();
//        urgentCasesAdapter = new UrgentCasesAdapter(getContext(), urgentCaseList, defaultImageResId);
        urgentCasesPagerAdapter = new UrgentCasesPagerAdapter(getContext(), urgentCaseList, defaultImageResId);
        urgentCasesViewPager.setAdapter(urgentCasesPagerAdapter);
//        urgentCasesRecycler.setAdapter(urgentCasesAdapter);

        homePresenter.getLatestProducts(appLang);
        homePresenter.getUrgentCases(appLang);
        homePresenter.getLatestCauses(appLang);
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
    public void showLatestProducts(List<Products> latestProductsList) {
        this.latestProductsList.clear();
        this.latestProductsList.addAll(latestProductsList);
        latestProductsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLatestCauses(List<Products> latestCausesList) {
        this.latestCausesList.clear();
        this.latestCausesList.addAll(latestCausesList);
        latestCausesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showUrgentCases(List<Products> urgentCaseList) {
        this.urgentCaseList.clear();
        this.urgentCaseList.addAll(urgentCaseList);
//        urgentCasesAdapter.notifyDataSetChanged();
        urgentCasesPagerAdapter.notifyDataSetChanged();

    }

    @Override
    public void showUrgentCasesLoading() {
        urgentCasesProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLatestProductsLoading() {
        latestProductsProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLatestCausesLoading() {
        latestCausesProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUrgentCasesLoading() {
        urgentCasesProgress.setVisibility(View.GONE);
    }

    @Override
    public void hideLatestProductsLoading() {
        latestProductsProgress.setVisibility(View.GONE);
    }

    @Override
    public void hideLatestCausesLoading() {
        latestCausesProgress.setVisibility(View.GONE);
    }
}
