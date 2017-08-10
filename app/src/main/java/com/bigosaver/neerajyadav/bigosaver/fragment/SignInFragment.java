package com.bigosaver.neerajyadav.bigosaver.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigosaver.Util.DigitVerification;
import com.bigosaver.Util.FBLoginUtil;
import com.bigosaver.Util.GoogleUtil;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.activity.ForgetPasswordActivity;
import com.bigosaver.neerajyadav.bigosaver.activity.MenuActivity;
import com.bigosaver.neerajyadav.bigosaver.model.LoginApi;
import com.bigosaver.neerajyadav.bigosaver.model.UserApi;
import com.bigosaver.neerajyadav.bigosaver.model.socialsignup.SocialSignUpResponse;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends BaseFragment {
    EditText et_userMobileNum, et_password;
    private UserApi user = new UserApi();
    private Button btnSignIn;
    private String userNum;
    private String pass;
    private FBLoginUtil fbLoginUtil;
    private GoogleUtil googleUtil;
    private TextView tv_fPassword;
    private PageChangeListener listener;
    private boolean flagNumberRequired;

    public SignInFragment() {
        // Required empty public constructor
    }

    public void setPageChangeListener(PageChangeListener listener) {
        this.listener = listener;
    }


    @Override
    public void initViews() {
        et_userMobileNum = (EditText) findView(R.id.et_user_mobile_num);
        et_password = (EditText) findView(R.id.et_password);
        tv_fPassword = (TextView) findView(R.id.tv_forgotpassword);
        btnSignIn = (Button) findView(R.id.btn_sign_in);
        getFbData();
        getGoogleData();
        setOnClickListener(R.id.btn_sign_in, R.id.rl_signin_fb, R.id.rl_signin_google,
                R.id.tv_signup_saving, R.id.tv_forgotpassword);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_sign_in:
                if (isValid()) {
                    executeSignIn();
                }
                break;
            case R.id.rl_signin_fb:
                fbLoginUtil.initiateFbLogin();
                break;
            case R.id.rl_signin_google:
                googleUtil.signInWithGoogle();
                break;
            case R.id.tv_forgotpassword:
                Intent i = new Intent(getActivity(), ForgetPasswordActivity.class);
                startActivityForResult(i, AppConstants.REQUEST_CODES.FORGOT_PASS);
                break;
            case R.id.tv_signup_saving:
                if (listener != null) {
                    listener.moveToSignUp();
                }
                break;
        }

    }


    private void getFbData() {
        fbLoginUtil = new FBLoginUtil(getActivity(), new FBLoginUtil.FBLoginCallback() {
            @Override
            public void onSuccess(Bundle bundle) {
                if (bundle != null) {
                    if (bundle.getString("name") != null && bundle.getString("last_name") != null) {
                        user.setFirst_name(bundle.getString("name"));
                        user.setLast_name(bundle.getString("last_name"));
                    }
                    if (bundle.getString("gender") != null)
                        user.setGender(bundle.getString("gender"));
                    if (bundle.getString("email") != null) {
                        user.setEmail(bundle.getString("email"));
                        user.setUsername(bundle.getString("email"));
                    }
                    if (bundle.getString("profileId") != null) {
                        user.setProfileId(bundle.getString("profileId"));
                    }
                    executeSignUp();
                }

            }

            @Override
            public void onFailure() {
                showToast(getString(R.string.social_login_error));
            }
        });
    }

    private void getGoogleData() {
        googleUtil = GoogleUtil.getInstance((AppCompatActivity) getActivity());
        googleUtil.setListener(new GoogleUtil.GoogleLoginCallBack() {
            @Override
            public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                if (googleSignInAccount != null) {
                    if (googleSignInAccount.getDisplayName() != null) {
                        String name = googleSignInAccount.getDisplayName().toString();
                        String str[] = name.split("\\s+");
                        user.setFirst_name(str[0]);
                        user.setLast_name(str[1]);
                    }
                    if (googleSignInAccount.getEmail() != null) {
                        user.setEmail(googleSignInAccount.getEmail());
                        user.setUsername(googleSignInAccount.getEmail());
                    }
                    user.setProfileId(googleSignInAccount.getId());
                    verifyPhone();
                }
            }

            @Override
            public void onFailed() {
                showToast(getString(R.string.social_login_error));
            }
        });

    }

    private void verifyPhone() {
        DigitVerification.authenticate("+91", new DigitVerification.DigitCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                if (session != null && !TextUtils.isEmpty(phoneNumber)) {
                    if (phoneNumber.contains("+91")) {
                        phoneNumber = phoneNumber.replace("+91", "");
                    }
                    proceedAfterDigitVerification(phoneNumber);
                }
            }

            @Override
            public void failure(DigitsException exception) {
                flagNumberRequired = false;
                showToast(getString(R.string.mobile_verification_error));
                return;

            }
        });
    }

    private void proceedAfterDigitVerification(String phoneNumber) {
        Preferences.saveData(AppConstants.PREF_KEYS.PHONE, phoneNumber);
        Digits.clearActiveSession();
        flagNumberRequired = true;
        executeSignUp();
    }

    private void executeSignUp() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SOCIAL_REGISTER);
        httpParamObject.setPostMethod();
        httpParamObject.addHeader("Authorization", "Token f55b58c1bccfb46f59ca9a43d7169b594c018e7215dd");
        httpParamObject.setJSONContentType();
        httpParamObject.setClassType(SocialSignUpResponse.class);
        httpParamObject.setJson(postData().toString());
        executeTask(AppConstants.TASKCODES.SOCIAL_REGISTER, httpParamObject);
    }


    private JSONObject postData() {
        JSONObject jsonObject = new JSONObject();
        try {
//            jsonObject.put("username", user.getEmail());
            jsonObject.put("country", "india");
            jsonObject.put("first_name", user.getFirst_name());
            jsonObject.put("last_name", user.getLast_name());
            jsonObject.put("email", user.getEmail());
            if (flagNumberRequired) {
                jsonObject.put("phone", Preferences.getData(AppConstants.PREF_KEYS.PHONE, "+91"));
            }
            if (!TextUtils.isEmpty(user.getProfileId()))
                jsonObject.put("social_id", user.getProfileId());
            if (!TextUtils.isEmpty(user.getGender()))
                jsonObject.put("gender", user.getGender());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case AppConstants.REQUEST_CODES.FORGOT_PASS:
                Bundle bundle = data.getExtras();
                if (bundle != null)
                    showToast((String) bundle.get(AppConstants.BUNDLE_KEYS.MESSAGE));
                break;
            case AppConstants.REQUEST_CODES.GOOGLE_SIGHN_IN:
                googleUtil.onActivityResult(data);
                return;
        }
        fbLoginUtil.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isValid() {
        userNum = et_userMobileNum.getText().toString().trim();
        pass = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(userNum)) {
            showToast(getString(R.string.username_not_empty));
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            showToast(getString(R.string.error_empty_password));
        }
        return true;
    }

    private void executeSignIn() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.LOGIN);
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        httpParamObject.setClassType(LoginApi.class);
        httpParamObject.setJson(logindata().toString());
        executeTask(AppConstants.TASKCODES.LOGIN, httpParamObject);
    }

    private JSONObject logindata() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", userNum);
            jsonObject.put("password", pass);
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
        switch (taskCode) {
            case AppConstants.TASKCODES.SOCIAL_REGISTER:
                hideProgressBar();
                if (!flagNumberRequired)
                    verifyPhone();
                else {
                    showToast(re.getMessage());
                    flagNumberRequired = false;
                }

                break;
            default:
                super.onBackgroundError(re, e, taskCode, params);
                break;
        }
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        switch (taskCode) {
            case AppConstants.TASKCODES.LOGIN:
                LoginApi loginApi = (LoginApi) response;
                if (loginApi.getStatus() == true) {
                    Preferences.saveData(Preferences.LOGIN_KEY, true);
                    Preferences.saveData(AppConstants.PREF_KEYS.USER_TOKEN, loginApi.getToken());
                    Preferences.saveData(AppConstants.PREF_KEYS.USER_TYPE, loginApi.getType());
                    Preferences.saveData(AppConstants.PREF_KEYS.IS_SOCIAL_SIGNUP, false);
                    startNextActivity(MenuActivity.class);
                    getActivity().finish();
                } else {
                    if (loginApi.getNonFieldErrors() != null && !loginApi.getNonFieldErrors().isEmpty())
                        showToast(loginApi.getNonFieldErrors().get(0));
                }
                break;
            case AppConstants.TASKCODES.SOCIAL_REGISTER:
                SocialSignUpResponse socialSignUpResponse = (SocialSignUpResponse) response;
                if (socialSignUpResponse.getStatus()) {
                    Preferences.saveData(Preferences.LOGIN_KEY, true);
                    Preferences.saveData(AppConstants.PREF_KEYS.USER_TOKEN, socialSignUpResponse.getToken());
                    Preferences.saveData(AppConstants.PREF_KEYS.USER_TYPE, socialSignUpResponse.getType());
                    Preferences.saveData(AppConstants.PREF_KEYS.IS_SOCIAL_SIGNUP, true);
                    startNextActivity(MenuActivity.class);
                    getActivity().finish();
                }
                break;
        }
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_sign_in;
    }

    public static interface PageChangeListener {
        public void moveToSignUp();
    }

}
