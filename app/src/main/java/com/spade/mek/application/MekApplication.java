package com.spade.mek.application;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.spade.mek.R;
import com.spade.mek.realm.RealmConfig;
import com.spade.mek.realm.RealmDbMigration;
import com.spade.mek.realm.RealmModules;
import com.spade.mek.utils.PrefUtils;
import com.spade.sociallogin.FacebookLoginManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by spade on 6/7/17.
 */

public class MekApplication extends Application {

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        sAnalytics = GoogleAnalytics.getInstance(this);
        FacebookLoginManager.initFacebookEvents(this);
        AndroidNetworking.initialize(this);
        Realm.init(this);
        setRealmDefaultConfiguration();
    }

    private void setRealmDefaultConfiguration() {
        if (PrefUtils.isFirstLaunch(this)) {
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().
                    name(RealmConfig.REALM_FILE).
                    schemaVersion(RealmConfig.REALM_VERSION).migration(new RealmDbMigration()).
                    modules(new RealmModules()).build();
            Realm.setDefaultConfiguration(realmConfiguration);
        }
    }

    synchronized public static Tracker getDefaultTracker() {
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker("UA-104912436-1");
        }
        return sTracker;
    }
}
