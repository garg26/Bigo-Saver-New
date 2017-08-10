package com.bigosaver.neerajyadav.bigosaver.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bigosavers.R;

import simplifii.framework.utility.Preferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

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
                        Intent i;
                        if (Preferences.isUserLoggerIn()) {
                            i = new Intent(SplashActivity.this, MenuActivity.class);
                        } else {
                            i = new Intent(SplashActivity.this, AppHomePageActivity.class);
                        }
                        startActivity(i);
                        finish();
                    }
                });
            }
        }.start();

    }
}

