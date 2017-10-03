package com.spade.mek.ui.more.regular_products.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.utils.FontUtils;

/**
 * Created by Ayman Abouzeid on 6/22/17.
 */

public class ViewSubscriptionDialog extends DialogFragment {

    private String title, quantity, amount, productPrice, duration;
    private SubscriptionActions subscriptionActions;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString(ProductDetailsFragment.EXTRA_PRODUCT_TITLE);
        quantity = getArguments().getString(ProductDetailsFragment.EXTRA_SUBSCRIPTION_QUANTITY);
        amount = getArguments().getString(ProductDetailsFragment.EXTRA_SUBSCRIPTION_AMOUNT);
        duration = getArguments().getString(SubscribeFragment.EXTRA_DURATION);
        productPrice = getArguments().getString(ProductDetailsFragment.EXTRA_PRODUCT_PRICE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_subscription_view, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        init(dialogView);
        FontUtils.overrideFonts(getContext(), dialogView);
        return dialogView;
    }


    private void init(View view) {
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity_text_view);
        TextView itemPrice = (TextView) view.findViewById(R.id.item_price);
        TextView itemTitle = (TextView) view.findViewById(R.id.product_title);
        TextView durationText = (TextView) view.findViewById(R.id.duration_text);
        Button unSubscribeBtn = (Button) view.findViewById(R.id.unsubscribe_btn);

        itemTitle.setText(title);
        quantityTextView.setText(String.format(getString(R.string.products_number), quantity, amount));
        itemPrice.setText(String.format(getString(R.string.egp), productPrice));
        durationText.setText(String.format(getString(R.string.month_reminder), duration));

        unSubscribeBtn.setOnClickListener(v -> {
            dismiss();
            subscriptionActions.onUnSubscribeClicked();
        });
    }

    public void setSubscriptionActions(SubscriptionActions subscriptionActions) {
        this.subscriptionActions = subscriptionActions;
    }

    public interface SubscriptionActions {
        void onUnSubscribeClicked();
    }

}
