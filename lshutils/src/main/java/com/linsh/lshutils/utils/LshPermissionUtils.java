package com.linsh.lshutils.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
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
     * 注: Android M 以下系统检查的是清单文件而不是用户决定是否授予的权限
     */
    public static boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(LshApplicationUtils.getContext(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, String permission) {
        requestPermissions(activity, new String[]{permission}, 0, null);
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

    //==================================== 权限组, 根据不同的权限组来自定义权限的获取 ======================================//

    private static class Calendar {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return LshPermissionUtils.checkPermission(Manifest.permission.READ_CALENDAR)
                        || LshPermissionUtils.checkPermission(Manifest.permission.WRITE_CALENDAR);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        public static boolean checkPermissionBeforeAndroidM() {
            // TODO: 17/6/17
            return false;
        }
    }

    public static class Camera {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return LshPermissionUtils.checkPermission(Manifest.permission.CAMERA);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        public static boolean checkPermissionBeforeAndroidM() {
            android.hardware.Camera cam = null;
            try {
                cam = android.hardware.Camera.open();
                cam.release();
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

    private static class Contacts {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return LshPermissionUtils.checkPermission(Manifest.permission.READ_CONTACTS)
                        || LshPermissionUtils.checkPermission(Manifest.permission.WRITE_CONTACTS)
                        || LshPermissionUtils.checkPermission(Manifest.permission.GET_ACCOUNTS);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        public static boolean checkPermissionBeforeAndroidM() {
            // TODO: 17/6/17
            return false;
        }
    }

    private static class Location {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return LshPermissionUtils.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        || LshPermissionUtils.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        public static boolean checkPermissionBeforeAndroidM() {
            // TODO: 17/6/17
            return false;
        }
    }

    public static class Microphone {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return LshPermissionUtils.checkPermission(Manifest.permission.RECORD_AUDIO);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

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

    private static class Phone {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return LshPermissionUtils.checkPermission(Manifest.permission.READ_PHONE_STATE)
                        || LshPermissionUtils.checkPermission(Manifest.permission.CALL_PHONE)
                        || LshPermissionUtils.checkPermission(Manifest.permission.READ_CALL_LOG)
                        || LshPermissionUtils.checkPermission(Manifest.permission.WRITE_CALL_LOG)
                        || LshPermissionUtils.checkPermission(Manifest.permission.ADD_VOICEMAIL)
                        || LshPermissionUtils.checkPermission(Manifest.permission.USE_SIP)
                        || LshPermissionUtils.checkPermission(Manifest.permission.PROCESS_OUTGOING_CALLS);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        public static boolean checkPermissionBeforeAndroidM() {
            // TODO: 17/6/17
            return false;
        }
    }

    private static class Sensors {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return LshPermissionUtils.checkPermission(Manifest.permission.BODY_SENSORS);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        public static boolean checkPermissionBeforeAndroidM() {
            // TODO: 17/6/17
            return false;
        }

        /**
         * 检查相机硬件是否可用
         */
        public static boolean checkHardware() {
            return LshApplicationUtils.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
        }
    }

    private static class Sms {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return LshPermissionUtils.checkPermission(Manifest.permission.SEND_SMS)
                        || LshPermissionUtils.checkPermission(Manifest.permission.RECEIVE_SMS)
                        || LshPermissionUtils.checkPermission(Manifest.permission.READ_SMS)
                        || LshPermissionUtils.checkPermission(Manifest.permission.RECEIVE_WAP_PUSH)
                        || LshPermissionUtils.checkPermission(Manifest.permission.RECEIVE_MMS);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        public static boolean checkPermissionBeforeAndroidM() {
            // TODO: 17/6/17
            return false;
        }
    }

    public static class Storage {

        public static boolean checkPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return LshPermissionUtils.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        || LshPermissionUtils.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                return checkPermissionBeforeAndroidM();
            }
        }

        public static boolean checkPermissionBeforeAndroidM() {
            return true;
        }
    }

}
