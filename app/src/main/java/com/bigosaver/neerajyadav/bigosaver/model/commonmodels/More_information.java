package com.bigosaver.neerajyadav.bigosaver.model.commonmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dhillon on 16-11-2016.
 */
public class More_information implements Serializable{

    @SerializedName("filter")
    @Expose
    private String filter;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("filter_name")
    @Expose
    private String filter_name;

    /**
     * @return The filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * @param filter The filter
     */
    public void setFilter(String filter) {
        this.filter = filter;
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
     * @return The value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return The filter_name
     */
    public String getFilter_name() {
        return filter_name;
    }

    /**
     * @param filter_name The filter_name
     */
    public void setFilter_name(String filter_name) {
        this.filter_name = filter_name;
    }

}
