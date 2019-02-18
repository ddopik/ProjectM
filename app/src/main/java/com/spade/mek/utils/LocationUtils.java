package com.spade.mek.utils;

import android.location.Location;

/**
 * Created by Ayman Abouzeid on 8/17/17.
 */

public class LocationUtils {

    public static float calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        Location locationA = new Location("point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lng1);
        Location locationB = new Location("point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lng2);
        return locationA.distanceTo(locationB);
    }

}
