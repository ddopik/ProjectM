package com.spade.mek.ui.more.regular_products.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 8/23/17.
 */

public class SubscriptionData {

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("amount")
    private double totalAmount;

    @SerializedName("duration")
    private String duration;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
