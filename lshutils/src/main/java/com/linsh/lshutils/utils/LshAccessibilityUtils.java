package com.linsh.lshutils.utils;

import android.content.Context;
import android.provider.Settings;
import android.widget.Toast;

public class LshAccessibilityUtils {

    public static boolean checkAccessibility() {
        if (isAccessibilitySettingsOn()) {
            return true;
        }
        LshIntentUtils.gotoAccessibilitySetting();
        Toast.makeText(LshContextUtils.get(), "请先开启该无障碍服务功能", Toast.LENGTH_SHORT).show();
        return false;
    }

    public static boolean isAccessibilitySettingsOn() {
        int result;
        Context context = LshContextUtils.get();
        try {
            result = Settings.Secure.getInt(context.getContentResolver(), "accessibility_enabled");
            if (result != 1)
                return false;
            String str = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services");
            if (str == null)
                return false;
            return str.toLowerCase().contains(context.getPackageName().toLowerCase());
        } catch (Settings.SettingNotFoundException localSettingNotFoundException) {
            localSettingNotFoundException.printStackTrace();
        }
        return false;
    }
}