package com.spade.mek.ui.more.zakat_calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.spade.mek.R;

import java.text.DecimalFormat;

/**
 * Created by Ayman Abouzeid on 8/1/17.
 */

public class ZakatCalculatorFragment extends Fragment {

    private EditText moneyAmountEditText, sharesValueEditText, gainedProfitEditText,
            bondsValueEditText, gramPriceEditText, goldWeightEditText, rentValueEditText;

    private double goldZakat = 0, moneyZakat = 0, assetsZakat = 0, rentZakat = 0;
    private static final int DIVIDED_BY_NUMBER = 40;
    private TextView zakatValue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zakat_calculator, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        moneyAmountEditText = (EditText) view.findViewById(R.id.money_amount_edit_text);
        sharesValueEditText = (EditText) view.findViewById(R.id.shares_value_edit_text);
        gainedProfitEditText = (EditText) view.findViewById(R.id.gained_profit_edit_text);
        bondsValueEditText = (EditText) view.findViewById(R.id.bonds_value_edit_text);
        gramPriceEditText = (EditText) view.findViewById(R.id.gram_price_edit_text);
        goldWeightEditText = (EditText) view.findViewById(R.id.gold_weight_edit_text);
        rentValueEditText = (EditText) view.findViewById(R.id.rent_value_edit_text);
        zakatValue = (TextView) view.findViewById(R.id.zakat_mount_text_view);

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
    }

    private void calculateRentZakat() {
        if (!rentValueEditText.getText().toString().isEmpty()) {
            try {
                rentZakat = Double.parseDouble(rentValueEditText.getText().toString()) * 0.3;
            } catch (NumberFormatException ignored) {
            }
        }
        calculateTotalZakat();
    }

    private void calculateMoneyZakat() {
        if (!moneyAmountEditText.getText().toString().isEmpty()) {
            try {
                moneyZakat = Double.parseDouble(moneyAmountEditText.getText().toString()) / DIVIDED_BY_NUMBER;
            } catch (NumberFormatException ignored) {
            }
        }
        calculateTotalZakat();
    }

    private void calculateZakatForAssets() {
        double sharesValue = 0;
        double bondsValue = 0;
        double gainedProfit = 0;
        if (!sharesValueEditText.getText().toString().isEmpty()) {
            try {
                sharesValue = Double.parseDouble(sharesValueEditText.getText().toString());
            } catch (NumberFormatException ignored) {
            }
        }
        if (!gainedProfitEditText.getText().toString().isEmpty()) {
            try {
                gainedProfit = Double.parseDouble(gainedProfitEditText.getText().toString());
            } catch (NumberFormatException ignored) {
            }

        }

        if (!bondsValueEditText.getText().toString().isEmpty()) {
            try {
                bondsValue = Double.parseDouble(bondsValueEditText.getText().toString());
            } catch (NumberFormatException ignored) {
            }

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
            } catch (NumberFormatException ignored) {
            }
            calculateTotalZakat();
        }
    }

    private void calculateTotalZakat() {
        double totalZakat = goldZakat + moneyZakat + rentZakat + assetsZakat;
        zakatValue.setText(String.format(getString(R.string.egp), String.valueOf(new DecimalFormat("##.##").format(totalZakat))));
    }

}

