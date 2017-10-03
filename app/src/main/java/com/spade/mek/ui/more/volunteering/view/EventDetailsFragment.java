package com.spade.mek.ui.more.volunteering.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.more.volunteering.model.Event;
import com.spade.mek.utils.GlideApp;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Ayman Abouzeid on 6/20/17.
 */

public class EventDetailsFragment extends BaseFragment {
    private View eventDetailsView;
    private TextView eventTitle, eventDetails,
            eventStartDate, eventLocation;
    private Event event;
    private ImageView eventImageView;
    private String appLang;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        event = getArguments().getParcelable(EventDetailsActivity.EXTRA_EVENT_DETAILS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        eventDetailsView = inflater.inflate(R.layout.fragment_event_details, container, false);
        initViews();
        overrideFonts(getContext(), eventDetailsView);
        return eventDetailsView;
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initViews() {
        appLang = PrefUtils.getAppLang(getContext());
        Button submitButton = (Button) eventDetailsView.findViewById(R.id.apply_btn);
        eventTitle = (TextView) eventDetailsView.findViewById(R.id.item_title);
        eventDetails = (TextView) eventDetailsView.findViewById(R.id.item_details);
        eventStartDate = (TextView) eventDetailsView.findViewById(R.id.item_publish_date);
        eventLocation = (TextView) eventDetailsView.findViewById(R.id.item_location);
        eventImageView = (ImageView) eventDetailsView.findViewById(R.id.event_image_view);
        submitButton.setOnClickListener(v -> applyToEvent());
        updateUI();
    }

    private void applyToEvent() {
        Intent intent = SubmitVolunteeringActivity.getLaunchIntent(getContext());
        intent.putExtra(SubmitVolunteerFragment.EXTRA_EVENT_ID, String.valueOf(event.getEventId()));
        startActivity(intent);
    }

    public void updateUI() {
        eventStartDate.setText(event.getEventStartDate().substring(0, 10));
        eventTitle.setText(event.getEventTitle());
        eventDetails.setText(event.getEventDetails());
        eventLocation.setText(event.getEventAddress());
        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(getResources(), ImageUtils.getDefaultImage(appLang)
                , null);
        GlideApp.with(getContext()).load(event.getEventImage()).
                placeholder(defaultDrawable).error(defaultDrawable).centerCrop().into(eventImageView);
    }

    private String getDate(long timeStamp) {
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();

        DateFormat dateFormatter = DateFormat.getDateInstance();
        dateFormatter.setTimeZone(timeZone);

        Calendar calendar =
                Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(timeStamp * 1000);
        String result = dateFormatter.format(calendar.getTime());
        calendar.clear();
        return result;
    }

}
