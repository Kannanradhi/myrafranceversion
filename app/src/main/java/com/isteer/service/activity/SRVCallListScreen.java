package com.isteer.service.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.isteer.service.R;
import com.isteer.service.adapter.SRVServiceCallsAdapter;
import com.isteer.service.app.App;
import com.isteer.service.config.DSRAppConstant;
import com.isteer.service.config.SRVAppConstant;
import com.isteer.service.model.ServiceCallData;
import com.isteer.service.volley.VolleyHttpRequest;
import com.isteer.service.volley.VolleyTaskListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.isteer.service.app.App.appPreference;

public class SRVCallListScreen extends Activity implements VolleyTaskListener, OnClickListener, SwipeRefreshLayout.OnRefreshListener, SRVServiceCallsAdapter.OnclickServiceCall {

    private String TAG = "SRVCallListScreen";

    private int NETWORK_CALL_SERVICECALL = 1;

    private static ProgressDialog pdialog;
    private static String PROGRESS_MSG = "Refreshing...";


    public static boolean isToRefresh = false;

    private TextView header_title;

    private RecyclerView entryList;
    private SRVServiceCallsAdapter serviceCallsAdaptor;

    public static ArrayList<ServiceCallData> entries = new ArrayList<ServiceCallData>();

    private SwipeRefreshLayout swipe_service_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.scr_srv_servicelist);

        initVar();
    }

    private void initVar() {

        header_title =  findViewById(R.id.header_title);
        header_title.setText("Service Calls");
        swipe_service_call = findViewById(R.id.swipe_service_call);
        swipe_service_call.setOnRefreshListener(this);

        ((View) findViewById(R.id.btn_header_right)).setOnClickListener(this);
        ((View) findViewById(R.id.btn_header_left)).setOnClickListener(this);

        //CellLocation.
        // ();

        entryList = findViewById(R.id.entryList);
        entryList.setLayoutManager(new LinearLayoutManager(this));
        serviceCallsAdaptor = new SRVServiceCallsAdapter(this);
        entryList.setAdapter(serviceCallsAdaptor);

    }

    @Override
    protected void onResume() {

        super.onResume();

        if (isToRefresh) {
            isToRefresh = false;
            refreshList();
        }

    }

    @Override
    public void onClick(View pView) {

        if (App.appUtils.isNetAvailable()) {

            switch (pView.getId()) {

                case R.id.btn_header_left:
                    gotoSRVMenuScreen();
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

        //Cursor cCursor = App.localDBS.fetchAll(this, SRVTableCreate.TABLE_SERVICECALL_MASTER, null);

        entries = (ArrayList<ServiceCallData>) App.getINSTANCE().getRoomDB().daoServicesCall().getPendingServiceCall(SRVAppConstant.SERVICE_STATUS_PENDING);
        serviceCallsAdaptor.setData(entries);
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
        gotoSRVMenuScreen();

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

    private void gotoSRVMenuScreen() {

        startActivity(new Intent(this, MenuScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    private void gotoSRVSyncScreen() {

        startActivity(new Intent(this, SRVSyncScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    @Override
    public void onRefresh() {
        getServiceCallFromServer();
    }

    private void getServiceCallFromServer() {

        VolleyHttpRequest.makeVolleyPost(this,
                appPreference.getBaseUrl() + SRVAppConstant.METHOD_GETMYSERVICECALLS,
                getInputReqServiceCall(), "" + NETWORK_CALL_SERVICECALL);
    }

    private JSONObject getInputReqServiceCall() {
        JSONObject json = new JSONObject();
        try {
            json.put(DSRAppConstant.KEY_USER_ID, appPreference.getUserId());
            json.put(DSRAppConstant.KEY_UNIT_KEY, appPreference.getUnitKey());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return json;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onclick(int pos) {
        SRVCallViewScreen.toRefresh = true;
        SRVCallViewScreen.currentServiceCall = entries.get(pos);
        gotoSRVCallViewScreen();
    }

    @Override
    public void handleResult(String method_name, JSONObject response)  {

        ServiceCallData serviceCallList = new Gson().fromJson(response.toString(), ServiceCallData.class);
        App.getINSTANCE().getRoomDB().daoServicesCall().insertAllServiceCall(serviceCallList.getData());
        refreshList();
        swipe_service_call.setRefreshing(false);

    }

    @Override
    public void handleError(String method_name, VolleyError e) {

    }
}
