package com.spade.mek.ui.home.products;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.more.regular_products.model.SubscriptionData;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class Products implements Parcelable {

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

    @SerializedName("created_at")
    private long createdAt;

    @SerializedName("is_subscribe")
    private boolean isSubscribed;

    @SerializedName("is_regular")
    private boolean isRegularProduct;

    @SerializedName("quantity")
    private String productQuantity;

    @SerializedName("price")
    private String causePrice;

    @SerializedName("subscription_user")
    private SubscriptionData subscriptionData;

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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public boolean isRegularProduct() {
        return isRegularProduct;
    }

    public void setRegularProduct(boolean regularProduct) {
        isRegularProduct = regularProduct;
    }

    public SubscriptionData getSubscriptionData() {
        return subscriptionData;
    }

    public void setSubscriptionData(SubscriptionData subscriptionData) {
        this.subscriptionData = subscriptionData;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getCausePrice() {
        return causePrice;
    }

    public void setCausePrice(String causePrice) {
        this.causePrice = causePrice;
    }

    public final static Parcelable.Creator<Products> CREATOR = new Creator<Products>() {
        @SuppressWarnings({"unchecked"})
        public Products createFromParcel(Parcel in) {
            Products instance = new Products();
            instance.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.productTarget = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.causeTarget = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.causeDone = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.productDone = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.productUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.productImage = ((String) in.readValue((String.class.getClassLoader())));
            instance.isFeatured = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.isUrgent = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.productType = ((String) in.readValue((String.class.getClassLoader())));
            instance.productTitle = ((String) in.readValue((String.class.getClassLoader())));
            instance.productDescription = ((String) in.readValue((String.class.getClassLoader())));
            instance.productHashTag = ((String) in.readValue((String.class.getClassLoader())));
            instance.productPrice = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.createdAt = ((Long) in.readValue((Long.class.getClassLoader())));
            instance.isSubscribed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.isRegularProduct = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.productQuantity = ((String) in.readValue((Integer.class.getClassLoader())));
            instance.causePrice = ((String) in.readValue((Double.class.getClassLoader())));
            instance.subscriptionData = ((SubscriptionData) in.readValue((SubscriptionData.class.getClassLoader())));
//            in.readList(instance.productCategoryList, (ProductCategory.class.getClassLoader()));
            return instance;
        }

        public Products[] newArray(int size) {
            return (new Products[size]);
        }

    };


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(productId);
        dest.writeValue(productTarget);
        dest.writeValue(causeTarget);
        dest.writeValue(causeDone);
        dest.writeValue(productDone);
        dest.writeValue(productUrl);
        dest.writeValue(productImage);
        dest.writeValue(isFeatured);
        dest.writeValue(isUrgent);
        dest.writeValue(productType);
        dest.writeValue(productTitle);
        dest.writeValue(productDescription);
        dest.writeValue(productHashTag);
        dest.writeValue(productPrice);
        dest.writeValue(createdAt);
        dest.writeValue(isSubscribed);
        dest.writeValue(isRegularProduct);
        dest.writeValue(productQuantity);
        dest.writeValue(causePrice);
        dest.writeValue(subscriptionData);
//        dest.writeList(productCategoryList);
    }

    public int describeContents() {
        return 0;
    }

}