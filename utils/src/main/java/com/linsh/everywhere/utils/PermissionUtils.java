package com.linsh.everywhere.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: 权限相关
 * </pre>
 */
public class PermissionUtils {

    /**
     * 默认的请求码
     */
    public static final int REQUEST_CODE_PERMISSION = 101;

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
        return ContextCompat.checkSelfPermission(ApplicationUtils.getContext(), permission)
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
     * @param activity   Activity
     * @param permission 系统权限
     * @param listener   权限回调
     */
    public static void checkAndRequestPermission(Activity activity, String permission, @NonNull PermissionListener listener) {
        checkAndRequestPermissions(activity, new String[]{permission}, listener);
    }

    /**
     * 检查多个权限, 如果不通过, 将自动请求该系统权限
     *
     * @param activity    Activity
     * @param permissions 多个系统权限
     * @param listener    权限回调
     */
    public static void checkAndRequestPermissions(Activity activity, String[] permissions, @NonNull PermissionListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> list = new ArrayList<>();
            for (String permission : permissions) {
                if (checkPermission(permission)) {
                    listener.onGranted(permission);
                } else if (isPermissionNeverAsked(activity, permission)) {
                    listener.onDenied(permission, true);
                } else {
                    list.add(permission);
                }
            }
            if (list.size() > 0) {
                requestPermissions(activity, ArrayUtils.toStringArray(list), listener);
            }
        } else {
            for (String permission : permissions) {
                listener.onBeforeAndroidM(permission);
            }
        }
    }

    /**
     * 申请系统权限
     *
     * @param activity   Activity
     * @param permission 系统权限
     * @param listener   权限回调
     */
    public static void requestPermission(Activity activity, String permission, @Nullable PermissionListener listener) {
        requestPermissions(activity, new String[]{permission}, listener);
    }

    /**
     * 申请duoge系统权限
     *
     * @param activity    Activity
     * @param permissions 系统权限
     * @param listener    权限回调
     */
    public static void requestPermissions(Activity activity, String[] permissions, @Nullable PermissionListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE_PERMISSION);
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
        if (REQUEST_CODE_PERMISSION == requestCode) {
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
    }

    public interface PermissionListener {

        void onGranted(String permission);

        void onDenied(String permission, boolean isNeverAsked);

        /**
         * 当前系统版本在为 Android M (6.0) 以下, 无法得知是否拥有该权限, 需要实际运行才可得知
         */
        void onBeforeAndroidM(String permission);
    }

    //==================================== 权限组, 根据不同的权限组来自定义权限的获取 ======================================//

    public static class Calendar {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return PermissionUtils.checkPermission(Manifest.permission.READ_CALENDAR)
                        || PermissionUtils.checkPermission(Manifest.permission.WRITE_CALENDAR);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        private static boolean checkPermissionBeforeAndroidM() {
            // TODO: 17/6/17
            return true;
        }
    }

    public static class Camera {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return PermissionUtils.checkPermission(Manifest.permission.CAMERA);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        /**
         * @return 返回 false 不一定是没有权限, 也有一定的可能是摄像头被占用的情况(可能性较低)
         */
        public static boolean checkPermissionBeforeAndroidM() {
            android.hardware.Camera cam = null;
            try {
                cam = android.hardware.Camera.open();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (cam != null) {
                        cam.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }
    }

    public static class Contacts {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return PermissionUtils.checkPermission(Manifest.permission.READ_CONTACTS)
                        || PermissionUtils.checkPermission(Manifest.permission.WRITE_CONTACTS)
                        || PermissionUtils.checkPermission(Manifest.permission.GET_ACCOUNTS);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        private static boolean checkPermissionBeforeAndroidM() {
            // TODO: 17/6/17
            return true;
        }
    }

    public static class Location {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return PermissionUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        || PermissionUtils.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        private static boolean checkPermissionBeforeAndroidM() {
            // TODO: 17/6/17
            return true;
        }
    }

    public static class Microphone {
        private static final int RECORDER_SAMPLERATE = 8000;
        private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
        private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return PermissionUtils.checkPermission(Manifest.permission.RECORD_AUDIO);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        /**
         * @return 返回 false 不一定是没有权限, 也有一定的可能是麦克风被占用的情况(可能性较低)
         */
        public static boolean checkPermissionBeforeAndroidM() {
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
    }

    public static class Phone {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return PermissionUtils.checkPermission(Manifest.permission.READ_PHONE_STATE)
                        || PermissionUtils.checkPermission(Manifest.permission.CALL_PHONE)
                        || PermissionUtils.checkPermission(Manifest.permission.READ_CALL_LOG)
                        || PermissionUtils.checkPermission(Manifest.permission.WRITE_CALL_LOG)
                        || PermissionUtils.checkPermission(Manifest.permission.ADD_VOICEMAIL)
                        || PermissionUtils.checkPermission(Manifest.permission.USE_SIP)
                        || PermissionUtils.checkPermission(Manifest.permission.PROCESS_OUTGOING_CALLS);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        private static boolean checkPermissionBeforeAndroidM() {
            // TODO: 17/6/17
            return true;
        }
    }

    public static class Sensors {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return PermissionUtils.checkPermission(Manifest.permission.BODY_SENSORS);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        private static boolean checkPermissionBeforeAndroidM() {
            // TODO: 17/6/17
            return true;
        }

        /**
         * 检查相机硬件是否可用
         */
        public static boolean checkHardware() {
            return ApplicationUtils.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
        }
    }

    public static class Sms {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return PermissionUtils.checkPermission(Manifest.permission.SEND_SMS)
                        || PermissionUtils.checkPermission(Manifest.permission.RECEIVE_SMS)
                        || PermissionUtils.checkPermission(Manifest.permission.READ_SMS)
                        || PermissionUtils.checkPermission(Manifest.permission.RECEIVE_WAP_PUSH)
                        || PermissionUtils.checkPermission(Manifest.permission.RECEIVE_MMS);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        private static boolean checkPermissionBeforeAndroidM() {
            // TODO: 17/6/17
            return true;
        }
    }

    public static class Storage {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return PermissionUtils.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        || PermissionUtils.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        public static boolean checkPermissionBeforeAndroidM() {
            return true;
        }
    }

}
