package com.spade.mek.ui.more.donation_channels.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/20/17.
 */

public class Bank {

    @SerializedName("id")
    private int bankId;
    @SerializedName("name")
    private String bankName;
    @SerializedName("description")
    private String bankDescription;
    @SerializedName("category")
    private List<BankCategory> bankCategoryList;

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankDescription() {
        return bankDescription;
    }

    public void setBankDescription(String bankDescription) {
        this.bankDescription = bankDescription;
    }

    public List<BankCategory> getBankCategoryList() {
        return bankCategoryList;
    }

    public void setBankCategoryList(List<BankCategory> bankCategoryList) {
        this.bankCategoryList = bankCategoryList;
    }
}
