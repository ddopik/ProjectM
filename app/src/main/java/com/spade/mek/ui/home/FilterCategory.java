package com.spade.mek.ui.home;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class FilterCategory {

    @SerializedName("name")
    private String categoryName;

    @SerializedName("id")
    private int categoryId;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
