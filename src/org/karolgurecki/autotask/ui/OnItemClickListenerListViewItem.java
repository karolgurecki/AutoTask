package org.karolgurecki.autotask.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class OnItemClickListenerListViewItem implements OnItemClickListener {

    private String type;
    private Context context;

    public OnItemClickListenerListViewItem(String type, Context context) {
        this.type = type;
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent("org.karolgurecki.autotask.addTaskObject");
        intent.putExtra("INDEX", position);
        intent.putExtra("TYPE", type);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
