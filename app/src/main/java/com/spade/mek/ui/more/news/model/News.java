
package com.spade.mek.ui.more.news.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News implements Parcelable {

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private String image;
    //todo A_M [New_task][1]
//    @SerializedName("youtubeImgUrl")
//    @Expose
//    private String youTubeImgUrl;

    //todo A_M [New_task]
    @SerializedName("youtube_url")
    @Expose
    private String youtubeUrl;
    @SerializedName("is_featured")
    @Expose
    private Boolean isFeatured;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("categories")
    @Expose
    private List<NewsCategory> categories = null;
    protected News(Parcel in) {
        url = in.readString();
        image = in.readString();
        title = in.readString();
        shortDescription = in.readString();
        body = in.readString();

        //todo A_M [New_task][1]
//        youTubeImgUrl = in.readString();
    }

//    public String getYouTubeImgUrl() {
//        return youTubeImgUrl;
//    }
//
//    public void setYouTubeImgUrl(String youTubeImgUrl) {
//        this.youTubeImgUrl = youTubeImgUrl;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<NewsCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<NewsCategory> categories) {
        this.categories = categories;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(url);
        dest.writeValue(image);
        dest.writeValue(isFeatured);
        dest.writeValue(title);
        dest.writeValue(createdAt);
        dest.writeValue(shortDescription);
        dest.writeValue(body);
        dest.writeList(categories);
        //todo A_M [New_task][1]
//        dest.writeList(youTubeImgUrl);
    }

    public int describeContents() {
        return 0;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
}
