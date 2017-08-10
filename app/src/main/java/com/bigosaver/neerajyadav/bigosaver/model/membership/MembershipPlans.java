package com.bigosaver.neerajyadav.bigosaver.model.membership;

import android.util.Log;

import com.bigosaver.neerajyadav.bigosaver.model.Filters.FilterData;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MembershipPlans implements Serializable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("monthly_price")
    @Expose
    private Integer monthly_price;
    @SerializedName("yearly_price")
    @Expose
    private Integer yearly_price;
    @SerializedName("offers")
    @Expose
    private Integer offers;
    @SerializedName("merchants")
    @Expose
    private Integer merchants;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("offer_food_beverages")
    @Expose
    private Integer offer_food_beverages;
    @SerializedName("offer_health_beauty")
    @Expose
    private Integer offer_health_beauty;
    @SerializedName("offer_retail_service")
    @Expose
    private Integer offer_retail_service;
    @SerializedName("offer_things_todo")
    @Expose
    private Integer offer_things_todo;
    @SerializedName("offer_hotel_resort")
    @Expose
    private Integer offer_hotel_resort;

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
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The monthly_price
     */
    public Integer getMonthly_price() {
        return monthly_price;
    }

    /**
     * @param monthly_price The monthly_price
     */
    public void setMonthly_price(Integer monthly_price) {
        this.monthly_price = monthly_price;
    }

    /**
     * @return The yearly_price
     */
    public Integer getYearly_price() {
        return yearly_price;
    }

    /**
     * @param yearly_price The yearly_price
     */
    public void setYearly_price(Integer yearly_price) {
        this.yearly_price = yearly_price;
    }

    /**
     * @return The offers
     */
    public Integer getOffers() {
        return offers;
    }

    /**
     * @param offers The offers
     */
    public void setOffers(Integer offers) {
        this.offers = offers;
    }

    /**
     * @return The merchants
     */
    public Integer getMerchants() {
        return merchants;
    }

    /**
     * @param merchants The merchants
     */
    public void setMerchants(Integer merchants) {
        this.merchants = merchants;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The offer_food_beverages
     */
    public Integer getOffer_food_beverages() {
        return offer_food_beverages;
    }

    /**
     * @param offer_food_beverages The offer_food_beverages
     */
    public void setOffer_food_beverages(Integer offer_food_beverages) {
        this.offer_food_beverages = offer_food_beverages;
    }

    /**
     * @return The offer_health_beauty
     */
    public Integer getOffer_health_beauty() {
        return offer_health_beauty;
    }

    /**
     * @param offer_health_beauty The offer_health_beauty
     */
    public void setOffer_health_beauty(Integer offer_health_beauty) {
        this.offer_health_beauty = offer_health_beauty;
    }

    /**
     * @return The offer_retail_service
     */
    public Integer getOffer_retail_service() {
        return offer_retail_service;
    }

    /**
     * @param offer_retail_service The offer_retail_service
     */
    public void setOffer_retail_service(Integer offer_retail_service) {
        this.offer_retail_service = offer_retail_service;
    }

    /**
     * @return The offer_things_todo
     */
    public Integer getOffer_things_todo() {
        return offer_things_todo;
    }

    /**
     * @param offer_things_todo The offer_things_todo
     */
    public void setOffer_things_todo(Integer offer_things_todo) {
        this.offer_things_todo = offer_things_todo;
    }

    /**
     * @return The offer_hotel_resort
     */
    public Integer getOffer_hotel_resort() {
        return offer_hotel_resort;
    }

    /**
     * @param offer_hotel_resort The offer_hotel_resort
     */
    public void setOffer_hotel_resort(Integer offer_hotel_resort) {
        this.offer_hotel_resort = offer_hotel_resort;
    }


}