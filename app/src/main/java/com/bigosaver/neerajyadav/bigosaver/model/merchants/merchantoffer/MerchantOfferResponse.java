package com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantoffer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;
import java.util.List;


public class MerchantOfferResponse {

    @SerializedName("not_in_plan")
    @Expose
    private List<Not_In_Plan> not_in_plan = new ArrayList<Not_In_Plan>();
    @SerializedName("in_plan")
    @Expose
    private List<In_plan> in_plan = new ArrayList<In_plan>();

    /**
     * @return The not_in_plan
     */
    public List<Not_In_Plan> getNot_in_plan() {
        return not_in_plan;
    }

    /**
     * @param not_in_plan The not_in_plan
     */
    public void setNot_in_plan(List<Not_In_Plan> not_in_plan) {
        this.not_in_plan = not_in_plan;
    }

    /**
     * @return The in_plan
     */
    public List<In_plan> getIn_plan() {
        return in_plan;
    }

    /**
     * @param in_plan The in_plan
     */
    public void setIn_plan(List<In_plan> in_plan) {
        this.in_plan = in_plan;
    }

}