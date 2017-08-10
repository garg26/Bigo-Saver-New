package com.bigosaver.neerajyadav.bigosaver.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EulaResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("eula")
    @Expose
    private String eula;
    @SerializedName("about_us")
    @Expose
    private String about_us;
    @SerializedName("term_of_use")
    @Expose
    private String term_of_use;
    @SerializedName("privacy_policy")
    @Expose
    private String privacy_policy;
    @SerializedName("rules_and_guidelines")
    @Expose
    private String rules_and_guidelines;

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
     * @return The eula
     */
    public String getEula() {
        return eula;
    }

    /**
     * @param eula The eula
     */
    public void setEula(String eula) {
        this.eula = eula;
    }

    /**
     * @return The about_us
     */
    public String getAbout_us() {
        return about_us;
    }

    /**
     * @param about_us The about_us
     */
    public void setAbout_us(String about_us) {
        this.about_us = about_us;
    }

    /**
     * @return The term_of_use
     */
    public String getTerm_of_use() {
        return term_of_use;
    }

    /**
     * @param term_of_use The term_of_use
     */
    public void setTerm_of_use(String term_of_use) {
        this.term_of_use = term_of_use;
    }

    /**
     * @return The privacy_policy
     */
    public String getPrivacy_policy() {
        return privacy_policy;
    }

    /**
     * @param privacy_policy The privacy_policy
     */
    public void setPrivacy_policy(String privacy_policy) {
        this.privacy_policy = privacy_policy;
    }

    /**
     * @return The rules_and_guidelines
     */
    public String getRules_and_guidelines() {
        return rules_and_guidelines;
    }

    /**
     * @param rules_and_guidelines The rules_and_guidelines
     */
    public void setRules_and_guidelines(String rules_and_guidelines) {
        this.rules_and_guidelines = rules_and_guidelines;
    }

}