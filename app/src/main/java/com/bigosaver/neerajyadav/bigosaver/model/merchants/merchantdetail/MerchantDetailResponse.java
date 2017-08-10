package com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail;

import com.bigosaver.neerajyadav.bigosaver.model.commonmodels.Image;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhillon on 28-10-2016.
 */
public class MerchantDetailResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_client")
    @Expose
    private String phone_client;
    @SerializedName("phone_outlet")
    @Expose
    private String phone_outlet;
    @SerializedName("partner_id")
    @Expose
    private String partner_id;
    @SerializedName("franchise_id")
    @Expose
    private String franchise_id;
    @SerializedName("franchise_name")
    @Expose
    private String franchise_name;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
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
    @SerializedName("partner_name")
    @Expose
    private String partner_name;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("is_popular")
    @Expose
    private Boolean is_popular;
    @SerializedName("block_type")
    @Expose
    private String block_type;
    @SerializedName("block_image")
    @Expose
    private String block_image;
    @SerializedName("place_type")
    @Expose
    private String place_type;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<Image>();
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
    @SerializedName("contract_start")
    @Expose
    private String contract_start;
    @SerializedName("contract_end")
    @Expose
    private String contract_end;
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
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
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
     * @return The phone_client
     */
    public String getPhone_client() {
        return phone_client;
    }

    /**
     * @param phone_client The phone_client
     */
    public void setPhone_client(String phone_client) {
        this.phone_client = phone_client;
    }

    /**
     * @return The phone_outlet
     */
    public String getPhone_outlet() {
        return phone_outlet;
    }

    /**
     * @param phone_outlet The phone_outlet
     */
    public void setPhone_outlet(String phone_outlet) {
        this.phone_outlet = phone_outlet;
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
     * @return The address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @param address1 The address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * @return The address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2 The address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
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
     * @return The area_name
     */
    public String getArea_name() {
        return area_name;
    }

    /**
     * @param area_name The area_name
     */
    public void setArea_name(String area_name) {
        this.area_name = area_name;
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
     * @return The logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo The logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * @return The recommendation
     */
    public String getRecommendation() {
        return recommendation;
    }

    /**
     * @param recommendation The recommendation
     */
    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    /**
     * @return The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website The website
     */
    public void setWebsite(String website) {
        this.website = website;
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
     * @return The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return The category_name
     */
    public String getCategory_name() {
        return category_name;
    }

    /**
     * @param category_name The category_name
     */
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
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
     * @return The pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pin The pin
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    /**
     * @return The is_popular
     */
    public Boolean getIs_popular() {
        return is_popular;
    }

    /**
     * @param is_popular The is_popular
     */
    public void setIs_popular(Boolean is_popular) {
        this.is_popular = is_popular;
    }

    /**
     * @return The block_type
     */
    public String getBlock_type() {
        return block_type;
    }

    /**
     * @param block_type The block_type
     */
    public void setBlock_type(String block_type) {
        this.block_type = block_type;
    }

    /**
     * @return The block_image
     */
    public String getBlock_image() {
        return block_image;
    }

    /**
     * @param block_image The block_image
     */
    public void setBlock_image(String block_image) {
        this.block_image = block_image;
    }

    /**
     * @return The place_type
     */
    public String getPlace_type() {
        return place_type;
    }

    /**
     * @param place_type The place_type
     */
    public void setPlace_type(String place_type) {
        this.place_type = place_type;
    }

    /**
     * @return The place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place The place
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * @return The images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * @param images The images
     */
    public void setImages(List<Image> images) {
        this.images = images;
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
     * @return The place_type_name
     */
    public String getPlace_type_name() {
        return place_type_name;
    }

    /**
     * @param place_type_name The place_type_name
     */
    public void setPlace_type_name(String place_type_name) {
        this.place_type_name = place_type_name;
    }

    /**
     * @return The place_name
     */
    public String getPlace_name() {
        return place_name;
    }

    /**
     * @param place_name The place_name
     */
    public void setPlace_name(String place_name) {
        this.place_name = place_name;
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
     * @return The filters
     */
    public Filters getFilters() {
        return filters;
    }

    /**
     * @param filters The filters
     */
    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    /**
     * @return The is_feature
     */
    public Boolean getIs_feature() {
        return is_feature;
    }

    /**
     * @param is_feature The is_feature
     */
    public void setIs_feature(Boolean is_feature) {
        this.is_feature = is_feature;
    }

    /**
     * @return The contract_start
     */
    public String getContract_start() {
        return contract_start;
    }

    /**
     * @param contract_start The contract_start
     */
    public void setContract_start(String contract_start) {
        this.contract_start = contract_start;
    }

    /**
     * @return The contract_end
     */
    public String getContract_end() {
        return contract_end;
    }

    /**
     * @param contract_end The contract_end
     */
    public void setContract_end(String contract_end) {
        this.contract_end = contract_end;
    }

    /**
     *
     * @return
     * The has_new_offer
     */
    public Boolean getHas_new_offer() {
        return has_new_offer;
    }

    /**
     *
     * @param has_new_offer
     * The has_new_offer
     */
    public void setHas_new_offer(Boolean has_new_offer) {
        this.has_new_offer = has_new_offer;
    }

    /**
     *
     * @return
     * The has_monthly_offer
     */
    public Boolean getHas_monthly_offer() {
        return has_monthly_offer;
    }

    /**
     *
     * @param has_monthly_offer
     * The has_monthly_offer
     */
    public void setHas_monthly_offer(Boolean has_monthly_offer) {
        this.has_monthly_offer = has_monthly_offer;
    }

    /**
     *
     * @return
     * The has_bigo_offer
     */
    public Boolean getHas_bigo_offer() {
        return has_bigo_offer;
    }

    /**
     *
     * @param has_bigo_offer
     * The has_bigo_offer
     */
    public void setHas_bigo_offer(Boolean has_bigo_offer) {
        this.has_bigo_offer = has_bigo_offer;
    }

    /**
     *
     * @return
     * The total_savings
     */
    public Integer getTotal_savings() {
        return total_savings;
    }

    /**
     *
     * @param total_savings
     * The total_savings
     */
    public void setTotal_savings(Integer total_savings) {
        this.total_savings = total_savings;
    }

    /**
     *
     * @return
     * The distance
     */
    public Double getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     * The distance
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    /**
     *
     * @return
     * The place_type_image
     */
    public String getPlace_type_image() {
        return place_type_image;
    }

    /**
     *
     * @param place_type_image
     * The place_type_image
     */
    public void setPlace_type_image(String place_type_image) {
        this.place_type_image = place_type_image;
    }

    /**
     *
     * @return
     * The gold_total_savings
     */
    public Integer getGold_total_savings() {
        return gold_total_savings;
    }

    /**
     *
     * @param gold_total_savings
     * The gold_total_savings
     */
    public void setGold_total_savings(Integer gold_total_savings) {
        this.gold_total_savings = gold_total_savings;
    }

    /**
     *
     * @return
     * The platinum_total_savings
     */
    public Integer getPlatinum_total_savings() {
        return platinum_total_savings;
    }

    /**
     *
     * @param platinum_total_savings
     * The platinum_total_savings
     */
    public void setPlatinum_total_savings(Integer platinum_total_savings) {
        this.platinum_total_savings = platinum_total_savings;
    }

    /**
     *
     * @return
     * The signature_total_savings
     */
    public Integer getSignature_total_savings() {
        return signature_total_savings;
    }

    /**
     *
     * @param signature_total_savings
     * The signature_total_savings
     */
    public void setSignature_total_savings(Integer signature_total_savings) {
        this.signature_total_savings = signature_total_savings;
    }

    /**
     *
     * @return
     * The default_total_savings
     */
    public Integer getDefault_total_savings() {
        return default_total_savings;
    }

    /**
     *
     * @param default_total_savings
     * The default_total_savings
     */
    public void setDefault_total_savings(Integer default_total_savings) {
        this.default_total_savings = default_total_savings;
    }

    /**
     *
     * @return
     * The default_offers
     */
    public Integer getDefault_offers() {
        return default_offers;
    }

    /**
     *
     * @param default_offers
     * The default_offers
     */
    public void setDefault_offers(Integer default_offers) {
        this.default_offers = default_offers;
    }

    /**
     *
     * @return
     * The gold_offers
     */
    public Integer getGold_offers() {
        return gold_offers;
    }

    /**
     *
     * @param gold_offers
     * The gold_offers
     */
    public void setGold_offers(Integer gold_offers) {
        this.gold_offers = gold_offers;
    }

    /**
     *
     * @return
     * The platinum_offers
     */
    public Integer getPlatinum_offers() {
        return platinum_offers;
    }

    /**
     *
     * @param platinum_offers
     * The platinum_offers
     */
    public void setPlatinum_offers(Integer platinum_offers) {
        this.platinum_offers = platinum_offers;
    }

    /**
     *
     * @return
     * The signature_offers
     */
    public Integer getSignature_offers() {
        return signature_offers;
    }

    /**
     *
     * @param signature_offers
     * The signature_offers
     */
    public void setSignature_offers(Integer signature_offers) {
        this.signature_offers = signature_offers;
    }

    /**
     *
     * @return
     * The default_total_offers
     */
    public Integer getDefault_total_offers() {
        return default_total_offers;
    }

    /**
     *
     * @param default_total_offers
     * The default_total_offers
     */
    public void setDefault_total_offers(Integer default_total_offers) {
        this.default_total_offers = default_total_offers;
    }

    /**
     *
     * @return
     * The gold_total_offers
     */
    public Integer getGold_total_offers() {
        return gold_total_offers;
    }

    /**
     *
     * @param gold_total_offers
     * The gold_total_offers
     */
    public void setGold_total_offers(Integer gold_total_offers) {
        this.gold_total_offers = gold_total_offers;
    }

    /**
     *
     * @return
     * The platinum_total_offers
     */
    public Integer getPlatinum_total_offers() {
        return platinum_total_offers;
    }

    /**
     *
     * @param platinum_total_offers
     * The platinum_total_offers
     */
    public void setPlatinum_total_offers(Integer platinum_total_offers) {
        this.platinum_total_offers = platinum_total_offers;
    }

    /**
     *
     * @return
     * The signature_total_offers
     */
    public Integer getSignature_total_offers() {
        return signature_total_offers;
    }

    /**
     *
     * @param signature_total_offers
     * The signature_total_offers
     */
    public void setSignature_total_offers(Integer signature_total_offers) {
        this.signature_total_offers = signature_total_offers;
    }

    /**
     *
     * @return
     * The is_favorite
     */
    public Boolean getIs_favorite() {
        return is_favorite;
    }

    /**
     *
     * @param is_favorite
     * The is_favorite
     */
    public void setIs_favorite(Boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

}
