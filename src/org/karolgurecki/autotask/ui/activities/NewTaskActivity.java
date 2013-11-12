package org.karolgurecki.autotask.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.ui.adapters.ItemAdapter;
import org.karolgurecki.autotask.ui.rows.ItemRow;
import org.karolgurecki.autotask.ui.views.NotScrollableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nappa
 * Date: 29.09.13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */

public class NewTaskActivity extends Activity {

    private Button triggerButton;

    private Button actionButton;

    private NotScrollableList triggerList;

    private NotScrollableList actionList;

    private ListView addNewTriggerList;

    private ListView addNewActionList;

    private List<ItemRow> itemRowList = new ArrayList<>();

    private ItemAdapter triggerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.edit_task);

        triggerButton = (Button) findViewById(R.id.triggerButton);
        actionButton = (Button) findViewById(R.id.actionButton);
        triggerList = (NotScrollableList) findViewById(R.id.triggerList);
        addNewTriggerList = (ListView) findViewById(R.id.addNewTriggerList);
        actionList = (NotScrollableList) findViewById(R.id.actionList);
        addNewActionList = (ListView) findViewById(R.id.addNewActionList);


        ItemRow rd = new ItemRow("item1", "description1");
        itemRowList.add(rd);
        rd = new ItemRow("item2", "description2");
        itemRowList.add(rd);
        rd = new ItemRow("item2", "description3");
        itemRowList.add(rd);

        triggerAdapter = new ItemAdapter(this, R.layout.item_row, itemRowList);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemRow rd = new ItemRow("item1", "description1");
                triggerAdapter.add(rd);
            }
        });
        LayoutInflater mInflater;

        mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        triggerAdapter.setmInflater(mInflater);
        triggerList.setAdapter(triggerAdapter);


    }
}