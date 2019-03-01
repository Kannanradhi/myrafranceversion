package com.isteer.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.isteer.service.app.App;
import com.isteer.service.config.DSRAppConstant;
import com.isteer.service.model.LocationLog;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

    public boolean isStarted;
    public LocationManager mLocManager;
    private String uriInProgress;
    private Context mContext;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("AlarmReceiver", "onReceive");

        mContext = context;

        if (intent.getAction() != null && intent.getAction().equalsIgnoreCase("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (App.appUtils.isNetAvailable())
                new SyncTaskToServer().execute(App.appPreference.getBaseUrl() + DSRAppConstant.METHOD_UPDATE_LOCATION_LOG);
        } else {
            if (App.appPreference.isDayStarted()) {
                System.out.println("Receiver:forced call");
                LocationLog data = new LocationLog();
                data.setLatitude(String.valueOf(App.userLocData.getLatitude()));
                data.setLongitude(String.valueOf(App.userLocData.getLongitude()));
                data.setAltitude(String.valueOf(App.userLocData.getAltitude()));
                data.setUser_key(String.valueOf(App.appPreference.getUserId()));
                data.setDate_time(simpleDateFormat.format(new Date()));
                data.setUpdate_status(DSRAppConstant.KEY_UPDATE_STATUS_PENDING);

                System.out.println("-------date--------"+simpleDateFormat.format(new Date()));

                Log.d("Location_kannan -> ", "lat : " + App.userLocData.getLatitude() + "Longi : " + App.userLocData.getLongitude());

                long tKey = App.getINSTANCE().getRoomDB().daoLocationLog().insertLocsationLog(data);

                if (tKey > 1) {
                    new SyncTaskToServer().execute(App.appPreference.getBaseUrl() + DSRAppConstant.METHOD_UPDATE_LOCATION_LOG);
                }
            } else {
                System.out.println("Receiver :Auto Call");
            }
        }
    }

    class SyncTaskToServer extends AsyncTask<String, String, String> {
        private int operationType;

        protected String doInBackground(String... urls) {
            uriInProgress = urls[0];
            Log.e("postUrl : ", uriInProgress);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(uriInProgress);
            try {
                String jsonString = "";
                try {
                    jsonString = (new JSONObject().put(DSRAppConstant.KEY_VALUES, getAsyncLocationList())).toString();
                    System.out.println("jsonString" + jsonString);
                    Log.e("jsonString : ", jsonString);
                } catch (JSONException e) {
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
        }

        protected void onPostExecute(String responseString) {

            Log.e("responseString : ", "" + responseString);
            try {

                JSONObject responseJson;
                responseString = (responseString == null || responseString.trim().equalsIgnoreCase("")) ? "" : responseString;

                responseJson = new JSONObject(responseString);
                if (responseJson.has(DSRAppConstant.KEY_STATUS) && responseJson.getString(DSRAppConstant.KEY_STATUS).equalsIgnoreCase("Success")) {

                    JSONArray keys = responseJson.getJSONArray("location");
                    for (int i = 0; i < keys.length(); i++) {
                        App.getINSTANCE().getRoomDB().daoLocationLog().deleteByKey(keys.getInt(i));

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSONException : ", e.toString());
            }
        }

    }

    private JSONArray getAsyncLocationList() throws JSONException {

        return new JSONArray(new Gson().toJson(App.getINSTANCE().getRoomDB().daoLocationLog().getAllLocationLog()));

    }



}
