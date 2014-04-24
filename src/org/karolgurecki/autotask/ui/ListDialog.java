package org.karolgurecki.autotask.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.tasks.AbstractTaskObject;
import org.karolgurecki.autotask.ui.adapters.ItemAdapter;

import java.util.List;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class ListDialog extends Dialog {

    private ListView listView;

    public ListDialog(Context context, List<AbstractTaskObject> taskObjects, String title) {
        super(context);

        setContentView(R.layout.task_list);
        listView = (ListView) findViewById(R.id.listView);
        ItemAdapter adapter = new ItemAdapter(context, R.layout.item_row, taskObjects);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListenerListViewItem(title, context));
        setTitle(title);
        show();
    }

    public class ListBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
