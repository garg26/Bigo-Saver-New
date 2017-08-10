package com.bigosaver.neerajyadav.bigosaver.model.offers;

import android.text.TextUtils;

import com.bigosaver.neerajyadav.bigosaver.model.commonmodels.Filters;
import com.bigosaver.neerajyadav.bigosaver.model.commonmodels.Image;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilterOfferResponse implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private Object address2;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("area_name")
    @Expose
    private String area_name;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("recommendation")
    @Expose
    private String recommendation;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("category_name")
    @Expose
    private String category_name;
    @SerializedName("phone_outlet")
    @Expose
    private String phone_outlet;
    @SerializedName("is_popular")
    @Expose
    private Boolean is_popular;
    @SerializedName("block_type")
    @Expose
    private Object block_type;
    @SerializedName("block_image")
    @Expose
    private String block_image;
    @SerializedName("place_type")
    @Expose
    private Object place_type;
    @SerializedName("place")
    @Expose
    private Object place;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("is_active")
    @Expose
    private Boolean is_active;
    @SerializedName("place_type_name")
    @Expose
    private String place_type_name;
    @SerializedName("place_name")
    @Expose
    private String place_name;
    @SerializedName("city_name")
    @Expose
    private String city_name;
    @SerializedName("filters")
    @Expose
    private Filters filters;
    @SerializedName("is_feature")
    @Expose
    private Boolean is_feature;
    @SerializedName("has_new_offer")
    @Expose
    private Boolean has_new_offer;
    @SerializedName("has_monthly_offer")
    @Expose
    private Boolean has_monthly_offer;
    @SerializedName("has_bigo_offer")
    @Expose
    private Boolean has_bigo_offer;
    @SerializedName("total_savings")
    @Expose
    private Integer total_savings;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("place_type_image")
    @Expose
    private String place_type_image;
    @SerializedName("gold_total_savings")
    @Expose
    private Integer gold_total_savings;
    @SerializedName("platinum_total_savings")
    @Expose
    private Integer platinum_total_savings;
    @SerializedName("signature_total_savings")
    @Expose
    private Integer signature_total_savings;
    @SerializedName("default_total_savings")
    @Expose
    private Integer default_total_savings;
    @SerializedName("default_offers")
    @Expose
    private Integer default_offers;
    @SerializedName("gold_offers")
    @Expose
    private Integer gold_offers;
    @SerializedName("platinum_offers")
    @Expose
    private Integer platinum_offers;
    @SerializedName("signature_offers")
    @Expose
    private Integer signature_offers;
    @SerializedName("default_total_offers")
    @Expose
    private Integer default_total_offers;
    @SerializedName("gold_total_offers")
    @Expose
    private Integer gold_total_offers;
    @SerializedName("platinum_total_offers")
    @Expose
    private Integer platinum_total_offers;
    @SerializedName("signature_total_offers")
    @Expose
    private Integer signature_total_offers;
    @SerializedName("is_favorite")
    @Expose
    private Boolean is_favorite;
    @SerializedName("one_line")
    @Expose
    private String one_line;
    @SerializedName("offer_viewed")
    @Expose
    private Integer offer_viewed;
    @SerializedName("opening_time")
    @Expose
    private Object opening_time;
    @SerializedName("closing_time")
    @Expose
    private Object closing_time;
    @SerializedName("offer_type")
    @Expose
    private List<String> offer_type = null;
    @SerializedName("timings")
    @Expose
    private String timings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public Object getAddress2() {
        return address2;
    }

    public void setAddress2(Object address2) {
        this.address2 = address2;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_name() {
        if(area_name==null){
            return "";
        }
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getPhone_outlet() {
        return phone_outlet;
    }

    public void setPhone_outlet(String phone_outlet) {
        this.phone_outlet = phone_outlet;
    }

    public Boolean getIs_popular() {
        return is_popular;
    }

    public void setIs_popular(Boolean is_popular) {
        this.is_popular = is_popular;
    }

    public Object getBlock_type() {
        return block_type;
    }

    public void setBlock_type(Object block_type) {
        this.block_type = block_type;
    }

    public String getBlock_image() {
        return block_image;
    }

    public void setBlock_image(String block_image) {
        this.block_image = block_image;
    }

    public Object getPlace_type() {
        return place_type;
    }

    public void setPlace_type(Object place_type) {
        this.place_type = place_type;
    }

    public Object getPlace() {
        return place;
    }

    public void setPlace(Object place) {
        this.place = place;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public String getPlace_type_name() {
        return place_type_name;
    }

    public void setPlace_type_name(String place_type_name) {
        this.place_type_name = place_type_name;
    }

    public String getPlace_name() {
        if(place_name==null){
            return "";
        }
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public Boolean getIs_feature() {
        return is_feature;
    }

    public void setIs_feature(Boolean is_feature) {
        this.is_feature = is_feature;
    }

    public Boolean getHas_new_offer() {
        return has_new_offer;
    }

    public void setHas_new_offer(Boolean has_new_offer) {
        this.has_new_offer = has_new_offer;
    }

    public Boolean getHas_monthly_offer() {
        return has_monthly_offer;
    }

    public void setHas_monthly_offer(Boolean has_monthly_offer) {
        this.has_monthly_offer = has_monthly_offer;
    }

    public Boolean getHas_bigo_offer() {
        return has_bigo_offer;
    }

    public void setHas_bigo_offer(Boolean has_bigo_offer) {
        this.has_bigo_offer = has_bigo_offer;
    }

    public Integer getTotal_savings() {
        return total_savings;
    }

    public void setTotal_savings(Integer total_savings) {
        this.total_savings = total_savings;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getPlace_type_image() {
        return place_type_image;
    }

    public void setPlace_type_image(String place_type_image) {
        this.place_type_image = place_type_image;
    }

    public Integer getGold_total_savings() {
        return gold_total_savings;
    }

    public void setGold_total_savings(Integer gold_total_savings) {
        this.gold_total_savings = gold_total_savings;
    }

    public Integer getPlatinum_total_savings() {
        return platinum_total_savings;
    }

    public void setPlatinum_total_savings(Integer platinum_total_savings) {
        this.platinum_total_savings = platinum_total_savings;
    }

    public Integer getSignature_total_savings() {
        return signature_total_savings;
    }

    public void setSignature_total_savings(Integer signature_total_savings) {
        this.signature_total_savings = signature_total_savings;
    }

    public Integer getDefault_total_savings() {
        return default_total_savings;
    }

    public void setDefault_total_savings(Integer default_total_savings) {
        this.default_total_savings = default_total_savings;
    }

    public Integer getDefault_offers() {
        return default_offers;
    }

    public void setDefault_offers(Integer default_offers) {
        this.default_offers = default_offers;
    }

    public Integer getGold_offers() {
        return gold_offers;
    }

    public void setGold_offers(Integer gold_offers) {
        this.gold_offers = gold_offers;
    }

    public Integer getPlatinum_offers() {
        return platinum_offers;
    }

    public void setPlatinum_offers(Integer platinum_offers) {
        this.platinum_offers = platinum_offers;
    }

    public Integer getSignature_offers() {
        return signature_offers;
    }

    public void setSignature_offers(Integer signature_offers) {
        this.signature_offers = signature_offers;
    }

    public Integer getDefault_total_offers() {
        return default_total_offers;
    }

    public void setDefault_total_offers(Integer default_total_offers) {
        this.default_total_offers = default_total_offers;
    }

    public Integer getGold_total_offers() {
        return gold_total_offers;
    }

    public void setGold_total_offers(Integer gold_total_offers) {
        this.gold_total_offers = gold_total_offers;
    }

    public Integer getPlatinum_total_offers() {
        return platinum_total_offers;
    }

    public void setPlatinum_total_offers(Integer platinum_total_offers) {
        this.platinum_total_offers = platinum_total_offers;
    }

    public Integer getSignature_total_offers() {
        return signature_total_offers;
    }

    public void setSignature_total_offers(Integer signature_total_offers) {
        this.signature_total_offers = signature_total_offers;
    }

    public Boolean getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(Boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

    public String getOne_line() {
        return one_line;
    }

    public void setOne_line(String one_line) {
        this.one_line = one_line;
    }

    public Integer getOffer_viewed() {
        return offer_viewed;
    }

    public void setOffer_viewed(Integer offer_viewed) {
        this.offer_viewed = offer_viewed;
    }

    public Object getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(Object opening_time) {
        this.opening_time = opening_time;
    }

    public Object getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(Object closing_time) {
        this.closing_time = closing_time;
    }

    public List<String> getOffer_type() {
        return offer_type;
    }

    public void setOffer_type(List<String> offer_type) {
        this.offer_type = offer_type;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public static List<FilterOfferResponse> parseJson(String json) throws JSONException {
        List<FilterOfferResponse> filterOfferResponseList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            Gson gson = new Gson();
            FilterOfferResponse filterOfferResponse = gson.fromJson(jsonObject.toString(), FilterOfferResponse.class);
            filterOfferResponseList.add(filterOfferResponse);
        }
        return filterOfferResponseList;
    }

    public String getPlaceAndArea() {
        if(!TextUtils.isEmpty(place_name) && !TextUtils.isEmpty(area_name)){
            return getPlace_name()+", "+getArea_name();
        }else
        return getPlace_name()+getArea_name();
    }
}
