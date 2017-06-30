package com.linsh.lshutils.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;
import com.linsh.lshutils.utils.Basic.LshIOUtils;
import com.linsh.lshutils.utils.Basic.LshLogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Senh Linsh on 17/6/7.
 */

public class LshIntentUtils {

    private static void gotoPickFile(Activity activity, int requestCode) {
        gotoPickFile(activity, null, "file/*", requestCode);
    }

    private static void gotoPickFile(Fragment fragment, int requestCode) {
        gotoPickFile(null, fragment, "file/*", requestCode);
    }

    public static void gotoPickFile(Activity activity, int requestCode, String fileExtension) {
        String type = LshMimeTypeUtils.getMimeTypeFromExtension(fileExtension);
        if (type == null) {
            return;
        }
        gotoPickFile(activity, null, type, requestCode);
    }

    public static void gotoPickPhoto(Activity activity, int requestCode) {
        gotoPickFile(activity, null, "image/*", requestCode);
    }

    public static void gotoPickPhoto(Fragment fragment, int requestCode) {
        gotoPickFile(null, fragment, "image/*", requestCode);
    }

    public static void gotoPickVideo(Activity activity, int requestCode) {
        gotoPickFile(activity, null, "video/*", requestCode);
    }

    public static void gotoPickVideo(Fragment fragment, int requestCode) {
        gotoPickFile(null, fragment, "video/*", requestCode);
    }

    public static void gotoPickAudio(Activity activity, int requestCode) {
        gotoPickFile(activity, null, "audio/*", requestCode);
    }

    public static void gotoPickAudio(Fragment fragment, int requestCode) {
        gotoPickFile(null, fragment, "audio/*", requestCode);
    }

    /**
     * 打开选择文件界面
     */
    private static void gotoPickFile(Activity activity, Fragment fragment, String type, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(activity, fragment, intent, requestCode);
    }

    public static void gotoTakePhoto(Activity activity, int requestCode, File outputFile) {
        gotoTakePhoto(activity, null, requestCode, outputFile);
    }

    public static void gotoTakePhoto(Fragment fragment, int requestCode, File outputFile) {
        gotoTakePhoto(null, fragment, requestCode, outputFile);
    }

