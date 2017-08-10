package com.bigosaver.neerajyadav.bigosaver.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.activity.MerchantActivity;
import com.bigosaver.neerajyadav.bigosaver.model.CategoryAPI;
import com.bigosaver.neerajyadav.bigosaver.model.favourites.FavouritesResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.utility.AppConstants;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends BaseFragment implements CustomListAdapterInterface, AdapterView.OnItemClickListener {
    List<FavouritesResponse> favouritesList = new ArrayList<>();
    private static final int REQ_OPEN_LOCATION = 5;
    private GridView gv_fav;
    private CustomListAdapter customListAdapter;
    private ArrayList<CategoryAPI> list;

    public static FavouritesFragment getInstance() {
        return new FavouritesFragment();
    }

    @Override
    public void initViews() {
        list = new ArrayList<>();
        list.addAll(CategoryAPI.getInstance());
        gv_fav = (GridView) findView(R.id.gv_fav);
        customListAdapter = new CustomListAdapter(getActivity(), R.layout.row_favourites, favouritesList, this);
        gv_fav.setAdapter(customListAdapter);
        gv_fav.setOnItemClickListener(this);
        getFavouriteData();
    }

    private void getFavouriteData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SETFAVOURITE);
        httpParamObject.setClassType(FavouritesResponse.class);
        executeTask(AppConstants.TASKCODES.GETFAVOURITES, httpParamObject);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (null == response) {
            showToast(getString(R.string.no_response));
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.GETFAVOURITES:
                List<FavouritesResponse> fr = (List<FavouritesResponse>) response;
                favouritesList.clear();
                if (null != fr && fr.size() > 0) {
                    findView(R.id.tv_empty).setVisibility(View.GONE);
                    gv_fav.setVisibility(View.VISIBLE);
                    favouritesList.addAll(fr);
                } else {
                    gv_fav.setVisibility(View.GONE);
                    findView(R.id.tv_empty).setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    private void showLocationDialog() {
        if (!isGPSEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    @Override
    public int getViewID() {
        return R.layout.fragment_favourites;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        final Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourceID, parent, false);
            holder = new FavouritesFragment.Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        FavouritesResponse favourite = favouritesList.get(position);
        if (!TextUtils.isEmpty(favourite.getMerchant_name()))
            holder.tvTitleFav.setText(favourite.getMerchant_name());

        StringBuilder stringBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(favourite.getMerchant_place()))
            stringBuilder.append(favourite.getMerchant_place() + ", ");

        if (!TextUtils.isEmpty(favourite.getMerchant_area_name()))
            stringBuilder.append(favourite.getMerchant_area_name() + ", ");

        if (!TextUtils.isEmpty(favourite.getMerchant_city()))
            stringBuilder.append(favourite.getMerchant_city());

        if (!TextUtils.isEmpty(stringBuilder.toString()))
            holder.tvDiscountFav.setText(stringBuilder.toString());
        else
            holder.tvDiscountFav.setText("");

        if (favourite.getMerchant_images() != null && favourite.getMerchant_images().size() > 0)
            if (!TextUtils.isEmpty(favourite.getMerchant_images().get(0).getImage()))
                Picasso.with(getActivity()).load(AppConstants.PAGE_URL.PHOTO_URL + favourite.getMerchant_images().get(0).getImage()).
                        placeholder(R.drawable.appiconlogo).into(holder.ivFavourite);
            else
                holder.ivFavourite.setBackgroundResource(R.drawable.appiconlogo);

        if (null != favourite.getMerchant_category_icon() && !TextUtils.isEmpty(favourite.getMerchant_category_icon()))
            Picasso.with(getActivity()).load(AppConstants.PAGE_URL.PHOTO_URL + favourite.getMerchant_category_icon()).
                    placeholder(R.drawable.appiconlogo).into(holder.ivCategory);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), MerchantActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BUNDLE_KEYS.MERCHANT, favouritesList.get(position).getMerchant());
//        bundle.putString(AppConstants.BUNDLE_KEYS.CATEGORY_DATA, findCategory(favouritesList.get(position).getMerchant_category_icon(), list));
        bundle.putString(AppConstants.BUNDLE_KEYS.CATEGORY_DATA, favouritesList.get(position).getMerchant_category_icon());
        intent.putExtras(bundle);
        startActivityForResult(intent, AppConstants.REQUEST_CODES.CATEGORY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != getActivity().RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case AppConstants.REQUEST_CODES.CATEGORY:
                getFavouriteData();
                break;
        }
    }

    class Holder {
        TextView tvTitleFav, tvDiscountFav;
        ImageView ivFavourite, ivCategory;

        public Holder(View v) {
            tvTitleFav = (TextView) v.findViewById(R.id.tv_title_fav);
            tvDiscountFav = (TextView) v.findViewById(R.id.tv_discount_fav);
            ivFavourite = (ImageView) v.findViewById(R.id.iv_main_favourites);
            ivCategory = (ImageView) v.findViewById(R.id.civ_row_fav);
        }
    }

}
