package com.bigosaver.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bigosaver.neerajyadav.bigosaver.activity.CashCardCodeActivity;
import com.bigosaver.neerajyadav.bigosaver.activity.MenuActivity;
import com.bigosaver.neerajyadav.bigosaver.activity.MerchantActivity;
import com.bigosaver.neerajyadav.bigosaver.fragment.CitySelectionFragment;
import com.bigosaver.neerajyadav.bigosaver.fragment.FavouritesFragment;
import com.bigosaver.neerajyadav.bigosaver.fragment.NotificationFragment;
import com.bigosaver.neerajyadav.bigosaver.fragment.ProfileFragment;
import com.bigosaver.neerajyadav.bigosaver.model.CategoryAPI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

/**
 * Created by nbansal2211 on 19/10/16.
 */

public class PushNavigationUtil {

    public static void navigate(Bundle bundle, MenuActivity context) {
        if (bundle == null) return;
        String pageName = bundle.getString(AppConstants.FCM_BUNDLE_KEYS.REDIRECT_TO);
        if (!TextUtils.isEmpty(pageName)) {
            redirectBasedOnType(bundle, context, pageName);
        }
    }

    public static void redirectBasedOnType(Bundle bundle, MenuActivity context, String pageName) {
        switch (pageName) {
            case AppConstants.FCM_BUNDLE_KEYS.MERCHANT_PAGE:
                if (!TextUtils.isEmpty(bundle.getString(AppConstants.FCM_BUNDLE_KEYS.MERCHANT_ID)) &&
                        !TextUtils.isEmpty(bundle.getString(AppConstants.FCM_BUNDLE_KEYS.CATEGORY_ID)))
                    moveToMerchantPage(context, bundle);
                break;
            case AppConstants.FCM_BUNDLE_KEYS.PROFILE_PAGE:
                moveToProfilePage(context);
                break;
            case AppConstants.FCM_BUNDLE_KEYS.CASHCODE_PAGE:
                if (!TextUtils.isEmpty(bundle.getString(AppConstants.FCM_BUNDLE_KEYS.CODE)))
                    moveToSubscriptionPage(context, bundle);
                break;
            case AppConstants.FCM_BUNDLE_KEYS.NEW_OFFER_TAB:
                if (!TextUtils.isEmpty(bundle.getString(AppConstants.FCM_BUNDLE_KEYS.CATEGORY_ID)))
                    moveToOfferPage(context, bundle);
                break;
            case AppConstants.FCM_BUNDLE_KEYS.NOTIFICATION_PAGE:
                moveToNotificationPage(context);
                break;
            case AppConstants.FCM_BUNDLE_KEYS.WHATS_ON:
                moveToWhatsOnPage(context);
                break;
            case AppConstants.FCM_BUNDLE_KEYS.FAVOURITES:
                moveToFavouritePage(context);
                break;
        }

    }

    private static void moveToMerchantPage(Context context, Bundle bundle) {
        Intent intent = new Intent((Activity) context, MerchantActivity.class);
        bundle.putString(AppConstants.BUNDLE_KEYS.CATEGORY_DATA, findCategoryImage(bundle.getString("cid")));
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private static String findCategoryImage(String cid) {
        String categoryList = Preferences.getData(AppConstants.PREF_KEYS.CATEGORY_LIST, "");
        List<CategoryAPI> list = new ArrayList<>();
        if (!TextUtils.isEmpty(categoryList)) {
            list = new Gson().fromJson(categoryList, new TypeToken<List<CategoryAPI>>() {
            }.getType());
        }
        if (null != list && list.size() == 5) {
            for (CategoryAPI c : list) {
                if (c.getId().compareTo(cid) == 0)
                    return c.getMobileImage();
            }
        }
        return "";
    }

    private static void moveToOfferPage(MenuActivity context, Bundle bundle) {
        String savedCity = Preferences.getData(AppConstants.PREF_KEYS.SELECTED_CITY, "");
        CitySelectionFragment citySelectionFragment = CitySelectionFragment.getInstance(context, bundle.getString("cid"), savedCity);
        context.addFragment(citySelectionFragment, false);
    }


    private static void moveToProfilePage(MenuActivity context) {
        context.addOtherFragments(ProfileFragment.getInstance());
    }


    private static void moveToNotificationPage(MenuActivity context) {
        context.addOtherFragments(NotificationFragment.getInstance());
    }


    private static void moveToWhatsOnPage(MenuActivity context) {
        context.addOtherFragments(ProfileFragment.getInstance());
    }


    private static void moveToFavouritePage(MenuActivity context) {
        context.addOtherFragments(FavouritesFragment.getInstance());
    }

    private static void moveToSubscriptionPage(Context context, Bundle bundle) {
        Intent intent = new Intent((Activity) context, CashCardCodeActivity.class);
        bundle.putBoolean(AppConstants.BUNDLE_KEYS.DRINKS, true);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
