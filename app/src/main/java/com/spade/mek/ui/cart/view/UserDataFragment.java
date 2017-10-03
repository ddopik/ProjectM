package com.spade.mek.ui.cart.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.cart.presenter.UserOrderPresenter;
import com.spade.mek.ui.cart.presenter.UserOrderPresenterImpl;
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.more.donation_channels.view.DonationChannelsActivity;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;
import com.spade.mek.utils.Validator;

/**
 * Created by Ayman Abouzeid on 6/23/17.
 */

public class UserDataFragment extends BaseFragment implements UserDataView {

    public static final String EXTRA_TOTAL_COST = "EXTRA_TOTAL_COST";
    public static final String EXTRA_ZAKAT_AMOUNT = "EXTRA_ZAKAT_AMOUNT";
    public static final String EXTRA_DONATE_TYPE = "EXTRA_DONATE_TYPE";
    public static final String EXTRA_PAYMENT_TYPE = "EXTRA_PAYMENT_TYPE";
    public static final int EXTRA_PAY_FOR_PRODUCTS = 800;

    public static final int EXTRA_DONATE_ZAKAT = 900;
    private static final int PAYMENT_REQUEST_CODE = 1001;
    public static final int ONLINE_PAYMENT_TYPE = 0;
    public static final int CASH_ON_DELIVERY = 1;

    private EditText firstNameEditText, lastNameEditText,
            phoneNumberEditText, emailAddressEditText, addressEditText;
    private String firstNameString, lastNameString, phoneNumberString,
            emailAddressString, addressString, donationTypeString = "";
    private ProgressDialog progressDialog;

    private View fragmentView;
    private UserOrderPresenter userOrderPresenter;

    private TextView onlinePayment, cashOnDelivery;
    private double totalCost;
    private int donationTypePosition = 0;
    private int paymentType;
    private int donationType;
//    private double zakatAmount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        donationType = getArguments().getInt(EXTRA_DONATE_TYPE);
        if (donationType == EXTRA_DONATE_ZAKAT) {
            totalCost = getArguments().getDouble(EXTRA_ZAKAT_AMOUNT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_user_data, container, false);
        initViews();
        overrideFonts(getContext(), fragmentView);
        return fragmentView;
    }

