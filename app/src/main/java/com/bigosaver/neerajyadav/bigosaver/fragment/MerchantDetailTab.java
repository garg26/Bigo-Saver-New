package com.bigosaver.neerajyadav.bigosaver.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bigosavers.R;

import java.util.ArrayList;
import java.util.List;

import com.bigosaver.neerajyadav.bigosaver.model.AdditionalDetails;
import com.bigosaver.neerajyadav.bigosaver.model.commonmodels.Cuisine;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.MerchantTimingResponse;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.MerchantDetailResponse;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.More_information;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.squareup.picasso.Picasso;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantDetailTab extends BaseFragment {
    private TextView tv_see_more;
    private List<AdditionalDetails> additionalDetailsList = new ArrayList<>();
    private LinearLayout ll_add_details, ll_add_details_link, llMoreInformation, llTimings,
            llWebsite, llDayTiming, llOutletInformation, llType, llCuisine, llFilters, llKnownfor, llKnown;
    private LayoutInflater inflater;
    //    private MapView mapView;
//    private GoogleMap googleMap;
    private LatLng location = new LatLng(0.0d, 0.0d);
    private String merchantId;
    private Marker marker;
    private boolean flagMapReady;
    private String userToken;
    private boolean flagResponse;
    private LinearLayout llMore;
    private Boolean flagEmptyOfferDetails = false;
    private GridView glType, glCuisine;
    private CustomListFilterAdapter gridTypeAdapter;
    private CustomListCuisineAdapter gridCuisineAdapter;
    private List<Chip> listType, listCuisine;
    private boolean typeEmptyFlag = false, cuisineEmptyFlag = false;
    private ImageView ivMap;
    private MerchantDetailResponse mdr;
    private View headerView;
    private ChipView cuisineChip, typeChip;
    private boolean isClicked = false;


    public Boolean getFlagEmptyOfferDetails() {
        return flagEmptyOfferDetails;
    }

    public void setFlagEmptyOfferDetails(Boolean flagEmptyOfferDetails) {
        this.flagEmptyOfferDetails = flagEmptyOfferDetails;
    }

    @Override
    public void initViews() {
        loadData();
        tv_see_more = (TextView) findView(R.id.tv_description);
        llWebsite = (LinearLayout) findView(R.id.ll_website);
        llTimings = (LinearLayout) findView(R.id.ll_timing);
        llDayTiming = (LinearLayout) findView(R.id.ll_day_timings);
        llMore = (LinearLayout) findView(R.id.ll_more_info);
        llKnown = (LinearLayout) findView(R.id.ll_known_for);
        llOutletInformation = (LinearLayout) findView(R.id.ll_outlet_info);
        llType = (LinearLayout) findView(R.id.ll_type);
        llCuisine = (LinearLayout) findView(R.id.ll_cuisine);
        llFilters = (LinearLayout) findView(R.id.ll_filters);
        cuisineChip = (ChipView) findView(R.id.cuisine_chip_view);
        typeChip = (ChipView) findView(R.id.type_chip_view);

        ivMap = (ImageView) findView(R.id.iv_map);

        listType = new ArrayList<>();
        listCuisine = new ArrayList<>();

        userToken = Preferences.getData(AppConstants.PREF_KEYS.USER_TOKEN, "");
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getTimingData();
        llMoreInformation = (LinearLayout) findView(R.id.ll_more_information);
        llKnownfor = (LinearLayout) findView(R.id.ll_known);

        setOnClickListener(R.id.tv_website, R.id.iv_map, R.id.tv_description);
        getDetailData();
    }

    private void loadData() {
        merchantId = Preferences.getData(AppConstants.PREF_KEYS.MERCHANT, "");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_website:
                String url = getTvText(R.id.tv_website);
                if (!TextUtils.isEmpty(url)) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
                break;
            case R.id.iv_map:
                String strUri = "http://maps.google.com/maps?q=loc:" + location.latitude + "," + location.longitude + " (" + mdr.getName() + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
                break;
            case R.id.tv_description:
                if (!isClicked) {
                    tv_see_more.setMaxLines(250);
                    isClicked = true;
                } else {
                    tv_see_more.setMaxLines(5);
                    isClicked = false;
                }
        }
    }

    private void getDetailData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.MERCHANTDETAIL + merchantId);
        httpParamObject.setClassType(MerchantDetailResponse.class);
        executeTask(AppConstants.TASKCODES.MERCHANTDETAIL, httpParamObject);
    }

    @Override
    public void onPreExecute(int taskCode) {
        super.onPreExecute(taskCode);
        showProgressBar();
    }

    private String getTvText(int id) {
        TextView tv = (TextView) findView(R.id.tv_website);
        if (tv != null)
            return tv.getText().toString();
        return "";
    }

    public void setKnownFor(MerchantDetailResponse mdr) {
        if (mdr.getRecommendation() != null && !TextUtils.isEmpty(mdr.getRecommendation())) {
            String[] knownForList = mdr.getRecommendation().split(",");
            for (String name : knownForList) {
                View view = inflater.inflate(R.layout.row_item_list_eveattion, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_item_list_pic);
                imageView.setVisibility(View.GONE);
                setText(R.id.tv_item_list_text, name, view);
                llKnownfor.addView(view);
            }
        } else
            findView(R.id.ll_known_for).setVisibility(View.GONE);
    }

    public void setAdditionalData(MerchantDetailResponse merchantDetailResponse) {
        if (merchantDetailResponse.getFilters().getMore_information().size() > 0)
            for (int i = 0; i < merchantDetailResponse.getFilters().getMore_information().size(); i++) {
                View view = inflater.inflate(R.layout.row_item_list_eveattion, null);
                final ImageView imageView = (ImageView) view.findViewById(R.id.iv_item_list_pic);
                More_information more_information = merchantDetailResponse.getFilters().getMore_information().get(i);
                if (!TextUtils.isEmpty(more_information.getValue()))
                    setText(R.id.tv_item_list_text, more_information.getValue(), view);
                if (!TextUtils.isEmpty(more_information.getImage()))
                    Picasso.with(getActivity()).load(AppConstants.PAGE_URL.PHOTO_URL + more_information.getImage()).
                            placeholder(R.drawable.appicon).into(imageView);
                llMoreInformation.addView(view);
            }
        else
            llMore.setVisibility(View.GONE);
    }

    private void getTimingData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.MERCHANTDETAIL + merchantId + "/get_merchant_timings/");
        httpParamObject.setClassType(MerchantTimingResponse.class);
        executeTask(AppConstants.TASKCODES.MERCHANTTIMING, httpParamObject);
    }


    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (null == response) {
            showToast("No response");
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.MERCHANTTIMING:
                List<MerchantTimingResponse> mtr = (List<MerchantTimingResponse>) response;
                if (mtr != null && mtr.size() > 0) {
                    llTimings.setVisibility(View.VISIBLE);
                    setTimingData(mtr);
                } else if (getFlagEmptyOfferDetails())
                    llOutletInformation.setVisibility(View.GONE);

                break;
            case AppConstants.TASKCODES.MERCHANTDETAIL:
                mdr = (MerchantDetailResponse) response;
                if (mdr != null) {
                    if (!TextUtils.isEmpty(mdr.getName()))
                        initToolBar(mdr.getName());
                    setAdditionalData(mdr);
                    setDetailData(mdr);
                    setFilters(mdr);
                    setKnownFor(mdr);
                } else {
                    setFlagEmptyOfferDetails(true);
                }
                break;
        }
    }

    @Override
    public void initToolBar(String title) {
        ((BaseActivity) getActivity()).initToolBar(title);
    }

    private void setTimingData(List<MerchantTimingResponse> mtr) {
        String day, primaryOpen, secondaryOpen, secondaryClose, primaryClose;
        for (int i = 0; i < mtr.size(); i++) {
            day = mtr.get(i).getDay().substring(0, 3).toString();
            primaryOpen = mtr.get(i).getPrimary_opening().toString();
            secondaryOpen = mtr.get(i).getSecondary_closing().toString();
            secondaryClose = mtr.get(i).getSecondary_opening().toString();
            primaryClose = mtr.get(i).getPrimary_closing().toString();

            View view = inflater.inflate(R.layout.row_layout_timings, null);
            if (!TextUtils.isEmpty(day))
                setTextView(view, R.id.tv_day, day);

            if (!TextUtils.isEmpty(primaryOpen) && !TextUtils.isEmpty(primaryClose) &&
                    primaryOpen.compareTo("00:00 AM") == 0 && primaryClose.compareTo("00:00 AM") == 0) {
                setTextView(view, R.id.tv_primary_ot, "24 Hours");
                llDayTiming.addView(view);
            } else if (!TextUtils.isEmpty(primaryOpen) && !TextUtils.isEmpty(primaryClose) &&
                    primaryOpen.compareTo("12:00 PM") == 0 && primaryClose.compareTo("12:00 PM") == 0) {
                setTextView(view, R.id.tv_primary_ot, "Closed");
                llDayTiming.addView(view);
            } else {
                if (!TextUtils.isEmpty(primaryOpen))
                    setTextView(view, R.id.tv_primary_ot, primaryOpen + " - ");
                if (!TextUtils.isEmpty(secondaryOpen))
                    setTextView(view, R.id.tv_primary_ct, secondaryOpen + " ");
                if (!TextUtils.isEmpty(secondaryClose))
                    setTextView(view, R.id.tv_secondary_ot, "  " + secondaryClose + " - ");
                if (!TextUtils.isEmpty(primaryClose))
                    setTextView(view, R.id.tv_secondary_ct, primaryClose);
                llDayTiming.addView(view);
            }
        }

    }

    private void setTextView(View v, int id, String s) {
        TextView tv = (TextView) v.findViewById(id);
        tv.setText(s);
    }

    public void setDetailData(MerchantDetailResponse mdr) {
        if (null != mdr.getCity_name())
            setText(R.id.tv_username, mdr.getCity_name());
        if (null != mdr.getPlace_name())
            setText(R.id.tv_place, mdr.getPlace_name());
        else
            findView(R.id.tv_place).setVisibility(View.GONE);
        if (null != mdr.getArea_name()) {
            setVisibility(R.id.area, View.VISIBLE);
            setVisibility(R.id.tv_area, View.VISIBLE);
            setText(R.id.tv_area, mdr.getArea_name());
        }
        if (!TextUtils.isEmpty(mdr.getWebsite())) {
            llWebsite.setVisibility(View.VISIBLE);
            setText(R.id.tv_website, mdr.getWebsite());
        }
        if (!TextUtils.isEmpty(mdr.getDescription()) || mdr.getDescription() != null) {
            String text = null;
            text = mdr.getDescription();
            tv_see_more.setText(text);
            Util.setDescText(getActivity(), text, tv_see_more, R.color.black, Util.MAX_LENGTH, null, "...Read more");
        } else {
            findView(R.id.ll_description).setVisibility(View.GONE);
        }

        String lat = mdr.getLatitude();
        String lng = mdr.getLongitude();
        try {
            location = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
            flagResponse = true;
            ivMap.setVisibility(View.VISIBLE);
            setPositionBitmap(location);
        } catch (NumberFormatException e) {
            location = new LatLng(0.0d, 0.0d);
            showToast("Couldnt receive location");
            ivMap.setVisibility(View.GONE);
//            setPosition();
        }
    }


    private void setPositionBitmap(LatLng location) {
        String url = "https://maps.googleapis.com/maps/api/staticmap?center=" + location.latitude + "," + location.longitude +
                "&zoom=15&size=450x160&maptype=roadmap&format=png&scale=2"
                + "&markers=size:mid%7Ccolor:0xff0000%7Clabel:1%7C" + location.latitude + "," + location.longitude;
        Picasso.with(getActivity()).load(url).into(ivMap);
    }


    public void setFilters(MerchantDetailResponse mdr) {
        listType.clear();
        listCuisine.clear();

        if (mdr.getFilters().getType() != null && mdr.getFilters().getType().size() > 0) {
            listType.addAll(mdr.getFilters().getType());
            typeChip.setChipLayoutRes(R.layout.row_chip_detail);
            typeChip.setChipList(listType);
        } else {
            typeEmptyFlag = true;
            llType.setVisibility(View.GONE);
        }
        if (mdr.getFilters().getCuisine() != null && mdr.getFilters().getCuisine().size() > 0) {
            listCuisine.addAll(mdr.getFilters().getCuisine());
            cuisineChip.setChipLayoutRes(R.layout.row_chip_detail);
            cuisineChip.setChipList(listCuisine);
        } else {
            cuisineEmptyFlag = true;
            llCuisine.setVisibility(View.GONE);
        }

        if (typeEmptyFlag && cuisineEmptyFlag) {
            llFilters.setVisibility(View.GONE);
        }
    }

    private void setVisibility(int id, int visible) {
        TextView tv = (TextView) findView(id);
        if (tv != null) {
            tv.setVisibility(visible);
        }
    }


    @Override
    public int getViewID() {
        return R.layout.fragment_detail;
    }

