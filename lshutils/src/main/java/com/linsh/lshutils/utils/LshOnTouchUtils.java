package com.linsh.lshutils.utils;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Senh Linsh on 17/9/14.
 */

public class LshOnTouchUtils {

    public static void asOnClick(final View view, final View.OnClickListener listener) {
        view.setOnTouchListener(new View.OnTouchListener() {
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
                            listener.onClick(view);
                        }
                        onClick = 0;
                        break;
                }
                return false;
            }
        });
    }
}
