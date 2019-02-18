package com.spade.mek.ui.more.volunteering.view;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.more.volunteering.model.Event;
import com.spade.mek.utils.GlideApp;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 8/24/17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private List<Event> eventList;
    private Context context;
    private int defaultDrawableResId;
    private OnEventClicked onEventClicked;
    private boolean isEnded;

    public EventsAdapter(List<Event> eventList, Context context, int defaultDrawableResId) {
        this.eventList = eventList;
        this.context = context;
        this.defaultDrawableResId = defaultDrawableResId;
    }

    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.volunteering_item, parent, false);
        return new EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventTitle.setText(event.getEventTitle());
        holder.eventDescription.setText(event.getEventDetails());
        holder.eventAddress.setText(event.getEventAddress());
        holder.eventDate.setText(event.getEventStartDate().substring(0, 10));
        if (isEnded) {
            holder.applyBtn.setVisibility(View.INVISIBLE);
        } else {
            holder.applyBtn.setVisibility(View.VISIBLE);
        }
        VectorDrawableCompat defaultDrawable = VectorDrawableCompat.create(context.getResources(), defaultDrawableResId, null);
        GlideApp.with(context).load(event.getEventImage()).
                placeholder(defaultDrawable).error(defaultDrawable).centerCrop().into(holder.eventImage);
        holder.itemView.setOnClickListener(v -> onEventClicked.onEventClicked(event));
        holder.applyBtn.setOnClickListener(v -> onEventClicked.onApplyClicked(String.valueOf(event.getEventId())));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    public void setOnEventClicked(OnEventClicked onEventClicked) {
        this.onEventClicked = onEventClicked;
    }

    public interface OnEventClicked {
        void onEventClicked(Event event);

        void onApplyClicked(String eventID);
    }

    class EventsViewHolder extends RecyclerView.ViewHolder {

        private TextView eventTitle, eventDescription, eventAddress, eventDate, applyBtn;
        private ImageView eventImage;

        public EventsViewHolder(View itemView) {
            super(itemView);
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
            eventAddress = (TextView) itemView.findViewById(R.id.event_location);
            eventDate = (TextView) itemView.findViewById(R.id.event_date);
            eventDescription = (TextView) itemView.findViewById(R.id.event_description);
            eventImage = (ImageView) itemView.findViewById(R.id.event_image);
            applyBtn = (TextView) itemView.findViewById(R.id.apply_btn);
        }
    }
}
