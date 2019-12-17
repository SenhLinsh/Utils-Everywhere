package com.linsh.utilseverywhere;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 权限相关
 * </pre>
 */
public class PermissionUtils {

    private PermissionUtils() {
    }

    private static Context getContext() {
        return ContextUtils.get();
    }

    //================================================ Android 6.0 权限申请 ================================================//

    /**
     * Android 6.0 以上检查权限
     * <p>该方法只有在 Android 6.0 以上版本才能返回正确的结果, 因此需要提前判断 SDK 版本
     * <br>注: Android M 以下系统检查的是清单文件而不是用户决定是否授予的权限
     *
     * @param permission 权限
     * @return 是否通过
     */
    public static boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(getContext(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Android 6.0 以上检查权限
     * <p>该方法只有在 Android 6.0 以上版本才能返回正确的结果, 因此需要提前判断 SDK 版本
     * <br>注: Android M 以下系统检查的是清单文件而不是用户决定是否授予的权限
     *
     * @param permissions 多个权限
     * @return 是否全部通过
     */
    public static boolean checkPermissions(String... permissions) {
        for (String permission : permissions) {
            if (!checkPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查权限, 如果不通过, 将自动请求该系统权限
     *
     * @param activity    Activity
     * @param permission  系统权限
     * @param requestCode 请求码
     */
    public static void checkOrRequestPermission(Activity activity, String permission, int requestCode) {
        checkOrRequestPermissions(activity, new String[]{permission}, requestCode);
    }

    /**
     * 检查多个权限, 如果不通过, 将自动请求该系统权限
     *
     * @param activity    Activity
     * @param permissions 多个系统权限
     * @param requestCode 请求码
     */
    public static void checkOrRequestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] requests = new String[permissions.length];
            int size = 0;
            for (String permission : permissions) {
                if (!checkPermission(permission) && !isPermissionNeverAsked(activity, permission)) {
                    requests[size++] = permission;
                }
            }
            if (size > 0) {
                String[] newRequests;
                if (size == permissions.length) {
                    newRequests = requests;
                } else {
                    newRequests = new String[size];
                    System.arraycopy(requests, 0, newRequests, 0, size);
                }
                requestPermissions(activity, newRequests, requestCode);
            }
        }
    }

    /**
     * 申请系统权限
     *
     * @param activity    Activity
     * @param permission  系统权限
     * @param requestCode 请求码
     */
    public static void requestPermission(Activity activity, String permission, int requestCode) {
        requestPermissions(activity, new String[]{permission}, requestCode);
    }

    /**
     * 申请duoge系统权限
     *
     * @param activity    Activity
     * @param permissions 系统权限
     * @param requestCode 请求码
     */
    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    /**
     * 判断指定的权限是否被用户选择不再弹出提醒 (即申请权限时无法弹出窗口, 需要用户手动前往权限界面进行打开)
     *
     * @param activity   Activity
     * @param permission 系统权限
     * @return 是否不再弹出提醒
     */
    public static boolean isPermissionNeverAsked(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    /**
     * 解析 Activity 中申请权限的回调, 无需自己进行判断, 需重写方法 {@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * 并在该方法中调用此方法
     *
     * @param activity     Activity
     * @param requestCode  requestCode
     * @param permissions  permissions
     * @param grantResults grantResults
     * @param listener     权限回调
     */
    public static void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions,
                                                  @NonNull int[] grantResults, @NonNull PermissionListener listener) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                listener.onGranted(permissions[i]);
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                listener.onDenied(permissions[i], false);
            } else {
                listener.onDenied(permissions[i], true);
            }
        }
    }

    public interface PermissionListener {

        void onGranted(String permission);

        void onDenied(String permission, boolean isNeverAsked);

        /**
         * 当前系统版本在为 Android M (6.0) 以下, 无法得知是否拥有该权限, 需要实际运行才可得知
         */
        void onBeforeAndroidM(String permission);
    }

}
