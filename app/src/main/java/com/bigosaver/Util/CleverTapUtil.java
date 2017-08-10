package com.bigosaver.Util;

import android.content.Context;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;

/**
 * Created by Dhillon on 07-02-2017.
 */

public class CleverTapUtil {
    private static CleverTapAPI cleverTapAPI;

    public static CleverTapAPI getInstance(Context applicationContext) {
        try {
            cleverTapAPI = CleverTapAPI.getInstance(applicationContext);
        } catch (CleverTapMetaDataNotFoundException e) {
            e.printStackTrace();
        } catch (CleverTapPermissionsNotSatisfied cleverTapPermissionsNotSatisfied) {
            cleverTapPermissionsNotSatisfied.printStackTrace();
        }
        return cleverTapAPI;
    }
}
