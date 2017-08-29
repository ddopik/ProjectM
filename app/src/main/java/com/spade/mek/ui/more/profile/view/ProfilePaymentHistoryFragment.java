package com.spade.mek.ui.more.profile.view;

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
import com.spade.mek.ui.more.profile.model.Payment;
import com.spade.mek.ui.more.profile.presenter.PaymentHistoryPresenterImpl;
import com.spade.mek.ui.more.profile.presenter.PaymentPresenter;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 8/29/17.
 */

public class ProfilePaymentHistoryFragment extends BaseFragment implements ProfilePaymentView {

    private View paymentView;
    private PaymentProfileAdapter paymentProfileAdapter;
    private List<Payment> paymentList = new ArrayList<>();
    private ProgressBar progressBar;
    private PaymentPresenter paymentPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        paymentView = inflater.inflate(R.layout.fragment_profile_payment_history, container, false);
        initViews();
        return paymentView;
    }

    @Override
    protected void initPresenter() {
        paymentPresenter = new PaymentHistoryPresenterImpl();
        paymentPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        progressBar = (ProgressBar) paymentView.findViewById(R.id.progress_bar);
        RecyclerView paymentHistoryRecyclerView = (RecyclerView) paymentView.findViewById(R.id.payments_recycle);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        paymentHistoryRecyclerView.setLayoutManager(linearLayoutManager);
        paymentProfileAdapter = new PaymentProfileAdapter(getContext(), paymentList);
        paymentHistoryRecyclerView.setAdapter(paymentProfileAdapter);
        paymentPresenter.getPaymentHistory(PrefUtils.getAppLang(getContext()), PrefUtils.getUserToken(getContext()));
    }

    @Override
    public void onError(String message) {
        if (getContext() != null)
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int resID) {
        if (getContext() != null)
            Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPaymentData(List<Payment> payments) {
        this.paymentList.clear();
        this.paymentList.addAll(payments);
        paymentProfileAdapter.notifyDataSetChanged();
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
