package org.karolgurecki.autotask.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.tasks.TaskObject;
import org.karolgurecki.autotask.ui.adapters.TaskObjectItemAdapter;

import java.util.List;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class ListDialog extends Dialog {

    private ListView listView;

    public ListDialog(Context context, List<TaskObject> taskObjects, String title, String type) {
        super(context);

        setContentView(R.layout.task_list);
        listView = (ListView) findViewById(R.id.listView);
        TaskObjectItemAdapter adapter = new TaskObjectItemAdapter(context, R.layout.item_row, taskObjects);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListenerListViewItem(type, context));
        setTitle(title);
        show();
    }

    public class ListBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
