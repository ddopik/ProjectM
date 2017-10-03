package com.spade.mek.ui.more.news.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/12/17.
 */

public class RelatedNewsResponse {

    @SerializedName("success")
    private boolean isSuccess;

    @SerializedName("data")
    private List<News> relatedNewsList;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<News> getRelatedNewsList() {
        return relatedNewsList;
    }

    public void setRelatedNewsList(List<News> relatedNewsList) {
        this.relatedNewsList = relatedNewsList;
    }
}

