package com.bigosaver.neerajyadav.bigosaver.fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigosavers.R;

import java.util.ArrayList;
import java.util.List;

import com.bigosaver.neerajyadav.bigosaver.model.CityData;

import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;


public class DialogLocationFragment extends DialogFragment implements CustomListAdapterInterface, AdapterView.OnItemClickListener {
    List<CityData> citiesList = new ArrayList<>();
    List<CityData> citiesCopyList = new ArrayList<>();
    private ListView lv_cities;
    CustomListAdapter customListAdapter;
    private Integer[] logo1;
    private CitySelectionListener listener;
    private EditText etCity;
    private String savedCity;
    private CityData selectedCity;

    public static DialogLocationFragment getInstance(String savedCity, List<CityData> citiesList, CitySelectionListener listener) {
        DialogLocationFragment dialogLocationFragment = new DialogLocationFragment();
        dialogLocationFragment.citiesList.addAll(citiesList);
        dialogLocationFragment.citiesCopyList.addAll(citiesList);
        dialogLocationFragment.listener = listener;
        dialogLocationFragment.savedCity = savedCity;
        return dialogLocationFragment;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_location, container, false);
        RelativeLayout btn_cancel = (RelativeLayout) view.findViewById(R.id.rl_city_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLocationFragment.this.dismiss();
            }
        });
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;
        double dialogWidth = width * 0.9;
        double dialogHeight = height * 0.9;
        view.setMinimumWidth((int) dialogWidth);
        view.setMinimumHeight((int) dialogHeight);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logo1 = new Integer[]{R.drawable.next1};
        lv_cities = (ListView) view.findViewById(R.id.lv_cities);
        customListAdapter = new CustomListAdapter(getActivity(), R.layout.row_dialog, citiesList, this);
        lv_cities.setAdapter(customListAdapter);
        lv_cities.setOnItemClickListener(this);
        setSearchBox(view);
        return view;
    }



    private void setSearchBox(View v) {
        EditText et = (EditText) v.findViewById(R.id.et_city);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterList(String s) {
        citiesList.clear();
        if (TextUtils.isEmpty(s)) {
            citiesList.addAll(citiesCopyList);
        } else {
            for (CityData c : citiesCopyList) {
                if (c.getName().toLowerCase().contains(s.toLowerCase())) {
                    citiesList.add(c);
                }
            }
        }
        customListAdapter.notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        final Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resourceID, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        CityData city = citiesList.get(position);
        holder.tv_ad_filter.setText(city.getName());
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listener != null) {
            listener.onCitySelected(citiesList.get(position));
        }
        dismiss();
    }

    class Holder {
        TextView tv_ad_filter;
        ImageView logo;

        public Holder(View v) {
            tv_ad_filter = (TextView) v.findViewById(R.id.lblDialogItem);
            logo = (ImageView) v.findViewById(R.id.iv_icon_dialog);
            logo.setVisibility(View.GONE);
        }

    }

    public static interface CitySelectionListener {
        public void onCitySelected(CityData city);
    }
}