    /**
     * 打开系统的拍照界面
     */
    private static void gotoTakePhoto(Activity activity, Fragment fragment, int requestCode, File outputFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, LshFileProviderUtils.getUriForFile(outputFile));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
        }
        startActivityForResult(activity, fragment, intent, requestCode);
    }

    public static void gotoCropPhoto(Activity activity, int requestCode, File inputFile, File outputFile,
                                      int aspectX, int aspectY, int outputX, int outputY) {
        gotoCropPhoto(activity, null, requestCode, inputFile, outputFile, aspectX, aspectY, outputX, outputY);
    }

    public static void gotoCropPhoto(Fragment fragment, int requestCode, File inputFile, File outputFile,
                                      int aspectX, int aspectY, int outputX, int outputY) {
        gotoCropPhoto(null, fragment, requestCode, inputFile, outputFile, aspectX, aspectY, outputX, outputY);
    }

    private static void gotoCropPhoto(Activity activity, Fragment fragment, int requestCode, File inputFile, File outputFile,
                                      int aspectX, int aspectY, int outputX, int outputY) {
        Uri inputUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            inputUri = LshFileProviderUtils.getUriForFile(inputFile);
        } else {
            inputUri = Uri.fromFile(inputFile);
        }
        gotoCropPhoto(activity, fragment, requestCode, inputUri, Uri.fromFile(outputFile), aspectX, aspectY, outputX, outputY);
    }

    public static void gotoCropPhoto(Activity activity, int requestCode, Uri inputUri, Uri outputUri,
                                      int aspectX, int aspectY, int outputX, int outputY) {
        gotoCropPhoto(activity, null, requestCode, inputUri, outputUri, aspectX, aspectY, outputX, outputY);
    }

    public static void gotoCropPhoto(Fragment fragment, int requestCode, Uri inputUri, Uri outputUri,
                                      int aspectX, int aspectY, int outputX, int outputY) {
        gotoCropPhoto(null, fragment, requestCode, inputUri, outputUri, aspectX, aspectY, outputX, outputY);
    }

    /**
     * 打开系统的剪裁界面
     */
    private static void gotoCropPhoto(Activity activity, Fragment fragment, int requestCode, Uri inputUri, Uri outputUri,
                                      int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(inputUri, "image/*");
        intent.putExtra("crop", "true");
        // 指定输出宽高比
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        // 指定输出宽高
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        // 指定输出路径和文件类型
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        startActivityForResult(activity, fragment, intent, requestCode);
    }

    /**
     * 跳转: 设置界面
     */
    public static void gotoSetting() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LshApplicationUtils.getContext().startActivity(intent);
    }

    /**
     * 跳转: 应用程序列表界面
     */
    public static void gotoAppsSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LshApplicationUtils.getContext().startActivity(intent);
    }

    /**
     * 跳转: Wifi列表设置
     */
    public static void gotoWifiSetting() {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LshApplicationUtils.getContext().startActivity(intent);
    }

    /**
     * 跳转: 飞行模式，无线网和网络设置界面
     */
    public static void gotoWirelessSetting() {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LshApplicationUtils.getContext().startActivity(intent);
    }

    /**
     * 根据各大厂商的不同定制而跳转至其权限设置
     * 目前已测试成功机型: 小米V7, 华为, 三星
     *
     * @return 成功跳转权限设置, 返回 true; 没有适配该厂商或不能跳转, 则自动默认跳转设置界面, 并返回 false
     */
    public static boolean gotoPermissionSetting() {
        boolean success = true;
        Intent intent = new Intent();
        String packageName = LshAppUtils.getPackageName();
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        switch (manufacturer) {
            case Manufacturer.HUAWEI:
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
                break;
            case Manufacturer.MEIZU:
                intent.setAction("com.meizu.safe.security.SHOW_APPSEC");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra("packageName", packageName);
                break;
            case Manufacturer.XIAOMI:
                String rom = getMiuiVersion();
                if ("V6".equals(rom) || "V7".equals(rom)) {
                    intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                    intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                    intent.putExtra("extra_pkgname", packageName);
                } else if ("V8".equals(rom)) {
                    intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                    intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                    intent.putExtra("extra_pkgname", packageName);
                } else {
                    Uri packageURI = Uri.parse("package:" + packageName);
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(packageURI);
                }
                break;
            case Manufacturer.SONY:
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
                break;
            case Manufacturer.OPPO:
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.PermissionManagerActivity"));
                break;
            case Manufacturer.LETV:
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps"));
                break;
            case Manufacturer.LG:
                intent.setAction("android.intent.action.MAIN");
                intent.putExtra("packageName", packageName);
                ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
                intent.setComponent(comp);
                break;
            case Manufacturer.SAMSUNG:
                Uri packageURI = Uri.parse("package:" + packageName);
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(packageURI);
                break;
            default:
                intent.setAction(Settings.ACTION_SETTINGS);
                LshLogUtils.i("没有适配该机型, 跳转普通设置界面");
                success = false;
                break;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            LshApplicationUtils.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // 跳转失败, 前往普通设置界面
            LshIntentUtils.gotoSetting();
            success = false;
            LshLogUtils.i("无法跳转权限界面, 开始跳转普通设置界面");
        }
        return success;
    }

    private static void startActivity(Activity activity, Fragment fragment, Intent intent) {
        if (activity != null) {
            activity.startActivity(intent);
        } else if (fragment != null) {
            fragment.startActivity(intent);
        }
    }

    private static void startActivityForResult(Activity activity, Fragment fragment, Intent intent, int requestCode) {
        if (activity != null) {
            activity.startActivityForResult(intent, requestCode);
        } else if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            LshIOUtils.close(input);
        }
        LshLogUtils.i("MiuiVersion = " + line);
        return line;
    }

    private interface Manufacturer {
        String HUAWEI = "huawei";    // 华为
        String MEIZU = "meizu";      // 魅族
        String XIAOMI = "xiaomi";    // 小米
        String SONY = "sony";        // 索尼
        String SAMSUNG = "samsung";  // 三星
        String LETV = "letv";        // 乐视
        String ZTE = "zte";          // 中兴
        String YULONG = "yulong";    // 酷派
        String LENOVO = "lenovo";    // 联想
        String LG = "lg";            // LG
        String OPPO = "oppo";        // oppo
        String VIVO = "vivo";        // vivo
    }
}
