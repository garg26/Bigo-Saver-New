package com.bigosaver.neerajyadav.bigosaver.model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dhillon on 22-11-2016.
 */

public class Membership_source {

    @SerializedName("amount")
    @Expose
    protected Double amount;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * @return The amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount The amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return The code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }
}
