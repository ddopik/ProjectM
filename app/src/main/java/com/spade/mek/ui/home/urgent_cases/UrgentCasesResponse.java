package com.spade.mek.ui.home.urgent_cases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class UrgentCasesResponse {

    @Expose
    @SerializedName("success")
    private boolean isSuccess;

    @Expose
    @SerializedName("data")
    private List<UrgentCase> urgentCaseList;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<UrgentCase> getUrgentCaseList() {
        return urgentCaseList;
    }

    public void setUrgentCaseList(List<UrgentCase> urgentCaseList) {
        this.urgentCaseList = urgentCaseList;
    }
}
