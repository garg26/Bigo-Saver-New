package com.bigosaver.neerajyadav.bigosaver.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigosaver.Util.RoundedTransformation;
import com.bigosaver.neerajyadav.bigosaver.model.commonmodels.Image;
import com.bigosaver.neerajyadav.bigosaver.model.membership.MembershipPlansResponse;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.CategoryAPI;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.MerchantData;
import com.bumptech.glide.Glide;
import com.lb.auto_fit_textview.AutoResizeTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Util;

/**
 * Created by Dhillon on 19-12-2016.
 */

public class MembershipMerchantActivity extends BaseActivity implements CustomListAdapterInterface, AdapterView.OnItemClickListener {
    private String planType = "";
    private List<MerchantData> merchantDataList;
    private ListView lvMerchants;
    private CustomListAdapter customListAdapter;
    private Double latitude = 0.0, longitude = 0.0;
    private List<CategoryAPI> list;
    private String categoryImage = "";
    private MembershipPlansResponse mpr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_merchants);
        getToolbarTitle();
        merchantDataList = new ArrayList<>();
        list = new ArrayList<>();
        list.addAll(CategoryAPI.getInstance());
        lvMerchants = (ListView) findViewById(R.id.lv_merchants);
        customListAdapter = new CustomListAdapter(this, R.layout.row_all_offers, merchantDataList, this);
        getMerchants(planType);
        lvMerchants.setAdapter(customListAdapter);
