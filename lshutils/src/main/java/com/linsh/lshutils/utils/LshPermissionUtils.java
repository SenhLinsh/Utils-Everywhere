package com.linsh.lshutils.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.provider.Settings;

import com.linsh.lshutils.utils_ex.AppUtils;

/**
 * Created by Senh Linsh on 16/7/9.
 */
public class LshPermissionUtils {
    private static final int RECORDER_SAMPLERATE = 8000;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    public static boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * 判断是是否有录音权限
     */
    public static boolean hasRecordPermission() {
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
    }

    public static boolean hasCameraPermission() {
        try {
            Camera cam = Camera.open();
            cam.release();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    // 根据包名跳转到系统自带的应用程序信息界面
    public static void gotoAppDetailSetting(Context context) {
        Uri packageURI = Uri.parse("package:" + AppUtils.getVersionName(context));
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        startActivityWithNewTask(context, intent);
    }

    private static void startActivityWithNewTask(Context context, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
