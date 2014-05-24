package org.karolgurecki.autotask.tasks.actions;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;
import org.karolgurecki.autotask.tasks.AbstractBroadcastReceiverTaskObject;

import java.util.Map;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public class ToastAction extends AbstractBroadcastReceiverTaskObject {
    private static final String ACTION = "org.karolgurecki.autotask.tasks.actions.ToastAction";
    private static final IntentFilter INTENT_FILTER = new IntentFilter(ACTION);
    private static final Intent INTENT = new Intent(ACTION);

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Test", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Map receive(Context context, Intent intent) {
        return null;
    }

    @Override
    public String getDisplayName(Context context) {
        return "Toast Action";
    }

    @Override
    public String getDisplayConfiguration(Context context) {
        return "";
    }

    @Override
    public void openDialog(Context context) {

    }

    @Override
    public IntentFilter getIntentFilter() {
        return INTENT_FILTER;
    }

    @Override
    public String getConfig() {
        return "";
    }

    @Override
    public void setConfig(String config) {

    }

    @Override
    public Intent getIntent() {
        return INTENT;
    }

    @Override
    public void assignResponseIntentToActivationStatus() {

    }
}
