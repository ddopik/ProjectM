package com.spade.mek.ui.more.volunteering.presenter;

import android.content.Context;

import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.more.volunteering.view.EventsView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 8/24/17.
 */

public class EventsPresenterImpl implements EventsPresenter {

    private Context mContext;
    private EventsView eventsView;

    public EventsPresenterImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void setView(EventsView view) {
        eventsView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void getCurrentEvents(String appLang, int pageNumber) {
        ApiHelper.getCurrentEvents(appLang, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventsResponse -> {
                    eventsView.showCurrentEvents(eventsResponse.getEventList());
                }, throwable -> {
                });
    }

    @Override
    public void getPreviousEvents(String appLang, int pageNumber) {
        ApiHelper.getPreviousEvents(appLang, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventsResponse -> {
                    eventsView.showPreviousEvents(eventsResponse.getEventList());
                }, throwable -> {
                    eventsView.onError(R.string.something_wrong);
                });
    }

    @Override
    public void getUpComingEvents(String appLang, int pageNumber) {
        ApiHelper.getUpcomingEvents(appLang, pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventsResponse -> {
                    eventsView.showUpComingEvents(eventsResponse.getEventList());
                }, throwable -> {
                    eventsView.onError(R.string.something_wrong);
                });
    }
}
