package com.spade.mek.ui.more.volunteering.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;

import static com.spade.mek.ui.register.RegisterActivity.EXTRA_TYPE;

/**
 * Created by Ayman Abouzeid on 8/24/17.
 */

public class VolunteeringFragment extends BaseFragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_paging, container, false);
        initViews();
        return view;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.fragments_viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        PagingAdapter adapter = new PagingAdapter(getChildFragmentManager());
        adapter.addFragment(getPreviousEventsFragment(), getString(R.string.previous_events));
        adapter.addFragment(getCurrentEventsFragment(), getString(R.string.current_events));
        adapter.addFragment(getUpComingEventsFragment(), getString(R.string.upcoming_events));
        viewPager.setAdapter(adapter);
    }

    private EventsFragment getCurrentEventsFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, EventsFragment.EXTRA_CURRENT_EVENTS);
        EventsFragment eventsFragment = new EventsFragment();
        eventsFragment.setArguments(bundle);
        return eventsFragment;
    }

    private EventsFragment getPreviousEventsFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, EventsFragment.EXTRA_PREVIOUS_EVENTS);
        EventsFragment eventsFragment = new EventsFragment();
        eventsFragment.setArguments(bundle);
        return eventsFragment;
    }

    private EventsFragment getUpComingEventsFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, EventsFragment.EXTRA_UP_COMING_EVENTS);
        EventsFragment eventsFragment = new EventsFragment();
        eventsFragment.setArguments(bundle);
        return eventsFragment;
    }
}
