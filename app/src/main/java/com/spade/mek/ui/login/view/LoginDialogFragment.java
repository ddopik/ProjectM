package com.spade.mek.ui.login.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.spade.mek.ui.login.presenter.LoginPresenter;
import com.spade.mek.ui.login.presenter.LoginPresenterImpl;
import com.spade.mek.ui.login.server_login.ServerLoginActivity;
import com.spade.mek.ui.register.RegisterActivity;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class LoginDialogFragment extends DialogFragment implements LoginDialogView {

    private LoginDialogActions loginDialogActions;
    private LoginPresenter loginPresenter;
    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenterImpl(this, getContext());
        loginPresenter.initLoginManagers(getActivity());
        type = getArguments().getInt(RegisterActivity.EXTRA_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_login, container, false);
        init(dialogView);
        overrideFonts(getContext(), dialogView);
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
        loginWithFacebook.setOnClickListener(v -> loginPresenter.loginWithFacebook(LoginDialogFragment.this));
        loginWithGoogle.setOnClickListener(v -> loginPresenter.loginWithGoogle(LoginDialogFragment.this));

        register.setOnClickListener(v -> {
            dismiss();
            Intent intent = RegisterActivity.getLaunchIntent(getContext());
            intent.putExtra(RegisterActivity.EXTRA_TYPE, type);
            if (type == RegisterActivity.CHECKOUT_TYPE) {
                getActivity().finish();
            }
            startActivity(intent);
        });

        signIn.setOnClickListener(v -> {
            dismiss();
            Intent intent = ServerLoginActivity.getLaunchIntent(getContext());
            intent.putExtra(RegisterActivity.EXTRA_TYPE, type);
            if (type == RegisterActivity.CHECKOUT_TYPE) {
                getActivity().finish();
            }
            startActivity(intent);
        });

        if (type == RegisterActivity.DEFAULT_TYPE) {
            continueAsGuest.setVisibility(View.GONE);
        }
    }

    private void overrideFonts(Context context, View v) {
        if (PrefUtils.getAppLang(context).equals(PrefUtils.ARABIC_LANG)) {
            try {
                if (v instanceof ViewGroup) {
                    ViewGroup vg = (ViewGroup) v;
                    for (int i = 0; i < vg.getChildCount(); i++) {
                        View child = vg.getChildAt(i);

                        overrideFonts(context, child);
                    }
                } else if (v instanceof TextView) {
                    ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/bahij_semi_bold.ttf"));
                }
            } catch (Exception e) {
            }
        }
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
