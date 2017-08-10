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
 * Created by Dhillon on 18-10-2016.
 */
public class Tab implements Serializable{

    @SerializedName("designType")
    @Expose
    private Integer designType;
    @SerializedName("endpoint")
    @Expose
    private String endpoint;
    @SerializedName("order")
    @Expose
    private long order;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     *
     * @return
     * The designType
     */
    public Integer getDesignType() {
        return designType;
    }

    /**
     *
     * @param designType
     * The designType
     */
    public void setDesignType(Integer designType) {
        this.designType = designType;
    }

    /**
     *
     * @return
     * The endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     *
     * @param endpoint
     * The endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     *
     * @return
     * The order
     */
    public long getOrder() {
        return order;
    }

    /**
     *
     * @param order
     * The order
     */
    public void setOrder(long order) {
        this.order = order;
    }

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

    public static List<CategoryAPI> parseJson(String json) throws JSONException {
        JSONArray array = new JSONArray(json);
//        JSONObject jsonObject = array.getJSONObject(array.length());
//        String id = jsonObject.getString("id");

        List<CategoryAPI> apiList = new ArrayList<>();
        for(int i=0; i<array.length();i++){
            CategoryAPI api = (CategoryAPI) JsonUtil.parseJson(array.getJSONObject(i).toString(), CategoryAPI.class);
            apiList.add(api);
        }
        return apiList;
    }

}
