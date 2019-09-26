package com.MobDev.die_vers.Calculators;


import android.location.Location;

public class DistanceCalculator {
    private static final int earthRadius = 6371;
    public static float calculateDistance(Location locationA, Location locationB)
    {
        float lat1 = (float) locationA.getLatitude();
        float lat2 = (float) locationB.getLatitude();
        float lon1 = (float) locationA.getLongitude();
        float lon2 = (float) locationB.getLongitude();

        float dLat = (float) Math.toRadians(lat2 - lat1);
        float dLon = (float) Math.toRadians(lon2 - lon1);
        float a =
                (float) (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2));
        float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        float d = earthRadius * c;
        return d;
    }
}