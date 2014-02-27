package org.karolgurecki.autotask.ui.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.factory.TaskFactory;
import org.karolgurecki.autotask.tasks.TaskObject;
import org.karolgurecki.autotask.tasks.impl.TestTaskObject;
import org.karolgurecki.autotask.ui.adapters.ExpandableListAdapter;
import org.karolgurecki.autotask.ui.dialogs.ListDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nappa
 * Date: 29.09.13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */

public class NewTaskActivity extends Activity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<TaskObject>> listDataChild;
    private String add_trigger;
    private String add_action;
    private static List<TaskObject> actionsList;
    private static List<TaskObject> triggersList;

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

        expListView = (ExpandableListView) findViewById(R.id.itemLIst);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

        add_trigger = getString(R.string.add_trigger);
        add_action = getString(R.string.add_action);
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

        // Adding child data
        List<TaskObject> top250 = new ArrayList<>();
        top250.add(new TestTaskObject("The Shawshank Redemption", ""));
        top250.add(new TestTaskObject("The Shawshank Redemption", ""));

        List<TaskObject> nowShowing = new ArrayList<>();
        nowShowing.add(new TestTaskObject("The Shawshank Redemption", ""));

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
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
            chooseTaskObject(1,actionsList,getString(R.string.add_action));
        } else if (item.getTitle().equals(add_trigger)) {
            chooseTaskObject(0,triggersList,getString(R.string.add_trigger));
        } else {
            finish();
        }
        return true;
    }

    private void chooseTaskObject(int listAdapterNum, List<TaskObject> taskObjects, String dialogTitle) {
        TaskObject taskObject = new TestTaskObject();

        Dialog dialog = new ListDialog(this, taskObjects, dialogTitle);
        Log.i("AutoTask","h");
        listAdapter.addItemToGroup(listAdapterNum, taskObject);
    }

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}