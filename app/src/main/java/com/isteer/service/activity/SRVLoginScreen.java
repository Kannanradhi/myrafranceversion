package com.isteer.service.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.isteer.service.R;
import com.isteer.service.app.App;
import com.isteer.service.config.DSRAppConstant;
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
import java.util.Date;

public class SRVLoginScreen extends Activity {

    private String TAG = "ISDLoginScreen";

    private Handler handler = new Handler();

    private Button btnLogin;
    private EditText login_name, login_pass;
    private String user_name, password;
    private TextView header_title;

    private static ProgressDialog pdialog;
    private static String PROGRESS_MSG = "Signing in...";
    private ImageView gohome;
    private ImageView goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scr_isr_login);

        asklocationPermision();

        initVar();
    }

    private void asklocationPermision() {

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    100);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!App.appPreference.isRegistered()) {
            clearEntries();
            gotoSRVSplashScreen();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onBackPressed() {

        goBack();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void goBack() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void initVar() {

        header_title = (TextView) findViewById(R.id.header_title);
        header_title.setText("Login");

        gohome = (ImageView) findViewById(R.id.btn_header_right);
        goback = (ImageView) findViewById(R.id.btn_header_left);
        gohome.setVisibility(View.INVISIBLE);
        goback.setVisibility(View.GONE);

        login_name = (EditText) findViewById(R.id.login_name);
        login_pass = (EditText) findViewById(R.id.login_pass);

        addListenerOnClick();

        ((View) findViewById(R.id.btn_header_left))
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        onBackPressed();
                    }
                });

    }

    private void addListenerOnClick() {

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                user_name = login_name.getText().toString();
                Log.e("email", user_name);

                password = login_pass.getText().toString();
                Log.d("password", password);

                if (user_name.length() < 3)
                    alertUserP(SRVLoginScreen.this, "Error", "Enter a valid name", "OK");
                else if (password.length() < 3)
                    alertUserP(SRVLoginScreen.this, "Error", "Enter a valid password", "OK");
                else if (!App.appUtils.isNetAvailable())
                    alertUserP(SRVLoginScreen.this, "Connection Error", "No Internet connection available", "OK");
                else if (App.appPreference.getBaseUrl() != null)
                    new AuthenticateTask().execute();
                else {
                    clearEntries();
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            SRVLoginScreen.this);
                    builder.setMessage(
                            "Application is not registered yet. Please register the app first")
                            .setTitle("Error")
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                            clearEntries();
                                            gotoSRVSplashScreen();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    class AuthenticateTask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... urls) {

            String postUrl = App.appPreference.getBaseUrl() + DSRAppConstant.METHOD_LOGIN;
            Log.e("postUrl : ", postUrl);

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(postUrl);
            try {

                JSONObject json = new JSONObject();

                try {
                    json.put("user_name", user_name);
                    json.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String jsonString = json.toString();

                Log.e("jsonString : ", jsonString);

                StringEntity se = new StringEntity(jsonString);
                httppost.setEntity(se);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                return httpclient.execute(httppost, responseHandler);

            } catch (ClientProtocolException e) {
                Log.e("Exception : ", e.toString());
            } catch (IOException e) {
                Log.e("IOException : ", e.toString());
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

            String message = "Authentication failed in a timely manner. Please try again";

            if (responseString == null) {

                runOnFail(message);
                return;
            }

            String se_key = null;
            String unit_key = null;
            String user_id = null;
            String Token = null;
            String upload_location_path = null;

            JSONObject responseOuterJson, responseJson;

            try {
                responseOuterJson = new JSONObject(responseString);

                if (responseOuterJson.has(SRVAppConstant.KEY_STATUS) && responseOuterJson.getString(SRVAppConstant.KEY_STATUS).equalsIgnoreCase(SRVAppConstant.KEY_SUCCESS)) {
                    if (responseOuterJson.has(SRVAppConstant.KEY_DATA)) {
                        responseJson = responseOuterJson.getJSONObject(SRVAppConstant.KEY_DATA);
                        if (responseJson.has(SRVAppConstant.KEY_USER_ID))
                            user_id = responseJson.getString(SRVAppConstant.KEY_USER_ID);

                        if (responseJson.has(SRVAppConstant.KEY_SE_KEY))
                            se_key = responseJson.getString(SRVAppConstant.KEY_SE_KEY);

                        if (responseJson.has(SRVAppConstant.KEY_UNIT_KEY))
                            unit_key = responseJson.getString(SRVAppConstant.KEY_UNIT_KEY);

                        if (responseJson.has(SRVAppConstant.KEY_TOKEN))
                            Token = responseJson.getString(SRVAppConstant.KEY_TOKEN);

                        if (responseJson.has(SRVAppConstant.UPLOAD_LOCATION_PATH))
                            upload_location_path = responseJson.getString(SRVAppConstant.UPLOAD_LOCATION_PATH);

                        Log.e("upload_location_path : ", "" + upload_location_path);

                        App.appPreference.setIsLoggedIn(true);
                        App.appPreference.setToken(Token);

                        if (user_id.equalsIgnoreCase(App.appPreference.getUserId())
                                && unit_key.equalsIgnoreCase(App.appPreference.getUnitKey())
                                && se_key.equalsIgnoreCase(App.appPreference.getSekey())) {
                            App.appPreference.setIsDbFilled(true);
                        } else {
                            App.appPreference.setIsDbFilled(false);
                        }

                        App.appPreference.setUserId(user_id);
                        App.appPreference.setUnitKey(unit_key);
                        App.appPreference.setSekey(se_key);

                        App.appPreference.setUserName(user_name);
                        App.appPreference.setUserPass(password);

                        App.appPreference.setIsTokenValid(true);
                        App.appPreference.setLastValidatedTime(new Date().getTime());
                        App.appPreference.setIsLoginFailed(false);
                        App.appPreference.setLoginFailCount(0);
                        App.appPreference.setUploadPath(upload_location_path);

                        clearEntries();
                        SRVSyncScreen.isFromLogin = true;

                        gotoSRVSyncScreen();

/*						if(App.appPreference.getLastLogin()!=0l && new Date().getTime()-App.appPreference.getLastLogin() < 8*60*60*1000)
							gotoSRVSyncScreen();
						else
						{
							SRVAlertScreen.toRefresh = true;
							gotoSRVAlertScreen();
						}*/

                        return;
                    }
                }

                if (responseOuterJson.has(SRVAppConstant.KEY_MSG))
                    message = responseOuterJson.getString(SRVAppConstant.KEY_MSG);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSONException : ", e.toString());

            }

            runOnFail(message);
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

    private void runOnFail(String message) {
        App.appPreference.setIsLoginFailed(true);
        int loginFailCount = App.appPreference.getLoginFailCount() + 1;
        if (loginFailCount >= App.appPreference.getMaxLoginAtt()) {
            forceLogoutApp();
            clearEntries();
            gotoSRVSplashScreen();
        } else {
            App.appPreference.setLoginFailCount(loginFailCount);
            alertUserP(SRVLoginScreen.this, "Failed", message, "OK");
        }
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

    public static boolean isSessionValid() {

        if (App.appPreference.isRegistered() && App.appPreference.isLoggedIn() && App.appPreference.isTokenValid())
            return true;
        else
            return false;
    }

    public static void forceLogoutApp() {

        App.appPreference.setBaseUrl(null);
        App.appPreference.setIsLoggedIn(false);
        App.appPreference.setIsRegistered(false);
        App.appPreference.setIsDbFilled(false);
        App.appPreference.setSekey(null);
        App.appPreference.setToken(null);
        App.appPreference.setUnitKey(null);
        App.appPreference.setUserId(null);
        App.appPreference.setUserName(null);
        App.appPreference.setUserPass(null);
        App.appPreference.setLastValidatedTime(01);

    }

    public static void autoLogoutApp() {

        App.appPreference.setIsLoggedIn(false);
        //DSRApp.dsrPreference.setSekey(null);
        App.appPreference.setToken(null);
        //DSRApp.dsrPreference.setUnitKey(null);
        //DSRApp.dsrPreference.setUserId(null);
        //DSRApp.dsrPreference.setUserName(null);
        //DSRApp.dsrPreference.setUserPass(null);
        App.appPreference.setLastValidatedTime(01);

    }

    private boolean validateEmail(String email1) {
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email1.matches(EMAIL_REGEX);
    }

    private void clearEntries() {
        login_name.setText("");
        login_pass.setText("");
    }

    private void gotoSRVSyncScreen() {

        startActivity(new Intent(this, SRVSyncScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    private void gotoSRVSplashScreen() {

        startActivity(new Intent(this, SRVSplashScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }



}
