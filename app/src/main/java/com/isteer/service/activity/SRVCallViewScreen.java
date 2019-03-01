package com.isteer.service.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.isteer.service.R;
import com.isteer.service.app.App;
import com.isteer.service.config.SRVAppConstant;
import com.isteer.service.model.ServiceCallData;

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
import java.util.HashMap;
import java.util.List;

public class SRVCallViewScreen extends Activity implements OnClickListener {

    private String TAG = "SRVCallViewScreen";

    public static boolean toRefresh = false;
    public static boolean skipForNow = false;

    public static boolean isPickerActive = false;
    public static boolean dontShowCityPicker = false;

    private static int currentStatus = SRVAppConstant.SERVICE_STATUS_PENDING;

    public static String serviceCallKey = null;
    public LocationManager location_manager;
    private static String PROGRESS_MSG = "Loading...";
    private String uriInProgress;
    private String datetimeFormat = "yyyy-MM-dd kk:mm:ss";

    public static ServiceCallData currentServiceCall;
    public static ArrayList<HashMap<String, String>> entries = new ArrayList<HashMap<String, String>>();

    private TextView header_title;
    private SearchView mSearchView;
    private static ProgressDialog pdialog;
    private Button btnLocate, btnAddWorkLog, btnViewWorkLogs;
    private TextView tv_customername, tv_service_contract, tv_reported_on, tv_service_status, tv_issue, tv_description, tv_site_address, tv_phone;
    //private View btn_header_left, btn_header_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scr_srv_view_servicecall);
        initVar();
    }

    @Override
    protected void onResume() {

        super.onResume();

        if (toRefresh) {
            toRefresh = false;
            updateCallDetails();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initVar() {

        location_manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );


        findViewById(R.id.btn_header_right).setOnClickListener(this);
        findViewById(R.id.btn_header_left).setOnClickListener(this);

        header_title = (TextView) findViewById(R.id.header_title);
        header_title.setText("Service Call");


        btnAddWorkLog = (Button) findViewById(R.id.btnAddWorkLog);
        btnAddWorkLog.setOnClickListener(this);

        btnViewWorkLogs = (Button) findViewById(R.id.btnViewWorkLogs);
        btnViewWorkLogs.setOnClickListener(this);

        tv_customername = (TextView) findViewById(R.id.tv_customername);
        tv_service_contract = (TextView) findViewById(R.id.tv_contract);
        tv_reported_on = (TextView) findViewById(R.id.tv_reported_on);
        tv_service_status = (TextView) findViewById(R.id.tv_service_status);

        tv_issue = (TextView) findViewById(R.id.tv_issue);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_site_address = (TextView) findViewById(R.id.tv_site_address);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        ((LinearLayout) findViewById(R.id.ll_locate)).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_locate)).setOnClickListener(this);
        setCustomStatus(SRVAppConstant.SERVICE_STATUS_PENDING);

    }

    private void updateCallDetails() {

        SRVWorklogListScreen.currentServiceCallKey = currentServiceCall.getServicecall_key();

        SRVAddWorklogScreen.SERVICECALL_ID = String.valueOf(currentServiceCall.getServicecall_key());


        tv_customername.setText("" + currentServiceCall.getCustomer_name());
        tv_service_contract.setText("" + currentServiceCall.getContract());
        tv_reported_on.setText("reported on " + App.appUtils.getFormattedDate("dd-MM-yyyy",
                App.appUtils.getTimestamp("yyyyMMdd", currentServiceCall.getReport_date())));
        tv_issue.setText("" + currentServiceCall.getService_issue());
        tv_description.setText("" + currentServiceCall.getService_desc());
        tv_site_address.setText("" + currentServiceCall.getCaller_address());
        tv_phone.setText("" + currentServiceCall.getCaller_mobile_no());

/*		Cursor syncCursor = DSRApp.dsrLdbs.fetchSelected(this, DSRTableCreate.TABLE_AERP_EVENT_MASTER , null, DSRTableCreate.COLOUMNS_EVENT_MASTER.event_key.name(), DSRLocalDBStorage.SELECTION_OPERATION_LIKE, new String[]{eventKey});

		if(syncCursor==null)
		{
			Toast.makeText(this, "Unable to fetch data in a timely manner", Toast.LENGTH_LONG);
		}
		else
		{
			currentEvent = new EventData();
			
			int columnIndexEventKey = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_key.name());
			int columnIndexEventUserKey = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_user_key.name());
			int columnIndexCustomerName = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.customer_name.name());
			int columnIndexEvenTitle = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_title.name());
			int columnIndexStatus = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.status.name());
			int columnIndexType = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_type.name());
			int columnIndexDesc = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_description.name());
			int columnIndexArea = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.area.name());
			int columnIndexFromTime = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.from_date_time.name());
			int columnIndexToTime = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.to_date_time.name());
			int columnIndexPurpose = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.preparation.name());
			int columnIndexObjective = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.objective.name());
			int columnIndexPlan = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.plan.name());
			int columnIndexStrategy = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.strategy.name());
			int columnIndexAction = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.action_response.name());
			int columnIndexEventDate = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_date.name());
			int columnIndexActionNew = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.action.name());
			int columnIndexMoMeet = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.mins_of_meet.name());
			int columnIndexCmkey = syncCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.cmkey.name());
	
			currentEvent.setEvent_key(syncCursor.getString(columnIndexEventKey));
			currentEvent.setEvent_user_key(syncCursor.getString(columnIndexEventUserKey));
			currentEvent.setCustomer_name(syncCursor.getString(columnIndexCustomerName));
			currentEvent.setEvent_title(syncCursor.getString(columnIndexEvenTitle));
			currentEvent.setArea(syncCursor.getString(columnIndexArea));
			currentEvent.setStatus(syncCursor.getString(columnIndexStatus));
			currentEvent.setEvent_type(syncCursor.getString(columnIndexType));
			currentEvent.setEvent_description(syncCursor.getString(columnIndexDesc));
			currentEvent.setCustomer_name(syncCursor.getString(columnIndexCustomerName));
			currentEvent.setFrom_date_time(syncCursor.getString(columnIndexFromTime));
			currentEvent.setTo_date_time(syncCursor.getString(columnIndexToTime));
			currentEvent.setPreparation(syncCursor.getString(columnIndexPurpose));
			currentEvent.setObjective(syncCursor.getString(columnIndexObjective));
			currentEvent.setPlan(syncCursor.getString(columnIndexPlan));
			currentEvent.setStrategy(syncCursor.getString(columnIndexStrategy));
			currentEvent.setAction_response(syncCursor.getString(columnIndexAction));
			currentEvent.setEvent_date(syncCursor.getString(columnIndexEventDate));
			currentEvent.setAction(syncCursor.getString(columnIndexActionNew));
			currentEvent.setMinutes_of_meet(syncCursor.getString(columnIndexMoMeet));
			currentEvent.setCmkey(syncCursor.getString(columnIndexCmkey));
			
			syncCursor.close();
			
			tv_customername.setText(""+currentEvent.getCustomer_name());
			et_customername.setText(""+currentEvent.getCustomer_name());
	
			tv_title.setText(""+currentEvent.getEvent_title());
			et_title.setText(""+currentEvent.getEvent_title());
			
			setCustomStatus(currentEvent.getStatus());
			
		}
		*/
    }

    private void setCustomStatus(int statusToSet) {/*

		currentStatus = SRVAppConstant.SERVICE_STATUS_PENDING;

		if(statusToSet==SRVAppConstant.SERVICE_STATUS_PENDING)
		{
			tvb_plan_status.setTextColor(Color.parseColor(ColorCodes.COLOR_RED));
			tvb_plan_status.setText(planList[0]);
			btnVisited.setVisibility(View.VISIBLE);
			wrapper2_viewplan_buttons.setVisibility(View.VISIBLE);
			setButtonset(true);
			return;
		}
		else if(statusToSet==SRVAppConstant.SERVICE_STATUS_COMPLETED)
		{
			wrapper2_viewplan_buttons.setVisibility(View.GONE);
			tvb_plan_status.setTextColor(Color.YELLOW);
		}
		else if(statusToSet==SRVAppConstant.SERVICE_STATUS_CANCELLED)
		{
			wrapper2_viewplan_buttons.setVisibility(View.GONE);
			tvb_plan_status.setTextColor(Color.parseColor(ColorCodes.COLOR_GREEN));
		}
		else if(statusToSet==SRVAppConstant.SERVICE_STATUS_ENTERED)
		{
			wrapper2_viewplan_buttons.setVisibility(View.GONE);
			tvb_plan_status.setTextColor(Color.parseColor(ColorCodes.COLOR_WHITE));
		}
		
		tvb_plan_status.setText(textToSet);
		btnVisited.setVisibility(View.GONE);
		setButtonset(true);

	*/
    }

