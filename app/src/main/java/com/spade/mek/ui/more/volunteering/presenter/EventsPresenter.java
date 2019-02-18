package com.spade.mek.ui.more.volunteering.presenter;

import com.spade.mek.base.BasePresenter;
import com.spade.mek.ui.more.volunteering.view.EventsView;

/**
 * Created by Ayman Abouzeid on 8/24/17.
 */

public interface EventsPresenter extends BasePresenter<EventsView> {

    void getCurrentEvents(String appLang, int pageNumber);

    void getPreviousEvents(String appLang, int pageNumber);

    void getUpComingEvents(String appLang, int pageNumber);

}
