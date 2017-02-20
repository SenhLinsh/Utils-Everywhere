package com.linsh.lshutils.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;

/**
 * Created by Senh Linsh on 17/1/6.
 */
public class LshShortcutUtils {

    /**
     * 添加桌面快捷方式
     * 权限: <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
     */
    public static void createShortcut(String shortcutName, int appIcon) {
        if (hasShortcut(shortcutName)) {
            return;
        }

        Context context = LshApplicationUtils.getContext();
        // 指定动作名称
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷方式名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        // 不允许重复创建（不一定有效）
        shortcut.putExtra("duplicate", false);
        // 快捷方式的图标
        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(context, appIcon);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
        shortcut.putExtra("createShortcut", "createShortcut");

        Intent shortcutIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

        context.sendBroadcast(shortcut);
    }

    /**
     * 创建快捷图标
     * 权限: <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
     */
    public static void createShortcut(String shortcutName, int appIcon, Class activity) {
        // 先判断该快捷是否存在
        if (hasShortcut(shortcutName)) {
            return;
        }

        Context context = LshApplicationUtils.getContext();

        Intent intent = new Intent();
        // 指定动作名称
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        // 指定快捷方式的图标
        Parcelable icon = Intent.ShortcutIconResource.fromContext(context, appIcon);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        // 指定快捷方式的名称
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);

        // 指定快捷图标激活哪个activity
        Intent i = new Intent();
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName component = new ComponentName(context, activity);
        i.setComponent(component);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);

        context.sendBroadcast(intent);
    }

    /**
     * 删除当前应用的桌面快捷方式
     */
    public static void delShortcut(String shortcutName) {
        if (!hasShortcut(shortcutName)) {
            return;
        }

        Context context = LshApplicationUtils.getContext();

        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        Intent shortcutIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        context.sendBroadcast(shortcut);
    }

    /**
     * 判断当前应用在桌面是否有桌面快捷方式
     * <p/>
     * 注意:目前此方法暂时无效, 需要后期进行改进
     */
    public static boolean hasShortcut(String shortcutName) {
        Context context = LshApplicationUtils.getContext();
        boolean result = false;

        final String uriStr;
        if (android.os.Build.VERSION.SDK_INT < 8) {
            uriStr = "content://com.android.launcher.settings/favorites?notify=true";
        } else if (android.os.Build.VERSION.SDK_INT < 19) {
            uriStr = "content://com.android.launcher2.settings/favorites?notify=true";
        } else {
            uriStr = "content://com.android.launcher3.settings/favorites?notify=true";
        }
        final Uri CONTENT_URI = Uri.parse(uriStr);
        final Cursor c = context.getContentResolver().query(CONTENT_URI, null,
                "title=?", new String[]{shortcutName}, null);
        if (c != null && c.getCount() > 0) {
            result = true;
        }
        return result;
    }
}
