package com.spade.mek.ui.home.causes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class LatestCauses {

    @SerializedName("id")
    private int causeId;

    @SerializedName("product_target")
    private String productTarget;

    @SerializedName("cause_target")
    private String causeTarget;

    @SerializedName("cause_done")
    private int causeDone;

    @SerializedName("product_done")
    private int productDone;

    @SerializedName("url")
    private String causeUrl;

    @SerializedName("image")
    private String causeImage;

    @SerializedName("is_featured")
    private int isFeatured;

    @SerializedName("is_urgent")
    private int isUrgent;

    @SerializedName("product_type")
    private String productType;

    @SerializedName("title")
    private String causeTitle;

    @SerializedName("description")
    private String causeDescription;

    @SerializedName("hashtag")
    private String causeHashTag;

    @SerializedName("categories")
    private List<CauseCategory> causeCategoryList;

    public int getCauseId() {
        return causeId;
    }

    public void setCauseId(int causeId) {
        this.causeId = causeId;
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

    public String getCauseUrl() {
        return causeUrl;
    }

    public void setCauseUrl(String causeUrl) {
        this.causeUrl = causeUrl;
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

    public String getCauseTitle() {
        return causeTitle;
    }

    public void setCauseTitle(String causeTitle) {
        this.causeTitle = causeTitle;
    }

    public String getCauseDescription() {
        return causeDescription;
    }

    public void setCauseDescription(String causeDescription) {
        this.causeDescription = causeDescription;
    }

    public String getCauseHashTag() {
        return causeHashTag;
    }

    public void setCauseHashTag(String causeHashTag) {
        this.causeHashTag = causeHashTag;
    }

    public List<CauseCategory> getCauseCategoryList() {
        return causeCategoryList;
    }

    public void setCauseCategoryList(List<CauseCategory> causeCategoryList) {
        this.causeCategoryList = causeCategoryList;
    }

    public String getCauseTarget() {
        return causeTarget;
    }

    public void setCauseTarget(String causeTarget) {
        this.causeTarget = causeTarget;
    }

    public String getCauseImage() {
        return causeImage;
    }

    public void setCauseImage(String causeImage) {
        this.causeImage = causeImage;
    }
}
