package org.karolgurecki.autotask.factory;

import android.app.Activity;
import android.util.Log;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.tasks.AbstractTask;
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


    public static List<AbstractTask> tasksWithoutConfigFromPropertyFileCreator(String propertyFile) {
        return tasksWithoutConfigFromPropertyFileCreator(new File(propertyFile));
    }

    public static List<AbstractTask> tasksWithoutConfigFromPropertyFileCreator(File propertyFile) {
        try {
            return tasksWithoutConfigFromPropertyFileCreator(new FileReader(propertyFile));
        } catch (FileNotFoundException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return null;
    }

    public static List<AbstractTask> tasksWithoutConfigFromPropertyFileCreator(InputStream propertyInputStream) {
        return tasksWithoutConfigFromPropertyFileCreator(new InputStreamReader(propertyInputStream));
    }

    public static List<AbstractTask> tasksWithoutConfigFromPropertyFileCreator(Reader propertyReader) {
        Properties properties = new Properties();

        try {
            properties.load(propertyReader);
        } catch (IOException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return tasksWithoutConfigFromPropertyFileCreator(properties);

    }

    public static List<AbstractTask> tasksWithoutConfigFromPropertyFileCreator(Properties properties) {
        List<AbstractTask> tasks = new ArrayList<>();

        String[] taskClasses = null;

        try {
            taskClasses = properties.getProperty("tasks.classes").split(",");
        } catch (NullPointerException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        for (String taskClazz : taskClasses) {

            try {
                Class clazz = Class.forName(taskClazz);
                tasks.add((AbstractTask) clazz.newInstance());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
            }
        }

        return tasks;
    }

    public static List<AbstractTask> EveryTaskCreator(Activity activity) {
        return tasksWithoutConfigFromPropertyFileCreator(activity.getResources().openRawResource(R.raw.task_classes));
    }

    public static List<AbstractTask> tasksWithConfigFromPropertyFileCreator(String propertyFile) {
        return tasksWithConfigFromPropertyFileCreator(new File(propertyFile));
    }

    public static List<AbstractTask> tasksWithConfigFromPropertyFileCreator(File propertyFile) {
        try {
            return tasksWithConfigFromPropertyFileCreator(new FileReader(propertyFile));
        } catch (FileNotFoundException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return null;
    }

    public static List<AbstractTask> tasksWithConfigFromPropertyFileCreator(InputStream propertyInputStream) {
        return tasksWithConfigFromPropertyFileCreator(new InputStreamReader(propertyInputStream));
    }

    public static List<AbstractTask> tasksWithConfigFromPropertyFileCreator(Reader propertyReader) {
        Properties properties = new Properties();

        try {
            properties.load(propertyReader);
        } catch (IOException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return tasksWithConfigFromPropertyFileCreator(properties);

    }

    public static List<AbstractTask> tasksWithConfigFromPropertyFileCreator(Properties properties) {
        List<AbstractTask> tasks = new ArrayList<>();

        String[] taskClasses = null;

        try {
            taskClasses = properties.getProperty("tasks.classes").split(",");
        } catch (NullPointerException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        for (String taskClazz : taskClasses) {

            try {
                Class clazz = Class.forName(taskClazz);
                AbstractTask task = (AbstractTask) clazz.newInstance();

                task.configure(properties.getProperty(taskClazz + ".config"));
                tasks.add(task);

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
            }
        }

        return tasks;
    }
}
