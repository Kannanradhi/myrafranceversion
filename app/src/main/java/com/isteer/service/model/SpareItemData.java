package com.isteer.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "spare_item")
public class SpareItemData {

    @SerializedName("dummy_key1")
    @Expose
    @Ignore
    private String dummy_key1 = "";

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("scsp_key")
    @ColumnInfo(name = "scsp_key")
    private int scsp_key;

    @ColumnInfo(name = "scl_key")
    @SerializedName("scl_key")
    private String scl_key = "";

    @ColumnInfo(name = "project_key")
    @SerializedName("project_key")
    private String project_key = "";

    @ColumnInfo(name = "service_key")
    @SerializedName("service_key")
    private String service_key = "";

    @ColumnInfo(name = "spare_part_code")
    @SerializedName("spare_part_code")
    private String spare_part_code = "";

    @ColumnInfo(name = "spare_part_name")
    @SerializedName("spare_part_name")
    private String spare_part_name = "";

    @ColumnInfo(name = "spare_part_desc")
    @SerializedName("spare_part_desc")
    private String spare_part_desc = "";

    @ColumnInfo(name = "rate")
    @SerializedName("rate")
    private String rate = "0.00";

    @ColumnInfo(name = "total_amount")
    @SerializedName("total_amount")
    private String total_amount = "0.00";

    @ColumnInfo(name = "qty")
    @SerializedName("qty")
    private String qty = "0";


    //is_synced_to_server values
    //	1 - for server values
    //  0 - for modified entries

    @ColumnInfo(name = "is_synced_to_server")
    private int is_synced_to_server = 1;


    // entry_key used to differ insert other modified entries
    // -1 for temprorary entries need to remove when you enter inside
    // 0 for default
    // servicecall id for particular worklog entries


    @ColumnInfo(name = "entry_key")
    private String entry_key = "0";


    public String getDummy_key1() {
        return  dummy_key1;
    }

    public void setDummy_key1(String dummy_key1) {
        this.dummy_key1 = dummy_key1;
    }

    public String getProject_key() {
        return project_key;
    }

    public void setProject_key(String project_key) {
        this.project_key = project_key;
    }


    public int getScsp_key() {
        return scsp_key;
    }

    public void setScsp_key(int scsp_key) {
        this.scsp_key = scsp_key;
    }

    public String getScl_key() {
        return scl_key;
    }

    public void setScl_key(String scl_key) {
        this.scl_key = scl_key;
    }

    public String getService_key() {
        return service_key;
    }

    public void setService_key(String service_key) {
        this.service_key = service_key;
    }

    public String getSpare_part_code() {
        return spare_part_code;
    }

    public void setSpare_part_code(String spare_part_code) {
        this.spare_part_code = spare_part_code;
    }

    public String getSpare_part_name() {
        return spare_part_name;
    }

    public void setSpare_part_name(String spare_part_name) {
        this.spare_part_name = spare_part_name;
    }

    public String getSpare_part_desc() {
        return spare_part_desc;
    }

    public void setSpare_part_desc(String spare_part_desc) {
        this.spare_part_desc = spare_part_desc;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public int getIs_synced_to_server() {
        return is_synced_to_server;
    }

    public void setIs_synced_to_server(int is_synced_to_server) {
        this.is_synced_to_server = is_synced_to_server;
    }

    public String getEntry_key() {
        return entry_key;
    }

    public void setEntry_key(String entry_key) {
        this.entry_key = entry_key;
    }
}
