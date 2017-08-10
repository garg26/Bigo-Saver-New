package com.bigosaver.neerajyadav.bigosaver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.CategoryAPI;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.search.BaseSearchClass;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.search.SearchMerchantWithPlace;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;

/**
 * Created by Dhillon on 23-11-2016.
 */

public class SearchPlaceMerchantActivity extends BaseActivity implements CustomListAdapterInterface {
    private ListView lvSearchItems;
    private String placeId, placeName, key, categoryId;
    private TextView tvEmpty;
    private List<BaseSearchClass> searchDataList;
    private CustomListAdapter customSearchListAdapter;
    private List<CategoryAPI> list;
    private Boolean isPlace = false, isCity = false;
    private Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_merchant_place);

        Intent i = getIntent();
        if (i != null) {
            if (i.getBooleanExtra(AppConstants.BUNDLE_KEYS.ISPLACE, false)) {
                placeId = i.getStringExtra(AppConstants.BUNDLE_KEYS.PLACEID);
                placeName = i.getStringExtra(AppConstants.BUNDLE_KEYS.NAME);
                categoryId = i.getStringExtra(AppConstants.BUNDLE_KEYS.CATEGORYID);
                latitude = i.getDoubleExtra(AppConstants.BUNDLE_KEYS.LATITUDE,0.0);
                longitude = i.getDoubleExtra(AppConstants.BUNDLE_KEYS.LONGITUDE,0.0);
                key = "place_id";
            }
            if(i.getBooleanExtra(AppConstants.BUNDLE_KEYS.ISAREA, false)){
                placeId = i.getStringExtra(AppConstants.BUNDLE_KEYS.AREAID);
                placeName = i.getStringExtra(AppConstants.BUNDLE_KEYS.NAME);
                categoryId = i.getStringExtra(AppConstants.BUNDLE_KEYS.CATEGORYID);
                latitude = i.getDoubleExtra(AppConstants.BUNDLE_KEYS.LATITUDE,0.0);
                longitude = i.getDoubleExtra(AppConstants.BUNDLE_KEYS.LONGITUDE,0.0);
                key = "area";
            }
//            searchDataWithPlace("57e79606c43d0e03107d759c");
        }
        if (!TextUtils.isEmpty(placeName))
            initToolBar(placeName);
        list = new ArrayList<>();
        list.addAll(CategoryAPI.getInstance());
        searchDataList = new ArrayList<>();
        lvSearchItems = (ListView) findViewById(R.id.lv_search_data);
        tvEmpty = (TextView) findViewById(R.id.empty_tv);
        customSearchListAdapter = new CustomListAdapter(this, R.layout.row_search_respone_merchants, searchDataList, this);
        lvSearchItems.setAdapter(customSearchListAdapter);
        searchDataWithPlace(key,placeId, categoryId);
    }


    private void searchDataWithPlace(String key, String id, String categoryId) {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SEARCHWITHPLACE);
        httpParamObject.setClassType(SearchMerchantWithPlace.class);
        httpParamObject.addParameter(key, id);
        httpParamObject.addParameter("category", categoryId);
        httpParamObject.addParameter("latitude", String.valueOf(latitude));
        httpParamObject.addParameter("longitude", String.valueOf(longitude));
        executeTask(AppConstants.TASKCODES.SEARCHWITHPLACE, httpParamObject);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (response == null) {
            showToast(getString(R.string.no_response));
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.SEARCHWITHPLACE:
                List<SearchMerchantWithPlace> data = (List<SearchMerchantWithPlace>) response;
                if (null != data && data.size() > 0) {
                    searchDataList.clear();
                    lvSearchItems.setVisibility(View.VISIBLE);
                    for (int i = 0; i < data.size(); i++) {
                        SearchMerchantWithPlace searchMerchantWithPlace = data.get(i);
                        if (i == 0)
                            searchMerchantWithPlace.setFirst(true);
                        searchDataList.add(searchMerchantWithPlace);
                    }
                    customSearchListAdapter.notifyDataSetChanged();
                } else {
                    lvSearchItems.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                }

                break;

        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_search_respone_merchants, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final SearchMerchantWithPlace smwp = (SearchMerchantWithPlace) searchDataList.get(position);
        if (smwp != null) {
            if (smwp.getFirst())
                holder.tvTop.setVisibility(View.VISIBLE);
            final String categoryImage = findCategory(smwp.getCategory(), list);
            StringBuilder merchantPlace = new StringBuilder();
            if (!TextUtils.isEmpty(smwp.getName())) {
                holder.tvName.setText(smwp.getName().toString());
            }
            if (!TextUtils.isEmpty(smwp.getPlace_name()))
                merchantPlace.append(smwp.getPlace_name()).append(", ");
            if (!TextUtils.isEmpty(smwp.getArea_name()))
                merchantPlace.append(smwp.getArea_name());
            holder.tvOutlets.setText(merchantPlace);
            if (!TextUtils.isEmpty(smwp.getLogo())) {
                Picasso.with(this).load("http://52.66.8.188/media/" + smwp.getLogo()).
                        placeholder(R.drawable.bigologo).into(holder.ivLogo);
            }
            if (smwp.getHas_new_offer())
                holder.ivNew.setVisibility(View.VISIBLE);
            if (smwp.getHas_monthly_offer())
                holder.ivMonthly.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(categoryImage))
                Picasso.with(this).load("http://52.66.8.188/media/" + categoryImage).into(holder.ivCategory);
            if (null != smwp.getHas_bigo_offer() && !smwp.getHas_bigo_offer())
                holder.ivDrink.setVisibility(View.GONE);
            if (smwp.getDistance()!=null) {
                holder.tvDistance.setVisibility(View.VISIBLE);
                holder.tvDistance.setText(getDistance(smwp.getDistance()));
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToNextActivity(smwp.getId(), categoryImage);
                }
            });
        }
        return convertView;
    }

    private String getDistance(Double distance) {
        if (distance < 1) {
            distance = distance * 1000;
            return distance.intValue() + " Meters";
        } else {
            return distance.intValue() + " Km";
        }
    }

    private void moveToNextActivity(String merchantId, String categoryImage) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BUNDLE_KEYS.MERCHANT, merchantId);
        bundle.putString(AppConstants.BUNDLE_KEYS.CATEGORY_DATA, categoryImage);
        startNextActivity(bundle, MerchantActivity.class);
    }

    class Holder {
        TextView tvName, tvOutlets, tvDistance, tvTop;
        ImageView ivLogo, ivNew, ivCategory, ivMonthly, ivDrink;

        public Holder(View v) {
            tvTop = (TextView) v.findViewById(R.id.tv_row_header);
            tvName = (TextView) v.findViewById(R.id.tv_top_all);
            tvOutlets = (TextView) v.findViewById(R.id.tv_num_outlets);
            tvDistance = (TextView) v.findViewById(R.id.tv_distance);
            ivLogo = (ImageView) v.findViewById(R.id.iv_offer_logo);
            ivNew = (ImageView) v.findViewById(R.id.iv_new_offer);
            ivMonthly = (ImageView) v.findViewById(R.id.civ_monthly);
            ivDrink = (ImageView) v.findViewById(R.id.civ_drinks);
            ivCategory = (ImageView) v.findViewById(R.id.civ_category);
        }
    }

    private String findCategory(String data, List<CategoryAPI> list) {
        for (CategoryAPI c : list) {
            if (c.getId().equals(data)) {
                return c.getMobileImage();
            }
        }
        return "";
    }
}
