package com.spade.mek.ui.more.volunteering.view;

import com.spade.mek.base.BaseView;
import com.spade.mek.ui.more.volunteering.model.Event;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 8/24/17.
 */

public interface EventsView extends BaseView {

    void showCurrentEvents(List<Event> eventList);

    void showPreviousEvents(List<Event> eventList);

    void showUpComingEvents(List<Event> eventList);

}
