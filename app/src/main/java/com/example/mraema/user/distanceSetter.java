package com.example.mraema.user;


import android.location.Location;

import java.util.Collections;
import java.util.List;

public class distanceSetter {

    double lat1;
    double lng1;
    double lat2;
    double lng2;


    public static double getKmFromLatLong(double lat1, double lng1, double lat2, double lng2){
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lng1);
        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lng2);
        double distanceInMeters = loc1.distanceTo(loc2);
        return  distanceInMeters/1000;
    }


}
