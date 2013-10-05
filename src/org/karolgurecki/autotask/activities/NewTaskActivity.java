package org.karolgurecki.autotask.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.karolgurecki.autotask.R;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Nappa
 * Date: 29.09.13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */

public class NewTaskActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.new_task);

    }
}