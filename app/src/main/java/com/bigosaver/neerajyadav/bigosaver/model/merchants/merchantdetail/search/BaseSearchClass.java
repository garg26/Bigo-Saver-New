package com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhillon on 14-11-2016.
 */

public class BaseSearchClass {
    protected Boolean isFirst = false;

    protected int type;

    public Boolean getFirst() {
        return isFirst;
    }

    public void setFirst(Boolean first) {
        isFirst = first;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
