package com.bigosaver.neerajyadav.bigosaver.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigosaver.Util.DigitVerification;
import com.bigosaver.neerajyadav.bigosaver.activity.MobileNumberConfirmationActivity;
import com.bigosaver.neerajyadav.bigosaver.model.User.UpdateProfileResponse;
import com.bigosaver.neerajyadav.bigosaver.model.UserApi;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.activity.MenuActivity;
import com.bigosaver.neerajyadav.bigosaver.model.socialsignup.SocialSignUpResponse;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;

import org.json.JSONException;
import org.json.JSONObject;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends BaseFragment {
    private TextInputLayout til_fname, til_lname, til_email, til_password, til_c_password, til_number, til_city;
    private Button btn_next;
    private String fname, lname, email, pass, c_pass, mobile, city;
    private TextView tv_signin;
    private PageChangeListener listener;
    private String otpText;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public void setPageChangeListener(PageChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void initViews() {
        til_fname = (TextInputLayout) findView(R.id.til_fname_of_user);
        til_lname = (TextInputLayout) findView(R.id.til_lname_of_user);
        til_email = (TextInputLayout) findView(R.id.til_email_of_user);
        til_password = (TextInputLayout) findView(R.id.til_pass_of_user);
        til_password = (TextInputLayout) findView(R.id.til_pass_of_user);
        til_c_password = (TextInputLayout) findView(R.id.til_cpass_of_user);
        til_city = (TextInputLayout) findView(R.id.til_city);
        til_number = (TextInputLayout) findView(R.id.til_mob_num_of_user);

        btn_next = (Button) findView(R.id.btn_next);
        tv_signin = (TextView) findView(R.id.tv_signin);

        setOnClickListener(R.id.btn_next, R.id.tv_signin);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (isValid()) {
                    validateMobileNumber(mobile);
//                    executeSignUp();
                }
                break;
            case R.id.tv_signin:
                if (listener != null) {
                    listener.moveToSignIn();
                }
                break;
        }
        super.onClick(v);
    }

    private boolean isValid() {
        fname = til_fname.getEditText().getText().toString().trim();
        lname = til_lname.getEditText().getText().toString().trim();
        email = til_email.getEditText().getText().toString().trim();
        pass = til_password.getEditText().getText().toString().trim();
        c_pass = til_c_password.getEditText().getText().toString().trim();
        mobile = til_number.getEditText().getText().toString().trim();
        city = til_city.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(fname)) {
            til_fname.setError(getString(R.string.cannot_empty));
            til_fname.getEditText().requestFocus();
            return false;
        } else {
            til_fname.setError(null);
        }
        if (TextUtils.isEmpty(lname)) {
            til_lname.setError(getString(R.string.cannot_empty));
            til_lname.getEditText().requestFocus();
            return false;
        } else {
            til_lname.setError(null);
        }
        if (TextUtils.isEmpty(city)) {
            til_city.setError(getString(R.string.cannot_empty));
            til_city.getEditText().requestFocus();
            return false;
        } else {
            til_city.setError(null);
        }
        if (TextUtils.isEmpty(email)) {
            til_email.setError(getString(R.string.cannot_empty));
            til_email.getEditText().requestFocus();
            return false;
        } else if (!Util.isValidEmail(email)) {
            til_email.setError(getString(R.string.invalid_email));
            return false;
        } else {
            til_email.setError(null);
        }
        if (TextUtils.isEmpty(pass)) {
            til_password.setError(getString(R.string.cannot_empty));
            til_password.getEditText().requestFocus();
            return false;
        } else {
            til_password.setError(null);
        }
        if (TextUtils.isEmpty(c_pass)) {
            til_c_password.setError(getString(R.string.cannot_empty));
            til_c_password.getEditText().requestFocus();
            return false;
        } else if (!c_pass.equals(pass)) {
            til_c_password.setError(getString(R.string.password_not_match));
            til_c_password.getEditText().requestFocus();
            return false;
        } else {
            til_c_password.setError(null);
        }
        if (mobile.length() < 10) {
            til_number.setError(getString(R.string.number_cannot_empty));
            til_number.getEditText().requestFocus();
            return false;
        } else {
            til_number.setError(null);
        }

        return true;
    }

    private void validateMobileNumber(final String mobileNumber) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BUNDLE_KEYS.MOBILE_NUMBER, mobileNumber);
        Intent intent = new Intent(getActivity(), MobileNumberConfirmationActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODES.OTP_REQUEST);
