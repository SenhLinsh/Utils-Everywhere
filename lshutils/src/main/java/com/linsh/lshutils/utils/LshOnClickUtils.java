package com.linsh.lshutils.utils;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Senh Linsh on 17/3/8.
 */

public class LshOnClickUtils {

    public static void setOnRecyclerViewClickListener(final RecyclerView recyclerView, final View.OnClickListener listener) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            private int onClick = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        onClick = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        onClick++;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (onClick < 3) {
                            listener.onClick(recyclerView);
                        }
                        onClick = 3;
                        break;
                }
                return false;
            }
        });
    }
}
