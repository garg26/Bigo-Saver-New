package com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantoffer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import simplifii.framework.utility.Util;

/**
 * Created by Dhillon on 22-11-2016.
 */
public class In_plan implements Cloneable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("is_active")
    @Expose
    private Boolean is_active;
    @SerializedName("merchant")
    @Expose
    private String merchant;
    @SerializedName("merchant_name")
    @Expose
    private String merchant_name;
    @SerializedName("merchant_logo")
    @Expose
    private String merchant_logo;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("savings")
    @Expose
    private String savings;
    @SerializedName("start_date")
    @Expose
    private String start_date;
    @SerializedName("end_date")
    @Expose
    private String end_date;
    @SerializedName("blackout_date")
    @Expose
    private String blackout_date;
    @SerializedName("need_activate_bigo")
    @Expose
    private Boolean need_activate_bigo;
    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions = new ArrayList<Transaction>();

    @SerializedName("is_blackout_day")
    @Expose
    private Boolean is_blackout_day;
    @SerializedName("default_number_offer")
    @Expose
    private Integer default_number_offer;
    @SerializedName("gold_number_offer")
    @Expose
    private Integer gold_number_offer;
    @SerializedName("platinum_number_offer")
    @Expose
    private Integer platinum_number_offer;
    @SerializedName("signature_number_offer")
    @Expose
    private Integer signature_number_offer;
    @SerializedName("offer_type")
    @Expose
    private String offer_type;
    @SerializedName("offer_option")
    @Expose
    private String offer_option;
    @SerializedName("merchant_category_mobile_icon")
    @Expose
    private String merchant_category_mobile_icon;
    @SerializedName("custom_terms")
    @Expose
    private String custom_terms;
    @SerializedName("partner_id")
    @Expose
    private String partner_id;
    @SerializedName("franchise_id")
    @Expose
    private String franchise_id;
    @SerializedName("partner_name")
    @Expose
    private String partner_name;
    @SerializedName("franchise_name")
    @Expose
    private String franchise_name;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("merchant_category_icon")
    @Expose
    private String merchant_category_icon;
    @SerializedName("is_default_limit_exceeded")
    @Expose
    private Boolean is_default_limit_exceeded;

    public Boolean getIs_default_limit_exceeded() {
        return is_default_limit_exceeded;
    }

    public void setIs_default_limit_exceeded(Boolean is_default_limit_exceeded) {
        this.is_default_limit_exceeded = is_default_limit_exceeded;
    }

    private Boolean isRedeemed = false;

    private String redeemptionDate="";

    public String getRedeemptionDate() {
        return redeemptionDate;
    }

    public void setRedeemptionDate(String redeemptionDate) {
        this.redeemptionDate = redeemptionDate;
    }

    public Boolean getRedeemed() {
        return isRedeemed;
    }

    public void setRedeemed(Boolean redeemed) {
        isRedeemed = redeemed;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getNeed_activate_bigo() {
        return need_activate_bigo;
    }

    public void setNeed_activate_bigo(Boolean need_activate_bigo) {
        this.need_activate_bigo = need_activate_bigo;
    }

    public Boolean getIs_blackout_day() {
        return is_blackout_day;
    }

    public void setIs_blackout_day(Boolean is_blackout_day) {
        this.is_blackout_day = is_blackout_day;
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
     * @return The creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator The creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @return The is_active
     */
    public Boolean getIs_active() {
        return is_active;
    }

    /**
     * @param is_active The is_active
     */
    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    /**
     * @return The merchant
     */
    public String getMerchant() {
        return merchant;
    }

    /**
     * @param merchant The merchant
     */
    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    /**
     * @return The merchant_name
     */
    public String getMerchant_name() {
        return merchant_name;
    }

    /**
     * @param merchant_name The merchant_name
     */
    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    /**
     * @return The merchant_logo
     */
    public String getMerchant_logo() {
        return merchant_logo;
    }

    /**
     * @param merchant_logo The merchant_logo
     */
    public void setMerchant_logo(String merchant_logo) {
        this.merchant_logo = merchant_logo;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The subtitle
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * @param subtitle The subtitle
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The savings
     */
    public String getSavings() {
        return savings;
    }

    /**
     * @param savings The savings
     */
    public void setSavings(String savings) {
        this.savings = savings;
    }

    /**
     * @return The start_date
     */
    public String getStart_date() {
        return start_date;
    }

    /**
     * @param start_date The start_date
     */
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    /**
     * @return The end_date
     */
    public String getEnd_date() {
        return end_date;
    }

    /**
     * @param end_date The end_date
     */
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    /**
     * @return The blackout_date
     */
    public String getBlackout_date() {
        return blackout_date;
    }

    /**
     * @param blackout_date The blackout_date
     */
    public void setBlackout_date(String blackout_date) {
        this.blackout_date = blackout_date;
    }

    /**
     * @return The default_number_offer
     */
    public Integer getDefault_number_offer() {
        return default_number_offer;
    }

    /**
     * @param default_number_offer The default_number_offer
     */
    public void setDefault_number_offer(Integer default_number_offer) {
        this.default_number_offer = default_number_offer;
    }

    /**
     * @return The gold_number_offer
     */
    public Integer getGold_number_offer() {
        return gold_number_offer;
    }

    /**
     * @param gold_number_offer The gold_number_offer
     */
    public void setGold_number_offer(Integer gold_number_offer) {
        this.gold_number_offer = gold_number_offer;
    }

    /**
     * @return The platinum_number_offer
     */
    public Integer getPlatinum_number_offer() {
        return platinum_number_offer;
    }

    /**
     * @param platinum_number_offer The platinum_number_offer
     */
    public void setPlatinum_number_offer(Integer platinum_number_offer) {
        this.platinum_number_offer = platinum_number_offer;
    }

    /**
     * @return The signature_number_offer
     */
    public Integer getSignature_number_offer() {
        return signature_number_offer;
    }

    /**
     * @param signature_number_offer The signature_number_offer
     */
    public void setSignature_number_offer(Integer signature_number_offer) {
        this.signature_number_offer = signature_number_offer;
    }

    /**
     * @return The offer_type
     */
    public String getOffer_type() {
        return offer_type;
    }

    /**
     * @param offer_type The offer_type
     */
    public void setOffer_type(String offer_type) {
        this.offer_type = offer_type;
    }

    /**
     * @return The offer_option
     */
    public String getOffer_option() {
        return offer_option;
    }

    /**
     * @param offer_option The offer_option
     */
    public void setOffer_option(String offer_option) {
        this.offer_option = offer_option;
    }

    /**
     * @return The merchant_category_mobile_icon
     */
    public String getMerchant_category_mobile_icon() {
        return merchant_category_mobile_icon;
    }

    /**
     * @param merchant_category_mobile_icon The merchant_category_mobile_icon
     */
    public void setMerchant_category_mobile_icon(String merchant_category_mobile_icon) {
        this.merchant_category_mobile_icon = merchant_category_mobile_icon;
    }

    /**
     * @return The custom_terms
     */
    public String getCustom_terms() {
        return custom_terms;
    }

    /**
     * @param custom_terms The custom_terms
     */
    public void setCustom_terms(String custom_terms) {
        this.custom_terms = custom_terms;
    }

    /**
     * @return The partner_id
     */
    public String getPartner_id() {
        return partner_id;
    }

    /**
     * @param partner_id The partner_id
     */
    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    /**
     * @return The franchise_id
     */
    public String getFranchise_id() {
        return franchise_id;
    }

    /**
     * @param franchise_id The franchise_id
     */
    public void setFranchise_id(String franchise_id) {
        this.franchise_id = franchise_id;
    }

    /**
     * @return The partner_name
     */
    public String getPartner_name() {
        return partner_name;
    }

    /**
     * @param partner_name The partner_name
     */
    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    /**
     * @return The franchise_name
     */
    public String getFranchise_name() {
        return franchise_name;
    }

    /**
     * @param franchise_name The franchise_name
     */
    public void setFranchise_name(String franchise_name) {
        this.franchise_name = franchise_name;
    }

    /**
     * @return The area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area The area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return The merchant_category_icon
     */
    public String getMerchant_category_icon() {
        return merchant_category_icon;
    }

    /**
     * @param merchant_category_icon The merchant_category_icon
     */
    public void setMerchant_category_icon(String merchant_category_icon) {
        this.merchant_category_icon = merchant_category_icon;
    }

    /**
     * @return The transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * @param transactions The transactions
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public In_plan clone(){
        try {
            return (In_plan)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
