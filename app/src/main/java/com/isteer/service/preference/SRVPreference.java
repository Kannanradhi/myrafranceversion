package com.isteer.service.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.isteer.service.config.SRVAppConfig;

import java.util.HashSet;
import java.util.Set;

public class SRVPreference {

	private SharedPreferences mSharedPreferences;

	private static final String APP_VERSION = "app_version";
	private static final String GCM_REG_ID  = "gcm_reg_id";

	private static final String COMPANY_CODE = "company_code";
	private static final String LAST_LOGIN = "last_login";
	private static final String USER_NAME = "username";
	private static final String USER_PASS = "password";

	private static final String IS_REGISTERED = "is_registered";
	private static final String BASE_URL1 = "site1";
	private static final String BASE_URL2 = "site2";
	private static final String BASE_URL3 = "site3";

	private static final String IS_LOGGED_IN = "is_logged_in";
	private static final String USER_USER_ID = "user_id";
	private static final String USER_UNIT_KEY = "unit_key";
	private static final String USER_SEKEY = "sekey";
	private static final String USER_TOKEN = "token";

	private static final String IS_LOGIN_FAILED = "is_login_failed";
	private static final String LOGIN_FAIL_COUNT = "login_fail_count";
	private static final String IS_TOKEN_VALID = "is_token_valid";
	private static final String LAST_VALIDATED_TIME = "last_validated_time";
	private static final String BRANCH_CODE = "branch_code";
	private static final String UPLOAD_PATH = "upload_path";

	private static final String IS_DB_FILLED = "is_db_filled";
	private static final String IS_FILLED_SERVICE_CALLS = "is_filled_service_calls";
	private static final String IS_FILLED_WORK_LOGS = "is_filled_work_logs";
	private static final String IS_FILLED_UPLOAD_FILES = "is_filled_upload_files";
	private static final String IS_FILLED_CUSTOMERS = "is_filled_customers";
	private static final String IS_FILLED_CUSTOMERS_INDIVIDUAL = "is_filled_customers_individual";
	private static final String IS_FILLED_MASTER_ITEM = "is_filled_master_item";
	private static final String IS_UPLOADED_NEW_WORKLOG = "is_uploaded_new_worklog";
	private static final String DB_FILLED_TIME = "db_filled_time";

	private static final String LAST_INDEX_FILLED = "last_index";

	public static final String LOGIN_CHECK_TIME = "LoginCheckTime";
	public static final String MAX_LOGIN_TIME = "MaxLoginTime";
	public static final String MAX_LOGIN_ATTEMPT = "MaxLoginAttempt";
	public static final String ATT_TIME = "AttendanceTrackingTime";
	public static final String ALARM_TIME = "AlarmTime";

	public static final String BR_CODES = "br_codes";
	public static final String BR_NAMES = "br_names";

	private static final String NEW_ENTRY_COUNT = "new_entry_count";
	private static final String NEW_ENTRY_COUNT_SPANCOP = "new_entry_count_spancop";
	private static final String NEW_ENTRY_COUNT_WORKLOG= "new_entry_count_worklog";

	private static final String TEMP_CUSTOMER_LIST = "temp_customer_list";

	private static final String DayStarted = "daystarted";
	private static final String PASS_CODE = "passcode";
	public static final String Start_Date_key="startdatekey";

	public SRVPreference(Context context) {
		mSharedPreferences = context.getSharedPreferences("isteer_service_techpump_preference", Context.MODE_PRIVATE);
	}

	public String getString(String pref_name){

		return mSharedPreferences.getString(pref_name, null);
	}

	//B2C

