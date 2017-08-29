package com.spade.mek.ui.more.profile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 8/28/17.
 */

public class PaymentHistoryData {

    @SerializedName("payments")
    private List<Payment> paymentList;

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }
}
