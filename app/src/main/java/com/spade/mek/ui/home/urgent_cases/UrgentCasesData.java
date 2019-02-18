package com.spade.mek.ui.home.urgent_cases;

import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.home.products.Products;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/11/17.
 */

public class UrgentCasesData {

    @SerializedName("products")
    private List<Products> productsList;

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }
}
