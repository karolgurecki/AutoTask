package org.karolgurecki.autotask.tasks.triggers;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import org.karolgurecki.autotask.tasks.AbstractBroadcastReceiverTaskObject;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public class BluetoothTrigger extends AbstractBroadcastReceiverTaskObject {

    private static final Intent INTENT = new Intent(BluetoothAdapter.ACTION_STATE_CHANGED);

    int activeValue = BluetoothAdapter.STATE_ON;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (INTENT.getAction().equalsIgnoreCase(intent.getAction())) {
            Boolean activated = false;
            if (activeValue == intent.getIntExtra(BluetoothAdapter.ACTION_STATE_CHANGED, -10)) {
                activated = true;
            }

            responseIntent.putExtra("org.karolgurecki.autotask.tasks.triggers.TRIGGER_ACTIVATED", activated);

            LocalBroadcastManager.getInstance(context).sendBroadcast(responseIntent);
        }
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getDisplayConfiguration() {
        return null;
    }

    @Override
    public void openDialog() {

    }

    @Override
    public IntentFilter getIntentFilter() {
        return null;
    }

    @Override
    public String getConfig() {
        return Integer.toString(activeValue);
    }

    @Override
    public void setConfig(String config) {
        activeValue = Integer.parseInt(config);
    }

    @Override
    public Intent getIntent() {
        return INTENT;
    }
}
