package com.spade.mek.ui.more.contact_us.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 7/25/17.
 */

public class Area implements Parcelable {

    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    public final static Parcelable.Creator<Area> CREATOR = new Creator<Area>() {
        @SuppressWarnings({"unchecked"})
        public Area createFromParcel(Parcel in) {
            Area instance = new Area();
            instance.area = ((String) in.readValue((String.class.getClassLoader())));
            instance.lat = ((String) in.readValue((String.class.getClassLoader())));
            instance.lng = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Area[] newArray(int size) {
            return (new Area[size]);
        }

    };

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(area);
        dest.writeValue(lat);
        dest.writeValue(lng);
    }

    public int describeContents() {
        return 0;
    }

}