package org.karolgurecki.autotask.ui.tasks;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.karolgurecki.autotask.R;
import org.karolgurecki.autotask.utils.ConstanceFieldHolder;

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

    public AbstractInputTextDialog(Context context, String title, String type) {
        super(context);
        setContentView(R.layout.text_field_dialog);
        setCancelable(true);
        setTitle(title);
        setRadioListener(context, type);
        cancelButton = (Button) findViewById(R.id.textDialogCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        show();
    }

    private void setRadioListener(final Context context, final String type) {
        textView = (TextView) findViewById(R.id.textDialogEditText);
        confirmButton = (Button) findViewById(R.id.textDialogConfirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setActiveValue(textView.getText().toString())){
                    Intent intent=new Intent(
                            ConstanceFieldHolder.INTERNAL_CONFIRM_ADDING_TASK_OBJECT_ACTION);
                    intent.putExtra(ConstanceFieldHolder.EXTRA_TYPE,type);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    dismiss();
                }
            }
        });
    }

    /**
     * Sets active value.
     * @return true if it's valid value
     */
    protected abstract boolean setActiveValue(String text);
}
