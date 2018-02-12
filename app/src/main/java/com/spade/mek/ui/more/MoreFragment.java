package com.spade.mek.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;

import com.spade.mek.R;
import com.spade.mek.base.BaseFragment;
import com.spade.mek.ui.login.view.LoginActivity;
import com.spade.mek.ui.login.view.LoginDialogFragment;
import com.spade.mek.ui.more.about.view.AboutUsActivity;
import com.spade.mek.ui.more.contact_us.view.ContactUsActivity;
import com.spade.mek.ui.more.donation_channels.view.DonationChannelsActivity;
import com.spade.mek.ui.more.news.view.NewsActivity;
import com.spade.mek.ui.more.profile.view.ProfileActivity;
import com.spade.mek.ui.more.regular_products.view.RegularProductsActivity;
import com.spade.mek.ui.more.zakat_calculator.ZakatCalculatorActivity;
import com.spade.mek.ui.register.RegisterActivity;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class MoreFragment extends BaseFragment implements MoreView, LoginDialogFragment.LoginDialogActions {

    private boolean isLoggedIn = false;
    private View moreView;
    private MorePresenter morePresenter;
    private Button logBtn;
    private RelativeLayout profileLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        moreView = inflater.inflate(R.layout.fragment_more, container, false);
        initViews();
        overrideFonts(getContext(), moreView);
        return moreView;
    }

    @Override
    protected void initPresenter() {
        morePresenter = new MorePresenterImpl(getContext());
        morePresenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    protected void initViews() {
        String appLang = PrefUtils.getAppLang(getContext());
        profileLayout = moreView.findViewById(R.id.profile_layout);
        RelativeLayout newsLayout = moreView.findViewById(R.id.news_layout);
        RelativeLayout donationsLayout = moreView.findViewById(R.id.donation_channels_layout);
        RelativeLayout contactUsLayout = moreView.findViewById(R.id.contact_us_layout);
        RelativeLayout zakatCalculatorLayout = moreView.findViewById(R.id.zakat_calculator_layout);
        RelativeLayout regularDonationsLayout = moreView.findViewById(R.id.regular_donation_layout);
        RelativeLayout AboutLayout = moreView.findViewById(R.id.about_layout);
//        RelativeLayout volunteeringLayout =   moreView.findViewById(R.id.volunteering_layout);

        ImageView arrowImage = moreView.findViewById(R.id.arrow_image);
        ImageView arrowImage1 = moreView.findViewById(R.id.arrow_image_1);
        ImageView arrowImage2 = moreView.findViewById(R.id.arrow_image_2);
        ImageView arrowImage3 = moreView.findViewById(R.id.arrow_image_3);
        ImageView arrowImage4 = moreView.findViewById(R.id.arrow_image_4);
        ImageView arrowImage5 = moreView.findViewById(R.id.arrow_image_5);
        ImageView arrowImage6 = moreView.findViewById(R.id.arrow_image_6);
        ImageView arrowImage7 = moreView.findViewById(R.id.arrow_image_7);

        if (appLang.equals(PrefUtils.ARABIC_LANG)) {
            arrowImage.setImageResource(R.drawable.rotated_triangle_image);
            arrowImage1.setImageResource(R.drawable.rotated_triangle_image);
            arrowImage2.setImageResource(R.drawable.rotated_triangle_image);
            arrowImage3.setImageResource(R.drawable.rotated_triangle_image);
            arrowImage4.setImageResource(R.drawable.rotated_triangle_image);
            arrowImage5.setImageResource(R.drawable.rotated_triangle_image);
            arrowImage6.setImageResource(R.drawable.rotated_triangle_image);
            arrowImage7.setImageResource(R.drawable.rotated_triangle_image);

        }

        AppCompatSpinner languageSpinner = moreView.findViewById(R.id.language_spinner);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.type_of_donation_item, getResources().getStringArray(R.array.languages));
        languageSpinner.setAdapter(spinnerAdapter);

        logBtn = moreView.findViewById(R.id.log_button);
        logBtn.setOnClickListener(v -> {
            if (!isLoggedIn) {
                showLoginDialog();
            } else {
                morePresenter.logout();
            }
        });

        contactUsLayout.setOnClickListener(v ->
                startActivity(ContactUsActivity.getLaunchIntent(getContext())));
        newsLayout.setOnClickListener(v -> startActivity(NewsActivity.getLaunchIntent(getContext())));
        donationsLayout.setOnClickListener(v -> startActivity(DonationChannelsActivity.getLaunchIntent(getContext())));
        zakatCalculatorLayout.setOnClickListener(v -> startActivity(ZakatCalculatorActivity.getLaunchIntent(getContext())));
        regularDonationsLayout.setOnClickListener(v -> startActivity(RegularProductsActivity.getLaunchIntent(getContext())));
//        volunteeringLayout.setOnClickListener(v -> startActivity(VolunteeringActivity.getLaunchIntent(getContext())));
        profileLayout.setOnClickListener(v -> startActivity(ProfileActivity.getLaunchIntent(getContext())));
        AboutLayout.setOnClickListener(v -> startActivity(AboutUsActivity.getLaunchIntent(getContext())));
        if (PrefUtils.getAppLang(getContext()).equals(PrefUtils.ENGLISH_LANG)) {
            languageSpinner.setSelection(0);
        } else {
            languageSpinner.setSelection(1);
        }

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && !PrefUtils.getAppLang(getContext()).equals(PrefUtils.ENGLISH_LANG))
                    morePresenter.changeLanguage(MorePresenterImpl.EN_LANG);
                if (position == 1 && !PrefUtils.getAppLang(getContext()).equals(PrefUtils.ARABIC_LANG))
                    morePresenter.changeLanguage(MorePresenterImpl.AR_LANG);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        updateUI();
    }

    private void updateUI() {
        isLoggedIn = PrefUtils.getLoginProvider(getContext()) != LoginProviders.NONE.getLoginProviderCode();
        if (isLoggedIn) {
            profileLayout.setVisibility(View.VISIBLE);
            logBtn.setText(getString(R.string.logout));
        } else {
            profileLayout.setVisibility(View.GONE);
            logBtn.setText(getString(R.string.login));
        }
    }

    private void showLoginDialog() {
        Bundle bundle = new Bundle();
        bundle.putInt(RegisterActivity.EXTRA_TYPE, RegisterActivity.DEFAULT_TYPE);
        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
        loginDialogFragment.setLoginDialogActions(this);
        loginDialogFragment.setArguments(bundle);
        loginDialogFragment.show(getChildFragmentManager(), LoginDialogFragment.class.getSimpleName());
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onError(int resID) {

    }

    @Override
    public void loginAsGuest() {

    }

    @Override
    public void onLoginSuccess() {
        updateUI();
    }

    @Override
    public void navigateToLoginScreen() {
        startActivity(LoginActivity.getLaunchIntent(getContext()));
        getActivity().finish();
    }

    @Override
    public void restartActivity() {
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }
}
