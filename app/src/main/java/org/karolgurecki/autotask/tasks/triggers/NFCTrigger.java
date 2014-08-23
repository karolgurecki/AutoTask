package org.karolgurecki.autotask.tasks.triggers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;

import org.karolgurecki.autotask.R;
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
public class NFCTrigger extends AbstractBroadcastReceiverTrigger {

    private static final IntentFilter INTENT_FILTER = new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);

    private static final Map<Integer, Set<Intent>> ACTIVATED_VALUES_MAP = new HashMap<>();

    private static int activeValue = NfcAdapter.STATE_OFF;

    @Override
    protected Map<Boolean, Set<Intent>> receive(Context context, Intent intent) {
        Map<Boolean, Set<Intent>> response = new HashMap<>();
        if (NfcAdapter.ACTION_ADAPTER_STATE_CHANGED.equalsIgnoreCase(intent.getAction())) {
            int intExtra = intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE, -10);

            if (NfcAdapter.STATE_ON == intExtra) {
                response.put(true, getIntentsFromActivateddMap(NfcAdapter.STATE_ON));
                response.put(false, getIntentsFromActivateddMap(NfcAdapter.STATE_OFF));
            } else if (NfcAdapter.STATE_OFF == intExtra) {
                response.put(true, getIntentsFromActivateddMap(NfcAdapter.STATE_OFF));
                response.put(false, getIntentsFromActivateddMap(NfcAdapter.STATE_ON));
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
        return context.getString(R.string.nfcTriggerdisplayName);
    }

    @Override
    public String getDisplayConfiguration(Context context) {
        String config = activeValue == NfcAdapter.STATE_ON ? context.getString(R.string.on) :
                context.getString(R.string.off);
        return String.format("%s: %s", context.getString(R.string.ifString), config);
    }

    @Override
    public void openDialog(Context context) {
        new NfcOnOffDialog(context,context.getString(R.string.nfcTriggerDialogTitle));
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

    private class NfcOnOffDialog extends AbstractOnOffDialog {

        private NfcOnOffDialog(Context context, String title) {
            super(context, title, ConstanceFieldHolder.TRIGGER_TYPE);
        }

        @Override
        protected void setActiveValue(boolean isOn) {
            if (isOn) {
                activeValue = NfcAdapter.STATE_ON;
            } else {
                activeValue = NfcAdapter.STATE_OFF;
            }
        }
    }
}
