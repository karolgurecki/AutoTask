package org.karolgurecki.autotask.utils;

import org.karolgurecki.autotask.tasks.TaskHolder;
import org.karolgurecki.autotask.tasks.TaskObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public class ConstanceFiledHolder {

    public static final Set<TaskHolder> taskHolderSet = new HashSet<>();
    public static final String PROPERTIES_FILE_EXTENTION = "properties";
    public static final String TRIGGER_CLASSES = "trigger.classes";
    public static final String ACTION_CLASSES = "action.classes";
    public static List<TaskObject> actionsList;
    public static List<TaskObject> triggersList;

    private ConstanceFiledHolder() {

    }
}
