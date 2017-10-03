package com.spade.mek.ui.register;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 8/9/17.
 */

public class RegistrationResponse {

    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("data")
    private RegistrationResponseData registrationResponseData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public RegistrationResponseData getRegistrationResponseData() {
        return registrationResponseData;
    }

    public void setRegistrationResponseData(RegistrationResponseData registrationResponseData) {
        this.registrationResponseData = registrationResponseData;
    }
}
