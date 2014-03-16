package org.karolgurecki.autotask.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import org.karolgurecki.autotask.R;

import java.io.File;

public class Main extends Activity {

    Button newTaskButton, taskListButton;
    // public static List<Task> TASKS;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new File(Environment.getExternalStorageDirectory() + "/AutoTask").mkdirs();

//        TASKS = TaskFactory.EveryTaskCreator(this);

        newTaskButton = (Button) findViewById(R.id.newTaskbutton);

        taskListButton = (Button) findViewById(R.id.taskListButton);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, NewTaskActivity.class);

                startActivity(intent);
            }
        });

        taskListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, TaskListActivity.class);
                startActivity(intent);
            }
        });

    }
}
