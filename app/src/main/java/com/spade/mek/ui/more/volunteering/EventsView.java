package com.spade.mek.ui.more.volunteering;

import com.spade.mek.base.BaseView;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 8/24/17.
 */

public interface EventsView extends BaseView {

    void showCurrentEvents(List<Event> eventList);

    void showPreviousEvents(List<Event> eventList);

    void showUpComingEvents(List<Event> eventList);

}
