package org.karolgurecki.autotask.tasks.triggers;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.tasks.AbstractBroadcastReceiverTaskObject;
import org.karolgurecki.autotask.ui.tasks.AbstractOnOffDialog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public class BluetoothTrigger extends AbstractBroadcastReceiverTaskObject {

    private static final Intent INTENT = new Intent(BluetoothAdapter.ACTION_STATE_CHANGED);
    private static final IntentFilter INTENT_FILTER = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

    private static final Map<Integer, Set<Intent>> ACTIVATED_VALUES_MAP = new HashMap<>();

    private static int activeValue = BluetoothAdapter.STATE_ON;

    @Override
    public Map<Boolean, Set<Intent>> receive(Context context, Intent intent) {
        Map<Boolean, Set<Intent>> response = new HashMap<>();
        if (INTENT.getAction().equalsIgnoreCase(intent.getAction())) {
            int intExtra = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -10);

            if (BluetoothAdapter.STATE_ON == intExtra) {
                response.put(true, getIntentsFromActivateddMap(BluetoothAdapter.STATE_ON));
                response.put(false, getIntentsFromActivateddMap(BluetoothAdapter.STATE_OFF));
            } else if (BluetoothAdapter.STATE_OFF == intExtra) {
                response.put(true, getIntentsFromActivateddMap(BluetoothAdapter.STATE_OFF));
                response.put(false, getIntentsFromActivateddMap(BluetoothAdapter.STATE_ON));
            } else {
                Set<Intent> tempSet = new HashSet<>();
                for (Set<Intent> set : ACTIVATED_VALUES_MAP.values()) {
                    tempSet.addAll(set);
                }
                response.put(false, tempSet);
            }
        }
        return response;
    }

    private Set<Intent> getIntentsFromActivateddMap(int key) {
        Set<Intent> intentSet = ACTIVATED_VALUES_MAP.get(key);
        return intentSet != null ? intentSet : new HashSet<Intent>();
    }

    @Override
    public String getDisplayName(Context context) {
        return context.getString(R.string.bluetoothTrigger);
    }

    @Override
    public String getDisplayConfiguration(Context context) {
        String config = activeValue == BluetoothAdapter.STATE_ON ? context.getString(R.string.on) :
                context.getString(R.string.off);
        return String.format("%s: %s", context.getString(R.string.ifString), config);
    }

    @Override
    public void openDialog(Context context) {
        new BluetoothOnOffDialog(context, context.getString(R.string.bluetoothTriggerConfigTitle));
    }

    @Override
    public IntentFilter getIntentFilter() {
        return INTENT_FILTER;
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

    @Override
    public void assignResponseIntentToActivationStatus() {
        Set<Intent> responseIntents = ACTIVATED_VALUES_MAP.get(activeValue);

        if (responseIntents == null) {
            responseIntents = new HashSet<>();
            ACTIVATED_VALUES_MAP.put(activeValue, responseIntents);
        }

        responseIntents.add(responseIntent);
    }

    private class BluetoothOnOffDialog extends AbstractOnOffDialog {

        private BluetoothOnOffDialog(Context context, String title) {
            super(context, title);
        }

        @Override
        protected void setActiveValue(boolean isOn) {
            if (isOn) {
                activeValue = BluetoothAdapter.STATE_ON;
            } else {
                activeValue = BluetoothAdapter.STATE_OFF;
            }
        }
    }
}
