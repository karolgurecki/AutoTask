package org.karolgurecki.autotask.factory;

import android.app.Activity;
import android.util.Log;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.tasks.Task;
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


    public static List<Task> tasksWithoutConfigFromPropertyFileCreator(String propertyFile) {
        return tasksWithoutConfigFromPropertyFileCreator(new File(propertyFile));
    }

    public static List<Task> tasksWithoutConfigFromPropertyFileCreator(File propertyFile) {
        try {
            return tasksWithoutConfigFromPropertyFileCreator(new FileReader(propertyFile));
        } catch (FileNotFoundException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return null;
    }

    public static List<Task> tasksWithoutConfigFromPropertyFileCreator(InputStream propertyInputStream) {
        return tasksWithoutConfigFromPropertyFileCreator(new InputStreamReader(propertyInputStream));
    }

    public static List<Task> tasksWithoutConfigFromPropertyFileCreator(Reader propertyReader) {
        Properties properties = new Properties();

        try {
            properties.load(propertyReader);
        } catch (IOException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return tasksWithoutConfigFromPropertyFileCreator(properties);

    }

    public static List<Task> tasksWithoutConfigFromPropertyFileCreator(Properties properties) {
        List<Task> tasks = new ArrayList<Task>();

        String[] taskClasses = null;

        try {
            taskClasses = properties.getProperty("tasks.classes").split(",");
        } catch (NullPointerException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        for (String taskClazz : taskClasses) {

            try {
                Class clazz = Class.forName(taskClazz);
                tasks.add((Task) clazz.newInstance());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
            }
        }

        return tasks;
    }

    public static List<Task> EveryTaskCreator(Activity activity) {
        List<Task> tasks = tasksWithoutConfigFromPropertyFileCreator(activity.getResources().openRawResource(R.raw.task_classes));

        for (Task task : tasks) {
            task.setActivity(activity);
        }

        return tasks;
    }

    public static List<Task> tasksWithConfigFromPropertyFileCreator(String propertyFile) {
        List<Task> tasks = tasksWithConfigFromPropertyFileCreator(new File(propertyFile));

        for (Task task : tasks) {
            task.setActivity(new Activity());
        }

        return tasks;
    }

    public static List<Task> tasksWithConfigFromPropertyFileCreator(File propertyFile) {
        try {
            return tasksWithConfigFromPropertyFileCreator(new FileReader(propertyFile));
        } catch (FileNotFoundException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return null;
    }

    public static List<Task> tasksWithConfigFromPropertyFileCreator(InputStream propertyInputStream) {
        return tasksWithConfigFromPropertyFileCreator(new InputStreamReader(propertyInputStream));
    }

    public static List<Task> tasksWithConfigFromPropertyFileCreator(Reader propertyReader) {
        Properties properties = new Properties();

        try {
            properties.load(propertyReader);
        } catch (IOException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return tasksWithConfigFromPropertyFileCreator(properties);

    }

    public static List<Task> tasksWithConfigFromPropertyFileCreator(Properties properties) {
        List<Task> tasks = new ArrayList<>();

        String[] taskClasses = null;

        try {
            taskClasses = properties.getProperty("tasks.classes").split(",");
        } catch (NullPointerException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        for (String taskClazz : taskClasses) {

            try {
                Class clazz = Class.forName(taskClazz);
                Task task = (Task) clazz.newInstance();

                task.configure(properties.getProperty(taskClazz + ".config"));
                tasks.add(task);

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
            }
        }

        return tasks;
    }
}
