package org.karolgurecki.autotask.tasks.impl;

import android.content.IntentFilter;
import org.karolgurecki.autotask.tasks.TaskObject;

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
    public String getDisplayName() {
        return name;
    }

    @Override
    public String getDisplayConfiguration() {
        return config;
    }

    @Override
    public void openDialog() {

    }

    @Override
    public IntentFilter getIntentFilter() {
        return null;
    }

    @Override
    public String getConfig() {
        return "testConfig";
    }

}
