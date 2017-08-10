package com.bigosaver.neerajyadav.bigosaver.fragment;

import android.widget.ImageView;

import com.bigosavers.R;
import com.squareup.picasso.Picasso;

import simplifii.framework.fragments.BaseFragment;

/**
 * Created by Neeraj Yadav on 9/29/2016.
 */

public class ImageFragment extends BaseFragment {
    String imageUrl;
    public static ImageFragment getInstance(String imageUrl) {
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.imageUrl = imageUrl;
        return imageFragment;
    }
    @Override
    public void initViews() {
        ImageView iv_slide = (ImageView) findView(R.id.iv_slid);
        Picasso.with(getActivity()).load(imageUrl).into(iv_slide);
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_image;
    }
}
