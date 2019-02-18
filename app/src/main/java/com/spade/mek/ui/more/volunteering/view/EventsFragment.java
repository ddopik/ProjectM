package com.spade.mek.ui.more.volunteering.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.more.volunteering.model.Event;
import com.spade.mek.ui.more.volunteering.presenter.EventsPresenter;
import com.spade.mek.ui.more.volunteering.presenter.EventsPresenterImpl;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import static com.spade.mek.ui.register.RegisterActivity.EXTRA_TYPE;

/**
 * Created by Ayman Abouzeid on 8/24/17.
 */

public class EventsFragment extends BaseFragment implements EventsView, EventsAdapter.OnEventClicked {
    public static final int EXTRA_CURRENT_EVENTS = 1;
    public static final int EXTRA_PREVIOUS_EVENTS = 2;
    public static final int EXTRA_UP_COMING_EVENTS = 3;
    private EventsAdapter eventsAdapter;
    private List<Event> eventList;
    private EventsPresenter eventsPresenter;
    private View eventView;
    private boolean isLoading = false;
    private int currentPage = 0;
    private int lastPage;
    private String appLang;
    private int eventsType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventsType = getArguments().getInt(EXTRA_TYPE);
    }

    @Override
    protected void initPresenter() {
        eventsPresenter = new EventsPresenterImpl(getContext());
        eventsPresenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        eventView = inflater.inflate(R.layout.fragment_event, container, false);
        initViews();
        return eventView;
    }

    @Override
    protected void initViews() {
        appLang = PrefUtils.getAppLang(getContext());
        RecyclerView eventsRecycler = (RecyclerView) eventView.findViewById(R.id.events_recycler_view);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        eventsRecycler.setLayoutManager(verticalLayoutManager);
        eventList = new ArrayList<>();
        eventsAdapter = new EventsAdapter(eventList, getContext(), ImageUtils.getDefaultImage(appLang));
        eventsAdapter.setOnEventClicked(this);
        eventsRecycler.setAdapter(eventsAdapter);
        eventsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = verticalLayoutManager.getChildCount();
                int totalItemCount = verticalLayoutManager.getItemCount();
                int firstVisibleItemPosition = verticalLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (currentPage < lastPage)) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        getEvents();
                    }
                }
            }
        });
        getEvents();
    }

    private void getEvents() {
        int pageNumber = currentPage + 1;
        isLoading = true;
        switch (eventsType) {
            case EXTRA_CURRENT_EVENTS:
                eventsPresenter.getCurrentEvents(appLang, pageNumber);
                break;
            case EXTRA_PREVIOUS_EVENTS:
                eventsPresenter.getPreviousEvents(appLang, pageNumber);
                break;
            case EXTRA_UP_COMING_EVENTS:
                eventsPresenter.getUpComingEvents(appLang, pageNumber);
                break;
        }
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onError(int resID) {
        if (getContext() != null)
            Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCurrentEvents(List<Event> eventList) {
        this.eventList.addAll(eventList);
        eventsAdapter.setEnded(false);
        eventsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showPreviousEvents(List<Event> eventList) {
        this.eventList.addAll(eventList);
        eventsAdapter.setEnded(true);
        eventsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showUpComingEvents(List<Event> eventList) {
        this.eventList.addAll(eventList);
        eventsAdapter.setEnded(false);
        eventsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEventClicked(Event event) {
        Intent intent = EventDetailsActivity.getLaunchIntent(getContext());
        intent.putExtra(EventDetailsActivity.EXTRA_EVENT_DETAILS, event);
        startActivity(intent);
    }

    @Override
    public void onApplyClicked(String eventID) {
        Intent intent = SubmitVolunteeringActivity.getLaunchIntent(getContext());
        intent.putExtra(SubmitVolunteerFragment.EXTRA_EVENT_ID, eventID);
        startActivity(intent);
    }
}
