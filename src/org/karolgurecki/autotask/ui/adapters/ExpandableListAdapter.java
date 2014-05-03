package org.karolgurecki.autotask.ui.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.tasks.TaskObject;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listHeaders; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<TaskObject>> listChildren;

    public ExpandableListAdapter(Context context, List<String> listHeaders,
                                 HashMap<String, List<TaskObject>> listChildData) {
        this.context = context;
        this.listHeaders = listHeaders;
        this.listChildren = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listChildren.get(this.listHeaders.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        TaskObject child = (TaskObject) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_row, null);
        }

        TextView childHeaderTextView = (TextView) convertView.findViewById(R.id.itemTitle);
        TextView childConfigTextView = (TextView) convertView.findViewById(R.id.itemDescription);

        childHeaderTextView.setText(child.getDisplayName());
        childConfigTextView.setText(child.getDisplayConfiguration());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChildren.get(this.listHeaders.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listHeaders.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listHeaders.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

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

    public void addItemToGroup(int groupPosition, TaskObject item) {
        listChildren.get(listHeaders.get(groupPosition)).add(item);
    }
}