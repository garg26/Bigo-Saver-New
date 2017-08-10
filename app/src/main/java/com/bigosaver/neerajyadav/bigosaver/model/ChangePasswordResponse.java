package com.bigosaver.neerajyadav.bigosaver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("detail")
    @Expose
    private String detail;

    /**
     * @return The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return The detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail The detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

}