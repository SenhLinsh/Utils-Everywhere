package com.linsh.lshutils.utils.Basic;

import android.widget.Toast;

/**
 * Created by Senh Linsh on 17/1/11.
 * <p>
 * 1.更加简练的方法调用, 不用传入context
 * 2.即时更改Toast内容, 不会多次弹出Toast
 */
public class LshToastUtils {

    private static Toast sToast;

    public static void showToast(String msg) {
        if (sToast == null) {
            sToast = Toast.makeText(LshApplicationUtils.getContext(), msg, Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.show();
    }
}
