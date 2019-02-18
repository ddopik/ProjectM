package com.spade.mek.ui.products.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.home.products.Products;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public class ProductsData {
    @Expose
    @SerializedName("products")
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


    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
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
