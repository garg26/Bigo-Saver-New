package com.bigosaver.neerajyadav.bigosaver.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigosaver.Util.RoundedTransformation;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.activity.MerchantActivity;
import com.bigosaver.neerajyadav.bigosaver.model.CategoryAPI;
import com.bigosaver.neerajyadav.bigosaver.model.CityData;
import com.bigosaver.neerajyadav.bigosaver.model.Filters.FilterItems;
import com.bigosaver.neerajyadav.bigosaver.model.offers.FilterOfferResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.plumillonforge.android.chipview.OnChipClickListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.FusedLocationService;
import simplifii.framework.utility.GPSTracker;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;

/**
 * Created by Dhillon on 20-10-2016.
 */

public class OfferListFragment extends BaseFragment implements CustomListAdapterInterface, AdapterView.OnItemClickListener {
    private int position;
    private CityData cityId;
    private CategoryAPI categoryAPI;
    private List<FilterOfferResponse> offerList;
    private ListView lv;
    private CustomListAdapter customListAdapter;
    private JSONArray jsonArray;
    //    private LinearLayout llEmpty;
    private RoundedBitmapDrawable roundedBitmapDrawable;
    private String savedFilters = "";
    private String savedCategory = "";
    private View headerView;
    private LayoutInflater inflater;
    private String jsonFilters = "";
    private List<FilterItems> selectedFilters;
    private List<Chip> filterChipList;
    private ChipView chipItem;
    private Gson gson = new Gson();


    private OnDataSetChanged onDataSetChanged;

    public static OfferListFragment getInstance(int position, CategoryAPI categoryAPI, CityData cityId,
                                                GPSTracker gpsTracker, JSONArray filterArray, OnDataSetChanged offerTabFragment) {
        OfferListFragment offerListFragment = new OfferListFragment();
        offerListFragment.position = position;
        offerListFragment.categoryAPI = categoryAPI;
        offerListFragment.cityId = cityId;
        offerListFragment.jsonArray = filterArray;
        offerListFragment.onDataSetChanged = offerTabFragment;
        return offerListFragment;
    }

