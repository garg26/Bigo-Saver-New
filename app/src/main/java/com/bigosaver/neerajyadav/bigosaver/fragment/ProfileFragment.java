package com.bigosaver.neerajyadav.bigosaver.fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;


import com.bigosavers.R;

import simplifii.framework.fragments.BaseFragment;

public class ProfileFragment extends BaseFragment {
    private TextView tv_profile, tv_account;
    private FragmentTransaction fragmentTransaction;

    public static ProfileFragment getInstance() {
        return new ProfileFragment();
    }

    @Override
    public void initViews() {
        tv_account = (TextView) findView(R.id.tv_account);
        tv_profile = (TextView) findView(R.id.tv_profile);
        MyProfileFragment profilefragment = new MyProfileFragment();
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLay_profile, profilefragment).commit();
        tv_profile.setBackgroundResource(R.drawable.shape_tv_tab);
        tv_profile.setTextColor(getResourceColor(R.color.black));
        tv_account.setBackgroundResource(R.drawable.shape_tv_button);

        setOnClickListener(R.id.tv_account, R.id.tv_profile, R.id.tv_title);
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_profile:
                MyProfileFragment profilefragment = new MyProfileFragment();
                fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLay_profile, profilefragment).commit();
                tv_profile.setBackgroundResource(R.drawable.shape_tv_tab);
                tv_profile.setTextColor(getResourceColor(R.color.black));
                tv_account.setBackgroundResource(R.drawable.shape_tv_button);
                tv_account.setTextColor(getResourceColor(R.color.color_DarkGray));
                break;
            case R.id.tv_account:
                AccountSettingFragment accountSettingFragment = new AccountSettingFragment();
                fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLay_profile, accountSettingFragment).commit();
                tv_profile.setBackgroundResource(R.drawable.shape_tv_button);
                tv_profile.setTextColor(getResourceColor(R.color.color_DarkGray));
                tv_account.setBackgroundResource(R.drawable.shape_tv_tab);
                tv_account.setTextColor(getResourceColor(R.color.black));
                break;

        }
    }
}
