package com.linsh.lshutils.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;

/**
 * Created by Senh Linsh on 17/5/11.
 */

public class LshPhoneStateUtils {

    /**
     * 判断屏幕是否处于锁屏状态 (黑屏或锁屏)
     */
    public static boolean isScreenLocked() {
        KeyguardManager km = (KeyguardManager) LshApplicationUtils.getContext().getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }

    /**
     * 判断屏幕是否亮着
     */
    public static boolean isScreenOn() {
        PowerManager pm = (PowerManager) LshApplicationUtils.getContext().getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }
}
