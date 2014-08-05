package org.karolgurecki.autotask.tasks.actions;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.Map;
import java.util.Set;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public class DiscardCallAction extends AbstractActionTaskObject {
    @Override
    protected Map<Boolean, Set<Intent>> receive(Context context, Intent intent) {
        return null;
    }

    @Override
    public String getDisplayName(Context context) {
        return "Discard Call Action";
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
        return null;
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
        return null;
    }

    @Override
    public void assignResponseIntentToActivationStatus() {

    }
}
