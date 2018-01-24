package com.spade.mek.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ConnectionQuality;
import com.androidnetworking.interfaces.ConnectionQualityChangeListener;

/**
 * Created by ddopik on 1/23/2018.
 */

public class NetWorkUtil {
    private static int mCurrentBandwid;

    public static void checkNetWork(){

        // Adding Listener
        AndroidNetworking.setConnectionQualityChangeListener(new ConnectionQualityChangeListener() {
            @Override
            public void onChange(ConnectionQuality currentConnectionQuality, int currentBandwidth) {
                mCurrentBandwid=currentBandwidth;
                Log.e("NetWorkUtil","checkNetWork()--->currentBandwidth= "+currentBandwidth);
                if(currentConnectionQuality == ConnectionQuality.EXCELLENT){
                    Log.e("NetWorkUtil","checkNetWork()--->connectionQuality_EXCELLENT= "+mCurrentBandwid);
// do something
                }else if (currentConnectionQuality == ConnectionQuality.POOR){
                    Log.e("NetWorkUtil","checkNetWork()--->connectionQuality_POOR= "+mCurrentBandwid);
// do something
                }else if (currentConnectionQuality == ConnectionQuality.UNKNOWN){
                    Log.e("NetWorkUtil","checkNetWork()--->connectionQuality_UNKNOWN= "+mCurrentBandwid);
// do something
                }
                // do something on change in connectionQuality
            }
        });

//
//        // Getting current ConnectionQuality
//        ConnectionQuality connectionQuality = AndroidNetworking.getCurrentConnectionQuality();


// Removing Listener
//        AndroidNetworking.removeConnectionQualityChangeListener();
    }
    public static   boolean isInternetAvailable(Context app) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if(activeNetworkInfo == null) {
            connected = false;
        }
        else
        {
            connected=true;
        }
        Log.e("AppConfig","ConnectionState---->"+connected+activeNetworkInfo);
        return connected;
    }


}
