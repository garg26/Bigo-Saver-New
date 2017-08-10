package com.bigosaver.neerajyadav.bigosaver.model.subscriptionhistory;

import com.bigosaver.neerajyadav.bigosaver.model.RedeemResponse.RedemptionHistoryResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.utility.JsonUtil;

public class SubscriptionHistoryResponse {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("full_name")
    @Expose
    private String full_name;
    @SerializedName("user_city")
    @Expose
    private String user_city;
    @SerializedName("cash_card")
    @Expose
    private String cash_card;
    @SerializedName("start_date")
    @Expose
    private String start_date;
    @SerializedName("issued_to")
    @Expose
    private String issued_to;
    @SerializedName("end_date")
    @Expose
    private String end_date;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("actual_amount")
    @Expose
    private Integer actual_amount;
    @SerializedName("discount_amount")
    @Expose
    private Integer discount_amount;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("bigo_drink")
    @Expose
    private Boolean bigo_drink;
    @SerializedName("membership_type")
    @Expose
    private String membership_type;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getCash_card() {
        return cash_card;
    }

    public void setCash_card(String cash_card) {
        this.cash_card = cash_card;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getIssued_to() {
        return issued_to;
    }

    public void setIssued_to(String issued_to) {
        this.issued_to = issued_to;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getActual_amount() {
        return actual_amount;
    }

    public void setActual_amount(Integer actual_amount) {
        this.actual_amount = actual_amount;
    }

    public Integer getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(Integer discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Boolean getBigo_drink() {
        return bigo_drink;
    }

    public void setBigo_drink(Boolean bigo_drink) {
        this.bigo_drink = bigo_drink;
    }

    public String getMembership_type() {
        return membership_type;
    }

    public void setMembership_type(String membership_type) {
        this.membership_type = membership_type;
    }

    public static List<SubscriptionHistoryResponse> parseJson(String json) throws JSONException {
        JSONArray array = new JSONArray(json);
        List<SubscriptionHistoryResponse> list = new ArrayList<>();
        if (array != null && array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                SubscriptionHistoryResponse data = (SubscriptionHistoryResponse) JsonUtil.parseJson(obj.toString(),
                        SubscriptionHistoryResponse.class);
                list.add(data);
            }
        }
        return list;
    }

}