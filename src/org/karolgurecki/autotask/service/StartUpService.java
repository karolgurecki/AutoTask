package org.karolgurecki.autotask.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import org.karolgurecki.autotask.tasks.TaskHolder;
import org.karolgurecki.autotask.utils.ConstanceFiledHolder;
import org.karolgurecki.autotask.utils.ExceptionUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.karolgurecki.autotask.factory.TaskFactory.createTaskObjects;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class StartUpService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(ConstanceFiledHolder.AUTOTASK_TAG, "Boo2");
        File autoTaskFolder = getFilesDir();
        Log.d(ConstanceFiledHolder.AUTOTASK_TAG, "Boo3");
        if (autoTaskFolder.exists()) {
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File pathname) {

                    if (pathname.getName().endsWith(ConstanceFiledHolder.PROPERTIES_FILE_EXTENTION)) {
                        return true;
                    }
                    return false;
                }
            };
            File[] fileArray = autoTaskFolder.listFiles(fileFilter);

            for (File file : fileArray) {
                try {
                    Log.d(ConstanceFiledHolder.AUTOTASK_TAG, String.format("Initializing task: %s", file.getName()));
                    Properties properties = new Properties();
                    properties.load(new FileReader(file));
                    TaskHolder holder = new TaskHolder(
                            createTaskObjects(properties, ConstanceFiledHolder.TRIGGER_CLASSES, true),
                            createTaskObjects(properties, ConstanceFiledHolder.ACTION_CLASSES, true),
                            System.currentTimeMillis(), this, file.getName());
                    this.registerReceiver(holder, holder.getIntentFilter());
                    if (ConstanceFiledHolder.taskHolderSet.add(holder)) {
                        Log.d(ConstanceFiledHolder.AUTOTASK_TAG, String.format("Starting task: %s", file.getName()));
                        holder.onCreate();
                        Log.d(ConstanceFiledHolder.AUTOTASK_TAG, String.format("Started task: %s", file.getName()));
                    } else {
                        Log.d(ConstanceFiledHolder.AUTOTASK_TAG, String.format("Task %s already started", file.getName()));
                    }
                } catch (IOException e) {
                    Log.d(ConstanceFiledHolder.AUTOTASK_TAG, String.format("Failed to initialized task: %s", file.getName()));
                    Log.e(ConstanceFiledHolder.AUTOTASK_TAG, ExceptionUtils.stackTraceToString(e));
                }

            }
        } else if (!autoTaskFolder.mkdirs()) {
            Log.e(ConstanceFiledHolder.AUTOTASK_TAG, "Can't create a AutoTask folder");
        }
        return Service.START_STICKY;
    }

    public IBinder onBind(Intent intent) {
        Log.d(ConstanceFiledHolder.AUTOTASK_TAG, "Boo1");
        return null;
    }

    @Override
    public boolean stopService(Intent name) {
        for (TaskHolder holder : ConstanceFiledHolder.taskHolderSet) {
            holder.onDestoy();
            unregisterReceiver(holder);
        }
        return super.stopService(name);
    }
}
