package com.spade.mek.ui.MainLanguage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.spade.mek.R;
import com.spade.mek.base.BaseActivity;
import com.spade.mek.ui.login.view.LoginActivity;
import com.spade.mek.ui.more.contact_us.view.ContactUsActivity;
import com.spade.mek.utils.PrefUtils;

import java.util.Locale;

/**
 * Created by abdalla-maged on 2/12/18.
 */

public class MainLanguageActivity extends AppCompatActivity {

    public static final String AR_LANG = "ar";
    public static final String EN_LANG = "en_US";

    private Button arabic_btn;
    private Button eng_btn;

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, MainLanguageActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_language_activity);
        intiView();
    }

    public void intiView() {


        arabic_btn = findViewById(R.id.arabic_btn);
        eng_btn = findViewById(R.id.eng_btn);

        arabic_btn.setOnClickListener(view -> {
            changeLanguage(AR_LANG);
        });
        eng_btn.setOnClickListener(view -> {
            changeLanguage(EN_LANG);
        });
    }

    @SuppressWarnings("deprecation")
    public void changeLanguage(String lang) {
        Locale myLocale = new Locale(lang);
        Configuration conf = new Configuration();
        conf.locale = myLocale;

        getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
        if (lang.equals(AR_LANG))
            PrefUtils.setAppLang(this, PrefUtils.ARABIC_LANG);
        else
            PrefUtils.setAppLang(this, PrefUtils.ENGLISH_LANG);
        PrefUtils.setIsLanguageSelected(this, true);
        startActivity(LoginActivity.getLaunchIntent(this));
    }

}
