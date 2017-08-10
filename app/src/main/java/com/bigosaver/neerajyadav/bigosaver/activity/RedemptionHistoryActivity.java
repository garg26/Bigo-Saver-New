package com.bigosaver.neerajyadav.bigosaver.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.RedeemResponse.RedemptionHistoryResponse;
import com.bigosaver.neerajyadav.bigosaver.model.User.UpdateProfileResponse;
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
 * Created by Dhillon on 23-11-2016.
 */

public class RedemptionHistoryActivity extends BaseActivity implements CustomListAdapterInterface {
    private TextView tvEmpty, tvOffersRedeemed, tvSaved, tvMerchantVisited;
    private ListView lvRedemptionHistory;
    private List<RedemptionHistoryResponse> redeemList;
    private CustomListAdapter customListAdapter;
    private UpdateProfileResponse updateProfileResponse;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redemption_history);
        initToolBar(getString(R.string.redemtion_history));
        updateProfileResponse = UpdateProfileResponse.getRunningInstance();
        tvEmpty = (TextView) findViewById(R.id.tv_empty);
        headerView = (View) getLayoutInflater().inflate(R.layout.row_redemption_header, null, false);
        setHeaderData(headerView, updateProfileResponse);
        headerView.setVisibility(View.GONE);
        lvRedemptionHistory = (ListView) findViewById(R.id.lv_redemption_list);
        lvRedemptionHistory.addHeaderView(headerView);
        redeemList = new ArrayList<>();
        customListAdapter = new CustomListAdapter(this, R.layout.row_search_respone_merchants, redeemList, this);
        lvRedemptionHistory.setAdapter(customListAdapter);
        getRedemptionHistory();
    }

    private void setHeaderData(View headerView, UpdateProfileResponse updateProfileResponse) {
        if (updateProfileResponse != null) {
            if (null != updateProfileResponse.getMerchant_visited()) {
                tvMerchantVisited = (TextView) headerView.findViewById(R.id.tv_merchant_visited);
                tvMerchantVisited.setText(updateProfileResponse.getMerchant_visited().toString());
            }
            if (null != updateProfileResponse.getTotal_redemption()) {
                tvOffersRedeemed = (TextView) headerView.findViewById(R.id.tv_offers_redeemed);
                tvOffersRedeemed.setText(updateProfileResponse.getTotal_redemption().toString());
            }
            if (null != updateProfileResponse.getSavings()) {
                tvSaved = (TextView) headerView.findViewById(R.id.tv_saved);
                tvSaved.setText(updateProfileResponse.getSavings().toString());
            }
        }
    }

    private void getRedemptionHistory() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.REDEMPTIONHISTORY);
        httpParamObject.setClassType(RedemptionHistoryResponse.class);
        executeTask(AppConstants.TASKCODES.REDEMPTIONHISTORY, httpParamObject);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (null == response) {
            showToast(getString(R.string.no_response));
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.REDEMPTIONHISTORY:
                List<RedemptionHistoryResponse> redemptionHistory = (List<RedemptionHistoryResponse>) response;
                if (null != redemptionHistory && redemptionHistory.size() > 0) {
                    redeemList.clear();
                    redeemList.addAll(redemptionHistory);
                    headerView.setVisibility(View.VISIBLE);
                    customListAdapter.notifyDataSetChanged();
                } else {
                    lvRedemptionHistory.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                }
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
        final RedemptionHistoryResponse rhr = redeemList.get(position);
        if (rhr != null) {
            StringBuilder merchantPlace = new StringBuilder();
            if (!TextUtils.isEmpty(rhr.getArea()))
                merchantPlace.append(rhr.getArea());

            holder.tvOutlets.setText(merchantPlace.toString());

            if(!TextUtils.isEmpty(rhr.getMerchant()))
                holder.tvName.setText(rhr.getMerchant());

            if (!TextUtils.isEmpty(rhr.getMerchant_logo())) {
                Picasso.with(this).load("http://52.66.8.188/media/" + rhr.getMerchant_logo()).
                        placeholder(R.drawable.appicon).into(holder.ivLogo);
            }
//            final String categoryImage = findCategory(rhr.getCategory(), list);
//            if (!TextUtils.isEmpty(categoryImage))
//                Picasso.with(this).load("http://52.66.8.188/media/" + categoryImage).into(holder.ivCategory);

            if (!TextUtils.isEmpty(rhr.getSavings())) {
                holder.tvDistance.setVisibility(View.VISIBLE);
                holder.tvDistance.setText("Savings INR " + rhr.getSavings());
            } else {
                holder.tvDistance.setVisibility(View.INVISIBLE);
            }

            if (!TextUtils.isEmpty(rhr.getOffer_title())) {
                holder.tvOfferTitle.setVisibility(View.VISIBLE);
                holder.tvOfferTitle.setText(rhr.getOffer_title());
            } else {
                holder.tvOfferTitle.setVisibility(View.GONE);
            }

            holder.ivDrink.setVisibility(View.GONE);
            holder.ivNext.setVisibility(View.GONE);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(R.layout.dialog_redeem_list, rhr);
                }
            });
    }

        return convertView;
    }

    private void showDialog(int redeem_offer, RedemptionHistoryResponse rhr) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(redeem_offer);

        ImageView closeButton = (ImageView) dialog.findViewById(R.id.iv_cancel_redeem);
        ImageView ivLogo = (ImageView) dialog.findViewById(R.id.iv_merchant_logo);
        TextView tvReferenceNo = (TextView) dialog.findViewById(R.id.tv_reference_no);
        TextView tvOffer = (TextView) dialog.findViewById(R.id.tv_offer);
        TextView tvRedemptionDate = (TextView) dialog.findViewById(R.id.tv_redemption_date);
        TextView tvSaved = (TextView) dialog.findViewById(R.id.tv_savings);
        TextView tvMerchantName = (TextView) dialog.findViewById(R.id.tv_merchant_name);
