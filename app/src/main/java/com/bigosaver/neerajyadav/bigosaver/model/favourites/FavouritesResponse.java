package com.bigosaver.neerajyadav.bigosaver.model.favourites;

import android.util.Log;

import com.bigosaver.neerajyadav.bigosaver.model.CategoryAPI;
import com.bigosaver.neerajyadav.bigosaver.model.commonmodels.Image;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.utility.Preferences;

public class FavouritesResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("merchant")
    @Expose
    private String merchant;
    @SerializedName("merchant_place")
    @Expose
    private String merchant_place;
    @SerializedName("merchant_area")
    @Expose
    private String merchant_area;

    public String getMerchant_area_name() {
        return merchant_area_name;
    }

    public void setMerchant_area_name(String merchant_area_name) {
        this.merchant_area_name = merchant_area_name;
    }

    @SerializedName("merchant_area_name")
    @Expose
    private String merchant_area_name;
    @SerializedName("merchant_city")
    @Expose
    private String merchant_city;
    @SerializedName("merchant_name")
    @Expose
    private String merchant_name;
    @SerializedName("merchant_category_icon")
    @Expose
    private String merchant_category_icon;
    @SerializedName("merchant_logo")
    @Expose
    private String merchant_logo;
    @SerializedName("merchant_images")
    @Expose
    private List<Image> merchant_images = new ArrayList<Image>();

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The merchant
     */
    public String getMerchant() {
        return merchant;
    }

    /**
     *
     * @param merchant
     * The merchant
     */
    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    /**
     *
     * @return
     * The merchant_place
     */
    public String getMerchant_place() {
        return merchant_place;
    }

    /**
     *
     * @param merchant_place
     * The merchant_place
     */
    public void setMerchant_place(String merchant_place) {
        this.merchant_place = merchant_place;
    }

    /**
     *
     * @return
     * The merchant_area
     */
    public String getMerchant_area() {
        return merchant_area;
    }

    /**
     *
     * @param merchant_area
     * The merchant_area
     */
    public void setMerchant_area(String merchant_area) {
        this.merchant_area = merchant_area;
    }

    /**
     *
     * @return
     * The merchant_city
     */
    public String getMerchant_city() {
        return merchant_city;
    }

    /**
     *
     * @param merchant_city
     * The merchant_city
     */
    public void setMerchant_city(String merchant_city) {
        this.merchant_city = merchant_city;
    }

    /**
     *
     * @return
     * The merchant_name
     */
    public String getMerchant_name() {
        return merchant_name;
    }

    /**
     *
     * @param merchant_name
     * The merchant_name
     */
    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    /**
     *
     * @return
     * The merchant_category_icon
     */
    public String getMerchant_category_icon() {
        return merchant_category_icon;
    }

    /**
     *
     * @param merchant_category_icon
     * The merchant_category_icon
     */
    public void setMerchant_category_icon(String merchant_category_icon) {
        this.merchant_category_icon = merchant_category_icon;
    }

    /**
     *
     * @return
     * The merchant_logo
     */
    public String getMerchant_logo() {
        return merchant_logo;
    }

    /**
     *
     * @param merchant_logo
     * The merchant_logo
     */
    public void setMerchant_logo(String merchant_logo) {
        this.merchant_logo = merchant_logo;
    }

    /**
     *
     * @return
     * The merchant_images
     */
    public List<Image> getMerchant_images() {
        return merchant_images;
    }

    /**
     *
     * @param merchant_images
     * The merchant_images
     */
    public void setMerchant_images(List<Image> merchant_images) {
        this.merchant_images = merchant_images;
    }


    public static List<FavouritesResponse> parseJson(String json) {
        Log.d("parse category", "parse json call");
        List<FavouritesResponse> favouritesList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Gson gson = new Gson();
                FavouritesResponse favouritesResponse = gson.fromJson(jsonObject.toString(), FavouritesResponse.class);
                favouritesList.add(favouritesResponse);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return favouritesList;
    }

}
