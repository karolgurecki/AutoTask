package org.karolgurecki.autotask.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by: Nappa
 * Version: 0.01
 * Since: 0.01
 */
public class NotScrollableList extends ListView {
    public NotScrollableList(Context context) {
        super(context);
    }

    public NotScrollableList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotScrollableList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE)
            return true;
        return super.dispatchTouchEvent(ev);
    }
}
