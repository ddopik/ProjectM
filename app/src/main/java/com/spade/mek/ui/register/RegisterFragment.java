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
import com.spade.mek.ui.cart.view.UserDataActivity;
import com.spade.mek.ui.cart.view.UserDataFragment;
import com.spade.mek.ui.home.MainActivity;
import com.spade.mek.ui.login.UserModel;
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
    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(RegisterActivity.EXTRA_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_register, container, false);
        initViews();
        overrideFonts(getContext(), fragmentView);
        return fragmentView;
    }

    @Override
    protected void initPresenter() {
        registerPresenter = new RegisterPresenterImpl(getContext());
        registerPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        firstNameEditText = fragmentView.findViewById(R.id.first_name_edit_text);
        lastNameEditText = fragmentView.findViewById(R.id.last_name_edit_text);
        phoneNumberEditText = fragmentView.findViewById(R.id.phone_number_edit_text);
        emailAddressEditText = fragmentView.findViewById(R.id.email_address_edit_text);
        addressEditText = fragmentView.findViewById(R.id.address_edit_text);
        passwordEditText = fragmentView.findViewById(R.id.password_edit_text);
        confirmPassswordEditText = fragmentView.findViewById(R.id.confirm_password_edit_text);
        Button proceedBtn = fragmentView.findViewById(R.id.register_btn);
        proceedBtn.setOnClickListener(v -> {
            if (checkIfDataIsValid()) {
                proceed();
            }
        });
    }


    private boolean checkIfDataIsValid() {
        firstNameString = firstNameEditText.getText().toString();
        lastNameString = lastNameEditText.getText().toString();
        emailAddressString = emailAddressEditText.getText().toString();
        phoneNumberString = phoneNumberEditText.getText().toString();
        addressString = addressEditText.getText().toString();
        passwordString = passwordEditText.getText().toString();
        confirmPasswordString = confirmPassswordEditText.getText().toString();

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

        if (confirmPasswordString.isEmpty()) {
            confirmPassswordEditText.setError(getString(R.string.enter_confirm_password));
            return false;
        }

        if (!passwordString.equals(confirmPasswordString)) {
            confirmPassswordEditText.setError(getString(R.string.password_mismatch));
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
            userModel.setUserAddress(addressString);
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
    public void navigate() {
        if (type == RegisterActivity.REGISTER_TYPE) {
            startActivity(MainActivity.getLaunchIntent(getContext()));
        } else if (type == RegisterActivity.CHECKOUT_TYPE) {
            Intent intent = UserDataActivity.getLaunchIntent(getContext());
            intent.putExtra(UserDataFragment.EXTRA_DONATE_TYPE, UserDataFragment.EXTRA_PAY_FOR_PRODUCTS);
            startActivity(intent);
//            startActivity(UserDataActivity.getLaunchIntent(getContext()));
        }
    }

}
