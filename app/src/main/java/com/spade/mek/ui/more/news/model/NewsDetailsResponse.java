package com.spade.mek.ui.more.news.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 7/11/17.
 */

public class NewsDetailsResponse {

    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("data")
    private News news;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
