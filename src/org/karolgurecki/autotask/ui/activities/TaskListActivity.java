package org.karolgurecki.autotask.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import org.karolgurecki.autotask.R;

/**
 * Created by goreckik on 09.10.13.
 */
public class TaskListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.task_list);
        setProgressBarIndeterminateVisibility(true);
    }
}
