package com.bigosaver.neerajyadav.bigosaver.activity;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.membership.BigoDrinkResponse;
import com.bigosaver.neerajyadav.bigosaver.model.membership.MembershipPlansResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.FusedLocationService;

/**
 * Created by Dhillon on 28-11-2016.
 */

public class SubscribeMembershipActivity extends BaseActivity implements CustomListAdapterInterface {
    private ListView lv_cart;
    CustomListAdapter customListAdapter;
    private List<MembershipPlansResponse> planList;
    private List offerList;
    private TextView tvEmpty, tvOffer, tvAlcoholicOffers;
    private View footerView;
    private String text;
    private RelativeLayout rlDrinksMerchants;
    private MembershipPlansResponse plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_membership);
        planList = new ArrayList<>();
        lv_cart = (ListView) findViewById(R.id.lv_cart);
        tvEmpty = (TextView) findViewById(R.id.tv_empty);
        customListAdapter = new CustomListAdapter(this, R.layout.row_cart, planList, this);
        footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_cart_drink, null, false);
        tvOffer = (TextView) footerView.findViewById(R.id.tv_offer);
        tvAlcoholicOffers = (TextView) footerView.findViewById(R.id.tv_alcoholic_offers);
        rlDrinksMerchants = (RelativeLayout) footerView.findViewById(R.id.rl_drinks_merchants);
        footerView.setVisibility(View.GONE);
        lv_cart.addFooterView(footerView);
        lv_cart.setAdapter(customListAdapter);
        rlDrinksMerchants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConstants.BUNDLE_KEYS.MERCHANT_TYPE, plan);
                FusedLocationService.MyLocation location = FusedLocationService.getLatestLocation();
                if (location != null) {
                    bundle.putDouble("latitude", location.getLatitude());
                    bundle.putDouble("longitude", location.getLongitude());
                }
                startNextActivity(bundle, MembershipMerchantActivity.class);
            }
        });
        setOnClickListener(R.id.tv_subscribe);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getCartData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_subscribe:
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstants.BUNDLE_KEYS.DRINKS, true);
                startNextActivity(bundle, CashCardCodeActivity.class);
                break;
        }
    }

    private void getBigoDrinkData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.BIGODRINKS);
        httpParamObject.setClassType(BigoDrinkResponse.class);
        executeTask(AppConstants.TASKCODES.BIGODRINKS, httpParamObject);
    }

    private void getCartData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.MEMBERSHIPPLAN);
        httpParamObject.setClassType(MembershipPlansResponse.class);
        executeTask(AppConstants.TASKCODES.MEMBERSHIPPLAN, httpParamObject);
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
            case AppConstants.TASKCODES.MEMBERSHIPPLAN:
                List<MembershipPlansResponse> mpr = (List<MembershipPlansResponse>) response;
                if (mpr != null || mpr.size() > 0) {
                    planList.clear();
                    planList.addAll(mpr);
                    customListAdapter.notifyDataSetChanged();
                } else {
                    lv_cart.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                }
                getBigoDrinkData();
                break;
            case AppConstants.TASKCODES.BIGODRINKS:
                BigoDrinkResponse bdr = (BigoDrinkResponse) response;
                if (bdr != null) {
                    footerView.setVisibility(View.VISIBLE);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Enjoy over ").append(bdr.getSignature_offer_count()).append(" offers at ").append(bdr.getMerchant_count()).append(" best restaurants, loungers, breweries, bars and nightspots.");
                    tvOffer.setText(stringBuilder.toString());
                    tvAlcoholicOffers.setText(String.valueOf(bdr.getSignature_offer_count()));
                }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        final Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourceID, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        plan = planList.get(position);

        if (!TextUtils.isEmpty(plan.getName()))
            holder.tvTitle.setText(plan.getName());
        if (!TextUtils.isEmpty(plan.getImage()))
            Picasso.with(this).load(AppConstants.PAGE_URL.PHOTO_URL + plan.getImage())
                    .placeholder(R.drawable.circle_member)
                    .into(holder.ivLogo);
        if (null != plan.getOffer_food_beverages())
            holder.tvFood.setText(plan.getOffer_food_beverages().toString());
        if (null != plan.getOffer_health_beauty())
            holder.tvHealth.setText(plan.getOffer_health_beauty().toString());
        if (null != plan.getOffer_hotel_resort().toString())
            holder.tvHotel.setText(plan.getOffer_hotel_resort().toString());
        if (null != plan.getOffer_retail_service())
            holder.tvRetail.setText(plan.getOffer_retail_service().toString());
        if (null != plan.getOffer_things_todo())
            holder.tvTodo.setText(plan.getOffer_things_todo().toString());
        if (null != plan.getOffers())
            holder.tvDesc.setText(getString(R.string.desc_text1) + plan.getOffers().toString() + " offers at " + plan.getMerchants()
                    + getString(R.string.desc_text2));
        holder.btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConstants.BUNDLE_KEYS.MEMBERSHIPID, plan);
                startNextActivity(bundle, MembershipSubscribeActivity.class);
            }
        });

        holder.rlMembershipMerchants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConstants.BUNDLE_KEYS.MERCHANT_TYPE, plan);
                FusedLocationService.MyLocation location = FusedLocationService.getLatestLocation();
                if (location != null) {
                    bundle.putDouble("latitude", location.getLatitude());
                    bundle.putDouble("longitude", location.getLongitude());
                }
                startNextActivity(bundle, MembershipMerchantActivity.class);
            }
        });
        return convertView;

    }

    private class Holder {
        TextView tvTitle, tvDesc, tvFood, tvHotel, tvRetail, tvTodo, tvHealth, btnSubscribe;
        ImageView ivLogo;
        RelativeLayout rlMembershipMerchants;


        public Holder(View v) {
            tvDesc = (TextView) v.findViewById(R.id.tv_membership_description);
            tvTitle = (TextView) v.findViewById(R.id.tv_membership_title);
            tvFood = (TextView) v.findViewById(R.id.tv_food_text);
            tvHotel = (TextView) v.findViewById(R.id.tv_hotel_text);
            tvHealth = (TextView) v.findViewById(R.id.tv_health_text);
            tvTodo = (TextView) v.findViewById(R.id.tv_todo_text);
            tvRetail = (TextView) v.findViewById(R.id.tv_retail_text);
            ivLogo = (ImageView) v.findViewById(R.id.iv_membership);
            btnSubscribe = (TextView) v.findViewById(R.id.tv_subscribe);
            rlMembershipMerchants = (RelativeLayout) v.findViewById(R.id.rl_membership_merchants);
        }
    }
}