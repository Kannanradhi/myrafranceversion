package com.isteer.service.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.isteer.service.R;
import com.isteer.service.app.App;
import com.isteer.service.config.SRVAppConstant;
import com.isteer.service.model.LocationData;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Logger;

import static com.isteer.service.app.App.appPreference;

public class SRVLocateScreen extends Activity implements OnMapClickListener,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener, OnClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private Location myLocation;
    private PolygonOptions polygonOptions;
    private Polygon polygon;

    private Marker marker;
    private boolean markerClicked;
    final int RQS_GooglePlayServices = 1;
    private LatLng latLng;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private GoogleMap mGoogleMap;

    private TextView header_title;
    public static boolean isToRefresh = false;
    private static ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.scr_srv_locate);

        initVar();

        createLocationRequest();
        startLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fusedLocationProviderClient != null) {
            stopRemoveLocationUpdates();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (isToRefresh) {
            isToRefresh = false;

           /* try {


                Log.e("onResume: Lat Log", "" + SRVCallViewScreen.currentServiceCall.getLatitude() + " " + SRVCallViewScreen.currentServiceCall.getLongitude());
                Log.e("onResume: Customer Name", " " + SRVCallViewScreen.currentServiceCall.getCustomer_name());

                if (SRVCallViewScreen.currentServiceCall.getLatitude() != 0.0
                        && SRVCallViewScreen.currentServiceCall.getLongitude() != 0.0) {

                    final LatLng locationPoint = new LatLng(SRVCallViewScreen.currentServiceCall.getLatitude(), SRVCallViewScreen.currentServiceCall.getLongitude());

                    mGoogleMap.addMarker(new MarkerOptions().position(locationPoint).draggable(false));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationPoint, 16));
                } else {

                    // Just for testing the marker . Remove before release build
                    final LatLng locationPoint = new LatLng(13.0406346, 80.1832114);

                    mGoogleMap.addMarker(new MarkerOptions().position(locationPoint).draggable(false));
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationPoint, 16));
                    // mapView.addMarker(new MarkerOptions().position(new LatLng(13.0406346,80.1832114)).draggable(false));
                    //  SRVCallViewScreen.currentServiceCall.setLatitude(13.0406346);
                    // SRVCallViewScreen.currentServiceCall.setLongitude(80.1832114);


                    //  alertUserP(this, "Alert", "Longclick on map to add a marker", "OK");
                }
            } catch (Exception e) {
                // alertUserP(this, "Alert", "Longclick on map to add a marker", "OK");
            }*/

/*			int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

			if (resultCode == ConnectionResult.SUCCESS) {
				Toast.makeText(getApplicationContext(),
						"isGooglePlayServicesAvailable SUCCESS", Toast.LENGTH_LONG)
						.show();
			} else {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						RQS_GooglePlayServices);
			}*/
        }
    }

    private void initVar() {
        findViewById(R.id.btn_header_left).setOnClickListener(this);
        findViewById(R.id.btn_header_right).setOnClickListener(this);

        header_title = (TextView) findViewById(R.id.header_title);
        header_title.setText("Locate");

        ((Button)findViewById(R.id.btnChangeLoc)).setOnClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SRVLocateScreen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    100);
            return;
        } else {

            FragmentManager myFragmentManager = getFragmentManager();
            MapFragment myMapFragment = (MapFragment) myFragmentManager.findFragmentById(R.id.mapview);
            myMapFragment.getMapAsync(this);

//            mapView.setMyLocationEnabled(true);
//            mapView.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//            mapView.setOnMapClickListener(this);
            //mapView.setOnMapLongClickListener(this);
            //mapView.setOnMarkerDragListener(this);

            markerClicked = false;
        }

    }

    @Override
    public void onMapClick(LatLng point) {
//        Log.e("onMapClick", point.toString());
//        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(point));
//
//        markerClicked = false;
//        mGoogleMap.clear();
//        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(point));
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 16));
//        mGoogleMap.setMyLocationEnabled(true);
        markerClicked = false;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPermission();
        }

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setTrafficEnabled(true);
        mGoogleMap.setIndoorEnabled(true);
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);


        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.setOnMapLongClickListener(this);
        mGoogleMap.setOnMarkerDragListener(this);

