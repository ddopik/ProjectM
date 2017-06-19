package com.spade.mek.ui.home.urgent_cases;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class UrgentCase {

    @SerializedName("id")
    private int caseId;

    @SerializedName("product_target")
    private String productTarget;

    @SerializedName("cause_target")
    private String causeTarget;

    @SerializedName("cause_done")
    private int causeDone;

    @SerializedName("product_done")
    private int productDone;

    @SerializedName("url")
    private String caseUrl;

    @SerializedName("image")
    private String caseImage;

    @SerializedName("is_featured")
    private int isFeatured;

    @SerializedName("is_urgent")
    private int isUrgent;

    @SerializedName("product_type")
    private String productType;

    @SerializedName("title")
    private String caseTitle;

    @SerializedName("description")
    private String caseDescription;

    @SerializedName("hashtag")
    private String caseHashTag;

    @SerializedName("categories")
    private List<CaseCategory> caseCategories;

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
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

    public String getCaseUrl() {
        return caseUrl;
    }

    public void setCaseUrl(String caseUrl) {
        this.caseUrl = caseUrl;
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

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }

    public String getCaseHashTag() {
        return caseHashTag;
    }

    public void setCaseHashTag(String caseHashTag) {
        this.caseHashTag = caseHashTag;
    }

    public List<CaseCategory> getCaseCategories() {
        return caseCategories;
    }

    public void setCaseCategories(List<CaseCategory> caseCategories) {
        this.caseCategories = caseCategories;
    }

    public String getCauseTarget() {
        return causeTarget;
    }

    public void setCauseTarget(String causeTarget) {
        this.causeTarget = causeTarget;
    }

    public String getCaseImage() {
        return caseImage;
    }

    public void setCaseImage(String caseImage) {
        this.caseImage = caseImage;
    }
}
