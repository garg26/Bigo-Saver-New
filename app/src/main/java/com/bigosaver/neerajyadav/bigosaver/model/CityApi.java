package com.bigosaver.neerajyadav.bigosaver.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhillon on 19-10-2016.
 */

public class CityApi implements Serializable {

    @SerializedName("id")
    @Expose
    protected String id;
    @SerializedName("name")
    @Expose
    private String name;

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public static Object parseJson(String json) {
        Log.d("parse category", "parse json call");
        List<CityApi> cityList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Gson gson = new Gson();
                CityApi cityAPI = gson.fromJson(jsonObject.toString(), CityApi.class);
                cityList.add(cityAPI);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cityList;
    }

}