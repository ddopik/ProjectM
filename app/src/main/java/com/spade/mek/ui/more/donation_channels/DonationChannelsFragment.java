package com.spade.mek.ui.more.donation_channels;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spade.mek.R;

/**
 * Created by Ayman Abouzeid on 7/13/17.
 */

public class DonationChannelsFragment extends Fragment implements DonationChannelsAdapter.OnChannelClicked {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.donation_channel_fragment, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        RecyclerView donationChannelsRecycler = (RecyclerView) view.findViewById(R.id.donation_channels_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        donationChannelsRecycler.setLayoutManager(gridLayoutManager);

        TypedArray typedArray = getResources().obtainTypedArray(R.array.donation_images);
        DonationChannelsAdapter donationChannelsAdapter = new DonationChannelsAdapter(getContext(), typedArray);
        donationChannelsAdapter.setOnChannelClicked(this);
        donationChannelsRecycler.setAdapter(donationChannelsAdapter);
    }

    @Override
    public void onChannelClicked(int position) {
        switch (position) {
            case 0:
                break;
            case 1:
                startActivity(DonationStoresActivity.getLaunchIntent(getContext()));
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }
}
