package com.spade.mek.ui.more.about.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla-maged on 2/11/18.
 */

public class AboutUs {


    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("project")
    @Expose
    private List<AboutUsProjects> aboutUsProjects = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<AboutUsProjects> getProjectProjects() {
        return aboutUsProjects;
    }

    public void setAboutUsProjects(List<AboutUsProjects> project) {
        this.aboutUsProjects = project;
    }

}

