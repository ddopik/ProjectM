package com.spade.mek.ui.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.spade.mek.R;
import com.spade.mek.ui.home.MainActivity;
import com.spade.mek.ui.login.LoginActivity;
import com.spade.mek.utils.ImageUtils;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;

/**
 * Created by spade on 6/7/17.
 */

public class SplashActivity extends AppCompatActivity {

    private static final long DELAY_MILLIS = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = (ImageView) findViewById(R.id.logo_image_view);
        String appLang = PrefUtils.getAppLang(getApplicationContext());
        imageView.setImageResource(ImageUtils.getSplashLogo(appLang));

        counterToNavigate();
    }

    private void counterToNavigate() {
        Handler handler = new Handler();
        Runnable runnable = this::navigate;
        handler.postDelayed(runnable, DELAY_MILLIS);
    }

    private void navigate() {
        int loginProvider = PrefUtils.getLoginProvider(this);
        if (loginProvider == LoginProviders.NONE.getLoginProviderCode()) {
            startActivity(LoginActivity.getLaunchIntent(this));
        } else {
            startActivity(MainActivity.getLaunchIntent(this));
        }
        finish();
    }
}
