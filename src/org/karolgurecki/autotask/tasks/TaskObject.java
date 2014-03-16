package org.karolgurecki.autotask.tasks;

import android.content.IntentFilter;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public interface TaskObject {

    String getDisplayName();

    String getDisplayConfiguration();

    void openDialog();

    IntentFilter getIntentFilter();


}
