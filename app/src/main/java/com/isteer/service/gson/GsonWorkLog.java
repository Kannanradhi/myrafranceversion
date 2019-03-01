package com.isteer.service.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.isteer.service.model.MasterItemData;
import com.isteer.service.model.WorkLogData;

import java.util.List;

public class GsonWorkLog {

    @SerializedName("data")
    @Expose
    private List<WorkLogData> data = null;

    @SerializedName("status")
    @Expose
    private Integer status;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public List<WorkLogData> getData() {
        return data;
    }

    public void setData(List<WorkLogData> data) {
        this.data = data;
    }

}