package org.karolgurecki.autotask.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.service.StartUpService;

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
        startService(new Intent(this, StartUpService.class));

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
