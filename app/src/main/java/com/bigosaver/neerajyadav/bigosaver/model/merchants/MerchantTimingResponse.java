package com.bigosaver.neerajyadav.bigosaver.model.merchants;

import com.bigosaver.neerajyadav.bigosaver.model.offers.FilterOfferResponse;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MerchantTimingResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("primary_opening")
    @Expose
    private String primary_opening;
    @SerializedName("primary_closing")
    @Expose
    private String primary_closing;
    @SerializedName("secondary_opening")
    @Expose
    private String secondary_opening;
    @SerializedName("secondary_closing")
    @Expose
    private String secondary_closing;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The day
     */
    public String getDay() {
        return day;
    }

    /**
     * @param day The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * @return The primary_opening
     */
    public String getPrimary_opening() {
        return primary_opening;
    }

    /**
     * @param primary_opening The primary_opening
     */
    public void setPrimary_opening(String primary_opening) {
        this.primary_opening = primary_opening;
    }

    /**
     * @return The primary_closing
     */
    public String getPrimary_closing() {
        return primary_closing;
    }

    /**
     * @param primary_closing The primary_closing
     */
    public void setPrimary_closing(String primary_closing) {
        this.primary_closing = primary_closing;
    }

    /**
     * @return The secondary_opening
     */
    public String getSecondary_opening() {
        return secondary_opening;
    }

    /**
     * @param secondary_opening The secondary_opening
     */
    public void setSecondary_opening(String secondary_opening) {
        this.secondary_opening = secondary_opening;
    }

    /**
     * @return The secondary_closing
     */
    public String getSecondary_closing() {
        return secondary_closing;
    }

    /**
     * @param secondary_closing The secondary_closing
     */
    public void setSecondary_closing(String secondary_closing) {
        this.secondary_closing = secondary_closing;
    }

    public  static  Object parseJson(String json) throws JSONException {
        List<MerchantTimingResponse> responseArrayList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for(int i = 0;i<jsonArray.length();i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            Gson gson = new Gson();
            MerchantTimingResponse filterOfferResponse = gson.fromJson(jsonObject.toString(), MerchantTimingResponse.class);
            responseArrayList.add(filterOfferResponse);
        }
        return responseArrayList;
    }

}