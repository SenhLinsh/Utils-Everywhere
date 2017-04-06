package com.linsh.lshutils.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.linsh.lshutils.utils.Basic.LshApplicationUtils;


/**
 * Created by Senh Linsh on 16/7/9.
 */
public class LshPermissionUtils {
    private static final int RECORDER_SAMPLERATE = 8000;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    //================================================ Android 6.0 权限申请 ================================================//

    public static boolean checkPermissions(String... permissions) {
        for (String permission : permissions) {
            if (!checkPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查权限, 适用于 Android M 以上系统 <br/>
     * 注: Android M 以下系统总是返回true
     */
    public static boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(LshApplicationUtils.getContext(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, String permission, int code, PermissionRequestHandler handler) {
        requestPermissions(activity, new String[]{permission}, code, handler);
    }

    public static void requestPermissions(Activity activity, String[] permissions, int code, PermissionRequestHandler handler) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;

        if (handler != null) {
            handler.mCode = code;
            handler.mPermissions = permissions;
        }

        ActivityCompat.requestPermissions(activity, permissions, code);
    }

    public static boolean isPermissionNeverAsked(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    public static abstract class PermissionRequestHandler {
        protected int mCode;
        protected String[] mPermissions;

        public void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if (mCode == requestCode) {
                for (int i = 0; i < permissions.length; i++) {
                    if (i < mPermissions.length && permissions[i].equals(mPermissions[i])) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            onGranted(mPermissions[i]);
                        } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                            onDenied(mPermissions[i]);
                        } else {
                            onNeverAsked(mPermissions[i]);
                        }
                    }
                }
            }
        }

        public abstract void onGranted(String permission);

        public abstract void onDenied(String permission);

        public abstract void onNeverAsked(String permission);
    }

    //================================================ 权限检查 ================================================//

    /**
     * 检查相机硬件是否可用
     */
    public static boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * 判断是是否有录音权限
     */
    public static boolean hasRecordPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return hasRecordPermissionBeforeAndroidM();
        } else {
            return hasRecordPermissionAfterAndroidM();
        }
    }

    public static boolean hasRecordPermissionBeforeAndroidM() {
        try {

            AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    RECORDER_SAMPLERATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, 1024 * 2);
            //开始录制音频
            try {
                // 防止某些手机崩溃，例如联想
                audioRecord.startRecording();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            // 根据开始录音判断是否有录音权限
            if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                return false;
            }
            audioRecord.stop();
            audioRecord.release();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean hasRecordPermissionAfterAndroidM() {
        return checkPermission(Manifest.permission.RECORD_AUDIO);
    }

    public static boolean hasCameraPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return hasCameraPermissionBeforeAndroidM();
        } else {
            return hasCameraPermissionAfterAndroidM();
        }
    }

    public static boolean hasCameraPermissionBeforeAndroidM() {
        try {
            Camera cam = Camera.open();
            cam.release();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean hasCameraPermissionAfterAndroidM() {
        return checkPermission(Manifest.permission.CAMERA);
    }

    // 跳转到设置界面
    public static void gotoSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivityWithNewTask(context, intent);
    }

    // 跳转应用程序列表界面
    public static void gotoAppSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
        startActivityWithNewTask(context, intent);
    }

    // 跳转Wifi列表设置
    public static void gotoWifiSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivityWithNewTask(context, intent);
    }

    // 飞行模式，无线网和网络设置界面
    public static void gotoWirelessSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivityWithNewTask(context, intent);
    }

    private static void startActivityWithNewTask(Context context, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
