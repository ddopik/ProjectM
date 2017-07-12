package com.spade.mek.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.home.MainActivity;
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
        return mView;
    }

    @Override
    protected void initPresenter() {
        mLoginPresenter = new LoginPresenterImpl(this, getContext());
        mLoginPresenter.initLoginManagers(getActivity());
    }

    @Override
    protected void initViews() {
        Button continueAsGuest = (Button) mView.findViewById(R.id.loginAsGuestBtn);
        Button loginWithFacebook = (Button) mView.findViewById(R.id.loginWithFacebookBtn);
        Button loginWithGoogle = (Button) mView.findViewById(R.id.loginWithGoogleBtn);

        ImageView imageView = (ImageView) mView.findViewById(R.id.logo_image_view);
        String appLang = PrefUtils.getAppLang(getContext());
        imageView.setImageResource(ImageUtils.getSplashLogo(appLang));

        continueAsGuest.setOnClickListener(v -> mLoginPresenter.loginAsGuest());
        loginWithFacebook.setOnClickListener(v -> mLoginPresenter.loginWithFacebook(this));
        loginWithGoogle.setOnClickListener(v -> mLoginPresenter.loginWithGoogle(this));
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