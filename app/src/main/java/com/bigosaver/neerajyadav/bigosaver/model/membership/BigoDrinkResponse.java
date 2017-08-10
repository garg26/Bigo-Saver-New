package com.bigosaver.neerajyadav.bigosaver.model.membership;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BigoDrinkResponse {

    @SerializedName("merchant_count")
    @Expose
    private int merchant_count;
    @SerializedName("platinum_offer_count")
    @Expose
    private int platinum_offer_count;
    @SerializedName("signature_offer_count")
    @Expose
    private int signature_offer_count;
    @SerializedName("gold_offer_count")
    @Expose
    private int gold_offer_count;
    @SerializedName("default_offer_count")
    @Expose
    private int default_offer_count;

    /**
     * @return The merchant_count
     */
    public int getMerchant_count() {
        return merchant_count;
    }

    /**
     * @param merchant_count The merchant_count
     */
    public void setMerchant_count(int merchant_count) {
        this.merchant_count = merchant_count;
    }

    /**
     * @return The platinum_offer_count
     */
    public int getPlatinum_offer_count() {
        return platinum_offer_count;
    }

    /**
     * @param platinum_offer_count The platinum_offer_count
     */
    public void setPlatinum_offer_count(int platinum_offer_count) {
        this.platinum_offer_count = platinum_offer_count;
    }

    /**
     * @return The signature_offer_count
     */
    public int getSignature_offer_count() {
        return signature_offer_count;
    }

    /**
     * @param signature_offer_count The signature_offer_count
     */
    public void setSignature_offer_count(int signature_offer_count) {
        this.signature_offer_count = signature_offer_count;
    }

    /**
     * @return The gold_offer_count
     */
    public int getGold_offer_count() {
        return gold_offer_count;
    }

    /**
     * @param gold_offer_count The gold_offer_count
     */
    public void setGold_offer_count(int gold_offer_count) {
        this.gold_offer_count = gold_offer_count;
    }

    /**
     * @return The default_offer_count
     */
    public int getDefault_offer_count() {
        return default_offer_count;
    }

    /**
     * @param default_offer_count The default_offer_count
     */
    public void setDefault_offer_count(int default_offer_count) {
        this.default_offer_count = default_offer_count;
    }

}