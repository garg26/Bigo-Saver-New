package com.bigosaver.neerajyadav.bigosaver.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bigosaver.Util.CleverTapUtil;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.User.UpdateProfileResponse;
import com.clevertap.android.sdk.CleverTapAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.exceptionhandler.RestException;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;

public class MyInformationActivity extends BaseActivity {
    private EditText etFname, etLname, etEmail, etPhone, etAboutMe, etNationality, etCity;
    private Switch switch_ree, switch_rpn;
    private TextView tvDob;
    private int mYear, mMonth, mDay;
    private boolean isSelect;
    private boolean isSocialsignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        initToolBar("My Information");
        getProfileData();
        isSocialsignUp = Preferences.getData(AppConstants.PREF_KEYS.IS_SOCIAL_SIGNUP, false);
        etFname = (EditText) findViewById(R.id.et_fname);
        etLname = (EditText) findViewById(R.id.et_lname);
        etEmail = (EditText) findViewById(R.id.et_email_info);
        etPhone = (EditText) findViewById(R.id.et_m_num);
        etNationality = (EditText) findViewById(R.id.et_nationality);
        etCity = (EditText) findViewById(R.id.et_city);
        switch_ree = (Switch) findViewById(R.id.switch_get_email);
        switch_rpn = (Switch) findViewById(R.id.switch_get_notification);
        etAboutMe = (EditText) findViewById(R.id.et_aboutme);
        tvDob = (TextView) findViewById(R.id.et_dob_info);
        setOnClickListener(R.id.tv_update_num);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_update_num:
                startNextActivity(UpdateMobileNumber.class);
                break;
        }
    }

    private void getProfileData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.GETUSER);
        httpParamObject.setClassType(UpdateProfileResponse.class);
        executeTask(AppConstants.TASKCODES.GETUSER, httpParamObject);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update:
                if (isSelect == false) {
                    setText("Save", R.id.update);
                    enableFields();
                } else if (isSelect == true) {
                    if (isValid()) {
                        setText("Edit", R.id.update);
                        updateData();
                    }
                }
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private boolean isValid() {
        if (TextUtils.isEmpty(etFname.getText())) {
            showToast(getString(R.string.error_no_first_name));
            return false;
        }
        if (TextUtils.isEmpty(etLname.getText())) {
            showToast(getString(R.string.error_no_last_name));
            return false;
        }
        if (TextUtils.isEmpty(etPhone.getText()) || etPhone.getText().length() != 10) {
            showToast(getString(R.string.invalid_phone_number));
            return false;
        }
        return true;
    }

    private void setData(UpdateProfileResponse profile) {
        if (!TextUtils.isEmpty(profile.getFirst_name()))
            setText(profile.getFirst_name(), R.id.et_fname);

        if (!TextUtils.isEmpty(profile.getLast_name()))
            setText(profile.getLast_name(), R.id.et_lname);

        if (!TextUtils.isEmpty(profile.getEmail()))
            setText(profile.getEmail(), R.id.et_email_info);

        if (!TextUtils.isEmpty(profile.getDob()))
            setText(profile.getDob(), R.id.et_dob_info);

        if (!TextUtils.isEmpty(profile.getCountry()))
            setText(profile.getCountry(), R.id.et_nationality);

        if (!TextUtils.isEmpty(profile.getCountry()))
            setText(profile.getCountry(), R.id.et_city);

        if (!TextUtils.isEmpty(profile.getPhone()))
            setText(profile.getPhone(), R.id.et_m_num);

        if (!TextUtils.isEmpty(profile.getAbout_me())) {
            setText(profile.getAbout_me(), R.id.et_aboutme);
            int lineCount = etAboutMe.getLineCount();
            if (lineCount > 1){
                etAboutMe.setGravity(0);
            }
        }

        if (!TextUtils.isEmpty(profile.getMembership_expired_at()))
            setText(Util.convertDateFormat(profile.getMembership_expired_at(), "yyyy-MM-dd", "dd-MMM-yyyy").replace("-", " ")
                    , R.id.tv_member_validity);

        if (null != profile.getBigo_drink() && profile.getBigo_drink())
            setText("Yes", R.id.tv_bigo_drink);
    }

    private void setTilText(String str, int id) {
        TextInputLayout til = (TextInputLayout) findViewById(id);
        til.getEditText().setText(str);
    }

    private void disableFields() {
        etFname.setEnabled(false);
        etLname.setEnabled(false);
        etEmail.setEnabled(false);
        etNationality.setEnabled(false);
        switch_ree.setEnabled(false);
        switch_rpn.setEnabled(false);
        tvDob.setEnabled(false);
        etAboutMe.setEnabled(false);
        etCity.setEnabled(false);
//        setLocksVisiblity(false);
        isSelect = false;
    }

    private void enableFields() {
        etFname.setEnabled(true);
        etFname.requestFocus();
        etLname.setEnabled(true);
        etNationality.setEnabled(true);
        if (!isSocialsignUp)
            etEmail.setEnabled(true);
        switch_ree.setEnabled(true);
        switch_rpn.setEnabled(true);
        tvDob.setEnabled(true);
        etAboutMe.setEnabled(true);
        if (etAboutMe.getLineCount() > 1){
            etAboutMe.setGravity(0);
        } else {
            etAboutMe.setGravity(1);
        }
        etCity.setEnabled(true);
//        setLocksVisiblity(true);
        tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });
        isSelect = true;
    }

    private void setLocksVisiblity(boolean b) {
        ImageView ivFname, ivLname, ivEmail, ivDob, ivMobile, ivAbout, ivBigo, ivNationality, ivCity;
        ivFname = (ImageView) findViewById(R.id.iv_fname);
        ivLname = (ImageView) findViewById(R.id.iv_lname);
        ivEmail = (ImageView) findViewById(R.id.iv_email_info);
        ivDob = (ImageView) findViewById(R.id.iv_dob_info);
        ivNationality = (ImageView) findViewById(R.id.iv_nationality);
//        ivMobile = (ImageView) findViewById(R.id.iv_mobile_info);
        ivAbout = (ImageView) findViewById(R.id.iv_about_me);
        ivCity = (ImageView) findViewById(R.id.iv_city);
        if (b) {
            ivFname.setVisibility(View.INVISIBLE);
            ivLname.setVisibility(View.INVISIBLE);
            ivNationality.setVisibility(View.INVISIBLE);
            if (!isSocialsignUp)
                ivEmail.setVisibility(View.INVISIBLE);
            ivDob.setVisibility(View.INVISIBLE);
//            ivMobile.setVisibility(View.INVISIBLE);
            ivAbout.setVisibility(View.INVISIBLE);
            ivCity.setVisibility(View.INVISIBLE);
        } else {
            ivFname.setVisibility(View.VISIBLE);
            ivDob.setVisibility(View.VISIBLE);
            ivEmail.setVisibility(View.VISIBLE);
            ivNationality.setVisibility(View.VISIBLE);
            ivLname.setVisibility(View.VISIBLE);
//            ivMobile.setVisibility(View.VISIBLE);
            ivAbout.setVisibility(View.VISIBLE);
            ivCity.setVisibility(View.VISIBLE);
        }
    }

    private void updateData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.UPDATEUSER);
        httpParamObject.setPutMethod();
        httpParamObject.setClassType(UpdateProfileResponse.class);
        httpParamObject.setJson(getUpdatedData().toString());
        httpParamObject.setJSONContentType();
        executeTask(AppConstants.TASKCODES.UPDATEUSER, httpParamObject);
    }

    @Override
    public void onBackgroundError(RestException re, Exception e, int taskCode, Object... params) {
        super.onBackgroundError(re, e, taskCode, params);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (null == response) {
            showToast(getString(R.string.no_response));
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.UPDATEUSER:
                UpdateProfileResponse upr = (UpdateProfileResponse) response;
                if (null != upr) {
                    if (!TextUtils.isEmpty(upr.getId())) {
                        Preferences.saveData(AppConstants.PREF_KEYS.USER_ID, upr.getId());
                        showToast("Profile Updated");
                    }
                    updateUserProfileCleverTap();
                    disableFields();
                }
                break;
            case AppConstants.TASKCODES.GETUSER:
                UpdateProfileResponse profile = (UpdateProfileResponse) response;
                if (null != profile) {
                    setData(profile);
                }
                break;
        }
    }

    private void updateUserProfileCleverTap() {
        UpdateProfileResponse response = UpdateProfileResponse.getRunningInstance();
        CleverTapAPI cleverTap = CleverTapUtil.getInstance(getApplicationContext());
        if (null != response) {
            HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
            profileUpdate.put("Name", response.getFirst_name() + " " + response.getLast_name());
            profileUpdate.put("Identity", response.getPhone());
            profileUpdate.put("Email", response.getEmail());
            profileUpdate.put("Redemption", response.getTotal_redemption());
            profileUpdate.put("Membership", response.getMembership_type());
            if (!TextUtils.isEmpty(response.getDob()))
                profileUpdate.put("DOB", response.getDob());
            if (!TextUtils.isEmpty(response.getCity_string()))
                profileUpdate.put("City", response.getCity_string());
            cleverTap.profile.push(profileUpdate);
        }
    }

    private JSONObject getUpdatedData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("first_name", getEditText(R.id.et_fname));
            jsonObject.put("last_name", getEditText(R.id.et_lname));
            jsonObject.put("phone", getEditText(R.id.et_m_num));
            jsonObject.put("username", getEditText(R.id.et_m_num));
            jsonObject.put("email", getEditText(R.id.et_email_info));
            jsonObject.put("about_me", getEditText(R.id.et_aboutme));
            String tvDob = getTvText(R.id.et_dob_info);
            if (!TextUtils.isEmpty(tvDob)) {
                jsonObject.put("dob", getTvText(R.id.et_dob_info));
            }
//            jsonObject.put("country", getEditText(R.id.et_nationality));
            jsonObject.put("country", getEditText(R.id.et_city));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private String getTvText(int id) {
        TextView tv = (TextView) findViewById(id);
        if (tv != null)
            return tv.getText().toString();
        return "";
    }

    private void setDate() {
        final Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(MyInformationActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tvDob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }
}