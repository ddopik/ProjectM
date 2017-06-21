package com.spade.mek.utils;

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
}
