package com.spade.mek.ui.home.causes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class LatestCausesResponse {

    @Expose
    @SerializedName("success")
    private boolean isSuccess;

    @Expose
    @SerializedName("data")
    private List<LatestCauses> latestCausesList;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<LatestCauses> getLatestCausesList() {
        return latestCausesList;
    }

    public void setLatestCausesList(List<LatestCauses> latestCausesList) {
        this.latestCausesList = latestCausesList;
    }
}
