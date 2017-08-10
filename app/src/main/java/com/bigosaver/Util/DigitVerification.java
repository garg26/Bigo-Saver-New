package com.bigosaver.Util;

import com.bigosaver.user.AppController;
import com.crashlytics.android.Crashlytics;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.AuthConfig;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;
import simplifii.framework.utility.Logger;

/**
 * Created by nitin on 08/02/16.
 */
public class DigitVerification {
    public static final String TAG = "DigitVerification";
    private AppController instance;
    private static AuthCallback authCallback;
    private static DigitVerification digitVerification = new DigitVerification();
    private static final String TWITTER_KEY = "ZgJhx2Q2d3rBkhXDqfFpGTWzd";
    private static final String TWITTER_SECRET = "vEHWOUA8EYQWrHnjtCNengY51sKOlejNy8ls3l1LCgrc9uqQ6d";

    private DigitVerification() {

    }

    public static DigitVerification getInstance(AppController instance) {
        digitVerification.instance = instance;
        return digitVerification;
    }

    public static void authenticate(String phoneNumber, final DigitCallback digitCallback) {
        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                Logger.info(TAG, "on Success" + phoneNumber);
                if (null != digitCallback) {
                    digitCallback.success(session, phoneNumber);
                }
            }

            @Override
            public void failure(DigitsException exception) {
                digitCallback.failure(exception);
            }
        };
        AuthConfig.Builder authConfigBuilder = new AuthConfig.Builder()
                .withAuthCallBack(authCallback)
                .withPhoneNumber(phoneNumber);

        Digits.authenticate(authConfigBuilder.build());
    }

    public void initDigitVerification() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(digitVerification.instance, new TwitterCore(authConfig), new Digits.Builder().build(),
                new Crashlytics());
    }

    public static AuthCallback getAuthCallback() {
        return authCallback;
    }

    public static interface DigitCallback {
        public void success(DigitsSession session, String phoneNumber);

        public void failure(DigitsException exception);
    }
}