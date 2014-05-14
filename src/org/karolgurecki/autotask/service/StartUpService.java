package org.karolgurecki.autotask.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import org.karolgurecki.autotask.R;
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
        Log.d("AutoTask", "Boo2");
        File autoTaskFolder = getFilesDir();
        Log.d("AutoTask", "Boo3");
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
                    Properties properties = new Properties();
                    properties.load(new FileReader(file));
                    TaskHolder holder = new TaskHolder(
                            createTaskObjects(properties, ConstanceFiledHolder.TRIGGER_CLASSES, true),
                            createTaskObjects(properties, ConstanceFiledHolder.ACTION_CLASSES, true),
                            System.currentTimeMillis(), this, file.getName());
                    holder.onCreate();
                    ConstanceFiledHolder.taskHolderSet.add(holder);
                } catch (IOException e) {
                    Log.e(getString(R.string.autotask), ExceptionUtils.stackTraceToString(e));
                }

            }
        } else if (!autoTaskFolder.mkdirs()) {
            Log.e(getString(R.string.autotask), "Can't create a AutoTask folder");
        }
        return Service.START_STICKY;
    }

    public IBinder onBind(Intent intent) {
        Log.d("AutoTask", "Boo1");
        return null;
    }
}
