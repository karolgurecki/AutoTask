package org.karolgurecki.autotask.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import org.karolgurecki.autotask.tasks.TaskHolder;
import org.karolgurecki.autotask.tasks.TaskHolderMap;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;
import org.karolgurecki.autotask.utils.ExceptionUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import static org.karolgurecki.autotask.factory.TaskFactory.createTaskObjects;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class StartUpService extends Service {

    public static final Set<String> TASK_PROPERTIES_NAME_SET = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    private static final TaskHolderMap TASK_HOLDER_MAP = new TaskHolderMap();
    private final static FileFilter FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String name = pathname.getName().toLowerCase();
            return name.endsWith(ConstanceFieldHolder.PROPERTIES_FILE_EXTENTION.toLowerCase()) &&
                    !TASK_PROPERTIES_NAME_SET.contains(name);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(ConstanceFieldHolder.AUTOTASK_TAG, "Boo2");
        File autoTaskFolder = getFilesDir();
        Log.d(ConstanceFieldHolder.AUTOTASK_TAG, "Boo3");
        if (autoTaskFolder.exists()) {
            File[] fileArray = autoTaskFolder.listFiles(FILE_FILTER);

            for (File file : fileArray) {
                try {
                    Log.d(ConstanceFieldHolder.AUTOTASK_TAG, String.format("Initializing task: %s", file.getName()));
                    Properties properties = new Properties();
                    properties.load(new FileReader(file));
                    String name = properties.getProperty(ConstanceFieldHolder.NAME_PROPERTY);
                    TaskHolder holder = new TaskHolder(
                            createTaskObjects(properties, ConstanceFieldHolder.TRIGGER_CLASSES, true),
                            createTaskObjects(properties, ConstanceFieldHolder.ACTION_CLASSES, true), this, name);
                    TaskHolder temp = TASK_HOLDER_MAP.put(name, holder);

                    Log.d(ConstanceFieldHolder.AUTOTASK_TAG, String.format("Starting task: %s", file.getName()));
                    holder.onCreate();
                    Log.d(ConstanceFieldHolder.AUTOTASK_TAG, String.format("Started task: %s", file.getName()));
                    if (temp != null) {
                        temp.onDestroy();
                    }
                } catch (IOException e) {
                    Log.d(ConstanceFieldHolder.AUTOTASK_TAG, String.format("Failed to initialized task: %s", file.getName()));
                    Log.e(ConstanceFieldHolder.AUTOTASK_TAG, ExceptionUtils.stackTraceToString(e));
                }
                TASK_PROPERTIES_NAME_SET.add(file.getName());
            }
        } else if (!autoTaskFolder.mkdirs()) {
            Log.e(ConstanceFieldHolder.AUTOTASK_TAG, "Can't create a AutoTask folder");
        }
        return Service.START_STICKY;
    }

    public IBinder onBind(Intent intent) {
        Log.d(ConstanceFieldHolder.AUTOTASK_TAG, "Boo1");
        return null;
    }

    @Override
    public boolean stopService(Intent name) {
        TASK_HOLDER_MAP.destroyAll();
        return super.stopService(name);
    }
}
