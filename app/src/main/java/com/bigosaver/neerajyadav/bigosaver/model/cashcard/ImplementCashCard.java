package com.bigosaver.neerajyadav.bigosaver.model.cashcard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImplementCashCard {

    @SerializedName("membership_type")
    @Expose
    private String membership_type;
    @SerializedName("membership_expiry_date")
    @Expose
    private String membership_expiry_date;

    /**
     * @return The membership_type
     */
    public String getMembership_type() {
        return membership_type;
    }

    /**
     * @param membership_type The membership_type
     */
    public void setMembership_type(String membership_type) {
        this.membership_type = membership_type;
    }

    /**
     * @return The membership_expiry_date
     */
    public String getMembership_expiry_date() {
        return membership_expiry_date;
    }

    /**
     * @param membership_expiry_date The membership_expiry_date
     */
    public void setMembership_expiry_date(String membership_expiry_date) {
        this.membership_expiry_date = membership_expiry_date;
    }

}