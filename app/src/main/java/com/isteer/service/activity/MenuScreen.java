package com.isteer.service.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.isteer.service.R;
import com.isteer.service.app.App;
import com.isteer.service.config.DSRAppConstant;
import com.isteer.service.model.AttendanceLog;
import com.isteer.service.receiver.AlarmReceiver;
import com.isteer.service.utility.BasicUtils;

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

public class MenuScreen extends Activity implements OnClickListener {

    private String TAG = "DSRLogoutScreen";
    private String uriInProgress;
    private BasicUtils isdUtils;
    private PendingIntent pendingIntent;

    private static ProgressDialog pdialog;
    private static String PROGRESS_MSG = "Loading...";

    public TextView startdaybutton;

    public TextView stopdaybutton;


    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    public static boolean addIsUptodate = false;
    public static boolean updateIsUptodate = false;
    public static boolean isSyncSuccess = false;
    private LinearLayout ll_stopdaybutton;
    private int press = 0;
    private ImageView gohome;
    private ImageView goback;
    private SwitchCompat sw_start_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scr_srv_menu);
        initVar();
        if (App.appPreference.isDayStarted()) {
            startdaybutton.setText("Day Started");

            sw_start_day.setChecked(true);
            startdaybutton.setBackground(getResources().getDrawable(R.drawable.daystart_bg));

        } else {
            startdaybutton.setText("Start Day");
            sw_start_day.setChecked(false);
            startdaybutton.setBackground(null);


        }

    }

    @Override
    protected void onResume() {
        super.onResume();


        //For Start Stop Day


    }

    private void initVar() {

        isdUtils = new BasicUtils(this);

        startdaybutton = findViewById(R.id.btnStartDay);
        stopdaybutton = findViewById(R.id.btnStopDay);
        ll_stopdaybutton = findViewById(R.id.ll_StopDay);
        sw_start_day = findViewById(R.id.sw_start_day);
        sw_start_day.setOnCheckedChangeListener(null);


        findViewById(R.id.ll_StartDay).setOnClickListener(this);
        findViewById(R.id.ll_StopDay).setOnClickListener(this);
        findViewById(R.id.ll_Logout).setOnClickListener(this);

        findViewById(R.id.ll_sync).setOnClickListener(this);
        findViewById(R.id.ll_servicecalls).setOnClickListener(this);

        ((TextView) findViewById(R.id.header_title)).setText("Main Menu");

        goback = findViewById(R.id.btn_header_left);
        goback.setVisibility(View.INVISIBLE);


        sw_start_day.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sw_start_day.isChecked()) {

                    verifyPass();

                    Log.e("onCheckedChanged: ", " TRUE called");
                } else if (!sw_start_day.isChecked()) {
                    Log.e("onCheckedChanged: ", " FALSE called");

                    if (App.appPreference.isDayStarted()) {
                        gotoStopDay();
                    } else {
                        gotoStartDay();
                    }
                }
            }
        });


    }

    private void verifyPass() {
        Log.e("verifyPass: ", " Called");
        if (App.appPreference.isDayStarted()) {
            gotoStopDay();
        } else {
            gotoStartDay();
        }
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
        return true;

    }

    private void goBack() {
        press = press + 1;

        if (press > 1) {
            Intent main = new Intent(Intent.ACTION_MAIN);
            main.addCategory(Intent.CATEGORY_HOME);
            main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(main);
        }
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
        //alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    private void showConfirmationAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Are you sure to logout the app ?")
                .setTitle("Alert!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                                App.appPreference.setIsDayStarted(false);
                                App.appPreference.setIsDbFilled(false);
                                App.appPreference.setIsLoggedIn(false);
                                App.appPreference.setSekey("");
                                App.appPreference.setToken("");
                                App.appPreference.setUnitKey("");
                                App.appPreference.setUserId("");

                                gotoB2CLoginScreen();
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    private void gotoB2CLoginScreen() {

        startActivity(new Intent(this, SRVLoginScreen.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));

    }


    private void gotoStartDay() {
        sw_start_day.setChecked(false);
        Log.e("gotoStartDay: ", "Called");
        System.out.println("gotoStartDay is called");
        AlertDialog.Builder alert = new AlertDialog.Builder(MenuScreen.this);
        alert.setTitle("Enter New Pass Code");
        alert.setMessage("Enter Any 4 Character");
        final EditText input = new EditText(MenuScreen.this);
        alert.setView(input);
        alert.setPositiveButton("Start", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String passcode = input.getText().toString();
                App.appPreference.setPassCode(passcode);
                if (passcode.length() < 4 || passcode.length() > 4 || passcode.trim().equals("") || passcode == null) {
                    Toast.makeText(MenuScreen.this, "Pass Code Not Valid", Toast.LENGTH_LONG).show();
                    sw_start_day.setChecked(false);
                } else {
                    dialog.cancel();
                    gotoInsertLocationAlarm();
                    startAlarm();

                    AttendanceLog attendanceLog = new AttendanceLog();
                    attendanceLog.setUser_key(App.appPreference.getUserId());
                    attendanceLog.setDate(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
                    attendanceLog.setStart_time(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                    attendanceLog.setStatus(DSRAppConstant.KEY_STATUS_STARTED);
                    attendanceLog.setUpdate_status(DSRAppConstant.KEY_UPDATE_STATUS_PENDING);


                    long tKey = App.getINSTANCE().getRoomDB().daoAttendanceLog().insertAttendenceLog(attendanceLog);
                    if (tKey > 1l) {
                        new SyncTaskToServer().execute(App.appPreference.getBaseUrl() + DSRAppConstant.METHOD_UPDATE_ATTENDENCE_LOG);
                        App.appPreference.setStartDateKey(tKey);
                        App.appPreference.setIsDayStarted(true);
                        startdaybutton.setText("Day Started");
                        startdaybutton.setBackground(getResources().getDrawable(R.drawable.daystart_bg));
                        sw_start_day.setChecked(true);
                        stopdaybutton.setVisibility(View.VISIBLE);
                        Toast.makeText(MenuScreen.this, "Day Started", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MenuScreen.this, "Day Not Started Try After Some Time", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
        alert.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
                sw_start_day.setChecked(false);
                Toast.makeText(MenuScreen.this, "Day Not Started", Toast.LENGTH_LONG).show();
            }
        });
        alert.show();

    }

    private void gotoStopDay() {
        sw_start_day.setChecked(true);
        Log.e("gotoStopDay: ", " called");
        AlertDialog.Builder alert = new AlertDialog.Builder(MenuScreen.this);
        alert.setTitle("Enter Pass Code");
        alert.setMessage("Enter 4 Character Pass Code");
        final EditText input = new EditText(MenuScreen.this);
        alert.setView(input);
        alert.setPositiveButton("Stop", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
                String passcode = input.getText().toString();
                if (passcode.equalsIgnoreCase(App.appPreference.getPassCode()) || passcode.equalsIgnoreCase(DSRAppConstant.DEFAULT_PASSSCODE)) {
                    Toast.makeText(MenuScreen.this, "Day Stopped", Toast.LENGTH_LONG).show();
                    long tKey = 0;
                    if (tKey > 1l) {
                        new SyncTaskToServer().execute(App.appPreference.getBaseUrl() + DSRAppConstant.METHOD_UPDATE_ATTENDENCE_LOG);
                    }
                    App.appPreference.setIsDayStarted(false);
                    sw_start_day.setChecked(false);
                    startdaybutton.setBackground(null);
                    startdaybutton.setText("Start Day");
                    //  ll_stopdaybutton.setVisibility(View.GONE);
                } else {
                    sw_start_day.setChecked(true);
                    Toast.makeText(MenuScreen.this, "Pass Code Not Matched", Toast.LENGTH_LONG).show();
                }
            }
        });
        alert.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
                sw_start_day.setChecked(true);
                Toast.makeText(MenuScreen.this, "Day Not Stopped", Toast.LENGTH_LONG).show();
            }
        });
        alert.show();
    }

    private void gotoInsertLocationAlarm() {
        System.out.println("gotoInsertLocationAlarm is called");
        Intent alarmIntent = new Intent(MenuScreen.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MenuScreen.this, 0, alarmIntent, 0);
    }

    private void startAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        long locationUpdateTime = App.appPreference.getAttTrackTime();
        /*int locationUpdateTime = Integer.parseInt(DSRApp.dsrLdbs.getSingleValue(this,"config_value"," aerp_app_config where config_name='AttendenceLocationUpdateTime' "));*/
        long interval = locationUpdateTime == 0 ? DSRAppConstant.DEFAULT_ATTENDENCE_INTERVAL : (1000 * 60 * locationUpdateTime);
        System.out.println("Location Update Interval in MilliSeconds" + interval);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        manager.setRepeating(AlarmManager.RTC_WAKEUP, new Date().getTime() + 5000, interval, pendingIntent);
    }

