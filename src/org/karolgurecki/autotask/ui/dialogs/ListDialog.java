package org.karolgurecki.autotask.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.widget.ListView;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.tasks.TaskObject;
import org.karolgurecki.autotask.tasks.impl.TestTaskObject;
import org.karolgurecki.autotask.ui.adapters.ItemAdapter;

import java.util.List;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class ListDialog extends Dialog {

    ListView listView;
    TaskObject taskObject;

    public ListDialog(Context context, List<TaskObject> taskObjects, String title, TaskObject taskObject) {
        super(context);
        this.taskObject=new TestTaskObject();
        taskObject=this.taskObject;
        setContentView(R.layout.task_list);
        listView=(ListView) findViewById(R.id.listView);
        ItemAdapter adapter = new ItemAdapter(context, R.layout.item_row, taskObjects);
        listView.setAdapter(adapter);
        setTitle(title);
        show();
    }
}
