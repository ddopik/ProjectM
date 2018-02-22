package com.spade.mek.ui.cart.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spade.mek.R;
import com.spade.mek.utils.FontUtils;

/**
 * Created by Ayman Abouzeid on 6/22/17.
 */

public class QuickDonationDialog extends DialogFragment {
    private double quantityAmount = 0;
    private EditText quantityEditText;
    private CheckOut checkOut;
    private LinearLayout itemHeadDetailsContainer;
    private View dialogView;
    private InputMethodManager inputMethodManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogView = inflater.inflate(R.layout.dialog_product, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        init(dialogView);
        FontUtils.overrideFonts(getContext(), dialogView);
        return dialogView;
    }


    private void init(View view) {
        quantityEditText = view.findViewById(R.id.quantityEditText);
        quantityEditText.requestFocus();
        if (quantityEditText.requestFocus()) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        ImageView increaseImage = view.findViewById(R.id.arrow_up);
        ImageView decreaseImage = view.findViewById(R.id.arrow_down);

        TextView currencyTitle = view.findViewById(R.id.currency_title);
        currencyTitle.setVisibility(View.VISIBLE);
        itemHeadDetailsContainer = view.findViewById(R.id.item_head_details_container);
        itemHeadDetailsContainer.setVisibility(View.GONE);
        Button addToCartButton = view.findViewById(R.id.add_to_cart_btn);
        addToCartButton.setText(getString(R.string.donate_now));
        quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    try {
                        quantityAmount = Double.parseDouble(s.toString());
                    } catch (NumberFormatException e) {
                        quantityAmount = 1;
                        setMoneyAmount();
                    }
                } else {
                    quantityAmount = 0;
                }
            }
        });

        increaseImage.setOnClickListener(v -> {
            quantityAmount += 1;
            setMoneyAmount();
        });

        decreaseImage.setOnClickListener(v -> {
            if (quantityAmount > 1) {
                quantityAmount -= 1;
                setMoneyAmount();
            }
        });

        addToCartButton.setOnClickListener(v -> {
            if (quantityAmount < 1) {
                quantityEditText.setError(getString(R.string.add_money_description));
            } else {
                checkOut.onCheckOutClicked(quantityAmount);
                dismiss();
            }
        });
    }


    private void setMoneyAmount() {
        quantityEditText.setText(String.valueOf(quantityAmount));
    }

    public void setCheckOut(CheckOut checkOut) {
        this.checkOut = checkOut;
    }

    public interface CheckOut {
        void onCheckOutClicked(double quantity);
    }

}