//        TextView tv_tnc = (TextView) dialog.findViewById(R.id.font_tv_footer);
//        SpannableString spannableString = new SpannableString(getString(R.string.offers_are_sub));
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                Bundle bundle = new Bundle();
//                bundle.putBoolean(AppConstants.BUNDLE_KEYS.RULES, true);
//                startNextActivity(bundle,EULA.class);
//            }
//
//        };
//        spannableString.setSpan(new UnderlineSpan(), 24, 44, 0);
//        spannableString.setSpan(clickableSpan, 24, 44, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 24, 44, 0);
//        tv_tnc.setMovementMethod(LinkMovementMethod.getInstance());
//        tv_tnc.setText(spannableString);

        if (!TextUtils.isEmpty(rhr.getReference_number()))
            tvReferenceNo.setText(rhr.getReference_number());

        if (rhr.getSavings() != null)
            tvSaved.setText(rhr.getSavings());

        if (!TextUtils.isEmpty(rhr.getMerchant_logo())) {
            Picasso.with(this).load("http://52.66.8.188/media/" + rhr.getMerchant_logo()).
                    placeholder(R.drawable.appicon).into(ivLogo);
        }
        if (!TextUtils.isEmpty(rhr.getOffer_title()))
            tvOffer.setText(rhr.getOffer_title());

        if (!TextUtils.isEmpty(rhr.getOffer_title()))
            tvMerchantName.setText(rhr.getOffer_title());

        if (!TextUtils.isEmpty(rhr.getCreated_at()))
            tvRedemptionDate.setText(Util.convertDateFormat(rhr.getCreated_at(), Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-"," "));

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    class Holder {
        TextView tvName, tvOutlets, tvDistance, tvTop, tvOfferTitle;
        ImageView ivLogo, ivNew, ivCategory, ivMonthly, ivDrink, ivNext;

        public Holder(View v) {
            tvTop = (TextView) v.findViewById(R.id.tv_row_header);
            tvName = (TextView) v.findViewById(R.id.tv_top_all);
            tvOutlets = (TextView) v.findViewById(R.id.tv_num_outlets);
            tvOfferTitle = (TextView) v.findViewById(R.id.tv_offer_title);
            tvDistance = (TextView) v.findViewById(R.id.tv_distance);
            ivLogo = (ImageView) v.findViewById(R.id.iv_offer_logo);
            ivNew = (ImageView) v.findViewById(R.id.iv_new_offer);
            ivMonthly = (ImageView) v.findViewById(R.id.civ_monthly);
            ivDrink = (ImageView) v.findViewById(R.id.civ_drinks);
            ivCategory = (ImageView) v.findViewById(R.id.civ_category);
            ivNext = (ImageView) v.findViewById(R.id.iv_next);
        }
    }
}
