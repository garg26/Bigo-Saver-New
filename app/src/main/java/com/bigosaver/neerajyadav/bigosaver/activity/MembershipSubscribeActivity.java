package com.bigosaver.neerajyadav.bigosaver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.User.UpdateProfileResponse;
import com.bigosaver.neerajyadav.bigosaver.model.cashcard.ImplementCashCard;
import com.bigosaver.neerajyadav.bigosaver.model.membership.MembershipPlans;
import com.bigosaver.neerajyadav.bigosaver.model.membership.MembershipPlansResponse;
import com.bigosaver.neerajyadav.bigosaver.model.membership.PayuParams;
import com.bigosaver.neerajyadav.bigosaver.model.promocode.PromoCardResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Util;

/**
 * Created by Dhillon on 19-11-2016.
 */

public class MembershipSubscribeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private String membershipId, toBePaidAmount, validity = "", promocode = "";
    private RadioGroup radioGroup;
    private RadioButton rbMonthly, rbYearly;
    private MembershipPlansResponse mpr;
    private ImageView ivLogo;
    private String var1;
    private String key = "Dpdk1BmA";
    private String salt = "Io2mnMHJvG";
    private Intent intent;
    private EditText etPromo;
    private TextView tvDisocuntAmount;
    private TextView tvPromoApplied;
    private int selected = 1;
    private String txnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_subscribe);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        rbMonthly = (RadioButton) findViewById(R.id.radio_monthly);
        rbYearly = (RadioButton) findViewById(R.id.radio_yearly);
        ivLogo = (ImageView) findViewById(R.id.iv_membership);
        etPromo = (EditText) findViewById(R.id.et_enter_promo_code);
        tvDisocuntAmount = (TextView) findViewById(R.id.tv_subscription_amount);
        tvPromoApplied = (TextView) findViewById(R.id.tv_promo_message);

        radioGroup.setOnCheckedChangeListener(this);
        Bundle bundle = getIntent().getExtras();

        if (mpr != null)
            setData(mpr);
