package org.karolgurecki.autotask.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.karolgurecki.autotask.R;

import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Nappa
 * Date: 29.09.13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */
public class NewTaskActivity extends Activity {
    private LayoutInflater mInflater;
    private Vector<RowData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.newtask);
        mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        data = new Vector<RowData>();
        RowData rd = new RowData("item1", "description1");
        data.add(rd);
        rd = new RowData("item2", "description2");
        data.add(rd);
        rd = new RowData("item2", "description3");
        data.add(rd);
        for (int i = 0; i < 15; i++) {
            rd = new RowData("item"+(3+i), "description"+(3+i));
            data.add(rd);
        }
        CustomAdapter adapter = new CustomAdapter(this, R.layout.custom_row, R.id.item, data);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        //getListView().setTextFilterEnabled(true);
    }


    /**
     * Data type used for custom adapter. Single item of the adapter.
     */
    private class RowData {
        protected String mItem;
        protected String mDescription;

        RowData(String item, String description) {
            mItem = item;
            mDescription = description;
        }

        @Override
        public String toString() {
            return mItem + " " + mDescription;
        }
    }

    private class CustomAdapter extends ArrayAdapter<RowData> {

        public CustomAdapter(Context context, int resource,
                             int textViewResourceId, List<RowData> objects) {
            super(context, resource, textViewResourceId, objects);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            //widgets displayed by each item in your list
            TextView item = null;
            TextView description = null;

            //data from your adapter
            RowData rowData = getItem(position);


            //we want to reuse already constructed row views...
            if (null == convertView) {
                convertView = mInflater.inflate(R.layout.custom_row, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            //
            holder = (ViewHolder) convertView.getTag();
            item = holder.getItem();
            item.setText(rowData.mItem);

            description = holder.getDescription();
            description.setText(rowData.mDescription);

            return convertView;
        }
    }

    /**
     * Wrapper for row data.
     */
    private class ViewHolder {
        private View mRow;
        private TextView description = null;
        private TextView item = null;

        public ViewHolder(View row) {
            mRow = row;
        }

        public TextView getDescription() {
            if (null == description) {
                description = (TextView) mRow.findViewById(R.id.textView);
            }
            return description;
        }

        public TextView getItem() {
            if (null == item) {
                item = (TextView) mRow.findViewById(R.id.item);
            }
            return item;
        }
    }
}