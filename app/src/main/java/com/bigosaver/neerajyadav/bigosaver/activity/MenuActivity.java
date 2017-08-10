package com.bigosaver.neerajyadav.bigosaver.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.fragment.CitySelectionFragment;
import com.bigosaver.neerajyadav.bigosaver.fragment.FavouritesFragment;
import com.bigosaver.neerajyadav.bigosaver.fragment.NotificationFragment;
import com.bigosaver.neerajyadav.bigosaver.fragment.ProfileFragment;
import com.bigosaver.neerajyadav.bigosaver.fragment.WhatsOnFragment;
import com.bigosaver.neerajyadav.bigosaver.model.interfaces.FragmentChangeListener;

import simplifii.framework.activity.BaseActivity;

public class MenuActivity extends BaseActivity implements FragmentChangeListener {
    public ImageView iv_home, iv_notification, iv_fav, iv_avatar, iv_cart;
    private LinearLayout ll_home, ll_notification,ll_fav, ll_avatar, ll_cart;
    private OfferTabFragment offerTabFragment;
    private CitySelectionFragment citySelectionFragment;
    private Fragment currentFragment;
    private ImageView selectedImageView;
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
        citySelectionFragment = CitySelectionFragment.getInstance(this);
        addFragment(citySelectionFragment, false);
        setSelect(iv_home);
        isNetworkAvailable();
//        setOnClickListener(R.id.iv_home, R.id.iv_notification, R.id.iv_fav, R.id.iv_avatar, R.id.iv_cart);
        setOnClickListener(R.id.lay_home, R.id.lay_notification, R.id.lay_fav, R.id.lay_avatar, R.id.lay_cart);
    }

    private void addFragment(Fragment fragment, boolean b) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
        if (b) {
            getSupportFragmentManager().popBackStack();
            fragmentTransaction.addToBackStack(fragment.getClass().getCanonicalName());
        }
        currentFragment = fragment;
        if (b) {
            fragmentTransaction.add(R.id.fl_fragment_menu, fragment).commit();
        } else {
            fragmentTransaction.replace(R.id.fl_fragment_menu, fragment).commit();
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
                addOtherFragments(NotificationFragment.getInstance());
                setSelect(iv_notification);
                break;
            case R.id.lay_avatar:
//                fragmentPosition = 3;
                addOtherFragments(ProfileFragment.getInstance());
                setSelect(iv_avatar);
                break;
            case R.id.lay_cart:
//                fragmentPosition = 4;
                addOtherFragments(WhatsOnFragment.getInstance());
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
