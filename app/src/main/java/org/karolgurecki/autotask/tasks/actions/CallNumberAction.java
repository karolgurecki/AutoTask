package org.karolgurecki.autotask.tasks.actions;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import org.apache.commons.lang3.StringUtils;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.ui.tasks.AbstractInputTextDialog;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nappa on 2014-08-03.
 */
public class CallNumberAction extends AbstractActionTaskObject {

    private static final String ACTION = "org.karolgurecki.autotask.tasks.actions.CallNumberAction";
    private static final IntentFilter INTENT_FILTER = new IntentFilter(ACTION);
    private static final Intent INTENT = new Intent(ACTION);
    private static final Map<String, String> STRING_STRING_MAP = new HashMap<>();
    private static String activeValue;

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskName = intent.getStringExtra(ConstanceFieldHolder.TASK_HOLDER_NAME_EXTRA);
        String number = STRING_STRING_MAP.get(taskName);
        Uri telUri = Uri.parse(String.format("tel:%s", number));
        Intent dialIntent = new Intent(Intent.ACTION_CALL, telUri);
        dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(dialIntent);
    }

    @Override
    protected Map<Boolean, Set<Intent>> receive(Context context, Intent intent) {
        return null;
    }

    @Override
    public String getDisplayName(Context context) {
        return context.getString(R.string.callNumberActionDisplayName);
    }

    @Override
    public String getDisplayConfiguration(Context context) {
        String willCall = context.getString(R.string.dialNumber);

        return String.format("%s %s", willCall, activeValue);
    }

    @Override
    public void openDialog(Context context) {
        new CallNumberDialog(context);
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
        return INTENT;
    }

    @Override
    public void assignResponseIntentToActivationStatus() {
        String taskName = responseIntent.getStringExtra(ConstanceFieldHolder.TASK_HOLDER_NAME_EXTRA);
        STRING_STRING_MAP.put(taskName, activeValue);
    }

    private final class CallNumberDialog extends AbstractInputTextDialog {

        private CallNumberDialog(Context context) {
            super(context, context.getString(R.string.callNumberActionDialogTitle),
                    ConstanceFieldHolder.ACTION_CLASSES);
        }

        @Override
        protected boolean setActiveValue(String text) {
            if (text == null || !text.matches("[+]?\\d+")) {
                return false;
            }
            activeValue = text;
            return true;
        }
    }
}
