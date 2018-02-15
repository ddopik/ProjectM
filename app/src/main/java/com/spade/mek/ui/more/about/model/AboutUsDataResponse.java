package com.spade.mek.ui.more.about.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla-maged on 2/11/18.
 */

public class AboutUsDataResponse {


    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("data")
    @Expose
    private List<AboutUs> data = null;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<AboutUs> getData() {
        return data;
    }

    public void setData(List<AboutUs> data) {
        this.data = data;
    }


}
