package com.isteer.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "aerp_upload_files")
public class ImagePathData {


    @SerializedName("data")
    @Ignore
    private ArrayList<ImagePathData> data;

    @SerializedName("upload_key")
    @ColumnInfo(name ="upload_key")
    @PrimaryKey()
    private int upload_key;

    @SerializedName("ref_key")
    @ColumnInfo(name ="ref_key")
    private int ref_key;

    @SerializedName("ref_key_add")
    @ColumnInfo(name ="ref_key_add")
    private int ref_key_add;

    @SerializedName("module_name")
    @ColumnInfo(name ="module_name")
    private String module_name;

    @SerializedName("media_type")
    @ColumnInfo(name ="media_type")
    private String media_type;

    @SerializedName("file_name")
    @ColumnInfo(name ="file_name")
    private String file_name;

    @SerializedName("remarks")
    @ColumnInfo(name ="remarks")
    private String remarks;

    @SerializedName("display_name")
    @ColumnInfo(name ="display_name")
    private String display_name;

    @SerializedName("upload_status")
    @ColumnInfo(name ="upload_status")
    private String upload_status;

    @SerializedName("uploaded_by")
    @ColumnInfo(name ="uploaded_by")
    private String uploaded_by;

    @SerializedName("uploaded_date")
    @ColumnInfo(name ="uploaded_date")
    private String uploaded_date;

    @SerializedName("last_modified_time")
    @ColumnInfo(name ="last_modified_time")
    private String last_modified_time;

    @SerializedName("modified_by")
    @ColumnInfo(name ="modified_by")
    private String modified_by;

    @SerializedName("thumbnail_filename")
    @ColumnInfo(name ="thumbnail_filename")
    private String thumbnail_filename;

    @SerializedName("file_path")
    @ColumnInfo(name ="file_path")
    private String file_path;


    @ColumnInfo(name ="is_syncTo_server")
    private int is_syncTo_server = 1;

    public int getIs_syncTo_server() {
        return is_syncTo_server;
    }

    public void setIs_syncTo_server(int is_syncTo_server) {
        this.is_syncTo_server = is_syncTo_server;
    }

    public ArrayList<ImagePathData> getData() {
        return data;
    }

    public void setData(ArrayList<ImagePathData> data) {
        this.data = data;
    }

    public int getUpload_key() {
        return upload_key;
    }

    public void setUpload_key(int upload_key) {
        this.upload_key = upload_key;
    }

    public int getRef_key() {
        return ref_key;
    }

    public void setRef_key(int ref_key) {
        this.ref_key = ref_key;
    }

    public int getRef_key_add() {
        return ref_key_add;
    }

    public void setRef_key_add(int ref_key_add) {
        this.ref_key_add = ref_key_add;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getUpload_status() {
        return upload_status;
    }

    public void setUpload_status(String upload_status) {
        this.upload_status = upload_status;
    }

    public String getUploaded_by() {
        return uploaded_by;
    }

    public void setUploaded_by(String uploaded_by) {
        this.uploaded_by = uploaded_by;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getThumbnail_filename() {
        return thumbnail_filename;
    }

    public void setThumbnail_filename(String thumbnail_filename) {
        this.thumbnail_filename = thumbnail_filename;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUploaded_date() {
        return uploaded_date;
    }

    public void setUploaded_date(String uploaded_date) {
        this.uploaded_date = uploaded_date;
    }

    public String getLast_modified_time() {
        return last_modified_time;
    }

    public void setLast_modified_time(String last_modified_time) {
        this.last_modified_time = last_modified_time;
    }

}