//        startNextActivity(bundle, MobileNumberConfirmationActivity.class);

//        DigitVerification.authenticate("+91" + mobileNumber, new DigitVerification.DigitCallback() {
//            @Override
//            public void success(DigitsSession session, String phoneNumber) {
//                if (session != null && !TextUtils.isEmpty(phoneNumber)) {
//                    if (phoneNumber.startsWith("+91")) {
//                        phoneNumber = phoneNumber.replace("+91", "");
//                    }
//                    Preferences.saveData(AppConstants.PREF_KEYS.PHONE, phoneNumber);
//                    mobile = phoneNumber;
//                    executeSignUp();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK){
            if (data != null){
                Bundle extras = data.getExtras();
                if (extras != null){
                    otpText = extras.getString(AppConstants.BUNDLE_KEYS.OTP);
                    mobile = extras.getString(AppConstants.BUNDLE_KEYS.M_NUMBER);
                    executeSignUp();
                }
            }
        }
    }

    private void executeSignUp() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.REGISTER);
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        httpParamObject.setClassType(UpdateProfileResponse.class);
        httpParamObject.addHeader("Authorization", "Token f55b58c1bccfb46f59ca9a43d7169b594c018e7215dd");
        httpParamObject.setJson(postData().toString());
        executeTask(AppConstants.TASKCODES.REGISTER, httpParamObject);
    }

    private JSONObject postData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("country", "india");
            jsonObject.put("first_name", fname);
            jsonObject.put("last_name", lname);
            jsonObject.put("email", email);
            jsonObject.put("phone", mobile);
            jsonObject.put("city_string", city);
            jsonObject.put("password", pass);
            jsonObject.put("otp_code", otpText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onPreExecute(int taskCode) {
        super.onPreExecute(taskCode);
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        super.onBackgroundError(re, e, taskCode, params);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (response == null) {
            showToast("No Response");
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.REGISTER:
                UpdateProfileResponse userApi = (UpdateProfileResponse) response;
                if (userApi.getError() != null) {
                    showToast(userApi.getError());
                } else {
                    Preferences.saveData(AppConstants.PREF_KEYS.USER_ID, userApi.getId());
                    loginUser();
                }
                break;
            case AppConstants.TASKCODES.LOGIN:
                SocialSignUpResponse response1 = (SocialSignUpResponse) response;
                if (response1 != null) {
                    if (response1.getError() != null) {
                        showToast(response1.getError());
                    } else {
                        Preferences.saveData(Preferences.LOGIN_KEY, true);
                        moveToHomePage(response1.getToken(), response1.getType());
                    }
                }
                break;
        }
    }

    private void loginUser() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.LOGIN);
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        httpParamObject.setClassType(SocialSignUpResponse.class);
        httpParamObject.setJson(getLoginJson().toString());
        executeTask(AppConstants.TASKCODES.LOGIN, httpParamObject);
    }

    private JSONObject getLoginJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", mobile);
            jsonObject.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void moveToHomePage(String token, String type) {
        Preferences.saveData(Preferences.LOGIN_KEY, true);
        showToast(getString(R.string.register_successful));
        Preferences.saveData(AppConstants.PREF_KEYS.USER_TOKEN, token);
        Preferences.saveData(AppConstants.PREF_KEYS.USER_TYPE, type);
        startNextActivity(MenuActivity.class);
        getActivity().finish();
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_sign_up;
    }

    public static interface PageChangeListener {
        public void moveToSignIn();
    }


}
