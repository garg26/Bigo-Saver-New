package com.bigosaver.neerajyadav.bigosaver.model;

/**
 * Created by Neeraj Yadav on 9/29/2016.
 */

public class Notification {
    int iv_back, iv_logo1, iv_logo2;
    String tvTitle, tvDescription;

    public int getIv_back() {
        return iv_back;
    }

    public void setIv_back(int iv_back) {
        this.iv_back = iv_back;
    }

    public int getIv_logo1() {
        return iv_logo1;
    }

    public void setIv_logo1(int iv_logo1) {
        this.iv_logo1 = iv_logo1;
    }

    public int getIv_logo2() {
        return iv_logo2;
    }

    public void setIv_logo2(int iv_logo2) {
        this.iv_logo2 = iv_logo2;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getTvDescription() {
        return tvDescription;
    }

    public void setTvDescription(String tvDescription) {
        this.tvDescription = tvDescription;
    }
}