//
//    public static MerchantDetailTab getInstance(FilterOfferResponse offer, FilterOfferResponse category) {
//        MerchantDetailTab merchant_detailFragment = new MerchantDetailTab();
//        merchant_detailFragment.merchantId = offer.getId();
//        merchant_detailFragment.offerResponse = offer;
//        return merchant_detailFragment;
//    }

    public static MerchantDetailTab getInstance(String id) {
        MerchantDetailTab merchant_detailFragment = new MerchantDetailTab();
        merchant_detailFragment.merchantId = id;
//        merchant_detailFragment.offerResponse = merchantData;
        return merchant_detailFragment;
    }

    private class Holder {
        TextView tv;

        public Holder(View view) {
            tv = (TextView) view.findViewById(R.id.tv_filter_name);
        }
    }

    private class CustomListFilterAdapter extends ArrayAdapter {
        List<Cuisine> typeList;

        public CustomListFilterAdapter(List<Cuisine> listType) {
            super(getActivity(), R.layout.row_filter, listType);
            typeList = listType;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row_filter, parent, false);
                holder = new Holder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            Cuisine cuisine = typeList.get(position);
            if (!TextUtils.isEmpty(cuisine.getValue()))
                holder.tv.setText(cuisine.getValue().trim());
            return convertView;
        }
    }

    private class CustomListCuisineAdapter extends ArrayAdapter {
        List<Cuisine> typeList;

        public CustomListCuisineAdapter(List<Cuisine> listType) {
            super(getActivity(), R.layout.row_filter, listType);
            typeList = listType;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row_filter, parent, false);
                holder = new Holder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            Cuisine cuisine = typeList.get(position);
            if (!TextUtils.isEmpty(cuisine.getValue()))
                holder.tv.setText(cuisine.getValue().trim());
            return convertView;
        }
    }


}
