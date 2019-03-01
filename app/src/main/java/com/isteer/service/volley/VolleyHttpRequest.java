package com.isteer.service.volley;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.isteer.service.app.App;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by anbu on 10/12/2016.
 */

public class VolleyHttpRequest {

    private static VolleyTaskListener volley;
    private static Activity getcontext;
    public static ProgressDialog pDialog;
    public static String dialog = "Loading";

    public static void makeVolleyPost(Fragment context, String url, final JSONObject postdata, String method_name) {
        volley = (VolleyTaskListener) context;
        getcontext = context.getActivity();
        pDialog  =new ProgressDialog(context.getActivity());
        pDialog.setMessage(dialog);
        pDialog.setCanceledOnTouchOutside(false);
        // showpDialog();
        startVollyTask(url, postdata, method_name);
    }

    public static void makeVolleyPost(final Activity context, String URL, final JSONObject jsonObject, String method_name) {

       Log.e("URL String", "" + URL);
        Log.e("Input String", "" + jsonObject.toString());

        volley = (VolleyTaskListener) context;
        getcontext = context;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(dialog);
        pDialog.setCanceledOnTouchOutside(false);
        // showpDialog();
        startVollyTask(URL, jsonObject, method_name);
    }

    public static void makeVolleyPostHeader(final Activity context, String URL, final JSONObject jsonObject,
                                            final JSONObject headerjson, String method_name) {

        Log.e("URL String", "" + URL);
        Log.e("Input String", "" + jsonObject.toString());
        Log.e("Header String", "" + headerjson.toString());

        volley = (VolleyTaskListener) context;
        getcontext = context;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(dialog);
        pDialog.setCanceledOnTouchOutside(false);
        // showpDialog();
        startVollyTaskHeader(URL, jsonObject, headerjson, method_name);

    }

    public static void makeVolleyPostHeader(final Fragment context, String URL, final JSONObject jsonObject, final JSONObject headerjson, String method_name) {

       // Log.e("URL String", "" + URL);
       // Log.e("Input String", "" + jsonObject.toString());
       // Log.e("Header String", "" + headerjson.toString());

        volley = (VolleyTaskListener) context;
        getcontext = context.getActivity();
        pDialog = new ProgressDialog(context.getActivity());
        pDialog.setMessage(dialog);
        pDialog.setCanceledOnTouchOutside(false);
        // showpDialog();

        startVollyTaskHeader(URL, jsonObject, headerjson, method_name);

    }


    private static void startVollyTask(String URL, final JSONObject jsonObject, final String method_name) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {
                            try {
                                // hidepDialog();
                                Log.e("response ", "" + response.toString());
                                volley.handleResult(method_name, response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // hidepDialog();
                        volley.handleError(method_name,error);

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getcontext);
        requestQueue.add(getRequest);
        requestQueue.getCache().remove(URL);
    }

    private static void startVollyTaskHeader(String URL, final JSONObject jsonObject, final JSONObject headerjson, final String method_name) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            try {
                                // hidepDialog();
                                Log.e("response ", "" + response.toString());
                                volley.handleResult(method_name, response);
                            } catch (JSONException e) {
                                // hidepDialog();
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // hidepDialog();
                        volley.handleError(method_name,error);

                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                try {
                    return jsonToMap(headerjson);
                } catch (JSONException e) {
                    // hidepDialog();

                    e.printStackTrace();
                }
                return null;
            }
        };

        App.getINSTANCE().addRequestQueue(getRequest);
        App.getINSTANCE().getRequestQueue().getCache().remove(URL);
        App.getINSTANCE().getRequestQueue().getCache().clear();

        /*RequestQueue requestQueue = Volley.newRequestQueue(getcontext);
        requestQueue.add(getRequest);
        requestQueue.getCache().remove(URL);*/
    }


    public static HashMap<String, String> jsonToMap(JSONObject t) throws JSONException {

        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jObject = t;
        Iterator<?> keys = jObject.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            String value = jObject.getString(key);
            map.put(key, value);
        }
        return map;
    }

    private static void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private static void hidepDialog() {
        pDialog.dismiss();
    }
}

