package com.spade.mek.ui.more.donation_channels.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/20/17.
 */

public class BanksResponse {

    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("data")
    private List<Bank> bankList;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Bank> getBankList() {
        return bankList;
    }

    public void setBankList(List<Bank> bankList) {
        this.bankList = bankList;
    }
}
