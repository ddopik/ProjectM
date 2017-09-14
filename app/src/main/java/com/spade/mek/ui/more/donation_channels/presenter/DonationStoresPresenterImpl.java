package com.spade.mek.ui.more.donation_channels.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.androidnetworking.error.ANError;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.donation_channels.model.Store;
import com.spade.mek.ui.more.donation_channels.view.DonationStoresView;
import com.spade.mek.utils.ErrorUtils;
import com.spade.mek.utils.LocationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public class DonationStoresPresenterImpl implements DonationStoresPresenter, ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    private DonationStoresView donationStoresView;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Activity activity;
    private Context context;
    private Location userLocation;


    public DonationStoresPresenterImpl(Context context) {
        this.context = context;
        Tracker storesTracker = MekApplication.getDefaultTracker();
        storesTracker.setScreenName(context.getString(R.string.stores_screen));
        storesTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void setView(DonationStoresView view) {
        donationStoresView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void getDonationStores(String appLang) {
        donationStoresView.showLoading();
        ApiHelper.getStores(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(storesResponse -> {
                    donationStoresView.hideLoading();
                    if (storesResponse != null && storesResponse.getStoresData() != null && storesResponse.getStoresData().getStoresList() != null) {
                        donationStoresView.showStores(storesResponse.getStoresData().getStoresList());
                    } else {
                        donationStoresView.onError(R.string.something_wrong);
                    }

                    if (storesResponse != null) {
                        donationStoresView.showCitiesAndAreas(storesResponse.getStoresData().getCities());
                    }
                }, throwable -> {
                    donationStoresView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        donationStoresView.onError(ErrorUtils.getErrors(anError.getErrorBody()));
                    }
                });
    }

    @Override
    public void sortStoresAscending(List<Store> storeList, Location userLocation) {
        this.userLocation = userLocation;
        new SortStoresTask().execute(storeList);
    }

    @Override
    public void requestLocationPermission(Activity activity) {
        this.activity = activity;
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            initGoogleApiClient();
        }
    }


    @SuppressWarnings("MissingPermission")
    private void requestLocationUpdates() {
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(mGoogleApiClient, mLocationRequest,
                            this)
                    .setResultCallback(status -> mGoogleApiClient.disconnect());
        }
    }

    private void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initGoogleApiClient();
                }
                break;
        }
    }


    public void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build()).
                setResultCallback(locationSettingsResult -> {
                    try {
                        locationSettingsResult.getStatus().startResolutionForResult(activity,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        checkLocationSettings();
        requestLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        donationStoresView.setUserLocation(location);
    }

    private class SortStoresTask extends AsyncTask<List<Store>, Void, List<Store>> {

        @Override
        protected List<Store> doInBackground(List<Store>... params) {
            List<Store> storeList = params[0];
            Map<Float, Store> storesDistance = new HashMap<>();

            for (int i = 0; i < storeList.size(); i++) {
                Store store = storeList.get(i);
                float distance =
                        LocationUtils.calculateDistance(Double.parseDouble(store.getLat()), Double.parseDouble(store.getLng()),
                                userLocation.getLatitude(), userLocation.getLongitude());
                storesDistance.put(distance, store);
            }

            List<Float> distanceKeySet = new ArrayList<>();
            distanceKeySet.addAll(storesDistance.keySet());
            Collections.sort(distanceKeySet);

            List<Store> storeListSorted = new ArrayList<>();
            for (float distance : distanceKeySet) {
                storeListSorted.add(storesDistance.get(distance));
            }
            return storeListSorted;
        }

        @Override
        protected void onPostExecute(List<Store> storeList) {
            super.onPostExecute(storeList);
            donationStoresView.showSortedList(storeList);
        }
    }

}
