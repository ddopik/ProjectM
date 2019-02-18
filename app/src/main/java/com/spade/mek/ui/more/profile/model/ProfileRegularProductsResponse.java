package com.spade.mek.ui.more.profile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.home.products.Products;
import com.spade.mek.ui.products.model.ProductsData;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class ProfileRegularProductsResponse {
    @Expose
    @SerializedName("success")
    private boolean isSuccess;

    @Expose
    @SerializedName("data")
    private List<Products> productsDataProductsList;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Products> getProductsDataProductsList() {
        return productsDataProductsList;
    }

    public void setProductsDataProductsList(List<Products> productsDataProductsList) {
        this.productsDataProductsList = productsDataProductsList;
    }
}
