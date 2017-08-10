package com.bigosaver.neerajyadav.bigosaver.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.User.UpdateProfileResponse;

import org.json.JSONException;
import org.json.JSONObject;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;

/**
 * Created by Dhillon on 09-11-2016.
 */
public class ChangePasswordActivity extends BaseActivity {
    private UpdateProfileResponse profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        if (UpdateProfileResponse.getRunningInstance() != null)
            profile = UpdateProfileResponse.getRunningInstance();
        setOnClickListener(R.id.btn_sign_in);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_sign_in:
                if (isValid())
                    executeChangePassword();
                break;
        }
    }

    private void executeChangePassword() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.CHANGEPASS);
        httpParamObject.setJson(getData().toString());
        httpParamObject.addParameter("format", "json");
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        executeTask(AppConstants.TASKCODES.CHANGEPASS, httpParamObject);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);

        switch (taskCode) {
            case AppConstants.TASKCODES.CHANGEPASS:
                showToast("Password changed successfully");
                break;
        }
    }

    private JSONObject getData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", profile.getPhone());
            jsonObject.put("new_password", getEditText(R.id.et_confirm_password));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private boolean isValid() {
        String pass = getEditText(R.id.et_password);
        String confirmPass = getEditText(R.id.et_confirm_password);

        if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(confirmPass)) {
            showToast(getString(R.string.error_empty_password));
            return false;
        }

        if (pass.length() < 4 || confirmPass.length() < 4) {
            showToast(getString(R.string.short_password));
            return false;
        }

        if (pass.compareTo(confirmPass) != 0) {
            showToast(getString(R.string.password_not_match));
            return false;
        }

        return true;
    }
}
