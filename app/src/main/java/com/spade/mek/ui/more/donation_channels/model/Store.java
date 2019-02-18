package com.spade.mek.ui.more.donation_channels.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/16/17.
 */

public class Store implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("lng")
    @Expose
    private String lng;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("city_id")
    @Expose
    private int cityId;

    @SerializedName("area_id")
    @Expose
    private int areaId;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("area")
    @Expose
    private String area;

    @SerializedName("working_hours")
    @Expose
    private String workingHours;

    @SerializedName("telephone")
    @Expose
    private List<String> telephone = new ArrayList<>();

    @SerializedName("fax")
    @Expose
    private List<String> fax = new ArrayList<>();

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("contracts_fax")
    @Expose
    private Long contractsFax;

    public final static Parcelable.Creator<Store> CREATOR = new Creator<Store>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Store createFromParcel(Parcel in) {
            Store instance = new Store();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.lat = ((String) in.readValue((String.class.getClassLoader())));
            instance.lng = ((String) in.readValue((String.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.address = ((String) in.readValue((String.class.getClassLoader())));
            instance.cityId = (Integer) in.readValue(Integer.class.getClassLoader());
            instance.areaId = (Integer) in.readValue(Integer.class.getClassLoader());
            instance.city = ((String) in.readValue((String.class.getClassLoader())));
            instance.area = ((String) in.readValue((String.class.getClassLoader())));
            instance.workingHours = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.telephone, (String.class.getClassLoader()));
            in.readList(instance.fax, (String.class.getClassLoader()));
            instance.email = ((String) in.readValue((String.class.getClassLoader())));
            instance.contractsFax = ((Long) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Store[] newArray(int size) {
            return (new Store[size]);
        }

    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public List<String> getTelephone() {
        return telephone;
    }

    public void setTelephone(List<String> telephone) {
        this.telephone = telephone;
    }

    public List<String> getFax() {
        return fax;
    }

    public void setFax(List<String> fax) {
        this.fax = fax;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getContractsFax() {
        return contractsFax;
    }

    public void setContractsFax(Long contractsFax) {
        this.contractsFax = contractsFax;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(lat);
        dest.writeValue(lng);
        dest.writeValue(title);
        dest.writeValue(address);
        dest.writeValue(cityId);
        dest.writeValue(areaId);
        dest.writeValue(city);
        dest.writeValue(area);
        dest.writeValue(workingHours);
        dest.writeList(telephone);
        dest.writeList(fax);
        dest.writeValue(email);
        dest.writeValue(contractsFax);
    }

    public int describeContents() {
        return 0;
    }
}
