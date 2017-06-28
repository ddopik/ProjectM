package com.spade.mek.ui.cart.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spade.mek.R;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class LoginDialogFragment extends DialogFragment {

    private LoginDialogActions loginDialogActions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_login, container, false);
        init(dialogView);
        return dialogView;
    }

    private void init(View mView) {
        Button continueAsGuest = (Button) mView.findViewById(R.id.loginAsGuestBtn);
        Button loginWithFacebook = (Button) mView.findViewById(R.id.loginWithFacebookBtn);
        Button loginWithGoogle = (Button) mView.findViewById(R.id.loginWithGoogleBtn);

        continueAsGuest.setOnClickListener(v -> {
            loginDialogActions.loginAsGuest();
            dismiss();
        });
        loginWithFacebook.setOnClickListener(v -> {
            loginDialogActions.loginWithFaceBook();
            dismiss();
        });
        loginWithGoogle.setOnClickListener(v -> {
            loginDialogActions.loginWithGoogle();
            dismiss();
        });
    }

    public void setLoginDialogActions(LoginDialogActions loginDialogActions) {
        this.loginDialogActions = loginDialogActions;
    }

    public interface LoginDialogActions {
        void loginAsGuest();

        void loginWithFaceBook();

        void loginWithGoogle();
    }
}
