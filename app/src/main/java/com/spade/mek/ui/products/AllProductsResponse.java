package com.spade.mek.ui.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.home.products.Products;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class AllProductsResponse {
    @Expose
    @SerializedName("success")
    private boolean isSuccess;

    @Expose
    @SerializedName("data")
    private List<Products> productsList;

    @Expose
    @SerializedName("next_page_url")
    private String nextPageUrl;

    @Expose
    @SerializedName("prev_page_url")
    private String previousPageUrl;

    @Expose
    @SerializedName("last_page")
    private int lastPage;

    @Expose
    @SerializedName("current_page")
    private int currentPage;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }
}
