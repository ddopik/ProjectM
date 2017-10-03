package com.spade.mek.ui.more.donation_channels.model;

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
    private List<Area> areaList;

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

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
