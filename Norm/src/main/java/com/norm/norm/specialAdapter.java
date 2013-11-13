package com.norm.norm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Cesar on 11/12/13.
 */

public class specialAdapter extends BaseExpandableListAdapter {
    private List<special> specials;
    private LayoutInflater inflater;

    public specialAdapter(Context context, List specials) {
        this.specials = specials;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return specials.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return specials.get(groupPosition).getName();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        TextView textView = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.expandlist_group_item, parent, false);
        }
        textView = (TextView) convertView.findViewById(R.id.tvGroup);
        textView.setText(getGroup(groupPosition).toString());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return specials.get(groupPosition).getElements().size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return specials.get(groupPosition).getElements().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        TextView textView = null;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.expandlist_child_item, parent, false);
        }
        textView = (TextView) convertView.findViewById(R.id.tvChild);
        textView.setText(getChild(groupPosition, childPosition).toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
