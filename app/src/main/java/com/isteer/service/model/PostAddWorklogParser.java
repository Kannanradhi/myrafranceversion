package com.isteer.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostAddWorklogParser {

    @SerializedName("vals")
    private List<WorkLogData> vals;
}
