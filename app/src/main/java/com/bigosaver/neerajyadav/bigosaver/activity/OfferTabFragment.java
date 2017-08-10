package com.bigosaver.neerajyadav.bigosaver.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.fragment.OfferListFragment;
import com.bigosaver.neerajyadav.bigosaver.model.CategoryAPI;
import com.bigosaver.neerajyadav.bigosaver.model.CityData;
import com.bigosaver.neerajyadav.bigosaver.model.Filters.FilterItems;
import com.bigosaver.neerajyadav.bigosaver.model.Tab;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomPagerAdapter;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.FusedLocationService;
import simplifii.framework.utility.GPSTracker;
import simplifii.framework.utility.Preferences;

/**
 * Created by Dhillon on 18-10-2016.
 */
public class OfferTabFragment extends BaseFragment implements CustomPagerAdapter.PagerAdapterInterface, SearchView.OnQueryTextListener, OfferListFragment.OnDataSetChanged {
    private static final int REQ_OPEN_LOCATION = 5;
    private CategoryAPI categoryAPI;
    private ArrayList<String> tabs;
    private CityData cityData;
    private List<OfferListFragment> offerListFragmentList;
    private JSONArray filterArray;
    private GPSTracker gpsTracker;
    private String savedFilters = "";
    private List<FilterItems> selectedFilters;
    private String savedCategory = "";
    private String jsonFilters = "";
    private Gson gson = new Gson();

    public static OfferTabFragment getInstance(CategoryAPI categoryAPI, CityData cityData, GPSTracker gpsTracker) {
        OfferTabFragment f = new OfferTabFragment();
        f.categoryAPI = categoryAPI;
        f.cityData = cityData;
        f.gpsTracker = gpsTracker;
        return f;
    }

