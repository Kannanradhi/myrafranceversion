package com.isteer.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "aerp_location_log")

public class LocationLog {


    @ColumnInfo(name = "loc_key")
    @PrimaryKey(autoGenerate = true)
    private int loc_key;

    @ColumnInfo(name = "user_key")
    private String user_key;

    @ColumnInfo(name = "date_time")
    private String date_time;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "altitude")
    private String altitude;

    @ColumnInfo(name = "update_status")
    private String update_status;

    public int getLoc_key() {
        return loc_key;
    }

    public void setLoc_key(int loc_key) {
        this.loc_key = loc_key;
    }

    public String getUser_key() {
        return user_key;
    }

    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
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
}
