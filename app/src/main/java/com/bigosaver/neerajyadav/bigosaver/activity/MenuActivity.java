package com.bigosaver.neerajyadav.bigosaver.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigosaver.Util.CleverTapUtil;
import com.bigosaver.Util.PushNavigationUtil;
import com.bigosaver.neerajyadav.bigosaver.model.User.UpdateProfileResponse;
import com.bigosaver.neerajyadav.bigosaver.fragment.CartFragment;
import com.bigosaver.user.AppController;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.fragment.CitySelectionFragment;
import com.bigosaver.neerajyadav.bigosaver.fragment.FavouritesFragment;
import com.bigosaver.neerajyadav.bigosaver.fragment.NotificationFragment;
import com.bigosaver.neerajyadav.bigosaver.fragment.ProfileFragment;
import com.bigosaver.neerajyadav.bigosaver.fragment.WhatsOnFragment;
import com.bigosaver.neerajyadav.bigosaver.model.interfaces.FragmentChangeListener;
import com.clevertap.android.sdk.CleverTapAPI;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;

public class MenuActivity extends BaseActivity implements FragmentChangeListener {
    public ImageView iv_home, iv_notification, iv_fav, iv_avatar, iv_cart;
    private LinearLayout ll_home, ll_notification, ll_fav, ll_avatar, ll_cart;
    private OfferTabFragment offerTabFragment;
    private CitySelectionFragment citySelectionFragment;
    private Fragment currentFragment;
    private ImageView selectedImageView;
    private Bundle bundle;
    private Location location;
    private int REQ_OPEN_LOCATION = 3;
//    private int fragmentPosition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_fav = (ImageView) findViewById(R.id.iv_fav);
        iv_notification = (ImageView) findViewById(R.id.iv_notification);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        iv_cart = (ImageView) findViewById(R.id.iv_cart);
        ll_home = (LinearLayout) findViewById(R.id.lay_home);
        ll_notification = (LinearLayout) findViewById(R.id.lay_notification);
        ll_fav = (LinearLayout) findViewById(R.id.lay_fav);
        ll_avatar = (LinearLayout) findViewById(R.id.lay_avatar);
        ll_cart = (LinearLayout) findViewById(R.id.lay_cart);
        String savedCity = Preferences.getData(AppConstants.PREF_KEYS.SELECTED_CITY, "");
        citySelectionFragment = CitySelectionFragment.getInstance(this, "", savedCity);
        setSelect(iv_home);
        addFragment(citySelectionFragment, false);
        checkGpsService();
        if (Preferences.getData(AppConstants.PREF_KEYS.IS_FIRST, true)) {
            updateUserProfileCleverTap();
        }
        bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstants.FCM_BUNDLE_KEYS.REDIRECT_TO)) {
            PushNavigationUtil.navigate(bundle, this);
            setUnselectAll();
            switch (bundle.getString(AppConstants.FCM_BUNDLE_KEYS.REDIRECT_TO)) {
                case AppConstants.FCM_BUNDLE_KEYS.PROFILE_PAGE:
                    setSelect(iv_avatar);
                    break;
                case AppConstants.FCM_BUNDLE_KEYS.NOTIFICATION_PAGE:
                    setSelect(iv_notification);
                    break;
                case AppConstants.FCM_BUNDLE_KEYS.WHATS_ON:
                    setSelect(iv_cart);
                    break;
                case AppConstants.FCM_BUNDLE_KEYS.FAVOURITES:
                    setSelect(iv_fav);
                    break;

            }
            bundle.clear();
        }

