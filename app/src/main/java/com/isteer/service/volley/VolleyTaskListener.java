package com.isteer.service.volley;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by anbu on 10/12/2016.
 */

public interface VolleyTaskListener {

    void handleResult(String method_name, JSONObject response) throws JSONException;
    void handleError(String method_name, VolleyError e);

}