    @Override
    public void initViews() {
        setHasOptionsMenu(true);
        offerListFragmentList = new ArrayList<>();
        filterArray = new JSONArray();
        selectedFilters = new ArrayList<>();
        tabs = new ArrayList<>();
        if (categoryAPI != null)
            initToolBar(categoryAPI.getDisplay_name());
        if (cityData != null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(cityData.getName());
//        askPermissions();
        initTab();
        ViewPager pager = (ViewPager) findView(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findView(R.id.tab_layout);
        CustomPagerAdapter adapter = new CustomPagerAdapter(getChildFragmentManager(), tabs, this);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public int getViewID() {
        return R.layout.activity_category_offer_activity;
    }

//    private void askPermissions() {
//        new TedPermission(getActivity())
//                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
//                .setPermissionListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted() {
//                        gpsTracker = new GPSTracker(getActivity().getApplicationContext());
//                        checkGpsService();
//                    }
//
//
//                    @Override
//                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                    }
//                }).check();
//    }
//
//    private void checkGpsService() {
//        if (AppController.checkLocationPermission()) {
//            showLocationDialog();
//        } else {
//
//        }
//    }
//
//    private void updateGPSTracker() {
//        for (OfferListFragment f : offerListFragmentList) {
//            f.setGpsTracker(gpsTracker);
//        }
//    }
//
//    private void showLocationDialog() {
//        if (!isGPSEnabled()) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setTitle(R.string.gps_not_found_title);
//            builder.setMessage(R.string.gps_not_found_message);
//            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQ_OPEN_LOCATION);
//                }
//            });
//            builder.setNegativeButton(R.string.no, null);
//            builder.create().show();
//        }
//    }
//
//    private boolean isGPSEnabled() {
//        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//    }


    private void initTab() {
        if (categoryAPI != null) {
            int i = 0;
            savedFilters = Preferences.getData(AppConstants.PREF_KEYS.SELECTED_FILTERS_JSON, "");
            savedCategory = Preferences.getData(AppConstants.PREF_KEYS.SELECTED_CATEGORY, "");
            if (!TextUtils.isEmpty(savedFilters) && categoryAPI.getName().compareTo(savedCategory) == 0) {
                try {
                    filterArray = new JSONArray(savedFilters);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (categoryAPI.getTabs() != null) {
                for (Tab tab : categoryAPI.getTabs()) {
                    tabs.add(tab.getName());
                    offerListFragmentList.add(OfferListFragment.getInstance(i++, categoryAPI,
                            cityData, gpsTracker, filterArray, this));
                }
            }
        }
    }

    @Override
    public Fragment getFragmentItem(int position, Object listItem) {
        return offerListFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position, Object listItem) {
        return tabs.get(position);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_food_n_drink, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.filter:
                intent = new Intent(getActivity(), FilterActivity.class);
                intent.putExtra(AppConstants.BUNDLE_KEYS.CATEGORY_DATA, categoryAPI);
                startActivityForResult(intent, AppConstants.REQUEST_CODES.FILTER);
                break;
            case R.id.search:
                intent = new Intent(getActivity(), SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.BUNDLE_KEYS.CATEGORYID, categoryAPI.getId());
                bundle.putString(AppConstants.BUNDLE_KEYS.CITYID, cityData.getId());
                FusedLocationService.MyLocation location = FusedLocationService.getLatestLocation();
                if (location != null) {
                    bundle.putDouble(AppConstants.BUNDLE_KEYS.LATITUDE, location.getLatitude());
                    bundle.putDouble(AppConstants.BUNDLE_KEYS.LONGITUDE, location.getLongitude());
                }
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK && requestCode == AppConstants.REQUEST_CODES.FILTER) {
            showToast("Filters not selected");
            return;
        }
        switch (requestCode) {
            case AppConstants.REQUEST_CODES.FILTER:
                Bundle bundle = data.getExtras();
                String json = (String) bundle.get(AppConstants.BUNDLE_KEYS.FILTERS);
                try {
                    JSONArray filteredArray = new JSONArray(json);
                    updateFragmentData(filteredArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case REQ_OPEN_LOCATION:

                break;
        }
    }

    private void updateFragmentData(JSONArray filteredArray) {
        for (int i = 0; i < categoryAPI.getTabs().size(); i++) {
            offerListFragmentList.get(i).onUpdate(filteredArray);
        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onFilterUpdation() {
        jsonFilters = Preferences.getData(AppConstants.PREF_KEYS.SELECTED_FILTERS, "");
        if (!TextUtils.isEmpty(jsonFilters)) {
            selectedFilters = new Gson().fromJson(jsonFilters, new TypeToken<List<FilterItems>>() {
            }.getType());
            try {
                if (selectedFilters.size() > 0)
                    updateFragmentData(getSelectedFilters());
                else
                    updateFragmentData(new JSONArray());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private JSONArray getSelectedFilters() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        if (selectedFilters.size() > 0) {
            for (int i = 0; i < selectedFilters.size(); i++) {
                if(selectedFilters.get(i).getFilterType().compareTo(getString(R.string.select_reject))==0){
                        if (selectedFilters.get(i).getSelected()) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("filterItemId", selectedFilters.get(i).getId());
                            jsonObject.put("value", "selected");
//                        jsonObject.put("filterId", selectedFilters.get(i).getFilter_values().get(j).getId());
                            jsonArray.put(jsonObject);
                        } else if (selectedFilters.get(i).getRejected()) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("filterItemId",selectedFilters.get(i).getId());
                            jsonObject.put("value", "not_selected");
//                        jsonObject.put("filterId", selectedFilters.get(i).getFilter_values().get(j).getId());
                            jsonArray.put(jsonObject);
                        }
                    }
                 else if (selectedFilters.get(i).getFilterType().compareTo(getString(R.string.multi_select))==0) {
                        if (selectedFilters.get(i).getSelected()) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("filterItemId", selectedFilters.get(i).getId());
                            jsonObject.put("value", "selected");
//                        jsonObject.put("filterId", selectedFilters.get(i).getFilter_values().get(j).getId());
                            jsonArray.put(jsonObject);
                        }
                    }
            }
            Preferences.saveData(AppConstants.PREF_KEYS.SELECTED_FILTERS, gson.toJson(selectedFilters));
            Preferences.saveData(AppConstants.PREF_KEYS.SELECTED_FILTERS_JSON, jsonArray.toString());
        }
        return jsonArray;
    }
}
