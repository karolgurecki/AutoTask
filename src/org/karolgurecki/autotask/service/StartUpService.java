package org.karolgurecki.autotask.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class StartUpService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("AutoTask", "I'm alive");
        return Service.START_STICKY;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
