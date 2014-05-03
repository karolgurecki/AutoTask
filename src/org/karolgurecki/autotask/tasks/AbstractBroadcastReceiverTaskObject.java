package org.karolgurecki.autotask.tasks;

import android.content.BroadcastReceiver;
import android.content.Intent;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public abstract class AbstractBroadcastReceiverTaskObject extends BroadcastReceiver implements TaskObject {

    protected Intent responseIntent;

    @Override
    public void setResponseIntent(Intent intent) {
        this.responseIntent = intent;
    }

    @Override
    public BroadcastReceiver getBroadcastReceiver() {
        return this;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.BROADCASTRECEIVER;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
