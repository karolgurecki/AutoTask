package org.karolgurecki.autotask.utils;

import org.karolgurecki.autotask.tasks.TaskObject;

import java.util.List;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public class ConstanceFieldHolder {

    public static final String PROPERTIES_FILE_EXTENTION = "properties";
    public static final String TRIGGER_CLASSES = "trigger.classes";
    public static final String ACTION_CLASSES = "action.classes";
    public static final String EXTRA_TRIGGER_ACTIVATED = "org.karolgurecki.autotask.tasks.triggers.TRIGGER_ACTIVATED";
    public static final String EXTRA_CLASS_NAAME = "org.karolgurecki.autotask.tasks.triggers.CLASS_NAME";
    public static final String AUTOTASK_TAG = "AutoTask";
    public static final String COMMA = ",";
    public static final String NAME_PROPERTY = "name";
    public static final String PROPERTIES_COMMENT = "This file is auto generated by AutoTask DO NOT CHANGE IT!";
    public static final char SPACE_REPLACEMENT = '_';
    public static final String TASKS_CLASSES_PROPERTY = "tasks.classes";
    public static final String IS_ON_EXTRA = "org.karolgurecki.autotask.ui.onOffDialog.is_on";
    public static final String TASK_HOLDER_NAME_EXTRA = "org.karolgurecki.autotask.tasks.taskHolder.NAME_EXTRA";
    public static final String INTERNAL_ADD_TASK_OBJECT_ACTION = "org.karolgurecki.autotask.addTaskObject";
    public static final String INTERNAL_CONFIRM_ADDING_TASK_OBJECT_ACTION = "org.karolgurecki.autotask.confirmAddingTaskObject";
    public static final String EXTRA_TYPE = "TYPE";
    public static final String EXTRA_INDEX = "INDEX";
    public static final String TRIGGER_TYPE="TRIGGER";
    public static final String ACTION_TYPE="ACTION";
    public static List<TaskObject> actionsList;
    public static List<TaskObject> triggersList;

    private ConstanceFieldHolder() {

    }
}
