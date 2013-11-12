package org.karolgurecki.autotask.tasks;


import android.app.Activity;

public interface Task {

    void configure(String config);

    String getConfiguration();

    void run();

    boolean isReady();

    void setReady(boolean ready);

    Activity getActivity();

    void setActivity(Activity activity);

    String getName();

    void setName(String name);
}
