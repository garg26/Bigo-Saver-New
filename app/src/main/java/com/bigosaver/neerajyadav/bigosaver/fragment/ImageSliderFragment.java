package com.bigosaver.neerajyadav.bigosaver.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;


import com.bigosavers.R;

import java.util.ArrayList;

import simplifii.framework.ListAdapters.CustomPagerAdapter;
import simplifii.framework.fragments.BaseFragment;

/**
 * Created by Neeraj Yadav on 9/29/2016.
 */

public class ImageSliderFragment extends BaseFragment implements CustomPagerAdapter.PagerAdapterInterface {
    ArrayList<String> imageUrls =new ArrayList<>();
    ArrayList<ImageFragment> imageFragments =new ArrayList<>();
    int count = 1;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private LinearLayout lay_indicator;
    private ViewPager viewpager_img;

    @Override
    public void initViews() {
        imageUrls.add("http://www.foodmanufacture.co.uk/var/plain_site/storage/images/publications/food-beverage-nutrition/foodmanufacture.co.uk/npd/top-10-functional-food-trends/11097085-1-eng-GB/Top-10-functional-food-trends_strict_xxl.jpg");
        imageUrls.add("http://optimalstrength.nl/wp-content/uploads/2015/08/Food-1.png");
        viewpager_img = (ViewPager) findView(R.id.viewpager_img);
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(getChildFragmentManager(), imageUrls,this);
        viewpager_img.setAdapter(customPagerAdapter);
        lay_indicator= (LinearLayout) findView(R.id.lay_indicator);
        setImages(imageUrls);
    }

    private void setImages(ArrayList<String> imageUrls) {
        for(String imageUrl:imageUrls){
            ImageFragment imageFragment = ImageFragment.getInstance(imageUrl);
            imageFragments.add(imageFragment);

        }
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_image_slider;
    }


    @Override
    public Fragment getFragmentItem(int position, Object listItem) {
        return imageFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position, Object listItem) {
        return null;
    }
}
