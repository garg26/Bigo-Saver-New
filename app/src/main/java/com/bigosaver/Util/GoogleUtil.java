package com.bigosaver.Util;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bigosavers.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.GoogleAuthProvider;

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
    Dialog dialog = null;

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

    private void registerOnFcm(final GoogleSignInAccount account) {
        showProgressDialog();
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("TAG", "signInWithCredential:onComplete:" + task.isSuccessful());
                if (task.isSuccessful()) {
                    loginCallBack.onSuccess(account);
                    hideProgressBar();
                } else {
                    loginCallBack.onFailed();
                    hideProgressBar();
                }
            }
        });
    }


    public void showProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
        } else {
            dialog = new Dialog(activity);
//            dialog.setMessage("Loading...");
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(simplifii.framework.R.layout.layout_progress_bar);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(false);
            int divierId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = dialog.findViewById(divierId);
            if (divider != null)
                divider.setVisibility(View.GONE);
            dialog.show();
        }
    }

    public void hideProgressBar() {
        Log.i("TAG" + "Dialog", Thread.currentThread().getName());
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public interface GoogleLoginCallBack {
        void onSuccess(GoogleSignInAccount googleSignInAccount);

        void onFailed();
    }
}
