package com.isteer.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.isteer.service.activity.SRVLoginScreen;
import com.isteer.service.app.App;
import com.isteer.service.config.DSRAppConstant;
import com.isteer.service.config.SRVAppConfig;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class DSRTVReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.e("SEMTVReceiver", "onReceive");

		if(App.appPreference.isLoggedIn())
		{
			if(App.appUtils.isNetAvailable())
				new TokenValidatorTask().execute();
			else
				runOnFail();
		}
	}

	class TokenValidatorTask extends AsyncTask<String, String, String> {

		protected String doInBackground(String... urls) {

			String postUrl = App.appPreference.getBaseUrl()
					+ DSRAppConstant.METHOD_VALIDATE_TOKEN + App.appPreference.getUserId() + "/"
					+ App.appPreference.getToken() ;
			Log.e("postUrl : ", postUrl);

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(postUrl);
			try {

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

			super.onPreExecute();
		}

		protected void onPostExecute(String responseString) {

			Log.e("responseString : ", "" + responseString);

			if (responseString == null) {

				runOnFail();
				return;
			}

			JSONObject responseJson;

			try {
				responseJson = new JSONObject(responseString);

				if (responseJson.has(DSRAppConstant.KEY_RESULT) && responseJson.getBoolean(DSRAppConstant.KEY_RESULT)) {
					App.appPreference.setLastValidatedTime(new Date().getTime());
					return;
				}

			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("JSONException : ", e.toString());

			}

			runOnFail();
		}

	}

	private void runOnFail() {
		
		if((new Date().getTime()- App.appPreference.getLastValidatedTime()) > SRVAppConfig.AUTO_LOGOUT_THRESHOLD)
			SRVLoginScreen.forceLogoutApp();
	}
	
}