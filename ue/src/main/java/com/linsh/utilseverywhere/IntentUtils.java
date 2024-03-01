package com.linsh.utilseverywhere;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import androidx.annotation.RequiresPermission;
import androidx.fragment.app.Fragment;

import com.linsh.utilseverywhere.tools.IntentBuilder;

import java.io.File;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/10
 *    desc   : 工具类: Intent 意图相关
 *
 *             注: 部分 API 直接参考或使用 https://github.com/Blankj/AndroidUtilCode 中 IntentUtils 类里面的方法
 * </pre>
 */
public class IntentUtils {

    private IntentUtils() {
    }

    private static Context getContext() {
        return ContextUtils.get();
    }
    //================================================ Intent 意图跳转相关 ================================================//

    /**
     * 创建 IntentBuilder 构建 Intent
     *
     * @return IntentBuilder
     */
    public static IntentBuilder buildIntent() {
        return new IntentBuilder();
    }

    /**
     * 创建 IntentBuilder 构建 Intent
     *
     * @param activity 需要启动的 Activity 类
     * @return IntentBuilder
     */
    public static IntentBuilder buildIntent(Class<?> activity) {
        return new IntentBuilder(activity);
    }

    /**
     * 启动 Activity
     *
     * @param context  当前 Activity
     * @param activity 需要启动的 Activity 类
     */
    public static void startActivity(Activity context, Class<?> activity) {
        context.startActivity(new Intent(context, activity));
    }

    /**
     * 启动 Activity, 并传递 String 类型数据
     *
     * @param context  当前 Activity
     * @param activity 需要启动的 Activity 类
     * @param data     需要传递的 String 类型数据, 获取数据时使用 {@link IntentBuilder#getStringExtra(int)} , 传入对应的 index 即可
     */
    public static void startActivityWithData(Activity context, Class<?> activity, String... data) {
        IntentBuilder build = new IntentBuilder(activity);
        for (int i = 0; i < data.length; i++) {
            build.putExtra(data[i], i);
        }
        build.startActivity(context);
    }

    //================================================ Intent 意图的获取 ================================================//

