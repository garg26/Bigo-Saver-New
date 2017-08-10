package com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail;

import com.bigosaver.neerajyadav.bigosaver.model.commonmodels.Cuisine;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Filters {

    @SerializedName("cuisine")
    @Expose
    private List<Cuisine> cuisine = new ArrayList<Cuisine>();
    @SerializedName("star rating")
    @Expose
    private List<Object> star_rating = new ArrayList<Object>();
    @SerializedName("more information")
    @Expose
    private List<More_information> more_information = new ArrayList<More_information>();
    @SerializedName("hotel rooms")
    @Expose
    private List<Object> hotel_rooms = new ArrayList<Object>();
    @SerializedName("themes")
    @Expose
    private List<Object> themes = new ArrayList<Object>();
    @SerializedName("hotel facilities")
    @Expose
    private List<Object> hotel_facilities = new ArrayList<Object>();
    @SerializedName("type")
    @Expose
    private List<Cuisine> type = new ArrayList<Cuisine>();
    @SerializedName("area / location")
    @Expose
    private List<Object> area___location = new ArrayList<Object>();

    /**
     * @return The cuisine
     */
    public List<Cuisine> getCuisine() {
        return cuisine;
    }

    /**
     * @param cuisine The cuisine
     */
    public void setCuisine(List<Cuisine> cuisine) {
        this.cuisine = cuisine;
    }

    /**
     * @return The star_rating
     */
    public List<Object> getStar_rating() {
        return star_rating;
    }

    /**
     * @param star_rating The star rating
     */
    public void setStar_rating(List<Object> star_rating) {
        this.star_rating = star_rating;
    }

    /**
     * @return The more_information
     */
    public List<More_information> getMore_information() {
        return more_information;
    }

    /**
     * @param more_information The more information
     */
    public void setMore_information(List<More_information> more_information) {
        this.more_information = more_information;
    }

    /**
     * @return The hotel_rooms
     */
    public List<Object> getHotel_rooms() {
        return hotel_rooms;
    }

    /**
     * @param hotel_rooms The hotel rooms
     */
    public void setHotel_rooms(List<Object> hotel_rooms) {
        this.hotel_rooms = hotel_rooms;
    }

    /**
     * @return The themes
     */
    public List<Object> getThemes() {
        return themes;
    }

    /**
     * @param themes The themes
     */
    public void setThemes(List<Object> themes) {
        this.themes = themes;
    }

    /**
     * @return The hotel_facilities
     */
    public List<Object> getHotel_facilities() {
        return hotel_facilities;
    }

    /**
     * @param hotel_facilities The hotel facilities
     */
    public void setHotel_facilities(List<Object> hotel_facilities) {
        this.hotel_facilities = hotel_facilities;
    }

    /**
     * @return The type
     */
    public List<Cuisine> getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(List<Cuisine> type) {
        this.type = type;
    }

    /**
     * @return The area___location
     */
    public List<Object> getArea___location() {
        return area___location;
    }

    /**
     * @param area___location The area / location
     */
    public void setArea___location(List<Object> area___location) {
        this.area___location = area___location;
    }

}