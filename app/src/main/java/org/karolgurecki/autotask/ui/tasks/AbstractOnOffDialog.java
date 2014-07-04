package org.karolgurecki.autotask.ui.tasks;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import org.karolgurecki.autotask.R;

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

    public AbstractOnOffDialog(Context context, String title) {
        super(context);
        setContentView(R.layout.on_off_chooser);
        setCancelable(true);
        setTitle(title);
        setRadioListener();
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        show();
    }

    private void setRadioListener() {
        radioOnOffGroup = (RadioGroup) findViewById(R.id.radioOnOff);
        confirmButton = (Button) findViewById(R.id.conformButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioButton = (RadioButton) findViewById(radioOnOffGroup.getCheckedRadioButtonId());
                setActiveValue(radioButton.getText().toString().equalsIgnoreCase(getContext().getString(R.string.on)));
                dismiss();
            }
        });
    }

    protected abstract void setActiveValue(boolean isOn);
}
