package org.karolgurecki.autotask.tasks.actions;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import org.karolgurecki.autotask.R;
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
public class OpenSiteAction extends AbstractActionTaskObject {
    private static final String ACTION = "org.karolgurecki.autotask.tasks.actions.CallNumberAction";
    private static final IntentFilter INTENT_FILTER = new IntentFilter(ACTION);
    private static final Intent INTENT = new Intent(ACTION);
    private static final Map<String, String> STRING_STRING_MAP = new HashMap<>();
    private static String activeValue;

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskName = intent.getStringExtra(ConstanceFieldHolder.TASK_HOLDER_NAME_EXTRA);
        String number = STRING_STRING_MAP.get(taskName);
        Uri webUri = Uri.parse(String.format("http://%s/", number));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
        webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(webIntent);
    }

    @Override
    protected Map<Boolean, Set<Intent>> receive(Context context, Intent intent) {
        return null;
    }

    @Override
    public String getDisplayName(Context context) {
        return context.getString(R.string.openSiteActionDisplayName);
    }

    @Override
    public String getDisplayConfiguration(Context context) {
        String willBeOpened = context.getString(R.string.openSite);

        return String.format("%s %s", activeValue, willBeOpened);
    }

    @Override
    public void openDialog(Context context) {
        new OpenSiteDialog(context);
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

    private final class OpenSiteDialog extends AbstractInputTextDialog {

        private OpenSiteDialog(Context context) {
            super(context, context.getString(R.string.openSiteActionDialogTitle),
                    ConstanceFieldHolder.ACTION_CLASSES);
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
