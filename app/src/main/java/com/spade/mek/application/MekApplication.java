package com.spade.mek.application;

import android.app.Application;

import com.spade.sociallogin.FacebookLoginManager;

/**
 * Created by spade on 6/7/17.
 */

public class MekApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookLoginManager.initFacebookEvents(this);
    }
}
