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

    public ProductsData getProductsData() {
        return productsData;
    }

    public void setProductsData(ProductsData productsData) {
        this.productsData = productsData;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getPreviousPageUrl() {
        return previousPageUrl;
    }

    public void setPreviousPageUrl(String previousPageUrl) {
        this.previousPageUrl = previousPageUrl;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
