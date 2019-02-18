package com.spade.mek.ui.more.contact_us.presenter;

import android.app.Activity;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.more.contact_us.view.ContactUsView;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public interface ContactUsPresenter extends BasePresenter<ContactUsView> {

    void getContactData(String appLang);

    void callNumber(Activity activity, String phoneNumber);

    void openUrl(String url);

    void requestLocationPermission(Activity activity);

    float calculateDistance(double lat1, double lng1, double lat2, double lng2);

    void sendMail(String mailTo);
}
