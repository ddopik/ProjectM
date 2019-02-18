package com.spade.mek.ui.more.donation_channels.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.more.donation_channels.model.Bank;
import com.spade.mek.ui.more.donation_channels.presenter.DonationBanksPresenter;
import com.spade.mek.ui.more.donation_channels.presenter.DonationBanksPresenterImpl;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public class BanksFragment extends BaseFragment implements DonationBanksView {

    private DonationBanksPresenter donationBanksPresenter;
    private View banksView;
    private BanksAdapter banksAdapter;
    private AppCompatSpinner banksNamesFilterSpinner;
    private List<Bank> bankList, allBanks;
    private List<String> banksNames = new ArrayList<>();
    private ProgressBar progressBar;
    private RelativeLayout filterLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tracker banksTracker = MekApplication.getDefaultTracker();
        banksTracker.setScreenName(getString(R.string.banks_screen));
        banksTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        banksView = inflater.inflate(R.layout.fragment_bank, container, false);
        initViews();
        overrideFonts(getContext(), banksView);
        return banksView;
    }

    @Override
    protected void initPresenter() {
        donationBanksPresenter = new DonationBanksPresenterImpl();
        donationBanksPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());

        bankList = new ArrayList<>();
        allBanks = new ArrayList<>();

        RecyclerView banksRecycler = banksView.findViewById(R.id.banks_recycler_view);
        banksNamesFilterSpinner = banksView.findViewById(R.id.banks_spinner);
        filterLayout = banksView.findViewById(R.id.filter_layout);

        banksNamesFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    new GetFilteredBanks().execute(banksNames.get(position));
                } else {
                    updateBanks(allBanks);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        progressBar = (ProgressBar) banksView.findViewById(R.id.progress_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        banksRecycler.setLayoutManager(linearLayoutManager);
        banksAdapter = new BanksAdapter(bankList, getContext());
        banksRecycler.setAdapter(banksAdapter);
        donationBanksPresenter.getDonationBanks(appLang);
//        updateAreas(areaList);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int resID) {
        Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();
    }

    private void updateBanks(List<Bank> filteredBanks) {
        this.bankList.clear();
        this.bankList.addAll(filteredBanks);
        banksAdapter.notifyDataSetChanged();
    }

    @Override
    public void showBanks(List<Bank> bankList) {
        this.bankList.addAll(bankList);
        this.allBanks.addAll(bankList);
        banksAdapter.notifyDataSetChanged();
        if (allBanks != null && !allBanks.isEmpty()) {
            new CreateFiltersTask().execute();
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void createFilters(List<String> banksNames) {
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.type_of_donation_item, banksNames);
        banksNamesFilterSpinner.setAdapter(spinnerAdapter);
        filterLayout.setVisibility(View.VISIBLE);
    }

    private class CreateFiltersTask extends AsyncTask<Void, List<String>, List<String>> {
        @Override
        protected List<String> doInBackground(Void... params) {
            banksNames.add(getString(R.string.view_all_banks));
            for (Bank bank : allBanks) {
                banksNames.add(bank.getBankName());
            }
            return banksNames;
        }

        @Override
        protected void onPostExecute(List<String> bankNames) {
            super.onPostExecute(bankNames);
            createFilters(bankNames);
        }
    }

    private class GetFilteredBanks extends AsyncTask<String, List<Bank>, List<Bank>> {
        @Override
        protected List<Bank> doInBackground(String... params) {
            String bankName = params[0];
            List<Bank> filteredBanks = new ArrayList<>();
            for (Bank bank : allBanks) {
                if (bank.getBankName().equals(bankName)) {
                    filteredBanks.add(bank);
                }
            }
            return filteredBanks;
        }

        @Override
        protected void onPostExecute(List<Bank> filteredBanks) {
            super.onPostExecute(filteredBanks);
            updateBanks(filteredBanks);
        }
    }

}
