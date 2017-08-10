package com.bigosaver.neerajyadav.bigosaver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dhillon on 18-01-2017.
 */

public class BaseResponse {
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @SerializedName("success")
    @Expose
    private String success;

}
