package com.spade.mek.ui.more.volunteering;

import com.spade.mek.base.BasePresenter;

/**
 * Created by Ayman Abouzeid on 8/24/17.
 */

public interface EventsPresenter extends BasePresenter<EventsView> {

    void getCurrentEvents(String appLang, int pageNumber);

    void getPreviousEvents(String appLang, int pageNumber);

    void getUpComingEvents(String appLang, int pageNumber);

}
