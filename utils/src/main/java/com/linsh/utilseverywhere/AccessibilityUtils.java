package com.linsh.utilseverywhere;

import android.content.Context;
import android.provider.Settings;
import android.widget.Toast;

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

    /**
     * 检查辅助功能（无障碍服务功能）是否开启，如果没有开启则自动跳转『无障碍服务』界面
     * @return
     */
    public static boolean checkAccessibility() {
        if (isAccessibilitySettingsOn()) {
            return true;
        }
        IntentUtils.gotoAccessibilitySetting();
        Toast.makeText(ContextUtils.get(), "请先开启该无障碍服务功能", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 检查辅助功能（无障碍服务功能）是否开启
     */
    public static boolean isAccessibilitySettingsOn() {
        int result;
        Context context = ContextUtils.get();
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