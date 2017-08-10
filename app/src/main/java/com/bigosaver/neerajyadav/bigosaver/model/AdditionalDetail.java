package com.bigosaver.neerajyadav.bigosaver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAHU on 28-07-2016.
 */
public class AdditionalDetail {
    @SerializedName("logo")
    @Expose
    public String logo;
    @SerializedName("value")
    @Expose
    public String value;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
