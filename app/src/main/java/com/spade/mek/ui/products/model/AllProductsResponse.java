package com.spade.mek.ui.products.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class AllProductsResponse {
    @Expose
    @SerializedName("success")
    private boolean isSuccess;

    @Expose
    @SerializedName("data")
    private ProductsData productsData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public ProductsData getProductsData() {
        return productsData;
    }

    public void setProductsData(ProductsData productsData) {
        this.productsData = productsData;
    }

}
