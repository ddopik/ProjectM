package com.spade.mek.ui.login.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.utils.Validator;

/**
 * Created by Ayman Abouzeid on 9/10/17.
 */

public class ForgetPasswordFragment extends Fragment {
    private View view;
    private EditText emailAddressEditText;
    private String emailAddressString;
    private ProgressDialog progressDialog;
    private NavigateInterface navigateInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        init();
        return view;
    }

    private void init() {
        Button sendCodeBtn = (Button) view.findViewById(R.id.send_code_btn);
        emailAddressEditText = (EditText) view.findViewById(R.id.email_address_edit_text);
        sendCodeBtn.setOnClickListener(v -> {
            if (checkIfDataIsValid()) {
                sendCode();
            }
        });
    }

    private boolean checkIfDataIsValid() {
        emailAddressString = emailAddressEditText.getText().toString();
        if (emailAddressString.isEmpty()) {
            emailAddressEditText.setError(getString(R.string.enter_email_address));
            return false;
        }

        if (!Validator.isEmailAddressValid(emailAddressString)) {
            emailAddressEditText.setError(getString(R.string.enter_valid_email_address));
            return false;
        }
        return true;
    }

    private void sendCode() {
        showProgressDialog();
        ApiHelper.sendMeCode(emailAddressString, new ApiHelper.SendCodeActions() {
            @Override
            public void onCodeSent() {
                hideProgressDialog();
                navigateInterface.navigateToChangePassword();
            }

            @Override
            public void onCodeSendFail(String error) {
                hideProgressDialog();
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showProgressDialog() {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null)
            progressDialog.hide();
    }

    public void setNavigateInterface(NavigateInterface navigateInterface) {
        this.navigateInterface = navigateInterface;
    }

    public interface NavigateInterface {
        void navigateToChangePassword();
    }
}