/*	private void showCompleteButton(boolean show) {

		if(show)
		{
			btnCompleteProg.setVisibility(View.VISIBLE);
			LayoutParams lprams1 = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 8);
			modify_programs_scroll.setLayoutParams(lprams1);
		}
		else
		{
			btnCompleteProg.setVisibility(View.GONE);
			LayoutParams lprams1 = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 9);
			modify_programs_scroll.setLayoutParams(lprams1);
		}
			
	}*/

    private void setButtonset(boolean isView) {/*
        showCompleteButton(!isView);

		if(isView)
		{
			
			switch (currentStatus) {
			
			case DSRAppConstant.STATUS_PENDING:
			case DSRAppConstant.STATUS_VISITED:
				btnEditProg.setText("Edit");
				break;

			case DSRAppConstant.STATUS_CONMPLETED:
			case DSRAppConstant.STATUS_CANCELLED:
				btnEditProg.setText("Back");
				break;

			default:
				break;
			}
		}
		else
		{

			switch (currentStatus) {
			
			case DSRAppConstant.STATUS_PENDING:
				btnEditProg.setText("Save");
				btnCompleteProg.setText("Cancel Visit");
				break;

			case DSRAppConstant.STATUS_VISITED:
				btnEditProg.setText("Save");
				btnCompleteProg.setText("Complete POPSA");
				break;

			default:
				break;
			}
		}
				
	*/
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
        gotoSRVCallListScreen();
    }

    private void setStatusVisited() {/*

		String tVisitedTime = ""+DateFormat.format(datetimeFormat, new Date().getTime());
		
		if(DSRApp.dsrLdbs.updateStatus(SRVCallViewScreen.this,  currentEvent.getEvent_key(), currentEvent.getEvent_user_key(),
				DSRAppConstant.EVENT_STATUS.Visited.name(), tVisitedTime, currentEvent.getAction_response(), currentEvent.getMinutes_of_meet()))
		{
			btnVisited.setVisibility(View.GONE);
			currentStatus = DSRAppConstant.STATUS_VISITED ;
			currentEvent.setStatus(DSRAppConstant.EVENT_STATUS.Visited.name());
			currentEvent.setVisit_update_time(tVisitedTime);
			currentEvent.setEvent_visited_latitude(""+locData.getLatitude());
			currentEvent.setEvent_visited_longitude(""+locData.getLongitude());
			currentEvent.setEvent_visited_altitude(""+locData.getAltitude());
			setCustomStatus(DSRAppConstant.EVENT_STATUS.Visited.name());
			
			tv_action_new.setText(currentEvent.getAction_response());
			et_action_new.setText(currentEvent.getAction_response());
			
			if(basicUtils.isNetAvailable())
			{
				new SyncToServerTask().execute(isvPreference.getBaseUrl()+DSRAppConstant.METHOD_SETASVISITED);
			}
			else
			{
				Toast.makeText(SRVCallViewScreen.this, "Saved locally", Toast.LENGTH_LONG).show();
				if(planNextVisit)
				{
					planNextVisit = false;
					cloneButtonClicked();
				}
			}
		}
		else
			Toast.makeText(SRVCallViewScreen.this, "Updation failed in a timely manner, please try again", Toast.LENGTH_LONG).show();
	*/
    }

    private void saveButtonClicked() {/*

		EventData tEvent = new EventData();
		
		tEvent.setEvent_key(currentEvent.getEvent_key());
		tEvent.setEvent_user_key(currentEvent.getEvent_user_key());
		tEvent.setEvent_title(""+et_title.getText());
		tEvent.setEvent_title(""+et_title.getText());
		tEvent.setArea(""+et_location.getText());
		tEvent.setStatus(currentEvent.getStatus());
		tEvent.setPreparation(""+et_purpose.getText());
		tEvent.setObjective(""+et_objective.getText());
		tEvent.setPlan(""+et_plan.getText());
		tEvent.setStrategy(""+et_strategy.getText());
		tEvent.setAction_response(""+et_action_new.getText());
		tEvent.setAction(""+et_action.getText());
		tEvent.setMinutes_of_meet(""+et_momeet.getText());

		if(DSRApp.dsrLdbs.updateData(SRVCallViewScreen.this,  tEvent))
		{
			
			currentEvent.setEvent_title(tEvent.getEvent_title());
			currentEvent.setArea(tEvent.getArea());
			currentEvent.setPreparation(tEvent.getPreparation());
			currentEvent.setObjective(tEvent.getObjective());
			currentEvent.setPlan(tEvent.getPlan());
			currentEvent.setStrategy(tEvent.getStrategy());
			currentEvent.setAction_response(tEvent.getAction_response());
			currentEvent.setAction(tEvent.getAction());
			currentEvent.setMinutes_of_meet(tEvent.getMinutes_of_meet());

			tv_location.setText(tEvent.getArea());
			tv_title.setText(tEvent.getEvent_title());
			tv_purpose.setText(tEvent.getPreparation());
			tv_objective.setText(tEvent.getObjective());
			tv_plan.setText(tEvent.getPlan());
			tv_strategy.setText(tEvent.getStrategy());
			tv_action.setText(tEvent.getAction());
			tv_action_new.setText(tEvent.getAction_response());
			tv_momeet.setText(tEvent.getMinutes_of_meet());
			
			showViewLayer(true);
			
			if(basicUtils.isNetAvailable())
				new SyncToServerTask().execute(isvPreference.getBaseUrl()+DSRAppConstant.METHOD_UPDATE_ALL_DATA);
			else
				Toast.makeText(SRVCallViewScreen.this, "Saved locally", Toast.LENGTH_LONG).show();
			
		}
		else
			Toast.makeText(SRVCallViewScreen.this, "Updation failed in a timely manner, please try again", Toast.LENGTH_LONG).show();
	*/
    }

    private void exitApp() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);

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

    private void SearchViewShow(MenuItem searchItem) {

        if (isAlwaysExpanded()) {
            mSearchView.setIconifiedByDefault(false);
        } else {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);
        }

        //mSearchView.setOnQueryTextListener(this);
    }

    public boolean onQueryTextChange(String newText) {

        return false;
    }

    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    public boolean onClose() {

        return false;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }

    @Override
    public void onClick(View pView) {

        switch (pView.getId()) {

            case R.id.btn_header_left:
                gotoSRVCallListScreen();
                break;

            case R.id.btn_header_right:
                gotoSRVMenuScreen();
                break;

            case R.id.btnAddWorkLog:
                SRVAddWorklogScreen.toRefresh = true;
                gotoSRVAddWorklogScreen();
                break;

            case R.id.btnViewWorkLogs:

                SRVWorklogListScreen.toRefresh = true;
                gotoSRVWorklogListScreen();
                break;

            case R.id.ll_locate:
                SRVLocateScreen.isToRefresh = true;



                if ( !location_manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                buildAlertMessageNoGps();

                }else {

                    gotoSRVLocateScreen();
                }



                break;

            case R.id.tv_locate:
                SRVLocateScreen.isToRefresh = true;

                if ( !location_manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    buildAlertMessageNoGps();
                }else {

                    gotoSRVLocateScreen();
                }


                break;

/*		case R.id.btnEditProg:
			
			if(((String) btnEditProg.getText()).equalsIgnoreCase("Back"))
				gotoDSRDayScreen();
			else
			{
			
				if(isShowingView)
				{
					showViewLayer(false);
				}
				else
				{
					saveButtonClicked();
				}
			}
			
			break;

		case R.id.btnClone:
			
			cloneButtonClicked();
			
			break;			
			
		case R.id.btnCancel:
			
			toSwitchView = false;
			cancelButtonClicked();
			
			break;	
			
		case R.id.btnCompleteProg:
			
			String statusSelected = btnCompleteProg.getText().toString();
						
			if(statusSelected.equalsIgnoreCase("Complete POPSA"))
			{
				completeButtonClicked();
			}
			else
			{
				toSwitchView = true;
				cancelButtonClicked();
			}
			
			break;*/

            default:
                break;
        }

    }

    private void completeButtonClicked() {/*
		final String tPurpose = ""+et_purpose.getText();
		final String tObjective = ""+et_objective.getText();
		final String tPlan = ""+et_plan.getText();
		final String tStrategy = ""+et_strategy.getText();
		final String tAction = ""+et_action.getText();
		final String tActionNew = ""+et_action_new.getText();
		final String tMOMeet = ""+ et_momeet.getText();

		if(tPurpose.equals("") || tObjective.equals("") || tPlan.equals("") || tStrategy.equals("") || tAction.equals("") || tActionNew.equals("") || tMOMeet.equals(""))
		{
			alertUserP(this, "Alert", "Please enter valid data to mark it as COMPLETED", "OK");
			return;
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SRVCallViewScreen.this);
		builder.setMessage(
				"Are you sure to mark it as COMPLETED ?")
				.setTitle("Alert!")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								dialog.cancel();
								
								String tCompletedTime = ""+DateFormat.format(datetimeFormat, new Date().getTime());

								if(DSRApp.dsrLdbs.updateStatus(SRVCallViewScreen.this,  currentEvent.getEvent_key(), currentEvent.getEvent_user_key(),
										DSRAppConstant.EVENT_STATUS.Completed.name(), tCompletedTime, "", ""))
								{
									currentStatus = DSRAppConstant.STATUS_CONMPLETED ;
									currentEvent.setStatus(DSRAppConstant.EVENT_STATUS.Completed.name());
									currentEvent.setCompleted_on(tCompletedTime);
									currentEvent.setPlan(tPlan);
									currentEvent.setObjective(tObjective);
									currentEvent.setPreparation(tPurpose);
									currentEvent.setStrategy(tStrategy);
									currentEvent.setAction_response(tAction);
									currentEvent.setAction(tActionNew);
									currentEvent.setMinutes_of_meet(tMOMeet);

									setCustomStatus(DSRAppConstant.EVENT_STATUS.Completed.name());
									
									if(isdUtils.isNetAvailable())
										new SyncToServerTask().execute(isdPreference.getBaseUrl()+DSRAppConstant.METHOD_SETASCOMPLETED);
									else
										Toast.makeText(DSREditProgScreen.this, "Saved locally", Toast.LENGTH_LONG).show();
									
									saveButtonClicked();								
								}
								else
									Toast.makeText(SRVCallViewScreen.this, "Updation failed in a timely manner, please try again", Toast.LENGTH_LONG).show();
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
		alert.show();
	*/
    }

    private void cancelButtonClicked() {/*
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SRVCallViewScreen.this);
		builder.setMessage(
				"Are you sure to mark it as CANCELLED ?")
				.setTitle("Alert!")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int id) {
								dialog.cancel();
								
								String tCancelledTime = ""+DateFormat.format(datetimeFormat, new Date().getTime());

								if(DSRApp.dsrLdbs.updateStatus(SRVCallViewScreen.this,  currentEvent.getEvent_key(), currentEvent.getEvent_user_key(),
										DSRAppConstant.EVENT_STATUS.Cancelled.name(), tCancelledTime, "", ""))
								{
									currentStatus = DSRAppConstant.STATUS_CANCELLED ;
									currentEvent.setStatus(DSRAppConstant.EVENT_STATUS.Cancelled.name());
									currentEvent.setCancelled_on(tCancelledTime);
									setCustomStatus(DSRAppConstant.EVENT_STATUS.Cancelled.name());

									if(App.appUtils.isNetAvailable())
										new SyncToServerTask().execute(App.appPreference.getBaseUrl()+DSRAppConstant.METHOD_SETASCANCELLED);
									else
										Toast.makeText(SRVCallViewScreen.this, "Saved locally", Toast.LENGTH_LONG).show();
								}
									else
										Toast.makeText(SRVCallViewScreen.this, "Updation failed in a timely manner, please try again", Toast.LENGTH_LONG).show();
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
		alert.show();
	*/
    }

    class SyncToServerTask extends AsyncTask<String, String, String> {

        private int operationType;

        @SuppressLint("LongLogTag")
        protected String doInBackground(String... urls) {

            uriInProgress = urls[0];
            Log.e("postUrl : ", uriInProgress);

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(uriInProgress);
            try {

                //operationType = getOperationType(uriInProgress);

                String jsonString = "";

                try {

                    if (operationType == SRVAppConstant.OPTYPE_GETWORKLOGS) {
                        //jsonString = getJsonParams(operationType).toString();
                    }

                    Log.e("jsonString", jsonString);

                } catch (NumberFormatException e) {
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

    private void gotoGPSSwitch() {
        Intent intent = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void gotoSRVAddWorklogScreen() {

        Log.e("test", "" + currentServiceCall.getService_issue());
        startActivity(new Intent(this, SRVAddWorklogScreen.class)
                .putExtra("worklog", currentServiceCall));
    }

    private void gotoSRVWorklogListScreen() {

        startActivity(new Intent(this, SRVWorklogListScreen.class)
                .setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    private void gotoSRVLocateScreen() {

        startActivity(new Intent(this, SRVLocateScreen.class)
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





    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
