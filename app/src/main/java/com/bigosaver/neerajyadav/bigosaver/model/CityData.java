package com.bigosaver.neerajyadav.bigosaver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import simplifii.framework.utility.JsonUtil;

/**
 * Created by nbansal2211 on 11/10/16.
 */

public class CityData implements Serializable {
    private String id;
    private String name;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public static List<CityData> parseJson(String json) throws JSONException {
        JSONArray array = new JSONArray(json);
        List<CityData> cities = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                CityData city = (CityData) JsonUtil.parseJson(array.getJSONObject(i).toString(), CityData.class);
                cities.add(city);
            }
        }
        return cities;
    }
}
