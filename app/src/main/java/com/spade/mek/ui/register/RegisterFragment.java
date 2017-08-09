package com.spade.mek.ui.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.home.MainActivity;
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.login.UserModel;
import com.spade.mek.utils.PrefUtils;
import com.spade.mek.utils.Validator;

/**
 * Created by Ayman Abouzeid on 6/23/17.
 */

public class RegisterFragment extends BaseFragment implements RegisterView {

    private EditText firstNameEditText, lastNameEditText,
            phoneNumberEditText, emailAddressEditText, addressEditText, passwordEditText, confirmPassswordEditText;
    private String firstNameString, lastNameString, phoneNumberString,
            emailAddressString, addressString, passwordString, confirmPasswordString;
    private ProgressDialog progressDialog;

    private View fragmentView;
    private RegisterPresenter registerPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_register, container, false);
        initViews();
        return fragmentView;
    }

    @Override
    protected void initPresenter() {
        registerPresenter = new RegisterPresenterImpl(getContext());
        registerPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        firstNameEditText = (EditText) fragmentView.findViewById(R.id.first_name_edit_text);
        lastNameEditText = (EditText) fragmentView.findViewById(R.id.last_name_edit_text);
        phoneNumberEditText = (EditText) fragmentView.findViewById(R.id.phone_number_edit_text);
        emailAddressEditText = (EditText) fragmentView.findViewById(R.id.email_address_edit_text);
        addressEditText = (EditText) fragmentView.findViewById(R.id.address_edit_text);
        passwordEditText = (EditText) fragmentView.findViewById(R.id.password_edit_text);
        Button proceedBtn = (Button) fragmentView.findViewById(R.id.register_btn);
        proceedBtn.setOnClickListener(v -> {
            if (checkIfDataIsValid()) {
                proceed();
            }
        });
//        setUserData();
    }

    private void setUserData() {
        User user = registerPresenter.getUser(PrefUtils.getUserId(getContext()));
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
        passwordString = passwordEditText.getText().toString();

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
        if (passwordString.isEmpty()) {
            passwordEditText.setError(getString(R.string.enter_password));
            return false;
        }
        return true;
    }

    private void proceed() {
        UserModel userModel = new UserModel();
        userModel.setFirstName(firstNameString);
        userModel.setLastName(lastNameString);
        userModel.setUserEmail(emailAddressString);
        userModel.setUserPhone(phoneNumberString);
        userModel.setPassword(passwordString);
        if (addressString.isEmpty()) {
            userModel.setUserAddress("");
        } else {
            userModel.getUserAddress();
        }
        registerPresenter.register(userModel);
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
    public void navigateToHomeScreen() {
        Intent intent = MainActivity.getLaunchIntent(getContext());
        startActivity(intent);
    }

}
