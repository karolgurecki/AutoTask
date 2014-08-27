package org.karolgurecki.autotask.tasks.actions;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.ui.tasks.AbstractInputTextDialog;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public class ToastAction extends AbstractActionTaskObject {
    private static final String ACTION = "org.karolgurecki.autotask.tasks.actions.ToastAction";
    private static final IntentFilter INTENT_FILTER = new IntentFilter(ACTION);
    private static final Intent INTENT = new Intent(ACTION);
    private static final Map<String, String> STRING_STRING_MAP = new HashMap<>();
    private static String toastString;

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskName = intent.getStringExtra(ConstanceFieldHolder.TASK_HOLDER_NAME_EXTRA);
        Toast.makeText(context, STRING_STRING_MAP.get(taskName), Toast.LENGTH_LONG).show();
    }

    @Override
    protected Map receive(Context context, Intent intent) {
        return null;
    }

    @Override
    public String getDisplayName(Context context) {
        return context.getString(R.string.toastActionName);
    }

    @Override
    public String getDisplayConfiguration(Context context) {
        return String.format("%s: %s", context.getString(R.string.display), toastString);
    }

    @Override
    public void openDialog(Context context) {
        new ToastActionTextEditDialog(context, context.getString(R.string.toastDialogTitle));
    }

    @Override
    public IntentFilter getIntentFilter() {
        return INTENT_FILTER;
    }

    @Override
    public String getConfig() {
        return toastString;
    }

    @Override
    public void setConfig(String config) {
        toastString = config;
    }

    @Override
    public Intent getIntent() {
        return new Intent(INTENT);
    }

    @Override
    public void assignResponseIntentToActivationStatus() {
        String taskName = responseIntent.getStringExtra(ConstanceFieldHolder.TASK_HOLDER_NAME_EXTRA);
        STRING_STRING_MAP.put(taskName, toastString);
    }

    final class ToastActionTextEditDialog extends AbstractInputTextDialog {

        public ToastActionTextEditDialog(Context context, String title) {
            super(context, title, ConstanceFieldHolder.ACTION_TYPE);
        }

        @Override
        protected boolean setActiveValue(String text) {
            if (text == null) {
                return false;
            }
            toastString = text;
            return true;
        }
    }
}
