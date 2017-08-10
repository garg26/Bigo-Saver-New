package com.bigosaver.neerajyadav.bigosaver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.CategoryAPI;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.search.Area;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.search.BaseSearchClass;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.search.Merchant;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.search.Place;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.search.SearchResponse;
import com.lb.auto_fit_textview.AutoResizeTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;

/**
 * Created by Dhillon on 14-11-2016.
 */
public class SearchActivity extends BaseActivity implements CustomListAdapterInterface {
    private ImageView ivSearch, ivCancel;
    private EditText etSearch;
    private List<Merchant> merchants;
    private List<Place> places;
    private List<BaseSearchClass> searchDataList;
    private ListView lvSearchItems;
    private CustomListAdapter customSearchListAdapter;
    private SearchResponse sr;
    private List<CategoryAPI> list;
    private TextView tvEmpty;
    private boolean emptyMerchant = false;
    private boolean emptyPlace = false;
    private String categoryId = "";
    private boolean emptyArea = false;
    private String cityId = "";
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        Bundle bundle = getIntent().getExtras();

        ivSearch = (ImageView) findViewById(R.id.iv_search);
        ivCancel = (ImageView) findViewById(R.id.iv_cancel);
        etSearch = (EditText) findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2)
                    searchDataWithoutPlace();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (etSearch.getText().length() > 0)
                        searchDataWithoutPlace();
                    else
                        showToast("Please enter some text to search.");

                    return true;
                }
                return false;
            }
        });
        lvSearchItems = (ListView) findViewById(R.id.lv_search_data);
        tvEmpty = (TextView) findViewById(R.id.empty_tv);
        merchants = new ArrayList<>();
        places = new ArrayList<>();
        searchDataList = new ArrayList<>();
        list = new ArrayList<>();
        list.addAll(CategoryAPI.getInstance());
        customSearchListAdapter = new CustomListAdapter(this, R.layout.row_search_respone_merchants, searchDataList, this);

        lvSearchItems.setAdapter(customSearchListAdapter);
        setOnClickListener(R.id.iv_search, R.id.iv_cancel, R.id.et_search);

    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);
        if (bundle != null) {
            categoryId = bundle.getString(AppConstants.BUNDLE_KEYS.CATEGORYID);
            cityId = bundle.getString(AppConstants.BUNDLE_KEYS.CITYID);
            latitude = bundle.getDouble(AppConstants.BUNDLE_KEYS.LATITUDE);
            longitude = bundle.getDouble(AppConstants.BUNDLE_KEYS.LONGITUDE);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_cancel:
                etSearch.setText("");
                break;
            case R.id.iv_search:
                if (!TextUtils.isEmpty(getEditText(R.id.et_search)) || getEditText(R.id.et_search).length() < 3)
                    searchDataWithoutPlace();
                else
                    showToast("Please enter some text to search.");
                break;
            case R.id.et_search:
                break;
        }
    }

    private void searchDataWithoutPlace() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SEARCH);
        httpParamObject.setClassType(SearchResponse.class);
        httpParamObject.addParameter("keyword", getEditText(R.id.et_search));
        httpParamObject.addParameter("category", categoryId);
        httpParamObject.addParameter("city", cityId);
        httpParamObject.addParameter("latitude", String.valueOf(latitude));
        httpParamObject.addParameter("longitude", String.valueOf(longitude));
        executeTask(AppConstants.TASKCODES.SEARCHWITHOUTPLACE, httpParamObject);
    }


    @Override
    public void showProgressDialog() {

    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (null == response) {
            showToast(getString(R.string.no_response));
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.SEARCHWITHOUTPLACE:
                sr = (SearchResponse) response;
                searchDataList.clear();
                if (sr != null) {
                    lvSearchItems.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE);

                    if (sr.getPlaces() != null && sr.getPlaces().size() > 0) {
                        for (int i = 0; i < sr.getPlaces().size(); i++) {
                            Place place = sr.getPlaces().get(i);
                            place.setType(2);
                            if (i == 0) {
                                place.setFirst(true);
                            }
                            searchDataList.add(place);
                        }
                        emptyPlace = false;
                    } else {
                        emptyPlace = true;
                    }

                    if (sr.getAreas() != null && sr.getAreas().size() > 0) {
                        for (int i = 0; i < sr.getAreas().size(); i++) {
                            Area area = sr.getAreas().get(i);
                            if (i == 0) {
                                area.setFirst(true);
                            }
                            area.setType(3);
                            searchDataList.add(area);
                        }
                        emptyArea = false;
                    } else {
                        emptyArea = true;
                    }

                    if (sr.getMerchant() != null && sr.getMerchant().size() > 0) {
                        for (int i = 0; i < sr.getMerchant().size(); i++) {
                            Merchant merchant = sr.getMerchant().get(i);
                            merchant.setType(1);
                            if (i == 0) {
                                merchant.setFirst(true);
                            }
                            searchDataList.add(merchant);
                        }
                        emptyMerchant = false;
                    } else {
                        emptyMerchant = true;
                    }

                }
                if (emptyMerchant && emptyPlace && emptyArea) {
                    lvSearchItems.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                }
                customSearchListAdapter.notifyDataSetChanged();
                break;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        BaseSearchClass bsr = searchDataList.get(position);
        if (bsr.getType() == 1) {
            Merchant merchant = (Merchant) bsr;
            resourceID = R.layout.row_search_respone_merchants;
            if (convertView == null) {
                convertView = inflater.inflate(resourceID, null);
                setDataMerchant(merchant, convertView);
            } else {
                convertView = inflater.inflate(resourceID, null);
                setDataMerchant(merchant, convertView);
            }
        } else if (bsr.getType() == 2) {
            Place place = (Place) bsr;
            resourceID = R.layout.row_search_response_place;
            if (convertView == null) {
                convertView = inflater.inflate(resourceID, null);
                setDataPlace(place, convertView);
            } else {
                convertView = inflater.inflate(resourceID, null);
                setDataPlace(place, convertView);
            }
        } else if (bsr.getType() == 3) {
            Area area = (Area) bsr;
            resourceID = R.layout.row_search_response_place;
            if (convertView == null) {
                convertView = inflater.inflate(resourceID, null);
                setDataArea(area, convertView);
            } else {
                convertView = inflater.inflate(resourceID, null);
                setDataArea(area, convertView);
            }
        }

        return convertView;
    }


    private void setDataMerchant(final Merchant merchant, View holderMerchant) {
        if (merchant.getFirst())
            holderMerchant.findViewById(R.id.tv_row_header).setVisibility(View.VISIBLE);
        else
            holderMerchant.findViewById(R.id.tv_row_header).setVisibility(View.GONE);

        final String categoryImage = findCategory(merchant.getCategory(), list);
        StringBuilder merchantPlace = new StringBuilder();
        if (!TextUtils.isEmpty(merchant.getName())) {
            TextView tv = (TextView) holderMerchant.findViewById(R.id.tv_top_all);
            tv.setText(merchant.getName().toString());
        }
        if (!TextUtils.isEmpty(merchant.getPlace_name()))
            merchantPlace.append(merchant.getPlace_name()).append(", ");
        if (!TextUtils.isEmpty(merchant.getArea_name()))
            merchantPlace.append(merchant.getArea_name());
        TextView tvOutlet = (TextView) holderMerchant.findViewById(R.id.tv_num_outlets);
        tvOutlet.setText(merchantPlace);

        if (!TextUtils.isEmpty(merchant.getTimings())) {
            AutoResizeTextView tvTime = (AutoResizeTextView) holderMerchant.findViewById(R.id.tv_time);
            tvTime.setVisibility(View.VISIBLE);
            tvTime.setText("Timing: " + merchant.getTimings());
        }

        if (!TextUtils.isEmpty(merchant.getLogo())) {
            ImageView iv = (ImageView) holderMerchant.findViewById(R.id.iv_offer_logo);
            Picasso.with(this).load("http://52.66.8.188/media/" + merchant.getLogo()).
                    placeholder(R.drawable.bigologo).into(iv);
        }
        if (merchant.getHas_new_offer())
            holderMerchant.findViewById(R.id.iv_new_offer).setVisibility(View.VISIBLE);
        else
            holderMerchant.findViewById(R.id.iv_new_offer).setVisibility(View.GONE);

        if (merchant.getHas_monthly_offer())
            holderMerchant.findViewById(R.id.civ_monthly).setVisibility(View.VISIBLE);
        else
            holderMerchant.findViewById(R.id.civ_monthly).setVisibility(View.GONE);

        ImageView iv = (ImageView) holderMerchant.findViewById(R.id.civ_category);
        if (!TextUtils.isEmpty(categoryImage))
            Picasso.with(this).load("http://52.66.8.188/media/" + categoryImage).into(iv);

        if (null != merchant.getHas_bigo_offer() && !merchant.getHas_bigo_offer())
            holderMerchant.findViewById(R.id.civ_drinks).setVisibility(View.GONE);
        else
            holderMerchant.findViewById(R.id.civ_drinks).setVisibility(View.VISIBLE);

        if (merchant.getDistance() != null) {
            TextView tv = (TextView) holderMerchant.findViewById(R.id.tv_distance);
            tv.setVisibility(View.VISIBLE);
            tv.setText(getDistance(merchant.getDistance()));
        } else
            holderMerchant.findViewById(R.id.tv_distance).setVisibility(View.GONE);

        holderMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNextActivity(merchant.getId(), categoryImage);
            }
        });
    }

    private String getDistance(Double distance) {
        if (distance < 1) {
            distance = distance * 1000;
            return distance.intValue() + " Meters";
        } else {
            return distance.intValue() + " Km";
        }
    }

    private void setDataPlace(final Place places, View holderPlace) {
        if (places.getFirst())
            holderPlace.findViewById(R.id.tv_row_header).setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(places.getName())) {
            TextView tv = (TextView) holderPlace.findViewById(R.id.tv_item_list_text);
            tv.setText(places.getName());
        }
        ImageView ivIcon = (ImageView) holderPlace.findViewById(R.id.iv_item_list_pic);
        switch (places.getPlace_type_name().toLowerCase()) {
            case "mall":
                ivIcon.setBackgroundResource(R.drawable.hotel_mall_icon);
                break;
            case "hotel":
                ivIcon.setBackgroundResource(R.drawable.hotel_circle_icon);
                break;
        }
        holderPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(getEditText(R.id.et_search)) || getEditText(R.id.et_search).length() < 3) {
                    Intent i = new Intent(SearchActivity.this, SearchPlaceMerchantActivity.class);
                    i.putExtra(AppConstants.BUNDLE_KEYS.PLACEID, places.getId());
                    i.putExtra(AppConstants.BUNDLE_KEYS.NAME, places.getName());
                    i.putExtra(AppConstants.BUNDLE_KEYS.CATEGORYID, categoryId);
                    i.putExtra(AppConstants.BUNDLE_KEYS.ISPLACE, true);
                    i.putExtra(AppConstants.BUNDLE_KEYS.LATITUDE, latitude);
                    i.putExtra(AppConstants.BUNDLE_KEYS.LONGITUDE, longitude);
                    startActivity(i);
                } else
                    showToast("Please enter a minimum of 3 characters.");
            }
        });
    }

    private void setDataArea(final Area area, View holderPlace) {
        if (emptyPlace) {
            if (area.getFirst())
                holderPlace.findViewById(R.id.tv_row_header).setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(area.getName())) {
            TextView tv = (TextView) holderPlace.findViewById(R.id.tv_item_list_text);
            tv.setText(area.getName());
        }

        ImageView ivIcon = (ImageView) holderPlace.findViewById(R.id.iv_item_list_pic);
        ivIcon.setBackgroundResource(R.drawable.circle_location_icon);

        holderPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(getEditText(R.id.et_search)) || getEditText(R.id.et_search).length() < 3) {
                    Intent i = new Intent(SearchActivity.this, SearchPlaceMerchantActivity.class);
                    i.putExtra(AppConstants.BUNDLE_KEYS.AREAID, area.getId());
                    i.putExtra(AppConstants.BUNDLE_KEYS.NAME, area.getName());
                    i.putExtra(AppConstants.BUNDLE_KEYS.CATEGORYID, categoryId);
                    i.putExtra(AppConstants.BUNDLE_KEYS.ISAREA, true);
                    i.putExtra(AppConstants.BUNDLE_KEYS.LATITUDE, latitude);
                    i.putExtra(AppConstants.BUNDLE_KEYS.LONGITUDE, longitude);
                    startActivity(i);
                } else
                    showToast("Please enter a minimum of 3 characters.");
            }
        });
    }

    private void moveToNextActivity(String merchantId, String categoryImage) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BUNDLE_KEYS.MERCHANT, merchantId);
        bundle.putString(AppConstants.BUNDLE_KEYS.CATEGORY_DATA, categoryImage);
        startNextActivity(bundle, MerchantActivity.class);
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
