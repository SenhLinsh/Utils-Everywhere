package com.linsh.utilseverywhere;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.linsh.utilseverywhere.module.CommandResult;

import java.util.Set;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/02/03
 *    desc   : 工具类: 执行 ADB 命令相关
 * </pre>
 */
public class ADBUtils {

    /**
     * 检测设备是否已 root
     *
     * @return 命令执行结果
     */
    public static boolean checkRoot() {
        CommandResult result = ShellUtils.execCmd("", true);
        return result.result != -1 && TextUtils.isEmpty(result.errorMsg);
    }

    //================================================ 应用管理 ================================================//

    /**
     * 安装 APK [需要 root 权限]
     *
     * @param path apk 文件路径
     * @return 命令执行结果
     */
    public static CommandResult installApk(String path) {
        return ShellUtils.execCmd("pm install " + path, true);
    }

    /**
     * 卸载应用 [需要 root 权限]
     *
     * @param packageName 应用包名
     * @return 命令执行结果
     */
    public static CommandResult uninstallApp(String packageName) {
        return ShellUtils.execCmd("pm uninstall " + packageName, true);
    }

    /**
     * 卸载应用 [需要 root 权限]
     *
     * @param packageName             应用包名
     * @param isRetainingDataAndCache 是否保留数据和缓存目录
     * @return 命令执行结果
     */
    public static CommandResult uninstallApp(String packageName, boolean isRetainingDataAndCache) {
        String option = isRetainingDataAndCache ? "-k " : "";
        return ShellUtils.execCmd("pm uninstall " + option + packageName, true);
    }

    /**
     * 清除应用数据与缓存 [不需要 root 权限]
     *
     * @param packageName 应用包名
     * @return 命令执行结果
     */
    public static CommandResult clearDataAndCache(String packageName) {
        return ShellUtils.execCmd("pm clear " + packageName, false);
    }

    /**
     * 查看前台 Activity [需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult showForegroundActivity() {
        return ShellUtils.execCmd("dumpsys activity activities | grep mFocusedActivity", true);
    }
//
//    /**
//     * 查看正在运行的 Services
//     * <p>
//     * 输出中包含很多信息，包括 Activity Resolver Table、Registered ContentProviders、包名、userId、
//     * 安装后的文件资源代码等路径、版本信息、权限信息和授予状态、签名版本信息等.
//     *
//     * @return 命令执行结果
//     */
//    public static CommandResult showRunningServices() {
//        return ShellUtils.execCmd("dumpsys activity services", true);
//    }

    /**
     * 查看正在运行的 Services [需要 root 权限]
     * <p>
     * <输出中包含很多信息，包括 Activity Resolver Table、Registered ContentProviders、包名、userId、
     * 安装后的文件资源代码等路径、版本信息、权限信息和授予状态、签名版本信息等。
     *
     * @param packageName 应用包名 (不一定要给出完整的包名, 可以输入部分包名进行匹配)
     * @return 命令执行结果
     */
    public static CommandResult showRunningServices(String packageName) {
        return ShellUtils.execCmd("dumpsys activity services " + packageName, true);
    }

    /**
     * 查看应用详细信息 [需要 root 权限]
     * <p>
     * 输出中包含很多信息，包括 Activity Resolver Table、Registered ContentProviders、包名、userId、
     * 安装后的文件资源代码等路径、版本信息、权限信息和授予状态、签名版本信息等
     *
     * @param packageName 应用包名
     * @return 命令执行结果
     */
    public static CommandResult showAppDetailInfo(String packageName) {
        return ShellUtils.execCmd("dumpsys package " + packageName, true);
    }

    /**
     * 查看应用安装路径 [不需要 root 权限]
     *
     * @param packageName 应用包名
     * @return 命令执行结果
     * <P>输出示例: package:/data/app/ecarx.weather-1.apk</P>
     */
    public static CommandResult showAppInstalledPath(String packageName) {
        return ShellUtils.execCmd("pm path " + packageName, false);
    }

