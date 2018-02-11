package com.spade.mek.ui.more.about.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by abdalla-maged on 2/11/18.
 */

public class AboutUsDataResponse {

    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("data")
    private AboutUs aboutUs;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public AboutUs getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(AboutUs news) {
        this.aboutUs = news;
    }
}
