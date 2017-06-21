package com.spade.mek.ui.causes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.products.model.ProductsData;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class AllCausesResponse {
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