    @Override
    protected void initPresenter() {
        userOrderPresenter = new UserOrderPresenterImpl(getContext());
        userOrderPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        firstNameEditText = (EditText) fragmentView.findViewById(R.id.first_name_edit_text);
        lastNameEditText = (EditText) fragmentView.findViewById(R.id.last_name_edit_text);
        phoneNumberEditText = (EditText) fragmentView.findViewById(R.id.phone_number_edit_text);
        emailAddressEditText = (EditText) fragmentView.findViewById(R.id.email_address_edit_text);
        addressEditText = (EditText) fragmentView.findViewById(R.id.address_edit_text);
        onlinePayment = (TextView) fragmentView.findViewById(R.id.online_payment);
        cashOnDelivery = (TextView) fragmentView.findViewById(R.id.cash_on_delivery);
//        donationEditText = (EditText) fragmentView.findViewById(R.id.type_of_donation_edit_text);
        TextView chooseAnotherChannel = (TextView) fragmentView.findViewById(R.id.choose_another_channel);
        chooseAnotherChannel.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        chooseAnotherChannel.setOnClickListener(v -> startActivity(DonationChannelsActivity.getLaunchIntent(getContext())));
        Button proceedBtn = (Button) fragmentView.findViewById(R.id.proceed_btn);
        AppCompatSpinner donationTypesSpinner = (AppCompatSpinner) fragmentView.findViewById(R.id.donation_types_spinner);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.type_of_donation_item, getResources().getStringArray(R.array.type_of_donations));
        donationTypesSpinner.setAdapter(spinnerAdapter);
        proceedBtn.setOnClickListener(v -> {
            if (checkIfDataIsValid()) {
                proceed();
            }
        });
        donationTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                donationTypePosition = position;
                if (position == 1)
                    donationTypeString = getString(R.string.zakat);
                if (position == 2)
                    donationTypeString = getString(R.string.sadaqah);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (PrefUtils.getLoginProvider(getContext()) == LoginProviders.NONE.getLoginProviderCode()) {
            onlinePayment.setVisibility(View.GONE);
        }
        onlinePayment.setOnClickListener(v -> chooseOnlinePayment());
        cashOnDelivery.setOnClickListener(v -> chooseCashOnDelivery());
        chooseCashOnDelivery();
        setUserData();

    }

    private void setUserData() {
        User user = userOrderPresenter.getUser(PrefUtils.getUserId(getContext()));
        if (user != null) {
            if (user.getFirstName() != null) {
                firstNameEditText.setText(user.getFirstName());
            }

            if (user.getLastName() != null) {
                lastNameEditText.setText(user.getLastName());
            }

            if (user.getUserPhone() != null) {
                phoneNumberEditText.setText(user.getUserPhone());
            }

            if (user.getUserAddress() != null) {
                addressEditText.setText(user.getUserAddress());
            }
            if (user.getUserEmail() != null) {
                emailAddressEditText.setText(user.getUserEmail());
            }
        }
    }

    private boolean checkIfDataIsValid() {
        firstNameString = firstNameEditText.getText().toString();
        lastNameString = lastNameEditText.getText().toString();
        emailAddressString = emailAddressEditText.getText().toString();
        phoneNumberString = phoneNumberEditText.getText().toString();
        addressString = addressEditText.getText().toString();

        if (firstNameString.isEmpty()) {
            firstNameEditText.setError(getString(R.string.enter_first_name));
            return false;
        }
        if (lastNameString.isEmpty()) {
            lastNameEditText.setError(getString(R.string.enter_last_name));
            return false;
        }
        if (emailAddressString.isEmpty()) {
            emailAddressEditText.setError(getString(R.string.enter_email_address));
            return false;
        }

        if (!Validator.isEmailAddressValid(emailAddressString)) {
            emailAddressEditText.setError(getString(R.string.enter_valid_email_address));
            return false;
        }

        if (phoneNumberString.isEmpty()) {
            phoneNumberEditText.setError(getString(R.string.enter_phone_number));
            return false;
        }
        if (addressString.isEmpty()) {
            addressEditText.setError(getString(R.string.enter_address));
            return false;
        }
        if (donationTypeString.isEmpty() || donationTypePosition == 0) {
            Toast.makeText(getContext(), getString(R.string.select_type_of_donation), Toast.LENGTH_LONG).show();
//            donationEditText.setError(getString(R.string.enter_type_of_donation));
            return false;
        }
        return true;
    }

    private void proceed() {
        userOrderPresenter.updateUserData(firstNameString, lastNameString, phoneNumberString,
                emailAddressString, addressString, PrefUtils.getUserId(getContext()));
        if (donationType == EXTRA_DONATE_ZAKAT) {
            userOrderPresenter.donateZakat(totalCost, donationTypeString, paymentType, UserOrderPresenterImpl.DONATE_WITHOUT_PRODUCTS);
        } else {
            totalCost = userOrderPresenter.getOrderTotalCost(PrefUtils.getUserId(getContext()));
            userOrderPresenter.makeOrder(donationTypeString, paymentType, UserOrderPresenterImpl.DONATE_FOR_PRODUCTS);
        }
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onError(int resID) {
        Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();

    }

    @Override
    public void showLoading() {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null)
            progressDialog.hide();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void navigateToConfirmationScreen() {
        Intent intent = OrderConfirmationActivity.getLaunchIntent(getContext());
        intent.putExtra(EXTRA_TOTAL_COST, String.valueOf(totalCost));
        intent.putExtra(EXTRA_PAYMENT_TYPE, paymentType);
        startActivity(intent);
    }

    @Override
    public void navigateToPayment(String paymentUrl) {
        Intent intent = PaymentActivity.getLaunchIntent(getContext());
        intent.putExtra(PaymentActivity.EXTRA_URL, paymentUrl);
        startActivityForResult(intent, PAYMENT_REQUEST_CODE);
    }

    public void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivityForResult(i, PAYMENT_REQUEST_CODE);
    }

    @Override
    public void showFailedTransactionAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setMessage(getString(R.string.payment_error))
                .setNegativeButton(getString(R.string.ok), (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private void chooseOnlinePayment() {
        paymentType = ONLINE_PAYMENT_TYPE;
        onlinePayment.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        onlinePayment.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
        cashOnDelivery.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
        cashOnDelivery.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightGrey));

    }

    private void chooseCashOnDelivery() {
        paymentType = CASH_ON_DELIVERY;
        onlinePayment.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
        onlinePayment.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightGrey));
        cashOnDelivery.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        cashOnDelivery.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
    }

//    private void finishPayment(int status) {
//        if (status == PaymentActivity.PAYMENT_SUCCESS) {
//
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                if (requestCode == PAYMENT_REQUEST_CODE) {
//                    int paymentStatus = data.getIntExtra(PaymentActivity.EXTRA_PAYMENT_STATUS, PaymentActivity.PAYMENT_SUCCESS);
//                    if (paymentStatus == 0) {
//                        userOrderPresenter.finishPaymentStatus(PaymentActivity.PAYMENT_SUCCESS);
//                    } else {
//                        userOrderPresenter.finishPaymentStatus(PaymentActivity.PAYMENT_FAIL);
//                    }
                }
                break;
        }
    }
}
