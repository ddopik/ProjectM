package com.spade.mek.ui.home.products;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class ProductCategory {

    @SerializedName("name")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
