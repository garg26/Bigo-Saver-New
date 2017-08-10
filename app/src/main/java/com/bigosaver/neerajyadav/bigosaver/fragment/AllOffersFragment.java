package com.bigosaver.neerajyadav.bigosaver.fragment;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bigosavers.R;

import java.util.ArrayList;
import java.util.List;

import com.bigosaver.neerajyadav.bigosaver.activity.MerchantActivity;
import com.bigosaver.neerajyadav.bigosaver.model.AllOffers;
import simplifii.framework.ListAdapters.CustomListAdapter;
import simplifii.framework.ListAdapters.CustomListAdapterInterface;
import simplifii.framework.fragments.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllOffersFragment extends BaseFragment implements CustomListAdapterInterface {
    List<AllOffers> allOffersList = new ArrayList<>();
    private ListView lv_all_offers;
    private CustomListAdapter customListAdapter;



    @Override
    public void initViews() {
        lv_all_offers = (ListView) findView(R.id.lv_all_offers);
        customListAdapter = new CustomListAdapter(getActivity(), R.layout.row_all_offers, allOffersList,this);
        lv_all_offers.setAdapter(customListAdapter);
        setData();
    }

    private void setData() {
        for (int x=0; x<8; x++){
            AllOffers allOffers = new AllOffers();
            allOffers.setTvTopAll("Elevation Burger " +x);
            allOffers.setTvNumOutlet(x + " Outlets");
            allOffers.setTvDistance("1234" +x +"Km");
            allOffersList.add(allOffers);
        }
        customListAdapter.notifyDataSetChanged();
    }

    @Override
    public int getViewID() {
        return R.layout.fragment_all_offers;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent, int resourceID, LayoutInflater inflater) {
        final Holder holder;
        if (convertView == null){
            convertView = inflater.inflate(resourceID, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        AllOffers allOffers = allOffersList.get(position);
        holder.tv_top_all.setText(allOffers.getTvTopAll());
        holder.tv_num_outlet.setText(allOffers.getTvNumOutlet());
        holder.tv_distance.setText(allOffers.getTvDistance());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        startNextActivity(MerchantActivity.class);
                        break;
                }
            }
        });
        return convertView;
    }

    class Holder{
        TextView tv_top_all, tv_num_outlet,tv_distance;
        public Holder (View v){
            tv_top_all = (TextView) v.findViewById(R.id.tv_top_all);
            tv_num_outlet = (TextView) v.findViewById(R.id.tv_num_outlets);
            tv_distance = (TextView) v.findViewById(R.id.tv_distance);
        }
    }
}
