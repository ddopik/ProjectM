package com.spade.mek.ui.home.search.model;

import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.home.products.Products;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 9/4/17.
 */

public class SearchResponse {

    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("data")
    private List<Products> itemsList;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Products> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Products> itemsList) {
        this.itemsList = itemsList;
    }
}
