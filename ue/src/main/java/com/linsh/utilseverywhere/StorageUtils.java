package com.linsh.utilseverywhere;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2021/01/25
 *    desc   : 储存相关工具类
 * </pre>
 */
public class StorageUtils {

    private StorageUtils() {
    }

    /**
     * 获取指定路径的储存容量
     *
     * @return 储存容量, 单位: B
     */
    public static long getStorageSize(String path) {
        StatFs statFs = new StatFs(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return statFs.getBlockCountLong() * statFs.getBlockSizeLong();
        } else {
            return statFs.getBlockCount() * statFs.getBlockSize();
        }
    }

    /**
     * 获取格式化的指定路径的储存容量
     *
     * @return 储存容量
     */
    public static String getFormattedStorageSize(String path) {
        return Formatter.formatFileSize(ContextUtils.get(), getStorageSize(path));
    }

    /**
     * 获取指定路径的可用容量
     *
     * @return 可用容量, 单位: B
     */
    public static long getAvailableStorageSize(String path) {
        StatFs statFs = new StatFs(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
        } else {
            return statFs.getAvailableBlocks() * statFs.getBlockSize();
        }
    }

    /**
     * 获取格式化的指定路径的可用容量
     *
     * @return 可用容量
     */
    public static String getFormattedAvailableStorageSize(String path) {
        return Formatter.formatFileSize(ContextUtils.get(), getAvailableStorageSize(path));
    }

    /**
     * 获取外部共享储存的储存容量
     *
     * @return 储存容量, 单位: B
     */
    public static long getExternalStorageSize() {
        return getStorageSize(getExternalStorageDirectory().getPath());
    }

    /**
     * 获取外部共享储存的可用容量
     *
     * @return 可用容量, 单位: B
     */
    public static long getAvailableExternalStorageSize() {
        return getAvailableStorageSize(getExternalStorageDirectory().getPath());
    }

    /**
     * 获取外部共享储存 (一般为SD卡) 的状态
     *
     * @return 以下其中一种: <br/>
     * {@link Environment#MEDIA_UNKNOWN},
     * {@link Environment#MEDIA_REMOVED},
     * {@link Environment#MEDIA_UNMOUNTED},
     * {@link Environment#MEDIA_CHECKING},
     * {@link Environment#MEDIA_NOFS},
     * {@link Environment#MEDIA_MOUNTED},
     * {@link Environment#MEDIA_MOUNTED_READ_ONLY},
     * {@link Environment#MEDIA_SHARED},
     * {@link Environment#MEDIA_BAD_REMOVAL},
     * {@link Environment#MEDIA_UNMOUNTABLE}.
     */
    @NonNull
    public static String getExternalStorageState() {
        return Environment.getExternalStorageState();
    }

    /**
     * 判断外部共享储存是否可用
     *
     * @return 是否可用
     */
    public static boolean isExternalStorageAvailable() {
        return getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取外部共享储存根目录
     *
     * @return 根目录文件对象
     */
    public static File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取 Download 文件夹
     *
     * @return Download 文件夹对象
     */
    public static File getDownloadDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    /**
     * 获取 DCIM 文件夹
     *
     * @return DCIM 文件夹对象
     */
    public static File getDCIMDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }

    /**
     * 获取 Movies 文件夹
     *
     * @return Movies 文件夹对象
     */
    public static File getMoviesDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    }

    /**
     * 获取 Music 文件夹
     *
     * @return Music 文件夹对象
     */
    public static File getMusicDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
    }

    /**
     * 获取 Pictures 文件夹
     *
     * @return Pictures 文件夹对象
     */
    public static File getPicturesDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    }

    /**
     * 获取 Documents 文件夹
     *
     * @return Documents 文件夹对象
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static File getDocumentsDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    }

    /**
     * 获取应用的缓存文件夹
     * <p>
     * 一般存放临时缓存数据
     *
     * @return 应用缓存文件夹对象
     */
    public static File getAppCacheDir() {
        return ContextUtils.get().getCacheDir();
    }

    /**
     * 获取外部共享储存中的应用的缓存文件夹
     * <p>
     * 一般存放临时缓存数据
     *
     * @return 应用缓存文件夹对象
     */
    public static File getExternalCacheDir() {
        return ContextUtils.get().getExternalCacheDir();
    }

    /**
     * 获取应用的文件缓存文件夹
     * <p>
     * 一般放一些长时间保存的数据
     *
     * @return 应用文件缓存文件夹对象
     */
    public static File getAppFilesDir() {
        return ContextUtils.get().getFilesDir();
    }

    /**
     * 获取外部共享储存中的应用的文件缓存文件夹
     * <p>
     * 一般放一些长时间保存的数据
     *
     * @return 应用文件缓存文件夹对象
     */
    public static File getExternalFilesDir() {
        return ContextUtils.get().getExternalFilesDir(null);
    }

    /**
     * 检查外置储存权限
     *
     * @return 是否有外置储存权限
     */
    public static boolean checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return true;
        }
        return ContextCompat.checkSelfPermission(ContextUtils.get(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ContextUtils.get(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 申请外置储存权限
     *
     * @param activity    Activity
     * @param requestCode 请求码
     */
    public static void requestPermission(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, requestCode);
        }
    }

    /**
     * 检查外置储存权限, 如果没有权限则进行申请
     *
     * @param activity    Activity
     * @param requestCode 请求码
     * @return 是否有外置储存权限
     */
    public static boolean checkOrRequestPermission(Activity activity, int requestCode) {
        if (checkPermission()) {
            return true;
        }
        requestPermission(activity, requestCode);
        return false;
    }
}
