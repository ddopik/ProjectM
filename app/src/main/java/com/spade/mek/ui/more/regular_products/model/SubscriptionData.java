package com.spade.mek.ui.more.regular_products.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 8/23/17.
 */

public class SubscriptionData implements Parcelable {

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("amount")
    private double totalAmount;

    @SerializedName("duration")
    private String duration;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public final static Parcelable.Creator<SubscriptionData> CREATOR = new Creator<SubscriptionData>() {
        @SuppressWarnings({"unchecked"})
        public SubscriptionData createFromParcel(Parcel in) {
            SubscriptionData instance = new SubscriptionData();
            instance.quantity = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.totalAmount = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.duration = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public SubscriptionData[] newArray(int size) {
            return (new SubscriptionData[size]);
        }

    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(quantity);
        dest.writeValue(totalAmount);
        dest.writeValue(duration);
    }

    public int describeContents() {
        return 0;
    }
}
