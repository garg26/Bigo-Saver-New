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
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

public class ForgetPasswordActivity extends BaseActivity {

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
//                    validateMobileNumber(getEditText(R.id.et_user_mobile_num_fp));
                }
                break;
        }
    }

    private void validateMobileNumber(final String mobileNumber) {
        DigitVerification.authenticate("+91" + mobileNumber, new DigitVerification.DigitCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                if (session != null && !TextUtils.isEmpty(phoneNumber)) {
                    if (phoneNumber.startsWith("+91")) {
                        phoneNumber = phoneNumber.replace("+91", "");
                    }
                    Preferences.saveData(AppConstants.PREF_KEYS.PHONE, phoneNumber);
                    executeForgotPassword();
                    Digits.clearActiveSession();
                }
            }

            @Override
            public void failure(DigitsException exception) {
                showToast(getString(R.string.mobile_verification_error));
                return;
            }
        });
    }

    private void executeForgotPassword() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.FORGOTPASS);
        httpParamObject.setClassType(ChangePasswordResponse.class);
        httpParamObject.setJson(getData().toString());
        httpParamObject.setPostMethod();
        httpParamObject.addHeader("Authorization","Token b2fe8ee868c200c99c51ef08da561ceb668cd830010f");
        httpParamObject.setJSONContentType();
        executeTask(AppConstants.TASKCODES.FORGOTPASS, httpParamObject);
    }

    private JSONObject getData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("old_password", getEditText(R.id.et_password));
            jsonObject.put("new_password", getEditText(R.id.et_confirm_password));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if(null == response){
            showToast("No response");
            return;
        }
        switch (taskCode){
            case AppConstants.TASKCODES.FORGOTPASS:
                ChangePasswordResponse cpr= (ChangePasswordResponse) response;
                if(null != cpr){
                    Intent intent = new Intent();
                    intent.putExtra(AppConstants.BUNDLE_KEYS.MESSAGE, cpr.getDetail());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;

        }
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
        }else  if (pass.compareTo(confirmPass) != 0) {
            showToast(getString(R.string.password_not_match));
            return false;
        }else if(pass.length()<4 || confirmPass.length()<4){
            showToast(getString(R.string.short_password));
            return false;
        }
        return true;
    }
}
