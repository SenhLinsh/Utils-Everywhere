package com.linsh.utilseverywhere;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类：辅助功能（无障碍服务功能）相关
 * </pre>
 */
public class AccessibilityUtils {

    private AccessibilityUtils() {
    }

    private static Context getContext() {
        return ContextUtils.get();
    }

    /**
     * 跳转『无障碍服务』界面
     */
    public static void gotoAccessibilitySetting() {
        getContext().startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 检查辅助功能（无障碍服务功能）是否开启
     */
    public static boolean isAccessibilitySettingsOn() {
        int result;
        Context context = getContext();
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