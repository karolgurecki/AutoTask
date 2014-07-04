package org.karolgurecki.autotask.tasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public class TaskHolderMap extends BroadcastReceiver {
    public static final String TASK_HOLDER_MAP_ACTION = "org.karolgurecki.autotask.tasks.TASK_HOLDER_MAP_ACTION";
    private static final Map<String, TaskHolder> TASK_HOLDER_HASH_MAP = new HashMap<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskHolderName = intent.getStringExtra(ConstanceFieldHolder.TASK_HOLDER_NAME_EXTRA);

        TaskHolder holder = get(taskHolderName);
        if (holder != null) {
            holder.onReceive(context, intent);
        }
    }

    /**
     * Gets task holder for given name
     *
     * @param taskHolderName the name
     *
     * @return the {@link org.karolgurecki.autotask.tasks.TaskHolder} object
     */
    public TaskHolder get(String taskHolderName) {
        return TASK_HOLDER_HASH_MAP.get(taskHolderName);
    }

    /**
     * Put TaskHolder object in the map with given key
     *
     * @param name       the key
     * @param taskHolder the TaskHolder object
     */
    public TaskHolder put(String name, TaskHolder taskHolder) {
        return TASK_HOLDER_HASH_MAP.put(name, taskHolder);
    }

    /**
     * Removes given key  from the map
     *
     * @param name the key
     */
    public void remove(String name) {
        TASK_HOLDER_HASH_MAP.remove(name).onDestroy();
    }

    /**
     * Destroy TaskHolder with given name but not remove it from the map
     *
     * @param name the name of TaskHolder
     */
    public void destroy(String name) {
        get(name).onDestroy();
    }

    public void destroyAll() {
        for (TaskHolder holder : TASK_HOLDER_HASH_MAP.values()) {
            holder.onDestroy();
        }
    }

}
