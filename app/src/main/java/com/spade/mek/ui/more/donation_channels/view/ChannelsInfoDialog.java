package com.spade.mek.ui.more.donation_channels.view;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.utils.FontUtils;

/**
 * Created by Ayman Abouzeid on 6/22/17.
 */

public class ChannelsInfoDialog extends DialogFragment {
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final int EGYPTIAN_BANKS = 0;
    public static final int FAWRY = 1;
    public static final int CASH_ON_DELIVERY = 2;
    public static final int ONLINE_PAYMENT = 3;
    private int type = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(EXTRA_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_static_payments, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        init(dialogView);
        FontUtils.overrideFonts(getContext(), dialogView);
        return dialogView;
    }


    private void init(View view) {
        ImageView paymentWayImage = (ImageView) view.findViewById(R.id.way_payment_image);
        TextView titleText = (TextView) view.findViewById(R.id.title_text);
        TextView description = (TextView) view.findViewById(R.id.details_text);
        TextView headline = (TextView) view.findViewById(R.id.headline);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.dialog_donation_images);

        titleText.setText(getContext().getResources().getStringArray(R.array.dialog_donation_channels)[type]);
        description.setText(getContext().getResources().getStringArray(R.array.dialog_donation_channels_details)[type]);
        headline.setText(getContext().getResources().getStringArray(R.array.dialog_donation_channels_headline)[type]);

        paymentWayImage.setImageResource(typedArray.getResourceId(type, 0));
    }

}
