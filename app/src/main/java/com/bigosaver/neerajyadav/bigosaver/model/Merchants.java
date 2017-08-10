package com.bigosaver.neerajyadav.bigosaver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAHU on 28-07-2016.
 */
public class Merchants {
    @SerializedName("merchantName")
    @Expose
    public String merchantName;
    @SerializedName("offers")
    @Expose
    public List<Offer> offers = new ArrayList<Offer>();
    @SerializedName("details")
    @Expose
    public Details details;
    @SerializedName("logo")
    @Expose
    public String logo;
    @SerializedName("firstImage")
    @Expose
    public String firstImage;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("areaName")
    @Expose
    public String areaName;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