//        lvMerchants.setOnItemClickListener(this);

    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);
        if (bundle != null) {
            bundle = getIntent().getExtras();
            mpr = (MembershipPlansResponse) bundle.getSerializable(AppConstants.BUNDLE_KEYS.MERCHANT_TYPE);
            planType = mpr.getName();
            latitude = bundle.getDouble(AppConstants.BUNDLE_KEYS.LATITUDE);
            longitude = bundle.getDouble(AppConstants.BUNDLE_KEYS.LONGITUDE);
        }
    }

    private void getToolbarTitle() {
        if (planType.toLowerCase().contains("gold")) {
            initToolBar("Gold Merchants");
            planType = "gold";
        } else if (planType.toLowerCase().contains("signature")) {
            initToolBar("Signature Merchants");
            planType = "signature";
        } else if (planType.toLowerCase().contains("platinum")) {
            initToolBar("Platinum Merchants");
            planType = "platinum";
        } else if (planType.toLowerCase().contains("drink")) {
            initToolBar("BIGO Drinks Merchants");
            planType = "bigo_drink";
        }
    }


    private void getMerchants(String planType) {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.GET_MEMBERSHIP_MERCHANTS);
        httpParamObject.addParameter("plan", planType);
        httpParamObject.setClassType(MerchantData.class);
        executeTask(AppConstants.TASKCODES.GET_MEMBERSHIP_MERCHANTS, httpParamObject);
    }

    @Override
    public void onPreExecute(int taskCode) {
        super.onPreExecute(taskCode);
        showProgressDialog();
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (null == response) {
            showToast(getString(R.string.no_response));
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.GET_MEMBERSHIP_MERCHANTS:
                List<MerchantData> data = (List<MerchantData>) response;
                merchantDataList.clear();
                if (data != null && data.size() > 0) {
                    merchantDataList.addAll(data);
                    customListAdapter.notifyDataSetChanged();
                }else {
                    findViewById(R.id.lv_merchants).setVisibility(View.GONE);
                    findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
                }
                break;
        }
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

        if (null != merchantDataList && merchantDataList.size() > 0) {

            StringBuilder merchantPlace = new StringBuilder();
            categoryImage = findCategory(merchantDataList.get(position).getCategory(), list);
            if (!TextUtils.isEmpty(merchantDataList.get(position).getName()))
                holder.tvName.setText(merchantDataList.get(position).getName().toString());

            if (!TextUtils.isEmpty(merchantDataList.get(position).getPlace_name()))
                merchantPlace.append(merchantDataList.get(position).getPlace_name()).append(", ");
            if (!TextUtils.isEmpty(merchantDataList.get(position).getArea_name()))
                merchantPlace.append(merchantDataList.get(position).getArea_name());

            if (!TextUtils.isEmpty(merchantPlace)) {
                holder.tvOutlets.setVisibility(View.VISIBLE);
                holder.tvOutlets.setText(merchantPlace.toString());
            } else {
                holder.tvOutlets.setVisibility(View.INVISIBLE);
            }

            if (merchantDataList.get(position).getOffer_type() != null) {
                List<String> offer_type = merchantDataList.get(position).getOffer_type();
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

            if (merchantDataList.get(position).getImages() != null) {
                List<Image> images = merchantDataList.get(position).getImages();
                if (images != null && images.size()>0) {
                    String image = images.get(0).getImage();
                    if (!TextUtils.isEmpty(image)) {
                        Glide.with(this).load(Util.getImageUrl(image))
                                .into(holder.ivMainImage);
                    }
                }
            }

            if (!TextUtils.isEmpty(merchantDataList.get(position).getOne_line())) {
                holder.tvOneLine.setText(merchantDataList.get(position).getOne_line());
                holder.tvOneLine.setVisibility(View.VISIBLE);
            } else {
                if (!TextUtils.isEmpty(merchantDataList.get(position).getDescription()))
                    holder.tvOneLine.setText(merchantDataList.get(position).getDescription());
                holder.tvOneLine.setVisibility(View.VISIBLE);
            }

            if (merchantDataList.get(position).getOffer_viewed() != null) {
                holder.tvTotalView.setText(merchantDataList.get(position).getOffer_viewed() + "");
            } else {
                holder.tvTotalView.setText("N/A");
            }

            if (merchantDataList.get(position).getTimings() != null) {
                holder.tvTime.setText(merchantDataList.get(position).getTimings() + "");
            } else {
                holder.tvTime.setText("N/A");
            }

            if (!TextUtils.isEmpty(merchantDataList.get(position).getLogo()))
                Picasso.with(this).
                        load(Util.getImageUrl(merchantDataList.get(position).getLogo())).
                        placeholder(R.drawable.bigologo)
                        .transform(new RoundedTransformation(5, 0))
                        .fit()
                        .into(holder.ivLogo);
            else {
                holder.ivLogo.setImageResource(R.drawable.bigologo);
            }

            if (merchantDataList.get(position).getHas_new_offer())
                holder.ivNew.setVisibility(View.VISIBLE);
            else
                holder.ivNew.setVisibility(View.GONE);

            if (null == merchantDataList.get(position).getHas_monthly_offer() || !merchantDataList.get(position).getHas_monthly_offer())
                holder.ivMonthly.setVisibility(View.GONE);
            else
                holder.ivMonthly.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(categoryImage))
                Picasso.with(this).load(Util.getImageUrl(categoryImage))
                        .placeholder(R.drawable.appiconlogo)
                        .into(holder.ivCategory);

            if (null == merchantDataList.get(position).getHas_bigo_offer() || !merchantDataList.get(position).getHas_bigo_offer())
                holder.ivDrink.setVisibility(View.GONE);
            else
                holder.ivDrink.setVisibility(View.VISIBLE);

            Double distance = merchantDataList.get(position).getDistance();
            if (distance != null) {
                String dString = getDistance(distance);
                holder.tvDistance.setVisibility(View.VISIBLE);
                holder.tvDistance.setText(dString);
            } else {
                holder.tvDistance.setVisibility(View.VISIBLE);
                holder.tvDistance.setText("N/A");
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.BUNDLE_KEYS.MERCHANT, merchantDataList.get(position).getId());
                bundle.putString(AppConstants.BUNDLE_KEYS.CATEGORY_DATA, categoryImage);
                startNextActivity(bundle, MerchantActivity.class);
            }
        });
        return convertView;
    }

    private String findCategory(String data, List<CategoryAPI> list) {
        for (CategoryAPI c : list) {
            if (c.getId().equals(data)) {
                return c.getMobileImage();
            }
        }
        return "";
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
//        Bundle bundle = new Bundle();
//        bundle.putString(AppConstants.BUNDLE_KEYS.MERCHANT, merchantDataList.get(position).getId());
//        bundle.putString(AppConstants.BUNDLE_KEYS.CATEGORY_DATA, categoryImage);
//        startNextActivity(bundle, MerchantActivity.class);
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
            ivMonthly = (ImageView) v.findViewById(R.id.civ_monthly);
            ivDrink = (ImageView) v.findViewById(R.id.civ_drinks);
            ivCategory = (ImageView) v.findViewById(R.id.civ_category);
            tvTotalView = (AutoResizeTextView) v.findViewById(R.id.tv_view);
            tvTime = (AutoResizeTextView) v.findViewById(R.id.tv_time);
            tvOneLine = (AutoResizeTextView) v.findViewById(R.id.tv_one_line);
            ivOption1 = (ImageView) v.findViewById(R.id.iv_option1);
            ivOption2 = (ImageView) v.findViewById(R.id.iv_option2);
            ivOption3 = (ImageView) v.findViewById(R.id.iv_option3);
            ivOption4 = (ImageView) v.findViewById(R.id.iv_option4);
            ivMainImage = (ImageView) v.findViewById(R.id.iv_main_image);
        }
    }
}