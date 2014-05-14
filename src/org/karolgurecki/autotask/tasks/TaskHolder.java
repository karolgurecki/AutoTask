package org.karolgurecki.autotask.tasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public final class TaskHolder extends BroadcastReceiver {
    private List<TaskObject> triggerList = new ArrayList<>();

    private List<TaskObject> actionList = new ArrayList<>();

    private Map<String, Boolean> triggerTriggered = new HashMap<>();

    private Long timestamp;

    private String name;

    private Context context;

    /**
     * Instantiates a new Task holder.
     *
     * @param triggerList the trigger list
     * @param actionList  the action list
     * @param timestamp   the timestamp
     * @param context     the context
     * @param name        the name
     */
    public TaskHolder(List<TaskObject> triggerList, List<TaskObject> actionList, Long timestamp,
                      Context context, String name) {
        this(triggerList, actionList, timestamp, context);
        this.name = name;
    }

    /**
     * Instantiates a new Task holder.
     *
     * @param triggerList the trigger list
     * @param actionList  the action list
     * @param timestamp   the timestamp
     * @param context     the context
     */
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
        Intent intent = new Intent(intentActions);
        registerReceivers(triggerList, intent);
        registerReceivers(actionList, null);

        for (TaskObject TaskObject : triggerList) {
            triggerTriggered.put(TaskObject.getClass().getName(), Boolean.FALSE);
        }
    }

    private void registerReceivers(List<TaskObject> taskObjectList, Intent intent) {
        for (TaskObject taskObject : taskObjectList) {
            LocalBroadcastManager.getInstance(context).registerReceiver(taskObject.getBroadcastReceiver(),
                    taskObject.getIntentFilter());
            taskObject.setResponseIntent(intent);
        }
    }

    /**
     * On destoy.
     */
    public void onDestoy() {
        unregisterReceivers(triggerList);
        unregisterReceivers(actionList);
    }

    private void unregisterReceivers(List<TaskObject> taskObjectList) {
        for (TaskObject taskObject : taskObjectList) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(taskObject.getBroadcastReceiver());
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String className = intent.getStringExtra("className");
        Boolean switchValue = intent.getBooleanExtra("switchValue", false);

        triggerTriggered.put(className, switchValue);

        if (!triggerTriggered.containsValue(false)) {
            for (TaskObject TaskObject : actionList) {
                context.sendBroadcast(TaskObject.getIntent());
            }
        }
    }

    /**
     * Gets trigger list.
     *
     * @return the trigger list
     */
    public List<TaskObject> getTriggerList() {
        return triggerList;
    }

    /**
     * Sets trigger list.
     *
     * @param triggerList the trigger list
     */
    public void setTriggerList(List<TaskObject> triggerList) {
        this.triggerList = triggerList;
    }

    /**
     * Gets action list.
     *
     * @return the action list
     */
    public List<TaskObject> getActionList() {
        return actionList;
    }

    /**
     * Sets action list.
     *
     * @param actionList the action list
     */
    public void setActionList(List<TaskObject> actionList) {
        this.actionList = actionList;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }


}
