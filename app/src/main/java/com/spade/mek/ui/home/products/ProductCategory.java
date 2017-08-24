package com.spade.mek.ui.home.products;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 6/15/17.
 */

public class ProductCategory {
//    implements
//} Parcelable {

    @SerializedName("name")
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

//    public final static Parcelable.Creator<ProductCategory> CREATOR = new Creator<ProductCategory>() {
//        @SuppressWarnings({"unchecked"})
//        public ProductCategory createFromParcel(Parcel in) {
//            ProductCategory instance = new ProductCategory();
//            instance.categoryName = ((String) in.readValue((String.class.getClassLoader())));
//            return instance;
//        }
//
//        public ProductCategory[] newArray(int size) {
//            return (new ProductCategory[size]);
//        }
//
//    };
//
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(categoryName);
//    }
//
//    public int describeContents() {
//        return 0;
//    }
//
//    protected ProductCategory(Parcel in) {
//        categoryName = in.readString();
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(categoryName);
//    }
//
//    @SuppressWarnings("unused")
//    public static final Parcelable.Creator<Products> CREATOR = new Parcelable.Creator<Products>() {
//        @Override
//        public Products createFromParcel(Parcel in) {
//            return new Products(in);
//        }
//
//        @Override
//        public Products[] newArray(int size) {
//            return new Products[size];
//        }
//    };
}
