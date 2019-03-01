package com.isteer.service.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.isteer.service.R;
import com.isteer.service.app.App;
import com.isteer.service.config.DSRAppConstant;
import com.isteer.service.config.SRVAppConstant;
import com.isteer.service.gson.GsonMasterItem;
import com.isteer.service.model.CustomerData;
import com.isteer.service.model.CustomerIndividual;
import com.isteer.service.model.ImagePathData;
import com.isteer.service.model.MasterItemData;
import com.isteer.service.model.ResponseWorklogPraser;
import com.isteer.service.model.ServiceCallData;
import com.isteer.service.model.WorkLogData;
import com.isteer.service.volley.VolleyHttpRequest;
import com.isteer.service.viewModel.WorklogViewModel;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.isteer.service.app.App.appPreference;

public class SRVSyncScreen extends AppCompatActivity implements OnClickListener {

    private TextView header_title;
    private ImageView btn_header_left;
    private TextView tv_sync_message_one, alertmessage;
    private ImageView spinnerImage;
    private Animation mRotateAnim;
    private Button btnSyncOK;
    private Handler myHandler = new Handler();

    //private ArrayList<String> customerList = new ArrayList<String>();

    public static boolean isSyncSuccess = false;
    public static boolean isSyncInProgress = false;
    public static boolean isAllRecordsFetched = false;
    public static boolean isFromLogin = false;

    private WorklogViewModel viewModel;

