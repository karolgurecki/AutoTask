package org.karolgurecki.autotask.tasks.actions;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Vibrator;

import org.karolgurecki.autotask.R;

import java.util.Map;
import java.util.Set;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public class VibrateAction extends AbstractActionTaskObject {

    private static final String ACTION = "org.karolgurecki.autotask.tasks.actions.VibrateAction";
    private static final IntentFilter INTENT_FILTER = new IntentFilter(ACTION);
    private static final Intent INTENT = new Intent(ACTION);

    @Override
    protected Map<Boolean, Set<Intent>> receive(Context context, Intent intent) {
        return null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator=(Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500l);
    }

    @Override
    public String getDisplayName(Context context) {
        return context.getString(R.string.vibrateActionName);
    }

    @Override
    public String getDisplayConfiguration(Context context) {
        return null;
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
        return null;
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
