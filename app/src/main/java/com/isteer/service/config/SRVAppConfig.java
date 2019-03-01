package com.isteer.service.config;

import android.app.AlarmManager;

public class SRVAppConfig {

	public static final int SPLASH_TIMEDELAY = 3*1000;
	public static final int MAX_LOGIN_ATTEMPT = 5;
	public static final long AUTO_LOGOUT_THRESHOLD = AlarmManager.INTERVAL_DAY;
	public static final long LOGIN_CHECKUP_TIME = 2* AlarmManager.INTERVAL_HOUR;

	public static final String APP_TAG = "iSteer Service" ;
	public static final String PACKAGE_IDENTIFIER = "com.amshuhu.techpump" ;

	public static final String DB_NAME = "service_db_room.db" ;
	public static final String DB_PATH = "/data/data/" + PACKAGE_IDENTIFIER + "/databases/";
		
	public static final String PROJECT_NUMBER = "173770840598";
	public static final String API_KEY = "AIzaSyAlORmwfD-gJ_owpGuv76RPIbnKyUbDXBY";
	
	public static final String DEFAULT_PASSSCODE = "2015";
	public static final long ALARM_RINGER_INTERVAL = 10*60*1000;
	public static final long DEFAULT_ATTENDENCE_INTERVAL = 10*60*1000;
	public static int LOC_TIME_INTERVAL = 3*60*1000;
    public static int LOC_TIME_INTERVAL_FASTEST = 1*60*1000;
    public static int LOC_DISPLACEMENT_INTERVAL = 5;
    
}
