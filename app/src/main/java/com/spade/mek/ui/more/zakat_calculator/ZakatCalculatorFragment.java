package com.spade.mek.ui.more.zakat_calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.cart.view.UserDataActivity;
import com.spade.mek.ui.cart.view.UserDataFragment;
import com.spade.mek.utils.ConstUtil;
import com.spade.mek.utils.FontUtils;
import com.spade.mek.utils.PrefUtils;

import java.text.DecimalFormat;

/**
 * Created by Ayman Abouzeid on 8/1/17.
 */

public class ZakatCalculatorFragment extends Fragment {

    private static final int DIVIDED_BY_NUMBER = 40;
    private EditText moneyAmountEditText, sharesValueEditText, gainedProfitEditText,
            bondsValueEditText, gramPriceEditText, goldWeightEditText, rentValueEditText;
    private double goldZakat = 0, moneyZakat = 0, assetsZakat = 0, rentZakat = 0;
    private TextView zakatValue;
    private double totalZakat;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tracker zakatCalculatorTracker = MekApplication.getDefaultTracker();
        zakatCalculatorTracker.setScreenName(getContext().getString(R.string.zakat_calculator_screen));
        zakatCalculatorTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zakat_calculator, container, false);
        init(view);
        FontUtils.overrideFonts(getContext(), view);
        return view;
    }

    private void init(View view) {
        Button donateNowBtn = view.findViewById(R.id.donate_now_btn);
        moneyAmountEditText = view.findViewById(R.id.money_amount_edit_text);
        sharesValueEditText = view.findViewById(R.id.shares_value_edit_text);
        gainedProfitEditText = view.findViewById(R.id.gained_profit_edit_text);
        bondsValueEditText = view.findViewById(R.id.bonds_value_edit_text);
        gramPriceEditText = view.findViewById(R.id.gram_price_edit_text);
        goldWeightEditText = view.findViewById(R.id.gold_weight_edit_text);
        rentValueEditText = view.findViewById(R.id.rent_value_edit_text);
        zakatValue = view.findViewById(R.id.zakat_mount_text_view);


        moneyAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateMoneyZakat();
            }
        });

        sharesValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateZakatForAssets();
            }
        });
        gainedProfitEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateZakatForAssets();
            }
        });
        bondsValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateZakatForAssets();
            }
        });
        gramPriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateZakatForGold();
            }
        });
        goldWeightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateZakatForGold();
            }
        });
        rentValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateRentZakat();
            }
        });

        donateNowBtn.setOnClickListener(v -> {
            if (totalZakat > 0) {
                donateZakat();
                //todo A_M [New_task]
                BaseFragment.sendTrackEvent(ConstUtil.CATEGORY_ZAKAT, ConstUtil.ACTION_DONNATE_YOUR_ZAKAT, PrefUtils.getUserId(getActivity()));
            } else {
                Toast.makeText(getContext(), getString(R.string.invalid_amount), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void donateZakat() {
        Intent intent = UserDataActivity.getLaunchIntent(getContext());
        intent.putExtra(UserDataFragment.EXTRA_DONATE_TYPE, UserDataFragment.EXTRA_DONATE_ZAKAT);
        intent.putExtra(UserDataFragment.EXTRA_ZAKAT_AMOUNT, totalZakat);
        startActivity(intent);
    }

    private void calculateRentZakat() {
        if (!rentValueEditText.getText().toString().isEmpty()) {
            try {
                rentZakat = Double.parseDouble(rentValueEditText.getText().toString()) * 0.3;
            } catch (NumberFormatException exception) {
                rentZakat = 0;
            }
        } else {
            rentZakat = 0;
        }
        calculateTotalZakat();
    }

    private void calculateMoneyZakat() {
        if (!moneyAmountEditText.getText().toString().isEmpty()) {
            try {
                moneyZakat = Double.parseDouble(moneyAmountEditText.getText().toString()) / DIVIDED_BY_NUMBER;
            } catch (NumberFormatException exception) {
                moneyZakat = 0;
            }
        } else {
            moneyZakat = 0;
        }
        calculateTotalZakat();
    }

    private void calculateZakatForAssets() {
        double sharesValue;
        double bondsValue;
        double gainedProfit;
        if (!sharesValueEditText.getText().toString().isEmpty()) {
            try {
                sharesValue = Double.parseDouble(sharesValueEditText.getText().toString());
            } catch (NumberFormatException exception) {
                sharesValue = 0;
            }
        } else {
            sharesValue = 0;
        }

        if (!gainedProfitEditText.getText().toString().isEmpty()) {
            try {
                gainedProfit = Double.parseDouble(gainedProfitEditText.getText().toString());
            } catch (NumberFormatException exception) {
                gainedProfit = 0;
            }
        } else {
            gainedProfit = 0;
        }

        if (!bondsValueEditText.getText().toString().isEmpty()) {
            try {
                bondsValue = Double.parseDouble(bondsValueEditText.getText().toString());
            } catch (NumberFormatException exception) {
                bondsValue = 0;
            }
        } else {
            bondsValue = 0;
        }

        if (!sharesValueEditText.getText().toString().isEmpty() && !gainedProfitEditText.getText().toString().isEmpty()) {
            assetsZakat = ((sharesValue + gainedProfit) / DIVIDED_BY_NUMBER) + (bondsValue / DIVIDED_BY_NUMBER);
        } else
            assetsZakat = (bondsValue / DIVIDED_BY_NUMBER);

        calculateTotalZakat();
    }

    private void calculateZakatForGold() {
        if (!gramPriceEditText.getText().toString().isEmpty() && !goldWeightEditText.getText().toString().isEmpty()) {
            try {
                goldZakat = (Double.parseDouble(gramPriceEditText.getText().toString()) *
                        Double.parseDouble(goldWeightEditText.getText().toString())) / DIVIDED_BY_NUMBER;
            } catch (NumberFormatException exception) {
                goldZakat = 0;
            }
        } else {
            goldZakat = 0;
        }
        calculateTotalZakat();
    }

    private void calculateTotalZakat() {
        totalZakat = goldZakat + moneyZakat + rentZakat + assetsZakat;
        zakatValue.setText(String.format(getString(R.string.egp), String.valueOf(new DecimalFormat("##.##").format(totalZakat))));
    }

}