	public void setCompanyCode(String company_code) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(COMPANY_CODE, company_code);
		edit.commit();
	}

	public String getCompanyCode() {
		return mSharedPreferences.getString(COMPANY_CODE, "");
	}

	public void setGCMRegID(String gcm_reg_id) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(GCM_REG_ID, gcm_reg_id);
		edit.commit();
	}

	public String getGCMRegID() {
		return mSharedPreferences.getString(GCM_REG_ID, "");
	}

	public void setAppVersion(int appVersion) {
		Editor edit = mSharedPreferences.edit();
		edit.putInt(APP_VERSION, appVersion);
		edit.commit();
	}

	public int getAppVersion() {
		return mSharedPreferences.getInt(APP_VERSION, -1);
	}

	public boolean isRegistered() {
		return mSharedPreferences.getBoolean(IS_REGISTERED, false);
	}

	public void setIsRegistered(boolean isRegistered) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(IS_REGISTERED, isRegistered);
		edit.commit();
	}

	public boolean isUploadedNewWorklog() {
		return mSharedPreferences.getBoolean(IS_UPLOADED_NEW_WORKLOG, false);
	}

	public void setIsUploadedNewWorklog(boolean isRegistered) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(IS_UPLOADED_NEW_WORKLOG, isRegistered);
		edit.commit();
	}

	public boolean isLoggedIn() {
		return mSharedPreferences.getBoolean(IS_LOGGED_IN, false);
	}

	public void setIsLoggedIn(boolean isLoggedIn) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(IS_LOGGED_IN, isLoggedIn);
		edit.commit();
	}

	public void setBaseUrl(String url) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(BASE_URL1, url);
		edit.commit();
	}

	public String getBaseUrl() {
		return mSharedPreferences.getString(BASE_URL1, null);
	}

	public void setBaseUrl2(String url) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(BASE_URL2, url);
		edit.commit();
	}

	public String getBaseUrl2() {
		return mSharedPreferences.getString(BASE_URL2, null);
	}

	public void setBaseUrl3(String url) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(BASE_URL3, url);
		edit.commit();
	}

	public String getBaseUrl3() {
		return mSharedPreferences.getString(BASE_URL3, null);
	}

	public void setUserId(String user_id) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(USER_USER_ID, user_id);
		edit.commit();
	}

	public String getUserId() {
		return mSharedPreferences.getString(USER_USER_ID, null);
	}

	public void setUnitKey(String unit_key) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(USER_UNIT_KEY, unit_key);
		edit.commit();
	}

	public String getUnitKey() {
		return mSharedPreferences.getString(USER_UNIT_KEY, null);
	}

	public void setSekey(String sekey) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(USER_SEKEY, sekey);
		edit.commit();
	}

	public String getSekey() {
		return mSharedPreferences.getString(USER_SEKEY, null);
	}

	public void setToken(String token) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(USER_TOKEN, token);
		edit.commit();
	}

	public String getToken() {
		return mSharedPreferences.getString(USER_TOKEN, null);
	}

	public boolean isDbFilled() {
		return mSharedPreferences.getBoolean(IS_DB_FILLED, false);
	}

	public void setIsDbFilled(boolean dbFilled) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(IS_DB_FILLED, dbFilled);
		edit.commit();
	}

	public boolean isFilledCustomers() {
		return mSharedPreferences.getBoolean(IS_FILLED_CUSTOMERS, false);
	}

	public void setIsFilledCustomers(boolean isFilled) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(IS_FILLED_CUSTOMERS, isFilled);
		edit.commit();
	}

	public boolean isFilledCustomerIndividual() {
		return mSharedPreferences.getBoolean(IS_FILLED_CUSTOMERS_INDIVIDUAL, false);
	}

	public void setIsFilledCustomerIndividual(boolean isFilled) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(IS_FILLED_CUSTOMERS_INDIVIDUAL, isFilled);
		edit.commit();
	}

	public boolean isFilledServiceCalls() {
		return mSharedPreferences.getBoolean(IS_FILLED_SERVICE_CALLS, false);
	}

	public void setIsFilledServiceCalls(boolean isFilled) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(IS_FILLED_SERVICE_CALLS, isFilled);
		edit.commit();
	}

	public boolean isFilledMasterItem() {
		return mSharedPreferences.getBoolean(IS_FILLED_MASTER_ITEM, false);
	}

	public void setIsFilledMasterItem(boolean isFilled) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(IS_FILLED_MASTER_ITEM, isFilled);
		edit.commit();
	}

	public boolean isFilledWorklogs() {
		return mSharedPreferences.getBoolean(IS_FILLED_WORK_LOGS, false);
	}

	public void setIsFilledWorklogs(boolean isFilled) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(IS_FILLED_WORK_LOGS, isFilled);
		edit.commit();
	}

	public boolean isFilledUploadFiles() {
		return mSharedPreferences.getBoolean(IS_FILLED_UPLOAD_FILES, false);
	}

	public void setIsFilledUploadFiles(boolean isFilled) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(IS_FILLED_UPLOAD_FILES, isFilled);
		edit.commit();
	}

	public String getDBFilledTime() {
		return mSharedPreferences.getString(DB_FILLED_TIME, "");
	}

	public void setDBFilledTime(String dbFilledTime) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(DB_FILLED_TIME, dbFilledTime);
		edit.commit();
	}

	public void setLastLogin(long newLastLogin) {
		Editor edit = mSharedPreferences.edit();
		edit.putLong(LAST_LOGIN, newLastLogin);
		edit.commit();
	}

	public long getLastLogin() {
		return mSharedPreferences.getLong(LAST_LOGIN, 0l);
	}

	public String getUserName() {
		return mSharedPreferences.getString(USER_NAME, null);
	}

	public void setUserName(String username) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(USER_NAME, username);
		edit.commit();
	}

	public String getUserPass() {
		return mSharedPreferences.getString(USER_PASS, null);
	}

	public void setUserPass(String userpass) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(USER_PASS, userpass);
		edit.commit();
	}

	public int getLastIndexFilled() {
		return mSharedPreferences.getInt(LAST_INDEX_FILLED, 0);
	}

	public void setLastIndexFilled(int lastIndexFilled) {
		Editor edit = mSharedPreferences.edit();
		edit.putInt(LAST_INDEX_FILLED, lastIndexFilled);
		edit.commit();
	}

	public boolean isLoginFailed() {
		return mSharedPreferences.getBoolean(IS_LOGIN_FAILED, false);
	}

	public void setIsLoginFailed(boolean isLoginFailed) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(IS_LOGIN_FAILED, isLoginFailed);
		edit.commit();
	}

	public int getLoginFailCount() {
		return mSharedPreferences.getInt(LOGIN_FAIL_COUNT, 0);
	}

	public void setLoginFailCount(int loginFailCount) {
		Editor edit = mSharedPreferences.edit();
		edit.putInt(LOGIN_FAIL_COUNT, loginFailCount);
		edit.commit();
	}

	public boolean isTokenValid() {
		return mSharedPreferences.getBoolean(IS_TOKEN_VALID, false);
	}

	public void setIsTokenValid(boolean isTokenValid) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(IS_TOKEN_VALID, isTokenValid);
		edit.commit();
	}

	public void setLastValidatedTime(long lastValidatedTime) {
		Editor edit = mSharedPreferences.edit();
		edit.putLong(LAST_VALIDATED_TIME, lastValidatedTime);
		edit.commit();
	}

	public long getLastValidatedTime() {
		return mSharedPreferences.getLong(LAST_VALIDATED_TIME, 0l);
	}

	public String getBranchCode() {
		return mSharedPreferences.getString(BRANCH_CODE, "");
	}

	public void setBranchCode(String brCode) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(BRANCH_CODE, brCode);
		edit.commit();
	}

	public void setLoginCheckTime(long loginCheckTime) {
		Editor edit = mSharedPreferences.edit();
		edit.putLong(LOGIN_CHECK_TIME, loginCheckTime);
		edit.commit();
	}

	public long getLoginCheckTime() {
		return mSharedPreferences.getLong(LOGIN_CHECK_TIME, 0l);
	}

	public void setMaxLoginTime(long maxLoginTime) {
		Editor edit = mSharedPreferences.edit();
		edit.putLong(MAX_LOGIN_TIME, maxLoginTime);
		edit.commit();
	}

	public long getMaxLoginTime() {
		return mSharedPreferences.getLong(MAX_LOGIN_TIME, 0l);
	}

	public void setAttTrackTime(long attTrackTime) {
		Editor edit = mSharedPreferences.edit();
		edit.putLong(ATT_TIME, attTrackTime);
		edit.commit();
	}

	public long getAttTrackTime() {
		return mSharedPreferences.getLong(ATT_TIME, 0l);
	}

	public void setMaxLoginAtt(int maxLoginAtt) {
		Editor edit = mSharedPreferences.edit();
		edit.putInt(MAX_LOGIN_ATTEMPT, maxLoginAtt);
		edit.commit();
	}

	public int getMaxLoginAtt() {
		return mSharedPreferences.getInt(MAX_LOGIN_ATTEMPT, SRVAppConfig.MAX_LOGIN_ATTEMPT);
	}

	public void setAlarmTime(long alarmTime) {
		Editor edit = mSharedPreferences.edit();
		edit.putLong(ALARM_TIME, alarmTime);
		edit.commit();
	}

	public long getAlarmTime() {
		return mSharedPreferences.getLong(ALARM_TIME, 0l);
	}

	public void setBRCodes(Set<String> brCodes) {
		Editor edit = mSharedPreferences.edit();
		edit.putStringSet(BR_CODES, brCodes);
		edit.commit();
	}

	public Set<String> getBRCodes() {
		return mSharedPreferences.getStringSet(BR_CODES, new HashSet<String>());
	}

	public void setBRNames(Set<String> brNames) {
		Editor edit = mSharedPreferences.edit();
		edit.putStringSet(BR_NAMES, brNames);
		edit.commit();
	}

	public Set<String> getBRNames() {
		return mSharedPreferences.getStringSet(BR_NAMES, new HashSet<String>());
	}

	public void setNewEntryCount(int newEntryCount) {
		Editor edit = mSharedPreferences.edit();
		edit.putInt(NEW_ENTRY_COUNT, newEntryCount);
		edit.commit();
	}

	public int getNewEntryCount() {
		return mSharedPreferences.getInt(NEW_ENTRY_COUNT, -1);
	}

	public void setNewEntryCountWorklog(int newEntryCount) {
		Editor edit = mSharedPreferences.edit();
		edit.putInt(NEW_ENTRY_COUNT_WORKLOG, newEntryCount);
		edit.commit();
	}

	public int getNewEntryCountWorklog() {
		return mSharedPreferences.getInt(NEW_ENTRY_COUNT_WORKLOG, -1);
	}

	public void setNewEntryCountSpancop(int newEntryCount) {
		Editor edit = mSharedPreferences.edit();
		edit.putInt(NEW_ENTRY_COUNT_SPANCOP, newEntryCount);
		edit.commit();
	}

	public int getNewEntryCountSpancop() {
		return mSharedPreferences.getInt(NEW_ENTRY_COUNT_SPANCOP, -1);
	}

	public void setTCustomerList(Set<String> tCList) {
		Editor edit = mSharedPreferences.edit();
		edit.putStringSet(TEMP_CUSTOMER_LIST, tCList);
		edit.commit();
	}

	public Set<String> getTCustomerList() {
		return mSharedPreferences.getStringSet(TEMP_CUSTOMER_LIST, new HashSet<String>());
	}


public boolean isDayStarted() {
		return mSharedPreferences.getBoolean(DayStarted, false);
	}

	public void setIsDayStarted(boolean isDayStarted) {
		Editor edit = mSharedPreferences.edit();
		edit.putBoolean(DayStarted, isDayStarted);
		edit.commit();
	}

	public void setPassCode(String passcode) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(PASS_CODE, passcode);
		edit.commit();
	}

	public String getPassCode() {
		return mSharedPreferences.getString(PASS_CODE, null);
	}

	public void setUploadPath(String passcode) {
		Editor edit = mSharedPreferences.edit();
		edit.putString(UPLOAD_PATH, passcode);
		edit.commit();
	}

	public String getUploadPath() {
		return mSharedPreferences.getString(UPLOAD_PATH, null);
	}

	public void setStartDateKey(long key) {
		Editor edit = mSharedPreferences.edit();
		edit.putLong(Start_Date_key, key);
		edit.commit();
	}

	public long getStartDateKey() {
		return mSharedPreferences.getLong(Start_Date_key, 1l);
	}


}
