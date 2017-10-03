package com.spade.mek.ui.more.news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 6/19/17.
 */

public class AllNewsResponse {
    @Expose
    @SerializedName("success")
    private boolean isSuccess;

    @Expose
    @SerializedName("data")
    private NewsData newsData;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public NewsData getNewsData() {
        return newsData;
    }

    public void setNewsData(NewsData newsData) {
        this.newsData = newsData;
    }

}
