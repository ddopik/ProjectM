package com.spade.mek.ui.more;

import android.content.Context;
import android.content.res.Configuration;

import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.utils.LoginProviders;
import com.spade.mek.utils.PrefUtils;

import java.util.Locale;

/**
 * Created by Ayman Abouzeid on 6/28/17.
 */

public class MorePresenterImpl implements MorePresenter {
    public static final String AR_LANG = "ar";
    public static final String EN_LANG = "en_US";
    private Context context;
    private RealmDbHelper realmDbHelper;
    private MoreView moreView;

    public MorePresenterImpl(Context context) {
        this.context = context;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(MoreView view) {
        this.moreView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public void login() {

    }

    @Override
    public void logout() {
        PrefUtils.setLoginProvider(context, LoginProviders.NONE.getLoginProviderCode());
        realmDbHelper.deleteUser(PrefUtils.getUserId(context));
        realmDbHelper.deleteAllCartItems(PrefUtils.getUserId(context));
        PrefUtils.setUserID(context, PrefUtils.GUEST_USER_ID);
        moreView.navigateToLoginScreen();
    }


    @SuppressWarnings("deprecation")
    @Override
    public void changeLanguage(String lang) {
        Locale myLocale = new Locale(lang);
        Configuration conf = new Configuration();
        conf.locale = myLocale;

        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
        if (lang.equals(AR_LANG))
            PrefUtils.setAppLang(context, PrefUtils.ARABIC_LANG);
        else
            PrefUtils.setAppLang(context, PrefUtils.ENGLISH_LANG);
        PrefUtils.setIsLanguageSelected(context, true);
        moreView.restartActivity();
    }

}
