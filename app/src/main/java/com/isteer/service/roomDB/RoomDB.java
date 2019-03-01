package com.isteer.service.roomDB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;

import com.isteer.service.dao.DaoAttendanceLog;
import com.isteer.service.dao.DaoImageUpload;
import com.isteer.service.dao.DaoLocationLog;
import com.isteer.service.dao.DaoMasterItem;
import com.isteer.service.dao.DaoServicesCall;
import com.isteer.service.dao.DaoSpareItem;
import com.isteer.service.dao.DaoWorkLog;
import com.isteer.service.model.AlarmLog;
import com.isteer.service.model.AttendanceLog;
import com.isteer.service.model.ImagePathData;
import com.isteer.service.model.LocationLog;
import com.isteer.service.model.MasterItemData;
import com.isteer.service.model.ServiceCallData;
import com.isteer.service.model.SpareItemData;
import com.isteer.service.model.WorkLogData;


@Database(entities = {MasterItemData.class,
        SpareItemData.class,
        WorkLogData.class, AlarmLog.class, ServiceCallData.class, AttendanceLog.class, ImagePathData.class, LocationLog.class}, version = 3, exportSchema = false)

public abstract class RoomDB extends RoomDatabase {

    public abstract DaoMasterItem daoMasterItem();

    public abstract DaoSpareItem daoSpareItem();

    public abstract DaoAttendanceLog daoAttendanceLog();

    public abstract DaoLocationLog daoLocationLog();

    public abstract DaoServicesCall daoServicesCall();

    public abstract DaoWorkLog daoWorkLog();

    public abstract DaoImageUpload daoImageUpload();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("drop table work_log");
            database.execSQL("CREATE TABLE IF NOT EXISTS `work_log` (`scl_key` INTEGER NOT NULL, `service_key` INTEGER NOT NULL, `project_key` TEXT, `mi_key` TEXT, `product_name` TEXT, `service_type` TEXT, `start_time` TEXT, `end_time` TEXT, `user_id` TEXT, `status` INTEGER NOT NULL, `is_spare_parts_needed` TEXT, `service_eng_count` INTEGER NOT NULL, `remarks` TEXT, `entered_by` TEXT, `entered_date_time` TEXT, `visited_date` TEXT, `entered_by_name` TEXT, `serviced_by_name` TEXT, `minutes_of_meeting` TEXT, `replace_suggestion` TEXT, `serviced_by` TEXT, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `altitude` REAL NOT NULL, `labour_Amount` TEXT, `visiting_Charge` TEXT, `other_Charge` TEXT, `total_amount` TEXT, `received_amount` TEXT, `balance_amount` TEXT, `replacement_suggestion` TEXT, `spares_count` TEXT, `spares_total_amount` TEXT, `is_synced_server` INTEGER NOT NULL, `worklog_key` TEXT, PRIMARY KEY(`scl_key`))");
        }
    };
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("CREATE TABLE IF NOT EXISTS `aerp_alarm_log` (`ala_key` INTEGER NOT NULL, `user_key` TEXT, `start_date_time` TEXT, `off_date_time` TEXT, `longitude` TEXT, `latitude` TEXT, `altitude` TEXT, `update_status` TEXT, `record_status` TEXT, PRIMARY KEY(`ala_key`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `aerp_servicecall_master` (`servicecall_key` INTEGER NOT NULL, `customer_key` INTEGER NOT NULL, `project_key` INTEGER NOT NULL, `customer_name` TEXT, `contract` TEXT, `contract_type` TEXT, `priority` TEXT, `service_issue` TEXT, `service_desc` TEXT, `type_of_call` TEXT, `caller_address` TEXT, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `altitude` REAL NOT NULL, `caller_mobile_no` TEXT, `report_date` TEXT, `status` INTEGER NOT NULL, `pending_remarks` TEXT, `amc_number` INTEGER NOT NULL, `amc_expiry_days` INTEGER NOT NULL, `is_syncTo_server` INTEGER NOT NULL, PRIMARY KEY(`servicecall_key`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `aerp_attendence_log` (`att_key` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_key` TEXT, `date` TEXT, `start_time` TEXT, `stop_time` TEXT, `status` TEXT, `update_status` TEXT)");
            database.execSQL("CREATE TABLE IF NOT EXISTS `aerp_upload_files` (`upload_key` INTEGER NOT NULL, `ref_key` INTEGER NOT NULL, `ref_key_add` INTEGER NOT NULL, `module_name` TEXT, `media_type` TEXT, `file_name` TEXT, `remarks` TEXT, `display_name` TEXT, `upload_status` TEXT, `uploaded_by` TEXT, `uploaded_date` TEXT, `last_modified_time` TEXT, `modified_by` TEXT, `thumbnail_filename` TEXT, `file_path` TEXT, `is_syncTo_server` INTEGER NOT NULL, PRIMARY KEY(`upload_key`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `aerp_location_log` (`loc_key` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_key` TEXT, `date_time` TEXT, `longitude` TEXT, `latitude` TEXT, `altitude` TEXT, `update_status` TEXT)");
        }
    };


}

