package org.karolgurecki.autotask.ui.tasks;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.karolgurecki.autotask.R;

/**
 * Created by: Karol Górecki
 * <a href="mailto:kagurecki@gmail.com?Subject=Autotask Question" target="_top">kagurecki (at) gmail.com</a>
 * Version: 0.01
 * Since: 0.01
 */
public abstract class AbstractInputTextDialog extends Dialog {

    private TextView textView;
    private Button confirmButton;
    private Button cancelButton;

    public AbstractInputTextDialog(Context context, String title) {
        super(context);
        setContentView(R.layout.text_field_dialog);
        setCancelable(true);
        setTitle(title);
        setRadioListener();
        cancelButton = (Button) findViewById(R.id.textDialogCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        show();
    }

    private void setRadioListener() {
        textView = (TextView) findViewById(R.id.textDialogEditText);
        confirmButton = (Button) findViewById(R.id.textDialogConfirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActiveValue(textView.getText().toString());
                dismiss();
            }
        });
    }

    protected abstract void setActiveValue(String text);
}
