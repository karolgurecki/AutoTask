package org.karolgurecki.autotask.tasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.karolgurecki.autotask.utils.ConstanceFiledHolder;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public abstract class AbstractBroadcastReceiverTaskObject extends BroadcastReceiver implements TaskObject {

    protected Intent responseIntent;

    protected Boolean activated;

    @Override
    public void onReceive(Context context, Intent intent) {
        receive(context, intent);
        responseIntent.putExtra(ConstanceFiledHolder.EXTRA_TRIGGER_ACTIVATED, activated);
        responseIntent.putExtra(ConstanceFiledHolder.EXTRA_CLASS_NAAME, getClass().getName());

        context.sendBroadcast(responseIntent);
    }


    protected abstract void receive(Context context, Intent intent);

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
