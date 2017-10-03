package com.spade.mek.ui.login.server_login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.cart.view.UserDataActivity;
import com.spade.mek.ui.cart.view.UserDataFragment;
import com.spade.mek.ui.home.MainActivity;
import com.spade.mek.ui.login.UserModel;
import com.spade.mek.ui.login.presenter.LoginPresenter;
import com.spade.mek.ui.login.presenter.LoginPresenterImpl;
import com.spade.mek.ui.login.view.ForgetPasswordActivity;
import com.spade.mek.ui.login.view.LoginView;
import com.spade.mek.ui.register.RegisterActivity;
import com.spade.mek.utils.Validator;

/**
 * Created by Ayman Abouzeid on 6/23/17.
 */

public class ServerLoginFragment extends BaseFragment implements LoginView {

    private EditText emailAddressEditText, passwordEditText;
    private String emailAddressString, passwordString;
    private ProgressDialog progressDialog;

    private View fragmentView;
    private LoginPresenter loginPresenter;
    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(RegisterActivity.EXTRA_TYPE);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_server_login, container, false);
        initViews();
        overrideFonts(getContext(), fragmentView);
        return fragmentView;
    }

    @Override
    protected void initPresenter() {
        loginPresenter = new LoginPresenterImpl(this, getContext());
        loginPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        emailAddressEditText = (EditText) fragmentView.findViewById(R.id.email_address_edit_text);
        passwordEditText = (EditText) fragmentView.findViewById(R.id.password_edit_text);
        Button proceedBtn = (Button) fragmentView.findViewById(R.id.register_btn);
        TextView forgetPassword = (TextView) fragmentView.findViewById(R.id.forget_password_text_view);
        proceedBtn.setOnClickListener(v -> {
            if (checkIfDataIsValid()) {
                proceed();
            }
        });
        forgetPassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgetPassword.setOnClickListener(v -> startActivity(ForgetPasswordActivity.getLaunchIntent(getContext())));
    }

    private boolean checkIfDataIsValid() {
        emailAddressString = emailAddressEditText.getText().toString();
        passwordString = passwordEditText.getText().toString();

        if (emailAddressString.isEmpty()) {
            emailAddressEditText.setError(getString(R.string.enter_email_address));
            return false;
        }

        if (!Validator.isEmailAddressValid(emailAddressString)) {
            emailAddressEditText.setError(getString(R.string.enter_valid_email_address));
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
        userModel.setUserEmail(emailAddressString);
        userModel.setPassword(passwordString);
        loginPresenter.serverLogin(userModel);
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
        }else{}
    }

    @Override
    public void navigateToMainScreen() {

    }
}
