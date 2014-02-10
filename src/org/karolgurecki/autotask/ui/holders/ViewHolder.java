package org.karolgurecki.autotask.ui.holders;

import android.view.View;
import android.widget.TextView;
import org.karolgurecki.autotask.R;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class ViewHolder {
    private View row;
    private TextView title = null;

    public ViewHolder(View row) {
        this.row = row;
    }

    public TextView getTitle() {
        if (null == title) {
            title = (TextView) row.findViewById(R.id.itemTitle);
        }
        return title;
    }
}