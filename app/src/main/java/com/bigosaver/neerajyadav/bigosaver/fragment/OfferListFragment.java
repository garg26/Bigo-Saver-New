package com.bigosaver.neerajyadav.bigosaver.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigosaver.Util.RoundedTransformation;
import com.bigosaver.neerajyadav.bigosaver.model.Tab;
import com.bigosaver.neerajyadav.bigosaver.model.commonmodels.Image;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.activity.MerchantActivity;
import com.bigosaver.neerajyadav.bigosaver.model.CategoryAPI;
import com.bigosaver.neerajyadav.bigosaver.model.CityData;
import com.bigosaver.neerajyadav.bigosaver.model.Filters.FilterItems;
import com.bigosaver.neerajyadav.bigosaver.model.offers.FilterOfferResponse;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lb.auto_fit_textview.AutoResizeTextView;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.plumillonforge.android.chipview.OnChipClickListener;
import com.squareup.picasso.Picasso;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.CardsEffect;
import com.twotoasters.jazzylistview.effects.GrowEffect;
import com.twotoasters.jazzylistview.effects.SlideInEffect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.PhantomReference;
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

public class OfferListFragment extends BaseFragment implements CustomListAdapterInterface, AdapterView.OnItemClickListener, AbsListView.OnScrollListener, OnDismissCallback {
    private int position;
    private CityData cityId;
    private CategoryAPI categoryAPI;
    private boolean allCity;
    private ImageView ivListTop;
    private List<FilterOfferResponse> offerList = new ArrayList<>();
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
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int INITIAL_DELAY_MILLIS = 100;

    private OnDataSetChanged onDataSetChanged;
    private int tabSize;
    private SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;
    private TextView tvEmpty;

    public static OfferListFragment getInstance(int position, CategoryAPI categoryAPI, CityData cityId,
                                                GPSTracker gpsTracker, JSONArray filterArray, OnDataSetChanged offerTabFragment, boolean allCity) {
        OfferListFragment offerListFragment = new OfferListFragment();
        offerListFragment.position = position;
        offerListFragment.categoryAPI = categoryAPI;
        offerListFragment.cityId = cityId;
        offerListFragment.jsonArray = filterArray;
        offerListFragment.onDataSetChanged = offerTabFragment;
        offerListFragment.allCity = allCity;
        return offerListFragment;
    }

    @Override
    public void initViews() {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = inflater.inflate(R.layout.filter_chips, null, false);
        tvEmpty = (TextView) headerView.findViewById(R.id.tv_empty);
        ivListTop = (ImageView) findView(R.id.iv_list_top);
        ivListTop.setVisibility(View.GONE);
        selectedFilters = new ArrayList<>();
        filterChipList = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) findView(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResourceColor(R.color.dot_dark_screen3)
                , getResourceColor(R.color.dot_dark_screen2)
                , getResourceColor(R.color.dot_dark_screen1)
                , getResourceColor(R.color.dot_dark_screen4));
        lv = (ListView) findView(R.id.lv_offer);
//        llEmpty = (LinearLayout) findView(R.id.ll_empty);
        customListAdapter = new CustomListAdapter(getActivity(), R.layout.row_all_offers, offerList, this);
        swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(new SwingBottomInAnimationAdapter(customListAdapter));
        swingBottomInAnimationAdapter.setAbsListView(lv);

        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

        lv.addHeaderView(headerView);
//        lv.setAdapter(customListAdapter);
        lv.setAdapter(swingBottomInAnimationAdapter);

