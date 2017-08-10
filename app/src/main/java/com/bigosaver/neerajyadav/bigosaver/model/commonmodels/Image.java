package com.bigosaver.neerajyadav.bigosaver.model.commonmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dhillon on 16-11-2016.
 */
public class Image implements Serializable{

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("order_number")
    @Expose
    private String order_number;
    @SerializedName("id")
    @Expose
    private String id;

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
     * @return The order_number
     */
    public String getOrder_number() {
        return order_number;
    }

    /**
     * @param order_number The order_number
     */
    public void setOrder_number(String order_number) {
        this.order_number = order_number;
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

}
