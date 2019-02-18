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

/**
 * Created by Ayman Abouzeid on 9/10/17.
 */

public class ChangePasswordFragment extends Fragment {
    private View view;
    private EditText passwordEditText, codeEditText;
    private String passwordString, codeString;
    private ProgressDialog progressDialog;
    private ChangePasswordInterface changePasswordInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change_password, container, false);
        init();
        return view;
    }

    private void init() {
        Button resetPasswordBtn = (Button) view.findViewById(R.id.reset_password_btn);
        passwordEditText = (EditText) view.findViewById(R.id.password_edit_text);
        codeEditText = (EditText) view.findViewById(R.id.code_edit_text);
        resetPasswordBtn.setOnClickListener(v -> {
            if (checkIfDataIsValid()) {
                changePassword();
            }
        });
    }

    private boolean checkIfDataIsValid() {
        passwordString = passwordEditText.getText().toString();
        codeString = codeEditText.getText().toString();

        if (passwordString.isEmpty()) {
            passwordEditText.setError(getString(R.string.enter_password));
            return false;
        }

        if (codeString.isEmpty()) {
            codeEditText.setError(getString(R.string.enter_code));
            return false;
        }

        return true;
    }

    private void changePassword() {
        showProgressDialog();
        ApiHelper.changePassword(passwordString, codeString, new ApiHelper.ChangePasswordActions() {
            @Override
            public void onPasswordChangedSuccess() {
                hideProgressDialog();
                changePasswordInterface.passwordChanged();
            }

            @Override
            public void onPasswordChangedFail(String error) {
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

    public void setChangePasswordInterface(ChangePasswordInterface changePasswordInterface) {
        this.changePasswordInterface = changePasswordInterface;
    }

    public interface ChangePasswordInterface {
        void passwordChanged();
    }
}
