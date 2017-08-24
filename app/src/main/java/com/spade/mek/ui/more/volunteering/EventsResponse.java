package com.spade.mek.ui.more.volunteering;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 8/24/17.
 */

public class EventsResponse {

    @Expose
    @SerializedName("success")
    private boolean isSuccess;

    @Expose
    @SerializedName("data")
    private List<Event> eventList;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