    @Override
    public void initViews() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = inflater.inflate(R.layout.filter_chips, null, false);
        selectedFilters = new ArrayList<>();
        filterChipList = new ArrayList<>();
        lv = (ListView) findView(R.id.lv_offer);
//        llEmpty = (LinearLayout) findView(R.id.ll_empty);
        offerList = new ArrayList<>();
        customListAdapter = new CustomListAdapter(getActivity(), R.layout.row_all_offers, offerList, this);
        lv.addHeaderView(headerView);
        lv.setAdapter(customListAdapter);
        lv.setOnItemClickListener(this);
        getData(position);

    }

    private void setFilterHeader(String savedFilters) {
        filterChipList.clear();
        selectedFilters = new Gson().fromJson(savedFilters, new TypeToken<List<FilterItems>>() {
        }.getType());
        headerView.setVisibility(View.VISIBLE);
        chipItem = (ChipView) headerView.findViewById(R.id.filter_chip_view);
        if (selectedFilters != null && selectedFilters.size() > 0) {
            for (FilterItems filterItems : selectedFilters) {
                if (filterItems.getSelected() || filterItems.getRejected())
                    filterChipList.add(filterItems);
            }
            chipItem.setChipLayoutRes(R.layout.row_chip);
            chipItem.setChipList(filterChipList);
            chipItem.setOnChipClickListener(filterChipClickListener);
        }
    }

    private OnChipClickListener filterChipClickListener = new OnChipClickListener() {
        @Override
        public void onChipClick(Chip chip) {
            FilterItems item = (FilterItems) chip;
            for (FilterItems filterItems : selectedFilters) {
                if (chip.getText() != null) {
                    if (filterItems.getValue().toLowerCase().compareTo(chip.getText().toLowerCase().toString()) == 0) {
                        filterItems.setSelected(false);
                        filterItems.setRejected(false);
                    }
                }
            }
            Preferences.saveData(AppConstants.PREF_KEYS.SELECTED_FILTERS, gson.toJson(selectedFilters));
            onDataSetChanged.onFilterUpdation();
        }
    };


    private void getData(int position) {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        httpParamObject.setUrl(AppConstants.PAGE_URL.OFFERS);
        httpParamObject.addParameter("format", "json");
        httpParamObject.setJson(getJsonData(position).toString());
        httpParamObject.setClassType(FilterOfferResponse.class);
        executeTask(AppConstants.TASKCODES.OFFEROPTION, httpParamObject);
    }

    private JSONObject getJsonData(int position) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("categoryId", categoryAPI.getId());
            jsonObject.put("cityId", cityId.getId());
            FusedLocationService.MyLocation location = FusedLocationService.getLatestLocation();
            if (location != null) {
                jsonObject.put("latitude", location.getLatitude());
                jsonObject.put("longitude", location.getLongitude());
            }
            jsonObject.put("offer_option", categoryAPI.getTabs().get(position).getEndpoint());

            jsonObject.put("filters", getSavedFilterArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private JSONArray getSavedFilterArray() {
        JSONArray filterArray = new JSONArray();
        savedFilters = Preferences.getData(AppConstants.PREF_KEYS.SELECTED_FILTERS_JSON, "");
        savedCategory = Preferences.getData(AppConstants.PREF_KEYS.SELECTED_CATEGORY, "");

        if (!TextUtils.isEmpty(savedFilters) && categoryAPI.getName().compareTo(savedCategory) == 0) {
            try {
                filterArray = new JSONArray(savedFilters);
                setFilterHeader(Preferences.getData(AppConstants.PREF_KEYS.SELECTED_FILTERS, ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        if (!TextUtils.isEmpty(jsonArray.toString()))
//            return jsonArray;
        return filterArray;
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        super.onBackgroundError(re, e, taskCode, params);
        headerView.setVisibility(View.VISIBLE);
        headerView.findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
//        llEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (null == response) {
            showToast("No response");
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.OFFEROPTION:
                List<FilterOfferResponse> filterOfferResponseList = (List<FilterOfferResponse>) response;
                offerList.clear();
                if (filterOfferResponseList != null && filterOfferResponseList.size() > 0) {
                    headerView.findViewById(R.id.tv_empty).setVisibility(View.GONE);
                    for (int i = 0; i < filterOfferResponseList.size(); i++) {
                        if (filterOfferResponseList.get(i).getIs_active() && filterOfferResponseList.get(i).getIs_active() != null)
                            offerList.add(filterOfferResponseList.get(i));
                    }
                } else {
                    headerView.setVisibility(View.VISIBLE);
                    headerView.findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
                }
                customListAdapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public int getViewID() {
        return R.layout.fragment_offer_list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourceID, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (null != offerList && offerList.size() > 0) {
            StringBuilder merchantPlace = new StringBuilder();
            if (!TextUtils.isEmpty(offerList.get(position).getName()))
                holder.tvName.setText(offerList.get(position).getName().toString());

            if (!TextUtils.isEmpty(offerList.get(position).getPlace_name()))
                merchantPlace.append(offerList.get(position).getPlace_name()).append(", ");
            if (!TextUtils.isEmpty(offerList.get(position).getArea_name()))
                merchantPlace.append(offerList.get(position).getArea_name());

            if (!TextUtils.isEmpty(merchantPlace)) {
                holder.tvOutlets.setVisibility(View.VISIBLE);
                holder.tvOutlets.setText(merchantPlace.toString());
            } else {
                holder.tvOutlets.setVisibility(View.INVISIBLE);
            }

            if (!TextUtils.isEmpty(offerList.get(position).getLogo()))
                Picasso.with(getActivity()).
                        load(Util.getImageUrl(offerList.get(position).getLogo())).
                        placeholder(R.drawable.bigologo).
                        transform(new RoundedTransformation(5, 0)).
                        fit().
                        into(holder.ivLogo);
            else {
                holder.ivLogo.setImageResource(R.drawable.bigologo);
            }

            if (offerList.get(position).getHas_new_offer())
                holder.ivNew.setVisibility(View.VISIBLE);
            else
                holder.ivNew.setVisibility(View.GONE);

            if (null == offerList.get(position).getHas_monthly_offer() || !offerList.get(position).getHas_monthly_offer())
                holder.ivMonthly.setVisibility(View.GONE);
            else
                holder.ivMonthly.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(categoryAPI.getMobileImage()))
                Picasso.with(getActivity()).load(Util.getImageUrl(categoryAPI.getMobileImage()))
                        .into(holder.ivCategory);

            if (null == offerList.get(position).getHas_bigo_offer() || !offerList.get(position).getHas_bigo_offer())
                holder.ivDrink.setVisibility(View.GONE);
            else
                holder.ivDrink.setVisibility(View.VISIBLE);

            Double distance = offerList.get(position).getDistance();
            if (distance != null) {
                String dString = getDistance(distance);
                holder.tvDistance.setVisibility(View.VISIBLE);
                holder.tvDistance.setText(dString);
            } else {
                holder.tvDistance.setVisibility(View.GONE);
            }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), MerchantActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BUNDLE_KEYS.MERCHANT, offerList.get(position - 1).getId());
        bundle.putString(AppConstants.BUNDLE_KEYS.CATEGORY_DATA, categoryAPI.getMobileImage());
        FusedLocationService.MyLocation location = FusedLocationService.getLatestLocation();
        if (location != null) {
            bundle.putDouble(AppConstants.BUNDLE_KEYS.LATITUDE, location.getLatitude());
            bundle.putDouble(AppConstants.BUNDLE_KEYS.LONGITUDE, location.getLongitude());
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onUpdate(JSONArray filteredArray) {
        jsonArray = filteredArray;
        if (isVisible())
            getData(position);
    }


    class Holder {
        TextView tvName, tvOutlets, tvDistance;
        ImageView ivLogo, ivNew, ivCategory, ivMonthly, ivDrink;

        public Holder(View v) {
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

    public interface OnDataSetChanged {
        void onFilterUpdation();
    }
}
