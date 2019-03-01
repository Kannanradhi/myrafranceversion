package com.isteer.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InnerArrayParser {

    @SerializedName("WorkLog")
    @Expose
    private ArrayList<WorkLogData> worklog;

    @SerializedName("SpareParts")
    @Expose
    private ArrayList<SpareItemData> sparepart;

    public ArrayList<WorkLogData> getWorklog() {
        return worklog;
    }

    public void setWorklog(ArrayList<WorkLogData> worklog) {
        this.worklog = worklog;
    }

    public ArrayList<SpareItemData> getSparepart() {
        return sparepart;
    }

    public void setSparepart(ArrayList<SpareItemData> sparepart) {
        this.sparepart = sparepart;
    }
}
