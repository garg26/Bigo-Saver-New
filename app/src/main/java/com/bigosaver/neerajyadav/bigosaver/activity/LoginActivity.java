package com.bigosaver.neerajyadav.bigosaver.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.fragment.SignInFragment;
import com.bigosaver.neerajyadav.bigosaver.fragment.SignUpFragment;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomPagerAdapter;
import simplifii.framework.activity.BaseActivity;
import simplifii.framework.utility.AppConstants;

public class LoginActivity extends BaseActivity implements CustomPagerAdapter.PagerAdapterInterface, SignInFragment.PageChangeListener, SignUpFragment.PageChangeListener {

    List<String> tabLogin = new ArrayList<>();
    private SignInFragment tabSignIn;
    private int redirectedPosition;
    private ViewPager pager;
    private SignUpFragment tabSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initTab();
        pager = (ViewPager) findViewById(R.id.pager_login);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_login);
        CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager(), tabLogin, this);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.setCurrentItem(redirectedPosition);
    }

    @Override
    protected void loadBundle(Bundle bundle) {
        redirectedPosition = bundle.getInt(AppConstants.BUNDLE_KEYS.KEY_POSITION, 0);
    }

    private void initTab() {
        tabLogin.add("Sign In");
        tabLogin.add("Sign Up");
    }

    @Override
    public Fragment getFragmentItem(int position, Object listItem) {
        switch (position) {
            case 0:
                tabSignIn = new SignInFragment();
                tabSignIn.setPageChangeListener(this);
                return tabSignIn;
            case 1:
                tabSignUp = new SignUpFragment();
                tabSignUp.setPageChangeListener(this);
                return tabSignUp;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position, Object listItem) {
        return tabLogin.get(position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tabSignIn.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void moveToSignUp() {
        pager.setCurrentItem(1);
    }


    @Override
    public void moveToSignIn() {
        pager.setCurrentItem(0);
    }
}
