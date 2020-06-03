package com.linsh.utilseverywhere;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import java.util.Collections;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/13
 *    desc   : 工具类: 快捷方式 相关
 *
 *          该工具类存在部分问题, 需要后期优化, 目前仅供参考
 * </pre>
 */
public class ShortcutUtils {

    private ShortcutUtils() {
    }

    private static Context getContext() {
        return ContextUtils.get();
    }

    /**
     * 创建桌面快捷方式
     *
     * @param name     名称
     * @param icon     图标
     * @param activity 指定打开的页面
     * @return 是否创建成功
     */
    public static boolean createShortcut(String name, int icon, Class<? extends Activity> activity) {
        return createShortcut(name, name, icon, activity);
    }

    /**
     * 创建桌面快捷方式
     *
     * @param id       唯一标识
     * @param name     名称
     * @param icon     图标
     * @param activity 点击跳转的 Activity
     * @return 是否创建成功
     */
    public static boolean createShortcut(String id, String name, int icon, Class<? extends Activity> activity) {
        Context context = getContext();
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            ShortcutInfoCompat info = new ShortcutInfoCompat.Builder(context, id)
                    .setIcon(IconCompat.createWithResource(context, icon))
                    .setShortLabel(name)
                    .setIntent(new Intent(context, activity)
                            .setAction(Intent.ACTION_VIEW))
                    .build();
            return ShortcutManagerCompat.requestPinShortcut(context, info, null);
        }
        return false;
    }

    /**
     * Android O 及以下创建桌面快捷方式
     *
     * @param id       唯一标识
     * @param name     名称
     * @param icon     图标
     * @param activity 点击跳转的 Activity
     * @return 是否创建成功
     * @deprecated 建议使用 {@link ShortcutManagerCompat#requestPinShortcut(Context, ShortcutInfoCompat, IntentSender)} 兼容
     */
    @Deprecated
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static boolean createShortCutAboveAndroidO(String id, String name, int icon, Class<? extends Activity> activity) {
        Context context = getContext();
        ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
        if (shortcutManager.isRequestPinShortcutSupported()) {
            ShortcutInfo info = new ShortcutInfo.Builder(context, id)
                    .setIcon(Icon.createWithResource(context, icon))
                    .setShortLabel(name)
                    .setIntent(new Intent(context, activity)
                            .setAction(Intent.ACTION_VIEW))
                    .build();
            return shortcutManager.requestPinShortcut(info, null);
        }
        return false;
    }

    /**
     * Android O 以下创建桌面快捷方式
     *
     * <p>需要权限: <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
     *
     * @param name      名称
     * @param icon      图标
     * @param activity  指定打开的页面
     * @param duplicate 是否允许重复创建
     * @deprecated 建议使用 {@link ShortcutManagerCompat#requestPinShortcut(Context, ShortcutInfoCompat, IntentSender)} 兼容
     */
    @Deprecated
    private static void createShortcutBelowAndroidO(String name, int icon, Class<? extends Activity> activity, boolean duplicate) {
        Context context = getContext();
        // action: INSTALL_SHORTCUT
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        // 图标
        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(context, icon);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
        // 是否允许重复创建
        shortcut.putExtra("duplicate", duplicate);
        // 关联程序
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(context, activity));
        // 广播注册快捷方式
        context.sendBroadcast(shortcut);
    }

    /**
     * 删除当前应用指点的桌面快捷方式
     *
     * @param id 唯一标识
     * @deprecated 未经测试验证
     */
    @Deprecated
    private static void removeShortcut(String id) {
        Context context = getContext();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
            shortcutManager.disableShortcuts(Collections.singletonList(id));
        } else {
            Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, id);
            context.sendBroadcast(shortcut);
        }
    }
}
