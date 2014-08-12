package org.karolgurecki.autotask.tasks.triggers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.tasks.AbstractBroadcastReceiverTaskObject;
import org.karolgurecki.autotask.ui.tasks.AbstractOnOffDialog;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;

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
public class WifiTrigger extends AbstractBroadcastReceiverTrigger {

    private static final IntentFilter INTENT_FILTER = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);

    private static final Map<Integer, Set<Intent>> ACTIVATED_VALUES_MAP = new HashMap<>();

    private static int activeValue = WifiManager.WIFI_STATE_ENABLED;

    @Override
    protected Map<Boolean, Set<Intent>> receive(Context context, Intent intent) {
        Map<Boolean, Set<Intent>> response = new HashMap<>();
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equalsIgnoreCase(intent.getAction())) {
            int intExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -10);

            if (WifiManager.WIFI_STATE_ENABLED == intExtra) {
                response.put(true, getIntentsFromActivateddMap(WifiManager.WIFI_STATE_ENABLED));
                response.put(false, getIntentsFromActivateddMap(WifiManager.WIFI_STATE_DISABLED));
            } else if (WifiManager.WIFI_STATE_DISABLED == intExtra) {
                response.put(true, getIntentsFromActivateddMap(WifiManager.WIFI_STATE_DISABLED));
                response.put(false, getIntentsFromActivateddMap(WifiManager.WIFI_STATE_ENABLED));
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
        return context.getString(R.string.wifiTriggerName);
    }

    @Override
    public String getDisplayConfiguration(Context context) {
        String config = activeValue == WifiManager.WIFI_STATE_ENABLED ? context.getString(R.string.on) :
                context.getString(R.string.off);
        return String.format("%s: %s", context.getString(R.string.ifString), config);
    }

    @Override
    public void openDialog(Context context) {
        new WifiOnOffDialog(context, context.getString(R.string.wifiTriggerConfigTitle));
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
        return null;
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

    private class WifiOnOffDialog extends AbstractOnOffDialog {

        private WifiOnOffDialog(Context context, String title) {
            super(context, title, ConstanceFieldHolder.TRIGGER_TYPE);
        }

        @Override
        protected void setActiveValue(boolean isOn) {
            if (isOn) {
                activeValue = WifiManager.WIFI_STATE_ENABLED;
            } else {
                activeValue = WifiManager.WIFI_STATE_DISABLED;
            }
        }
    }
}
