package org.karolgurecki.autotask.ui.tasks;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public abstract class AbstractOnOffDialog extends Dialog {

    private RadioGroup radioOnOffGroup;
    private Button confirmButton;
    private Button cancelButton;

    public AbstractOnOffDialog(Context context, String title, String type) {
        super(context);
        setContentView(R.layout.on_off_chooser);
        setCancelable(true);
        setTitle(title);
        setRadioListener(context,type);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        show();
    }

    private void setRadioListener(final Context context, final String type) {
        radioOnOffGroup = (RadioGroup) findViewById(R.id.radioOnOff);
        confirmButton = (Button) findViewById(R.id.conformButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioButton = (RadioButton) findViewById(radioOnOffGroup.getCheckedRadioButtonId());
                if(radioButton!=null) {
                    setActiveValue(radioButton.getText().toString().equalsIgnoreCase(getContext().getString(R.string.on)));
                    Intent intent=new Intent(
                            ConstanceFieldHolder.INTERNAL_CONFIRM_ADDING_TASK_OBJECT_ACTION);
                    intent.putExtra(ConstanceFieldHolder.EXTRA_TYPE,type);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    dismiss();
                }
            }
        });
    }

    protected abstract void setActiveValue(boolean isOn);
}
