package com.bigosaver.neerajyadav.bigosaver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigosaver.Util.DigitVerification;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.User.UpdateProfileResponse;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;

import org.json.JSONException;
import org.json.JSONObject;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

/**
 * Created by Dhillon on 25-11-2016.
 */

public class UpdateMobileNumber extends BaseActivity {
    private UpdateProfileResponse user;
    private View llUpdateNumber, llConfirmation;
    private EditText tvPhone;
    private String phoneNumber;
    private String otpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mobile);
        llUpdateNumber = findViewById(R.id.view_mobile_update);
        llConfirmation = findViewById(R.id.view_mobile_confirmation);
        tvPhone = (EditText) findViewById(R.id.et_mobile);
        user = UpdateProfileResponse.getRunningInstance();
        if (user != null) {
            if (!TextUtils.isEmpty(user.getPhone()))
                setText(user.getPhone(), R.id.tv_current_number);
        }
        setOnClickListener(R.id.btn_confirm, R.id.btn_done);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_confirm:
                if (isValid()) {
                    validateMobileNumber();
                }
                break;
            case R.id.btn_done:
                finish();
                break;
        }
    }

    private boolean isValid() {
        if (tvPhone.getText().toString().length() != 10) {
            showToast("Please enter a valid mobile number");
            return false;
        }
        phoneNumber = tvPhone.getText().toString().trim();
        return true;
    }

    private void updateMobile(String mobileNum) {
        String phoneNumber = tvPhone.getText().toString().trim();
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.UPDATENUMBER);
        httpParamObject.setJson(getData(mobileNum).toString());
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        executeTask(AppConstants.TASKCODES.UPDATENUMBER, httpParamObject);
    }

    private JSONObject getData(String phoneNumber) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", phoneNumber);
            jsonObject.put("otp_code", otpText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (response == null) {
            showToast(getString(R.string.no_response));
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.UPDATENUMBER:
                llUpdateNumber.setVisibility(View.GONE);
                llConfirmation.setVisibility(View.VISIBLE);
                setText(tvPhone.getText().toString(), R.id.tv_new_number);
                showToast("Mobile number update successfully");
        }

    }

    private void validateMobileNumber() {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BUNDLE_KEYS.MOBILE_NUMBER, phoneNumber);
        Intent intent = new Intent(this, MobileNumberConfirmationActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODES.OTP_REQUEST);

//        DigitVerification.authenticate("+91" + phoneNumber, new DigitVerification.DigitCallback() {
//            @Override
//            public void success(DigitsSession session, String phoneNumber) {
//                if (session != null && !TextUtils.isEmpty(phoneNumber)) {
//                    if (phoneNumber.startsWith("+91")) {
//                        phoneNumber = phoneNumber.replace("+91", "");
//                    }
//                    Preferences.saveData(AppConstants.PREF_KEYS.PHONE, phoneNumber);
//                    updateMobile(phoneNumber);
//                    Digits.clearActiveSession();
//                }
//            }
//
//            @Override
//            public void failure(DigitsException exception) {
//                showToast(getString(R.string.mobile_verification_error));
//                return;
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    otpText = extras.getString(AppConstants.BUNDLE_KEYS.OTP);
                    String mobileNum = extras.getString(AppConstants.BUNDLE_KEYS.M_NUMBER);
                    updateMobile(mobileNum);
                }
            }
        }
    }
}
