package org.karolgurecki.autotask.tasks.actions;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
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
    private static String toastString;

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskName = intent.getStringExtra(ConstanceFieldHolder.TASK_HOLDER_NAME_EXTRA);
        String number = STRING_STRING_MAP.get(taskName);
        Uri telUri = Uri.parse(String.format("tel:%s", number));
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, telUri);
        context.startActivity(dialIntent);
    }

    @Override
    protected Map<Boolean, Set<Intent>> receive(Context context, Intent intent) {
        return null;
    }

    @Override
    public String getDisplayName(Context context) {
        return "Call Number Action";
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
