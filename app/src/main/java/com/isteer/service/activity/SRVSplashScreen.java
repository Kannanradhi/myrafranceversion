package com.isteer.service.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.isteer.service.R;
import com.isteer.service.app.App;
import com.isteer.service.config.DSRAppConstant;
import com.isteer.service.config.SRVAppConfig;
import com.isteer.service.config.SRVAppConstant;

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

public class SRVSplashScreen extends Activity {

	private Handler handler = new Handler();
	private String companyCode;

	private View ll_register;
	private Button btnRegister;
	private ImageView splashimage;
	private String TAG = "SRVSplashScreen";

	private static ProgressDialog pdialog;
	private static String PROGRESS_MSG = "Loading...";
	
	private EditText et_company_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.scr_isr_splash);

		initVar();
		//testQuery(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
				
		hideStatusBar();
		ll_register.setVisibility(View.INVISIBLE);

		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				if (!App.appPreference.isRegistered())
					ll_register.setVisibility(View.VISIBLE);
				else
				{
					Log.e("getBaseUrl", " : "+ App.appPreference.getBaseUrl());

					if(App.appPreference.isLoggedIn() && App.appPreference.getUserId()!=null)
					{
						
						Log.e("getUserId", " : "+ App.appPreference.getUserId());
						Log.e("getUnitKey", " : "+ App.appPreference.getUnitKey());
						Log.e("getSekey", " : "+ App.appPreference.getSekey());
						Log.e("getToken", " : "+ App.appPreference.getToken());

						gotoSRVMenuScreen();
						//gotoSRVSyncScreen();

/*						if(DSRApp.dsrPreference.getLastLogin()!=0l && new Date().getTime()-DSRApp.dsrPreference.getLastLogin() < 8*60*60*1000)
							gotoDSRDayScreen();
						else
							gotoDsrAlertScreen();*/
					}
					else
						gotoSRVLoginScreen();
				}
			}
		}, SRVAppConfig.SPLASH_TIMEDELAY);
	}
	
	private void initVar() {

		ll_register = (View) findViewById(R.id.ll_register);
		et_company_name = (EditText) findViewById(R.id.et_company_name);
		splashimage = findViewById(R.id.splash_img);
		btnRegister = (Button) findViewById(R.id.btnRegister);

		btnRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				companyCode = et_company_name.getText().toString();

				Log.e("companyCode", companyCode);

				if (companyCode.length() < 1)
					alertUserP(SRVSplashScreen.this, "Error", "Enter a valid name", "OK");
				else if(!App.appUtils.isNetAvailable())
					alertUserP(SRVSplashScreen.this, "Connection Error", "No Internet connection available", "OK");
				else {

					// clearEntries();
					new AuthenticateTask().execute();

				}

			}
		});
	}

	class AuthenticateTask extends AsyncTask<String, String, String> {

		@SuppressLint("LongLogTag")
		protected String doInBackground(String... urls) {

			String postUrl = SRVAppConstant.URL_REGISTER;
			Log.e("postUrl : ", postUrl);

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(postUrl);
			try {

				JSONObject json = new JSONObject();
				json.put("sitename", companyCode);
				String jsonString = json.toString();
				
				Log.e("jsonString : ", jsonString);

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
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {

			updateProgress(PROGRESS_MSG);

		}

		protected void onPostExecute(String responseString) {

			Log.e("responseString : ", "" + responseString);

			dismissProgress();

			if (responseString == null) {

				alertUserP(SRVSplashScreen.this, "Failed", "Registration failed in a timely manner. Please try again", "OK");
				return;
			}

			String site=null, site2=null, success=null;

			JSONObject responseJson;

			try {
				responseJson = new JSONObject(responseString);

				if (responseJson.has(SRVAppConstant.KEY_SUCCESS))
					success = responseJson
							.getString(SRVAppConstant.KEY_SUCCESS);
								
				if(success!=null && success.equalsIgnoreCase(SRVAppConstant.KEY_SUCCESS))
				{
					
					if (responseJson.has(DSRAppConstant.KEY_SITE))
						site = responseJson
								.getString(DSRAppConstant.KEY_SITE);

					if (responseJson.has(SRVAppConstant.KEY_SITE2))
						site2 = responseJson
								.getString(SRVAppConstant.KEY_SITE2);
					
					clearEntries();
					App.appPreference.setIsRegistered(true);
					App.appPreference.setCompanyCode(companyCode);
					App.appPreference.setBaseUrl(site);
					App.appPreference.setBaseUrl2(site2);
					gotoSRVLoginScreen();
					return;
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("JSONException : ", e.toString());
			}
			alertUserP(SRVSplashScreen.this, "Failed", "Registration failed in a timely manner. Please try again", "OK");
		}
	}

	private void clearEntries() {
		et_company_name.setText("");
	}

	@Override
	public void onBackPressed() {

		handler.removeCallbacksAndMessages(null);

		goBack();
	}

	private void goBack() {

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}

	private void hideStatusBar() {
		if (Build.VERSION.SDK_INT < 16) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} else {
			View decorView = getWindow().getDecorView();

			int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);

			ActionBar actionBar = getActionBar();
			if (actionBar != null)
				actionBar.hide();
		}
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
	
	private void gotoSRVLoginScreen() {

		startActivity(new Intent(this, SRVLoginScreen.class)
				.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
	}

	private void gotoSRVSyncScreen() {

		startActivity(new Intent(this, SRVSyncScreen.class)
				.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
	}

	private void gotoSRVMenuScreen() {

		startActivity(new Intent(this, MenuScreen.class)
				.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
	}

/*	public Cursor testQuery(Context context) {
		
		SQLiteDatabase db = context.openOrCreateDatabase(DSRAppConstant.DB_NAME, SQLiteDatabase.OPEN_READWRITE, null);
		
        String query = "select " + DSRTableCreate.COLOUMNS_CONTACT_INDIVIDUAL.contact_key.name() // + " as " + B2CTableCreate.COLOUMNS_PENDING_ORDERS.customer_key.name()
        		+ " , (" + getCustomerName(context, DSRTableCreate.COLOUMNS_CONTACT_INDIVIDUAL.contact_key.name()) + ") as customer_name " 
        		+ " , " + "count(*) as count" + " from " 
        		+ DSRTableCreate.TABLE_AERP_CONTACT_INDIVIDUAL
        		+ " group by " + DSRTableCreate.COLOUMNS_CONTACT_INDIVIDUAL.contact_key.name() 
        		+ " order by " + DSRTableCreate.COLOUMNS_CONTACT_INDIVIDUAL.contact_key.name() + ";" ;
         
        Cursor mCursor = db.rawQuery(query, null);
		if (mCursor != null && mCursor.getCount() != 0 && mCursor.moveToFirst())
		{
			Log.e("fetchAreaWiseCount", "notNull");
			Log.e("fetchAreaWiseCount row count", "" + mCursor.getCount());
		}
		else 
		{
			Log.e("fetchAreaWiseCount", "Null");
			mCursor = null;
		}
		
		for(int i=0; i<mCursor.getCount(); i++)
			Log.e("getCustomerName", " : " + mCursor.getString(1));
			
		return mCursor;
	}
	
	private String getCustomerName(Context context, String customer_key)
	{
		return getKeyPairValue(context, DSRTableCreate.COLOUMNS_CONTACT_MASTER.company_name.name(), DSRTableCreate.TABLE_AERP_CONTACT_MASTER,
				DSRTableCreate.COLOUMNS_CONTACT_MASTER.cmkey.name(), new String[]{""+customer_key}); 
	}
	
	private String getKeyPairValue(Context context , String coloumnName, String tableName, String selection, String[] selectionArgs)
	{
		SQLiteDatabase db = context.openOrCreateDatabase(DSRAppConstant.DB_NAME, SQLiteDatabase.OPEN_READONLY, null);
        Cursor mCursor = db.rawQuery("select " + coloumnName + " from " + tableName + " where " +
        		selection + DSRLocalDBStorage.SELECTION_OPERATION_LIKE, selectionArgs);
       
		if (mCursor != null && mCursor.getCount() != 0 && mCursor.moveToFirst())
			return mCursor.getString(0);
		else
			return null;
	}*/
	
}
