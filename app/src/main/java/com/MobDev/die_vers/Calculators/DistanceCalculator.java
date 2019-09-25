package com.MobDev.die_vers.Calculators;


public class DistanceCalculator {
    private static final int earthRadius = 6371;
    public static float calculateDistance(double lat_1, double lon_1, double lat_2, double lon_2)
    {
        float lat1 = (float) lat_1;
        float lat2 = (float) lat_2;
        float lon1 = (float) lon_1;
        float lon2 = (float) lon_2;



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