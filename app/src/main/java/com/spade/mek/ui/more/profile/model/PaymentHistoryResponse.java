package com.spade.mek.ui.more.profile.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 8/28/17.
 */

public class PaymentHistoryResponse {

    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("data")
    private PaymentHistoryData paymentHistoryData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public PaymentHistoryData getPaymentHistoryData() {
        return paymentHistoryData;
    }

    public void setPaymentHistoryData(PaymentHistoryData paymentHistoryData) {
        this.paymentHistoryData = paymentHistoryData;
    }
}
