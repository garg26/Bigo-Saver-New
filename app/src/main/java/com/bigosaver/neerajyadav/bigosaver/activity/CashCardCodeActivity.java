package com.bigosaver.neerajyadav.bigosaver.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bigosaver.neerajyadav.bigosaver.model.User.UpdateProfileResponse;
import com.bigosaver.neerajyadav.bigosaver.model.cashcard.CashCardResponse;
import com.bigosaver.neerajyadav.bigosaver.model.cashcard.ImplementCashCard;
import com.bigosavers.R;

import org.json.JSONException;
import org.json.JSONObject;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Util;

public class CashCardCodeActivity extends BaseActivity {
    private Button btn_apply;
    private View layout_enter_code, layout_code_detail, layout_bigo_drink, layout_upgrade;
    private UpdateProfileResponse profile;
    private CashCardResponse ccr;
    private Boolean isDrink = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_card_code);
        profile = UpdateProfileResponse.getRunningInstance();
        btn_apply = (Button) findViewById(R.id.btn_apply);
        if (isDrink)
            setText("DRINKS4U", R.id.et_cash_card);
        else
            setText("", R.id.et_cash_card);

        layout_enter_code = findViewById(R.id.layout_enter_code);
        layout_code_detail = findViewById(R.id.layout_code_details);
        layout_bigo_drink = findViewById(R.id.layout_bigo_drink);
        layout_upgrade = findViewById(R.id.layout_upgrade);
        setOnClickListener(R.id.btn_apply, R.id.btn_confirm, R.id.btn_done, R.id.btn_done_upgrade);
    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);
        if (bundle != null) {
            bundle = getIntent().getExtras();
            isDrink = (Boolean) bundle.get(AppConstants.BUNDLE_KEYS.DRINKS);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_apply:
                if (isValid())
                    useCashCard();
                break;
            case R.id.btn_confirm:
                CheckBox cb = (CheckBox) findViewById(R.id.cb_age_confirmation);
                if (cb.getVisibility() != View.GONE)
                    if (!cb.isChecked()) {
                        showToast("Please accept the above check box");
                        return;
                    }
                implementCashCard();
                break;
            case R.id.btn_done:
                finish();
                break;
            case R.id.btn_done_upgrade:
                finish();
                break;
        }

    }

    private void implementCashCard() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.IMPLEMENTCASHCARD);
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        httpParamObject.setJson(getCashCard().toString());
        httpParamObject.setClassType(ImplementCashCard.class);
        executeTask(AppConstants.TASKCODES.IMPLEMENTCASHCARD, httpParamObject);
    }

    private boolean isValid() {
        String cashcard = getEditText(R.id.et_cash_card);
        if (TextUtils.isEmpty(cashcard)) {
            showToast(getString(R.string.cannot_empty));
            return false;
        }
        return true;
    }

    private void useCashCard() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.CASHCARDUSE);
        httpParamObject.setClassType(CashCardResponse.class);
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        httpParamObject.setJson(getCashCard().toString());
        executeTask(AppConstants.TASKCODES.CASHCARDUSE, httpParamObject);
    }


    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (null == response) {
            showToast("Cash Card not found");
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.CASHCARDUSE:
                ccr = (CashCardResponse) response;
                if (ccr != null && ccr.getStatus()) {
                    layout_enter_code.setVisibility(View.GONE);
                    layout_code_detail.setVisibility(View.VISIBLE);
                    setCashCardData(ccr);
                } else {
                    if (!TextUtils.isEmpty(ccr.getMessage()))
                        showToast(ccr.getMessage());
                }
                break;
            case AppConstants.TASKCODES.IMPLEMENTCASHCARD:
                ImplementCashCard icc = (ImplementCashCard) response;
                if (icc != null) {
                    layout_code_detail.setVisibility(View.GONE);
                    if (ccr.getIs_bigo())
                        layout_bigo_drink.setVisibility(View.VISIBLE);
                    else {
                        layout_upgrade.setVisibility(View.VISIBLE);
                        String membership = icc.getMembership_type();
                        ImageView ivMembership = (ImageView) findViewById(R.id.iv_membership);
                        if (membership.toLowerCase().contains("gold"))
                            ivMembership.setBackgroundResource(R.drawable.gold_member_icon);
                        else if (membership.toLowerCase().contains("signature"))
                            ivMembership.setBackgroundResource(R.drawable.signature_member_icon);
                        else if (membership.toLowerCase().contains("platinum"))
                            ivMembership.setBackgroundResource(R.drawable.platinum_member_icon);
                        if (!TextUtils.isEmpty(membership))
                            setText(membership, R.id.tv_upgraded);
                        if (!TextUtils.isEmpty(icc.getMembership_expiry_date()))
                            setText(Util.convertDateFormat(icc.getMembership_expiry_date(), "yyyy-MM-dd", "dd-MMM-yyyy").replace("-", " ")
                                    , R.id.tv_valid_till);
                    }
                    showToast("Cashcard applied successfully");
                }
                break;
        }
    }

    private void setCashCardData(CashCardResponse ccr) {
        if (!ccr.getIs_bigo()) {
            findViewById(R.id.cb_age_confirmation).setVisibility(View.GONE);
            findViewById(R.id.tv_bigo_drink).setVisibility(View.GONE);
            findViewById(R.id.rl_validity).setVisibility(View.VISIBLE);
            findViewById(R.id.rl_upgrade).setVisibility(View.VISIBLE);
            findViewById(R.id.rl_upgradedto).setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(ccr.getNew_plan()))
                setText(ccr.getNew_plan(), R.id.tv_upgrade);
        }
//        if (profile.getFirst_name() != null && !TextUtils.isEmpty(profile.getFirst_name()))
//            setText("Hello " + profile.getFirst_name() + ",", R.id.tv_name);
        if (profile.getMembership_expired_at() != null && !TextUtils.isEmpty(profile.getMembership_expired_at()))
            setText(Util.convertDateFormat(profile.getMembership_expired_at(), "yyyy-MM-dd", "dd-MMM-yyyy").replace("-", " ")
                    , R.id.tv_validity);
        else{
            findViewById(R.id.tv_validity).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(ccr.getCurrrent_plan()))
            setText(ccr.getCurrrent_plan(), R.id.tv_current_membership);
        if (!TextUtils.isEmpty(ccr.getCode()))
            setText(ccr.getCode(), R.id.tv_cash_card_code);
        if (!TextUtils.isEmpty(ccr.getValidity()))
            setText(ccr.getValidity(), R.id.tv_upgradedto);

    }

    private JSONObject getCashCard() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", getEditText(R.id.et_cash_card).toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
