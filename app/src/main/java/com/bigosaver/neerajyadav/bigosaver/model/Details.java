package com.bigosaver.neerajyadav.bigosaver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAHU on 28-07-2016.
 */

public class Details {

    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("addressLine2")
    @Expose
    public String addressLine2;
    @SerializedName("longitude")
    @Expose
    public double longitude;
    @SerializedName("addressLine1")
    @Expose
    public String addressLine1;
    @SerializedName("imgs")
    @Expose
    public List<String> imgs = new ArrayList<String>();
    @SerializedName("area")
    @Expose
    public String area;
    @SerializedName("merchant_property")
    @Expose
    public List<MerchantProperty> merchantProperty = new ArrayList<MerchantProperty>();
    @SerializedName("localArea")
    @Expose
    public String localArea;
    @SerializedName("timings")
    @Expose
    public String timings;
    @SerializedName("latitude")
    @Expose
    public double latitude;
    @SerializedName("additionalDetails")
    @Expose
    public List<AdditionalDetail> additionalDetails = new ArrayList<AdditionalDetail>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<MerchantProperty> getMerchantProperty() {
        return merchantProperty;
    }

    public void setMerchantProperty(List<MerchantProperty> merchantProperty) {
        this.merchantProperty = merchantProperty;
    }

    public String getLocalArea() {
        return localArea;
    }

    public void setLocalArea(String localArea) {
        this.localArea = localArea;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }


    public List<AdditionalDetail> getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(List<AdditionalDetail> additionalDetails) {
        this.additionalDetails = additionalDetails;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
