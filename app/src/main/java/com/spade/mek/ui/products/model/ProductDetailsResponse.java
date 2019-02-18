package com.spade.mek.ui.products.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.home.products.Products;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public class ProductDetailsResponse {

    @Expose
    @SerializedName("success")
    private boolean isSuccess;

    @Expose
    @SerializedName("data")
    private Products product;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}
