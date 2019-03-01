package com.isteer.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "work_log")
public class WorkLogData {

    @SerializedName("dummy_key")
    @Expose
    @Ignore
    private String dummy_key = "";

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @SerializedName("scl_key")
    @ColumnInfo(name = "scl_key")
    private int scl_key;

    @SerializedName("service_key")
    @ColumnInfo(name = "service_key")
    private int service_key;

    @SerializedName("project_key")
    @ColumnInfo(name = "project_key")
    private String project_key;

    @SerializedName("mi_key")
    @ColumnInfo(name = "mi_key")
    private String mi_key = "";

    @SerializedName("product_name")
    @ColumnInfo(name = "product_name")
    private String product_name = "";

    @SerializedName("service_type")
    @ColumnInfo(name = "service_type")
    private String service_type = "";

    @SerializedName("start_time")
    @ColumnInfo(name = "start_time")
    private String start_time = "";

    @SerializedName("end_time")
    @ColumnInfo(name = "end_time")
    private String end_time = "";

    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    private String user_id = "";

    @SerializedName("status")
    @ColumnInfo(name = "status")
    private int status;

    @SerializedName("is_spare_parts_needed")
    @ColumnInfo(name = "is_spare_parts_needed")
    private String is_spare_parts_needed = "n";

    @SerializedName("service_eng_count")
    @ColumnInfo(name = "service_eng_count")
    private int service_eng_count = 0;

    @SerializedName("remarks")
    @ColumnInfo(name = "remarks")
    private String remarks = "";

    @SerializedName("entered_by")
    @ColumnInfo(name = "entered_by")
    private String entered_by = "";

    @SerializedName("entered_date_time")
    @ColumnInfo(name = "entered_date_time")
    private String entered_date_time = "";

    @SerializedName("visited_date")
    @ColumnInfo(name = "visited_date")
    private String visited_date = "";

    @SerializedName("entered_by_name")
    @ColumnInfo(name = "entered_by_name")
    private String entered_by_name = "";

    @SerializedName("serviced_by_name")
    @ColumnInfo(name = "serviced_by_name")
    private String serviced_by_name = "";

    @SerializedName("minutes_of_meeting")
    @ColumnInfo(name = "minutes_of_meeting")
    private String minutes_of_meeting = "";

    @SerializedName("replace_suggestion")
    @ColumnInfo(name = "replace_suggestion")
    private String replace_suggestion = "";

    @SerializedName("serviced_by")
    @ColumnInfo(name = "serviced_by")
    private String serviced_by = "";

    @SerializedName("latitude")
    @ColumnInfo(name = "latitude")
    private double latitude = 0.00;

    @SerializedName("longitude")
    @ColumnInfo(name = "longitude")
    private double longitude = 0.00;

    @SerializedName("altitude")
    @ColumnInfo(name = "altitude")
    private double altitude = 0.00;

    @SerializedName("labour_Amount")
    @ColumnInfo(name = "labour_Amount")
    private String labour_Amount = "";

    @SerializedName("visiting_Charge")
    @ColumnInfo(name = "visiting_Charge")
    private String visiting_Charge = "";

    @SerializedName("other_Charge")
    @ColumnInfo(name = "other_Charge")
    private String other_Charge = "";

    @SerializedName("total_amount")
    @ColumnInfo(name = "total_amount")
    private String total_amount = "";

    @SerializedName("received_amount")
    @ColumnInfo(name = "received_amount")
    private String received_amount = "";

    @SerializedName("balance_amount")
    @ColumnInfo(name = "balance_amount")
    private String balance_amount = "";

    @SerializedName("replacement_suggestion")
    @ColumnInfo(name = "replacement_suggestion")
    private String replacement_suggestion = "";

    @SerializedName("spares_count")
    @ColumnInfo(name = "spares_count")
    private String spares_count = "";

    @SerializedName("spares_total_amount")
    @ColumnInfo(name = "spares_total_amount")
    private String spares_total_amount = "";


    @ColumnInfo(name = "is_synced_server")
    private int is_synced_server = 1;


    @ColumnInfo(name = "worklog_key")
    private String worklog_key = "";


    @ColumnInfo(name = "new_pump")
    private String new_pump = "no";

    @ColumnInfo(name = "new_pump_value")
    private String new_pump_value = "";

    @ColumnInfo(name = "spares")
    private String spares = "no";

    @ColumnInfo(name = "spares_value")
    private String spares_value = "";

    @ColumnInfo(name = "amc")
    private String amc = "no";

    @ColumnInfo(name = "amc_amount")
    private String amc_amount = "";

    @ColumnInfo(name = "to_ic")
    private String to_ic = "no";

    @ColumnInfo(name = "to_ic_amount")
    private String to_ic_amount = "";


    public String getWorklog_key() {
        return worklog_key;
    }

    public void setWorklog_key(String worklog_key) {
        this.worklog_key = worklog_key;
    }

    @NonNull
    public int getScl_key() {
        return scl_key;
    }

    public void setScl_key(@NonNull int scl_key) {
        this.scl_key = scl_key;
    }

    public int getService_key() {
        return service_key;
    }

    public void setService_key(int service_key) {
        this.service_key = service_key;
    }

    public String getProject_key() {
        return project_key;
    }