//        getMembershipData();
        setOnClickListener(R.id.tv_subscribe, R.id.btn_apply_promo, R.id.btn_done_upgrade);
    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);
        if (bundle != null)
            mpr = (MembershipPlansResponse) bundle.getSerializable(AppConstants.BUNDLE_KEYS.MEMBERSHIPID);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_subscribe:
                startPaymentPage();
                break;
            case R.id.btn_apply_promo:
                if (!TextUtils.isEmpty(etPromo.getText()))
                    usePromoCode();
                else
                    showToast(getString(R.string.promo_code_message));
                break;

            case R.id.btn_done_upgrade:
                finish();
                break;
        }
    }

    private void usePromoCode() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.PROMOCARDUSE);
        httpParamObject.setClassType(PromoCardResponse.class);
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        httpParamObject.setJson(getPromoDetails().toString());
        executeTask(AppConstants.TASKCODES.PROMOCARDUSE, httpParamObject);
    }

    private JSONObject getPromoDetails() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("plan_id", mpr.getId());
            jsonObject.put("promo_code", etPromo.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    private void startPaymentPage() {
        Bundle b = new Bundle();
        b.putSerializable(AppConstants.BUNDLE_KEYS.KEY_SERIALIZABLE_OBJECT, makePayuParams());
        Intent i = new Intent(this, PaymentActivity.class);
        i.putExtras(b);
        startActivityForResult(i, AppConstants.REQUEST_CODES.PAYMENT);
    }

    private PayuParams makePayuParams() {
        PayuParams p = new PayuParams();
//        p.setAmount((mpr.getMonthly_price().toString()));
        p.setAmount(toBePaidAmount);
        txnId = new Date().getTime() + "";
        p.setTxnId(txnId);
        UpdateProfileResponse response = UpdateProfileResponse.getRunningInstance();
        if (response != null) {
            p.setEmail(response.getEmail());
            p.setFirstname(response.getFirst_name());
            p.setPhone(response.getPhone());
        } else {
            p.setEmail("");
            p.setFirstname("");
            p.setPhone("");
        }
        p.setProductInfo(mpr.getName());
        return p;
    }

    private void getMembershipData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.MEMBERSHIPPLAN + membershipId);
        httpParamObject.setClassType(MembershipPlans.class);
        executeTask(AppConstants.TASKCODES.MEMBERSHIPPLANWITHID, httpParamObject);
    }

    @Override
    public void onPreExecute(int taskCode) {
        super.onPreExecute(taskCode);
        showProgressDialog();
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (response == null) {
            showToast(getString(R.string.no_response));
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.MEMBERSHIPPLANWITHID:
                MembershipPlans mpr = (MembershipPlans) response;
                if (null != mpr) {
//                    setData(mpr);
                }
                break;
            case AppConstants.TASKCODES.PROMOCARDUSE:
                PromoCardResponse pcr = (PromoCardResponse) response;
                if (null != pcr) {
                    promocode = etPromo.getText().toString();
                    setPromoData(pcr);
                }
                break;
            case AppConstants.TASKCODES.IMPLEMENTPROMOCODE:
                ImplementCashCard icc = (ImplementCashCard) response;
                if (icc != null) {
                    findViewById(R.id.rl_subscribe).setVisibility(View.GONE);
                    findViewById(R.id.rl_upgrade).setVisibility(View.VISIBLE);
                    setUpgradeData(icc);
                }
                break;
        }
    }

    private void setUpgradeData(ImplementCashCard icc) {
        String membership = icc.getMembership_type();
        ImageView ivMembership = (ImageView) findViewById(R.id.iv_membership);
        if (membership.toLowerCase().contains("gold"))
            ivMembership.setImageResource(R.drawable.gold_member_icon);
        else if (membership.toLowerCase().contains("signature"))
            ivMembership.setImageResource(R.drawable.signature_member_icon);
        else if (membership.toLowerCase().contains("platinum"))
            ivMembership.setImageResource(R.drawable.platinum_member_icon);
        if (!TextUtils.isEmpty(membership))
            setText(membership, R.id.tv_upgraded);
        if (!TextUtils.isEmpty(icc.getMembership_expiry_date()))
            setText(Util.convertDateFormat(icc.getMembership_expiry_date(), "yyyy-MM-dd", "dd-MMM-yyyy").replace("-", " ")
                    , R.id.tv_valid_till);
    }

    private void setPromoData(PromoCardResponse pcr) {
        if (pcr.getValidity().compareTo(getString(R.string.twelve_month)) == 0) {
            if (selected == 1)
                showToast(getString(R.string.invalid_promo_1_months));
            else if (selected == 12) {
                if (pcr.getDiscount_price() != null) {
                    tvDisocuntAmount.setText(String.valueOf(pcr.getDiscount_price()));
                    validity = getString(R.string.twelve_month);
                    toBePaidAmount = String.valueOf(pcr.getDiscount_price());
                    findViewById(R.id.rl_promo).setVisibility(View.GONE);
                    findViewById(R.id.rl_radio).setVisibility(View.GONE);
                    findViewById(R.id.rl_promo_applied).setVisibility(View.VISIBLE);
                    tvPromoApplied.setText(promocode + " has been successfully applied.");
                }
            }
        } else {
            if (selected == 12)
                showToast(getString(R.string.invalid_promo_12_months));
            else if (selected == 1) {
                if (pcr.getDiscount_price() != null) {
                    tvDisocuntAmount.setText(String.valueOf(pcr.getDiscount_price()));
                    toBePaidAmount = String.valueOf(pcr.getDiscount_price());
                    validity = getString(R.string.one_month);
                    findViewById(R.id.rl_promo).setVisibility(View.GONE);
                    findViewById(R.id.rl_radio).setVisibility(View.GONE);
                    findViewById(R.id.rl_promo_applied).setVisibility(View.VISIBLE);
                    tvPromoApplied.setText(promocode + " has been successfully applied.");
                }
            }
        }

    }

    private void setData(MembershipPlansResponse mpr) {
        if (!TextUtils.isEmpty(mpr.getName()))
            setText(mpr.getName(), R.id.tv_membership_title);
        if (!TextUtils.isEmpty(mpr.getImage()))
            Picasso.with(this).load(AppConstants.PAGE_URL.PHOTO_URL + mpr.getImage()).
                    placeholder(R.drawable.circle_member).into(ivLogo);
        if (mpr.getMonthly_price() != null) {
            setText(mpr.getMonthly_price().toString(), R.id.tv_subscription_amount);
            toBePaidAmount = String.valueOf(mpr.getMonthly_price());
        }
        if (!TextUtils.isEmpty(mpr.getImage()))
            Picasso.with(this).load(AppConstants.PAGE_URL.PHOTO_URL + mpr.getImage()).into(ivLogo);
        hideProgressBar();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_monthly:
                if (mpr != null)
                    if (mpr.getMonthly_price() != null) {
                        setText(mpr.getMonthly_price().toString(), R.id.tv_subscription_amount);
                        toBePaidAmount = mpr.getMonthly_price().toString();
                        validity = getString(R.string.one_month);
                        selected = 1;
                    }
                break;
            case R.id.radio_yearly:
                if (mpr != null)
                    if (mpr.getYearly_price() != null) {
                        setText(mpr.getYearly_price().toString(), R.id.tv_subscription_amount);
                        toBePaidAmount = mpr.getYearly_price().toString();
                        validity = getString(R.string.twelve_month);
                        selected = 12;
                    }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                showToast("Payment successful");
                implementPromoCode();
                break;
            case RESULT_CANCELED:
                showToast("Payment cancelled");
                break;
            case RESULT_FIRST_USER:
                showToast("Payment failed");
                break;
        }
    }

    private void implementPromoCode() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.IMPLEMENTPROMOCODE);
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        httpParamObject.setJson(getImplementPromo().toString());
        httpParamObject.setClassType(ImplementCashCard.class);
        executeTask(AppConstants.TASKCODES.IMPLEMENTPROMOCODE, httpParamObject);
    }

    private JSONObject getImplementPromo() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(mpr.getId()))
                jsonObject.put("plan_id", mpr.getId());
            if (!TextUtils.isEmpty(validity))
                jsonObject.put("validity", validity);
            if (!TextUtils.isEmpty(promocode))
                jsonObject.put("promo_code", promocode);
            if (!TextUtils.isEmpty(txnId))
                jsonObject.put("transaction_id", txnId);
            if (!TextUtils.isEmpty(toBePaidAmount))
                jsonObject.put("amount", toBePaidAmount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
