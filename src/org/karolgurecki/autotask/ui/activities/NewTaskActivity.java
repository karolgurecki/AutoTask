package org.karolgurecki.autotask.ui.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;
import org.apache.commons.lang3.StringUtils;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.factory.TaskFactory;
import org.karolgurecki.autotask.tasks.TaskObject;
import org.karolgurecki.autotask.ui.ListDialog;
import org.karolgurecki.autotask.ui.adapters.ExpandableListAdapter;
import org.karolgurecki.autotask.utils.ExceptionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static android.app.AlertDialog.Builder;
import static android.content.DialogInterface.OnClickListener;

/**
 * Created with IntelliJ IDEA.
 * User: Nappa
 * Date: 29.09.13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */

public class NewTaskActivity extends Activity {

    private static final String COMMA = ",";
    private static final String AUTO_TASK_TAG = "AutoTask";
    private static List<TaskObject> actionsList;
    private static List<TaskObject> triggersList;
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<TaskObject>> listDataChild;
    private String add_trigger;
    private String add_action;
    private List<TaskObject> taskTriggerList = new ArrayList<>();
    private List<TaskObject> taskActionsList = new ArrayList<>();
    private Dialog dialog = null;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String type = intent.getStringExtra("TYPE");
            int index = intent.getIntExtra("INDEX", -1);

            if (StringUtils.isNotBlank(type) && index >= 0) {
                if (type.equalsIgnoreCase(getString(R.string.add_trigger))) {
                    addObject(triggersList, taskTriggerList, index);
                } else {
                    addObject(actionsList, taskActionsList, index);
                }
            }

            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }

        }

        private void addObject(List<TaskObject> taskObjectList, List<TaskObject> taskList, int index) {
            try {
                TaskObject obj = taskObjectList.get(index).getClass().newInstance();
                obj.openDialog();
                taskList.add(obj);
                listAdapter.notifyDataSetChanged();
            } catch (InstantiationException | IllegalAccessException e) {
                Log.e(AUTO_TASK_TAG, ExceptionUtils.stackTraceToString(e));
            }
        }
    };
    private Builder alertDialogBuilder;
    private TextView taskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.edit_task);
        if (actionsList == null) {
            actionsList = TaskFactory.tasksWithoutConfigFromPropertyFileCreator(getResources().openRawResource(R.raw.actions_classes));
        }

        if (triggersList == null) {
            triggersList = TaskFactory.tasksWithoutConfigFromPropertyFileCreator(getResources().openRawResource(R.raw.actions_classes));
        }
        taskName = (TextView) findViewById(R.id.editText);

        expListView = (ExpandableListView) findViewById(R.id.itemLIst);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

        add_trigger = getString(R.string.add_trigger);
        add_action = getString(R.string.add_action);

        alertDialogBuilder = new Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.saveTaskTitleAlert));
        alertDialogBuilder.setMessage(getString(R.string.doYouWantSave));
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setPositiveButton(R.string.yes, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveTask();
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.no, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        alertDialogBuilder.setNeutralButton(R.string.cancel, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add(getString(R.string.triggers));
        listDataHeader.add(getString(R.string.actions));

        listDataChild.put(listDataHeader.get(0), taskTriggerList); // Header, Child data
        listDataChild.put(listDataHeader.get(1), taskActionsList);

    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        super.onCreatePanelMenu(featureId, menu);    //To change body of overridden methods use File | Settings | File Templates.

        menu.add(getString(R.string.add_trigger));
        menu.add(getString(R.string.add_action));
        menu.add(getString(R.string.finish));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(add_action)) {
            chooseTaskObject(1, actionsList, getString(R.string.add_action));
        } else if (item.getTitle().equals(add_trigger)) {
            chooseTaskObject(0, triggersList, getString(R.string.add_trigger));
        } else {
            alertDialogBuilder.show();
        }
        return true;
    }

    private void chooseTaskObject(int listAdapterNum, List<TaskObject> taskObjects, String dialogTitle) {
        dialog = new ListDialog(this, taskObjects, dialogTitle);
    }

    private void saveTask() {
        FileOutputStream stream = null;
        try {
            File autoTaskFolder = new File(Environment.getExternalStorageDirectory() + "/AutoTask");
            stream = new FileOutputStream(new File(autoTaskFolder.getPath(),
                    String.format("%s.properties", taskName.getText())));
            StringBuilder builder = new StringBuilder();
            builder.append("#This file is auto generated by AutoTask DO NOT CHANGE IT!");
            appendConfig(builder, "trigger.classes", triggersList);
            appendConfig(builder, "actions.classes", actionsList);
            stream.write(builder.toString().getBytes(Charset.forName("ISO-8859-1")));
            Properties properties = new Properties();
            properties.store(stream, null);
        } catch (IOException e) {
            Log.e(AUTO_TASK_TAG, ExceptionUtils.stackTraceToString(e));
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    Log.e(AUTO_TASK_TAG, ExceptionUtils.stackTraceToString(e));
                }
            }
        }
    }

    private void appendConfig(StringBuilder builder, String classesPropName, List<TaskObject> taskObjectList) {
        builder.append(String.format("%s=", classesPropName));

        for (int i = 0; i < taskObjectList.size(); i++) {
            builder.append(taskObjectList.get(i).getClass().getName());
            if (i + 1 != taskObjectList.size()) {
                builder.append(COMMA);
            }
        }
        builder.append("\n");
        for (TaskObject taskObject : taskObjectList) {
            builder.append(String.format("%s.config=%s\n", taskObject.getClass().getName(), taskObject.getConfig()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter("org.karolgurecki.autotask.addTaskObject"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        alertDialogBuilder.show();
    }
}