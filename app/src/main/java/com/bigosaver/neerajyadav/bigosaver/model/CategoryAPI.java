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

import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

/**
 * Created by Neeraj Yadav on 10/7/2016.
 */

public class CategoryAPI implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("display_name")
    @Expose
    private String display_name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("mobile_image")
    @Expose
    private String mobileImage;
    @SerializedName("tabs")
    @Expose
    private List<Tab> tabs = new ArrayList<Tab>();

    public String getMobileImage() {
        return mobileImage;
    }

    public void setMobileImage(String mobileImage) {
        this.mobileImage = mobileImage;
    }
    //    @SerializedName("merchants")
//    @Expose
//    private List<Object> merchants = new ArrayList<Object>();

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
     * @return The display_name
     */
    public String getDisplay_name() {
        return display_name;
    }

    /**
     * @param display_name The display_name
     */
    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
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
     * @return The mobile_image
     */
    public String getMobile_image() {
        return mobileImage;
    }

    /**
     * @param mobileImage The mobile_image
     */
    public void setMobile_image(String mobileImage) {
        this.mobileImage = mobileImage;
    }

    /**
     * @return The tabs
     */
    public List<Tab> getTabs() {
        return tabs;
    }

    /**
     * @param tabs The tabs
     */
    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
    }

    public static List<CategoryAPI> parseJson(String json) {
        Log.d("parse category", "parse json call");
        List<CategoryAPI> categoryList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Gson gson = new Gson();
                CategoryAPI categoryAPI = gson.fromJson(jsonObject.toString(), CategoryAPI.class);
                categoryList.add(categoryAPI);
            }
            Preferences.saveData(Preferences.CATEGORY, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public static List<CategoryAPI> getInstance() {
        List<CategoryAPI> user = null;
        String category = Preferences.getData(Preferences.CATEGORY, "");
        if (category != null) {
            user = parseJson(category);
        }
        return user;
    }

}


