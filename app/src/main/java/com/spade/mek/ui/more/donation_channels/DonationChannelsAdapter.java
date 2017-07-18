package com.spade.mek.ui.more.donation_channels;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.mek.R;

/**
 * Created by Ayman Abouzeid on 7/13/17.
 */

public class DonationChannelsAdapter extends RecyclerView.Adapter<DonationChannelsAdapter.DonationChannelsViewHolder> {
    private Context mContext;
    private TypedArray typedArray;
    private OnChannelClicked onChannelClicked;

    public DonationChannelsAdapter(Context mContext, TypedArray typedArray) {
        this.mContext = mContext;
        this.typedArray = typedArray;
    }

    @Override
    public DonationChannelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.donation_channel_item, parent, false);
        return new DonationChannelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DonationChannelsViewHolder holder, int position) {
        holder.donationTitle.setText(mContext.getResources().getStringArray(R.array.donation_channels)[position]);
        holder.donationImage.setImageResource(typedArray.getResourceId(position, 0));
        holder.itemView.setOnClickListener(v -> onChannelClicked.onChannelClicked(position));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void setOnChannelClicked(OnChannelClicked onChannelClicked) {
        this.onChannelClicked = onChannelClicked;
    }

    public interface OnChannelClicked {
        void onChannelClicked(int position);
    }

    class DonationChannelsViewHolder extends RecyclerView.ViewHolder {
        TextView donationTitle;
        ImageView donationImage;

        public DonationChannelsViewHolder(View itemView) {
            super(itemView);
            donationTitle = (TextView) itemView.findViewById(R.id.donation_title);
            donationImage = (ImageView) itemView.findViewById(R.id.donation_image);
        }
    }
}
