package com.spade.mek.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Ayman Abouzeid on 6/21/17.
 */

public class ShareManager {

    public static void share(String url, Context mContext) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");
        mContext.startActivity(sendIntent);
    }
}
