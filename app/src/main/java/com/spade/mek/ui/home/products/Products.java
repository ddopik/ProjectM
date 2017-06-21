package com.spade.mek.ui.home.products;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class Products {

    @SerializedName("id")
    private int productId;

    @SerializedName("product_target")
    private int productTarget;

    @SerializedName("cause_target")
    private double causeTarget;

    @SerializedName("cause_done")
    private double causeDone;

    @SerializedName("product_done")
    private int productDone;

    @SerializedName("url")
    private String productUrl;

    @SerializedName("image")
    private String productImage;

    @SerializedName("is_featured")
    private boolean isFeatured;

    @SerializedName("is_urgent")
    private boolean isUrgent;

    @SerializedName("product_type")
    private String productType;

    @SerializedName("title")
    private String productTitle;

    @SerializedName("description")
    private String productDescription;

    @SerializedName("hashtag")
    private String productHashTag;

    @SerializedName("product_price")
    private double productPrice;
//    @SerializedName("created_at")
//    private String createdAt;

    @SerializedName("categories")
    private List<ProductCategory> productCategoryList;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    public int getProductDone() {
        return productDone;
    }

    public void setProductDone(int productDone) {
        this.productDone = productDone;
    }


    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }


    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductHashTag() {
        return productHashTag;
    }

    public void setProductHashTag(String productHashTag) {
        this.productHashTag = productHashTag;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductTarget() {
        return productTarget;
    }

    public void setProductTarget(int productTarget) {
        this.productTarget = productTarget;
    }

    public double getCauseTarget() {
        return causeTarget;
    }

    public void setCauseTarget(double causeTarget) {
        this.causeTarget = causeTarget;
    }

    public double getCauseDone() {
        return causeDone;
    }

    public void setCauseDone(double causeDone) {
        this.causeDone = causeDone;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    //    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
}
