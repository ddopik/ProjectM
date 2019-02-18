package com.spade.mek.ui.login.view;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.home.MainActivity;
import com.spade.mek.ui.login.presenter.LoginPresenter;
import com.spade.mek.ui.login.presenter.LoginPresenterImpl;
import com.spade.mek.ui.login.server_login.ServerLoginActivity;
import com.spade.mek.ui.register.RegisterActivity;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public class LoginFragment extends BaseFragment implements LoginView {

    private LoginPresenter mLoginPresenter;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();
        overrideFonts(getContext(), mView);
        return mView;
    }

    @Override
    protected void initPresenter() {
        mLoginPresenter = new LoginPresenterImpl(this, getContext());
        mLoginPresenter.initLoginManagers(getActivity());
    }

    @Override
    protected void initViews() {
        TextView continueAsGuest = (TextView) mView.findViewById(R.id.loginAsGuestBtn);
        Button loginWithFacebook = (Button) mView.findViewById(R.id.loginWithFacebookBtn);
        Button loginWithGoogle = (Button) mView.findViewById(R.id.loginWithGoogleBtn);
        Button signInButton = (Button) mView.findViewById(R.id.loginBtn);
        Button registerButton = (Button) mView.findViewById(R.id.registerBtn);

        ImageView imageView = (ImageView) mView.findViewById(R.id.logo_image_view);
        String appLang = PrefUtils.getAppLang(getContext());
        imageView.setImageResource(ImageUtils.getSplashLogo(appLang));

        continueAsGuest.setOnClickListener(v -> mLoginPresenter.loginAsGuest());
        loginWithFacebook.setOnClickListener(v -> mLoginPresenter.loginWithFacebook(this));
        loginWithGoogle.setOnClickListener(v -> mLoginPresenter.loginWithGoogle(this));
        registerButton.setOnClickListener(v -> {
            Intent intent = RegisterActivity.getLaunchIntent(getContext());
            intent.putExtra(RegisterActivity.EXTRA_TYPE, RegisterActivity.REGISTER_TYPE);
            startActivity(intent);
        });
        signInButton.setOnClickListener(v -> startActivity(ServerLoginActivity.getLaunchIntent(getContext())));
        continueAsGuest.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void navigate() {
        startActivity(MainActivity.getLaunchIntent(getContext()));
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(MainActivity.getLaunchIntent(getContext()));
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop() {
        super.onStop();
        mLoginPresenter.disconnectGoogleApiClient();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoginPresenter.disconnectGoogleApiClient();
    }
}
