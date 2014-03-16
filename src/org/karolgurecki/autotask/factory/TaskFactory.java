package org.karolgurecki.autotask.factory;

import android.app.Activity;
import android.util.Log;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.tasks.TaskObject;
import org.karolgurecki.autotask.utils.ExceptionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by <a href="mailto:karolgurecki@gmail.com">Karol Górecki</a> on 09.10.13.
 */
public class TaskFactory {
    private TaskFactory() {

    }


    public static List<TaskObject> tasksWithoutConfigFromPropertyFileCreator(String propertyFile) {
        return tasksWithoutConfigFromPropertyFileCreator(new File(propertyFile));
    }

    public static List<TaskObject> tasksWithoutConfigFromPropertyFileCreator(File propertyFile) {
        try {
            return tasksWithoutConfigFromPropertyFileCreator(new FileReader(propertyFile));
        } catch (FileNotFoundException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return null;
    }

    public static List<TaskObject> tasksWithoutConfigFromPropertyFileCreator(InputStream propertyInputStream) {
        return tasksWithoutConfigFromPropertyFileCreator(new InputStreamReader(propertyInputStream));
    }

    public static List<TaskObject> tasksWithoutConfigFromPropertyFileCreator(Reader propertyReader) {
        Properties properties = new Properties();

        try {
            properties.load(propertyReader);
        } catch (IOException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return tasksWithoutConfigFromPropertyFileCreator(properties);

    }

    public static List<TaskObject> tasksWithoutConfigFromPropertyFileCreator(Properties properties) {
        List<TaskObject> tasks = new ArrayList<TaskObject>();

        String[] taskClasses = null;

        try {
            taskClasses = properties.getProperty("tasks.classes").split(",");
        } catch (NullPointerException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        for (String taskClazz : taskClasses) {

            try {
                Class clazz = Class.forName(taskClazz);
                tasks.add((TaskObject) clazz.newInstance());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
            }
        }

        return tasks;
    }

    public static List<TaskObject> EveryTaskCreator(Activity activity) {
        List<TaskObject> tasks = tasksWithoutConfigFromPropertyFileCreator(activity.getResources().openRawResource(R.raw.actions_classes));

        for (TaskObject task : tasks) {
            //task.setActivity(activity);
        }

        return tasks;
    }

    public static List<TaskObject> tasksWithConfigFromPropertyFileCreator(String propertyFile) {
        List<TaskObject> tasks = tasksWithConfigFromPropertyFileCreator(new File(propertyFile));

        for (TaskObject task : tasks) {
            // task.setActivity(new Activity());
        }

        return tasks;
    }

    public static List<TaskObject> tasksWithConfigFromPropertyFileCreator(File propertyFile) {
        try {
            return tasksWithConfigFromPropertyFileCreator(new FileReader(propertyFile));
        } catch (FileNotFoundException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return null;
    }

    public static List<TaskObject> tasksWithConfigFromPropertyFileCreator(InputStream propertyInputStream) {
        return tasksWithConfigFromPropertyFileCreator(new InputStreamReader(propertyInputStream));
    }

    public static List<TaskObject> tasksWithConfigFromPropertyFileCreator(Reader propertyReader) {
        Properties properties = new Properties();

        try {
            properties.load(propertyReader);
        } catch (IOException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return tasksWithConfigFromPropertyFileCreator(properties);

    }

    public static List<TaskObject> tasksWithConfigFromPropertyFileCreator(Properties properties) {
        List<TaskObject> tasks = new ArrayList<>();

        String[] taskClasses = null;

        try {
            taskClasses = properties.getProperty("tasks.classes").split(",");
        } catch (NullPointerException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        for (String taskClazz : taskClasses) {

            try {
                Class clazz = Class.forName(taskClazz);
                TaskObject task = (TaskObject) clazz.newInstance();

                //task.(properties.getProperty(taskClazz + ".config"));
                tasks.add(task);

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
            }
        }

        return tasks;
    }
}
