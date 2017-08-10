package com.bigosaver.neerajyadav.bigosaver.activity;

import android.content.Intent;
import android.os.Bundle;

import com.bigosaver.Util.CleverTapUtil;
import com.bigosavers.R;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.utility.Preferences;

public class SplashActivity extends BaseActivity {
    private Bundle dataBundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CleverTapAPI cleverTap = CleverTapUtil.getInstance(getApplicationContext());


        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        goToNextActivity();

                    }
                });
            }
        }.start();

    }

    private void goToNextActivity() {
        Intent i;
        if (Preferences.isUserLoggerIn()) {
            i = new Intent(SplashActivity.this, MenuActivity.class);
            if (null != dataBundle)
                i.putExtras(dataBundle);
        } else {
            i = new Intent(SplashActivity.this, AppHomePageActivity.class);
        }
        startActivity(i);
        finish();
    }

    @Override
    protected void loadBundle(Bundle bundle) {
        super.loadBundle(bundle);
        dataBundle = bundle;
//        dataBundle.putString("redirectTo", "md");
//        dataBundle.putString("mid", "584d74d486ee204a25b164fb");
//        dataBundle.putString("cid", "57e78cbfc43d0e03107d7595");
    }

}

