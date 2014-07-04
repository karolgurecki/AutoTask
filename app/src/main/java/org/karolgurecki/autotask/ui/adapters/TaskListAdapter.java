package org.karolgurecki.autotask.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.ui.ViewHolder;

import java.util.List;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public class TaskListAdapter extends ArrayAdapter<String> {

    private LayoutInflater inflater;

    public TaskListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        //widgets displayed by each item in your list
        TextView item = null;

        //data from your adapter
        String itemRow = getItem(position);


        //we want to reuse already constructed row views...
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_row, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        //
        holder = (ViewHolder) convertView.getTag();
        item = holder.getTitle();
        item.setText(itemRow);

        return convertView;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }
}
