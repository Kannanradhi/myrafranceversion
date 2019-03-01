package com.isteer.service.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "aerp_alarm_log")
public class AlarmLog {


    @ColumnInfo(name = "ala_key")
    @PrimaryKey
    private int ala_key;

    @ColumnInfo(name = "user_key")
    private String user_key;

    @ColumnInfo(name = "start_date_time")
    private String start_date_time;

    @ColumnInfo(name = "off_date_time")
    private String off_date_time;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "altitude")
    private String altitude;

    @ColumnInfo(name = "update_status")
    private String update_status;

    @ColumnInfo(name = "record_status")
    private String record_status;

    public int getAla_key() {
        return ala_key;
    }

    public void setAla_key(int ala_key) {
        this.ala_key = ala_key;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(String start_date_time) {
        this.start_date_time = start_date_time;
    }

    public String getOff_date_time() {
        return off_date_time;
    }

    public void setOff_date_time(String off_date_time) {
        this.off_date_time = off_date_time;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(String update_status) {
        this.update_status = update_status;
    }

    public String getRecord_status() {
        return record_status;
    }

    public void setRecord_status(String record_status) {
        this.record_status = record_status;
    }
}
