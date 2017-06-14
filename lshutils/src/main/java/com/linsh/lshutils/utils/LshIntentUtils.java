package com.linsh.lshutils.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;

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

    public static void gotoPickFile(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoPickFile(Activity activity, int requestCode, String fileExtension) {
        String type = LshMimeTypeUtils.getMimeTypeFromExtension(fileExtension);
        if (type == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoPickPhoto(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoPickVideo(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoPickAudio(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoTakePhoto(Activity activity, int requestCode, String authority, File outputFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(activity, authority, outputFile));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
        }
        activity.startActivityForResult(intent, requestCode);
    }

    public static void gotoCropPhotoAsAvatar(Activity activity, int requestCode, String authority, File inputFile, File outputFile) {
        gotoCropPhoto(activity, requestCode, authority, inputFile, outputFile, 1, 1, 1024, 1024);
    }

    public static void gotoCropPhoto(Activity activity, int requestCode, String authority, File inputFile, File outputFile,
                                     int aspectX, int aspectY, int outputX, int outputY) {
        Uri inputUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            inputUri = FileProvider.getUriForFile(activity, authority, inputFile);
        } else {
            inputUri = Uri.fromFile(inputFile);
        }
        gotoCropPhoto(activity, requestCode, inputUri, Uri.fromFile(outputFile), aspectX, aspectY, outputX, outputY);
    }

    public static void gotoCropPhoto(Activity activity, int requestCode, Uri inputUri, Uri outputUri,
                                     int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
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
        activity.startActivityForResult(intent, requestCode);
    }

    public static String getFilePathFromResult(Intent data) {
        if (data == null) return null;

        Uri uri = data.getData();
        if ("content".equals(uri.getScheme())) {
            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};
            try {
                cursor = LshApplicationUtils.getContext().getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int column_index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(column_index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    // 跳转: 设置界面
    public static void gotoSetting() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LshApplicationUtils.getContext().startActivity(intent);
    }

    // 跳转: 应用程序列表界面
    public static void gotoAppsSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    // 跳转: Wifi列表设置
    public static void gotoWifiSetting() {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    // 跳转: 飞行模式，无线网和网络设置界面
    public static void gotoWirelessSetting() {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
