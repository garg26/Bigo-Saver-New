package com.bigosaver.neerajyadav.bigosaver.model.cashcard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CashCardResponse {

    @SerializedName("currrent_plan")
    @Expose
    private String currrent_plan;
    @SerializedName("is_bigo")
    @Expose
    private Boolean is_bigo;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("new_plan")
    @Expose
    private String new_plan;
    @SerializedName("validity")
    @Expose
    private String validity;
    @SerializedName("status")
    @Expose
    private Boolean status = true;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     * The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The currrent_plan
     */
    public String getCurrrent_plan() {
        return currrent_plan;
    }

    /**
     * @param currrent_plan The currrent_plan
     */
    public void setCurrrent_plan(String currrent_plan) {
        this.currrent_plan = currrent_plan;
    }

    /**
     * @return The is_bigo
     */
    public Boolean getIs_bigo() {
        return is_bigo;
    }

    /**
     * @param is_bigo The is_bigo
     */
    public void setIs_bigo(Boolean is_bigo) {
        this.is_bigo = is_bigo;
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
     * @return The new_plan
     */
    public String getNew_plan() {
        return new_plan;
    }

    /**
     * @param new_plan The new_plan
     */
    public void setNew_plan(String new_plan) {
        this.new_plan = new_plan;
    }

    /**
     * @return The validity
     */
    public String getValidity() {
        return validity;
    }

    /**
     * @param validity The validity
     */
    public void setValidity(String validity) {
        this.validity = validity;
    }

}