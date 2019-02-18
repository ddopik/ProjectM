package com.spade.mek.ui.more.volunteering.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 8/24/17.
 */

public class Event implements Parcelable {

    @Expose
    @SerializedName("id")
    private int eventId;
    @Expose
    @SerializedName("start_date")
    private String eventStartDate;
    @Expose
    @SerializedName("end_date")
    private String eventEndDate;
    @Expose
    @SerializedName("title")
    private String eventTitle;
    @Expose
    @SerializedName("description")
    private String eventDetails;
    @Expose
    @SerializedName("location")
    private String eventLocation;
    @Expose
    @SerializedName("address")
    private String eventAddress;
    @Expose
    @SerializedName("image")
    private String eventImage;
    public final static Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Event createFromParcel(Parcel in) {
            Event instance = new Event();
            instance.eventId = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.eventImage = ((String) in.readValue((String.class.getClassLoader())));
            instance.eventStartDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.eventEndDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.eventLocation = ((String) in.readValue((String.class.getClassLoader())));
            instance.eventTitle = ((String) in.readValue((String.class.getClassLoader())));
            instance.eventDetails = ((String) in.readValue((String.class.getClassLoader())));
            instance.eventAddress = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Event[] newArray(int size) {
            return (new Event[size]);
        }

    };

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(eventId);
        dest.writeValue(eventImage);
        dest.writeValue(eventStartDate);
        dest.writeValue(eventEndDate);
        dest.writeValue(eventLocation);
        dest.writeValue(eventTitle);
        dest.writeValue(eventDetails);
        dest.writeValue(eventAddress);
    }

    public int describeContents() {
        return 0;
    }
}
