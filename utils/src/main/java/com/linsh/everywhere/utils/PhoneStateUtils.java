package com.linsh.everywhere.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 手机状态相关
 * </pre>
 */
public class PhoneStateUtils {

    /**
     * 判断屏幕是否处于锁屏状态 (黑屏或锁屏)
     *
     * @return 是否处于锁屏状态
     */
    public static boolean isScreenLocked() {
        KeyguardManager km = (KeyguardManager) ApplicationUtils.getContext().getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }

    /**
     * 判断屏幕是否亮着
     *
     * @return 是否亮着
     */
    public static boolean isScreenOn() {
        PowerManager pm = (PowerManager) ApplicationUtils.getContext().getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }
}
