package com.spade.mek.ui.home.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class LatestProductsResponse {

    @Expose
    @SerializedName("success")
    private boolean isSuccess;

    @Expose
    @SerializedName("data")
    private List<LatestProducts> latestProductsList;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<LatestProducts> getLatestProductsList() {
        return latestProductsList;
    }

    public void setLatestProductsList(List<LatestProducts> latestProductsList) {
        this.latestProductsList = latestProductsList;
    }
}
