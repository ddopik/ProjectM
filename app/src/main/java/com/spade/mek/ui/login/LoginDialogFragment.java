package com.spade.mek.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.ui.register.RegisterActivity;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class LoginDialogFragment extends DialogFragment implements LoginDialogView {

    private LoginDialogActions loginDialogActions;
    private LoginPresenter loginPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenterImpl(this, getContext());
        loginPresenter.initLoginManagers(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_login, container, false);
        init(dialogView);
        return dialogView;
    }

    private void init(View mView) {
        TextView continueAsGuest = (TextView) mView.findViewById(R.id.loginAsGuestBtn);
        Button loginWithFacebook = (Button) mView.findViewById(R.id.loginWithFacebookBtn);
        Button loginWithGoogle = (Button) mView.findViewById(R.id.loginWithGoogleBtn);
        Button signIn = (Button) mView.findViewById(R.id.loginBtn);
        Button register = (Button) mView.findViewById(R.id.registerBtn);

        continueAsGuest.setOnClickListener(v -> {
            loginDialogActions.loginAsGuest();
            dismiss();
        });
        loginWithFacebook.setOnClickListener(v -> {
//            loginDialogActions.loginWithFaceBook();
            loginPresenter.loginWithFacebook(LoginDialogFragment.this);
//            dismiss();
        });
        loginWithGoogle.setOnClickListener(v -> {
//            loginDialogActions.loginWithGoogle();
            loginPresenter.loginWithGoogle(LoginDialogFragment.this);
//            dismiss();
        });
        register.setOnClickListener(v -> {
            dismiss();
            startActivity(RegisterActivity.getLaunchIntent(getContext()));
        });
    }

    public void setLoginDialogActions(LoginDialogActions loginDialogActions) {
        this.loginDialogActions = loginDialogActions;
    }

    @Override
    public void onError(String message) {
        dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(int resID) {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void loginSuccess() {
        loginDialogActions.onLoginSuccess();
        dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginPresenter.disconnectGoogleApiClient();
    }

    @Override
    public void onStop() {
        super.onStop();
        loginPresenter.disconnectGoogleApiClient();
    }

    public interface LoginDialogActions {
        void loginAsGuest();

        void onLoginSuccess();

//        void loginWithFaceBook();

//        void loginWithGoogle();
    }
}
