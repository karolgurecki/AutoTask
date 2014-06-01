package org.karolgurecki.autotask.tasks;

import android.content.Context;
import android.content.Intent;
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
public final class TaskHolder {

    private Intent intent;

    private List<TaskObject> triggerList = new ArrayList<>();

    private List<TaskObject> actionList = new ArrayList<>();

    private Map<String, Boolean> triggerTriggered = new HashMap<>();


    private String name;

    private Context context;

    /**
     * Instantiates a new Task holder.
     *
     * @param triggerList the trigger list
     * @param actionList  the action list
     * @param context     the context
     * @param name        the name
     */
    public TaskHolder(List<TaskObject> triggerList, List<TaskObject> actionList, Context context, String name) {
        this.triggerList = triggerList;
        this.actionList = actionList;
        this.context = context;
        this.name = name;
    }

    /**
     * It's called when task is creating
     */
    public TaskHolder onCreate() {
        intent = new Intent(TaskHolderMap.TASK_HOLDER_MAP_ACTION);
        intent.putExtra(ConstanceFieldHolder.TASK_HOLDER_NAME_EXTRA, name);
        registerReceivers(triggerList);
        registerReceivers(actionList);

        for (TaskObject TaskObject : triggerList) {
            triggerTriggered.put(TaskObject.getClass().getName(), Boolean.FALSE);
        }
        return this;
    }

    private void registerReceivers(List<TaskObject> taskObjectList) {
        for (TaskObject taskObject : taskObjectList) {
            if (taskObject.getTaskType().equals(TaskType.BROADCASTRECEIVER)) {
                context.registerReceiver(taskObject.getBroadcastReceiver(), taskObject.getIntentFilter());
            } else {
                taskObject.start();
            }
            taskObject.setResponseIntent(intent);
            taskObject.assignResponseIntentToActivationStatus();
        }
    }

    /**
     * On destroy.
     */
    public void onDestroy() {
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

    public void onReceive(Context context, Intent intent) {
        String className = intent.getStringExtra(ConstanceFieldHolder.EXTRA_CLASS_NAAME);
        Boolean switchValue = intent.getBooleanExtra(ConstanceFieldHolder.EXTRA_TRIGGER_ACTIVATED, false);

        triggerTriggered.put(className, switchValue);

        if (!triggerTriggered.containsValue(false)) {
            for (TaskObject TaskObject : actionList) {
                Intent taskObjectIntent = TaskObject.getIntent();
                taskObjectIntent.putExtra(ConstanceFieldHolder.TASK_HOLDER_NAME_EXTRA, name);
                context.sendBroadcast(taskObjectIntent);
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
}
