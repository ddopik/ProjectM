package com.spade.mek.ui.more.donation_channels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/18/17.
 */

public class StoresData {

    @SerializedName("stores")
    private List<Store> storesList;

    @SerializedName("cities")
    private List<City> cities;

    public List<Store> getStoresList() {
        return storesList;
    }

    public void setStoresList(List<Store> storesList) {
        this.storesList = storesList;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
