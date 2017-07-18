package com.spade.mek.ui.more.donation_channels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public class City {

    @SerializedName("id")
    private int cityId;

    @SerializedName("name")
    private String cityName;

    @SerializedName("areas")
    private List<String> areaList;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<String> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<String> areaList) {
        this.areaList = areaList;
    }
}
