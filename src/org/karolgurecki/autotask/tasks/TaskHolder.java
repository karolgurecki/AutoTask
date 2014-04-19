package org.karolgurecki.autotask.tasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public final class TaskHolder extends BroadcastReceiver {
    private List<TaskObject> triggerList = new ArrayList<>();

    private List<TaskObject> actionList = new ArrayList<>();

    private Long timestamp;

    private String name;

    private Context context;

    public TaskHolder(List<TaskObject> triggerList, List<TaskObject> actionList, Long timestamp, Context context, String name) {
        this(triggerList, actionList, timestamp, context);
        this.name = name;
    }

    public TaskHolder(List<TaskObject> triggerList, List<TaskObject> actionList, Long timestamp, Context context) {
        this.triggerList = triggerList;
        this.actionList = actionList;
        this.timestamp = timestamp;
        this.context = context;
    }

    /**
     * It's called when task is creating
     */
    public void onCreate() {
        String intentActions = String.format("%s_%d", name, timestamp);
        IntentFilter filter = new IntentFilter(intentActions);
        registerReceivers(triggerList, filter);
        registerReceivers(actionList, null);
    }

    public void onDestoy() {
        unregisterReceivers(triggerList);
        unregisterReceivers(actionList);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    private void unregisterReceivers(List<TaskObject> taskObjectList) {
        for (TaskObject taskObject : taskObjectList) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(taskObject.getBroadcastReceiver());
        }
    }

    private void registerReceivers(List<TaskObject> taskObjectList, IntentFilter intentFilter) {
        for (TaskObject taskObject : taskObjectList) {
            LocalBroadcastManager.getInstance(context).registerReceiver(taskObject.getBroadcastReceiver(),
                    taskObject.getIntentFilter());
            taskObject.setResponseIntentFilter(intentFilter);
        }
    }

    public List<TaskObject> getTriggerList() {
        return triggerList;
    }

    public void setTriggerList(List<TaskObject> triggerList) {
        this.triggerList = triggerList;
    }

    public List<TaskObject> getActionList() {
        return actionList;
    }

    public void setActionList(List<TaskObject> actionList) {
        this.actionList = actionList;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
