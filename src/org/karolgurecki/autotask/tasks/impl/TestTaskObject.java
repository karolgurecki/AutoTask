package org.karolgurecki.autotask.tasks.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import org.karolgurecki.autotask.tasks.TaskObject;
import org.karolgurecki.autotask.tasks.TaskType;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class TestTaskObject implements TaskObject {

    String name, config;

    public TestTaskObject() {
        this.name = this.getClass().getSimpleName();
    }

    public TestTaskObject(String name, String config) {
        this.name = name;
        this.config = config;
    }

    @Override
    public String getDisplayName(Context context) {
        return name;
    }

    @Override
    public String getDisplayConfiguration(Context context) {
        return config;
    }

    @Override
    public void openDialog(Context context) {

    }

    @Override
    public IntentFilter getIntentFilter() {
        return null;
    }

    @Override
    public void setResponseIntent(Intent intent) {

    }

    @Override
    public String getConfig() {
        return "testConfig";
    }

    @Override
    public void setConfig(String config) {

    }

    @Override
    public Intent getIntent() {
        return null;
    }

    @Override
    public BroadcastReceiver getBroadcastReceiver() {
        return null;
    }

    @Override
    public TaskType getTaskType() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

}
