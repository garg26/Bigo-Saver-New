package com.bigosaver.neerajyadav.bigosaver.model.promocode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoCardResponse {

    @SerializedName("monthly_price")
    @Expose
    private Integer monthly_price;
    @SerializedName("yearly_price")
    @Expose
    private Integer yearly_price;
    @SerializedName("discount_price")
    @Expose
    private Integer discount_price;
    @SerializedName("validity")
    @Expose
    private String validity;

    public Integer getMonthly_price() {
        return monthly_price;
    }

    public void setMonthly_price(Integer monthly_price) {
        this.monthly_price = monthly_price;
    }

    public Integer getYearly_price() {
        return yearly_price;
    }

    public void setYearly_price(Integer yearly_price) {
        this.yearly_price = yearly_price;
    }

    public Integer getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(Integer discount_price) {
        this.discount_price = discount_price;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

}