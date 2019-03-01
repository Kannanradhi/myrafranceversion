package com.isteer.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


@Entity(tableName = "aerp_servicecall_master")

public class ServiceCallData implements Parcelable {


    @SerializedName("data")
    @Expose
    @Ignore
    private ArrayList<ServiceCallData> data;


    @SerializedName("servicecall_key")
    @Expose
    @ColumnInfo(name = "servicecall_key")
    @PrimaryKey
    private int servicecall_key;

    @SerializedName("customer_key")
    @Expose
    @ColumnInfo(name = "customer_key")
    private int customer_key;

    @SerializedName("project_key")
    @Expose
    @ColumnInfo(name = "project_key")
    private int project_key = -1;

    @SerializedName("customer_name")
    @Expose
    @ColumnInfo(name = "customer_name")
    private String customer_name;

    @SerializedName("contract")
    @Expose
    @ColumnInfo(name = "contract")
    private String contract;

    @SerializedName("contract_type")
    @Expose
    @ColumnInfo(name = "contract_type")
    private String contract_type;

    @SerializedName("priority")
    @Expose
    @ColumnInfo(name = "priority")
    private String priority;

    @SerializedName("service_issue")
    @Expose
    @ColumnInfo(name = "service_issue")
    private String service_issue;

    @SerializedName("service_desc")
    @Expose
    @ColumnInfo(name = "service_desc")
    private String service_desc;

    @SerializedName("type_of_call")
    @Expose
    @ColumnInfo(name = "type_of_call")
    private String type_of_call;

    @SerializedName("caller_address")
    @Expose
    @ColumnInfo(name = "caller_address")
    private String caller_address;

    @SerializedName("latitude")
    @Expose
    @ColumnInfo(name = "latitude")
    private double latitude = 0.0;

    @SerializedName("longitude")
    @Expose
    @ColumnInfo(name = "longitude")
    private double longitude = 0.0;

    @SerializedName("altitude")
    @Expose
    @ColumnInfo(name = "altitude")
    private double altitude = 0.0;

    @SerializedName("caller_mobile_no")
    @Expose
    @ColumnInfo(name = "caller_mobile_no")
    private String caller_mobile_no;

    @SerializedName("report_date")
    @Expose
    @ColumnInfo(name = "report_date")
    private String report_date;

    @SerializedName("status")
    @Expose
    @ColumnInfo(name = "status")
    private int status;

    @SerializedName("pending_remarks")
    @Expose
    @ColumnInfo(name = "pending_remarks")
    private String pending_remarks;

    @SerializedName("amc_number")
    @Expose
    @ColumnInfo(name = "amc_number")
    private int amc_number;

    @SerializedName("amc_expiry_days")
    @Expose
    @ColumnInfo(name = "amc_expiry_days")
    private int amc_expiry_days = 0;

    @ColumnInfo(name = "is_syncTo_server")
    private int is_syncTo_server = 1;

    public ServiceCallData() {

    }

    protected ServiceCallData(Parcel in) {
        servicecall_key = in.readInt();
        customer_key = in.readInt();
        project_key = in.readInt();
        customer_name = in.readString();
        contract = in.readString();
        contract_type = in.readString();
        priority = in.readString();
        service_issue = in.readString();
        service_desc = in.readString();
        type_of_call = in.readString();
        caller_address = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        altitude = in.readDouble();
        caller_mobile_no = in.readString();
        report_date = in.readString();
        status = in.readInt();
        pending_remarks = in.readString();
        amc_number = in.readInt();
        amc_expiry_days = in.readInt();
    }

    public static final Creator<ServiceCallData> CREATOR = new Creator<ServiceCallData>() {
        @Override
        public ServiceCallData createFromParcel(Parcel in) {
            return new ServiceCallData(in);
        }

        @Override
        public ServiceCallData[] newArray(int size) {
            return new ServiceCallData[size];
        }
    };


    public int getIs_syncTo_server() {
        return is_syncTo_server;
    }

    public void setIs_syncTo_server(int is_syncTo_server) {
        this.is_syncTo_server = is_syncTo_server;
    }

    public ArrayList<ServiceCallData> getData() {
        return data;
    }

    public void setData(ArrayList<ServiceCallData> data) {
        this.data = data;
    }

    public int getServicecall_key() {
        return servicecall_key;
    }

    public void setServicecall_key(int servicecall_key) {
        this.servicecall_key = servicecall_key;
    }

    public int getCustomer_key() {
        return customer_key;
    }

    public void setCustomer_key(int customer_key) {
        this.customer_key = customer_key;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getService_issue() {
        return service_issue;
    }

    public void setService_issue(String service_issue) {
        this.service_issue = service_issue;
    }

    public String getService_desc() {
        return service_desc;
    }

    public void setService_desc(String service_desc) {
        this.service_desc = service_desc;
    }

    public String getType_of_call() {
        return type_of_call;
    }

    public void setType_of_call(String type_of_call) {
        this.type_of_call = type_of_call;
    }

    public String getCaller_address() {
        return caller_address;
    }

    public void setCaller_address(String caller_address) {
        this.caller_address = caller_address;
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

    public String getCaller_mobile_no() {
        return caller_mobile_no;
    }

    public void setCaller_mobile_no(String caller_mobile_no) {
        this.caller_mobile_no = caller_mobile_no;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPending_remarks() {
        return pending_remarks;
    }

    public void setPending_remarks(String pending_remarks) {
        this.pending_remarks = pending_remarks;
    }

    public int getAmc_number() {
        return amc_number;
    }

    public void setAmc_number(int amc_number) {
        this.amc_number = amc_number;
    }

    public int getAmc_expiry_days() {
        return amc_expiry_days;
    }

    public void setAmc_expiry_days(int amc_expiry_days) {
        this.amc_expiry_days = amc_expiry_days;
    }

    public int getProject_key() {
        return project_key;
    }

    public void setProject_key(int project_key) {
        this.project_key = project_key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(servicecall_key);
        dest.writeInt(customer_key);
        dest.writeInt(project_key);
        dest.writeString(customer_name);
        dest.writeString(contract);
        dest.writeString(contract_type);
        dest.writeString(priority);
        dest.writeString(service_issue);
        dest.writeString(service_desc);
        dest.writeString(type_of_call);
        dest.writeString(caller_address);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(altitude);
        dest.writeString(caller_mobile_no);
        dest.writeString(report_date);
        dest.writeInt(status);
        dest.writeString(pending_remarks);
        dest.writeInt(amc_number);
        dest.writeInt(amc_expiry_days);
    }
}
