package org.karolgurecki.autotask.factory;

import android.util.Log;
import org.karolgurecki.autotask.tasks.AbstractTaskObject;
import org.karolgurecki.autotask.utils.ExceptionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by: Karol Górrecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public class TaskFactory {
    private TaskFactory() {

    }

    /**
     * Creates list of task objects from property file in given path
     *
     * @param propertyFile the path to property file
     * @param rootProperty the root property from which list of the classes to be created
     * @param loadConfig   this flag indicted if configuration of this class with be loaded from file
     *
     * @return a list of AbstractTaskObject objects
     */
    public static List<AbstractTaskObject> createTaskObjects(String propertyFile, String rootProperty, boolean loadConfig) {
        return createTaskObjects(new File(propertyFile), rootProperty, loadConfig);
    }

    /**
     * Creates list of task objects from property file
     *
     * @param propertyFile the property file
     * @param rootProperty the root property from which list of the classes to be created
     * @param loadConfig   this flag indicted if configuration of this class with be loaded from file
     *
     * @return a list of AbstractTaskObject objects
     */
    public static List<AbstractTaskObject> createTaskObjects(File propertyFile, String rootProperty, boolean loadConfig) {
        try {
            return createTaskObjects(new FileReader(propertyFile), rootProperty, loadConfig);
        } catch (FileNotFoundException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        return null;
    }

    /**
     * Creates list of task objects from given inputStream
     *
     * @param propertyInputStream the inputStream
     * @param rootProperty        the root property from which list of the classes to be created
     * @param loadConfig          this flag indicted if configuration of this class with be loaded from file
     *
     * @return a list of AbstractTaskObject objects
     */
    public static List<AbstractTaskObject> createTaskObjects(InputStream propertyInputStream, String rootProperty,
                                                             boolean loadConfig) {
        return createTaskObjects(new InputStreamReader(propertyInputStream), rootProperty, loadConfig);
    }

    /**
     * Creates list of task objects from given reader
     *
     * @param propertyReader the reader
     * @param rootProperty   the root property from which list of the classes to be created
     * @param loadConfig     this flag indicted if configuration of this class with be loaded from file
     *
     * @return a list of AbstractTaskObject objects
     */
    public static List<AbstractTaskObject> createTaskObjects(Reader propertyReader, String rootProperty,
                                                             boolean loadConfig) {
        Properties properties = new Properties();

        try {
            properties.load(propertyReader);
            return createTaskObjects(properties, rootProperty, loadConfig);
        } catch (IOException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }


        return new LinkedList<>();
    }

    /**
     * Creates list of task objects from given properties
     *
     * @param properties   the properties
     * @param rootProperty the root property from which list of the classes to be created
     * @param loadConfig   this flag indicted if configuration of this class with be loaded from file
     *
     * @return a list of AbstractTaskObject objects
     */
    public static List<AbstractTaskObject> createTaskObjects(Properties properties, String rootProperty,
                                                             boolean loadConfig) {
        List<AbstractTaskObject> tasks = new ArrayList<AbstractTaskObject>();

        String[] taskClasses = null;

        try {
            taskClasses = properties.getProperty(rootProperty).split(",");
        } catch (NullPointerException e) {
            Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
        }

        for (String taskClazz : taskClasses) {

            try {
                Class clazz = Class.forName(taskClazz);
                AbstractTaskObject taskObject = (AbstractTaskObject) clazz.newInstance();
                if (loadConfig) {
                    taskObject.setConfig(properties.getProperty(String.format("%s.config", taskClazz)));
                }
                tasks.add(taskObject);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                Log.e("AutoTask", ExceptionUtils.stackTraceToString(e));
            }
        }

        return tasks;
    }
}
