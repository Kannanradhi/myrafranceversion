package com.isteer.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseWorklogPraser {

    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("data")
    @Expose
    private InnerArrayParser data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InnerArrayParser getData() {
        return data;
    }

    public void setData(InnerArrayParser data) {
        this.data = data;
    }
}


