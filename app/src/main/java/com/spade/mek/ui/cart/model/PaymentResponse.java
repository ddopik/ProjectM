package com.spade.mek.ui.cart.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 8/7/17.
 */

public class PaymentResponse {

    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("data")
    private PaymentResponseData paymentResponseData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public PaymentResponseData getPaymentResponseData() {
        return paymentResponseData;
    }

    public void setPaymentResponseData(PaymentResponseData paymentResponseData) {
        this.paymentResponseData = paymentResponseData;
    }
}
