package com.isteer.service.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.isteer.service.config.SRVAppConfig;
import com.isteer.service.roomDB.RoomDB;
import com.isteer.service.model.LocationData;
import com.isteer.service.preference.SRVPreference;
import com.isteer.service.receiver.SEMTVReceiver;
import com.isteer.service.utility.BasicUtils;
import com.isteer.service.utility.ValidationUtils;


import io.fabric.sdk.android.Fabric;

import java.util.Date;

public class App extends Application implements android.location.LocationListener, com.google.android.gms.location.LocationListener, ConnectionCallbacks, OnConnectionFailedListener {

    public static final String TAG = "App";


    public static LocationData userLocData = new LocationData();
    public static SRVPreference appPreference;
    public static BasicUtils appUtils;

    public static Context appContext;
    private LocationManager mLocManager;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLocation1;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public static boolean isLocationProviderDisabled = true;
    private LocationCallback locationCallback;

    public static App INSTANCE;
    public static RoomDB roomDB;
    private RequestQueue requestQueue;
    public static ValidationUtils validationUtils;

    @Override
    public void onCreate() {

        super.onCreate();
        Fabric.with(this, new Crashlytics());

        initGoogleAPIClient();
        initRoomDB();
        locationListner();

        appPreference = new SRVPreference(this);
        appUtils = new BasicUtils(this);
        validationUtils = new ValidationUtils(this);
        initTokenValidator();

    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("onLocationChanged is called");
        if (location != null) {

            Log.e("onLocationChanged", " lat : " + location.getLatitude() + " Long :" + location.getLongitude());

            userLocData.setAltitude(location.getAltitude());
            userLocData.setLongitude(location.getLongitude());
            userLocData.setLatitude(location.getLatitude());
        }
    }



    private void locationListner() {
        new Handler().postDelayed(initLocation, 60 * 1000);
    }

    private Runnable initLocation = new Runnable() {
        @Override
        public void run() {
            System.out.println("initLocation Thread is called");
            if (appPreference.isLoggedIn()) {
                mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED  &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                } else {
                    if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000, 10,getINSTANCE());
                    }
                    if (mLocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                        mLocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 10, getINSTANCE());


                }
            }

        }
    };


    public static App getINSTANCE() {
        return INSTANCE;
    }

    public RoomDB getRoomDB() {
        return roomDB;
    }


    public void initRoomDB() {
        roomDB = Room.databaseBuilder(getApplicationContext(), RoomDB.class, SRVAppConfig.DB_NAME + 1)
                .addMigrations(RoomDB.MIGRATION_1_2,RoomDB.MIGRATION_2_3)
                .allowMainThreadQueries()
                .build();

        INSTANCE = this;
    }

    public <T> void addRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }
        return requestQueue;

    }


    private void initTokenValidator() {
        Log.e("initTokenValidator", "called");

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this.getApplicationContext(), SEMTVReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this.getApplicationContext(), 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime() + 10 * 1000, SRVAppConfig.LOGIN_CHECKUP_TIME, pi);
    }


    private void initGoogleAPIClient() {

        Log.e("initGoogleAPIClient", "called");

        if (checkPlayServices()) {

            buildGoogleApiClient();
            createLocationRequest();

            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        } else
            Log.e("checkPlayServices", "not exists");

    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {

            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    protected void createLocationRequest() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(SRVAppConfig.LOC_TIME_INTERVAL);
        mLocationRequest.setFastestInterval(SRVAppConfig.LOC_TIME_INTERVAL_FASTEST);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(SRVAppConfig.LOC_DISPLACEMENT_INTERVAL);

    }

    @SuppressLint("MissingPermission")
    protected void startLocationUpdates() {



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            askPermission();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

        mLocation1 = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


    }

    protected void stopLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
    }




    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

        isLocationProviderDisabled = false;
    }

    @Override
    public void onProviderDisabled(String provider) {

        isLocationProviderDisabled = true;

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

        Log.e("onConnectionFailed", "ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        Log.e("onConnected", "called");

        if (appPreference.isDayStarted()) {
            startLocationUpdates();
        }



    }

    @Override
    public void onConnectionSuspended(int arg0) {

        Log.e("onConnectionSuspended", "called");

        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

}
