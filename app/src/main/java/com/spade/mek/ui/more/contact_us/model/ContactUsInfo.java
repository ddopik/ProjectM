package com.spade.mek.ui.more.contact_us.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 7/25/17.
 */

public class ContactUsInfo implements Parcelable {

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("areas")
    @Expose
    private List<Area> areas = null;
    public final static Parcelable.Creator<ContactUsInfo> CREATOR = new Creator<ContactUsInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ContactUsInfo createFromParcel(Parcel in) {
            ContactUsInfo instance = new ContactUsInfo();
            instance.phone = ((String) in.readValue((String.class.getClassLoader())));
            instance.mail = ((String) in.readValue((String.class.getClassLoader())));
            instance.website = ((String) in.readValue((String.class.getClassLoader())));
            instance.facebook = ((String) in.readValue((String.class.getClassLoader())));
            instance.twitter = ((String) in.readValue((String.class.getClassLoader())));
            instance.instagram = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.areas, (Area.class.getClassLoader()));
            return instance;
        }

        public ContactUsInfo[] newArray(int size) {
            return (new ContactUsInfo[size]);
        }

    };

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(phone);
        dest.writeValue(mail);
        dest.writeValue(website);
        dest.writeValue(facebook);
        dest.writeValue(twitter);
        dest.writeValue(instagram);
        dest.writeList(areas);
    }

    public int describeContents() {
        return 0;
    }

}