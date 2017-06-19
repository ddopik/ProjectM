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
    private String productTarget;

    @SerializedName("cause_target")
    private String causeTarget;

    @SerializedName("cause_done")
    private int causeDone;

    @SerializedName("product_done")
    private int productDone;

    @SerializedName("url")
    private String productUrl;

    @SerializedName("image")
    private String productImage;

    @SerializedName("is_featured")
    private int isFeatured;

    @SerializedName("is_urgent")
    private int isUrgent;

    @SerializedName("product_type")
    private String productType;

    @SerializedName("title")
    private String productTitle;

    @SerializedName("description")
    private String productDescription;

    @SerializedName("hashtag")
    private String productHashTag;

    @SerializedName("categories")
    private List<ProductCategory> productCategoryList;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductTarget() {
        return productTarget;
    }

    public void setProductTarget(String productTarget) {
        this.productTarget = productTarget;
    }

    public int getProductDone() {
        return productDone;
    }

    public void setProductDone(int productDone) {
        this.productDone = productDone;
    }

    public int getCauseDone() {
        return causeDone;
    }

    public void setCauseDone(int causeDone) {
        this.causeDone = causeDone;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public int getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(int isFeatured) {
        this.isFeatured = isFeatured;
    }

    public int getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(int isUrgent) {
        this.isUrgent = isUrgent;
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

    public String getCauseTarget() {
        return causeTarget;
    }

    public void setCauseTarget(String causeTarget) {
        this.causeTarget = causeTarget;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
