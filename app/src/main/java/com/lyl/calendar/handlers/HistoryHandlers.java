package com.lyl.calendar.handlers;

import android.util.Log;
import android.view.View;

/**
 * Created by lyl on 2016/8/6.
 */

public class HistoryHandlers {

    private static final String TAG = HistoryHandlers.class.getSimpleName();

    public void onClick(View view) {
        Log.d(TAG, "onClick: " + view.getId());
    }
}
