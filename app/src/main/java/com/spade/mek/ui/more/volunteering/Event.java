package com.spade.mek.ui.more.volunteering;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 8/24/17.
 */

public class Event {

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
}
