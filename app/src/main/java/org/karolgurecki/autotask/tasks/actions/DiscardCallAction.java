package org.karolgurecki.autotask.tasks.actions;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;
import org.karolgurecki.autotask.utils.ExceptionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import static android.app.AlertDialog.*;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public class DiscardCallAction extends AbstractActionTaskObject {

    private static final String ACTION = "org.karolgurecki.autotask.tasks.actions.DiscardCallAction";
    private static final IntentFilter INTENT_FILTER = new IntentFilter(ACTION);
    private static final Intent INTENT = new Intent(ACTION);
    public static final String GET_I_TELEPHONY_METHOD_NAME = "getITelephony";
    public static final String END_CALL_METHOD_NAME = "endCall";

    @Override
    protected Map<Boolean, Set<Intent>> receive(Context context, Intent intent) {
        return null;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            TelephonyManager telephonyManager =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            Class classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method getITelephonyMethod = classTelephony.getDeclaredMethod(GET_I_TELEPHONY_METHOD_NAME);

            getITelephonyMethod.setAccessible(true);

            Object telephonyInterface = getITelephonyMethod.invoke(telephonyManager);

            Class telephonyInterfaceClass =
                    Class.forName(telephonyInterface.getClass().getName());
            Method endCallMethod = telephonyInterfaceClass.getDeclaredMethod(END_CALL_METHOD_NAME);

            endCallMethod.invoke(telephonyInterface);

        } catch (Exception e) {
            Log.d(ConstanceFieldHolder.AUTOTASK_TAG, ExceptionUtils.stackTraceToString(e));
        }
    }

    @Override
    public String getDisplayName(Context context) {
        return context.getString(R.string.discardCallAction);
    }

    @Override
    public String getDisplayConfiguration(Context context) {
        return "";
    }

    @Override
    public void openDialog(final Context context) {

        new Builder(context).
                setTitle(context.getString(R.string.doYouWantProceedCallDiscardDialogTitle)).
                setMessage(context.getString(R.string.doYouWantProceedCallDiscardDialogText)).
                setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                ConstanceFieldHolder.INTERNAL_CONFIRM_ADDING_TASK_OBJECT_ACTION);
                        intent.putExtra(ConstanceFieldHolder.EXTRA_TYPE,
                                ConstanceFieldHolder.ACTION_TYPE);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        dialog.dismiss();
                    }
                }).
                setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();

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
        //do nothing not needed in this action
    }

    @Override
    public Intent getIntent() {
        return INTENT;
    }

    @Override
    public void assignResponseIntentToActivationStatus() {

    }
}
