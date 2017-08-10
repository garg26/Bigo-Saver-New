package com.bigosaver.neerajyadav.bigosaver.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bigosaver.neerajyadav.bigosaver.model.BaseModel;
import com.bigosaver.neerajyadav.bigosaver.model.BaseResponse;
import com.bigosaver.neerajyadav.bigosaver.model.BaseResponseModel;
import com.bigosavers.R;

import org.json.JSONException;
import org.json.JSONObject;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.AppConstants;

public class MobileNumberConfirmationActivity extends BaseActivity {
    private EditText etMobileNum;
    private Button btnSubmit;
    private String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number_confirmation);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mobileNumber = bundle.getString(AppConstants.BUNDLE_KEYS.MOBILE_NUMBER);
        }

        etMobileNum = (EditText) findViewById(R.id.et_mobile);
        btnSubmit = (Button) findViewById(R.id.btn_submit_pin);

        etMobileNum.setText(mobileNumber);
        setOnClickListener(R.id.btn_submit_pin);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_submit_pin:
                String mNumber = etMobileNum.getText().toString().trim();
                if (!TextUtils.isEmpty(mNumber) && mNumber.length()==10){
                    mobileNumber = mNumber;
                    sendOtp(mNumber);
                } else {
                    showToast("Please enter valid mobile number");
                }
                break;
        }
    }

    private void sendOtp(String mNumber) {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SEND_OTP);
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone_number", mNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpParamObject.addHeader("Authorization", "Token f55b58c1bccfb46f59ca9a43d7169b594c018e7215dd");
        httpParamObject.setJson(jsonObject.toString());
        httpParamObject.setClassType(BaseResponseModel.class);
        executeTask(AppConstants.TASKCODES.SEND_OTP, httpParamObject);
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
        switch (taskCode){
            case AppConstants.TASKCODES.SEND_OTP:
                BaseResponseModel baseModel = (BaseResponseModel) response;
                if (baseModel != null && baseModel.getSuccess() == true){
//                    setResult(RESULT_OK);
                    Intent intent = new Intent(this, OtpVerificationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.BUNDLE_KEYS.MOBILE_NUMBER, mobileNumber);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, AppConstants.REQUEST_CODES.OTP_CONFIRM);
                } else {
                    showToast(baseModel.getError());
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (data != null){
                Bundle extras = data.getExtras();
                if (extras != null){
                    String otpText = extras.getString(AppConstants.BUNDLE_KEYS.OTP);
                    String mNumber = extras.getString(AppConstants.BUNDLE_KEYS.M_NUMBER);
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.BUNDLE_KEYS.OTP, otpText);
                    bundle.putString(AppConstants.BUNDLE_KEYS.M_NUMBER, mNumber);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }
    }
}
