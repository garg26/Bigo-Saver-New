package com.bigosaver.neerajyadav.bigosaver.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigosaver.user.AppController;
import com.bigosaver.user.GetUpdatedDataService;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.activity.MerchantActivity;
import com.bigosaver.neerajyadav.bigosaver.activity.OfferTabFragment;
import com.bigosaver.neerajyadav.bigosaver.model.CategoryAPI;
import com.bigosaver.neerajyadav.bigosaver.model.CityData;
import com.bigosaver.neerajyadav.bigosaver.model.interfaces.FragmentChangeListener;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.MerchantData;
import com.bumptech.glide.Glide;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.google.android.gms.common.ConnectionResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.FusedLocationService;
import simplifii.framework.utility.JsonUtil;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class CitySelectionFragment extends BaseFragment implements DialogLocationFragment.CitySelectionListener {
    private static final int REQ_OPEN_LOCATION = 5;
    private ImageView civ_food, civ_health, civ_things, civ_retail, civ_hotels;
    private TextView tv_food, tv_health, tv_things, tv_retail, tv_hotels;
    private List<CityData> cities = new ArrayList<>();
    private CityData selectedCity;

    private List<CategoryAPI> list;
    private List<MerchantData> merchantDataList = new ArrayList<>();
    private LinearLayout popularPlaces;
    private FragmentChangeListener listener;
    private String savedCity;
    private TextView tvCity;
    private ImageView ivCityBg;
    boolean isLocationAsked = false;
    private FusedLocationListener fusedLocationService;
    private FusedLocationService.MyLocation location;
    private String categoryId = "";
    private PullToZoomScrollViewEx scrollView;
    private boolean showCity;

    public static CitySelectionFragment getInstance(FragmentChangeListener listener, String categoryId, String savedCity) {
        CitySelectionFragment f = new CitySelectionFragment();
        f.listener = listener;
        f.categoryId = categoryId;
        f.savedCity = savedCity;
        return f;
    }


    @Override
    public void initViews() {
        findViews();
        savedCity = Preferences.getData(AppConstants.PREF_KEYS.SELECTED_CITY, "");
        checkGpsService();
        Intent i = new Intent(getActivity(), GetUpdatedDataService.class);
        getActivity().startService(i);
        setOnClickListener(R.id.tv_select_city);
    }

    private void setSelecteCity(String savedCity) {
        setText(R.id.tv_select_city, savedCity);
        for (int i = 0; i < cities.size(); i++) {
            if (savedCity.toLowerCase().equals(cities.get(i).getName().toLowerCase())) {
                selectedCity = cities.get(i);
                getPopularMerchants(selectedCity.getId());
                if (!TextUtils.isEmpty(selectedCity.getImage()))
                    Picasso.with(getActivity()).load(Util.getImageUrl(selectedCity.getImage())).
                            placeholder(R.drawable.california_tree).into(ivCityBg);
                break;
            }
        }
        if (!TextUtils.isEmpty(categoryId)) {
            onListItemClicked(findCategoryPosition(categoryId, list), 2);
        }

    }

    private void getCitiesList() {
        HttpParamObject obj = new HttpParamObject();
        obj.setUrl(AppConstants.PAGE_URL.CITIES);
        obj.setClassType(CityData.class);
        executeTask(AppConstants.TASKCODES.CITIES, obj);
    }

    private void findViews() {
//        scrollView = (PullToZoomScrollViewEx) findView(R.id.scroll_view);
        civ_food = (ImageView) findView(R.id.civ_Food);
        civ_health = (ImageView) findView(R.id.civ_health);
        civ_things = (ImageView) findView(R.id.civ_things);
        civ_retail = (ImageView) findView(R.id.civ_Retail);
        civ_hotels = (ImageView) findView(R.id.civ_hotels);

        tv_food = (TextView) findView(R.id.tv_food_drink);
        tv_health = (TextView) findView(R.id.tv_health);
        tv_things = (TextView) findView(R.id.tv_things);
        tv_retail = (TextView) findView(R.id.tv_retail);
        tv_hotels = (TextView) findView(R.id.tv_hotels);

        ivCityBg = (ImageView) findView(R.id.iv_city_background);
        popularPlaces = (LinearLayout) findView(R.id.ll_popular_places);
        loadCategory();
    }

    private void getPopularMerchants(String id) {
        HttpParamObject obj = new HttpParamObject();
        obj.setUrl(AppConstants.PAGE_URL.MERCHANTS_FEATURE_LIST + "?city=" + id);
        obj.setClassType(MerchantData.class);
        executeTask(AppConstants.TASKCODES.MERCHANTS_FEATURE_LIST, obj);
    }

    private void loadCategory() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.CATEGORY);
        httpParamObject.setClassType(CategoryAPI.class);
        executeTask(AppConstants.TASKCODES.CATEGORYCODE, httpParamObject);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        switch (taskCode) {
            case AppConstants.TASKCODES.CATEGORYCODE:
                list = (List<CategoryAPI>) response;
                if (list == null) {
                    showToast("error");
                    return;
                } else {
                    setCategoryList();
                }
                getCitiesList();
                break;
            case AppConstants.TASKCODES.CITIES:
                List<CityData> citiesList = (List<CityData>) response;
                if (citiesList != null && citiesList.size() > 0) {
                    List<CityData> cityDatas = filterCityList(citiesList);
                    if (cityDatas != null) {
                        this.cities.addAll(cityDatas);
                    }
                    setCategoryList();
                    if (!TextUtils.isEmpty(savedCity)) {
                        setSelecteCity(savedCity);
                    } else {
                        showCity = true;
                    }
                }
                hideProgressBar();
                break;
            case AppConstants.TASKCODES.MERCHANTS_FEATURE_LIST:
                List<MerchantData> data = (List<MerchantData>) response;
                merchantDataList.clear();
                if (data != null && data.size() > 0) {
                    merchantDataList.addAll(data);
                }
                showPopularMerchants();
                break;
        }
    }

    private List<CityData> filterCityList(List<CityData> citiesList) {
        List<CityData> cityDatas = new ArrayList<>();
        for (CityData cityData : citiesList){
            if (cityData.is_active()){
                cityDatas.add(cityData);
            }
        }
        return cityDatas;
    }

    private void showPopularMerchants() {
        findView(R.id.ll_must_try).setVisibility(View.VISIBLE);
        popularPlaces.removeAllViewsInLayout();
        if (merchantDataList != null && merchantDataList.size() > 0)
            for (MerchantData data : merchantDataList) {
                popularPlaces.addView(getMerchantRow(data));
            }
        else {
            findView(R.id.ll_must_try).setVisibility(View.GONE);
        }
    }

    private View getMerchantRow(final MerchantData data) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.row_popular_places, popularPlaces, false);
        TextView tvMore = (TextView) v.findViewById(R.id.tv_find_more);
        TextView tvSaving = (TextView) v.findViewById(R.id.tv_saving);
        ImageView imageView = (ImageView) v.findViewById(R.id.iv_place);
        ImageView ivCat = (ImageView) v.findViewById(R.id.iv_cat_icon);
        ImageView ivPlaceType = (ImageView) v.findViewById(R.id.iv_place_type);
        final String categoryImage = findCategory(data.getCategory(), list);

        if (!TextUtils.isEmpty(data.getPlace_type_name())) {
            if (data.getPlace_type_name().toLowerCase().compareTo("hotel") == 0)
                ivPlaceType.setBackgroundResource(R.drawable.hotel);
            if (data.getPlace_type_name().toLowerCase().compareTo("mall") == 0)
                ivPlaceType.setBackgroundResource(R.drawable.cart_icon);
        }

        if (!TextUtils.isEmpty(data.getName()))
            setText(R.id.tv_name_of_offer, data.getName(), v);

        if (data.getTotal_savings() != null)
            setText(R.id.tv_saving, data.getTotal_savings().toString(), v);

        if (!TextUtils.isEmpty(data.getPlace_name().toString()))
            setText(R.id.tv_location, data.getPlace_name(), v);
        else
            v.findViewById(R.id.ll_place).setVisibility(View.GONE);

        StringBuilder area = new StringBuilder();
        if (!TextUtils.isEmpty(data.getArea_name()))
            area.append(data.getArea_name()).append(", ");
        if (!TextUtils.isEmpty(data.getCity_name()))
            area.append(data.getCity_name());

        if (!TextUtils.isEmpty(area.toString()))
            setText(R.id.tv_place, area.toString(), v);
        else
            v.findViewById(R.id.ll_location).setVisibility(View.INVISIBLE);

        if (!TextUtils.isEmpty(categoryImage))
            Picasso.with(getActivity()).load(Util.getImageUrl(categoryImage)).into(ivCat);

        if (!TextUtils.isEmpty(data.getImages().get(0).getImage()))
            Picasso.with(getActivity()).load(Util.getImageUrl(data.getImages().get(0).getImage())).placeholder(R.drawable.appiconlogo).into(imageView);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MerchantActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.BUNDLE_KEYS.MERCHANT, data.getId());
                bundle.putString(AppConstants.BUNDLE_KEYS.CATEGORY_DATA, categoryImage);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return v;
    }

    private String findCategory(String data, List<CategoryAPI> list) {
        for (CategoryAPI c : list) {
            if (c.getId().equals(data)) {
                return c.getMobileImage();
            }
        }
        return "";
    }

    private int findCategoryPosition(String data, List<CategoryAPI> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(data)) {
                return i;
            }
        }
        return -1;
    }


    private void setCategoryList() {
        if (list != null && list.size() == 5) {
            Gson gson = new Gson();
            Preferences.saveData(AppConstants.PREF_KEYS.CATEGORY_LIST, gson.toJson(list));
            Picasso.with(getActivity()).load(Util.getImageUrl(list.get(0).getMobileImage())).into(civ_food);
            tv_food.setText(list.get(0).getDisplay_name());
            Picasso.with(getActivity()).load(Util.getImageUrl(list.get(1).getMobileImage())).into(civ_health);
            tv_health.setText(list.get(1).getDisplay_name());
            Picasso.with(getActivity()).load(Util.getImageUrl(list.get(2).getMobileImage())).into(civ_things);
            tv_things.setText(list.get(2).getDisplay_name());
            Picasso.with(getActivity()).load(Util.getImageUrl(list.get(3).getMobileImage())).into(civ_retail);
            tv_retail.setText(list.get(3).getDisplay_name());
            Picasso.with(getActivity()).load(Util.getImageUrl(list.get(4).getMobileImage())).into(civ_hotels);
            tv_hotels.setText(list.get(4).getDisplay_name());
        }
        setCategoryListeners();
    }

    private void setCategoryListeners() {
        setOnClickListener(R.id.rl_1, R.id.rl_2, R.id.rl_3, R.id.rl_4, R.id.rl_5);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_select_city:
                showCityDialog();
                break;
            case R.id.rl_1:
                onListItemClicked(0, 0);
                break;
            case R.id.rl_2:
                onListItemClicked(1, 0);
                break;
            case R.id.rl_3:
                onListItemClicked(2, 0);
                break;
            case R.id.rl_4:
                onListItemClicked(3, 0);
                break;
            case R.id.rl_5:
                onListItemClicked(4, 0);
                break;

        }
    }

    public void openCorrespondingCategory(String cid) {
        int position = -1;
        position = findCategoryPosition(cid, list);
        onListItemClicked(position, 2);
    }

    private void onListItemClicked(int position, int tab) {
        if (selectedCity == null) {
            showToast(getString(R.string.select_your_city));
            if (showCity == true){
                showCityDialog();
            }
        } else {
            CategoryAPI categoryAPI = list.get(position);
            OfferTabFragment f;
            if (categoryAPI.isShowAllCities() == true){
                boolean allCity = true;
                f = OfferTabFragment.getInstance(categoryAPI, selectedCity, null, allCity);
            } else {
                f = OfferTabFragment.getInstance(categoryAPI, selectedCity, null, false);
            }
            if (listener != null) {
                listener.changeFragment(f);
            }
        }
    }

    private void showCityDialog() {
        DialogLocationFragment f = DialogLocationFragment.getInstance(savedCity, cities, this);
        f.show(getActivity().getSupportFragmentManager(), "Location");
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_city_selection;
    }

    @Override
    public void onCitySelected(CityData city) {
        selectedCity = city;
        Preferences.saveData(AppConstants.PREF_KEYS.SELECTED_CITY, selectedCity.getName());
        setText(R.id.tv_select_city, city.getName());
        if (!TextUtils.isEmpty(selectedCity.getImage()))
            Glide.with(getActivity()).load(Util.getImageUrl(selectedCity.getImage())).
                    placeholder(R.drawable.california_tree).into(ivCityBg);
        getPopularMerchants(selectedCity.getId());
    }

    @Override
    public void onPreExecute(int taskCode) {
        switch (taskCode) {
            case AppConstants.TASKCODES.CATEGORYCODE:
                showProgressBar();
                break;
            case AppConstants.TASKCODES.CITIES:
                break;
            case AppConstants.TASKCODES.MERCHANTS_FEATURE_LIST:
                break;
        }
    }

    private void checkLocationPermission() {
        if (AppController.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            getLocation();
        } else {
            new TedPermission(getActivity()).setPermissionListener(new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    getLocation();
                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                }
            }).setPermissions(Manifest.permission.ACCESS_FINE_LOCATION).check();
        }
    }

    private void getLocation() {
        fusedLocationService = new FusedLocationListener(getActivity());
    }

    class FusedLocationListener extends FusedLocationService {

        public FusedLocationListener(Activity mContext) {
            super(mContext);
        }

        @Override
        protected void onSuccess(MyLocation mLastKnownLocation) {
            CitySelectionFragment.this.location = mLastKnownLocation;
            // ToDO
        }

        @Override
        protected void onFailed(ConnectionResult connectionResult) {

        }
    }

    private void checkGpsService() {
        if (AppController.checkLocationPermission()) {
            if (isGPSEnabled()) {
                getLocation();
            } else {
                showLocationDialog();
            }

        } else {
            checkLocationPermission();
        }
    }

    private void showLocationDialog() {
        if (!isGPSEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.gps_not_found_title);
            builder.setMessage(R.string.gps_not_found_message);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQ_OPEN_LOCATION);
                }
            });
            builder.setNegativeButton(R.string.no, null);
            builder.create().show();
        }
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_OPEN_LOCATION:
                if (isGPSEnabled()) {
                    getLocation();
                }
                break;
        }
    }
}
