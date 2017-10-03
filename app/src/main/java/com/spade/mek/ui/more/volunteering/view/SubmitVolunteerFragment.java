package com.spade.mek.ui.more.volunteering.view;

import android.app.ProgressDialog;
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
import com.spade.mek.ui.cart.view.UserDataView;
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.more.regular_products.view.ConfirmSubscriptionDialog;
import com.spade.mek.ui.more.volunteering.presenter.VolunteerPresenter;
import com.spade.mek.ui.more.volunteering.presenter.VolunteerPresenterImpl;
import com.spade.mek.utils.PrefUtils;
import com.spade.mek.utils.Validator;

/**
 * Created by Ayman Abouzeid on 6/23/17.
 */

public class SubmitVolunteerFragment extends BaseFragment implements UserDataView, ConfirmVolunteeringDialog.ConfirmActions {

    public static final String EXTRA_EVENT_ID = "EXTRA_EVENT_ID";
    private EditText firstNameEditText, lastNameEditText,
            phoneNumberEditText, emailAddressEditText, addressEditText;
    private String firstNameString, lastNameString, phoneNumberString,
            emailAddressString, addressString;
    private ProgressDialog progressDialog;

    private View fragmentView;
    private VolunteerPresenter volunteerPresenter;
    private String eventID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventID = getArguments().getString(EXTRA_EVENT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_volunteer, container, false);
        initViews();
        overrideFonts(getContext(), fragmentView);
        return fragmentView;
    }

    @Override
    protected void initPresenter() {
        volunteerPresenter = new VolunteerPresenterImpl(getContext());
        volunteerPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        firstNameEditText = (EditText) fragmentView.findViewById(R.id.first_name_edit_text);
        lastNameEditText = (EditText) fragmentView.findViewById(R.id.last_name_edit_text);
        phoneNumberEditText = (EditText) fragmentView.findViewById(R.id.phone_number_edit_text);
        emailAddressEditText = (EditText) fragmentView.findViewById(R.id.email_address_edit_text);
        addressEditText = (EditText) fragmentView.findViewById(R.id.address_edit_text);
        Button proceedBtn = (Button) fragmentView.findViewById(R.id.proceed_btn);
        proceedBtn.setOnClickListener(v -> {
            if (checkIfDataIsValid()) {
                proceed();
            }
        });
        setUserData();
    }

    private void setUserData() {
        User user = volunteerPresenter.getUser(PrefUtils.getUserId(getContext()));
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
        return true;
    }

    private void proceed() {
        volunteerPresenter.volunteer(firstNameString, lastNameString, phoneNumberString,
                emailAddressString, addressString, eventID);
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
        ConfirmVolunteeringDialog confirmVolunteeringDialog = new ConfirmVolunteeringDialog();
        confirmVolunteeringDialog.setConfirmActions(this);
        confirmVolunteeringDialog.show(getChildFragmentManager(), ConfirmSubscriptionDialog.class.getSimpleName());
    }

    @Override
    public void navigateToPayment(String paymentUrl) {
    }

    @Override
    public void openUrl(String paymentUrl) {

    }

    @Override
    public void showFailedTransactionAlert() {
    }

    @Override
    public void onDismiss() {
        finish();
    }
}
