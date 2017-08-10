package com.bigosaver.fcm;

import android.util.Log;

import com.bigosaver.Util.CleverTapUtil;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.FcmManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

public class MyFirebaseInstanceIDService extends com.clevertap.android.sdk.FcmTokenListenerService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Refreshed token: " + refreshedToken);
        Preferences.saveData(AppConstants.PREF_KEYS.FCM_KEY, refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        CleverTapAPI cleverTapAPI = CleverTapUtil.getInstance(getApplicationContext());
        cleverTapAPI.data.pushFcmRegistrationId(token, true);
    }
}