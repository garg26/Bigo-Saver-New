package com.bigosaver.neerajyadav.bigosaver.activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.fragment.MerchantDetailTab;
import com.bigosaver.neerajyadav.bigosaver.fragment.MerchantOffersTab;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.MerchantDetailResponse;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomPagerAdapter;
import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.FusedLocationService;
import simplifii.framework.utility.Preferences;

public class MerchantActivity extends BaseActivity implements CustomPagerAdapter.PagerAdapterInterface {
    private List<String> tab = new ArrayList<>();
    private static String offerId;
    private MerchantOffersTab tabMerchantOffersTab;
    private MerchantDetailTab tabMerchantDetailTab;
    private static String category;
    private static double latitude, longitude;
    private String distance = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevation_burger);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        getHomeIcon();
        initTab();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        loadData();
        FusedLocationService.MyLocation location = FusedLocationService.getLatestLocation();
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        tabMerchantOffersTab = MerchantOffersTab.getInstance(offerId, category, latitude, longitude);
        tabMerchantDetailTab = MerchantDetailTab.getInstance(offerId);
        ViewPager pager = (ViewPager) findViewById(R.id.elevationPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout_elev_burger);
        CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager(), tab, this);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
//        getDetailData();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadData();
//        tabMerchantOffersTab = MerchantOffersTab.getInstance(offerId, category, latitude, longitude);
//        tabMerchantDetailTab = MerchantDetailTab.getInstance(offerId);
//    }

    private void loadData() {
        offerId = Preferences.getData(AppConstants.PREF_KEYS.MERCHANT, "");
        category = Preferences.getData(AppConstants.PREF_KEYS.CATEGORY_DATA, "");
    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);
        if (bundle != null) {
            offerId = bundle.getString(AppConstants.BUNDLE_KEYS.MERCHANT);
            category = bundle.getString(AppConstants.BUNDLE_KEYS.CATEGORY_DATA);
            saveData();
        }
    }

    private void saveData() {
        Preferences.saveData(AppConstants.PREF_KEYS.MERCHANT, offerId);
        Preferences.saveData(AppConstants.PREF_KEYS.CATEGORY_DATA, category);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    private void getDetailData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.MERCHANTDETAIL + offerId);
        httpParamObject.setClassType(MerchantDetailResponse.class);
        executeTask(AppConstants.TASKCODES.MERCHANTDETAIL, httpParamObject);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (null == response) {
            showToast("No response");
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.MERCHANTDETAIL:
                MerchantDetailResponse mdr = (MerchantDetailResponse) response;
                if (mdr != null) {
                    if (!TextUtils.isEmpty(mdr.getName()))
                        initToolBar(mdr.getName());
                    tabMerchantDetailTab.setAdditionalData(mdr);
                    tabMerchantDetailTab.setDetailData(mdr);
                    tabMerchantDetailTab.setFilters(mdr);
                    tabMerchantOffersTab.setImage(mdr);
                    tabMerchantOffersTab.setPhone("tel:" + mdr.getPhone_outlet());
                    tabMerchantOffersTab.setUrl(mdr.getWebsite());
                    tabMerchantOffersTab.setFav(mdr.getIs_favorite());
                    tabMerchantOffersTab.setMdr(mdr);
                    tabMerchantDetailTab.setKnownFor(mdr);
                } else {
                    tabMerchantDetailTab.setFlagEmptyOfferDetails(true);
                }
                break;

        }
    }

    private void initTab() {
        tab.add(AppConstants.TABLAYOUT_TITLES.TITLE_OFFERS);
        tab.add(AppConstants.TABLAYOUT_TITLES.TITLE_DETAILS);
    }

    @Override
    public Fragment getFragmentItem(int position, Object listItem) {

        switch (position) {
            case 0:
                return tabMerchantOffersTab;
            case 1:
                return tabMerchantDetailTab;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position, Object listItem) {
        return tab.get(position);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