    public void setProject_key(String project_key) {
        this.project_key = project_key;
    }

    public String getMi_key() {
        return mi_key;
    }

    public void setMi_key(String mi_key) {
        this.mi_key = mi_key;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
       /* if (status == 1) {
        } else if (status == 2) {
        } else if (status == 3) {

        } else if (status == 4) {

        } else {

        }
*/

        this.status = status;
    }

    public String getIs_spare_parts_needed() {
        return is_spare_parts_needed;
    }

    public void setIs_spare_parts_needed(String is_spare_parts_needed) {
        this.is_spare_parts_needed = is_spare_parts_needed;
    }

    public int getService_eng_count() {
        return service_eng_count;
    }

    public void setService_eng_count(int service_eng_count) {
        this.service_eng_count = service_eng_count;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEntered_by() {
        return entered_by;
    }

    public void setEntered_by(String entered_by) {
        this.entered_by = entered_by;
    }

    public String getEntered_date_time() {
        return entered_date_time;
    }

    public void setEntered_date_time(String entered_date_time) {
        this.entered_date_time = entered_date_time;
    }

    public String getVisited_date() {
        return visited_date;
    }

    public void setVisited_date(String visited_date) {
        this.visited_date = visited_date;
    }

    public String getEntered_by_name() {
        return entered_by_name;
    }

    public void setEntered_by_name(String entered_by_name) {
        this.entered_by_name = entered_by_name;
    }

    public String getServiced_by_name() {
        return serviced_by_name;
    }

    public void setServiced_by_name(String serviced_by_name) {
        this.serviced_by_name = serviced_by_name;
    }

    public String getMinutes_of_meeting() {
        return minutes_of_meeting;
    }

    public void setMinutes_of_meeting(String minutes_of_meeting) {
        this.minutes_of_meeting = minutes_of_meeting;
    }

    public String getReplace_suggestion() {
        return replace_suggestion;
    }

    public void setReplace_suggestion(String replace_suggestion) {
        this.replace_suggestion = replace_suggestion;
    }

    public String getServiced_by() {
        return serviced_by;
    }

    public void setServiced_by(String serviced_by) {
        this.serviced_by = serviced_by;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getLabour_Amount() {
        return labour_Amount;
    }

    public void setLabour_Amount(String labour_Amount) {
        this.labour_Amount = labour_Amount;
    }

    public String getVisiting_Charge() {
        return visiting_Charge;
    }

    public void setVisiting_Charge(String visiting_Charge) {
        this.visiting_Charge = visiting_Charge;
    }

    public String getOther_Charge() {
        return other_Charge;
    }

    public void setOther_Charge(String other_Charge) {
        this.other_Charge = other_Charge;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getReceived_amount() {
        return received_amount;
    }

    public void setReceived_amount(String received_amount) {
        this.received_amount = received_amount;
    }

    public String getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(String balance_amount) {
        this.balance_amount = balance_amount;
    }

    public String getReplacement_suggestion() {
        return replacement_suggestion;
    }

    public void setReplacement_suggestion(String replacement_suggestion) {
        this.replacement_suggestion = replacement_suggestion;
    }

    public String getSpares_count() {
        return spares_count;
    }

    public void setSpares_count(String spares_count) {
        this.spares_count = spares_count;
    }

    public String getSpares_total_amount() {
        return spares_total_amount;
    }

    public void setSpares_total_amount(String spares_total_amount) {
        this.spares_total_amount = spares_total_amount;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDummy_key() {
        return dummy_key;
    }

    public void setDummy_key(String dummy_key) {
        this.dummy_key = dummy_key;
    }

    public int getIs_synced_server() {
        return is_synced_server;
    }

    public void setIs_synced_server(int is_synced_server) {
        this.is_synced_server = is_synced_server;

    }

    public String getNew_pump() {
        return new_pump;
    }

    public void setNew_pump(String new_pump) {
        this.new_pump = new_pump;
    }

    public String getNew_pump_value() {
        return new_pump_value;
    }

    public void setNew_pump_value(String new_pump_value) {
        this.new_pump_value = new_pump_value;
    }

    public String getSpares() {
        return spares;
    }

    public void setSpares(String spares) {
        this.spares = spares;
    }

    public String getSpares_value() {
        return spares_value;
    }

    public void setSpares_value(String spares_value) {
        this.spares_value = spares_value;
    }

    public String getAmc() {
        return amc;
    }

    public void setAmc(String amc) {
        this.amc = amc;
    }

    public String getAmc_amount() {
        return amc_amount;
    }

    public void setAmc_amount(String amc_amount) {
        this.amc_amount = amc_amount;
    }

    public String getTo_ic() {
        return to_ic;
    }

    public void setTo_ic(String to_ic) {
        this.to_ic = to_ic;
    }

    public String getTo_ic_amount() {
        return to_ic_amount;
    }

    public void setTo_ic_amount(String to_ic_amount) {
        this.to_ic_amount = to_ic_amount;
    }


    @SerializedName("spare_parts")
    @Ignore
    private ArrayList<SpareItemData> spare_parts;


    public ArrayList<SpareItemData> getSpare_parts() {
        return spare_parts;
    }

    public void setSpare_parts(ArrayList<SpareItemData> spare_parts) {
        this.spare_parts = spare_parts;
    }

}
