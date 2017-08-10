package com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dhillon on 14-11-2016.
 */
public class Place extends BaseSearchClass{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("place_type")
    @Expose
    private String place_type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("city_name")
    @Expose
    private String city_name;
    @SerializedName("place_type_name")
    @Expose
    private String place_type_name;

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
     * @return The place_type
     */
    public String getPlace_type() {
        return place_type;
    }

    /**
     * @param place_type The place_type
     */
    public void setPlace_type(String place_type) {
        this.place_type = place_type;
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

    /**
     * @return The place_type_name
     */
    public String getPlace_type_name() {
        return place_type_name;
    }

    /**
     * @param place_type_name The place_type_name
     */
    public void setPlace_type_name(String place_type_name) {
        this.place_type_name = place_type_name;
    }

}
