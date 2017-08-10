package com.bigosaver.neerajyadav.bigosaver.fragment;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.Notification;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.fragments.BaseFragment;


public class NotificationFragment extends BaseFragment implements CustomListAdapterInterface, ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {
    List<Notification> notificationList = new ArrayList<>();
    private CustomListAdapter customListAdapter;
    private ListView lv_notification;
    private SliderLayout sliderNotification;
    private TextView tvEmpty;

    public static NotificationFragment getInstance() {
        return new NotificationFragment();
    }


    @Override
    public void initViews() {
        sliderNotification = (SliderLayout) findView(R.id.slider_notification);
        tvEmpty = (TextView) findView(R.id.tv_empty);
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("a", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("b", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("c", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("d", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            sliderNotification.addSlider(textSliderView);
        }
        sliderNotification.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderNotification.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderNotification.setCustomAnimation(new DescriptionAnimation());
        setOnClickListener(R.id.tv_title, R.id.tv_empty);

        lv_notification = (ListView) findView(R.id.lv_notification);
        customListAdapter = new CustomListAdapter(getActivity(), R.layout.row_notification, notificationList, this);
        lv_notification.setAdapter(customListAdapter);
        setData();
    }

    private void setData() {
        for (int x = 0; x < 3; x++) {
            Notification notification = new Notification();
            notification.setTvTitle("jddkdf aahfbkjadf ddbfadk" + x);
            notification.setTvDescription("vjdskjv sdfbsjdbv sddfbksjv skvbadskv skvbsjkvb svbsakv" + x);
            notificationList.add(notification);
        }
        customListAdapter.notifyDataSetChanged();
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_notification;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        final Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourceID, parent, false);
            holder = new NotificationFragment.Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (NotificationFragment.Holder) convertView.getTag();
        }
        Notification notification = notificationList.get(position);
        holder.tv_title.setText(notification.getTvTitle());
        return convertView;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("slider", "page changed" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class Holder {
        TextView tv_title, tv_description;
        ImageView iv_notification_back, iv_logo1, iv_logo2;

        public Holder(View v) {
            tv_description = (TextView) v.findViewById(R.id.tv_description);
            tv_title = (TextView) v.findViewById(R.id.tv_title);
            iv_notification_back = (ImageView) v.findViewById(R.id.iv_notification_back);
            iv_logo1 = (ImageView) v.findViewById(R.id.iv_logo1);
            iv_logo2 = (ImageView) v.findViewById(R.id.iv_logo2);
        }
    }
}