    //================================================ 与应用交互 ================================================//

    /**
     * 启动应用 [需要 root 权限]
     *
     * @param packageName  应用包名
     * @param activityName Activity 全路径名
     * @return 命令执行结果
     */
    public static CommandResult startActivity(String packageName, String activityName) {
        return ShellUtils.execCmd(String.format("am start -n %s/%s", packageName, activityName), true);
    }

    /**
     * 启动应用 [需要 root 权限]
     *
     * @param intent Intent
     * @return 命令执行结果
     */
    public static CommandResult startActivity(@NonNull Intent intent) {
        String options = getIntentOptions(intent);
        if (options.length() == 0) return new CommandResult(-1, null, null);
        return ShellUtils.execCmd("am start" + options, true);
    }

    /**
     * 调起 Service [需要 root 权限]
     *
     * @param packageName  应用包名
     * @param activityName Service 全路径名
     * @return 命令执行结果
     */
    public static CommandResult startService(String packageName, String activityName) {
        return ShellUtils.execCmd(String.format("am startservice -n %s/%s ", packageName, activityName), true);
    }

    /**
     * 调起 Service [需要 root 权限]
     *
     * @param intent Intent
     * @return 命令执行结果
     */
    public static CommandResult startService(@NonNull Intent intent) {
        String options = getIntentOptions(intent);
        if (options.length() == 0) return new CommandResult(-1, null, null);
        return ShellUtils.execCmd("am startservice" + options, true);
    }

    /**
     * 停止 Service [需要 root 权限]
     *
     * @param packageName  应用包名
     * @param activityName Service 全路径名
     * @return 命令执行结果
     */
    public static CommandResult stopService(String packageName, String activityName) {
        return ShellUtils.execCmd(String.format("am stopservice -n %s/%s ", packageName, activityName), true);
    }

    /**
     * 停止 Service [需要 root 权限]
     *
     * @param intent Intent
     * @return 命令执行结果
     */
    public static CommandResult stopService(@NonNull Intent intent) {
        String options = getIntentOptions(intent);
        if (options.length() == 0) return new CommandResult(-1, null, null);
        return ShellUtils.execCmd("am stopservice" + options, true);
    }

    /**
     * 发送广播 [需要 root 权限]
     *
     * @param intent Intent
     * @return 命令执行结果
     */
    public static CommandResult broadcast(@NonNull Intent intent) {
        String options = getIntentOptions(intent);
        if (options.length() == 0) return new CommandResult(-1, null, null);
        return ShellUtils.execCmd("am broadcast" + options, true);
    }

