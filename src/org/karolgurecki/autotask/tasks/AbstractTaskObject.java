package org.karolgurecki.autotask.tasks;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public abstract class AbstractTaskObject extends BroadcastReceiver implements TaskObject {

    protected IntentFilter responseIntentFilter;

    @Override
    public void setResponseIntentFilter(IntentFilter intentFilter) {
        this.responseIntentFilter = intentFilter;
    }
}
