package com.spade.mek.ui.more.contact_us.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
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
import com.spade.mek.ui.more.contact_us.view.ContactUsView;
import com.spade.mek.utils.ErrorUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class ContactUsPresenterImpl implements ContactUsPresenter,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private Context context;
    private ContactUsView contactUsView;
    private static final int REQUEST_CALL_PERMISSION = 0;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    private String phoneNumber;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Activity activity;

    public ContactUsPresenterImpl(Context context) {
        this.context = context;
        Tracker contactUsTracker = MekApplication.getDefaultTracker();
        contactUsTracker.setScreenName(context.getString(R.string.contact_us_screen));
        contactUsTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void setView(ContactUsView view) {
        this.contactUsView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void getContactData(String appLang) {
        contactUsView.showLoading();
        ApiHelper.getContactInfo(appLang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contactUsDataResponse -> {
                    if (contactUsDataResponse != null && contactUsDataResponse.isSuccess()) {
                        contactUsView.showData(contactUsDataResponse.getContactUsInfo());
                    }
                    contactUsView.hideLoading();
                }, throwable -> {
                    contactUsView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        contactUsView.onError(ErrorUtils.getErrors(anError));
                    }
                });
    }


    @Override
    public void callNumber(Activity activity, String phoneNumber) {
        this.phoneNumber = phoneNumber;
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PERMISSION);
        } else {
            call(phoneNumber);
        }

    }

    @SuppressWarnings("MissingPermission")
    private void call(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }


    @Override
    public void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
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
            case REQUEST_CALL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call(phoneNumber);
                }
                break;
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
    public float calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        Location locationA = new Location("point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lng1);
        Location locationB = new Location("point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lng2);
        return locationA.distanceTo(locationB);
    }

    @Override
    public void sendMail(String mailTo) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + mailTo));
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        contactUsView.setUserLocation(location);
    }

}