    /**
     * 将 Intent 意图封装的数据格式化为命令行可以识别的意图字符串
     *
     * @param intent Intent
     * @return 命令执行结果
     */
    private static String getIntentOptions(@NonNull Intent intent) {
        StringBuilder options = new StringBuilder();
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action)) {
            options.append(" -a ").append(action);
        }
        Set<String> categories = intent.getCategories();
        if (categories != null) {
            for (String category : categories) {
                options.append(" -c ").append(category);
            }
        }
        ComponentName component = intent.getComponent();
        if (component != null) {
            String packageName = component.getPackageName();
            String shortClassName = component.getShortClassName();
            options.append(" -n ").append(packageName).append("/").append(shortClassName);
        }
        Bundle extras = intent.getExtras();
        if (extras != null && !extras.isEmpty()) {
            for (String key : extras.keySet()) {
                Object value = extras.get(key);
                if (value instanceof String) {
                    options.append(" --es ").append(value);
                } else if (value instanceof Boolean) {
                    options.append(" --ez ").append(value);
                } else if (value instanceof Integer) {
                    options.append(" --ei ").append(value);
                } else if (value instanceof Long) {
                    options.append(" --el ").append(value);
                } else if (value instanceof Float) {
                    options.append(" --ef ").append(value);
                } else if (value instanceof Double) {
                    options.append(" --ed ").append(value);
                } else if (value instanceof int[]) {
                    int[] array = (int[]) value;
                    for (int i = 0; i < array.length; i++) {
                        int item = array[i];
                        if (i == 0) options.append(" --eia ").append(item);
                        else {
                            if (i == 1) options.append("[");
                            options.append(",").append(item);
                            if (i == array.length - 1) options.append("]");
                        }
                    }
                } else if (value instanceof long[]) {
                    long[] array = (long[]) value;
                    for (int i = 0; i < array.length; i++) {
                        long item = array[i];
                        if (i == 0) options.append(" --ela ").append(item);
                        else {
                            if (i == 1) options.append("[");
                            options.append(",").append(item);
                            if (i == array.length - 1) options.append("]");
                        }
                    }
                }
            }
        }
        return options.toString();
    }

    /**
     * 强制停止应用 [需要 root 权限]
     *
     * @param packageName 应用包名
     * @return 命令执行结果
     */
    public static CommandResult forceStopApp(String packageName) {
        return ShellUtils.execCmd("am force-stop " + packageName, true);
    }

    /**
     * 收紧内存 [需要 root 权限]
     *
     * @param pid   进程 ID
     * @param level HIDDEN、RUNNING_MODERATE、BACKGROUND、 RUNNING_LOW、MODERATE、RUNNING_CRITICAL、COMPLETE
     * @return 命令执行结果
     */
    public static CommandResult sendTrimMemory(String pid, String level) {
        return ShellUtils.execCmd(String.format("am send-trim-memory %s %s", pid, level), true);
    }

    //================================================ 文件管理 ================================================//

    /**
     * 复制文件 [需要 root 权限]
     *
     * @param srcPath  原文件路径
     * @param destPath 目标文件路径
     * @return 命令执行结果
     */
    public static CommandResult copyFile(String srcPath, String destPath) {
        return ShellUtils.execCmd(String.format("cp %s %s", srcPath, destPath), true);
    }

    //================================================ 模拟按键/输入 ================================================//
    /*
        Usage: input [<source>] <command> [<arg>...]

        The sources are:
              mouse
              keyboard
              joystick
              touchnavigation
              touchpad
              trackball
              stylus
              dpad
              gesture
              touchscreen
              gamepad

        The commands and default sources are:
              text <string> (Default: touchscreen)
              keyevent [--longpress] <key code number or name> ... (Default: keyboard)
              tap <x> <y> (Default: touchscreen)
              swipe <x1> <y1> <x2> <y2> [duration(ms)] (Default: touchscreen)
              press (Default: trackball)
    */

    /**
     * 模拟按键 [需要 root 权限]
     *
     * @param keycode 完整的 keycode 见 {@link KeyEvent}.KEYCODE_XXX
     *                <P>一下列举常用的一些 KeyEvent</P>
     *                <ul>
     *                <li>{@link KeyEvent#KEYCODE_HOME}                 Home 键</li>
     *                <li>{@link KeyEvent#KEYCODE_BACK}                 返回键</li>
     *                <li>{@link KeyEvent#KEYCODE_VOLUME_UP}            增加音量</li>
     *                <li>{@link KeyEvent#KEYCODE_VOLUME_DOWN}          降低音量</li>
     *                <li>{@link KeyEvent#KEYCODE_POWER}                电源键</li>
     *                <li>{@link KeyEvent#KEYCODE_CAMERA}               拍照（需要在相机应用里）</li>
     *                <li>{@link KeyEvent#KEYCODE_EXPLORER}             打开浏览器</li>
     *                <li>{@link KeyEvent#KEYCODE_MENU}                 菜单键</li>
     *                <li>{@link KeyEvent#KEYCODE_MEDIA_PLAY_PAUSE}     播放/暂停</li>
     *                <li>{@link KeyEvent#KEYCODE_MEDIA_STOP}           停止播放</li>
     *                <li>{@link KeyEvent#KEYCODE_MEDIA_NEXT}           播放下一首</li>
     *                <li>{@link KeyEvent#KEYCODE_MEDIA_PREVIOUS}       播放上一首</li>
     *                <li>{@link KeyEvent#KEYCODE_MOVE_HOME}            移动光标到行首或列表顶部</li>
     *                <li>{@link KeyEvent#KEYCODE_MOVE_END}             移动光标到行末或列表底部</li>
     *                <li>{@link KeyEvent#KEYCODE_MEDIA_PLAY}           恢复播放</li>
     *                <li>{@link KeyEvent#KEYCODE_MEDIA_PAUSE}          暂停播放</li>
     *                <li>{@link KeyEvent#KEYCODE_VOLUME_MUTE}          静音</li>
     *                <li>{@link KeyEvent#KEYCODE_SETTINGS}             打开系统设置</li>
     *                <li>{@link KeyEvent#KEYCODE_APP_SWITCH}           切换应用</li>
     *                <li>{@link KeyEvent#KEYCODE_CONTACTS}             打开联系人</li>
     *                <li>{@link KeyEvent#KEYCODE_CALENDAR}             打开日历</li>
     *                <li>{@link KeyEvent#KEYCODE_MUSIC}                打开音乐</li>
     *                <li>{@link KeyEvent#KEYCODE_CALCULATOR}           打开计算器</li>
     *                <li>{@link KeyEvent#KEYCODE_BRIGHTNESS_DOWN}      降低屏幕亮度</li>
     *                <li>{@link KeyEvent#KEYCODE_BRIGHTNESS_UP}        提高屏幕亮度</li>
     *                <li>{@link KeyEvent#KEYCODE_SLEEP}                系统休眠</li>
     *                <li>{@link KeyEvent#KEYCODE_WAKEUP}               点亮屏幕</li>
     *                <li>{@link KeyEvent#KEYCODE_SOFT_SLEEP}           如果没有 wakelock 则让系统休眠</li>
     *                </ul>
     * @return 命令执行结果
     */
    public static CommandResult inputKeyEvent(int keycode) {
        return ShellUtils.execCmd("input keyevent " + keycode, true);
    }

    /**
     * 模拟按键 [需要 root 权限]
     *
     * @param keycodeName 按键名, 如 "KEYCODE_HOME", 完整的 keycode name 见 {@link KeyEvent}.KEYCODE_XXX
     * @return 命令执行结果
     */
    public static CommandResult inputKeyEvent(String keycodeName) {
        return ShellUtils.execCmd("input keyevent " + keycodeName, true);
    }

    /**
     * 模拟按键 [需要 root 权限]
     *
     * @param keycode   完整的 keycode 见 {@link KeyEvent}.KEYCODE_XXX
     * @param longPress 是否长按
     * @return 命令执行结果
     */
    public static CommandResult inputKeyEvent(int keycode, boolean longPress) {
        String option = longPress ? "--longpress " : "";
        return ShellUtils.execCmd(String.format("input keyevent %s%s", option, keycode), true);
    }


    /**
     * 模拟输入文本 [需要 root 权限]
     * <p>
     * 在焦点处于某文本框时，可以通过该命令来输入文本
     *
     * @param text 文本
     * @return 命令执行结果
     */
    public static CommandResult inputText(String text) {
        return ShellUtils.execCmd("input text " + text, true);
    }

    /**
     * 模拟触摸屏幕 [需要 root 权限]
     *
     * @param x 屏幕坐标 x
     * @param y 屏幕坐标 y
     * @return 命令执行结果
     */
    public static CommandResult inputTab(int x, int y) {
        return ShellUtils.execCmd(String.format("input tap %s %s", x, y), true);
    }

    /**
     * 模拟滑动屏幕 [需要 root 权限]
     *
     * @param x1 起点坐标 x
     * @param y1 起点坐标 y
     * @param x2 终点坐标 x
     * @param y2 终点坐标 y
     * @return 命令执行结果
     */
    public static CommandResult inputSwipe(int x1, int y1, int x2, int y2) {
        return ShellUtils.execCmd(String.format("input swipe %s %s %s %s", x1, y1, x2, y2), true);
    }

    /**
     * 模拟滑动屏幕 [需要 root 权限]
     *
     * @param x1       起点坐标 x
     * @param y1       起点坐标 y
     * @param x2       终点坐标 x
     * @param y2       终点坐标 y
     * @param duration 滑动时长
     * @return 命令执行结果
     */
    public static CommandResult inputSwipe(int x1, int y1, int x2, int y2, int duration) {
        return ShellUtils.execCmd(String.format("input swipe %s %s %s %s %s", x1, y1, x2, y2, duration), true);
    }

    //================================================ 查看设备信息 ================================================//

    /**
     * 查看电池状况 [需要 root 权限]
     * <p>
     * <P>输出示例</P>
     * <br>Current Battery Service state:
     * <br>  AC powered: false
     * <br>  USB powered: true
     * <br>  Wireless powered: false
     * <br>  status: 2
     * <br>  health: 2
     * <br>  present: true
     * <br>  level: 44
     * <br>  scale: 100
     * <br>  voltage: 3872
     * <br>  temperature: 280
     * <br>  technology: Li-poly
     * <p>
     * 其中 scale 代表最大电量，level 代表当前电量。上面的输出表示还剩下 44% 的电量。
     *
     * @return 命令执行结果
     */
    public static CommandResult showBatteryInfo() {
        return ShellUtils.execCmd("dumpsys battery", true);
    }

    /**
     * 查看屏幕分辨率 [需要 root 权限]
     * <p>
     * <P>输出示例</P>
     * Physical size: 1080x1920
     * 表示该设备屏幕分辨率为 1080px * 1920px。
     * <p>
     * 如果使用命令修改过，那输出可能是：
     * Physical size: 1080x1920
     * Override size: 480x1024
     * 表明设备的屏幕分辨率原本是 1080px * 1920px，当前被修改为 480px * 1024px。
     *
     * @return 命令执行结果
     */
    public static CommandResult showScreenSize() {
        return ShellUtils.execCmd("wm size", true);
    }

    /**
     * 查看屏幕密度 [需要 root 权限]
     * <p>
     * <P>输出示例</P>
     * Physical size: 1080x1920
     * 该设备屏幕密度为 420dpi。
     * <p>
     * 如果使用命令修改过，那输出可能是：
     * Physical density: 480
     * Override density: 160
     * 表明设备的屏幕密度原来是 480dpi，当前被修改为 160dpi。
     *
     * @return 命令执行结果
     */
    public static CommandResult showScreenDensity() {
        return ShellUtils.execCmd("wm density", true);
    }

    /**
     * 查看屏幕参数 [需要 root 权限]
     * <p>
     * <P>输出示例</P>
     * <br> WINDOW MANAGER DISPLAY CONTENTS (dumpsys window displays)
     * <br> Display: mDisplayId=0
     * <br> init=1080x1920 420dpi cur=1080x1920 app=1080x1794 rng=1080x1017-1810x1731
     * <br> deferred=false layoutNeeded=false
     * <p>
     * 其中 mDisplayId 为 显示屏编号，init 是初始分辨率和屏幕密度，app 的高度比 init 里的要小，表示屏幕底部有虚拟按键，高度为 1920 - 1794 = 126px 合 42dp。
     *
     * @return 命令执行结果
     */
    public static CommandResult showScreenInfo() {
        return ShellUtils.execCmd("dumpsys window displays", true);
    }

    /**
     * 查看 Android Id [需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult showAndroidId() {
        return ShellUtils.execCmd("settings get secure android_id", true);
    }

    /**
     * 查看 Device ID (IMEI) [需要 root 权限] (没有 root 权限可以得到 successMsg 但是里面不包含 IMEI)
     * <p>
     * <P>在 Android 4.4 及以下版本可通过如下命令获取 IMEI：</P>
     * <P>输出示例：</P>
     * <br>Phone Subscriber Info:
     * <br>  Phone Type = GSM
     * <br>  Device ID = 860955027785041
     * 其中的 Device ID 就是 IMEI。
     * <p>
     * <P>而在 Android 5.0 及以上版本里这个命令输出为空，得通过其它方式获取了（需要 root 权限）：</P>
     * <P>输出示例：</P>
     * <br>Result: Parcel(
     * <br>  0x00000000: 00000000 0000000f 00360038 00390030 '........8.6.0.9.'
     * <br>  0x00000010: 00350035 00320030 00370037 00350038 '5.5.0.2.7.7.8.5.'
     * <br>  0x00000020: 00340030 00000031                   '0.4.1...        ')
     * 把里面的有效内容提取出来就是 IMEI 了，比如这里的是 860955027785041。
     *
     * @return 命令执行结果
     */
    public static CommandResult showDeviceId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ShellUtils.execCmd("service call iphonesubinfo 1", true);
        } else {
            return ShellUtils.execCmd("dumpsys iphonesubinfo", true);
        }
    }

    /**
     * 查看 IP 地址 [不需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult showIpAddress() {
        return ShellUtils.execCmd("ifconfig | grep Mask", false);
    }

    /**
     * 查看 Mac 地址 [需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult showMacAddress() {
        return ShellUtils.execCmd("cat /sys/class/net/wlan0/address", true);
    }

    /**
     * 查看 CPU 信息 [不需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult showCpuInfo() {
        return ShellUtils.execCmd("cat /proc/cpuinfo", false);
    }

    /**
     * 查看内存信息 [不需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult showMemoryInfo() {
        return ShellUtils.execCmd("cat /proc/meminfo", false);
    }

    /**
     * 查看更多硬件与系统属性 [不需要 root 权限]
     * <p>
     * 这会输出很多信息，包括前面几个小节提到的「型号」和「Android 系统版本」等。
     * 输出里还包括一些其它有用的信息，它们也可通过 adb shell getprop <属性名> 命令单独查看，列举一部分属性如下：
     * <p>
     * <br>ro.build.version.sdk             SDK 版本
     * <br>ro.build.version.release         Android 系统版本
     * <br>ro.build.version.security_patch  Android 安全补丁程序级别
     * <br>ro.product.model                 型号
     * <br>ro.product.brand                 品牌
     * <br>ro.product.name                  设备名
     * <br>ro.product.board                 处理器型号
     * <br>ro.product.cpu.abilist           CPU 支持的 abi 列表[节注一]
     * <br>persist.sys.isUsbOtgEnabled      是否支持 OTG
     * <br>dalvik.vm.heapsize               每个应用程序的内存上限
     * <br>ro.sf.lcd_density                屏幕密度
     *
     * @return 命令执行结果
     */
    public static CommandResult showBuildProp() {
        return ShellUtils.execCmd("cat /system/build.prop", false);
    }

    /**
     * 查看更多硬件与系统属性中具体的属性 [不需要 root 权限]
     *
     * @param prop 属性名
     * @return 命令执行结果
     */
    public static CommandResult showBuildProp(String prop) {
        return ShellUtils.execCmd("getprop " + prop, false);
    }

    //================================================ 修改设置 ================================================//
    /* 注： 修改设置之后，运行恢复命令有可能显示仍然不太正常，可以运行 adb reboot 重启设备，或手动重启。*/

    /**
     * 修改分辨率 [需要 root 权限]
     *
     * @param width  屏幕宽
     * @param height 屏幕高
     * @return 命令执行结果
     */
    public static CommandResult setScreenSize(int width, int height) {
        return ShellUtils.execCmd(String.format("wm size %sx%s", width, height), true);
    }

    /**
     * 恢复原分辨率 [需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult resetScreenSize() {
        return ShellUtils.execCmd("wm size reset", true);
    }

    /**
     * 修改屏幕密度 [需要 root 权限]
     *
     * @param density 屏幕密度
     * @return 命令执行结果
     */
    public static CommandResult setScreenDensity(int density) {
        return ShellUtils.execCmd("wm density " + density, true);
    }

    /**
     * 恢复原屏幕密度 [需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult resetScreenDensity() {
        return ShellUtils.execCmd("wm density reset", true);
    }

    /**
     * 修改屏幕显示区域 [需要 root 权限]
     *
     * @param toLeft   距离左边缘的留白像素
     * @param toTop    距离上边缘的留白像素
     * @param toRight  距离右边缘的留白像素
     * @param toBottom 距离下边缘的留白像素
     * @return 命令执行结果
     */
    public static CommandResult setScreenOverscan(int toLeft, int toTop, int toRight, int toBottom) {
        return ShellUtils.execCmd(String.format("wm overscan %s,%s,%s,%s", toLeft, toTop, toRight, toBottom), true);
    }

    /**
     * 恢复原屏幕显示区域 [需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult resetScreenOverscan() {
        return ShellUtils.execCmd("wm overscan reset", true);
    }

    /**
     * 开启无障碍辅助服务 [需要 root 权限]
     *
     * @param packageName 应用包名
     * @param serviceName 服务名
     * @return 命令执行结果
     */
    public static CommandResult startAccessibilityService(String packageName, String serviceName) {
        return ShellUtils.execCmd(String.format("settings put secure enabled_accessibility_services %s/%s", packageName, serviceName), true);
    }

    //================================================ 实用功能 ================================================//

    /**
     * 屏幕截图 [需要 root 权限]
     *
     * @param path 图片保存路径
     * @return 命令执行结果
     */
    public static CommandResult screenCapture(String path) {
        return ShellUtils.execCmd("screencap -p " + path, true);
    }

    /**
     * 屏幕录制 [需要 root 权限]
     *
     * @param path      视频输出路径
     * @param timeLimit 录制时长，单位秒
     * @return 命令执行结果
     */
    public static CommandResult screenRecord(String path, int timeLimit) {
        return ShellUtils.execCmd(String.format("screenrecord --time-limit %s %s", timeLimit, path), true);
    }

    /**
     * 屏幕录制 [需要 root 权限]
     *
     * @param path      视频输出路径
     * @param timeLimit 录制时长，单位秒
     * @param width     视频的尺寸: 宽
     * @param height    视频的尺寸: 高
     * @return 命令执行结果
     */
    public static CommandResult screenRecord(String path, int timeLimit, int width, int height) {
        return ShellUtils.execCmd(String.format("screenrecord --size %sx%s --time-limit %s %s", width, height, timeLimit, path), true);
    }

    /**
     * 查看连接过的 WiFi 密码 [需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult showWifiConfig() {
        return ShellUtils.execCmd("cat /data/misc/wifi/*.conf", true);
    }

    /**
     * 使用 Monkey 进行压力测试 [需要 root 权限]
     * <p>
     * Monkey 可以生成伪随机用户事件来模拟单击、触摸、手势等操作，可以对正在开发中的程序进行随机压力测试。
     *
     * @param packageName 指定应用的包名
     * @param num         伪随机事件次数
     * @return 命令执行结果
     */
    public static CommandResult monkey(String packageName, int num) {
        return ShellUtils.execCmd(String.format("monkey -p %s -v %s", packageName, num), true);
    }

    /**
     * 开启 WiFi [需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult enableWifi() {
        return ShellUtils.execCmd("svc wifi enable", true);
    }

    /**
     * 关闭 WiFi [需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult disableWifi() {
        return ShellUtils.execCmd("svc wifi disable", true);
    }

    /**
     * 重启手机 [需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult reboot() {
        return ShellUtils.execCmd("reboot", true);
    }

    /**
     * 重启到 Recovery 模式 [需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult rebootRecovery() {
        return ShellUtils.execCmd("reboot recovery", true);
    }

    /**
     * 重启到 Fastboot 模式 [需要 root 权限]
     *
     * @return 命令执行结果
     */
    public static CommandResult rebootBootloader() {
        return ShellUtils.execCmd("reboot bootloader", true);
    }

}
