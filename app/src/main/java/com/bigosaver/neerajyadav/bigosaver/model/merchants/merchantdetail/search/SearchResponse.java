package com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhillon on 14-11-2016.
 */
public class SearchResponse {

    @SerializedName("merchant")
    @Expose
    private List<Merchant> merchant = new ArrayList<Merchant>();
    @SerializedName("places")
    @Expose
    private List<Place> places = new ArrayList<Place>();
    @SerializedName("areas")
    @Expose
    private List<Area> areas = new ArrayList<Area>();

    /**
     * @return The merchant
     */
    public List<Merchant> getMerchant() {
        return merchant;
    }

    /**
     * @param merchant The merchant
     */
    public void setMerchant(List<Merchant> merchant) {
        this.merchant = merchant;
    }

    /**
     * @return The places
     */
    public List<Place> getPlaces() {
        return places;
    }

    /**
     * @param places The places
     */
    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    /**
     * @return The areas
     */
    public List<Area> getAreas() {
        return areas;
    }

    /**
     * @param areas The areas
     */
    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }


}