    /**
     * 获取分享文本的意图
     *
     * @param content 文本内容
     * @return 意图
     */
    public static Intent getShareTextIntent(String content) {
        return new Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, content)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「桌面主页」的意图
     *
     * @return 意图
     */
    public static Intent getHomeIntent() {
        return new Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_HOME);
    }

    /**
     * 获取跳转「选择文件」的意图
     *
     * @return 意图
     */
    public static Intent getPickFileIntent() {
        return getPickIntent("file/*");
    }

    /**
     * 获取跳转「选择文件」的意图, 指定文件扩展名
     *
     * @param fileExtension 文件扩展名
     * @return 意图
     */
    public static Intent getPickFileIntent(String fileExtension) {
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        if (type == null) {
            return null;
        }
        return getPickIntent(type);
    }

    /**
     * 获取跳转「选择图片」的意图
     *
     * @return 意图
     */
    public static Intent getPickPhotoIntent() {
        return getPickIntent("image/*");
    }

    /**
     * 获取跳转「选择视频」的意图
     *
     * @return 意图
     */
    public static Intent getPickVideoIntent() {
        return getPickIntent("video/*");
    }

    /**
     * 获取跳转「选择音频」的意图
     *
     * @return 意图
     */
    public static Intent getPickAudioIntent() {
        return getPickIntent("audio/*");
    }

    /**
     * 获取跳转「选择...」的意图
     *
     * @param type mimeType 类型
     * @return 意图
     */
    public static Intent getPickIntent(String type) {
        return new Intent(Intent.ACTION_GET_CONTENT)
                .setType(type)
                .addCategory(Intent.CATEGORY_OPENABLE);
    }

    /**
     * 获取跳转「系统相机」的意图
     *
     * @param outputFile 拍摄图片的输入文件对象
     * @return 意图
     */
    public static Intent getTakePhotoIntent(File outputFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProviderUtils.getUriForFile(outputFile));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
        }
        return intent;
    }

    /**
     * 获取跳转「系统剪裁」的意图
     *
     * @param inputFile  剪裁图片文件
     * @param outputFile 输出图片文件
     * @param aspectX    输出图片宽高比中的宽
     * @param aspectY    输出图片宽高比中的高
     * @param outputX    输出图片的宽
     * @param outputY    输出图片的高
     * @return 意图
     */
    public static Intent getCropPhotoIntent(File inputFile, File outputFile, int aspectX, int aspectY, int outputX, int outputY) {
        Uri inputUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            inputUri = FileProviderUtils.getUriForFile(inputFile);
        } else {
            inputUri = Uri.fromFile(inputFile);
        }
        return getCropPhotoIntent(inputUri, Uri.fromFile(outputFile), aspectX, aspectY, outputX, outputY);
    }


    /**
     * 获取跳转「系统剪裁」的意图
     *
     * @param inputUri  剪裁图片文件的 Uri
     * @param outputUri 输出图片文件的 Uri
     * @param aspectX   输出图片宽高比中的宽
     * @param aspectY   输出图片宽高比中的高
     * @param outputX   输出图片的宽
     * @param outputY   输出图片的高
     * @return 意图
     */
    public static Intent getCropPhotoIntent(Uri inputUri, Uri outputUri, int aspectX, int aspectY, int outputX, int outputY) {
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
        if (outputUri.getPath().endsWith(".jpg") || outputUri.getPath().endsWith(".jpeg")) {
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        } else if (outputUri.getPath().endsWith(".png") || inputUri.getPath().endsWith(".png")) {
            intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        } else {
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        }
        intent.putExtra("return-data", false);
        return intent;
    }

    /**
     * 获取跳转「安装应用」的意图
     * <p>
     * 注:
     * Android O 以上需要注册权限 {@link Manifest.permission#REQUEST_INSTALL_PACKAGES}
     * <p>
     * 判断权限:
     * {@link PackageManager#canRequestPackageInstalls()}
     *
     * @param apkFile APK 文件
     * @return 意图
     */
    public static Intent getInstallAppIntent(File apkFile) {
        if (apkFile == null || !apkFile.exists() || !apkFile.isFile())
            return null;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            uri = FileProviderUtils.getUriForFile(apkFile);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(apkFile);
        }
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * 获取跳转「卸载应用」的意图
     *
     * @param packageName 应用包名
     * @return 意图
     */
    public static Intent getUninstallAppIntent(String packageName) {
        if (TextUtils.isEmpty(packageName)) return null;

        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * 获取跳转「应用」的意图
     *
     * @param packageName 应用包名
     * @return 意图
     */
    public static Intent getLaunchAppIntent(String packageName) {
        return getContext().getPackageManager().getLaunchIntentForPackage(packageName);
    }


    /**
     * 获取跳转「应用组件」的意图
     *
     * @param packageName 应用包名
     * @param className   应用组件的类名
     * @return 意图
     */
    public static Intent getComponentIntent(String packageName, String className) {
        return new Intent(Intent.ACTION_VIEW)
                .setComponent(new ComponentName(packageName, className))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「拨号界面」的意图
     *
     * @return 意图
     */
    public static Intent getDialIntent() {
        return new Intent(Intent.ACTION_DIAL)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「拨号界面」的意图
     *
     * @param phoneNumber 电话号码
     * @return 意图
     */
    public static Intent getDialIntent(String phoneNumber) {
        return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「拨打电话」的意图, 即直接拨打电话
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
     *
     * @param phoneNumber 电话号码
     * @return 意图
     */
    @RequiresPermission(value = Manifest.permission.CALL_PHONE)
    public static Intent getCallIntent(String phoneNumber) {
        return new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「发送短信」的意图
     *
     * @param phoneNumber 电话号码
     * @param content     预设内容
     * @return 意图
     */
    public static Intent getSendSmsIntent(String phoneNumber, String content) {
        return new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber))
                .putExtra("sms_body", content)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「联系人」的意图
     *
     * @return 意图
     */
    public static Intent getContactsIntent() {
        return new Intent(Intent.ACTION_VIEW)
                .setData(ContactsContract.Contacts.CONTENT_URI);
    }

    /**
     * 获取跳转「联系人详情」的意图
     *
     * @param contactId 联系人的 contactId
     * @param lookupKey 联系人的 lookupKey
     * @return 意图
     */
    public static Intent getContactDetailIntent(long contactId, String lookupKey) {
        Uri data = ContactsContract.Contacts.getLookupUri(contactId, lookupKey);
        return new Intent(Intent.ACTION_VIEW)
                .setDataAndType(data, ContactsContract.Contacts.CONTENT_ITEM_TYPE);
    }

    /**
     * 获取跳转「浏览器」的意图
     *
     * @param url 网址
     * @return 意图
     */
    public static Intent getBrowserIntent(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }

    /**
     * 获取跳转「设置界面」的意图
     *
     * @return 意图
     */
    public static Intent getSettingIntent() {
        return new Intent(Settings.ACTION_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「应用详情」的意图
     *
     * @param packageName 应用包名
     * @return 意图
     */
    public static Intent getAppDetailsSettingsIntent(String packageName) {
        return new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + packageName))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「应用列表」的意图
     *
     * @return 意图
     */
    public static Intent getAppsIntent() {
        return new Intent(Settings.ACTION_APPLICATION_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「 Wifi 设置」的意图
     *
     * @return 意图
     */
    public static Intent getWifiSettingIntent() {
        return new Intent(Settings.ACTION_WIFI_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「网络设置」的意图
     *
     * @return 意图
     */
    public static Intent getWirelessSettingIntent() {
        return new Intent(Settings.ACTION_WIRELESS_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳转「无障碍服务设置」的意图
     *
     * @return 意图
     */
    public static Intent getAccessibilitySettingIntent() {
        return new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    //================================================ Intent 意图的跳转 ================================================//

    /**
     * 跳转:「安装应用」界面
     * <p>
     * 注:
     * Android O 以上需要注册权限 {@link Manifest.permission#REQUEST_INSTALL_PACKAGES}
     * <p>
     * 判断权限:
     * {@link PackageManager#canRequestPackageInstalls()}
     *
     * @param apkFile APK 文件
     */
    public static void gotoInstallApp(File apkFile) {
        startActivity(getInstallAppIntent(apkFile));
    }

    /**
     * 跳转:「卸载应用」界面
     *
     * @param packageName 应用包名
     */
    public static void gotoUninstallApp(String packageName) {
        startActivity(getUninstallAppIntent(packageName));
    }

    /**
     * 跳转: 指定应用
     *
     * @param packageName 应用包名
     */
    public static void gotoApp(String packageName) {
        if (TextUtils.isEmpty(packageName))
            return;
        startActivity(getLaunchAppIntent(packageName));
    }

    /**
     * 跳转:「系统桌面」
     */
    public static void gotoHome() {
        startActivity(getHomeIntent());
    }

    /**
     * 跳转:「拨号」界面
     *
     * @param phoneNumber 电话号码
     */
    public static void gotoDial(String phoneNumber) {
        startActivity(getDialIntent(phoneNumber));
    }

    /**
     * 执行「打电话」
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
     *
     * @param phoneNumber 电话号码
     */
    @RequiresPermission(value = Manifest.permission.CALL_PHONE)
    public static void gotoCallPhone(String phoneNumber) {
        startActivity(getCallIntent(phoneNumber));
    }

    /**
     * 跳转:「发送短信」界面
     *
     * @param phoneNumber 电话号码
     * @param content     短信内容
     */
    public static void gotoSendSms(String phoneNumber, String content) {
        startActivity(getSendSmsIntent(phoneNumber, content));
    }

    /**
     * 跳转:「联系人」界面
     */
    public static void gotoContacts() {
        startActivity(getContactsIntent());
    }

    /**
     * 跳转:「联系人详情」界面
     *
     * @param contactId 联系人的 contactId
     * @param lookupKey 联系人的 lookupKey
     */
    public static void gotoContactDetail(long contactId, String lookupKey) {
        startActivity(getContactDetailIntent(contactId, lookupKey));
    }

    /**
     * 跳转:「系统选择文件」界面
     *
     * @param activity    Activity
     * @param requestCode 请求码
     */
    public static void gotoPickFile(Activity activity, int requestCode) {
        startActivityForResult(activity, null, getPickFileIntent(), requestCode);
    }

    /**
     * 跳转:「系统选择文件」界面
     *
     * @param fragment    Fragment
     * @param requestCode 请求码
     */
    public static void gotoPickFile(Fragment fragment, int requestCode) {
        startActivityForResult(null, fragment, getPickFileIntent(), requestCode);
    }

    /**
     * 跳转:「系统选择文件」界面
     *
     * @param activity      Activity
     * @param requestCode   请求码
     * @param fileExtension 文件扩展名
     */
    public static void gotoPickFile(Activity activity, int requestCode, String fileExtension) {
        startActivityForResult(activity, null, getPickFileIntent(fileExtension), requestCode);
    }

    /**
     * 跳转:「系统选择图片」界面
     *
     * @param activity    Activity
     * @param requestCode 请求码
     */
    public static void gotoPickPhoto(Activity activity, int requestCode) {
        startActivityForResult(activity, null, getPickPhotoIntent(), requestCode);
    }

    /**
     * 跳转:「系统选择图片」界面
     *
     * @param fragment    Fragment
     * @param requestCode 请求码
     */
    public static void gotoPickPhoto(Fragment fragment, int requestCode) {
        startActivityForResult(null, fragment, getPickPhotoIntent(), requestCode);
    }

    /**
     * 跳转:「系统选择视频」界面
     *
     * @param activity    Activity
     * @param requestCode 请求码
     */
    public static void gotoPickVideo(Activity activity, int requestCode) {
        startActivityForResult(activity, null, getPickVideoIntent(), requestCode);
    }

    /**
     * 跳转:「系统选择视频」界面
     *
     * @param fragment    Fragment
     * @param requestCode 请求码
     */
    public static void gotoPickVideo(Fragment fragment, int requestCode) {
        startActivityForResult(null, fragment, getPickVideoIntent(), requestCode);
    }

    /**
     * 跳转:「系统选择音频」界面
     *
     * @param activity    Activity
     * @param requestCode 请求码
     */
    public static void gotoPickAudio(Activity activity, int requestCode) {
        startActivityForResult(activity, null, getPickAudioIntent(), requestCode);
    }

    /**
     * 跳转:「系统选择音频」界面
     *
     * @param fragment    Fragment
     * @param requestCode 请求码
     */
    public static void gotoPickAudio(Fragment fragment, int requestCode) {
        startActivityForResult(null, fragment, getPickAudioIntent(), requestCode);
    }

    /**
     * 跳转:「系统拍照」界面
     *
     * @param activity    Activity
     * @param requestCode 请求码
     * @param outputFile  拍摄照片的输出文件
     */
    public static void gotoTakePhoto(Activity activity, int requestCode, File outputFile) {
        startActivityForResult(activity, null, getTakePhotoIntent(outputFile), requestCode);
    }

    /**
     * 跳转:「系统拍照」界面
     *
     * @param fragment    Fragment
     * @param requestCode 请求码
     * @param outputFile  拍摄照片的输出文件
     */
    public static void gotoTakePhoto(Fragment fragment, int requestCode, File outputFile) {
        startActivityForResult(null, fragment, getTakePhotoIntent(outputFile), requestCode);
    }

    /**
     * 跳转:「系统剪裁」界面
     *
     * @param activity    Activity
     * @param requestCode 请求码
     * @param inputFile   剪裁图片文件
     * @param outputFile  输出图片文件
     * @param aspectX     输出图片宽高比中的宽
     * @param aspectY     输出图片宽高比中的高
     * @param outputX     输出图片的宽
     */
    public static void gotoCropPhoto(Activity activity, int requestCode, File inputFile, File outputFile,
                                     int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = getCropPhotoIntent(inputFile, outputFile, aspectX, aspectY, outputX, outputY);
        startActivityForResult(activity, null, intent, requestCode);
    }

    /**
     * 跳转:「系统剪裁」界面
     *
     * @param fragment    Fragment
     * @param requestCode 请求码
     * @param inputFile   剪裁图片文件
     * @param outputFile  输出图片文件
     * @param aspectX     输出图片宽高比中的宽
     * @param aspectY     输出图片宽高比中的高
     * @param outputX     输出图片的宽
     */
    public static void gotoCropPhoto(Fragment fragment, int requestCode, File inputFile, File outputFile,
                                     int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = getCropPhotoIntent(inputFile, outputFile, aspectX, aspectY, outputX, outputY);
        startActivityForResult(null, fragment, intent, requestCode);
    }

    /**
     * 跳转:「系统剪裁」界面
     *
     * @param activity    Activity
     * @param requestCode 请求码
     * @param inputUri    剪裁图片文件的 Uri
     * @param outputUri   输出图片文件的 Uri
     * @param aspectX     输出图片宽高比中的宽
     * @param aspectY     输出图片宽高比中的高
     * @param outputX     输出图片的宽
     * @param outputY     输出图片的高
     */
    public static void gotoCropPhoto(Activity activity, int requestCode, Uri inputUri, Uri outputUri,
                                     int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = getCropPhotoIntent(inputUri, outputUri, aspectX, aspectY, outputX, outputY);
        startActivityForResult(activity, null, intent, requestCode);
    }

    /**
     * 跳转:「系统剪裁」界面
     *
     * @param fragment    Fragment
     * @param requestCode 请求码
     * @param inputUri    剪裁图片文件的 Uri
     * @param outputUri   输出图片文件的 Uri
     * @param aspectX     输出图片宽高比中的宽
     * @param aspectY     输出图片宽高比中的高
     * @param outputX     输出图片的宽
     * @param outputY     输出图片的高
     */
    public static void gotoCropPhoto(Fragment fragment, int requestCode, Uri inputUri, Uri outputUri,
                                     int aspectX, int aspectY, int outputX, int outputY) {
        Intent intent = getCropPhotoIntent(inputUri, outputUri, aspectX, aspectY, outputX, outputY);
        startActivityForResult(null, fragment, intent, requestCode);
    }

    /**
     * 跳转:「浏览器」界面
     *
     * @param url 网址
     */
    public static void gotoBrowser(String url) {
        startActivity(getBrowserIntent(url));
    }

    /**
     * 跳转:「设置」界面
     */
    public static void gotoSetting() {
        getContext().startActivity(getSettingIntent());
    }

    /**
     * 跳转:「应用详情」界面
     *
     * @param packageName 应用包名
     */
    public static void gotoAppDetailSetting(String packageName) {
        getContext().startActivity(getAppDetailsSettingsIntent(packageName));
    }

    /**
     * 跳转:「应用程序列表」界面
     */
    public static void gotoAppsSetting() {
        getContext().startActivity(getAppsIntent());
    }

    /**
     * 跳转:「Wifi列表」设置
     */
    public static void gotoWifiSetting() {
        getContext().startActivity(getWifiSettingIntent());
    }


    /**
     * 跳转:「飞行模式，无线网和网络设置」界面
     */
    public static void gotoWirelessSetting() {
        getContext().startActivity(getWirelessSettingIntent());
    }

    /**
     * 跳转:「无障碍设置」界面
     */
    public static void gotoAccessibilitySetting() {
        getContext().startActivity(getAccessibilitySettingIntent());
    }


    /**
     * 跳转界面
     */
    private static void startActivity(Intent intent) {
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }
    }

    /**
     * 跳转界面
     */
    private static void startActivity(Activity activity, Fragment fragment, Intent intent) {
        if (intent == null) return;
        if (activity != null) {
            activity.startActivity(intent);
        } else if (fragment != null) {
            fragment.startActivity(intent);
        }
    }

    /**
     * 跳转界面
     */
    private static void startActivityForResult(Activity activity, Fragment fragment, Intent intent, int requestCode) {
        if (intent == null) return;
        if (activity != null) {
            activity.startActivityForResult(intent, requestCode);
        } else if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        }
    }
}
