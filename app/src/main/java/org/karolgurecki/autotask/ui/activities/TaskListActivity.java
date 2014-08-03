package org.karolgurecki.autotask.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.service.StartUpService;
import org.karolgurecki.autotask.ui.adapters.TaskListAdapter;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by goreckik on 09.10.13.
 */
public class TaskListActivity extends Activity {

    private ListView taskListView;
    private AlertDialog.Builder alertDialogBuilder;
    private int chossenTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.task_list);
        taskListView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter adapter = new TaskListAdapter(this, R.layout.item_row, new ArrayList<>(StartUpService.TASK_PROPERTIES_NAME_LIST));
        taskListView.setAdapter(adapter);
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                chossenTask = position;
                alertDialogBuilder.show();
                return true;
            }
        });
        alertDialogBuilder = new AlertDialog.Builder(this).
                setTitle(getString(R.string.saveTaskTitleAlert)).
                setMessage(getString(R.string.doYouWantSave)).
                setCancelable(true).
                setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String taskName = StartUpService.TASK_PROPERTIES_NAME_LIST.remove(chossenTask);

                        File file = new File(String.format("%s%s%s.%s", getFilesDir(), File.separator,
                                taskName.replace(' ', ConstanceFieldHolder.SPACE_REPLACEMENT),
                                ConstanceFieldHolder.PROPERTIES_FILE_EXTENTION));
                        if (file.delete()) {
                            StartUpService.TASK_HOLDER_MAP.remove(taskName);
                            adapter.notifyDataSetChanged();
                            Intent thisActivityIntent=getIntent();
                            thisActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            startActivity(thisActivityIntent);
                        } else {
                            Log.e(ConstanceFieldHolder.AUTOTASK_TAG, String.format("Can't delete %s task", taskName));
                            StartUpService.TASK_PROPERTIES_NAME_LIST.add(taskName);
                        }
                    }
                }).
                setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }
}
