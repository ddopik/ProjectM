package com.spade.mek.ui.more.donation_channels.view;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.more.donation_channels.model.Area;
import com.spade.mek.ui.more.donation_channels.model.City;
import com.spade.mek.ui.more.donation_channels.model.Store;
import com.spade.mek.ui.more.donation_channels.presenter.DonationStoresPresenter;
import com.spade.mek.ui.more.donation_channels.presenter.DonationStoresPresenterImpl;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public class StoresFragment extends BaseFragment implements DonationStoresView, StoresAdapter.StoreActions {
    private DonationStoresPresenter donationStoresPresenter;
    private View storesView;
    private StoresAdapter storesAdapter;
    private CitiesSpinnerAdapter citiesSpinnerAdapter;
    private AreasSpinnerAdapter areasSpinnerAdapter;
    private List<Store> storeList, allStores;
    private List<City> cityList;
    private List<Area> areaList;
    private ProgressBar progressBar;
    private LinearLayout filterLayout;
    private Location userLocation;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        storesView = inflater.inflate(R.layout.fragment_stores, container, false);
        initViews();
        overrideFonts(getContext(), storesView);
        return storesView;
    }

    @Override
    protected void initPresenter() {
        donationStoresPresenter = new DonationStoresPresenterImpl(getContext());
        donationStoresPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());

        storeList = new ArrayList<>();
        allStores = new ArrayList<>();
        cityList = new ArrayList<>();
        areaList = new ArrayList<>();

        filterLayout = (LinearLayout) storesView.findViewById(R.id.filter_layout);
        RecyclerView storesRecycler = (RecyclerView) storesView.findViewById(R.id.stores_recycler_view);
        AppCompatSpinner citiesSpinner = (AppCompatSpinner) storesView.findViewById(R.id.cities_spinner);
        AppCompatSpinner areasSpinner = (AppCompatSpinner) storesView.findViewById(R.id.areas_spinner);

        citiesSpinnerAdapter = new CitiesSpinnerAdapter(cityList, getContext());
        citiesSpinner.setAdapter(citiesSpinnerAdapter);

        areasSpinnerAdapter = new AreasSpinnerAdapter(areaList, getContext());
        areasSpinner.setAdapter(areasSpinnerAdapter);

        citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) parent.getItemAtPosition(position);
                updateAreas(city.getAreaList());
                if (city.getCityId() != -1) {
                    new GetFilteredStoresByCity().execute(city.getCityId());
                } else {
                    updateStores(allStores);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        areasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Area area = (Area) parent.getItemAtPosition(position);
                if (position != 0) {
                    new GetFilteredStoresByArea().execute(area.getAreaName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        progressBar = (ProgressBar) storesView.findViewById(R.id.progress_bar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        storesRecycler.setLayoutManager(linearLayoutManager);
        storesAdapter = new StoresAdapter(storeList, getContext());
        storesAdapter.setStoreActions(this);
        storesRecycler.setAdapter(storesAdapter);
        donationStoresPresenter.getDonationStores(appLang);
        updateAreas(areaList);
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
        this.allStores.addAll(storeList);
        storesAdapter.notifyDataSetChanged();
        donationStoresPresenter.requestLocationPermission(getActivity());
//        sortStores();
    }

    @Override
    public void showCitiesAndAreas(List<City> cities) {
        City city = new City();
        city.setCityId(-1);
        city.setCityName(getString(R.string.city));
        this.cityList.add(city);
        this.cityList.addAll(cities);
        citiesSpinnerAdapter.notifyDataSetChanged();
        if (!cities.isEmpty())
            filterLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUserLocation(Location userLocation) {
        this.userLocation = userLocation;
        sortStores();
    }

    @Override
    public void showSortedList(List<Store> stores) {
        this.storeList.clear();
        this.storeList.addAll(stores);
        storesAdapter.notifyDataSetChanged();
    }

    private void sortStores() {
        if (userLocation != null && storeList != null && !storeList.isEmpty()) {
            donationStoresPresenter.sortStoresAscending(storeList, userLocation);
        }
    }

    private void updateStores(List<Store> filteredStores) {
        this.storeList.clear();
        this.storeList.addAll(filteredStores);
        storesAdapter.notifyDataSetChanged();
        sortStores();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void updateAreas(List<Area> areaList) {
        Area area = new Area();
        area.setAreaName(getString(R.string.area));

        this.areaList.clear();
        this.areaList.add(area);
        if (areaList != null && !areaList.isEmpty())
            this.areaList.addAll(areaList);
        areasSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckDirectionClicked(String lat, String lng) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + lat + "," + lng + "(" + getString(R.string.app_name) + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private class GetFilteredStoresByCity extends AsyncTask<Integer, List<Store>, List<Store>> {
        @Override
        protected List<Store> doInBackground(Integer... params) {
            int cityId = params[0];
            List<Store> filteredStores = new ArrayList<>();
            for (Store store : allStores) {
                if (store.getCityId() == cityId) {
                    filteredStores.add(store);
                }
            }
            return filteredStores;
        }

        @Override
        protected void onPostExecute(List<Store> filteredList) {
            super.onPostExecute(filteredList);
            updateStores(filteredList);
        }
    }

    private class GetFilteredStoresByArea extends AsyncTask<String, List<Store>, List<Store>> {
        @Override
        protected List<Store> doInBackground(String... params) {
            String area = params[0];
            List<Store> filteredStores = new ArrayList<>();
            for (Store store : allStores) {
                if (store.getArea().equals(area)) {
                    filteredStores.add(store);
                }
            }
            return filteredStores;
        }

        @Override
        protected void onPostExecute(List<Store> filteredList) {
            super.onPostExecute(filteredList);
            updateStores(filteredList);
        }
    }

}
