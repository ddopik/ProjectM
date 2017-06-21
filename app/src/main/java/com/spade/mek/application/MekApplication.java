package com.spade.mek.application;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
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

    @Override
    public void onCreate() {
        super.onCreate();
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
}
