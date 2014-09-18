package org.karolgurecki.autotask.tasks.actions;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Vibrator;

import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.ui.activities.NewTaskActivity;
import org.karolgurecki.autotask.ui.tasks.AbstractInputTextDialog;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;

import java.util.HashMap;
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
    privBtatic final IntentFilter INTENT_FILTER = new IntentFilter(ACTION);
    private static final Intent INTENT = new Intent(ACTION);
    private static final Map<String,Long> VIBRATE_DURATION_MAP= new HashMap<>();

    private static Long activateValue;

    @Override
    protected Map<Boolean, Set<Intent>> receive(Context context, Intent intent) {
        return null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        String taskName = intent.getStringExtra(ConstanceFieldHolder.TASK_HOLDER_NAME_EXTRA);
        vibrator.vibrate(VIBRATE_DURATION_MAP.get(taskName));
    }

    @Override
    public String getDisplayName(Context context) {
        return context.getString(R.string.vibrateActionName);
    }

    @Override
    public String getDisplayConfiguration(Context context) {
        String willVibrate= context.getString(R.string.willVibrate);
        String ms=context.getString(R.string.ms);
        return String.format("%s %s %s", willVibrate,activateValue,ms);
    }

    @Override
    public void openDialog(Context context) {
        new VibrateActionDialog(context);
    }

    @Override
    public IntentFilter getIntentFilter() {
        return INTENT_FILTER;
    }

    @Override
    public String getConfig() {
        return activateValue.toString();
    }

    @Override
    public void setConfig(String config) {
        activateValue=Long.parseLong(config);
    }

    @Override
    public Intent getIntent() {
        return INTENT;
    }

    @Override
    public void assignResponseIntentToActivationStatus() {
        String taskName = responseIntent.getStringExtra(ConstanceFieldHolder.TASK_HOLDER_NAME_EXTRA);
        VIBRATE_DURATION_MAP.put(taskName, activateValue);
    }

    private final class VibrateActionDialog extends AbstractInputTextDialog {

        public VibrateActionDialog(Context context) {
            super(context, context.getString(R.string.vibrateActionConfigurationDialogTitle),
                    ConstanceFieldHolder.ACTION_TYPE);
        }

        @Override
        protected boolean setActiveValue(String text) {
            try {
                activateValue = Long.parseLong(text);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
    }
}
