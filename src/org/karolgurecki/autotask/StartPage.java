package org.karolgurecki.autotask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import org.karolgurecki.autotask.activities.NewTaskActivity;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

public class StartPage extends Activity {

    Button newTaskButton, taskListButton;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

       0 ApplicationContext context = new ClassPathXmlApplicationContext(getString(R.xml.auto_task_context));



        newTaskButton = (Button) findViewById(R.id.newTaskbutton);

        taskListButton = (Button) findViewById(R.id.taskListButton);

        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPage.this, NewTaskActivity.class);
                startActivity(intent);
            }
        });

    }
}
