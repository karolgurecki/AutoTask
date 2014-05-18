package org.karolgurecki.autotask.tasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;

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

    private String intentAction;

    private Intent intent;

    private IntentFilter intentFilter;

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
        intentAction = String.format("%s.%s%d", getClass().getName(), name, timestamp);
        intent = new Intent(intentAction);
        intentFilter = new IntentFilter(intentAction);
        registerReceivers(triggerList, intent);
        registerReceivers(actionList, null);

        for (TaskObject TaskObject : triggerList) {
            triggerTriggered.put(TaskObject.getClass().getName(), Boolean.FALSE);
        }
    }

    private void registerReceivers(List<TaskObject> taskObjectList, Intent intent) {
        for (TaskObject taskObject : taskObjectList) {
            if (taskObject.getTaskType().equals(TaskType.BROADCASTRECEIVER)) {
                context.registerReceiver(taskObject.getBroadcastReceiver(), taskObject.getIntentFilter());
            } else {
                taskObject.start();
            }
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
            if (taskObject.getTaskType().equals(TaskType.BROADCASTRECEIVER)) {
                context.unregisterReceiver(taskObject.getBroadcastReceiver());
            } else {
                taskObject.stop();
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String className = intent.getStringExtra(ConstanceFieldHolder.EXTRA_CLASS_NAAME);
        Boolean switchValue = intent.getBooleanExtra(ConstanceFieldHolder.EXTRA_TRIGGER_ACTIVATED, false);

        triggerTriggered.put(className, switchValue);

        if (!triggerTriggered.containsValue(false)) {
            for (TaskObject TaskObject : actionList) {
                context.sendBroadcast(TaskObject.getIntent());
            }
        }
    }


    /**
     * Gets intent.
     *
     * @return Value of intent.
     */
    public Intent getIntent() {
        return intent;
    }

    /**
     * Gets intentFilter.
     *
     * @return Value of intentFilter.
     */
    public IntentFilter getIntentFilter() {
        return intentFilter;
    }
}