        lv.setOnScrollListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(position);
            }
        });

        if (categoryAPI != null) {
            List<Tab> tabs = categoryAPI.getTabs();
            if (tabs != null) {
                tabSize = tabs.size();
            }
            if (position < tabSize - 1) {
                tvEmpty.setText("Oops, no offers found. Please check the Locked Offers Tab");
            }
            if (!Preferences.getData(cityId.getId() + categoryAPI.getId() + categoryAPI.getTabs().get(position).getName(), false))
                getData(position);
            else {
                loadCacheData();
                setFilterHeader(Preferences.getData(AppConstants.PREF_KEYS.SELECTED_FILTERS, ""));
            }
        }
        ivListTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.setSelectionAfterHeaderView();
                swipeRefreshLayout.setRefreshing(true);
                hideProgressBar();
            }
        });
    }

    private void loadCacheData() {
        new AsyncTask<String, Void, Object>() {

            @Override
            protected Object doInBackground(String... params) {
                String savedJson = Preferences.getData(cityId.getId() + categoryAPI.getId() + categoryAPI.getTabs().get(position).getName() + "data", "");
                if (!TextUtils.isEmpty(savedJson)) {
                    try {
                        List<FilterOfferResponse> savedList = new ArrayList<>();
                        savedList = FilterOfferResponse.parseJson(savedJson);
                        return savedList;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                checkEmptyList();
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                List<FilterOfferResponse> savedList = (List<FilterOfferResponse>) o;
                if (null != savedList && savedList.size() > 0) {
                    offerList.clear();
                    offerList.addAll(savedList);
                }
                checkEmptyList();
                customListAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }.execute(categoryAPI.getId() + categoryAPI.getTabs().get(position).getName() + "data");

    }

    private void checkEmptyList() {
        if (null != offerList && offerList.size() > 0) {
            headerView.findViewById(R.id.tv_empty).setVisibility(View.GONE);
        } else {
            headerView.setVisibility(View.VISIBLE);
            headerView.findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
        }
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
            if (allCity == false) {
                jsonObject.put("categoryId", categoryAPI.getId());
                jsonObject.put("cityId", cityId.getId());
            }
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
                saveCacheData(filterOfferResponseList);

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
                swipeRefreshLayout.setRefreshing(false);
                break;
        }
    }

    private void saveCacheData(final List<FilterOfferResponse> filterOfferResponseList) {
        new AsyncTask<String, Void, Object>() {

            @Override
            protected Object doInBackground(String... params) {
                Preferences.saveData(cityId.getId() + categoryAPI.getId() + categoryAPI.getTabs().get(position).getName(), true);
                Preferences.saveData(cityId.getId() + categoryAPI.getId() + categoryAPI.getTabs().get(position).getName() + "data", new Gson().toJson(filterOfferResponseList));
                return null;
            }
        }.execute();
    }


    @Override
    public void showProgressBar() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_offer_list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourceID, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        FilterOfferResponse filterOfferResponse = offerList.get(position);

        StringBuilder merchantPlace = new StringBuilder();
        if (!TextUtils.isEmpty(filterOfferResponse.getName()))
            holder.tvName.setText(filterOfferResponse.getName().toString());

//        if (!TextUtils.isEmpty(filterOfferResponse.getPlace_name()))
//            merchantPlace.append(filterOfferResponse.getPlace_name()).append(", ");
//        if (!TextUtils.isEmpty(filterOfferResponse.getArea_name()))
//            merchantPlace.append(filterOfferResponse.getArea_name());

        if (!TextUtils.isEmpty(filterOfferResponse.getPlaceAndArea())) {
            holder.tvOutlets.setVisibility(View.VISIBLE);
            holder.tvOutlets.setText(filterOfferResponse.getPlaceAndArea());
        } else {
            holder.tvOutlets.setVisibility(View.INVISIBLE);
        }

        if (!TextUtils.isEmpty(filterOfferResponse.getLogo()))
            Glide.with(getActivity()).
                    load(Util.getImageUrl(filterOfferResponse.getLogo())).
                    placeholder(R.drawable.bigologo).
                    into(holder.ivLogo);
        else {
            holder.ivLogo.setImageResource(R.drawable.bigologo);
        }

        if (filterOfferResponse.getOffer_viewed() != null) {
            holder.tvTotalView.setText(filterOfferResponse.getOffer_viewed() + "");
        } else {
            holder.tvTotalView.setText("N/A");
        }

        if (filterOfferResponse.getTimings() != null) {
            holder.tvTime.setText(filterOfferResponse.getTimings() + "");
        } else {
            holder.tvTime.setText("N/A");
        }

        if (filterOfferResponse.getOffer_type() != null) {
            List<String> offer_type = filterOfferResponse.getOffer_type();
            holder.ivOption1.setVisibility(View.GONE);
            holder.ivOption2.setVisibility(View.GONE);
            holder.ivOption3.setVisibility(View.GONE);
            holder.ivOption4.setVisibility(View.GONE);
            for (String s : offer_type) {
                if (s.equalsIgnoreCase("B1G1")) {
                    holder.ivOption1.setVisibility(View.VISIBLE);
                }
                if (s.equalsIgnoreCase("B2G2")) {
                    holder.ivOption2.setVisibility(View.VISIBLE);
                }
                if (s.equalsIgnoreCase("%OFF")) {
                    holder.ivOption3.setVisibility(View.VISIBLE);
                }
                if (s.equalsIgnoreCase("LIVE")) {
                    holder.ivOption4.setVisibility(View.VISIBLE);
                }
            }
        }

        if (!TextUtils.isEmpty(filterOfferResponse.getOne_line()) && !filterOfferResponse.getOne_line().equalsIgnoreCase("null")) {
            holder.tvOneLine.setText(filterOfferResponse.getOne_line());
            holder.tvOneLine.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(filterOfferResponse.getDescription())&& !filterOfferResponse.getDescription().equalsIgnoreCase("null")) {
            holder.tvOneLine.setText(filterOfferResponse.getDescription());
            holder.tvOneLine.setVisibility(View.VISIBLE);
        } else {
            holder.tvOneLine.setVisibility(View.GONE);
        }

        if (filterOfferResponse.getHas_new_offer())
            holder.ivNew.setVisibility(View.GONE);
        else
            holder.ivNew.setVisibility(View.GONE);

        if (null == filterOfferResponse.getHas_monthly_offer() || !filterOfferResponse.getHas_monthly_offer())
            holder.ivMonthly.setVisibility(View.GONE);
        else
            holder.ivMonthly.setVisibility(View.VISIBLE);

        if (null == filterOfferResponse.getHas_new_offer() || !filterOfferResponse.getHas_new_offer())
            holder.ivCategory.setVisibility(View.GONE);
        else
            holder.ivCategory.setVisibility(View.VISIBLE);

        if (filterOfferResponse.getImages() != null) {
            List<Image> images = filterOfferResponse.getImages();
            if (images != null) {
                String image = images.get(0).getImage();
                if (!TextUtils.isEmpty(image)) {
                    Glide.with(getActivity()).load(Util.getImageUrl(image))
                            .into(holder.ivMainImage);
                }
            }
        }

        if (null == filterOfferResponse.getHas_bigo_offer() || !filterOfferResponse.getHas_bigo_offer())
            holder.ivDrink.setVisibility(View.GONE);
        else
            holder.ivDrink.setVisibility(View.VISIBLE);

        Double distance = filterOfferResponse.getDistance();
        if (distance != null) {
            String dString = getDistance(distance);
            holder.tvDistance.setVisibility(View.VISIBLE);
            holder.tvDistance.setText(dString);
        } else {
            holder.tvDistance.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MerchantActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.BUNDLE_KEYS.MERCHANT_NAME, offerList.get(position).getName());
                bundle.putString(AppConstants.BUNDLE_KEYS.MERCHANT, offerList.get(position).getId());
                bundle.putString(AppConstants.BUNDLE_KEYS.CATEGORY_DATA, categoryAPI.getMobileImage());
                FusedLocationService.MyLocation location = FusedLocationService.getLatestLocation();
                if (location != null) {
                    bundle.putDouble(AppConstants.BUNDLE_KEYS.LATITUDE, location.getLatitude());
                    bundle.putDouble(AppConstants.BUNDLE_KEYS.LONGITUDE, location.getLongitude());
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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
//        Bundle bundle = new Bundle();
//        bundle.putString(AppConstants.BUNDLE_KEYS.MERCHANT_NAME, offerList.get(position - 1).getName());
//        bundle.putString(AppConstants.BUNDLE_KEYS.MERCHANT, offerList.get(position - 1).getId());
//        bundle.putString(AppConstants.BUNDLE_KEYS.CATEGORY_DATA, categoryAPI.getMobileImage());
//        FusedLocationService.MyLocation location = FusedLocationService.getLatestLocation();
//        if (location != null) {
//            bundle.putDouble(AppConstants.BUNDLE_KEYS.LATITUDE, location.getLatitude());
//            bundle.putDouble(AppConstants.BUNDLE_KEYS.LONGITUDE, location.getLongitude());
//        }
//        intent.putExtras(bundle);
//        startActivity(intent);
    }

    public void onUpdate(JSONArray filteredArray) {
        jsonArray = filteredArray;
        if (isVisible()) {
            swipeRefreshLayout.setRefreshing(true);
            getData(position);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, final int i, int i1, int i2) {
        if (i <= 3) {
            ivListTop.animate().translationY(240);
//            ivListTop.setVisibility(View.GONE);
        } else {
            ivListTop.animate().translationY(0);
            ivListTop.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            customListAdapter.remove(position);
            swingBottomInAnimationAdapter.reset();
        }
    }


    class Holder {
        TextView tvName, tvOutlets, tvDistance;
        AutoResizeTextView tvTotalView, tvTime, tvOneLine;
        ImageView ivLogo, ivNew, ivCategory, ivMonthly, ivDrink, ivOption1, ivOption2, ivOption3, ivOption4,
                ivMainImage;

        public Holder(View v) {
            tvName = (TextView) v.findViewById(R.id.tv_top_all);
            tvOutlets = (TextView) v.findViewById(R.id.tv_num_outlets);
            tvDistance = (TextView) v.findViewById(R.id.tv_distance);
            ivLogo = (ImageView) v.findViewById(R.id.iv_offer_logo);
            ivNew = (ImageView) v.findViewById(R.id.iv_new_offer);
            tvTotalView = (AutoResizeTextView) v.findViewById(R.id.tv_view);
            tvTime = (AutoResizeTextView) v.findViewById(R.id.tv_time);
            tvOneLine = (AutoResizeTextView) v.findViewById(R.id.tv_one_line);
            ivMonthly = (ImageView) v.findViewById(R.id.civ_monthly);
            ivDrink = (ImageView) v.findViewById(R.id.civ_drinks);
            ivCategory = (ImageView) v.findViewById(R.id.civ_category);
            ivOption1 = (ImageView) v.findViewById(R.id.iv_option1);
            ivOption2 = (ImageView) v.findViewById(R.id.iv_option2);
            ivOption3 = (ImageView) v.findViewById(R.id.iv_option3);
            ivOption4 = (ImageView) v.findViewById(R.id.iv_option4);
            ivMainImage = (ImageView) v.findViewById(R.id.iv_main_image);
        }
    }

    public interface OnDataSetChanged {
        void onFilterUpdation();
    }
}
