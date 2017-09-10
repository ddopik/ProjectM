package com.spade.mek.ui.more.profile.view;

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
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.login.UserModel;
import com.spade.mek.ui.more.profile.presenter.EditProfilePresenter;
import com.spade.mek.ui.more.profile.presenter.EditProfilePresenterImpl;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by Ayman Abouzeid on 9/5/17.
 */

public class EditProfileFragment extends BaseFragment implements EditProfileView {

    private EditProfilePresenter editProfilePresenter;
    private View mEditProfileView;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneNumberEditText;
    private EditText addressEditText;
    private String firstNameString, lastNameString, phoneNumberString,
             addressString;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mEditProfileView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        initViews();
        return mEditProfileView;
    }

    @Override
    protected void initPresenter() {
        editProfilePresenter = new EditProfilePresenterImpl(getContext());
        editProfilePresenter.setView(this);
    }

    @Override
    protected void initViews() {
        EditText emailAddressEditText = (EditText) mEditProfileView.findViewById(R.id.email_address_edit_text);
        firstNameEditText = (EditText) mEditProfileView.findViewById(R.id.first_name_edit_text);
        lastNameEditText = (EditText) mEditProfileView.findViewById(R.id.last_name_edit_text);
        phoneNumberEditText = (EditText) mEditProfileView.findViewById(R.id.phone_number_edit_text);
        addressEditText = (EditText) mEditProfileView.findViewById(R.id.address_edit_text);
        Button proceedBtn = (Button) mEditProfileView.findViewById(R.id.proceed_btn);

        emailAddressEditText.setVisibility(View.GONE);
        proceedBtn.setOnClickListener(v -> {
            setUserModel();
            proceed();
        });
        setUserData();
    }

    private void setUserData() {
        User user = editProfilePresenter.getUser(PrefUtils.getUserId(getContext()));
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
        }
    }

    private void setUserModel() {
        firstNameString = firstNameEditText.getText().toString();
        lastNameString = lastNameEditText.getText().toString();
        phoneNumberString = phoneNumberEditText.getText().toString();
        addressString = addressEditText.getText().toString();
    }

    private void proceed() {
        UserModel userModel = new UserModel();
        userModel.setFirstName(firstNameString);
        userModel.setLastName(lastNameString);
        userModel.setUserAddress(addressString);
        userModel.setUserPhone(phoneNumberString);

        editProfilePresenter.editProfile(userModel);
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
}
