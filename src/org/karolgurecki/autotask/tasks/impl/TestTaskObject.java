package org.karolgurecki.autotask.tasks.impl;

import org.karolgurecki.autotask.tasks.TaskObject;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class TestTaskObject implements TaskObject {

    String name, config;

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
}
