package com.spade.mek.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ayman Abouzeid on 6/12/17.
 */

public class PrefUtils {
    private static final String PREF_FILE_NAME = "MEK_PREF_FILE";
    private static final String LOGIN_PROVIDER = "LOGIN_PROVIDER";
    private static final String USER_ID = "USER_ID";
    private static final String USER_TOKEN = "USER_TOKEN";
    private static final String IS_FIRST_LAUNCH = "IS_FIRST_LAUNCH";
    private static final String APP_LANG = "APP_LANG";
    public static final String ARABIC_LANG = "ar";
    public static final String ENGLISH_LANG = "en";

    private static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void setLoginProvider(Context context, int loginProvider) {
        getSharedPref(context).edit().putInt(LOGIN_PROVIDER, loginProvider).apply();
    }

    public static int getLoginProvider(Context context) {
        return getSharedPref(context).getInt(LOGIN_PROVIDER, LoginProviders.NONE.getLoginProviderCode());
    }

    public static void setUserID(Context context, String userID) {
        getSharedPref(context).edit().putString(USER_ID, userID).apply();
    }

    public static void setUserToken(Context context, String userToken) {
        getSharedPref(context).edit().putString(USER_TOKEN, userToken).apply();
    }

    public static void setIsFirstLaunch(Context context, boolean isFirstLaunch) {
        getSharedPref(context).edit().putBoolean(IS_FIRST_LAUNCH, isFirstLaunch).apply();
    }

    public static boolean isFirstLaunch(Context context) {
        return getSharedPref(context).getBoolean(IS_FIRST_LAUNCH, true);
    }

    public static String getAppLang(Context context) {
        return getSharedPref(context).getString(APP_LANG, ENGLISH_LANG);
    }

    public void setAppLang(Context context, String appLang) {
        getSharedPref(context).edit().putString(APP_LANG, appLang).apply();
    }
}
