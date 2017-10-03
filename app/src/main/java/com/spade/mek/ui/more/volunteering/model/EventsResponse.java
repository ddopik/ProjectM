package com.spade.mek.ui.more.volunteering.model;

import android.os.Parcel;
import android.os.Parcelable;

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
    public final static Parcelable.Creator<EventsResponse> CREATOR = new Parcelable.Creator<EventsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public EventsResponse createFromParcel(Parcel in) {
            EventsResponse instance = new EventsResponse();
            instance.isSuccess = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            in.readList(instance.eventList, (Event.class.getClassLoader()));
            return instance;
        }

        public EventsResponse[] newArray(int size) {
            return (new EventsResponse[size]);
        }

    };

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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(isSuccess);
        dest.writeList(eventList);
    }

    public int describeContents() {
        return 0;
    }
}
