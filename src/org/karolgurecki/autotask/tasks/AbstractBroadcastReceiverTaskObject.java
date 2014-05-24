package org.karolgurecki.autotask.tasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;

import java.util.Map;
import java.util.Set;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public abstract class AbstractBroadcastReceiverTaskObject extends BroadcastReceiver implements TaskObject {

    protected static Intent responseIntent;

    protected Boolean activated;

    @Override
    public void onReceive(Context context, Intent intent) {
        Map<Boolean, Set<Intent>> activationSet = receive(context, intent);
        for (Map.Entry<Boolean, Set<Intent>> activationEntry : activationSet.entrySet()) {
            for (Intent intentFromEntry : activationEntry.getValue()) {
                intentFromEntry.putExtra(ConstanceFieldHolder.EXTRA_TRIGGER_ACTIVATED, activationEntry.getKey());
                intentFromEntry.putExtra(ConstanceFieldHolder.EXTRA_CLASS_NAAME, getClass().getName());

                context.sendBroadcast(intentFromEntry);
            }
        }
    }


    protected abstract Map<Boolean, Set<Intent>> receive(Context context, Intent intent);

    @Override
    public void setResponseIntent(Intent intent) {
        responseIntent = intent;
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
