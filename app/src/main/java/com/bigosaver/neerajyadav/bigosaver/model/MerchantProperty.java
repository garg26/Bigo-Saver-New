package com.bigosaver.neerajyadav.bigosaver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RAHU on 28-07-2016.
 */
public class MerchantProperty {
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("img")
    @Expose
    public String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
