package com.spade.mek.ui.more.donation_channels;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public class StoresFragment extends BaseFragment implements DonationStoresView {
    private DonationStoresPresenter donationStoresPresenter;
    private View storesView;
    private StoresAdapter storesAdapter;
    private List<Store> storeList;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        storesView = inflater.inflate(R.layout.fragment_stores, container, false);
        initViews();
        return storesView;
    }

    @Override
    protected void initPresenter() {
        donationStoresPresenter = new DonationStoresPresenterImpl();
        donationStoresPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());
        storeList = new ArrayList<>();
        RecyclerView storesRecycler = (RecyclerView) storesView.findViewById(R.id.stores_recycler_view);
        progressBar = (ProgressBar) storesView.findViewById(R.id.progress_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        storesRecycler.setLayoutManager(linearLayoutManager);
        storesAdapter = new StoresAdapter(storeList, getContext());
        storesRecycler.setAdapter(storesAdapter);
        donationStoresPresenter.getDonationStores(appLang);
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
    public void showStores(List<Store> storeList) {
        this.storeList.addAll(storeList);
        storesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }


}
