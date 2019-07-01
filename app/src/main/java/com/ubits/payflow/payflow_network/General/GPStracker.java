package com.ubits.payflow.payflow_network.General;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.ContextCompat;

import static android.content.Context.LOCATION_SERVICE;

public class GPStracker implements LocationListener {
    static final int MIN_TIME_INTERVAL = 30 * 1000;

    private Context context;
    private LocationManager lm;

    public GPStracker(Context context) {
        super();
        this.context = context;
        lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    public Location getLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_INTERVAL, 0, this);
                Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (loc != null) return loc;
                loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return loc;

            } else {
                return null;
            }

        } else {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_INTERVAL, 0, this);
            Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) return loc;
            loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return loc;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            lm.removeUpdates(this);
        } catch (Exception e) {
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
