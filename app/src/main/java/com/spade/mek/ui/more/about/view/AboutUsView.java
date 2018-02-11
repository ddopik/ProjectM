package com.spade.mek.ui.more.about.view;

import android.location.Location;

import com.spade.mek.ui.more.contact_us.model.ContactUsInfo;

/**
 * Created by abdalla-maged on 2/11/18.
 */

public interface AboutUsView {

    void showLoading();

    void hideLoading();

    void showAboutUsData(ContactUsInfo contactUsInfo);

}
