package com.bigosaver.neerajyadav.bigosaver.model.Filters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectedFilter {

    @SerializedName("filterItemId")
    @Expose
    private String filterItemId;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("filterId")
    @Expose
    private String filterId;

    /**
     * @return The filterItemId
     */
    public String getFilterItemId() {
        return filterItemId;
    }

    /**
     * @param filterItemId The filterItemId
     */
    public void setFilterItemId(String filterItemId) {
        this.filterItemId = filterItemId;
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
     * @return The filterId
     */
    public String getFilterId() {
        return filterId;
    }

    /**
     * @param filterId The filterId
     */
    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

}