//        isNetworkAvailable();
//        setOnClickListener(R.id.iv_home, R.id.iv_notification, R.id.iv_fav, R.id.iv_avatar, R.id.iv_cart);
        setOnClickListener(R.id.lay_home, R.id.lay_notification, R.id.lay_fav, R.id.lay_avatar, R.id.lay_cart);
    }

    private void checkGpsService() {
        if (AppController.checkLocationPermissionCourse()) {
            if (isGPSEnabled()) {
                getLocation();
            } else {
                showLocationDialog();
            }

        } else {
            checkLocationPermission();
        }
    }

    private void showLocationDialog() {
        if (!isGPSEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.gps_not_found_title);
            builder.setMessage(R.string.gps_not_found_message);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQ_OPEN_LOCATION);
                }
            });
            builder.setNegativeButton(R.string.no, null);
            builder.create().show();
        }
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void checkLocationPermission() {
        new TedPermission(this).setPermissionListener(new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                getLocation();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        }).setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION).check();
    }

    private void getLocation() {
        CleverTapAPI cleverTap = CleverTapUtil.getInstance(getApplicationContext());
        location = cleverTap.getLocation();
    }

    private void updateUserProfileCleverTap() {
        UpdateProfileResponse response = UpdateProfileResponse.getRunningInstance();
        CleverTapAPI cleverTap = CleverTapUtil.getInstance(getApplicationContext());
        if (location != null) {
            cleverTap.setLocation(location);
        }
        if (null != response) {
            HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
            profileUpdate.put("Name", response.getFirst_name() + " " + response.getLast_name());
            profileUpdate.put("Identity", response.getPhone());
            profileUpdate.put("Email", response.getEmail());
            profileUpdate.put("Redemption", response.getTotal_redemption());
            profileUpdate.put("Membership", response.getMembership_type());
            if (!TextUtils.isEmpty(response.getDob()))
                profileUpdate.put("DOB", response.getDob());
            if (!TextUtils.isEmpty(response.getCity_string()))
                profileUpdate.put("City", response.getCity_string());
            cleverTap.profile.push(profileUpdate);
            Preferences.saveData(AppConstants.PREF_KEYS.IS_FIRST, false);
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        bundle = intent.getExtras();
        PushNavigationUtil.navigate(bundle, this);
    }

    public void addFragment(Fragment fragment, boolean b) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
        if (b) {
            getSupportFragmentManager().popBackStack();
            fragmentTransaction.addToBackStack(fragment.getClass().getCanonicalName());
        }
        currentFragment = fragment;
        if (b) {
            fragmentTransaction.add(R.id.fl_fragment_menu, fragment, fragment.getClass().getCanonicalName()).commitAllowingStateLoss();
        } else {
            fragmentTransaction.replace(R.id.fl_fragment_menu, fragment, fragment.getClass().getCanonicalName()).commitAllowingStateLoss();
        }
    }

    @Override
    public void onClick(View v) {
        if (selectedImageView == v) {
            return;
        }
        setUnselectAll();
        switch (v.getId()) {
            case R.id.lay_home:
//                fragmentPosition = 1;
//                addFragment(citySelectionFragment, true);
//                setSelect(iv_home);
                onHomeClicked();
                break;
            case R.id.lay_notification:
//                fragmentPosition = 2;
//                addOtherFragments(NotificationFragment.getInstance());
//                setSelect(iv_notification);
                break;
            case R.id.lay_avatar:
//                fragmentPosition = 3;
                addOtherFragments(ProfileFragment.getInstance());
                setSelect(iv_avatar);
                break;
            case R.id.lay_cart:
//                fragmentPosition = 4;
                addOtherFragments(CartFragment.getInstance());
                setSelect(iv_cart);
                break;
            case R.id.lay_fav:
//                fragmentPosition = 5;
                addOtherFragments(FavouritesFragment.getInstance());
                setSelect(iv_fav);
                break;
        }
    }


    public void addOtherFragments(Fragment f) {
        int countStack = 0;
        if (offerTabFragment != null) {
            countStack = 1;
        }
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > countStack) {
            fm.popBackStack();
        }
        addFragment(f, true);
    }

    private void onHomeClicked() {
        onBackPressed();
    }

    public void addOfferTabFragment(OfferTabFragment fragment) {
        offerTabFragment = fragment;
        addFragment(offerTabFragment, true);
    }

    @Override
    public void onBackPressed() {
//        if (fragmentPosition != 1) {
//            fragmentPosition = 1;
//            addFragment(citySelectionFragment, true);
//        } else
//            finish();

        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1);
            fm.popBackStack(entry.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
        offerTabFragment = null;
        setUnselectAll();
        setSelect(iv_home);
    }

    private void setUnselectAll() {
        iv_home.setColorFilter(getResourceColor(R.color.color_DarkGray));
        iv_notification.setColorFilter(getResourceColor(R.color.color_DarkGray));
        iv_fav.setColorFilter(getResourceColor(R.color.color_DarkGray));
        iv_avatar.setColorFilter(getResourceColor(R.color.color_DarkGray));
        iv_cart.setColorFilter(getResourceColor(R.color.color_DarkGray));
    }

    public void setSelect(ImageView iv) {
        iv.setColorFilter(getResourceColor(R.color.icon_blue));
        selectedImageView = iv;
    }


    @Override
    public void changeFragment(Fragment f) {
//        fragmentPosition = 0;
        if (f instanceof OfferTabFragment) {
            addOfferTabFragment((OfferTabFragment) f);
        }
    }

    public void setFavouriteFragment() {
        addOtherFragments(FavouritesFragment.getInstance());
        setSelect(iv_fav);
    }

}
