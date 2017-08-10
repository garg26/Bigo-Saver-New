package com.bigosaver.neerajyadav.bigosaver.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.activity.CashCardCodeActivity;
import com.bigosaver.neerajyadav.bigosaver.activity.ChangePasswordActivity;
import com.bigosaver.neerajyadav.bigosaver.activity.EULA;
import com.bigosaver.neerajyadav.bigosaver.activity.MyInformationActivity;
import com.bigosaver.neerajyadav.bigosaver.activity.RedemptionHistoryActivity;
import com.bigosaver.neerajyadav.bigosaver.activity.SplashActivity;
import com.bigosaver.neerajyadav.bigosaver.activity.SubscribeMembershipActivity;
import com.bigosaver.neerajyadav.bigosaver.activity.SubscriptionHistoryActivity;
import com.bigosaver.neerajyadav.bigosaver.activity.TutorialActivity;
import com.bigosaver.neerajyadav.bigosaver.model.Account;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

/**
 * Created by Neeraj Yadav on 9/28/2016.
 */

public class AccountSettingFragment extends BaseFragment implements CustomListAdapterInterface {
    List<Account> accountList = new ArrayList<>();
    private CustomListAdapter customListAdapter;
    private ListView lv_account;
    String[] accountInfo = {"My Information", "Enter Cash Card Code", "Subscribe Membership", "How to Use the App", "Subscription History",
            "Redemption History", "Rules & Guidelines", "End User License Agreement", "Change Password", "Log Out"};

    @Override
    public void initViews() {
        lv_account = (ListView) findView(R.id.lv_account);
        customListAdapter = new CustomListAdapter(getActivity(), R.layout.row_account_setting, accountList, this);
        lv_account.setAdapter(customListAdapter);
        setData();
    }


    private void setData() {
        for (int x = 0; x < accountInfo.length; x++) {
            Account account = new Account();
            account.setTitle(accountInfo[x]);
            accountList.add(account);
        }
        customListAdapter.notifyDataSetChanged();
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_account_setting;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        final Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourceID, parent, false);
            holder = new AccountSettingFragment.Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AccountSettingFragment.Holder) convertView.getTag();
        }
        Account account = accountList.get(position);
        holder.tv_title.setText(account.getTitle());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle;
                switch (position) {
                    case 0:
                        startNextActivity(MyInformationActivity.class);
                        break;
                    case 1:
                        bundle = new Bundle();
                        bundle.putBoolean(AppConstants.BUNDLE_KEYS.DRINKS, false);
                        startNextActivity(bundle, CashCardCodeActivity.class);
                        break;
                    case 2:
                        startNextActivity(SubscribeMembershipActivity.class);
                        break;
                    case 3:
                        startNextActivity(TutorialActivity.class);
                        break;
                    case 4:
                        startNextActivity(SubscriptionHistoryActivity.class);
                        break;
                    case 5:
                        startNextActivity(RedemptionHistoryActivity.class);
                        break;
                    case 6:
                        bundle = new Bundle();
                        bundle.putBoolean(AppConstants.BUNDLE_KEYS.RULES, true);
                        startNextActivity(bundle, EULA.class);
                        break;
                    case 7:
                        bundle = new Bundle();
                        bundle.putBoolean(AppConstants.BUNDLE_KEYS.EULA, true);
                        startNextActivity(bundle, EULA.class);
                        break;
                    case 8:
                        startNextActivity(ChangePasswordActivity.class);
                        break;
                    case 9:
                        logoutUser();
                        break;
                }
            }
        });
        return convertView;
    }

    private void logoutUser() {
        Preferences.deleteAllData();
        Intent i = new Intent(getActivity(), SplashActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
        getActivity().finish();
    }

    class Holder {
        TextView tv_title;

        public Holder(View view) {
            tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
