package com.bigosaver.neerajyadav.bigosaver.model.RedeemResponse;

import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.search.SearchMerchantWithPlace;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.utility.JsonUtil;

public class RedemptionHistoryResponse {

    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("offer_title")
    @Expose
    private String offer_title;
    @SerializedName("reference_number")
    @Expose
    private String reference_number;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("savings")
    @Expose
    private Integer savings;
    @SerializedName("merchant")
    @Expose
    private String merchant;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("merchant_logo")
    @Expose
    private String merchant_logo;
    @SerializedName("area")
    @Expose
    private String area;

    /**
     * @return The offer
     */
    public String getOffer() {
        return offer;
    }

    /**
     * @param offer The offer
     */
    public void setOffer(String offer) {
        this.offer = offer;
    }

    /**
     * @return The offer_title
     */
    public String getOffer_title() {
        return offer_title;
    }

    /**
     * @param offer_title The offer_title
     */
    public void setOffer_title(String offer_title) {
        this.offer_title = offer_title;
    }

    /**
     * @return The reference_number
     */
    public String getReference_number() {
        return reference_number;
    }

    /**
     * @param reference_number The reference_number
     */
    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }

    /**
     * @return The user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The savings
     */
    public String getSavings() {
        if (savings != null)
            return savings.toString();
        return "0";
    }

    /**
     * @param savings The savings
     */
    public void setSavings(Integer savings) {
        this.savings = savings;
    }

    /**
     * @return The merchant
     */
    public String getMerchant() {
        return merchant;
    }

    /**
     * @param merchant The merchant
     */
    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    /**
     * @return The created_at
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * @param created_at The created_at
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    /**
     * @return The merchant_logo
     */
    public String getMerchant_logo() {
        return merchant_logo;
    }

    /**
     * @param merchant_logo The merchant_logo
     */
    public void setMerchant_logo(String merchant_logo) {
        this.merchant_logo = merchant_logo;
    }

    /**
     * @return The area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area The area
     */
    public void setArea(String area) {
        this.area = area;
    }

    public static List<RedemptionHistoryResponse> parseJson(String json) throws JSONException {
        JSONArray array = new JSONArray(json);
        List<RedemptionHistoryResponse> list = new ArrayList<>();
        if (array != null && array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                RedemptionHistoryResponse data = (RedemptionHistoryResponse) JsonUtil.parseJson(obj.toString(),
                        RedemptionHistoryResponse.class);
                list.add(data);
            }
        }
        return list;
    }

}