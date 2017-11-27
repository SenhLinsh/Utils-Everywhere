package com.linsh.lshutils.utils;

import android.view.MotionEvent;
import android.view.View;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 触摸事件相关
 * </pre>
 */
public class TouchUtils {

    /**
     * 重写触摸事件回调, 模仿点击事件
     *
     * @param view     View
     * @param listener 点击时间回调
     */
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
