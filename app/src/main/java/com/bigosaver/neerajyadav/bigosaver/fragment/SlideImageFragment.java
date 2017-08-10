package com.bigosaver.neerajyadav.bigosaver.fragment;


import android.widget.ImageView;

import com.bigosavers.R;

import simplifii.framework.fragments.BaseFragment;


public class SlideImageFragment extends BaseFragment {
    ImageView imageView;
    int slideImageresource;

    public static SlideImageFragment getInstance(int slideImageResource) {
        SlideImageFragment slideImageFragment = new SlideImageFragment();
        slideImageFragment.slideImageresource = slideImageResource;
        return slideImageFragment;
    }

    @Override
    public void initViews() {
        try {
            imageView = (ImageView) findView(R.id.iv_slider_home);
            imageView.setImageResource(slideImageresource);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_slide_image;
    }

}
