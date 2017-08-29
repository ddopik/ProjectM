package com.spade.mek.ui.more.profile.model;

import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.home.products.Products;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 8/28/17.
 */
public class Payment {
    @SerializedName("id")
    private int id;
    @SerializedName("amount")
    private double amount;
    @SerializedName("items")
    private int itemsCount;
    @SerializedName("type_of_donation")
    private String typeOfDonation;
    @SerializedName("created_at")
    private long createdAt;
    @SerializedName("products")
    private List<Products> productsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public String getTypeOfDonation() {
        return typeOfDonation;
    }

    public void setTypeOfDonation(String typeOfDonation) {
        this.typeOfDonation = typeOfDonation;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }
}
