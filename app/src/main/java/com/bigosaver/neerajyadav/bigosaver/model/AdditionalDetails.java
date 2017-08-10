package com.bigosaver.neerajyadav.bigosaver.model;

import java.util.List;

/**
 * Created by my on 29-09-2016.
 */
public class AdditionalDetails {
    int ivLogo, ivNo, ivYes;
    String tvFilter,tv_subtitle;
    List<AllOffers> offerList;

    public String getTv_subtitle() {
        return tv_subtitle;
    }

    public void setTv_subtitle(String tv_subtitle) {
        this.tv_subtitle = tv_subtitle;
    }


    public List<AllOffers> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<AllOffers> offerList) {
        this.offerList = offerList;
    }

    public int getIvLogo() {
        return ivLogo;
    }

    public void setIvLogo(int ivLogo) {
        this.ivLogo = ivLogo;
    }

    public int getIvNo() {
        return ivNo;
    }

    public void setIvNo(int ivNo) {
        this.ivNo = ivNo;
    }

    public int getIvYes() {
        return ivYes;
    }

    public void setIvYes(int ivYes) {
        this.ivYes = ivYes;
    }

    public String getTvFilter() {
        return tvFilter;
    }

    public void setTvFilter(String tvFilter) {
        this.tvFilter = tvFilter;
    }
}
