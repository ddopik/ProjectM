package com.spade.mek.ui.more.donation_channels.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/20/17.
 */

public class BankCategory {
    @SerializedName("category")
    private String categoryName;
    @SerializedName("bank_account")
    private String bankAccount;
    @SerializedName("currency")
    private String currency;
    @SerializedName("swift_code")
    private List<String> swiftCodes;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<String> getSwiftCodes() {
        return swiftCodes;
    }

    public void setSwiftCodes(List<String> swiftCodes) {
        this.swiftCodes = swiftCodes;
    }
}
