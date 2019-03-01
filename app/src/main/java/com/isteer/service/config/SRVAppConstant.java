package com.isteer.service.config;

import android.util.Log;

import com.isteer.service.app.App;

public class SRVAppConstant {

    public static final String[] SERVICE_TYPE = {

            "Visit", "Call", "Remote Service"
    };

    public static final String[] SERVICE_LOG_STATUS = {

            "Office Closed", "Completed", "Incompleted", "Partially Completed", "Re Opened"
    };

    public static final int SERVICE_STATUS_ENTERED = 1;
    public static final int SERVICE_STATUS_COMPLETED = 2;
    public static final int SERVICE_STATUS_CANCELLED = 3;
    public static final int SERVICE_STATUS_PENDING = 4;

    public static final int WORKLOG_STATUS_OFFICE_CLOSED = 1;
    public static final int WORKLOG_STATUS_COMPLETED = 2;
    public static final int WORKLOG_STATUS_INCOMPLETED = 3;
    public static final int WORKLOG_STATUS_PARTIALLY_COMPLETED = 4;
    public static final int WORKLOG_STATUS_RE_OPENED = 5;

    public static final String MAINFOLDER = "iSteer Service";
    public static final String SUBFOLDER_TEMP = "/Temp";

    public static final int OPTYPE_UNKNOWN = -1;
    public static final int OPTYPE_GET_PRODUCTS = 50;
    public static final int OPTYPE_GET_CUST_DET = 51;
    public static final int OPTYPE_GET_CUST_LOC = 52;
    public static final int OPTYPE_GETMYSERVICECALLS = 53;
    public static final int OPTYPE_GETWORKLOGS = 54;
    public static final int OPTYPE_ADDWORKLOGS = 55;
    public static final int OPTYPE_UPLOAD_IMAGES = 56;
    public static final int OPTYPE_GET_MASTER_ITEMS = 57;
    public static final int OPTYPE_GETIMAGEPATHS = 58;
    public static final int OPTYPE_UPDATECALLSTATUS = 59;

    public static final int SYNC_STATUS_RED = 151;
    public static final int SYNC_STATUS_YELLOW = 152;
    public static final int SYNC_STATUS_GREEN = 153;

    public static final int SYNC_TYPE_EVENT = 201;
    public static final int SYNC_TYPE_CONTACT = 202;
    public static final int SYNC_TYPE_SERVICE = 203;

    public static final String datetimeFormatImage = "yyyy-MM-dd_kk:mm:ss";
    public static final String datetimeFormat = "yyyy-MM-dd kk:mm:ss";
    public static final String dateFormat = "yyyy-MM-dd";
    public static final String dateFormat2 = "yyyyMMdd";
    public static final String dateFormat3 = "dd-MM-yyyy";

    public static final String KEY_CUST_ID = "cus_id";
    public static final String KEY_UNIT_KEY = "unit_key";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_TOKEN = "Token";
    public static final String KEY_FILE_PATHS = "file_paths";
    public static final String UPLOAD_LOCATION_PATH = "upload_location_path";

    public static final String KEY_SE_KEY = "se_key";
    public static final String KEY_CUST_KEY = "cus_key";
    public static final String KEY_CUST_KEYS = "cus_keys";
    public static final String KEY_CONTACT_KEY = "contact_key";
    public static final String KEY_AUTH_TOKEN = "auth_token";
    public static final String KEY_SECURITY_TOKEN = "security_token";
    public static final String KEY_BILLING_ADDRESS = "billing_address";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_NO_OF_QTY = "no_of_qty";
    public static final String KEY_MFGR_KEY = "mfgr_key";
    public static final String KEY_SO_ITEM_QTY = "so_item_qty";
    public static final String KEY_SO_ITEM_DISCOUNT_PER = "so_item_discount_per";
    public static final String KEY_SO_ITEM_DISCOUNTED_RATE = "so_item_discounted_rate";
    public static final String KEY_CONTACT_PERSON = "contact_person";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String IS_SUCCESS = "is_success";

    public static final String KEY_BANK = "bank";
    public static final String KEY_TRANS_DATE = "trans_date";
    public static final String KEY_PAYMENT_MODE = "payment_mode";
    public static final String KEY_CHEQUE_NO = "cheque_no";
    public static final String KEY_CHEQUE_DATE = "cheque_date";

    public static final String KEY_BILLS = "bills";
    public static final String KEY_CREDIT_DAYS = "credit_days";
    public static final String KEY_CREDIT_USED = "credit_used";
    public static final String KEY_TYPE = "type";
    public static final String KEY_UNMAPPED_AMOUNT = "unmapped_amount";
    public static final String KEY_SUSPENSE_AMOUNT = "suspense_amount";

    public static final String KEY_REMARKS1 = "remarks";

    public static final String KEY_COMPANY_CODE = "company_code";
    public static final String KEY_REG_ID = "reg_id";
    public static final String KEY_IMEI_NO = "imei_no";
    public static final String KEY_ANDROID_ID = "android_id";
    public static final String KEY_MODEL_NAME = "model_name";

