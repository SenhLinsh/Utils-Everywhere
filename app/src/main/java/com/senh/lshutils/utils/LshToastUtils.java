package com.senh.lshutils.utils;

import android.widget.Toast;

import com.example.yy.feibo_common.DrawApplication;

/**
 * 1.更加简练的方法调用, 不用传入context
 * 2.即时更改Toast内容, 不会多次弹出Toast
 */
public class LshToastUtils {

    private static Toast sToast;

    public static void showToast(String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(DrawApplication.getContext(), msg, Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.show();
    }
}
