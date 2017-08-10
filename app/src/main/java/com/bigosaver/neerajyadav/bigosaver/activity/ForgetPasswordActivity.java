package com.bigosaver.neerajyadav.bigosaver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bigosaver.Util.DigitVerification;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.ChangePasswordResponse;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;

import org.json.JSONException;
import org.json.JSONObject;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

public class ForgetPasswordActivity extends BaseActivity {

    private String otpText;
    private String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        setOnClickListener(R.id.btn_sign_in);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_sign_in:
                if (isValid()) {
                    mobileNumber = getEditText(R.id.et_user_mobile_num_fp).toString().trim();
                    validateMobileNumber();
                }
                break;
        }
    }

    private void validateMobileNumber() {

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BUNDLE_KEYS.MOBILE_NUMBER, mobileNumber);
        Intent intent = new Intent(this, MobileNumberConfirmationActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODES.OTP_REQUEST_FORGETPASS);

//        DigitVerification.authenticate("+91" + mobileNumber, new DigitVerification.DigitCallback() {
//            @Override
//            public void success(DigitsSession session, String phoneNumber) {
//                if (session != null && !TextUtils.isEmpty(phoneNumber)) {
//                    if (phoneNumber.startsWith("+91")) {
//                        phoneNumber = phoneNumber.replace("+91", "");
//                    }
//                    executeForgotPassword(phoneNumber);
//                    Digits.clearActiveSession();
//                }
//            }
//
//            @Override
//            public void failure(DigitsException exception) {
////                showToast(getString(R.string.mobile_verification_error));
////                return;
//            }
//        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (data != null){
                Bundle extras = data.getExtras();
                if (extras != null){
                    otpText = extras.getString(AppConstants.BUNDLE_KEYS.OTP);
                    mobileNumber = extras.getString(AppConstants.BUNDLE_KEYS.M_NUMBER);
                    executeForgotPassword();
                }
            }
        }
    }

    private void executeForgotPassword() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.FORGOTPASS);
        httpParamObject.setClassType(ChangePasswordResponse.class);
        httpParamObject.setJson(getData(mobileNumber).toString());
        httpParamObject.setPostMethod();
//        httpParamObject.addHeader("Authorization", "Token b2fe8ee868c200c99c51ef08da561ceb668cd830010f");
        httpParamObject.setJSONContentType();
        executeTask(AppConstants.TASKCODES.FORGOTPASS, httpParamObject);
    }

    private JSONObject getData(String phn) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", phn);
            jsonObject.put("new_password", getEditText(R.id.et_confirm_password));
            jsonObject.put("otp_code", otpText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (null == response) {
            return;
        }

        switch (taskCode) {
            case AppConstants.TASKCODES.FORGOTPASS:
                showToast(getString(R.string.success_forgot_password));
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        super.onBackgroundError(re, e, taskCode, params);
//        showToast(getString(R.string.error_forgot_password));

    }

    private boolean isValid() {
        String phone = getEditText(R.id.et_user_mobile_num_fp);
        String pass = getEditText(R.id.et_password);
        String confirmPass = getEditText(R.id.et_confirm_password);
        if (TextUtils.isEmpty(phone)) {
            if (phone.length() != 10) {
                showToast(getString(R.string.invalid_phone_number));
                return false;
            }
        }
        if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(confirmPass)) {
            showToast(getString(R.string.error_empty_password));
            return false;
        } else if (pass.compareTo(confirmPass) != 0) {
            showToast(getString(R.string.password_not_match));
            return false;
        } else if (pass.length() < 4 || confirmPass.length() < 4) {
            showToast(getString(R.string.short_password));
            return false;
        }
        return true;
    }
}
