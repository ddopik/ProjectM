package com.spade.mek.ui.more.donation_channels.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 7/16/17.
 */

public class StoresResponse {

    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("data")
    private StoresData storesData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public StoresData getStoresData() {
        return storesData;
    }

    public void setStoresData(StoresData storesData) {
        this.storesData = storesData;
    }
}
