package com.bigosaver.user;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.bigosaver.Util.DigitVerification;
import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import simplifii.framework.utility.Preferences;

/**
 * Created by Neeraj Yadav on 10/7/2016.
 */
public class AppController extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.

    private static AppController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DigitVerification.getInstance(this).initDigitVerification();
        Preferences.initSharedPreferences(this);
        FacebookSdk.sdkInitialize(this);
        printHash();
    }

    public static boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (instance.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean checkLocationPermission() {
        return checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public void printHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.bigosavers", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
