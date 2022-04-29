package com.example.escapebrides.tools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;

public class GPS implements LocationListener {
    public static int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    //defult jerusalem
    private double lat = 31.771959;
    private double lag = 35.217018;

    private LocationManager locationManager;


    public GPS(AppCompatActivity activity) {
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
                0, this);


    }
    //get lat and lng only once
    @Override
    public void onLocationChanged(Location location) {
        Log.d("myLog", "getting location" + location.getLatitude() + "---" + location.getLongitude());
        lat = location.getLatitude();
        lag = location.getLongitude();
        stop_location_track();
    }

    public double getLag() {
        return lag;
    }

    public double getLat() {
        return lat;
    }

    public void stop_location_track() {
        locationManager.removeUpdates(this);
    }

}
