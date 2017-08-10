package com.bigosaver.neerajyadav.bigosaver.model.merchants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Filters {

    @SerializedName("cuisine")
    @Expose
    private List<Object> cuisine = new ArrayList<Object>();
    @SerializedName("additional_options")
    @Expose
    private List<Object> additional_options = new ArrayList<Object>();
    @SerializedName("type")
    @Expose
    private List<Object> type = new ArrayList<Object>();

    /**
     * @return The cuisine
     */
    public List<Object> getCuisine() {
        return cuisine;
    }

    /**
     * @param cuisine The cuisine
     */
    public void setCuisine(List<Object> cuisine) {
        this.cuisine = cuisine;
    }

    /**
     * @return The additional_options
     */
    public List<Object> getAdditional_options() {
        return additional_options;
    }

    /**
     * @param additional_options The additional_options
     */
    public void setAdditional_options(List<Object> additional_options) {
        this.additional_options = additional_options;
    }

    /**
     * @return The type
     */
    public List<Object> getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(List<Object> type) {
        this.type = type;
    }

}