    public static final String KEY_SITE1 = "sitename1";
    public static final String KEY_SITE2 = "sitename2";
    public static final String KEY_SITE3 = "sitename3";
    public static final String KEY_SUCCESS = "success";

    public static final String KEY_STATUS = "status";
    public static final String KEY_MSG = "msg";
    public static final String KEY_DATA = "data";
    public static final String KEY_CUSTOMER_KEY = "customer_key";
    public static final String KEY_SALES_ORDER = "sales_order";

    public static final String KEY_SO_KEY = "so_key";
    public static final String KEY_SO_ITEM_KEY = "so_item_key";
    public static final String KEY_SUPPLIED_QTY = "supplied_qty";

    public static final String KEY_MI_NAME = "mi_name";
    public static final String KEY_DATE = "date";
    public static final String KEY_ORDERED_QTY = "ordered_qty";
    public static final String KEY_PENDING_QTY = "pending_qty";
    public static final String KEY_PENDING_ORDERS = "pending_orders";

    public static final String KEY_USER_KEY = "user_key";
    public static final String KEY_PUNIT_KEY = "p_unit_key";
    public static final String KEY_CM_KEY = "cm_key";
    public static final String KEY_RESULT = "result";
    public static final String KEY_BRANCHES = "branches";
    public static final String KEY_BRANCH_CODE = "brCode";
    public static final String KEY_BRANCH_NAME = "brName";

    public static final String KEY_OBJ_NAME = "objName";
    public static final String KEY_MANU_CODE = "manu_code";
    public static final String KEY_MOD_TIME = "modified_time";
    public static final String KEY_LAST_INDEX = "lastindex";
    public static final String KEY_MI_KEY = "mi_key";
    public static final String KEY_QTY = "qty";
    public static final String KEY_PRICE = "price";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_REMARKS = "remarks";
    public static final String KEY_ENTERED_DATE_TIME = "entered_date_time";

    public static final String KEY_LOGIN_CHECK_TIME = "LoginCheckTime";
    public static final String KEY_MAX_LOGIN_TIME = "MaxLoginTime";
    public static final String KEY_MAX_LOGIN_ATTEMPT = "MaxLoginAttempt";
    public static final String KEY_ATT_TIME = "AttendanceTrackingTime";
    public static final String KEY_ALARM_TIME = "AlarmTime";

    public static final String KEY_CMKEY = "cmkey";
    public static final String KEY_CON_KEY = "con_key";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_ALTITUDE = "altitude";

    public static final String KEY_LAST_iNDEX = "lastIndex";
    public static final String KEY_TOTAL_RECORDS = "totalRecords";
    public static final String KEY_ALL_BRANDS = "All brands";
    public static final String KEY_QUANTITY = "quantity";
    public static final String URL_REGISTER = "http://appregistration.isteer.co/salesmgt.php";

    // API Methods
    public static final String METHOD_LOGIN = "/loginauthentication";
    public static final String METHOD_GET_PRODUCTS = "getMasterItem/";
    //public static final String METHOD_PLACE_AN_ORDER = "PlaceOrderOnly/";
    public static final String METHOD_VALIDATE_TOKEN = "isTokenValid/";
    public static final String METHOD_PUT_GCMID = "putRegIDS";

    public static final String METHOD_GET_CUST_DET = "iSteer_customer_details/";
    public static final String METHOD_GET_CUST_LOC = "getAllCustomerSiteLoc/";
    public static final String METHOD_GET_MASTER_ITEM = "/get_aerp_master_item/";

    public static final String METHOD_ADD_IMAGE_PATHS = "/iSteerService/addImagePaths/";
    public static final String METHOD_GETMYSERVICECALLS = "/iSteerService/getMyServiceCalls/";
    public static final String METHOD_UPDATECALLSTATUS = "/iSteerService/updateServiceCallStatus/";
    //public static final String METHOD_GETWORKLOGS = "/iSteerService/getWorkLogs/";
    public static final String METHOD_GETWORKLOGS = "/iSteerService/getWorkLogsWithSpareParts/";
    public static final String METHOD_ADDWORKLOGS = "/iSteerService/addWorkLogs/";
    public static final String METHOD_GETALLIMAGEPATHS = "/iSteerService/getAllImagePaths/";

    public static final String UPLOAD_PATH_SUFFIX = "/system_uploaded";
    public static final String METHOD_ADD_WORKLOG = "/add_worklog/";
    public static final String METHOD_UPDATE_LOC = "/updateCustomerLoc/";


    //public static final String UPLOAD_PATH = "http://www.lucroapps.com/qa3ti/";

    public static String getImageUploadPath() {
        String base_url = App.appPreference.getBaseUrl();
        String imageUploadPath = base_url.substring(0, base_url.indexOf("/", 8)) + UPLOAD_PATH_SUFFIX;

        Log.e("getImageUploadPath", "" + imageUploadPath);

        return imageUploadPath;
    }

    // http://shellcrm2014.isteer.co/shellstage/restphp

}
