package com.isteer.service.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.isteer.service.activity.SRVSyncScreen;
import com.isteer.service.app.App;
import com.isteer.service.config.DSRAppConstant;
import com.isteer.service.model.CustomerData;
import com.isteer.service.model.EventData;

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
import java.util.HashSet;

public class DSRConnectivityReceiver extends BroadcastReceiver {

	public Context mContext;
	
	public static boolean isSyncSuccess = false;
	public static boolean addIsUptodate = false;
	public static boolean updateIsUptodate = false;
	
	public int currentSync = DSRAppConstant.SYNC_TYPE_NOTHING;

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.e("DSRConnectivityReceiver", "onReceive");

		mContext = context;
		
/*		if(DSRApp.dsrUtils.isNetAvailable())
		{
			DSRSyncScreen.isAutoSync = true;
			gotoDSRSyncScreen(context);
			
			syncDataToServer();
		}*/
	}
	
	private void syncDataToServer()
	{
		addIsUptodate = false;
		updateIsUptodate = false;
		
		if(!addIsUptodate)
		{
			isSyncSuccess = true;
			currentSync = DSRAppConstant.SYNC_TYPE_PLANS;
			new DSRSyncToServer().execute(App.appPreference.getBaseUrl2()+DSRAppConstant.METHOD_ADDALL_NEW_EVENTS);
		}
		else if(!updateIsUptodate)
		{
			isSyncSuccess = true;
			currentSync = DSRAppConstant.SYNC_TYPE_PLANS;
			new DSRSyncToServer().execute(App.appPreference.getBaseUrl2()+DSRAppConstant.METHOD_UPDATE_ALL_DATA);
		}	
		else
		{
			Toast.makeText(mContext, "Application Data are upto date", Toast.LENGTH_LONG).show();
		}

	}
	
	class DSRSyncToServer extends AsyncTask<String, String, String> {

		private static final int OPTYPE_UNKNOWN = -1;
		private static final int OPTYPE_ADDALLNEWDATA = 61;
		private static final int OPTYPE_UPDATEALLDATA = 62;

		private int operationType;
		private String uriInProgress;
		
		protected String doInBackground(String... urls) {

			uriInProgress = urls[0];
			Log.e("postUrl : ", uriInProgress);

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(uriInProgress);
			try {

				operationType = getOperationType(uriInProgress);

				String jsonString = "";

				jsonString = /*(new JSONObject().put(DSRAppConstant.KEY_VALUES,
                        getAllEventsArray(operationType))).toString()*/"";

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
			}

			return null;
		}

		@Override
		protected void onPreExecute() {

			Toast.makeText(mContext, "Syncing data...", Toast.LENGTH_SHORT).show();

		}

		protected void onPostExecute(String responseString) {

			Log.e("responseString : ", "" + responseString);

			if (responseString == null) {

				isSyncSuccess = false;

				if(operationType==OPTYPE_ADDALLNEWDATA)
				{
					
					if(!updateIsUptodate)
						new DSRSyncToServer().execute(App.appPreference.getBaseUrl2()+DSRAppConstant.METHOD_UPDATE_ALL_DATA);
					else
					{
						//Toast.makeText(mContext, "Sync failed...", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					//Toast.makeText(mContext, "Sync failed...", Toast.LENGTH_SHORT).show();
				}
			}
			else
			{
				if(operationType==OPTYPE_ADDALLNEWDATA)
					onPostExecuteAddAll(responseString);
				else if(operationType==OPTYPE_UPDATEALLDATA)
					onPostExecuteUpdateAll(responseString);
			}

		}

		protected void onPostExecuteAddAll(String responseString) {

			JSONObject responseJson;
			JSONArray responseArray = null;
			try {
				responseArray = new JSONArray(responseString);
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("JSONException add : ", e.toString());
				isSyncSuccess = false;
				if(!updateIsUptodate)
					new DSRSyncToServer().execute(App.appPreference.getBaseUrl2()+DSRAppConstant.METHOD_UPDATE_ALL_DATA);
				else
				{
					//Toast.makeText(mContext, "Sync failed...", Toast.LENGTH_SHORT).show();
				}
				return;
			}

			for(int i=0; i<responseArray.length(); i++)
			{
				try {
					responseJson = responseArray.getJSONObject(i);
		
					if (responseJson.has(DSRAppConstant.KEY_STATUS)&& responseJson.getString(DSRAppConstant.KEY_STATUS).equalsIgnoreCase(DSRAppConstant.KEY_SUCCESS)) {
						

					}
					else
						isSyncSuccess = false;

				} catch (JSONException e) {
					e.printStackTrace();
					Log.e("JSONException add 2 : ", e.toString());
					isSyncSuccess = false;
				}
			}
			
			if(!updateIsUptodate)
				new DSRSyncToServer().execute(App.appPreference.getBaseUrl2()+DSRAppConstant.METHOD_UPDATE_ALL_DATA);
			else
			{
				if(isSyncSuccess)
				{
					//Toast.makeText(mContext, "Sync plans success...", Toast.LENGTH_SHORT).show();
					refreshDB();
				}
				else
				{
					//Toast.makeText(mContext, "Sync failed...", Toast.LENGTH_SHORT).show();
				}
			}
		}
		
		protected void onPostExecuteUpdateAll(String responseString) {
			
			JSONObject responseJson;
			JSONArray responseArray = null;
			try {
				responseArray = new JSONArray(responseString);
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("JSONException upd : ", e.toString());
				isSyncSuccess = false;
				
				//Toast.makeText(mContext, "Sync failed...", Toast.LENGTH_SHORT).show();
				
				return;
			}

			for(int i=0; i<responseArray.length(); i++)
			{
				try {
					responseJson = responseArray.getJSONObject(i);
				
					if (responseJson.has(DSRAppConstant.KEY_STATUS) && responseJson.getString(DSRAppConstant.KEY_STATUS).equalsIgnoreCase(DSRAppConstant.KEY_SUCCESS))
					{
					}
					else
						isSyncSuccess = false;
				
				} catch (JSONException e) {
					e.printStackTrace();
					Log.e("JSONException upd 2 : ", e.toString());
					isSyncSuccess = false;
				}
			}
			
			if(isSyncSuccess == false)
			{
				//Toast.makeText(mContext, "Sync failed...", Toast.LENGTH_SHORT).show();
			}
			else
			{
				//Toast.makeText(mContext, "Sync plans success...", Toast.LENGTH_SHORT).show();
				refreshDB();
			}
		}
		
		private int getOperationType(String tUri) {

			int operation = OPTYPE_UNKNOWN;

			if (tUri.contains(DSRAppConstant.METHOD_ADDALL_NEW_EVENTS))
				operation = OPTYPE_ADDALLNEWDATA;
			else if (tUri.contains(DSRAppConstant.METHOD_UPDATE_ALL_DATA))
				operation = OPTYPE_UPDATEALLDATA;
			else
				operation = OPTYPE_UNKNOWN;

			return operation;
		}
		
		private void alertUserP(Context context, String title, String msg, String btn) {
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
		
		/*private JSONArray getAllEventsArray(int optype) throws JSONException
		{
			
			JSONArray tJsonArray = new JSONArray();
			String keyName;
			
			Cursor mCursor;
			
			if(optype==OPTYPE_UPDATEALLDATA)
			{
				mCursor = null;
				keyName = DSRTableCreate.COLOUMNS_EVENT_MASTER.event_key.name();
			}else
			{
				mCursor = null;
				keyName = DSRAppConstant.KEY_DUMMY_KEY;
			}
			
			if (mCursor == null)
				return tJsonArray;
			
			int columnIndexKey					= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_key.name());
			int columnIndexUserKey				= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_user_key.name());
			int columnIndexType 				= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_type.name());
			int columnIndexTitle				= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_title.name());
			int columnIndexFromDatetime 		= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.from_date_time.name());
			int columnIndexToDateTime 			= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.to_date_time.name());
			int columnIndexEventDesc 			= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_description.name());
			int columnIndexEventStatus 			= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.status.name());
			int columnIndexCMKey 				= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.cmkey.name());
			int columnIndexLocation 			= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.area.name());
			int columnIndexLatitude 			= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.latitude.name());
			int columnIndexLongitude 			= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.longitude.name());
			int columnIndexAltitude 			= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.altitude.name());
			int columnIndexVisitUpdateTime 		= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.visit_update_time.name());
			int columnIndexActionDesc 			= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.action_response.name());
			int columnIndexPlan 				= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.plan.name());
			int columnIndexObjective 			= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.objective.name());
			int columnIndexStrategy 			= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.strategy.name());
			int columnIndexCustomerName 		= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.customer_name.name());
			int columnIndexPurpose 				= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.preparation.name());
			int columnIndexvisitedLatitude 		= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_visited_latitude.name());
			int columnIndexvisitedLongitude 	= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_visited_longitude.name());
			int columnIndexvisitedAltitude 		= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_visited_altitude.name());
			int columnIndexCompletedOn 			= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.completed_on.name());
			int columnIndexCancelledOn 			= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.cancelled_on.name());
			int columnIndexAction 				= mCursor.getColumnIndex(DSRTableCreate.COLOUMNS_EVENT_MASTER.action.name());

			do {
				
				JSONObject json = new JSONObject();

				json.put(keyName, mCursor.getString(columnIndexKey));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_user_key.name(), mCursor.getString(columnIndexUserKey));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_type.name(), mCursor.getString(columnIndexType));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_title.name(), mCursor.getString(columnIndexTitle));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.from_date_time.name(), mCursor.getString(columnIndexFromDatetime));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.to_date_time.name(), mCursor.getString(columnIndexToDateTime));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_description.name(), mCursor.getString(columnIndexEventDesc));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.status.name(), mCursor.getString(columnIndexEventStatus));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.cmkey.name(), mCursor.getString(columnIndexCMKey));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.area.name(), mCursor.getString(columnIndexLocation));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.latitude.name(), mCursor.getString(columnIndexLatitude));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.longitude.name(), mCursor.getString(columnIndexLongitude));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.altitude.name(), mCursor.getString(columnIndexAltitude));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.visit_update_time.name(), mCursor.getString(columnIndexVisitUpdateTime));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.action_response.name(), mCursor.getString(columnIndexActionDesc));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.plan.name(), mCursor.getString(columnIndexPlan));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.objective.name(), mCursor.getString(columnIndexObjective));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.strategy.name(), mCursor.getString(columnIndexStrategy));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.customer_name.name(), mCursor.getString(columnIndexCustomerName));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.preparation.name(), mCursor.getString(columnIndexPurpose));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_visited_latitude.name(), mCursor.getString(columnIndexvisitedLatitude));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_visited_longitude.name(), mCursor.getString(columnIndexvisitedLongitude));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_visited_altitude.name(), mCursor.getString(columnIndexvisitedAltitude));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.completed_on.name(), mCursor.getString(columnIndexCompletedOn));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.cancelled_on.name(), mCursor.getString(columnIndexCancelledOn));
				json.put(DSRTableCreate.COLOUMNS_EVENT_MASTER.action.name(), mCursor.getString(columnIndexAction));

				json.put("event_repeat_config", "ONE_TIME_EVENT");

				tJsonArray.put(json);
				
			} while (mCursor.moveToNext());
			
			mCursor.close();
			mCursor = null;
			
			return tJsonArray;
		}*/
		
	}
	
	private void refreshDB() 
	{
		clearDB();
		fillDB();
	}
	
	private void clearDB() {

		
	}
	
	private void fillDB() {
		currentSync = DSRAppConstant.SYNC_TYPE_REFRESH;
		new PostRequestManager().execute(App.appPreference.getBaseUrl2() +  DSRAppConstant.METHOD_GETALLEVENTS);
	}

	class PostRequestManager extends AsyncTask<String, String, String> {

		private int opType = DSRAppConstant.OPTYPE_UNKNOWN;
		private String uriInProgress;
		
		protected String doInBackground(String... urls) {

			uriInProgress = urls[0];
			
			Log.e("uriInProgress : ", uriInProgress);
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(uriInProgress);
			try {
				
				opType = getOperationType(uriInProgress);
				
				JSONObject json = getJsonParams(opType);
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
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("IOException : ", e.toString());
				e.printStackTrace();
			} catch (JSONException e) {
				Log.e("JSONException : ", e.toString());
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPreExecute() {

		}

		protected void onPostExecute(String responseString) {
			Log.e("responseString : ", "" + responseString);

			if (responseString == null) {

				//Toast.makeText(mContext, "Sync failed...", Toast.LENGTH_SHORT).show();
				return;
			}
			else if(opType==DSRAppConstant.OPTYPE_GETALLEVENTS){

            }
				//onPostGetAllEvents(responseString);
			else if(opType==DSRAppConstant.OPTYPE_GETCUSTOMERS){

            }
			//	onPostCustomerDetails(responseString);
			else if(opType==DSRAppConstant.OPTYPE_GETCUSTCATLIST)
				onPostCustCatList(responseString);
		}
	}
	
	private int getOperationType(String uri) {

		int optype = DSRAppConstant.OPTYPE_UNKNOWN;
		
		if(uri.contains(DSRAppConstant.METHOD_GETALLEVENTS))
			optype = DSRAppConstant.OPTYPE_GETALLEVENTS;
		else if(uri.contains(DSRAppConstant.METHOD_GET_CUSTOMER_DETAILS))
			optype = DSRAppConstant.OPTYPE_GETCUSTOMERS;
		else if(uri.contains(DSRAppConstant.METHOD_GET_SPANCOPLIST))
			optype = DSRAppConstant.OPTYPE_GETSPANCOPLIST;
		else if(uri.contains(DSRAppConstant.METHOD_GET_CUST_CAT_LIST))
			optype = DSRAppConstant.OPTYPE_GETCUSTCATLIST;
		else if(uri.contains(DSRAppConstant.METHOD_GET_AERP_MASTER_ITEM))
			optype = DSRAppConstant.OPTYPE_GETMASTERITEM;
		else if(uri.contains(DSRAppConstant.METHOD_GET_AERP_ITEM_MSTR))
			optype = DSRAppConstant.OPTYPE_GETITEMMSTR;
		else if(uri.contains(DSRAppConstant.METHOD_GET_AERP_PROSPECT_MSTR))
			optype = DSRAppConstant.OPTYPE_GETPROSPECTMSTR;
		
		return optype;
		
	}
	
	private JSONObject getJsonParams(int opType) throws JSONException {
		
		JSONObject json = new JSONObject();
		
		json.put(DSRAppConstant.KEY_USER_KEY, App.appPreference.getUserId());
		
		if(opType==DSRAppConstant.OPTYPE_GETALLEVENTS || opType==DSRAppConstant.OPTYPE_GETCUSTCATLIST)
		{
		}
		else if(opType==DSRAppConstant.OPTYPE_GETCUSTOMERS)
		{
			json.put(DSRAppConstant.KEY_PUNIT_KEY, App.appPreference.getUnitKey());
		}
		else if(opType==DSRAppConstant.OPTYPE_GETSPANCOPLIST)
		{
			json.put(DSRAppConstant.KEY_CM_KEY, "");
		}
			
		return json;
	}

/*
	protected void onPostGetAllEvents(String responseString) {

		String result = "false";
		String uid = null;
		String pid = null;
		String name = null;
		String message = "Operation failed in a timely manner. Please try again";

		JSONObject outerJson;

		try {
			outerJson = new JSONObject(responseString);

			if (outerJson.has(DSRAppConstant.KEY_STATUS) && outerJson.getString(DSRAppConstant.KEY_STATUS).equalsIgnoreCase(DSRAppConstant.KEY_SUCCESS))
			{
				JSONArray dataArray = outerJson.getJSONArray(DSRAppConstant.KEY_DATA);

				ArrayList<EventData> eventList = new ArrayList<EventData>();

				for(int i=0; i<dataArray.length(); i++)
				{
					JSONObject singleEvent = dataArray.getJSONObject(i);
					EventData eventData = new EventData();

					if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_key.name()))
					{
						eventData.setEvent_key(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_key.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_user_key.name()))
							eventData.setEvent_user_key(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_user_key.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_type.name()))
							eventData.setEvent_type(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_type.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_title.name()))
							eventData.setEvent_title(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_title.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.area.name()))
							eventData.setArea(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.area.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.from_date_time.name()))
						{
							String timeString  = singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.from_date_time.name());
							eventData.setFrom_date_time(timeString);
							eventData.setEvent_month(timeString.substring(0, timeString.indexOf('-', 5)));
							eventData.setEvent_date(timeString.substring(0, timeString.indexOf(" ")));
							eventData.setEvent_date_absolute(timeString.substring(8, timeString.indexOf(" ")));
						}

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.to_date_time.name()))
							eventData.setTo_date_time(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.to_date_time.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_description.name()))
							eventData.setEvent_description(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_description.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.status.name()))
							eventData.setStatus(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.status.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.cmkey.name()))
							eventData.setCmkey(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.cmkey.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.latitude.name()))
							eventData.setLatitude(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.latitude.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.longitude.name()))
							eventData.setLongitude(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.longitude.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.altitude.name()))
							eventData.setAltitude(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.altitude.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.visit_update_time.name()))
							eventData.setVisit_update_time(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.visit_update_time.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.action_response.name()))
							eventData.setAction_response(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.action_response.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.action.name()))
							eventData.setAction(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.action.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.plan.name()))
							eventData.setPlan(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.plan.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.objective.name()))
							eventData.setObjective(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.objective.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.strategy.name()))
							eventData.setStrategy(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.strategy.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.customer_name.name()))
							eventData.setCustomer_name(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.customer_name.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.preparation.name()))
							eventData.setPreparation(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.preparation.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_visited_latitude.name()))
							eventData.setEvent_visited_latitude(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_visited_latitude.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_visited_longitude.name()))
							eventData.setEvent_visited_longitude(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_visited_longitude.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_visited_altitude.name()))
							eventData.setEvent_visited_altitude(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.event_visited_altitude.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.completed_on.name()))
							eventData.setCompleted_on(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.completed_on.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.cancelled_on.name()))
							eventData.setCancelled_on(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.cancelled_on.name()));

						if (singleEvent.has(DSRTableCreate.COLOUMNS_EVENT_MASTER.entered_on.name()))
							eventData.setEntered_on(singleEvent.getString(DSRTableCreate.COLOUMNS_EVENT_MASTER.entered_on.name()));

						eventData.setIs_synced_to_server("1");

						eventList.add(eventData);

					}
				}

				currentSync = DSRAppConstant.SYNC_TYPE_REFRESH;
				new PostRequestManager().execute(App.appPreference.getBaseUrl2() + DSRAppConstant.METHOD_GET_CUSTOMER_DETAILS);
				return;
			}
			else
			{
				if (outerJson.has(DSRAppConstant.KEY_MSG))
					message = outerJson.getString(DSRAppConstant.KEY_MSG);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("Exception : ", e.toString());
		}

		//Toast.makeText(mContext, "Sync failed...", Toast.LENGTH_SHORT).show();

	}
*/

/*
	protected void onPostCustomerDetails(String responseString) {

		JSONObject responseOuterJson;
		JSONArray dataArray;

		String message = "Operation failed in a timely manner. Please try again";

		try {
			responseOuterJson = new JSONObject(responseString);

			if (responseOuterJson.getString(DSRAppConstant.KEY_STATUS).equalsIgnoreCase(DSRAppConstant.KEY_SUCCESS) && responseOuterJson.has(DSRAppConstant.KEY_DATA))
			{
				dataArray = responseOuterJson.getJSONArray(DSRAppConstant.KEY_DATA);
				ArrayList<CustomerData> custList = new ArrayList<CustomerData>();

				for(int i=0; i<dataArray.length(); i++)
				{
					JSONObject singleEntry = dataArray.getJSONObject(i);
					CustomerData custData = new CustomerData();

					if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.cmkey.name()))
					{
						custData.setCmkey(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.cmkey.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.userkey.name()))
							custData.setUserkey(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.userkey.name()));

						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.cmp_phone1.name()))
							custData.setCmp_phone1(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.cmp_phone1.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.cmp_phone2.name()))
							custData.setCmp_phone2(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.cmp_phone2.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.cmp_email.name()))
							custData.setCmp_email(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.cmp_email.name()));

						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.company_name.name()))
							custData.setCompany_name(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.company_name.name()));

						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.address1.name()))
							custData.setAddress1(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.address1.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.address2.name()))
							custData.setAddress2(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.address2.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.address3.name()))
							custData.setAddress3(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.address3.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.area.name()))
							custData.setArea(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.area.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.city.name()))
							custData.setCity(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.city.name()));

						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.state.name()))
							custData.setState(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.state.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.country.name()))
							custData.setCountry(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.country.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.pincode.name()))
							custData.setPincode(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.pincode.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.email.name()))
							custData.setEmail(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.email.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.phone1.name()))
							custData.setPhone1(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.phone1.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.phone2.name()))
							custData.setPhone2(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.phone2.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.website.name()))
							custData.setWebsite(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.website.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.industry.name()))
							custData.setIndustry(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.industry.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.category1.name()))
							custData.setCategory1(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.category1.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.category2.name()))
							custData.setCategory2(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.category2.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.category3.name()))
							custData.setCategory3(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.category3.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.display_code.name()))
							custData.setDisplay_code(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.display_code.name()));
						
						if (singleEntry.has(DSRTableCreate.COLOUMNS_CONTACT_MASTER.area_name.name()))
							custData.setArea_name(singleEntry.getString(DSRTableCreate.COLOUMNS_CONTACT_MASTER.area_name.name()));
						
						if (singleEntry
								.has(SRVTableCreate.COLOUMNS_CONTACT_MASTER.first_name
										.name()))
							custData.setFirst_name(singleEntry
									.getString(SRVTableCreate.COLOUMNS_CONTACT_MASTER.first_name
											.name()));
					}				
					custList.add(custData);
				}
				
				App.appPreference.setIsDbFilled(App.appPreference.isDbFilled() && App.localDBS.insertCustomer(mContext, custList));
				currentSync = DSRAppConstant.SYNC_TYPE_REFRESH;
				new PostRequestManager().execute(App.appPreference.getBaseUrl2() + DSRAppConstant.METHOD_GET_SPANCOPLIST);
				return;
			}
			else
			{
				if (responseOuterJson.has(DSRAppConstant.KEY_MSG)) 
					message = responseOuterJson.getString(DSRAppConstant.KEY_MSG);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("JSONException : ", e.toString());
		}
		
		//Toast.makeText(mContext, "Sync failed...", Toast.LENGTH_SHORT).show();

	}
*/

	protected void onPostCustCatList(String responseString) {
		JSONObject responseOuterJson;
		JSONArray dataArray;
		HashSet<String> eventList = new HashSet<String>();
		String message = "Operation failed in a timely manner. Please try again";
		try {
			responseOuterJson = new JSONObject(responseString);
			if (responseOuterJson.getString(DSRAppConstant.KEY_STATUS).equalsIgnoreCase(DSRAppConstant.KEY_SUCCESS) && responseOuterJson.has(DSRAppConstant.KEY_DATA)){
				dataArray = responseOuterJson.getJSONArray("data");
				for(int i=0; i<dataArray.length(); i++){
					JSONObject singleEntry = dataArray.getJSONObject(i);
					if (singleEntry.has(DSRAppConstant.KEY_COMPANY_NAME)){
						eventList.add(singleEntry.getString(DSRAppConstant.KEY_COMPANY_NAME));
					}					
				}
				App.appPreference.setTCustomerList(eventList);

				if(isSyncSuccess)
				{
					
					//Toast.makeText(mContext, "Sync failed...", Toast.LENGTH_SHORT).show();

				}
				isSyncSuccess = false;
				return;
			}else{				
				if (responseOuterJson.has(DSRAppConstant.KEY_MSG)) 
					message = responseOuterJson.getString(DSRAppConstant.KEY_MSG);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("JSONException : ", e.toString());
		}
		
		//Toast.makeText(mContext, "Sync failed...", Toast.LENGTH_SHORT).show();

	}
	
	private void gotoDSRSyncScreen(Context context) {

		context.startActivity(new Intent(context, SRVSyncScreen.class)
				.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	
	}

}