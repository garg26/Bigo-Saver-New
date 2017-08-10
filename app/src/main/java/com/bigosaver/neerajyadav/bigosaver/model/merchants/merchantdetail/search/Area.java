package com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Area extends BaseSearchClass {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("city_name")
    @Expose
    private String city_name;

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
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The city_name
     */
    public String getCity_name() {
        return city_name;
    }

    /**
     * @param city_name The city_name
     */
    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

}