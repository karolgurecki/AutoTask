package org.karolgurecki.autotask.tasks;

import android.content.BroadcastReceiver;
import android.content.Intent;

/**
 * Created by: Karol Górrecki <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public abstract class AbstractThreadTaskObject implements TaskObject {


    protected Intent responseIntent;

    @Override
    public void setResponseIntent(Intent intent) {
        this.responseIntent = intent;
    }

    @Override
    public BroadcastReceiver getBroadcastReceiver() {
        return null;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.THREAD;
    }
}
