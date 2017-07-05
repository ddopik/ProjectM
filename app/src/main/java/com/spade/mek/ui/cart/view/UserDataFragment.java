package com.spade.mek.ui.cart.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.cart.presenter.UserOrderPresenter;
import com.spade.mek.ui.cart.presenter.UserOrderPresenterImpl;
import com.spade.mek.ui.login.User;
import com.spade.mek.utils.PrefUtils;
import com.spade.mek.utils.Validator;

/**
 * Created by Ayman Abouzeid on 6/23/17.
 */

public class UserDataFragment extends BaseFragment implements UserDataView {

    public static final String EXTRA_TOTAL_COST = "EXTRA_TOTAL_COST";
    private EditText firstNameEditText, lastNameEditText,
            phoneNumberEditText, emailAddressEditText, addressEditText;
    //    , donationEditText;
    private String firstNameString, lastNameString, phoneNumberString,
            emailAddressString, addressString, donationTypeString = "";
    private ProgressDialog progressDialog;

    private View fragmentView;
    private UserOrderPresenter userOrderPresenter;

    private double totalCost;
    private int donationTypePosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_user_data, container, false);
        initViews();
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
//        donationEditText = (EditText) fragmentView.findViewById(R.id.type_of_donation_edit_text);
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
                donationTypeString = getResources().getStringArray(R.array.type_of_donations)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        totalCost = userOrderPresenter.getOrderTotalCost(PrefUtils.getUserId(getContext()));
        userOrderPresenter.makeOrder(donationTypeString);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onError(int resID) {
        Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();

    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, UserDataFragment.class);
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
        startActivity(intent);
    }
}
