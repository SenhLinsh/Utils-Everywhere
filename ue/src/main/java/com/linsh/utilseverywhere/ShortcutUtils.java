package com.linsh.utilseverywhere;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;

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
@Deprecated
public class ShortcutUtils {

    private ShortcutUtils() {
    }

    private static Context getContext() {
        return ContextUtils.get();
    }

    /**
     * 创建桌面快捷方式, 点击跳转本应用
     * <p>需要权限: <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
     *
     * @param name 名称
     * @param icon 图标
     */
    public static void createShortcut(String name, int icon) {
        if (hasShortcut(name)) {
            return;
        }
        Context context = getContext();
        // 指定动作名称
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷方式名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        // 不允许重复创建（不一定有效）
        shortcut.putExtra("duplicate", false);
        // 快捷方式的图标
        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(context, icon);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
        shortcut.putExtra("createShortcut", "createShortcut");

        Intent shortcutIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

        context.sendBroadcast(shortcut);
    }

    /**
     * 创建桌面快捷方式, 点击指定 Activity
     * 权限: <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
     *
     * @param name     名称
     * @param icon     图标
     * @param activity 需要跳转的 Activity 的类
     */
    public static void createShortcut(String name, int icon, Class<? extends Activity> activity) {
        // 先判断该快捷是否存在
        if (hasShortcut(name)) {
            return;
        }
        Context context = getContext();
        Intent intent = new Intent();
        // 指定动作名称
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        // 指定快捷方式的图标
        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(context, icon);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
        // 指定快捷方式的名称
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);

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
     * 删除当前应用指点的桌面快捷方式
     *
     * @param name 快捷方式名称
     */
    public static void delShortcut(String name) {
        if (!hasShortcut(name)) {
            return;
        }
        Context context = getContext();
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        Intent shortcutIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        context.sendBroadcast(shortcut);
    }

    /**
     * 判断当前应用在桌面上是否存在指定的桌面快捷方式
     * <p/>
     * 注意:目前此方法暂时无效, 需要后期进行改进 // TODO: 17/11/13
     *
     * @param shortcutName 快捷方式名称
     * @return 是否存在该快捷方式
     */
    @Deprecated
    public static boolean hasShortcut(String shortcutName) {
        Context context = getContext();
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
