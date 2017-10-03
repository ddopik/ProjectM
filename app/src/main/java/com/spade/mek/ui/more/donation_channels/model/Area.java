package com.spade.mek.ui.more.donation_channels.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 7/19/17.
 */

public class Area {
    @SerializedName("name")
    private String areaName;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
