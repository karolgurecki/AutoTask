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
    private View mRow;
    private TextView description = null;
    private TextView title = null;

    public ViewHolder(View row) {
        mRow = row;
    }

    public TextView getDescription() {
        if (null == description) {
            description = (TextView) mRow.findViewById(R.id.litemDescription);
        }
        return description;
    }

    public TextView getTitle() {
        if (null == title) {
            title = (TextView) mRow.findViewById(R.id.itemTitle);
        }
        return title;
    }
}
