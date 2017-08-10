package com.bigosaver.neerajyadav.bigosaver.fragment;

import android.support.v4.app.Fragment;

import com.bigosavers.R;

import simplifii.framework.fragments.BaseFragment;

/**
 * Created by Dhillon on 07-12-2016.
 */

public class WhatsOnFragment extends BaseFragment {
    @Override
    public void initViews() {

    }

    @Override
    public int getViewID() {
        return R.layout.fragment_whats_on;
    }

    public static Fragment getInstance() {
        return new WhatsOnFragment();
    }
}
