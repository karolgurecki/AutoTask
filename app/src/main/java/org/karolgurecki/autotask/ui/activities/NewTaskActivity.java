package org.karolgurecki.autotask.ui.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.factory.TaskFactory;
import org.karolgurecki.autotask.service.StartUpService;
import org.karolgurecki.autotask.tasks.TaskObject;
import org.karolgurecki.autotask.ui.ListDialog;
import org.karolgurecki.autotask.ui.adapters.ExpandableListAdapter;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;
import org.karolgurecki.autotask.utils.ExceptionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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


    private LocalBroadcastManager localBroadcastManager;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<TaskObject>> listDataChild;
    private String add_trigger;
    private String add_action;
    private List<TaskObject> taskTriggerList = new ArrayList<>();
    private List<TaskObject> taskActionsList = new ArrayList<>();
    private Dialog dialog = null;
    private BroadcastReceiver addTaskObjectreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String type = intent.getStringExtra(ConstanceFieldHolder.EXTRA_TYPE);
            int index = intent.getIntExtra(ConstanceFieldHolder.EXTRA_INDEX, -1);

            if (StringUtils.isNotBlank(type) && index >= 0) {
                if (ConstanceFieldHolder.TRIGGER_TYPE.equalsIgnoreCase(type)) {
                    openConfigDialog(ConstanceFieldHolder.triggersList, taskTriggerList, index);
                } else {
                    openConfigDialog(ConstanceFieldHolder.actionsList, taskActionsList, index);
                }
            }

            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }


        }

        private void openConfigDialog(List<TaskObject> taskObjectList, List<TaskObject> taskList, int index) {
            try {
                tempChosenObject = taskObjectList.get(index).getClass().newInstance();
                tempChosenObject.openDialog(NewTaskActivity.this);
                ;
            } catch (InstantiationException | IllegalAccessException e) {
                Log.e(ConstanceFieldHolder.AUTOTASK_TAG, ExceptionUtils.stackTraceToString(e));
            }
        }
    };
    private BroadcastReceiver confirmAddingObject = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra(ConstanceFieldHolder.EXTRA_TYPE);
            if (StringUtils.isNotBlank(type)) {
                if (type.equalsIgnoreCase(ConstanceFieldHolder.TRIGGER_TYPE)) {
                    taskTriggerList.add(tempChosenObject);
                } else {
                    taskActionsList.add(tempChosenObject);
                }
                listAdapter.notifyDataSetChanged();
            }
        }
    };

    private TaskObject tempChosenObject = null;
    private Builder alertDialogBuilder;
    private TextView taskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.edit_task);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        if (ConstanceFieldHolder.actionsList == null) {
            ConstanceFieldHolder.actionsList = TaskFactory.createTaskObjects(getResources().openRawResource(R.raw.actions_classes),
                    ConstanceFieldHolder.TASKS_CLASSES_PROPERTY, false);
        }

        if (ConstanceFieldHolder.triggersList == null) {
            ConstanceFieldHolder.triggersList = TaskFactory.createTaskObjects(getResources().openRawResource(R.raw.trigers_classes),
                    ConstanceFieldHolder.TASKS_CLASSES_PROPERTY, false);
        }
        taskName = (TextView) findViewById(R.id.editText);

        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.itemLIst);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);
        expListView.expandGroup(0, false);
        expListView.expandGroup(1, false);

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
        alertDialogBuilder.setNeutralButton(android.R.string.cancel, new OnClickListener() {
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

    private void saveTask() {
        FileOutputStream stream = null;
        try {
            File autoTaskFolder = getFilesDir();
            stream = new FileOutputStream(new File(autoTaskFolder.getPath(),
                    String.format("%s.%s", taskName.getText().toString().replace(' ',
                            ConstanceFieldHolder.SPACE_REPLACEMENT), ConstanceFieldHolder.PROPERTIES_FILE_EXTENTION)
            ));
            Properties properties = new Properties();
            properties.setProperty(ConstanceFieldHolder.NAME_PROPERTY, taskName.getText().toString());
            appendConfig(properties, ConstanceFieldHolder.TRIGGER_CLASSES, taskTriggerList);
            appendConfig(properties, ConstanceFieldHolder.ACTION_CLASSES, taskActionsList);
            properties.store(stream, ConstanceFieldHolder.PROPERTIES_COMMENT);
        } catch (IOException e) {
            Log.e(ConstanceFieldHolder.AUTOTASK_TAG, ExceptionUtils.stackTraceToString(e));
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    Log.e(ConstanceFieldHolder.AUTOTASK_TAG, ExceptionUtils.stackTraceToString(e));
                }
            }
        }
        startService(new Intent(this, StartUpService.class));
    }

    private void appendConfig(Properties properties, String classesPropName, List<TaskObject> taskObjectList) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < taskObjectList.size(); i++) {
            builder.append(taskObjectList.get(i).getClass().getName());
            if (i + 1 != taskObjectList.size()) {
                builder.append(ConstanceFieldHolder.COMMA);
            }
        }
        properties.setProperty(classesPropName, builder.toString());
        for (TaskObject taskObject : taskObjectList) {
            properties.setProperty(String.format("%s.config", taskObject.getClass().getName()), taskObject.getConfig());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        localBroadcastManager.registerReceiver(addTaskObjectreceiver,
                new IntentFilter(ConstanceFieldHolder.INTERNAL_ADD_TASK_OBJECT_ACTION));
        localBroadcastManager.registerReceiver(confirmAddingObject,
                new IntentFilter(ConstanceFieldHolder.INTERNAL_CONFIRM_ADDING_TASK_OBJECT_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        localBroadcastManager.unregisterReceiver(addTaskObjectreceiver);
        localBroadcastManager.unregisterReceiver(confirmAddingObject);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(addTaskObjectreceiver);
        localBroadcastManager.unregisterReceiver(confirmAddingObject);
    }

    @Override
    public void onBackPressed() {
        alertDialogBuilder.show();
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
            chooseTaskObject(ConstanceFieldHolder.actionsList, getString(R.string.add_action), ConstanceFieldHolder.ACTION_TYPE);
        } else if (item.getTitle().equals(add_trigger)) {
            chooseTaskObject(ConstanceFieldHolder.triggersList, getString(R.string.add_trigger), ConstanceFieldHolder.TRIGGER_TYPE);
        } else {
            alertDialogBuilder.show();
        }
        return true;
    }

    private void chooseTaskObject(List<TaskObject> taskObjects, String dialogTitle, String type) {
        dialog = new ListDialog(this, taskObjects, dialogTitle, type);
    }
}