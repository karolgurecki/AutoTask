package org.karolgurecki.autotask.tasks.triggers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.ui.tasks.AbstractInputTextDialog;
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
public class OutgoingCallTrigger extends AbstractBroadcastReceiverTrigger {

    private static final IntentFilter INTENT_FILTER = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);

    private static final Map<String, Set<Intent>> ACTIVATED_VALUES_MAP = new HashMap<>();

    private static String activeValue = "";

    @Override
    protected Map<Boolean, Set<Intent>> receive(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        Map<Boolean, Set<Intent>> response = new HashMap<>();
        if (phoneNumber != null) {
            for (Map.Entry<String, Set<Intent>> activatedValue : ACTIVATED_VALUES_MAP.entrySet()) {
                if (phoneNumber.contains(activatedValue.getKey())) {
                    putValueIntoMap(response,true,activatedValue.getValue());
                } else {
                    putValueIntoMap(response,false,activatedValue.getValue());
                }
            }
        }
        return response;
    }

    private void putValueIntoMap(Map<Boolean, Set<Intent>> response, boolean key,
                                        Set<Intent> intents) {
        Set<Intent> intentSet = response.get(key);
        if (intentSet != null){
            intentSet.addAll(intents);
        } else {
            response.put(key,new HashSet<>(intents));
        }
    }

    @Override
    public String getDisplayName(Context context) {
        return context.getString(R.string.outgoindCallTriggerName);
    }

    @Override
    public String getDisplayConfiguration(Context context) {
        return String.format("%s: %s", context.getString(R.string.ifCallingTo), activeValue);
    }

    @Override
    public void openDialog(Context context) {
        new OutgoingCallDialog(context,context.getString(R.string.outgoingCalldialogTitle));
    }

    @Override
    public IntentFilter getIntentFilter() {
        return INTENT_FILTER;
    }

    @Override
    public String getConfig() {
        return activeValue;
    }

    @Override
    public void setConfig(String config) {
        activeValue = config;
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

    private class OutgoingCallDialog  extends AbstractInputTextDialog {

        public OutgoingCallDialog(Context context, String title) {
            super(context, title, ConstanceFieldHolder.TRIGGER_TYPE);
        }

        @Override
        protected boolean setActiveValue(String text) {
            if (text == null) {
                return false;
            }
            activeValue = text;
            return true;
        }
    }
}
