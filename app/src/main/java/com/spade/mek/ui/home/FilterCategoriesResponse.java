package com.spade.mek.ui.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 8/2/17.
 */

public class FilterCategoriesResponse {

    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("data")
    private List<FilterCategory> filterCategories;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<FilterCategory> getFilterCategories() {
        return filterCategories;
    }

    public void setFilterCategories(List<FilterCategory> filterCategories) {
        this.filterCategories = filterCategories;
    }
}
