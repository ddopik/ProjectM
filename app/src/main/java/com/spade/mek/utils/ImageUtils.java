package com.spade.mek.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.spade.mek.R;

/**
 * Created by Ayman Abouzeid on 6/18/17.
 */

public class ImageUtils {

    public static int getDefaultImage(String appLang) {
        if (appLang.equals(PrefUtils.ENGLISH_LANG)) {
            return R.drawable.ic_en_default;
        } else {
            return R.drawable.ic_ar_default;
        }
    }

    public static int getSplashLogo(String appLang) {
        if (appLang.equals(PrefUtils.ENGLISH_LANG)) {
            return R.drawable.ic_logo_splash_en;
        } else {
            return R.drawable.ic_logo_splash_ar;
        }
    }

    public static int getOrderConfirmationLogo(String appLang) {
        if (appLang.equals(PrefUtils.ENGLISH_LANG)) {
            return R.drawable.ic_logo_confirmation_en;
        } else {
            return R.drawable.ic_logo_confirmation_ar;
        }
    }

    public static int getArrow(String appLang) {
        if (appLang.equals(PrefUtils.ENGLISH_LANG)) {
            return R.drawable.ic_arrow_right;
        } else {
            return R.drawable.ic_arrow_left;
        }
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
