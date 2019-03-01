package com.isteer.service.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.isteer.service.R;
import com.isteer.service.adapter.SRVWorklogsAdapter;
import com.isteer.service.app.App;
import com.isteer.service.model.WorkLogData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SRVWorklogListScreen extends Activity implements OnClickListener {

    private String TAG = "SRVWorklogListScreen";

    private PopupWindow ppWindow;
    private static ProgressDialog pdialog;
    private static String PROGRESS_MSG = "Refreshing...";

    public static boolean isFromHome = false;
    public static boolean isToCloseAlert = false;
    public static boolean toRefresh = false;
    public static int currentServiceCallKey;

    private TextView header_title;
    private SimpleDateFormat simpleFormat;

    private ListView entryList;
    private SRVWorklogsAdapter worklogsAdapter;

    private double altitude, latitude, longitude;
    public static ArrayList<WorkLogData> entries = new ArrayList<WorkLogData>();
    public static WorkLogData currentWorklog = new WorkLogData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.scr_srv_workloglist);

        initVar();
    }

    private void initVar() {

        header_title = (TextView) findViewById(R.id.header_title);
        header_title.setText("Worklogs");

        ((View) findViewById(R.id.btn_header_right)).setOnClickListener(this);
        ((View) findViewById(R.id.btn_header_left)).setOnClickListener(this);

        //CellLocation.requestLocationUpdate();

        entryList = ((ListView) findViewById(R.id.entryList));

    }

    @Override
    protected void onResume() {

        super.onResume();

        if (toRefresh) {
            toRefresh = false;
            refreshList();
        }
    }

    @Override
    public void onClick(View pView) {

        if (App.appUtils.isNetAvailable()) {

            switch (pView.getId()) {

                case R.id.btn_header_left:
                    goBack();
                    break;
                case R.id.btn_header_right:
                    gotoSRVMenuScreen();
                    break;

            }

        } else
            alertUserP(this, "Connection Error",
                    "No Internet connection available", "OK");

    }

    private void refreshList() {
        entries.clear();

        entries = (ArrayList<WorkLogData>) App.getINSTANCE().getRoomDB().daoWorkLog().fetchAllWorkLogData(SRVCallViewScreen.currentServiceCall.getServicecall_key());

        worklogsAdapter = new SRVWorklogsAdapter(this, entries);
        entryList.setAdapter(worklogsAdapter);

        entryList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });
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



    public void onRemarksClicked(int pos) {
        alertUserP(SRVWorklogListScreen.this, "Remarks",
                "" + entries.get(pos).getRemarks(), "OK");
    }

    public void onMOMClicked(int pos) {
        alertUserP(SRVWorklogListScreen.this, "Minutes of meeting",
                "" + entries.get(pos).getMinutes_of_meeting(), "OK");
    }

    private void goBack() {

        gotoSRVCallViewScreen();
    }

    /*
        class PostRequestManager extends AsyncTask<String, String, String> {

            private int operationType;

            protected String doInBackground(String... uri) {

                String postUrl = uri[0];

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(postUrl);
                try {

                    uriInProgress = uri[0];
                    operationType = getOperationType(uriInProgress);

                    Log.e("uriInProgress", "" + uriInProgress.toString());

                    StringEntity se = new StringEntity(
                            getJsonPayload(operationType));
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

                    alertUserP(SRVCallListScreen.this, "Failed",
                            "Unable to fetch data in a timely manner", "OK");
                } else {


                }
            }
        }
    */
    private String getCurrentTime() {

        return simpleFormat.format(new Date().getTime());
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

    private void gotoSRVCallViewScreen() {

        startActivity(new Intent(this, SRVCallViewScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    private void gotoSRVCallListScreen() {

        startActivity(new Intent(this, SRVCallListScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    private void gotoSRVMenuScreen() {

        startActivity(new Intent(this, MenuScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    private void gotoSRVSyncScreen() {

        startActivity(new Intent(this, SRVSyncScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }


}
