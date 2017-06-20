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

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }
}
