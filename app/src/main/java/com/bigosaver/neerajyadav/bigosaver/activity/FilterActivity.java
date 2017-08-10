package com.bigosaver.neerajyadav.bigosaver.activity;

import com.bigosaver.neerajyadav.bigosaver.model.AdditionalDetails;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.bigosaver.user.ExpandableListAdapter;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.CategoryAPI;
import com.bigosaver.neerajyadav.bigosaver.model.Filters.FilterData;
import com.bigosaver.neerajyadav.bigosaver.model.Filters.FilterItems;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterActivity extends BaseActivity {
    private List<String> listDataHeader;
    private List<AdditionalDetails> additionalDetailsList = new ArrayList<>();
    private ExpandableListAdapter elistAdapter;
    private ExpandableListView expListView;
    private HashMap<String, List<String>> listDataChild;
    private LinearLayout ll_additional;
    private LayoutInflater inflater;
    private CategoryAPI category;
    private List<FilterData> filterDataList, categoryFilters;
    private List<FilterItems> onlySelectedFilters, selectedFilters;
    private Gson gson = new Gson();
    private String jsonFilters = "";
    private String savedFilterArray = "";
    private JSONArray jsonArray;
    private String savedCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        initToolBar(AppConstants.TOOLBAR_TITLES.TITLE_FILTER);

        expListView = (ExpandableListView) findViewById(R.id.elv_filter);
        filterDataList = new ArrayList<>();
        categoryFilters = new ArrayList<>();
        selectedFilters = new ArrayList<>();
        onlySelectedFilters = new ArrayList<>();

        savedCategory = Preferences.getData(AppConstants.PREF_KEYS.SELECTED_CATEGORY, "");
        jsonFilters = Preferences.getData(AppConstants.PREF_KEYS.SELECTED_FILTERS, "");
        savedFilterArray = Preferences.getData(AppConstants.PREF_KEYS.SELECTED_FILTERS_JSON, "");
        try {
            jsonArray = new JSONArray(savedFilterArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (category.getName().compareTo(savedCategory) != 0)
            jsonFilters = "";
        getFilterData();
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                onRadioSelect(groupPosition, childPosition, sele)

                return true;
            }
        });
    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);
        category = (CategoryAPI) bundle.getSerializable(AppConstants.BUNDLE_KEYS.CATEGORY_DATA);
    }

    private void getFilterData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(String.format(AppConstants.PAGE_URL.FILTERITEMS, category.getId()));
        httpParamObject.setClassType(FilterData.class);
        executeTask(AppConstants.TASKCODES.FILTERITEMS, httpParamObject);
    }

    @Override
    public void onPreExecute(int taskCode) {
        super.onPreExecute(taskCode);
        showProgressDialog();
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        super.onBackgroundError(re, e, taskCode, params);
        showToast("Background error");
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (response == null) {
            showToast("No response");
            hideProgressBar();
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.FILTERITEMS:
                List<FilterData> filterData = (List<FilterData>) response;
                if (null != filterData) {
                    filterDataList.addAll(filterData);
                    setCategoryFilter(filterDataList);
                    prepareFilterListData();
                }
                hideProgressBar();
                break;

        }
    }

    private void setCategoryFilter(List<FilterData> filterDataList) {
        categoryFilters = new ArrayList<>();
        for (int i = 0; i < filterDataList.size(); i++) {
            if (filterDataList.get(i).getCategory().equals(category.getId())) {
                categoryFilters.add(filterDataList.get(i));
            }
        }
    }


    private void prepareFilterListData() {
//        listDataHeader = new ArrayList<String>();
//        listDataChild = new HashMap<String, List<String>>();
//        List<String> multiSelect = new ArrayList<String>();
//        List<String> selectReject = new ArrayList<String>();
//        int l = 0;
//         Adding child data
//        if (categoryFilters != null) {
//            for (int k = 0; k < categoryFilters.size(); k++) {
//                listDataHeader.add(categoryFilters.get(k).getTitle());
//                for (int i = 0; i < categoryFilters.get(k).getFilter_values().size(); i++) {
//                    multiSelect.add(categoryFilters.get(k).getFilter_values().get(i).getValue());
//                }
//            }
//            listDataChild.put(listDataHeader.get(0), multiSelect);
//        }

//        if (categorySrFilter != null) {
//            for (int k = 0; k < categorySrFilter.size(); k++) {
//                listDataHeader.add(categorySrFilter.get(k).getTitle());
//                for (int i = 0; i < categorySrFilter.get(k).getFilter_values().size(); i++) {
//                    selectReject.add(categorySrFilter.get(k).getFilter_values().get(i).getValue());
//                }
//                listDataChild.put(listDataHeader.get(l++), selectReject);
//            }

        if (!TextUtils.isEmpty(jsonFilters)) {
            try {
                onlySelectedFilters = new Gson().fromJson(jsonFilters, new TypeToken<List<FilterItems>>() {
                }.getType());
                setSavedFilters(categoryFilters, onlySelectedFilters);
//                setSavedFilterValues(jsonArray, categoryFilters);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        elistAdapter = new ExpandableListAdapter(this, categoryFilters);
        expListView.setAdapter(elistAdapter);
        expListView.setGroupIndicator(null);
    }


    private void setSavedFilters(List<FilterData> categoryFilters, List<FilterItems> onlySelectedFilters) throws JSONException {
        if (onlySelectedFilters != null && onlySelectedFilters.size() > 0) {
            for (FilterData fd : categoryFilters) {
                if (savedCategory.compareTo(fd.getCategory_name()) == 0) {
                    for (FilterItems filterItem : fd.getFilter_values()) {
                        for(FilterItems savedFilterItem: onlySelectedFilters){
                            if(savedFilterItem.getId().compareTo(filterItem.getId())==0){
                                filterItem.setSelected(savedFilterItem.getSelected());
                                filterItem.setRejected(savedFilterItem.getRejected());
                            }
                        }
                    }
                }
            }

//            for (int i = 0; i < selectedFilters.size(); i++) {
//                for (int j = 0; j < categoryFilters.size(); j++)
//                    if (savedCategory.compareTo(categoryFilters.get(j).getCategory()) == 0) {
//                        if (selectedFilters.get(i).getId().compareTo(categoryFilters.get(j).getId()) == 0) {
//                            categoryFilters.remove(i);
//                            categoryFilters.add(i, selectedFilters.get(i));
//                        }
//                    }
//            }
        }
    }


    @Override
    protected int getHomeIcon() {
        return R.drawable.back;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        int position = 0;
        MenuItem item = menu.getItem(position);
        SpannableString s = new SpannableString("APPLY");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        item.setTitle(s);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.tv_done:
                showToast("Filter Applied");
                intent = new Intent(FilterActivity.this, OfferTabFragment.class);
                try {
                    intent.putExtra(AppConstants.BUNDLE_KEYS.FILTERS, getSelectedFilters().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setResult(RESULT_OK, intent);
                finish();
                break;
            case android.R.id.home:
                if (onlySelectedFilters.size() > 0) {
                    showToast("Filter Applied");
                    intent = new Intent(FilterActivity.this, OfferTabFragment.class);
                    try {
                        intent.putExtra(AppConstants.BUNDLE_KEYS.FILTERS, getSelectedFilters().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private JSONArray getSelectedFilters() throws JSONException {
        onlySelectedFilters.clear();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < categoryFilters.size(); i++) {
            if (categoryFilters.get(i).getType().equals(getString(R.string.select_reject))) {
                for (int j = 0; j < categoryFilters.get(i).getFilter_values().size(); j++) {
                    categoryFilters.get(i).getFilter_values().get(j).setFilterType(getString(R.string.select_reject));
                    if (categoryFilters.get(i).getFilter_values().get(j).getSelected()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("filterItemId", categoryFilters.get(i).getFilter_values().get(j).getId());
                        jsonObject.put("value", "selected");
                        onlySelectedFilters.add(categoryFilters.get(i).getFilter_values().get(j));
//                        jsonObject.put("filterId", categoryFilters.get(i).getFilter_values().get(j).getId());
                        jsonArray.put(jsonObject);
                    } else if (categoryFilters.get(i).getFilter_values().get(j).getRejected()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("filterItemId", categoryFilters.get(i).getFilter_values().get(j).getId());
                        jsonObject.put("value", "not_selected");
                        onlySelectedFilters.add(categoryFilters.get(i).getFilter_values().get(j));
//                        jsonObject.put("filterId", categoryFilters.get(i).getFilter_values().get(j).getId());
                        jsonArray.put(jsonObject);
                    }
                }
            } else if (categoryFilters.get(i).getType().equals(getString(R.string.multi_select))) {
                for (int j = 0; j < categoryFilters.get(i).getFilter_values().size(); j++) {
                    categoryFilters.get(i).getFilter_values().get(j).setFilterType(getString(R.string.multi_select));
                    if (categoryFilters.get(i).getFilter_values().get(j).getSelected()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("filterItemId", categoryFilters.get(i).getFilter_values().get(j).getId());
                        jsonObject.put("value", "selected");
                        onlySelectedFilters.add(categoryFilters.get(i).getFilter_values().get(j));
//                        jsonObject.put("filterId", categoryFilters.get(i).getFilter_values().get(j).getId());
                        jsonArray.put(jsonObject);
                    }
                }
            }
        }
        Preferences.saveData(AppConstants.PREF_KEYS.SELECTED_FILTERS, gson.toJson(onlySelectedFilters));
//        Preferences.saveData(AppConstants.PREF_KEYS.SELECTED_FILTERS, gson.toJson(categoryFilters));
        Preferences.saveData(AppConstants.PREF_KEYS.SELECTED_CATEGORY, category.getName());
        Preferences.saveData(AppConstants.PREF_KEYS.SELECTED_FILTERS_JSON, jsonArray.toString());
        return jsonArray;
    }


    public void onRadioSelect(int grpPos, int childPos) {

    }
}
