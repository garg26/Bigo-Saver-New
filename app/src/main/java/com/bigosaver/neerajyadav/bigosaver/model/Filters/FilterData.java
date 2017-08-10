package com.bigosaver.neerajyadav.bigosaver.model.Filters;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.plumillonforge.android.chipview.Chip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhillon on 19-10-2016.
 */

public class FilterData implements Chip {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("filter_values")
    @Expose
    private List<FilterItems> filter_values = new ArrayList<FilterItems>();
    @SerializedName("category_name")
    @Expose
    private String category_name;
    @SerializedName("filter_name")
    @Expose
    private String filter_name;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("category")
    @Expose
    private String category;
    Boolean isOpened = false;

    public Boolean getOpened() {
        return isOpened;
    }

    public void setOpened(Boolean opened) {
        isOpened = opened;
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
     * The filter_values
     */
    public List<FilterItems> getFilter_values() {
        return filter_values;
    }

    /**
     *
     * @param filter_values
     * The filter_values
     */
    public void setFilter_values(List<FilterItems> filter_values) {
        this.filter_values = filter_values;
    }

    /**
     *
     * @return
     * The category_name
     */
    public String getCategory_name() {
        return category_name;
    }

    /**
     *
     * @param category_name
     * The category_name
     */
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    /**
     *
     * @return
     * The filter_name
     */
    public String getFilter_name() {
        return filter_name;
    }

    /**
     *
     * @param filter_name
     * The filter_name
     */
    public void setFilter_name(String filter_name) {
        this.filter_name = filter_name;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     *
     * @param subtitle
     * The subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The category
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public static Object parseJson(String json) {
        Log.d("parse category", "parse json call");
        List<FilterData> filterItem = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Gson gson = new Gson();
                FilterData filter = gson.fromJson(jsonObject.toString(), FilterData.class);
                filterItem.add(filter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return filterItem;
    }


    @Override
    public String getText() {
        return null;
    }
}