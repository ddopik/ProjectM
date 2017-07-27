package com.spade.mek.ui.more.contact_us.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 7/25/17.
 */

public class ContactUsDataResponse {

    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("data")
    private ContactUsInfo contactUsInfo;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public ContactUsInfo getContactUsInfo() {
        return contactUsInfo;
    }

    public void setContactUsInfo(ContactUsInfo contactUsInfo) {
        this.contactUsInfo = contactUsInfo;
    }
}
