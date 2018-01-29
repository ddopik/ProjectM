package com.spade.mek.application;

import android.app.Application;
import android.content.Intent;

import com.androidnetworking.AndroidNetworking;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.spade.mek.realm.RealmConfig;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.realm.RealmDbMigration;
import com.spade.mek.realm.RealmModules;
import com.spade.mek.ui.cart.presenter.UserOrderPresenterImpl;
import com.spade.mek.ui.home.MainActivity;
import com.spade.mek.ui.products.view.ProductDetailsFragment;
import com.spade.mek.ui.splash.SplashActivity;
import com.spade.mek.utils.PrefUtils;
import com.spade.sociallogin.FacebookLoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.BuildConfig;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by spade on 6/7/17.
 */

public class MekApplication extends Application {

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;
    public static final String TYPE_NOTIFICATION = "TYPE_NOTIFICATION";
    public static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        initGoogleAnalytics();
        initFacebookEvents();
        initAndroidNetworking();
        initRealm();
        initOneSignal();
    }

    private void initGoogleAnalytics() {
        sAnalytics = GoogleAnalytics.getInstance(this);
    }

    private void initFacebookEvents() {
        FacebookLoginManager.initFacebookEvents(this);
    }

    private void initAndroidNetworking() {
        AndroidNetworking.initialize(this);
    }

    private void initRealm() {
        Realm.init(this);
        setRealmDefaultConfiguration();
    }

    private void initOneSignal() {
        OneSignal.startInit(this).setNotificationReceivedHandler(new NotificationReceivingHandler())
                .setNotificationOpenedHandler(new NotificationOpenReceiver()).init();
        OneSignal.idsAvailable((userId, registrationId) -> PrefUtils.setNotificationToken(this, userId));
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

    private class NotificationReceivingHandler implements OneSignal.NotificationReceivedHandler {
        @Override
        public void notificationReceived(OSNotification notification) {
            try {
                JSONObject dataObject = notification.payload.additionalData;
                String type = dataObject.getString("type");
                if (type.equals("payment")) {
                    int donationType = dataObject.getInt("type_of_donation");
                    if (donationType == UserOrderPresenterImpl.DONATE_FOR_PRODUCTS) {
                        boolean isSuccess = dataObject.getInt("status") == 1;
                        if (isSuccess) {
                            RealmDbHelper realmDbHelper = new RealmDbImpl();
                            realmDbHelper.deleteAllCartItems(PrefUtils.getUserId(getApplicationContext()));
                        }
                    }
                }
            } catch (JSONException e) {


            }
        }
    }

    private class NotificationOpenReceiver implements OneSignal.NotificationOpenedHandler {

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            try {
                JSONObject dataObject = result.notification.payload.additionalData;
                String type = dataObject.getString("type");
                if (type.equals("custom")) {
                    startActivity(SplashActivity.getLaunchIntent(getApplicationContext()));
                } else {
                    int id = dataObject.getInt("product_id");
                    startMainActivity(type, id);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void startMainActivity(String type, int id) {
        Intent intent = MainActivity.getLaunchIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ProductDetailsFragment.ITEM_ID, id);
        intent.putExtra(ProductDetailsFragment.EXTRA_PRODUCT_TYPE, type);
        intent.setType(TYPE_NOTIFICATION);
        startActivity(intent);
    }
}
