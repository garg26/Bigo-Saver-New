package com.bigosaver.user;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.Filters.FilterData;
import com.bigosaver.neerajyadav.bigosaver.model.Filters.FilterItems;
import com.squareup.picasso.Picasso;

import simplifii.framework.utility.Util;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private List<FilterData> items;

    public ExpandableListAdapter(Context context, List<FilterData> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return items.get(groupPosition).getFilter_values().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_elistview, parent, false);
        }

        final FilterItems item = (FilterItems) getChild(groupPosition, childPosition);
        FilterData filterData = (FilterData) getGroup(groupPosition);
        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        final ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_filter_icon);
        final ImageView ivTick = (ImageView) convertView.findViewById(R.id.iv_icon_tick);
        final ImageView ivCross = (ImageView) convertView.findViewById(R.id.iv_icon_cross);
        if (!TextUtils.isEmpty(item.getImage())) {
            ivIcon.setVisibility(View.VISIBLE);
            Picasso.with(context).load(Util.getImageUrl(item.getImage())).into(ivIcon);
        } else {
            ivIcon.setVisibility(View.INVISIBLE);
        }

        txtListChild.setText(item.getValue());
        ivTick.setImageResource(R.drawable.selecticonuncheck);
        ivCross.setImageResource(R.drawable.rejecticonunchecked);
        ivCross.setVisibility(View.GONE);
        if (item.getSelected()) {
            ivTick.setImageResource(R.drawable.selecticon);
        } else {
            ivTick.setImageResource(R.drawable.selecticonuncheck);
        }

        switch (filterData.getType()) {
            case "select_reject":
                ivCross.setVisibility(View.VISIBLE);
                if (item.getRejected()) {
                    ivCross.setImageResource(R.drawable.rejecticon);
                } else {
                    ivCross.setImageResource(R.drawable.rejecticonunchecked);
                }
                ivCross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getSelected())
                            item.setSelected(false);
                        item.setRejected(!item.getRejected());
                        notifyDataSetChanged();
                    }
                });
                break;
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getRejected())
                    item.setRejected(false);
                item.setSelected(!item.getSelected());
                notifyDataSetChanged();
            }
        });
        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this.items.get(groupPosition).getFilter_values().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.items.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.items.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        FilterData filterData = (FilterData) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_header_elistview, parent, false);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        TextView lblListType = (TextView) convertView.findViewById(R.id.lblListtype);
        final ImageView lblIcon = (ImageView) convertView.findViewById(R.id.iv_elv_arrow);
        lblListHeader.setText(filterData.getTitle());
        lblListType.setText(filterData.getSubtitle());
        if (isExpanded) {
            lblIcon.setRotation(90);
        } else {
            lblIcon.setRotation(360);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}