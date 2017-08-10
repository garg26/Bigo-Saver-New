package com.bigosaver.neerajyadav.bigosaver.activity;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigosaver.Util.CleverTapUtil;
import com.bigosaver.neerajyadav.bigosaver.model.BaseResponse;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.fragment.MerchantDetailTab;
import com.bigosaver.neerajyadav.bigosaver.fragment.MerchantOffersTab;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.MerchantDetailResponse;
import com.clevertap.android.sdk.CleverTapAPI;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomPagerAdapter;
import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.FusedLocationService;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;

public class MerchantActivity extends BaseActivity implements CustomPagerAdapter.PagerAdapterInterface, RadioGroup.OnCheckedChangeListener {
    private List<String> tab = new ArrayList<>();
    private static String offerId;
    private MerchantOffersTab tabMerchantOffersTab;
    private MerchantDetailTab tabMerchantDetailTab;
    private static String category;
    private static double latitude, longitude;
    private String distance = "", status = "", message = "";
    private String merchantName = "";
    private CleverTapAPI cleverTap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        if (!TextUtils.isEmpty(merchantName))
            initToolBar(merchantName);
        getHomeIcon();
        cleverTap = CleverTapUtil.getInstance(getApplicationContext());
        initTab();
        setOnClickListener(R.id.iv_feedback);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_feedback:
                openFeedbackDialog();
                break;
        }
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
        tabMerchantOffersTab = MerchantOffersTab.getInstance(offerId, category, latitude, longitude, cleverTap);
        tabMerchantDetailTab = MerchantDetailTab.getInstance(offerId, cleverTap);
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
            merchantName = bundle.getString(AppConstants.BUNDLE_KEYS.MERCHANT_NAME);
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
            case AppConstants.TASKCODES.SUBMIT_FEEDBACK:
                BaseResponse baseResponse = (BaseResponse) response;
                if (null != baseResponse && baseResponse.getSuccess().compareTo("true") == 0) {
                    showToast(getString(R.string.feedback_submitted));
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
        setResult(RESULT_OK);
        finish();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_merchant_feedback, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.tv_feedback:
//                openFeedbackDialog();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void openFeedbackDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_feedback);
        dialog.setCancelable(false);

        ImageView closeButton = (ImageView) dialog.findViewById(R.id.iv_cancel);
        RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radio_group);
        final RadioButton radioAccept = (RadioButton) dialog.findViewById(R.id.radio_accepted);
        final RadioButton radioDecline = (RadioButton) dialog.findViewById(R.id.radio_declined);
        Button btnSubmit = (Button) dialog.findViewById(R.id.btn_apply_promo);
        final EditText etFeedback = (EditText) dialog.findViewById(R.id.et_feedback);

        radioGroup.setOnCheckedChangeListener(this);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(radioAccept, radioDecline, etFeedback)) {
                    dialog.dismiss();
                    submitFeedback();
                }
            }
        });
        dialog.show();
    }

    private boolean isValid(RadioButton radioAccept, RadioButton radioDecline, EditText etFeedback) {
        if (!radioAccept.isChecked() && !radioDecline.isChecked()) {
            showToast(getString(R.string.select_option));
            return false;
        }

        if (etFeedback != null && !TextUtils.isEmpty(etFeedback.getText().toString())) {
            message = etFeedback.getText().toString();
        }
        return true;
    }

    private void submitFeedback() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SUBMIT_FEEDBACK + offerId + "/feedback/");
        httpParamObject.setJSONContentType();
        httpParamObject.setJson(getFeedBackJson().toString());
        httpParamObject.setPostMethod();
        httpParamObject.setClassType(BaseResponse.class);
        executeTask(AppConstants.TASKCODES.SUBMIT_FEEDBACK, httpParamObject);
    }

    private JSONObject getFeedBackJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status);
            jsonObject.put("message", message);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_accepted:
                status = "offer_accepted";
                break;
            case R.id.radio_declined:
                status = "offer_rejected";
                break;
        }
    }

}
