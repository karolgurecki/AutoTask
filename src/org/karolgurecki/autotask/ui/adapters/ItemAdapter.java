package org.karolgurecki.autotask.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.ui.holders.ViewHolder;
import org.karolgurecki.autotask.ui.rows.ItemRow;

import java.util.List;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class ItemAdapter extends ArrayAdapter<ItemRow> {

    private LayoutInflater mInflater;

    public ItemAdapter(Context context, int resource, List<ItemRow> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        //widgets displayed by each item in your list
        TextView item = null;
        TextView description = null;

        //data from your adapter
        ItemRow itemRow = getItem(position);


        //we want to reuse already constructed row views...
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_row, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        //
        holder = (ViewHolder) convertView.getTag();
        item = holder.getTitle();
        item.setText(itemRow.getTitle());

        description = holder.getDescription();
        description.setText(itemRow.getDescription());

        return convertView;
    }

    public LayoutInflater getmInflater() {
        return mInflater;
    }

    public void setmInflater(LayoutInflater mInflater) {
        this.mInflater = mInflater;
    }
}