//        if (SRVCallViewScreen.currentServiceCall.getLatitude() != 0.0 && SRVCallViewScreen.currentServiceCall.getLatitude() != 0.0) {
//
//            final LatLng locationPoint = new LatLng(SRVCallViewScreen.currentServiceCall.getLatitude(),
//                    SRVCallViewScreen.currentServiceCall.getLongitude());
//
//            mGoogleMap.addMarker(new MarkerOptions().position(locationPoint).draggable(false)
//                    .title(SRVCallViewScreen.currentServiceCall.getCustomer_name())
//                    .snippet(SRVCallViewScreen.currentServiceCall.getCaller_address()));
//
//            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationPoint, 16));
//        } else {
//            // Just for testing the marker . Remove before release build
//            final LatLng locationPoint = new LatLng(13.0406346, 80.1832114);
//
//            mGoogleMap.addMarker(new MarkerOptions().position(locationPoint).draggable(false)
//                    .title(SRVCallViewScreen.currentServiceCall.getCustomer_name()));
//            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationPoint, 16));
//        }

        setinitLocation();

    }


    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(20000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;

    }

    @Override
    public void onMapLongClick(LatLng point) {

        Log.e("onMapLongClick", point.toString());

        mGoogleMap.clear();
        marker = mGoogleMap.addMarker(new MarkerOptions().position(point).draggable(true));

        markerClicked = false;

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

        if (marker != null && marker.getPosition() != null) {
            SRVCallViewScreen.currentServiceCall.setLatitude(marker.getPosition().latitude);
            SRVCallViewScreen.currentServiceCall.setLongitude(marker.getPosition().longitude);
        }

    }


    public void setinitLocation() {
        Log.e("locationPointstart", "" + SRVCallViewScreen.currentServiceCall.getLatitude());
        Log.e("locationPointstart", "" + SRVCallViewScreen.currentServiceCall.getLongitude());
        //  Logger.LogError("mLastLocation11", "" + mLastLocation.getLongitude());
        //  Logger.LogError("mLastLocation22", "" + mLastLocation.getLongitude());

       /* if (mLastLocation != null) {
            Logger.LogError("Latitude:Longitude locate", +mLastLocation.getLatitude() + " " + mLastLocation.getLongitude());
            //place marker at current position
            mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            *//*MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            marker = mGoogleMap.addMarker(markerOptions);*//*
        }*/


        try {
            //mGoogleMap.clear();
            if (SRVCallViewScreen.currentServiceCall.getLatitude() != 0 && SRVCallViewScreen.currentServiceCall.getLongitude() != 0) {

                final LatLng locationPoint = new LatLng(SRVCallViewScreen.currentServiceCall.getLatitude(), SRVCallViewScreen.currentServiceCall.getLongitude());
                Log.e("locationPoint11", "" + locationPoint);
                mGoogleMap.addMarker(new MarkerOptions().position(locationPoint).draggable(true));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationPoint, 16));
            } else if (mLastLocation != null && mLastLocation.getLatitude() != 0 && mLastLocation.getLongitude() != 0) {
                final LatLng locationPoint = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                Log.e("locationPoint122", "" + locationPoint);
                mGoogleMap.addMarker(new MarkerOptions().position(locationPoint).draggable(true));
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationPoint, 16));
            } else {
                Toast.makeText(this, "Long press on map to add a marker", Toast.LENGTH_SHORT).show();
                // alertUserP(this, "Alert", "Longclick on map to add a marker", "OK", "longclick");

            }
        } catch (Exception e) {

            Toast.makeText(this, "Long press on map to add a marker", Toast.LENGTH_SHORT).show();
        }
    }


    class PostRequestManager extends AsyncTask<String, String, String> {

        //private int operationType;
        private String uriInProgress;

        @SuppressLint("LongLogTag")
        protected String doInBackground(String... urls) {

            uriInProgress = urls[0];
            Log.e("postUrl : ", uriInProgress);

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(uriInProgress);
            try {

                //operationType = getOperationType(uriInProgress);

                String jsonString = "";
				
				try {

					JSONObject tJson = new JSONObject();


					tJson.put("cm_key", ""+SRVCallViewScreen.currentServiceCall.getCustomer_key());
//					tJson.put(SRVAppConstant.KEY_CON_KEY, ""+DSRCustDetailScreen.currentCustomerConKey);
										
					if(SRVCallViewScreen.currentServiceCall.getLatitude()!=0 && SRVCallViewScreen.currentServiceCall.getLongitude()!=0)
					{

						tJson.put(SRVAppConstant.KEY_LATITUDE, ""+SRVCallViewScreen.currentServiceCall.getLatitude());
						tJson.put(SRVAppConstant.KEY_LONGITUDE, ""+SRVCallViewScreen.currentServiceCall.getLongitude());
						tJson.put(SRVAppConstant.KEY_ALTITUDE, ""+0.0);						
					}
						
					jsonString = tJson.toString();
					Log.e("jsonString : ", jsonString);
				
				}
				catch(JSONException e)
				{
					e.printStackTrace();
					Log.e("JSONException : ", e.toString());
				}

                StringEntity se = new StringEntity(jsonString);
                httppost.setEntity(se);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                return httpclient.execute(httppost, responseHandler);

            } catch (ClientProtocolException e) {
                Log.e("ClientProtocolException : ", e.toString());
            } catch (IOException e) {
                Log.e("IOException : ", e.toString());
            }

            return null;
        }

        @Override
        protected void onPreExecute() {

            updateProgress("Updating...");
        }

        protected void onPostExecute(String responseString) {

            Log.e("responseString : ", "" + responseString);

            dismissProgress();

            if (responseString == null) {
                alertUserP(SRVLocateScreen.this, "Failed", "Location update failed in a timely manner", "OK");
                return;
            }

            try {

                JSONObject responseJson = new JSONObject(responseString);

                if (responseJson.has(SRVAppConstant.KEY_STATUS) && responseJson.getInt(SRVAppConstant.KEY_STATUS) == 1) {
                    alertUserP(SRVLocateScreen.this, "Success", "Location update successfully", "OK");
                    //App.localDBS.updateAsSynced(DSRPickLocScreen.this, SRVAppConstant.SYNC_TYPE_CONTACT, ""+DSRCustDetailScreen.currentCustomerConKey, DSRCustDetailScreen.currentCustomer.getCmkey());
                    return;
                }

            } catch (JSONException e) {

                e.printStackTrace();
                Log.e("JSONException : ", e.toString());
            }

            alertUserP(SRVLocateScreen.this, "Failed", "Location update failed in a timely manner", "OK");
        }
    }


    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPermission();
        }


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {


                mLastLocation = location;
                //   Log.e("Location.getLatitude start locate", "" + mLastLocation.getLatitude());
                //  Log.e("Location.getLongitude start locate", "" + mLastLocation.getLongitude());
                setinitLocation();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    mLastLocation = location;
                    //  Log.e("Location.getLatitude changes locate", "" + mLastLocation.getLatitude());
                    //  Log.e("Location.getLongitude changes locate", "" + mLastLocation.getLongitude());

                }

            }
        };

        startRequestLocationUpdates();
        Log.e("startLocationUpdates", "startLocationUpdates");

    }


    public void stopRemoveLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    public void startRequestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPermission();
        }
        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback, null);
    }



    private void updateLoc() {


        if (mLastLocation != null) {
            if (marker != null && marker.getPosition() != null) {
                Log.e("marker.getPosition().latitude", "" + marker.getPosition().latitude);
                Log.e("marker.getPosition().longitude", "" + marker.getPosition().longitude);
                setAlertForConfirmLocation();
            } else {
                if (mLastLocation.getLatitude() != 0 && mLastLocation.getLongitude() != 0) {
                    Log.e("mLastLocation.getLatitude()", "" + mLastLocation.getLatitude());
                    Log.e("mLastLocation.getLongitude()", "" + mLastLocation.getLongitude());
                    setAlertForConfirmLocation();

                } else
                    Toast.makeText(this, "Please Long press to add a customer location", Toast.LENGTH_SHORT).show();
                //   alertUserP(B2CLocateScreen.this, "Alert !", "Please Long click to add a customer location", "OK", "Error");
            }
        } else {

            setAlertForConfirmLocation();
        }




//        if (marker != null && marker.getPosition() != null) {
//            LocationData loc = new LocationData();
//            loc.setLatitude(marker.getPosition().latitude);
//            loc.setLongitude(marker.getPosition().longitude);
//            loc.setAltitude(0.0);
//
///*			if(App.localDBS.updateCustomerLocation(DSRPickLocScreen.this, ""+DSRCustDetailScreen.currentCustomerConKey, DSRCustDetailScreen.currentCustomer.getCmkey(),
//					loc))
//			{
//				SRVCallViewScreen.isLocationsUpdated = true;
//
//				SRVCallViewScreen.serviceCall.setLatitude(loc.getLatitude());
//				SRVCallViewScreen.serviceCall.setLongitude(loc.getLongitude());
//
//				if(App.basicUtils.isNetAvailable())
//				{
//					new PostRequestManager().execute(App.appPreference.getBaseUrl()+SRVAppConstant.METHOD_UPDATE_CUST_LOC);
//				}
//				else
//				{
//					Toast.makeText(DSRPickLocScreen.this, "Saved locally", Toast.LENGTH_LONG).show();
//				}
//			}
//			else
//				Toast.makeText(DSRPickLocScreen.this, "Location update failed in a timely manner", Toast.LENGTH_LONG).show();*/
//        } else
//            alertUserP(SRVLocateScreen.this, "Error", "Please mark a valid location", "OK");
    }

    @Override
    public void onClick(View pView) {

        switch (pView.getId()) {

            case R.id.btn_header_right:
                gotoSRVMenuScreen();
                break;


            case R.id.btn_header_left:
                gotoSRVCallListScreen();
                break;

          /*  case R.id.btn_header_right:
                gotoDSRDayScreen();
                break;*/
				
			case R.id.btnChangeLoc:
				updateLoc();
				break;
        }
    }


    public void setCusLocation(double latitude, double longitude) {
        LocationData loc = new LocationData();
        loc.setLatitude(latitude);
        loc.setLongitude(longitude);
        loc.setAltitude(0.0);
//        Log.e("B2CCounterDetailScreen.currentContactIndKey", "" + B2CCounterDetailScreen.currentContactIndKey);
        Log.e("custmar_key", "" + SRVCallViewScreen.currentServiceCall.getCustomer_key());
			/*int intupdateCusLoc = B2CApp.getINSTANCE().getRoomDB().customerindividual_dao().updateCustomerLocation("" + B2CCounterDetailScreen.currentContactIndKey,
					"" + B2CCounterDetailScreen.currentCustomer.getCmkey(), loc.getLatitude(),
					loc.getLongitude());*/

        Log.e("loc.getLatitude()insert", "" + loc.getLatitude());
        Log.e("loc.getLongitude()insert", "" + loc.getLongitude());
        int intupdateCusLoc = App.getINSTANCE().getRoomDB().daoServicesCall().updateCustomerLocation(
                "" + SRVCallViewScreen.currentServiceCall.getCustomer_key(), loc.getLatitude(),
                loc.getLongitude());
			/*if(B2CApp.b2cLdbs.updateCustomerLocation(this, ""+B2CCounterDetailScreen.currentContactIndKey, ""+B2CCounterDetailScreen.currentCustomer.getCmkey(),
					loc))*/
        Log.e("intupdateCusLoc", "" + intupdateCusLoc);
        if (intupdateCusLoc > 0) {
//            B2CCounterDetailScreen.isLocationsUpdated = true;

            SRVCallViewScreen.currentServiceCall.setLatitude(loc.getLatitude());
            SRVCallViewScreen.currentServiceCall.setLongitude(loc.getLongitude());

            if (App.appUtils.isNetAvailable()) {
                new PostRequestManager().execute(appPreference.getBaseUrl()
                        + SRVAppConstant.METHOD_UPDATE_LOC);

            } else {
                //B2CApp.b2cPreference.setIsUpdatedlocation
                Toast.makeText(this, "Saved locally", Toast.LENGTH_LONG).show();
            }
        } else
            Toast.makeText(this, "Location update failed in a timely manner", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {

        goBack();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            goBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goBack() {
        gotoSRVCallViewScreen();
    }

    private void gotoGPSSwitch() {
        Intent intent = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void switchGPS(boolean switch_on) {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");

        if (switch_on && provider.contains("gps")) {
            intent.putExtra("enabled", true);
        } else {
            intent.putExtra("enabled", false);
        }

        sendBroadcast(intent);
    }

    private void updateProgress(String message) {
        if (pdialog != null && pdialog.isShowing())
            pdialog.setMessage(message);
        else
            pdialog = ProgressDialog.show(this, "", message, true);
    }

    private void dismissProgress() {
        if (pdialog != null && pdialog.isShowing())
            pdialog.dismiss();
    }

    public void alertUserP(Context context, String title, String msg, String btn) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg).setTitle(title).setCancelable(false)
                .setPositiveButton(btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void gotoSRVCallListScreen() {

        startActivity(new Intent(this, SRVCallListScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
        finish();
    }

    private void gotoSRVCallViewScreen() {

        startActivity(new Intent(this, SRVCallViewScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
        finish();
    }


    private void gotoSRVSyncScreen() {

        startActivity(new Intent(this, SRVSyncScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    private void gotoSRVMenuScreen() {

        startActivity(new Intent(this, MenuScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }


    private void askPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, 101);
    }


    public void setAlertForConfirmLocation() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure change Marker Location as Customer location")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                        setCusLocation(marker.getPosition().latitude, marker.getPosition().longitude);
                        dialog.cancel();



                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();




    }


}