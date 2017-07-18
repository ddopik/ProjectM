package com.spade.mek.ui.home.urgent_cases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.home.products.Products;

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
    private List<Products> urgentCases;
//    private UrgentCasesData urgentCasesData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Products> getUrgentCases() {
        return urgentCases;
    }

    public void setUrgentCases(List<Products> urgentCases) {
        this.urgentCases = urgentCases;
    }

    //    public UrgentCasesData getUrgentCasesData() {
//        return urgentCasesData;
//    }
//
//    public void setUrgentCasesData(UrgentCasesData urgentCasesData) {
//        this.urgentCasesData = urgentCasesData;
//    }
}
