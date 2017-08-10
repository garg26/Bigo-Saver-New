package com.bigosaver.neerajyadav.bigosaver.model;

import java.util.List;

/**
 * Created by Neeraj Yadav on 9/29/2016.
 */
public class Cart {
    String tvTitle, tvDesc;
    int ivMedal;
    List<Offer> offerList;

    public List<Offer> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getTvDesc() {
        return tvDesc;
    }

    public void setTvDesc(String tvDesc) {
        this.tvDesc = tvDesc;
    }

    public int getIvMedal() {
        return ivMedal;
    }

    public void setIvMedal(int ivMedal) {
        this.ivMedal = ivMedal;
    }
}
