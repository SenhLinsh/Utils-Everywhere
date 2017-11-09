package com.linsh.lshutils.utils;

import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * Created by Senh Linsh on 17/7/6.
 */

public class LshCameraUtils {

    public static boolean checkCameraHardware() {
        if (LshApplicationUtils.getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean hasBackFacingCamera() {
        return checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    public static boolean hasFrontFacingCamera() {
        return checkCameraFacing(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }

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
