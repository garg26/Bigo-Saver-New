package com.bigosaver.Util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import simplifii.framework.utility.AppConstants;

/**
 * Created by Admin on 20-May-16.
 */

/**
 * Created by Admin on 20-May-16.
 */
public class GoogleUtil {
    boolean isGoogleLogin = false;
    public static GoogleApiClient mGoogleApiClient;
    AppCompatActivity activity;
    GoogleLoginCallBack loginCallBack;

    public static GoogleUtil getInstance(AppCompatActivity activity) {
        GoogleUtil googleUtil = new GoogleUtil();
        googleUtil.activity = activity;
        return googleUtil;
    }

    public void setListener(GoogleLoginCallBack loginCallBack) {
        this.loginCallBack = loginCallBack;
    }

    public void signInWithGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        if (!isGoogleLogin) {
            mGoogleApiClient = new GoogleApiClient.Builder(activity)
                    .enableAutoManage(activity, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Toast.makeText(activity, "failed to Google Login..!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            isGoogleLogin = true;
        }
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, AppConstants.REQUEST_CODES.GOOGLE_SIGHN_IN);
    }

    public void onActivityResult(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            loginCallBack.onSuccess(acct);
        } else {

        }
    }

    public interface GoogleLoginCallBack {
        void onSuccess(GoogleSignInAccount googleSignInAccount);

        void onFailed();
    }
}
