package com.bigosaver.neerajyadav.bigosaver.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.fragment.SlideImageFragment;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomPagerAdapter;
import simplifii.framework.activity.BaseActivity;

public class TutorialActivity extends BaseActivity implements CustomPagerAdapter.PagerAdapterInterface {
    int[] slideImges = {R.mipmap.tour_new_home, R.mipmap.tour_new_category,
            R.mipmap.tour_new_merchant, R.mipmap.tour_new_details,
            R.mipmap.tour_new_redeem};
    private LinearLayout slide_circle_container;
    private List<View> circleViews = new ArrayList<>();
    private List<SlideImageFragment> slideImageFragments = new ArrayList<>();
    List<String> tab = new ArrayList<>();
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        slide_circle_container = (LinearLayout) findViewById(R.id.lay_slide_circles);
        viewPager = (ViewPager) findViewById(R.id.viewpager_home);
        setSliderShow(slideImges);
        setOnClickListener(R.id.tv_skip);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_skip:
                finish();
                break;
        }
    }

    private void setSliderShow(int[] slideImges) {
        for (int x = 0; x < slideImges.length; x++) {
            View view = LayoutInflater.from(this).inflate(R.layout.row_slide_circle, null);
            slide_circle_container.addView(view);
            circleViews.add(view);
            SlideImageFragment slideImageFragment = SlideImageFragment.getInstance(slideImges[x]);
            slideImageFragments.add(slideImageFragment);
            tab.add("");
        }
        CustomPagerAdapter pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), tab, this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
//8769999417
            @Override
            public void onPageSelected(int position) {
                unSelectAll();
                circleViews.get(position).findViewById(R.id.lay_row_slider).setBackgroundResource(R.drawable.slide_circle_select);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (circleViews.size() > 0) {
            circleViews.get(0).findViewById(R.id.lay_row_slider).setBackgroundResource(R.drawable.slide_circle_select);
        }


    }

    private void unSelectAll() {
        for (int x = 0; x < circleViews.size(); x++) {
            circleViews.get(x).findViewById(R.id.lay_row_slider).setBackgroundResource(R.drawable.slide_circle_unselect);
        }
    }

    @Override
    public Fragment getFragmentItem(int position, Object listItem) {
        return slideImageFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position, Object listItem) {
        return tab.get(position);
    }
}
