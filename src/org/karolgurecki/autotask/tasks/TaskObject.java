package org.karolgurecki.autotask.tasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public interface TaskObject {

    /**
     * Gets displayed name of this task object
     *
     * @return the name to display
     */
    String getDisplayName(Context context);

    /**
     * Gets formatted configuration of this task object.
     *
     * @return the configuration to display
     */
    String getDisplayConfiguration(Context context);

    /**
     * Display dialog or activity with configuration UI.
     */
    void openDialog(Context context);

    /**
     * Get intent filter on which task will receive/
     *
     * @return the intent filter
     */
    IntentFilter getIntentFilter();

    /**
     * Sets intent filter to which massage will be send after broadcast receiver ends it's work.
     *
     * @param intent the intent filter
     */
    void setResponseIntent(Intent intent);

    /**
     * Gets configuration in string to save in the property file.
     *
     * @return the configuration in string
     */
    String getConfig();

    /**
     * Sets configuration received from property file in string form
     *
     * @param config the configuration in string
     */
    void setConfig(String config);

    /**
     * @return the intent on which task object will response
     */
    Intent getIntent();

    /**
     * @return this task object BroadcastReceiver
     */
    BroadcastReceiver getBroadcastReceiver();

    /**
     * @return the task object runtime type.
     */
    TaskType getTaskType();

    /**
     * Stats THREAD type task object
     */
    void start();

    /**
     * Stops THREAD type task object
     */
    void stop();
}
