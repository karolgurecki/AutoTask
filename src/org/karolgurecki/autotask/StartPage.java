package org.karolgurecki.autotask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import org.karolgurecki.autotask.activities.NewTaskActivity;
import org.karolgurecki.autotask.activities.TaskListActivity;
import org.karolgurecki.autotask.factory.TaskFactory;
import org.karolgurecki.autotask.tasks.AbstractTask;

import java.io.File;
import java.util.List;

public class StartPage extends Activity {

    Button newTaskButton, taskListButton;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new File(Environment.getExternalStorageDirectory()+"/AutoTask").mkdirs();

        List<AbstractTask> tasks= TaskFactory.defaultTasksWithoutConfigCreator(this);

        newTaskButton = (Button) findViewById(R.id.newTaskbutton);

        taskListButton = (Button) findViewById(R.id.taskListButton);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPage.this, NewTaskActivity.class);
                startActivity(intent);
            }
        });

        taskListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPage.this, TaskListActivity.class);
                startActivity(intent);
            }
        });

    }
}