    //public static boolean isByReset = true;
    public static int manufacturerCode = 0;
    private Dialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scr_srv_sync);

        viewModel = ViewModelProviders.of(SRVSyncScreen.this).get(WorklogViewModel.class);


        initVar();
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (!SRVLoginScreen.isSessionValid())
            gotoSEMSplashScreen();
        else if (isSyncInProgress)
            return;
        else {
            if (!isSyncInProgress)
                resetProgression();

            if (App.appUtils.isNetAvailable()) {
                btnSyncOK.setAlpha(1.0f);
                alertmessage.setVisibility(View.GONE);


                if (!appPreference.isDbFilled() || appPreference.getDBFilledTime().equalsIgnoreCase("")) {
/*					if(!App.appPreference.isFilledCustomers())
                        syncCustomersFromServer();
					else if(!App.appPreference.isFilledCustomerIndividual())
						syncCustomersIndFromServer();			*/

                    if (!appPreference.isFilledServiceCalls())
                        syncServiceCallsFromServer();
                    else if (!appPreference.isFilledWorklogs())
                        syncWorklogsFromServer();
                    else if (!appPreference.isFilledUploadFiles())
                        syncUploadFilesFromServer();
                    else if (!appPreference.isFilledMasterItem())
                        syncMasterItemsFromServer();
                    else {
                        btnSyncOK.setText("Sync");
                        updateMessageColor(SRVAppConstant.SYNC_STATUS_GREEN, "Sync Completed Successfully");
                    }
                } else {
                    btnSyncOK.setText("Sync");
                    updateMessageColor(SRVAppConstant.SYNC_STATUS_GREEN, "Sync Completed Successfully");
                }
            } else {
                toggleLoader(false);
                btnSyncOK.setAlpha(0.5f);
                btnSyncOK.setText("Sync");
                alertmessage.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initVar() {

        header_title = (TextView) findViewById(R.id.header_title);
        header_title.setText("Sync Data");

        btn_header_left = (ImageView) findViewById(R.id.btn_header_left);
        btn_header_left.setVisibility(View.VISIBLE);
        btn_header_left.setOnClickListener(this);
        findViewById(R.id.btn_header_right).setOnClickListener(this);
        /*((View) findViewById(R.id.bottombar_one)).setOnClickListener(this);
        ((View) findViewById(R.id.bottombar_two)).setOnClickListener(this);
		((View) findViewById(R.id.bottombar_three)).setOnClickListener(this);
		*/
        btnSyncOK = (Button) findViewById(R.id.btnSyncOK);
        btnSyncOK.setOnClickListener(this);

        spinnerImage = (ImageView) findViewById(R.id.loading);
        mRotateAnim = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);

        alertmessage = (TextView) findViewById(R.id.alertmessage);
        tv_sync_message_one = (TextView) findViewById(R.id.tv_sync_message_one);

    }

    @Override
    public void onClick(View pView) {

        switch (pView.getId()) {

            case R.id.btn_header_right:
                //gotoURL("http://www.getisteer.com/");
                gotoB2CMenuScreen();
                break;

            case R.id.btn_header_left:
                gotoB2CMenuScreen();
                break;

            case R.id.btnSyncOK:
                if (App.appUtils.isNetAvailable()) {
                    if (btnSyncOK.getText().toString().equalsIgnoreCase("Sync") || btnSyncOK.getText().toString().equalsIgnoreCase("Retry")) {

                        if (!appPreference.isUploadedNewWorklog()) {
                            syncDataToServer();
                        } else {

                        }
                        clearDatabase();
                        syncDataFromServer();
                    }
                }
                break;
        }
    }

    private void syncDataToServer() {

    }

    public void getServerWorklog() {
        HashMap<String, String> header = new HashMap<>();

        VolleyHttpRequest.dialog = getResources().getString(R.string.loader);
        JSONObject jsonObjectHeader = new JSONObject();
        JSONObject json = new JSONObject(header);

        if (App.appUtils.isNetAvailable()) {

            VolleyHttpRequest.makeVolleyPostHeader(this, App.appPreference.getBaseUrl() + SRVAppConstant.METHOD_ADD_WORKLOG,

                    getLocalWorklog(), jsonObjectHeader, SRVAppConstant.METHOD_ADD_WORKLOG);
        } else {
            dismissProgress();
            Toast.makeText(this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

        }

    }

    @SuppressLint("LongLogTag")
    private JSONObject getLocalWorklog() {
        ArrayList<WorkLogData> workLogArrayList = new ArrayList<WorkLogData>();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            workLogArrayList = (ArrayList<WorkLogData>) App.getINSTANCE().getRoomDB().daoWorkLog().getLocalWorkLogDatas();

            if (workLogArrayList.size() == 0) {
                jsonObject.put("vals", jsonArray);


                return jsonObject;
            } else {
                for (WorkLogData data : workLogArrayList) {
                    JSONObject json = new JSONObject();

                    json.put("oem_key", data.getScl_key());
                    json.put("sename", data.getService_key());
                    json.put("sekey", data.getProject_key());
                    json.put("lead_status", data.getService_type());
                    json.put("lead_status", data.getStart_time());
                    json.put("lead_status", data.getEnd_time());
                    json.put("lead_status", data.getStatus());
                    json.put("lead_status", data.getIs_spare_parts_needed());
                    json.put("lead_status", data.getService_eng_count());
                    json.put("lead_status", data.getRemarks());
                    json.put("lead_status", data.getEntered_by());
                    json.put("lead_status", data.getEntered_date_time());
                    json.put("lead_status", data.getEntered_by_name());
                    json.put("lead_status", data.getEntered_by());

                    jsonArray.put(json);
                }
                jsonObject.put("vals", jsonArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    private void dismissProgress() {
        if (pdialog != null && pdialog.isShowing())
            pdialog.dismiss();
    }

    private void resetProgression() {
        toggleLoader(false);
        updateMessageColor(SRVAppConstant.SYNC_STATUS_RED, "Sync Data");
    }

    private void toggleLoader(boolean showLoader) {
        if (showLoader) {
            btnSyncOK.setVisibility(View.GONE);
            spinnerImage.setVisibility(View.VISIBLE);
            spinnerImage.startAnimation(mRotateAnim);
        } else {
            spinnerImage.clearAnimation();
            spinnerImage.setVisibility(View.GONE);
            btnSyncOK.setVisibility(View.VISIBLE);
        }
    }

    private void updateMessageColor(int syncStatus, String message) {

        if (syncStatus == SRVAppConstant.SYNC_STATUS_RED)
            btnSyncOK.setText("Retry");
        else
            btnSyncOK.setText("Sync");

        tv_sync_message_one.setText(message);

        if (syncStatus == SRVAppConstant.SYNC_STATUS_RED) {
            tv_sync_message_one.setTextColor(getResources().getColor(R.color.dark_red));
        } else if (syncStatus == SRVAppConstant.SYNC_STATUS_YELLOW) {
            tv_sync_message_one.setTextColor(getResources().getColor(R.color.dark_yellow));
        } else if (syncStatus == SRVAppConstant.SYNC_STATUS_GREEN) {
            tv_sync_message_one.setTextColor(getResources().getColor(R.color.dark_green));
        }

    }

    private void refreshDB() {
        syncDataFromServer();
    }

    class SyncManager extends AsyncTask<String, String, String> {

        private int operationType;
        private String uriInProgress;

        @SuppressLint("LongLogTag")
        protected String doInBackground(String... urls) {

            uriInProgress = urls[0];


            Log.e("URLS : ", uriInProgress);

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(uriInProgress);
            try {

                operationType = getOperationType(uriInProgress);

                String jsonString = "";

                try {

                    jsonString = getJsonParams(operationType).toString();

                    Log.e("input :", jsonString);


                } catch (JSONException e) {
                    e.printStackTrace();

                }

                StringEntity se = new StringEntity(jsonString);
                httppost.setEntity(se);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                return httpclient.execute(httppost, responseHandler);

            } catch (ClientProtocolException e) {

            } catch (IOException e) {

            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            isSyncInProgress = true;
            toggleLoader(true);
        }

        protected void onPostExecute(String responseString) {

            Log.e("response : ", "" + responseString);


            if (responseString == null) {

                if (operationType == SRVAppConstant.OPTYPE_GETMYSERVICECALLS) {
                    Toast.makeText(SRVSyncScreen.this, "Sync Service Calls Failed", Toast.LENGTH_SHORT).show();
                    isSyncSuccess = false;
                    syncWorklogsFromServer();
                } else if (operationType == SRVAppConstant.OPTYPE_GETWORKLOGS) {
                    Toast.makeText(SRVSyncScreen.this, "Sync Work Logs Failed", Toast.LENGTH_SHORT);
                    isSyncSuccess = false;
                    syncUploadFilesFromServer();
                } else if (operationType == SRVAppConstant.OPTYPE_GETIMAGEPATHS) {
                    Toast.makeText(SRVSyncScreen.this, "Sync Image Files Failed", Toast.LENGTH_SHORT);
                    isSyncSuccess = false;
                    syncMasterItemsFromServer();
                } else if (operationType == SRVAppConstant.OPTYPE_GET_MASTER_ITEMS) {
                    Toast.makeText(SRVSyncScreen.this, "Sync Master Items Failed", Toast.LENGTH_SHORT);
                    isSyncSuccess = false;
                    toggleLoader(false);
                    updateMessageColor(SRVAppConstant.SYNC_STATUS_RED, "Sync Failed");
                } else if (operationType == SRVAppConstant.OPTYPE_GET_CUST_DET) {
                }

            } else {
                if (operationType == SRVAppConstant.OPTYPE_GETMYSERVICECALLS)
                    onPostGetMyServiceCalls(responseString);
                else if (operationType == SRVAppConstant.OPTYPE_GETWORKLOGS)
                    onPostGetMyWorklogs(responseString);
                else if (operationType == SRVAppConstant.OPTYPE_GETIMAGEPATHS)
                    onPostGetImageFiles(responseString);
                else if (operationType == SRVAppConstant.OPTYPE_GET_MASTER_ITEMS)
                    onPostMasterItem(responseString);
            }
        }
    }

    private int getOperationType(String uri) {

        int optype = SRVAppConstant.OPTYPE_UNKNOWN;

        if (uri.contains(SRVAppConstant.METHOD_GETMYSERVICECALLS))
            optype = SRVAppConstant.OPTYPE_GETMYSERVICECALLS;
        else if (uri.contains(SRVAppConstant.METHOD_GETWORKLOGS))
            optype = SRVAppConstant.OPTYPE_GETWORKLOGS;
        else if (uri.contains(SRVAppConstant.METHOD_GET_CUST_DET))
            optype = SRVAppConstant.OPTYPE_GET_CUST_DET;
        else if (uri.contains(SRVAppConstant.METHOD_GET_CUST_LOC))
            optype = SRVAppConstant.OPTYPE_GET_CUST_LOC;
        if (uri.contains(SRVAppConstant.METHOD_GET_MASTER_ITEM))
            optype = SRVAppConstant.OPTYPE_GET_MASTER_ITEMS;
        if (uri.contains(SRVAppConstant.METHOD_GETALLIMAGEPATHS))
            optype = SRVAppConstant.OPTYPE_GETIMAGEPATHS;
        return optype;

    }

    private JSONObject getJsonParams(int opType) throws JSONException {

        JSONObject json = new JSONObject();

        if (opType == SRVAppConstant.OPTYPE_GETMYSERVICECALLS || opType == SRVAppConstant.OPTYPE_GETWORKLOGS
                || opType == SRVAppConstant.OPTYPE_GETIMAGEPATHS) {
            json.put(DSRAppConstant.KEY_USER_ID, appPreference.getUserId());
            json.put(DSRAppConstant.KEY_UNIT_KEY, appPreference.getUnitKey());
        } else if (opType == SRVAppConstant.OPTYPE_GET_CUST_DET) {
            json.put(DSRAppConstant.KEY_USER_KEY, appPreference.getUserId());
            json.put(DSRAppConstant.KEY_PUNIT_KEY, appPreference.getUnitKey());
        } else if (opType == SRVAppConstant.OPTYPE_GET_CUST_LOC) {
            json.put(DSRAppConstant.KEY_USER_KEY, appPreference.getUserId());
            json.put(SRVAppConstant.KEY_SE_KEY, appPreference.getSekey());
        } else if (opType == SRVAppConstant.OPTYPE_GET_MASTER_ITEMS) {
            json.put(SRVAppConstant.KEY_USER_KEY, appPreference.getUserId());
        }

        return json;
    }

    protected void onPostGetMyServiceCalls(String responseString) {

        ServiceCallData serviceCallList = new Gson().fromJson(responseString, ServiceCallData.class);

        appPreference.setIsFilledServiceCalls(true);
        App.getINSTANCE().getRoomDB().daoServicesCall().insertAllServiceCall(serviceCallList.getData());

        appPreference.setIsDbFilled(appPreference.isFilledServiceCalls() && appPreference.isDbFilled());
        syncWorklogsFromServer();

    }

    protected void onPostGetMyWorklogs(String responseString) {

        JSONObject responseOuterJson;

        String message = "Operation failed in a timely manner. Please try again";

        try {

            responseOuterJson = new JSONObject(responseString);
            if (responseOuterJson.has(DSRAppConstant.KEY_STATUS) && responseOuterJson.getInt(DSRAppConstant.KEY_STATUS) == 1
                    && responseOuterJson.has(DSRAppConstant.KEY_DATA)) {
                ResponseWorklogPraser worklogData = new Gson().fromJson(responseString, ResponseWorklogPraser.class);


                if (worklogData.getData().getWorklog().size() == 0) {
                    toggleLoader(false);
                    updateMessageColor(SRVAppConstant.SYNC_STATUS_GREEN, "Sync Completed Successfully");
//                    return;
                }

                viewModel.syncWorklog(worklogData.getData().getWorklog());
                viewModel.syncSpareParts(worklogData.getData().getSparepart());


                appPreference.setIsFilledWorklogs(true);
                appPreference.setIsDbFilled(appPreference.isFilledWorklogs() && appPreference.isDbFilled());
                syncUploadFilesFromServer();
                return;

            } else {

                if (responseOuterJson.has(DSRAppConstant.KEY_MSG))
                    message = responseOuterJson.getString(DSRAppConstant.KEY_MSG);
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

        syncUploadFilesFromServer();
    }

    protected void onPostGetImageFiles(String responseString) {


        String message = "Operation failed in a timely manner. Please try again";


        ImagePathData imagePathList = new Gson().fromJson(responseString, ImagePathData.class);


        if (imagePathList.getData().size() <= 0) {
            toggleLoader(false);
            syncMasterItemsFromServer();
            updateMessageColor(SRVAppConstant.SYNC_STATUS_GREEN, "Sync Completed Successfully");
            return;
        }

        App.getINSTANCE().getRoomDB().daoImageUpload().insertImageUpload(imagePathList.getData());

        appPreference.setIsFilledUploadFiles(true);
        appPreference.setIsDbFilled(appPreference.isFilledUploadFiles() && appPreference.isDbFilled());
        syncMasterItemsFromServer();

    }

    protected void onPostMasterItem(String responseString) {
        JSONObject responseOuterJson;
        ArrayList<MasterItemData> masterItemDataArrayList = new ArrayList<MasterItemData>();

        String message = "Operation failed in a timely manner. Please try again";
        System.out.println("Inside onPostMasterItem ");
        System.out.println("*******************************************Sync Completed************************************************************");
        try {
            responseOuterJson = new JSONObject(responseString);

            if (responseOuterJson.getString(SRVAppConstant.KEY_STATUS).equalsIgnoreCase("1")) {

                GsonMasterItem gsonMasterItem = new Gson().fromJson(responseString, GsonMasterItem.class);
                masterItemDataArrayList = (ArrayList<MasterItemData>) gsonMasterItem.getData();


                App.getINSTANCE().getRoomDB().daoMasterItem().insertMasterItemDataList(masterItemDataArrayList);


                App.appPreference.setIsFilledMasterItem(true);

                appPreference.setIsDbFilled(appPreference.isFilledMasterItem()
                        && appPreference.isDbFilled());


                appPreference.setDBFilledTime("" + new Date().getTime());

                updateMessageColor(SRVAppConstant.SYNC_STATUS_GREEN, "Sync Completed Successfully");

                isSyncInProgress = false;
                toggleLoader(false);
//                return;

                gotoB2CMenuScreen();


            } else {
                if (responseOuterJson.has(DSRAppConstant.KEY_MSG))
                    message = responseOuterJson.getString(DSRAppConstant.KEY_MSG);
            }
        } catch (JSONException e) {
            e.printStackTrace();

            updateMessageColor(SRVAppConstant.SYNC_STATUS_RED, "Sync Failed");

        }

        isSyncInProgress = false;
        toggleLoader(false);

        return;
    }


    private void syncDataFromServer() {

        syncServiceCallsFromServer();
    }

    private void clearDatabase() {
        App.getINSTANCE().getRoomDB().clearAllTables();

        appPreference.setIsDbFilled(false);
        appPreference.setIsFilledServiceCalls(false);
        appPreference.setIsFilledWorklogs(false);
        appPreference.setIsFilledUploadFiles(false);
        appPreference.setIsFilledMasterItem(false);

    }

    private void syncMasterItemsFromServer() {
        isSyncInProgress = true;
        updateMessageColor(SRVAppConstant.SYNC_STATUS_YELLOW, "Syncing MasterItems");
        new SyncManager().execute(appPreference.getBaseUrl()
                + SRVAppConstant.METHOD_GET_MASTER_ITEM);
    }

    private void syncServiceCallsFromServer() {
        isSyncInProgress = true;
        updateMessageColor(SRVAppConstant.SYNC_STATUS_YELLOW, "Syncing Service Calls");
        new SyncManager().execute(appPreference.getBaseUrl()
                + SRVAppConstant.METHOD_GETMYSERVICECALLS);
    }

    private void
    syncWorklogsFromServer() {
        isSyncInProgress = true;
        updateMessageColor(SRVAppConstant.SYNC_STATUS_YELLOW, "Syncing Work Logs");
        new SyncManager().execute(appPreference.getBaseUrl() +
                SRVAppConstant.METHOD_GETWORKLOGS);
    }

    private void syncUploadFilesFromServer() {
        isSyncInProgress = true;
        updateMessageColor(SRVAppConstant.SYNC_STATUS_YELLOW, "Syncing Image Files");
        new SyncManager().execute(appPreference.getBaseUrl() +
                SRVAppConstant.METHOD_GETALLIMAGEPATHS);
    }

	/*	private void syncCustomersFromServer() {
	
	isSyncInProgress = true;
	updateMessageColor(SRVAppConstant.SYNC_STATUS_YELLOW, "Syncing Customers");
	new SyncManager().execute(App.appPreference.getBaseUrl2()
			+ SRVAppConstant.METHOD_GET_CUST_DET);
	}

	private void syncCustomersIndFromServer() {
	
		isSyncInProgress = true;
		updateMessageColor(SRVAppConstant.SYNC_STATUS_YELLOW, "Syncing Customers");
		new SyncManager().execute(App.appPreference.getBaseUrl2()
				+ SRVAppConstant.METHOD_GET_CUST_LOC);
	}

	private void refillCustomers()
	{
		Cursor tCursor = App.localDBS.fetchDistinct(this, SRVTableCreate.TABLE_CONTACT_MASTER, SRVTableCreate.COLOUMNS_CONTACT_MASTER.cmkey.name(),
				SRVTableCreate.COLOUMNS_CONTACT_MASTER.cmkey.name());
		customerList = new SRVCursorFactory().fetchAColoumn(tCursor, SRVTableCreate.COLOUMNS_CONTACT_MASTER.cmkey.name());
	}*/

    @Override
    public void onBackPressed() {

        goBack();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            goBack();
            return true;

        }

        return super.onKeyDown(keyCode, event);
    }

    private void goBack() {
        gotoB2CMenuScreen();
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

    private void gotoSEMSplashScreen() {

        startActivity(new Intent(this, SRVSplashScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    private void gotoURL(String weblink) {
        Intent lLink = new Intent(Intent.ACTION_VIEW, Uri.parse(weblink));
        startActivity(lLink);
    }

    private void gotoB2CMenuScreen() {

        startActivity(new Intent(this, MenuScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    private void gotoSRVCallListScreen() {

        SRVCallListScreen.isToRefresh = true;

        startActivity(new Intent(this, SRVCallListScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

}
