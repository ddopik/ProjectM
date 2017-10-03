package com.spade.mek.ui.more.news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public class NewsData {
    @Expose
    @SerializedName("news")
    private List<News> newsList;

    @Expose
    @SerializedName("next_page_url")
    private String nextPageUrl;

    @Expose
    @SerializedName("prev_page_url")
    private String previousPageUrl;

    @Expose
    @SerializedName("last_page")
    private int lastPage;

    @Expose
    @SerializedName("current_page")
    private int currentPage;


    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getPreviousPageUrl() {
        return previousPageUrl;
    }

    public void setPreviousPageUrl(String previousPageUrl) {
        this.previousPageUrl = previousPageUrl;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
