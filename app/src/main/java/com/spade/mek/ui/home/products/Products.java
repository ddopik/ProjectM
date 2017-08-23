package com.spade.mek.ui.home.products;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.more.regular_products.model.SubscriptionData;

import java.util.ArrayList;
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

    protected Products(Parcel in) {
        productId = in.readInt();
        productTarget = in.readInt();
        causeTarget = in.readDouble();
        causeDone = in.readDouble();
        productDone = in.readInt();
        productUrl = in.readString();
        productImage = in.readString();
        isFeatured = in.readByte() != 0x00;
        isUrgent = in.readByte() != 0x00;
        productType = in.readString();
        productTitle = in.readString();
        productDescription = in.readString();
        productHashTag = in.readString();
        productPrice = in.readDouble();
        createdAt = in.readLong();
        isSubscribed = in.readByte() != 0x00;
        isRegularProduct = in.readByte() != 0x00;

        if (in.readByte() == 0x01) {
            productCategoryList = new ArrayList<ProductCategory>();
            in.readList(productCategoryList, ProductCategory.class.getClassLoader());
        } else {
            productCategoryList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeInt(productTarget);
        dest.writeDouble(causeTarget);
        dest.writeDouble(causeDone);
        dest.writeInt(productDone);
        dest.writeString(productUrl);
        dest.writeString(productImage);
        dest.writeByte((byte) (isFeatured ? 0x01 : 0x00));
        dest.writeByte((byte) (isUrgent ? 0x01 : 0x00));
        dest.writeString(productType);
        dest.writeString(productTitle);
        dest.writeString(productDescription);
        dest.writeString(productHashTag);
        dest.writeDouble(productPrice);
        dest.writeLong(createdAt);
        dest.writeByte((byte) (isSubscribed ? 0x01 : 0x00));
        dest.writeByte((byte) (isRegularProduct ? 0x01 : 0x00));

        if (productCategoryList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(productCategoryList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Products> CREATOR = new Parcelable.Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };
}