package org.karolgurecki.autotask.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.tasks.TaskObject;
import org.karolgurecki.autotask.tasks.impl.TestTaskObject;
import org.karolgurecki.autotask.ui.adapters.ExpandableListAdapter;

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

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<TaskObject>> listDataChild;
    String add_trigger;
    String add_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.edit_task);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.itemLIst);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
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
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");

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

        TaskObject taskObject;

        if (item.getTitle().equals(add_action)) {
            taskObject = new TestTaskObject("Action added", "by menu");
            listAdapter.addItemToGroup(1, taskObject);
            listAdapter.notifyDataSetChanged();
        } else if (item.getTitle().equals(add_trigger)) {
            taskObject = new TestTaskObject("Trigger added", "by menu");
            listAdapter.addItemToGroup(0, taskObject);
            listAdapter.notifyDataSetChanged();
        } else {
            finish();
        }
        return true;
    }
}