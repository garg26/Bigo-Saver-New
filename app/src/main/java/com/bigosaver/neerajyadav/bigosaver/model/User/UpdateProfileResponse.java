
package com.bigosaver.neerajyadav.bigosaver.model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.JsonUtil;
import simplifii.framework.utility.Preferences;

public class UpdateProfileResponse {

    private static UpdateProfileResponse runningInstance;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("first_name")
    @Expose
    private String first_name;
    @SerializedName("last_name")
    @Expose
    private String last_name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("about_me")
    @Expose
    private String about_me;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("city_name")
    @Expose
    private String city_name;
    @SerializedName("membership_type")
    @Expose
    private String membership_type;
    @SerializedName("bigo_drink")
    @Expose
    private Boolean bigo_drink;
    @SerializedName("membership_source")
    @Expose
    private Membership_source membership_source;
    @SerializedName("registered_date")
    @Expose
    private String registered_date;
    @SerializedName("total_redemption")
    @Expose
    private Integer total_redemption;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("savings")
    @Expose
    private Integer savings;
    @SerializedName("merchant_visited")
    @Expose
    private Integer merchant_visited;
    @SerializedName("membership_expired_at")
    @Expose
    private String membership_expired_at;
    @SerializedName("membership_start_at")
    @Expose
    private String membership_start_at;

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
     * @return The first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name The first_name
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return The last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name The last_name
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
     * @return The about_me
     */
    public String getAbout_me() {
        return about_me;
    }

    /**
     * @param about_me The about_me
     */
    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    /**
     * @return The nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality The nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * @return The dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @param dob The dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * @return The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
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

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The city_name
     */
    public String getCity_name() {
        return city_name;
    }

    /**
     * @param city_name The city_name
     */
    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

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
     * @return The bigo_drink
     */
    public Boolean getBigo_drink() {
        return bigo_drink;
    }

    /**
     * @param bigo_drink The bigo_drink
     */
    public void setBigo_drink(Boolean bigo_drink) {
        this.bigo_drink = bigo_drink;
    }

    /**
     * @return The membership_source
     */
    public Membership_source getMembership_source() {
        return membership_source;
    }

    /**
     * @param membership_source The membership_source
     */
    public void setMembership_source(Membership_source membership_source) {
        this.membership_source = membership_source;
    }

    /**
     * @return The registered_date
     */
    public String getRegistered_date() {
        return registered_date;
    }

    /**
     * @param registered_date The registered_date
     */
    public void setRegistered_date(String registered_date) {
        this.registered_date = registered_date;
    }

    /**
     * @return The total_redemption
     */
    public Integer getTotal_redemption() {
        return total_redemption;
    }

    /**
     * @param total_redemption The total_redemption
     */
    public void setTotal_redemption(Integer total_redemption) {
        this.total_redemption = total_redemption;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
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

    /**
     * @return The merchant_visited
     */
    public Integer getMerchant_visited() {
        return merchant_visited;
    }

    /**
     * @param merchant_visited The merchant_visited
     */
    public void setMerchant_visited(Integer merchant_visited) {
        this.merchant_visited = merchant_visited;
    }

    /**
     * @return The membership_expired_at
     */
    public String getMembership_expired_at() {
        return membership_expired_at;
    }

    /**
     * @param membership_expired_at The membership_expired_at
     */
    public void setMembership_expired_at(String membership_expired_at) {
        this.membership_expired_at = membership_expired_at;
    }

    /**
     * @return The membership_start_at
     */
    public String getMembership_start_at() {
        return membership_start_at;
    }

    /**
     * @param membership_start_at The membership_start_at
     */
    public void setMembership_start_at(String membership_start_at) {
        this.membership_start_at = membership_start_at;
    }


    public static UpdateProfileResponse parseJson(String json) {
        runningInstance = (UpdateProfileResponse) JsonUtil.parseJson(json, UpdateProfileResponse.class);
        if (runningInstance != null) {
            Preferences.saveData(AppConstants.PREF_KEYS.PROFILE, json);
        } else {
            Preferences.removeData(AppConstants.PREF_KEYS.PROFILE);
        }
        return runningInstance;
    }

    public static UpdateProfileResponse getRunningInstance() {
        if (runningInstance == null) {
            String json = Preferences.getData(AppConstants.PREF_KEYS.PROFILE, "");
            runningInstance = (UpdateProfileResponse) JsonUtil.parseJson(json, UpdateProfileResponse.class);
        }
        return runningInstance;
    }

}