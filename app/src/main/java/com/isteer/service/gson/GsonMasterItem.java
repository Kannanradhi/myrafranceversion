package com.isteer.service.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.isteer.service.model.MasterItemData;

import java.util.List;

public class GsonMasterItem {

   @SerializedName("data")
   @Expose
   private List<MasterItemData> data;

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MasterItemData> getData() {
        return data;
    }

    public void setData(List<MasterItemData> data) {
        this.data = data;
    }

}