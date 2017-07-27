package com.spade.mek.ui.more.contact_us.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.contact_us.view.ContactUsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class ContactUsPresenterImpl implements ContactUsPresenter, ActivityCompat.OnRequestPermissionsResultCallback {
    private Context context;
    private ContactUsView contactUsView;
    private static final int REQUEST_CALL_PERMISSION = 0;
    private String phoneNumber;

    public ContactUsPresenterImpl(Context context) {
        this.context = context;
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
                    contactUsView.onError(throwable.getLocalizedMessage());
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call(phoneNumber);
                }
                break;
        }
    }
}
