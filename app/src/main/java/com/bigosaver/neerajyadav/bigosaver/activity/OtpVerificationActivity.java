package com.bigosaver.neerajyadav.bigosaver.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigosaver.neerajyadav.bigosaver.model.BaseResponseModel;
import com.bigosavers.R;

import org.json.JSONException;
import org.json.JSONObject;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;

public class OtpVerificationActivity extends BaseActivity {

    private String mobileNumber;
    private EditText etOtp;
    private Button btnSubmit, resendOtp, editNumber;
    private TextView tvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mobileNumber = bundle.getString(AppConstants.BUNDLE_KEYS.MOBILE_NUMBER);
        }

        etOtp = (EditText) findViewById(R.id.et_otp);
        tvNumber = (TextView) findViewById(R.id.tv_number);
        btnSubmit = (Button) findViewById(R.id.btn_submit_pin);
        resendOtp = (Button) findViewById(R.id.btn_resend_otp);
        editNumber = (Button) findViewById(R.id.btn_edit_mobile_number);
        tvNumber.setText("+91" +mobileNumber);

        setOnClickListener(R.id.btn_submit_pin, R.id.btn_resend_otp, R.id.btn_edit_mobile_number);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_submit_pin:
                String otpText = etOtp.getText().toString().trim();
                if (TextUtils.isEmpty(otpText)){
                    showToast("Please Enter OTP first");
                    return;
                }
                if (otpText.length()<4){
                    showToast("Please enter 4 digit Pin");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.BUNDLE_KEYS.OTP, otpText);
                bundle.putString(AppConstants.BUNDLE_KEYS.M_NUMBER, mobileNumber);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btn_resend_otp:
                resendOtpText();
                break;
            case R.id.btn_edit_mobile_number:
                finish();
                break;
        }
    }

    private void resendOtpText() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.RESEND_OTP);
        httpParamObject.setPostMethod();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone_number", mobileNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpParamObject.addHeader("Authorization", "Token f55b58c1bccfb46f59ca9a43d7169b594c018e7215dd");
        httpParamObject.setJson(jsonObject.toString());
        httpParamObject.setJSONContentType();
        httpParamObject.setClassType(BaseResponseModel.class);
        executeTask(AppConstants.TASKCODES.RESEND_OTP, httpParamObject);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (response == null) {
            showToast("No Response");
            return;
        }
        switch (taskCode){
            case AppConstants.TASKCODES.RESEND_OTP:
                BaseResponseModel baseModel = (BaseResponseModel) response;
                if (baseModel != null && baseModel.getSuccess() == true){
//                    setResult(RESULT_OK);
                    showToast("OTP sent Successfully");
                }
                break;
        }
    }
}
