package com.linsh.utilseverywhere;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/06/13
 *    desc   : Utils-Everywhere 独家权限封装工具类类
 * </pre>
 */
public class UEPermission {

    private UEPermission() {
    }

    private static Context getContext() {
        return ContextUtils.get();
    }

    /**
     * 相机权限 {@link Manifest.permission#CAMERA}
     */
    public static class Camera {

        /**
         * 检查是否拥有相机权限
         *
         * @return 是否拥有权限
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        public static boolean check() {
            return ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        }

        /**
         * 在 Android M 之前的版本, 检查相机权限. 由于无法直接调用 API 查询是否拥有权限,
         * 需显式调用相机相关 API, 通过抓取异常来进行判断.
         *
         * @return 返回 false 不一定是没有权限, 也有一定的可能是摄像头被占用的情况(可能性较低)
         */
        public static boolean checkBeforeAndroidM() {
            android.hardware.Camera cam = null;
            try {
                cam = android.hardware.Camera.open();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (cam != null) cam.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 请求相机权限
         *
         * @param activity    Activity
         * @param requestCode 请求码
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        public static void request(Activity activity, @IntRange(from = 0) int requestCode) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA}, requestCode);
        }

        /**
         * 检查相机权限, 如果检查未通过, 则自动请求权限
         *
         * @param activity    Activity
         * @param requestCode 请求码
         * @return 检查通过返回 true, 检查未通过返回 false
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        public static boolean checkOrRequest(Activity activity, @IntRange(from = 0) int requestCode) {
            if (check()) {
                return true;
            }
            request(activity, requestCode);
            return false;
        }

        /**
         * 检查 Activity 中 onRequestPermissionResult() 回调的结果, 是否获取到该权限
         *
         * @return 是否获取到该权限
         */
        public static boolean checkResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
            for (int i = 0; i < permissions.length; i++) {
                if (Manifest.permission.CAMERA.equals(permissions[i])) {
                    return grantResults[i] == PackageManager.PERMISSION_GRANTED;
                }
            }
            return false;
        }

        /**
         * 检查该权限是否已被用户设置 "不再提醒" 该权限的请求窗口
         *
         * @return true: 不再提醒, 即无法再次弹出请求权限的窗口; false 反之
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        public static boolean isNeverAsked(Activity activity) {
            return !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.CAMERA);
        }
    }

    /**
     * 悬浮窗权限 {@link Manifest.permission#SYSTEM_ALERT_WINDOW}
     */
    public static class SystemAlertWindow {

        @RequiresApi(api = Build.VERSION_CODES.M)
        public static boolean check(Activity activity) {
            return Settings.canDrawOverlays(activity);
        }

        /**
         * 悬浮窗权限默认是关闭的, 常规的请求无法弹出请求窗口, 需要自己动手打开 (目前发现小米系统是这样的)
         *
         * @param activity Activity
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        public static void gotoPermissionSetting(Activity activity) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            activity.startActivity(intent);
        }
    }

    /**
     * 麦克风权限 {@link Manifest.permission#RECORD_AUDIO}
     */
    public static class Microphone {
        private static final int RECORDER_SAMPLERATE = 8000;
        private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
        private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public static boolean check() {
            return ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        }

        /**
         * @return 返回 false 不一定是没有权限, 也有一定的可能是麦克风被占用的情况(可能性较低)
         */
        public static boolean checkBeforeAndroidM() {
            try {
                boolean hasPermission = true;

                AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                        RECORDER_SAMPLERATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, 1024 * 2);
                //开始录制音频
                try {
                    // 防止某些手机崩溃
                    audioRecord.startRecording();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                // 根据开始录音判断是否有录音权限
                if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
                    hasPermission = false;
                }
                audioRecord.stop();
                audioRecord.release();
                return hasPermission;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 请求麦克风权限
         *
         * @param activity    Activity
         * @param requestCode 请求码
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        public static void request(Activity activity, @IntRange(from = 0) int requestCode) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.RECORD_AUDIO}, requestCode);
        }

        /**
         * 检查麦克风权限, 如果检查未通过, 则自动请求权限
         *
         * @param activity    Activity
         * @param requestCode 请求码
         * @return 检查通过返回 true, 检查未通过返回 false
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        public static boolean checkOrRequest(Activity activity, @IntRange(from = 0) int requestCode) {
            if (check()) {
                return true;
            }
            request(activity, requestCode);
            return false;
        }

        /**
         * 检查 Activity 中 onRequestPermissionResult() 回调的结果, 是否获取到该权限
         *
         * @return 是否获取到该权限
         */
        public static boolean checkResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
            for (int i = 0; i < permissions.length; i++) {
                if (Manifest.permission.RECORD_AUDIO.equals(permissions[i])) {
                    return grantResults[i] == PackageManager.PERMISSION_GRANTED;
                }
            }
            return false;
        }

        /**
         * 检查该权限是否已被用户设置 "不再提醒" 该权限的请求窗口
         *
         * @return true: 不再提醒, 即无法再次弹出请求权限的窗口; false 反之
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        public static boolean isNeverAsked(Activity activity) {
            return !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.RECORD_AUDIO);
        }
    }

    /**
     * 文件读写权限 {@link Manifest.permission#READ_EXTERNAL_STORAGE} &
     * {@link Manifest.permission#WRITE_EXTERNAL_STORAGE}
     */
    public static class Storage {

        public static boolean check() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
            }
            return true;
        }

        /**
         * 请求文件读写权限
         *
         * @param activity    Activity
         * @param requestCode 请求码
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        public static void request(Activity activity, @IntRange(from = 0) int requestCode) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
        }

        /**
         * 检查文件读写权限, 如果检查未通过, 则自动请求权限
         *
         * @param activity    Activity
         * @param requestCode 请求码
         * @return 检查通过返回 true, 检查未通过返回 false
         */
        public static boolean checkOrRequest(Activity activity, @IntRange(from = 0) int requestCode) {
            if (check()) {
                return true;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                request(activity, requestCode);
            }
            return false;
        }

        /**
         * 检查 Activity 中 onRequestPermissionResult() 回调的结果, 是否获取到该权限
         *
         * @return 是否获取到该权限
         */
        public static boolean checkResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
            for (int i = 0; i < permissions.length; i++) {
                if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permissions[i])
                        || Manifest.permission.READ_EXTERNAL_STORAGE.equals(permissions[i])) {
                    return grantResults[i] == PackageManager.PERMISSION_GRANTED;
                }
            }
            return false;
        }

        /**
         * 检查该权限是否已被用户设置 "不再提醒" 该权限的请求窗口
         *
         * @return true: 不再提醒, 即无法再次弹出请求权限的窗口; false 反之
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        public static boolean isNeverAsked(Activity activity) {
            return !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }
}
