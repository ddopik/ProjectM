package com.spade.mek.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.ui.home.MainActivity;
import com.spade.mek.ui.login.view.LoginActivity;
import com.spade.mek.ui.more.MorePresenterImpl;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;

import java.util.Locale;

/**
 * Created by spade on 6/7/17.
 */

public class SplashActivity extends AppCompatActivity {

    private static final long DELAY_MILLIS = 1000;
    private ProgressBar progressBar;

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = findViewById(R.id.logo_image_view);
        progressBar = findViewById(R.id.progress_bar);
        String appLang = PrefUtils.getAppLang(getApplicationContext());
        imageView.setImageResource(ImageUtils.getSplashLogo(appLang));
        Log.d("Language", PrefUtils.getUserToken(this));
        changeLanguage();
//        counterToNavigate();
        saveNotificationToken();
    }

    private void counterToNavigate() {
        Handler handler = new Handler();
        Runnable runnable = this::navigate;
        handler.postDelayed(runnable, DELAY_MILLIS);
    }

    private void navigate() {
        int loginProvider = PrefUtils.getLoginProvider(this);
        if (PrefUtils.isFirstLaunch(this)) {

        } else if (loginProvider == LoginProviders.NONE.getLoginProviderCode()) {
            startActivity(LoginActivity.getLaunchIntent(this));
        } else {
            startActivity(MainActivity.getLaunchIntent(this));
        }
        finish();
    }

    @SuppressWarnings("deprecation")
    public void changeLanguage() {
        Locale locale;
        if (PrefUtils.isLanguageSelected(this)) {
            if (PrefUtils.getAppLang(this).equals(PrefUtils.ARABIC_LANG)) {
                locale = new Locale(MorePresenterImpl.AR_LANG);
            } else {
                locale = new Locale(MorePresenterImpl.EN_LANG);
            }
            Configuration conf = new Configuration();
            conf.locale = locale;
            getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
        } else {
            String deviceLang = Locale.getDefault().getLanguage();
            if (!deviceLang.equals(PrefUtils.ARABIC_LANG)) {
                PrefUtils.setAppLang(this, PrefUtils.ENGLISH_LANG);
            } else {
                PrefUtils.setAppLang(this, PrefUtils.ARABIC_LANG);
            }
        }
    }

    private void saveNotificationToken() {
        if (!PrefUtils.isTokenSaved(this)) {
            progressBar.setVisibility(View.VISIBLE);
            ApiHelper.saveToken(PrefUtils.getNotificationToken(this), new ApiHelper.SaveTokenCallBacks() {
                @Override
                public void onTokenSavedSuccess() {
                    PrefUtils.setIsTokenSaved(getApplicationContext(), true);
                    progressBar.setVisibility(View.GONE);
                    navigate();
//                    counterToNavigate();
                }

                @Override
                public void onTokenSavedFailed(String error) {
                    PrefUtils.setIsTokenSaved(getApplicationContext(), false);
                    progressBar.setVisibility(View.GONE);
                    navigate();
//                    counterToNavigate();
                }
            });
        } else {
            counterToNavigate();
        }
    }
}
