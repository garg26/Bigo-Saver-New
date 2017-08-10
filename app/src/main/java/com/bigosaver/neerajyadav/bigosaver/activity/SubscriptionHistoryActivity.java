package com.bigosaver.neerajyadav.bigosaver.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.subscriptionhistory.SubscriptionHistoryResponse;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Util;

/**
 * Created by Dhillon on 17-12-2016.
 */

public class SubscriptionHistoryActivity extends BaseActivity implements CustomListAdapterInterface {
    private ListView lvSubscriptionHistory;
    private CustomListAdapter subscriptionAdapter;
    private List<SubscriptionHistoryResponse> subscriptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_history);
        initToolBar(getString(R.string.subcription));
        lvSubscriptionHistory = (ListView) findViewById(R.id.lv_subscription_list);
        subscriptionList = new ArrayList<>();
        subscriptionAdapter = new CustomListAdapter(this, R.layout.row_search_respone_merchants, subscriptionList, this);
        lvSubscriptionHistory.setAdapter(subscriptionAdapter);
        getSubscriptionHistory();
    }

    private void getSubscriptionHistory() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SUBSCRIPTION_HISTORY);
        httpParamObject.setClassType(SubscriptionHistoryResponse.class);
        executeTask(AppConstants.TASKCODES.SUBSCRIPTION_HISTORY, httpParamObject);
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
            case AppConstants.TASKCODES.SUBSCRIPTION_HISTORY:
                List<SubscriptionHistoryResponse> subscriptionHistory = (List<SubscriptionHistoryResponse>) response;
                if (null != subscriptionHistory && subscriptionHistory.size() > 0) {
                    subscriptionList.clear();
                    subscriptionList.addAll(subscriptionHistory);
                    subscriptionAdapter.notifyDataSetChanged();
                } else {
                    lvSubscriptionHistory.setVisibility(View.GONE);
                    findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
                }
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_membership_subscription, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        SubscriptionHistoryResponse sbr = subscriptionList.get(position);

        if (sbr != null) {
            if (null != sbr.getMembership_type() && !TextUtils.isEmpty(sbr.getMembership_type())) {
                holder.tvName.setText(sbr.getMembership_type());
                setMembershipIcon(sbr.getMembership_type(), holder.ivLogo);
            }

            if (null != sbr.getActual_amount()) {
                holder.tvActualPrice.setText("Actual Price: ₹" + String.valueOf(sbr.getActual_amount()));
            } else {
                holder.tvActualPrice.setVisibility(View.GONE);
            }

            if (null != sbr.getDiscount_amount()) {
                holder.tvDiscount.setText("You paid: ₹" + String.valueOf(sbr.getDiscount_amount()));
            } else {
                holder.tvDiscount.setVisibility(View.GONE);
            }

            if (null != sbr.getEnd_date() && !TextUtils.isEmpty(sbr.getEnd_date())) {
                holder.tvExpiration.setText(Util.convertDateFormat(sbr.getEnd_date(), "yyyy-MM-dd", "dd-MMM-yyyy").replace("-", " "));
            } else {
                holder.tvExpiration.setVisibility(View.GONE);
            }

        }


        return convertView;
    }

    private void setMembershipIcon(String membership_type, ImageView ivLogo) {
        if (membership_type.toLowerCase().contains("gold"))
            ivLogo.setImageResource(R.drawable.gold_member_icon);
        else if (membership_type.toLowerCase().contains("signature"))
            ivLogo.setImageResource(R.drawable.signature_member_icon);
        else if (membership_type.toLowerCase().contains("platinum"))
            ivLogo.setImageResource(R.drawable.platinum_member_icon);
    }

    class Holder {
        TextView tvName, tvActualPrice, tvDiscount, tvExpiration;
        ImageView ivLogo;

        public Holder(View v) {
            tvName = (TextView) v.findViewById(R.id.tv_top_all);
            ivLogo = (ImageView) v.findViewById(R.id.iv_offer_logo);
            tvActualPrice = (TextView) v.findViewById(R.id.tv_actual_price);
            tvDiscount = (TextView) v.findViewById(R.id.tv_discount_price);
            tvExpiration = (TextView) v.findViewById(R.id.tv_expiration_date);
        }
    }
}
