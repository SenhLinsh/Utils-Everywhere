package com.linsh.utilseverywhere;

import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: 摄像头相关
 *             API 包括检查摄像头、判断是否有前置或后置摄像头等
 * </pre>
 */
public class CameraUtils {

    private CameraUtils() {
    }

    /**
     * 检查摄像头硬件是否可用 (部分手机或 Pad 没有摄像头)
     *
     * @return true 为可用; false 为不可用
     */
    public static boolean checkCameraHardware() {
        if (ContextUtils.get().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否有后置摄像头
     */
    public static boolean hasBackFacingCamera() {
        return checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    /**
     * 判断是否有前置摄像头
     */
    public static boolean hasFrontFacingCamera() {
        return checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }

    /**
     * 判断是否有某朝向的摄像头
     *
     * @param facing 摄像头朝向, 前置或后置
     */
    private static boolean checkCameraFacing(final int facing) {
        final int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }
}