//    private void gotoDynamiciSteerWebApp(String fetchedurl, String username, String password) {
//        /*startActivity(new Intent(this, DsrDynamicWebApp.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));*/
//        Intent dynamicwebapp = new Intent(this, SRVDynamicWebApp.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        dynamicwebapp.putExtra("fetchedurl", fetchedurl);
//        dynamicwebapp.putExtra("username", username);
//        dynamicwebapp.putExtra("password", password);
//        startActivity(dynamicwebapp);
//    }

    class verifyAuthenticatedUser extends AsyncTask<String, String, String> {

        @SuppressLint("LongLogTag")
        protected String doInBackground(String... urls) {
            uriInProgress = urls[0];
            Log.e("postUrl : ", uriInProgress);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(uriInProgress);
            try {
                String jsonString = "";
                try {
                    JSONObject json = new JSONObject();
                    json.put("user_id", App.appPreference.getUserId());
                    json.put("Token", App.appPreference.getToken());
                    jsonString = json.toString();
                    Log.e("json : ", json.toString());
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
            updateProgress(PROGRESS_MSG);
        }

        protected void onPostExecute(String responseString) {
            Log.e("responseString : ", "" + responseString);
            dismissProgress();
            if (responseString == null) {
                Toast.makeText(MenuScreen.this, "Network Error", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                JSONObject responseJson;
                responseJson = new JSONObject(responseString);
                if (responseJson.has(DSRAppConstant.KEY_STATUS) && responseJson.getString(DSRAppConstant.KEY_STATUS).equalsIgnoreCase("Success")) {
                    String finalurl = responseJson.getString("url");
                    String username = responseJson.getString("username");
                    String password = responseJson.getString("password");
                    //gotoDynamiciSteerWebApp(finalurl, username, password);
                } else {
                    Toast.makeText(MenuScreen.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSONException : ", e.toString());
            }
        }
    }

    @Override
    public void onClick(View pView) {

        switch (pView.getId()) {

            case R.id.ll_Logout:
                showConfirmationAlert();
                break;

                case R.id.ll_sync:
                gotoSRVSyncScreen();
                break;

            case R.id.ll_servicecalls:
                gotoSRVCallListScreen();
                break;

            default:
                break;
        }
    }

    private void gotoDSRSyncScreen() {
        startActivity(new Intent(this, SRVSyncScreen.class).setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }


    class SyncTaskToServer extends AsyncTask<String, String, String> {
        private int operationType;

        @SuppressLint("LongLogTag")
        protected String doInBackground(String... urls) {
            uriInProgress = urls[0];
            Log.e("postUrl : ", uriInProgress);
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(uriInProgress);
            try {
                String jsonString = "";
                try {
                    jsonString = (new JSONObject().put(DSRAppConstant.KEY_VALUES, getAsyncAttendenceList())).toString();
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
                    JSONArray keys = responseJson.getJSONArray("attendence");
                    for (int i = 0; i <= keys.length() - 1; i++) {

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSONException : ", e.toString());
            }
        }
    }

    private JSONArray getAsyncAttendenceList() throws JSONException {
        return new JSONArray(new Gson().toJson(App.getINSTANCE().getRoomDB().daoAttendanceLog().getAsyncronizedAttendenceList()));

    }

    public void scanBar(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            //Intent intent = new Intent(this, CaptureActivity.class);
            //intent.setAction(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            //intent.putExtra("SCAN_FORMATS", "UPC_E");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    public void scanQR(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            //Intent intent = new Intent(this, CaptureActivity.class);
            //intent.setAction(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {

                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
            }
        } else if (requestCode == 100) {
            if (resultCode == RESULT_OK) {

            }

        }
    }

    private void gotoSRVSyncScreen() {

        startActivity(new Intent(this, SRVSyncScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    private void gotoSRVCallListScreen() {
        SRVCallListScreen.isToRefresh = true;
        startActivity(new Intent(this, SRVCallListScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }



}