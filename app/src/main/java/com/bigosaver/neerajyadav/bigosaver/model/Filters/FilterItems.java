package com.bigosaver.neerajyadav.bigosaver.model.Filters;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.plumillonforge.android.chipview.Chip;

/**
 * Created by Dhillon on 19-10-2016.
 */

public class FilterItems implements Chip {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("filter_name")
    @Expose
    private String filter_name;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("filter")
    @Expose
    private String filter;

    private String filterType="";

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    Boolean isSelected = false;
    Boolean isRejected = false;

    public Boolean getRejected() {
        return isRejected;
    }

    public void setRejected(Boolean rejected) {
        isRejected = rejected;
    }

    public Boolean getSelected() {

        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
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

    @Override
    public String getText() {
        if (!TextUtils.isEmpty(value))
            return value;
        return null;
    }
}