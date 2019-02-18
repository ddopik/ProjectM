package com.spade.mek.ui.more.contact_us.view;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.more.contact_us.presenter.SendMessagePresenter;
import com.spade.mek.ui.more.contact_us.presenter.SendMessagePresenterImpl;
import com.spade.mek.utils.PrefUtils;
import com.spade.mek.utils.Validator;

/**
 * Created by Ayman Abouzeid on 6/23/17.
 */

public class SendMessageFragment extends BaseFragment implements SendMessageView {

    private EditText userNameEditText,
            subjectEditText, emailAddressEditText, messageEditText, cityEditText;
    private String userNameString,
            emailAddressString, subjectString, cityString, messageString;
    private ProgressDialog progressDialog;

    private View fragmentView;
    private SendMessagePresenter sendMessagePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_send_messsage, container, false);
        initViews();
        overrideFonts(getContext(), fragmentView);
        return fragmentView;
    }

    @Override
    protected void initPresenter() {
        sendMessagePresenter = new SendMessagePresenterImpl(getContext());
        sendMessagePresenter.setView(this);
    }

    @Override
    protected void initViews() {
        userNameEditText = (EditText) fragmentView.findViewById(R.id.user_name_edit_text);
        subjectEditText = (EditText) fragmentView.findViewById(R.id.subject_edit_text);
        cityEditText = (EditText) fragmentView.findViewById(R.id.city_edit_text);
        emailAddressEditText = (EditText) fragmentView.findViewById(R.id.email_address_edit_text);
        messageEditText = (EditText) fragmentView.findViewById(R.id.message_edit_text);
        Button proceedBtn = (Button) fragmentView.findViewById(R.id.send_message);
        proceedBtn.setOnClickListener(v -> {
            if (checkIfDataIsValid()) {
                proceed();
            }
        });
        setUserData();
    }

    private void setUserData() {
        User user = sendMessagePresenter.getUser(PrefUtils.getUserId(getContext()));
        if (user != null) {
            if (user.getFirstName() != null) {
                userNameEditText.setText(user.getFirstName());
            }

            if (user.getUserEmail() != null) {
                emailAddressEditText.setText(user.getUserEmail());
            }
        }
    }

    private boolean checkIfDataIsValid() {
        userNameString = userNameEditText.getText().toString();
        cityString = cityEditText.getText().toString();
        emailAddressString = emailAddressEditText.getText().toString();
        subjectString = subjectEditText.getText().toString();
        messageString = messageEditText.getText().toString();

        if (userNameString.isEmpty()) {
            userNameEditText.setError(getString(R.string.enter_user_name));
            return false;
        }
        if (cityString.isEmpty()) {
            cityEditText.setError(getString(R.string.enter_city));
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

        if (subjectString.isEmpty()) {
            subjectEditText.setError(getString(R.string.enter_subject));
            return false;
        }
        if (messageString.isEmpty()) {
            messageEditText.setError(getString(R.string.enter_message));
            return false;
        }
        return true;
    }

    private void proceed() {
        sendMessagePresenter.sendMessage(userNameString, cityString, subjectString, emailAddressString, messageString);
    }

    @Override
    public void onError(String message) {
        if (getContext() != null)
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onError(int resID) {
        if (getContext() != null)
            Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();

    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, SendMessageFragment.class);
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
        Intent intent = MessageConfirmationActivity.getLaunchIntent(getContext());
        startActivity(intent);
    }
}
