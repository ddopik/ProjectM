package com.spade.mek.ui.cart.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 8/7/17.
 */

public class PaymentResponseData {

    @SerializedName("url")
    private String paymentUrl;

    @SerializedName("order_id")
    private String orderId;

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
