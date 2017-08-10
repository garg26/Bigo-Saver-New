package com.bigosaver.neerajyadav.bigosaver.model;

import com.bigosaver.neerajyadav.bigosaver.model.User.UpdateProfileResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.JsonUtil;
import simplifii.framework.utility.Preferences;

/**
 * Created by Neeraj Yadav on 10/6/2016.
 */

public class UserApi extends BaseModel {


    private static UserApi runningInstance;

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
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("dob")
    @Expose
    private String dob="";
    @SerializedName("gender")
    @Expose
    private String gender="";
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("city_name")
    @Expose
    private String city_name;

    private String profileId;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The first_name
     */
    public Object getFirst_name() {
        return first_name;
    }

    /**
     *
     * @param first_name
     * The first_name
     */
    public void setFirst_name(Object first_name) {
        this.first_name = (String) first_name;
    }

    /**
     *
     * @return
     * The last_name
     */
    public Object getLast_name() {
        return last_name;
    }

    /**
     *
     * @param last_name
     * The last_name
     */
    public void setLast_name(Object last_name) {
        this.last_name = (String) last_name;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The country
     */
    public Object getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(Object country) {
        this.country = (String) country;
    }

    /**
     *
     * @return
     * The dob
     */
    public Object getDob() {
        return dob;
    }

    /**
     *
     * @param dob
     * The dob
     */
    public void setDob(Object dob) {
        this.dob = (String) dob;
    }

    /**
     *
     * @return
     * The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(Object gender) {
        this.gender = (String) gender;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The city
     */
    public Object getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(Object city) {
        this.city = (String) city;
    }

    /**
     *
     * @return
     * The city_name
     */
    public String getCity_name() {
        return city_name;
    }

    /**
     *
     * @param city_name
     * The city_name
     */
    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public static UserApi parseJson(String json) {
        runningInstance = (UserApi) JsonUtil.parseJson(json, UserApi.class);
        if (runningInstance != null) {
            Preferences.saveData(AppConstants.PREF_KEYS.PROFILE, json);
        }else{
            Preferences.removeData(AppConstants.PREF_KEYS.PROFILE);
        }
        return runningInstance;
    }

    public static UserApi getRunningInstance(){
        if(runningInstance == null){
            String json = Preferences.getData(AppConstants.PREF_KEYS.PROFILE,"");
            runningInstance = (UserApi) JsonUtil.parseJson(json, UserApi.class);
        }
        return runningInstance;
    }

}