package com.spade.mek.ui.home.search.model;

import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.more.news.model.News;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 9/4/17.
 */

public class NewsSearchResponse {

    @SerializedName("success")
    private boolean isSuccess;
    @SerializedName("data")
    private List<News> newsList;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
