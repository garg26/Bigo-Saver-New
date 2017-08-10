package com.bigosaver.neerajyadav.bigosaver.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigosavers.R;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

public class AppHomePageActivity extends BaseActivity {
    private TextView tvSignIn, tvTakeATour, tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home_page);
        tvSignIn = (TextView) findViewById(R.id.tv_signin_home);
        tvTakeATour = (TextView) findViewById(R.id.tv_take_tour);
        tvSignUp = (TextView) findViewById(R.id.tv_home_signup);

        tvSignIn.setPaintFlags(tvSignIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvTakeATour.setPaintFlags(tvTakeATour.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvSignUp.setPaintFlags(tvSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if (Preferences.isUserLoggerIn()) {
            startNextActivity(MenuActivity.class);
            finish();
            return;
        }
        setOnClickListener(R.id.ll_signup, R.id.ll_take, R.id.ll_already_acc);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_already_acc:
                moveToSignInSignUp(0);
                break;
            case R.id.ll_take:
                Intent i = new Intent(this, TutorialActivity.class);
                startActivity(i);
                break;
            case R.id.ll_signup:
                moveToSignInSignUp(1);
                break;
        }
    }

    private void moveToSignInSignUp(int position) {
        Bundle b = new Bundle();
        b.putInt(AppConstants.BUNDLE_KEYS.KEY_POSITION, position);
        startNextActivity(b, LoginActivity.class);
        finish();
    }
}
