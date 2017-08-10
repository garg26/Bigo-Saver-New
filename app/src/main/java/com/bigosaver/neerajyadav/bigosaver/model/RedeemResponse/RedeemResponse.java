package com.bigosaver.neerajyadav.bigosaver.model.RedeemResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RedeemResponse {

    @SerializedName("reference_number")
    @Expose
    private String reference_number;
    @SerializedName("savings")
    @Expose
    private Integer savings;

    /**
     * @return The reference_number
     */
    public String getReference_number() {
        return reference_number;
    }

    /**
     * @param reference_number The reference_number
     */
    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }

    /**
     * @return The savings
     */
    public Integer getSavings() {
        return savings;
    }

    /**
     * @param savings The savings
     */
    public void setSavings(Integer savings) {
        this.savings = savings;
    }

}