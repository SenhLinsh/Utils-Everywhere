# Utils-Everywhere

[![Release](https://jitpack.io/v/com.github.SenhLinsh/Utils-Everywhere.svg)](https://jitpack.io/#com.github.SenhLinsh/Utils-Everywhere)

整理并收集各种常用的覆盖面广的工具类。


## 1. 简介

**[Utils-Everywhere](https://github.com/SenhLinsh/Utils-Everywhere)**  是日常项目和工作中将常
用的代码进行封装和整理形成的工具类，一部分是结合自己再实际开发中整理而得，一部分则是直接参考或使用其他优
秀的开源工具类（文末和代码中均标明出处，部分遗漏之处请谅解）。使用封装后的工具库，可以大大提高我们的开发
效率，一方面可以减少非常多的重复代码，另一方面可以避免对某些代码或API的死记硬背，工具类会以尽可能平白简
单的方式来让大家熟悉并使用其中的方法。

由于部分工具类是由本人『亲自操刀』，而本人能力有限，离大神尚且遥远，可能部分方法没有经过优化甚至存
在 BUG，希望广大码友进行指正。通过也欢迎大家对工具类进行补充和完善，一起打造一个覆盖面广的工具类集合。


## 2. 使用

### 2.1 添加依赖

##### 2.1.1 在项目根目录的build.gradle中添加自定义仓库
 ```
 allprojects {
   repositories {
     ...
     maven { url 'https://jitpack.io' }
   }
 }
 ```

##### 2.1.2 在module里面添加依赖
新版 Gradle 依赖 (Android Gradle 3.0.0 之后):

 ```
 dependencies {
     implementation 'com.github.SenhLinsh:Utils-Everywhere:2.1.0'
 }
  ```

旧版 Gradle 依赖 (Android Gradle 3.0.0 之前):

 ```
 dependencies {
     compile 'com.github.SenhLinsh:Utils-Everywhere:2.1.0'
 }
  ```

### 2.2 初始化
在使用本项目中工具类的方法之前，请先进行初始化

 ```
 Utils.init(context);
 ```


## 3. 工具类介绍

### 3.1 工具类（utils）

| 工具类 | 简介 | 主要 API |
| - | - | - |
| AccessibilityUtils | 辅助功能（无障碍服务功能）相关 | - |
| ActivityLifecycleUtils | Activity 生命周期回调相关，目前主要用于获取顶部 Activity 以及判断 APP 是否处于后台 | - |
| ADBUtils | 执行 ADB 命令相关 | - |
| AppUtils | APP 相关 | - |
| ArrayUtils | 数组相关 | - |
| BitmapUtils | Bitmap 相关，如对 Bitmap 对象的获取、处理、转化和保存 | - |
| CameraUtils | 摄像头相关，如检查摄像头设备、检查前后置摄像头等 | - |
| ChineseNumberUtils | 中文数字处理 | - |
| ClassUtils | Class 字节码相关、反射相关 | - |
| CleanUtils | 清理缓存和数据相关 | - |
| ClipboardUtils | 剪贴板相关 | - |
| ColorUtils | 颜色处理 | - |
| ContextUtils | APP Context 的获取，简化 Context 方法 | - |
| DateUtils | 日期相关 | - |
| DeviceUtils | 设备相关，判断手机或平板设备 | - |
| EncodeUtils | 编码解码相关 | - |
| EncryptUtils | 加密解密相关 | - |
| FileUtils | 文件或文件夹操作相关，如读写文件、操作文件等 | - |
| FragmentUtils | Fragment 相关 | - |
| HandlerUtils | 默认开启一个 Handler，方便在各个地方随时执行主线程任务 | - |
| IntentUtils | Intent 意图相关 | - |
| IOUtils | IO 流对象相关，用于简化关闭流操作 | - |
| KeyboardUtils | 操作系统键盘 | - |
| ListUtils | 对集合进行操作 | - |
| LogUtils | Log 日志打印简化 | - |
| LunarCalendarUtils | 农历日期的解析和格式化等 | - |
| LunarConverseUtils | 阳历阴历转换相关 | - |
| NetworkUtils | 网络相关，如检查网络、获取 IP 等 | - |
| OSUtils | 判断当前 OS 系统 | - |
| PermissionUtils | 权限处理相关 | - |
| PhoneStateUtils | 手机状态相关，如判断锁屏、屏幕是否亮着 | - |
| PhoneUtils | 设备硬件相关，获取手机信息（厂商、品牌、型号、IMEI 码、IMSI 码、IP 等） | - |
| RandomUtils | 随机数相关 | - |
| RegexUtils | 正则相关 | - |
| ResourceUtils | 简化资源的对象的获取（资源 id、图片、字符串、颜色等） | - |
| ScreenUtils | 屏幕相关，如获取屏幕尺寸、方向、截屏等 | - |
| SDCardUtils | 外部储存相关，如检查 SD 卡，获取根目录、容量等 | - |
| SharedPreferenceUtils | 简化 SharedPreference 配置的保存和获取 | - |
| ShellUtils | Shell 命令相关 | - |
| ShortcutUtils | 桌面快捷方式相关 | - |
| StringUtils | 字符串处理 | - |
| ToastUtils | Toast 相关，简化调用 | - |
| UnitConverseUtils | 单位转换相关，如 dp、px、sp 之间的转换 | - |
| XmlUtils | XML 相关，目前有生成 Shape 和状态选择器 | - |
| ZipUtils | 压缩相关 | - |

### 3.2 辅助类（tools）

| 工具类 | 简介 | 主要 API |
| - | - | - |
| AccessibilityHelper | 简化 AccessibilityService 的使用 | - |
| ColorSelectorBuilder | 构建颜色选择器的辅助类 | - |
| ContactsEditor | 对系统联系人 (Contacts) 进行增删改的帮助类 | - |
| CursorHelper | 简化 Cursor 对象的操作 | - |
| DrawableSelectorBuilder | 构建图像选择器的辅助类 | - |
| IntentBuilder | Intent 构建辅助类，链式编程简化 Intent 构建，传递数据时可隐藏 key 的使用 | - |
| ParamSpannableStringBuilder | 构建参数化 SpannableString 的辅助类 | - |
| ShapeBuilder | 构建 Shape 的辅助类 | - |
| WhereBuilder | 构建 SQL 查询条件筛选语句的辅助类 | - |


## 4. 参考
本项目中参考并借鉴了多个优秀开源项目的工具类，包括但不限于以下：

> * **AndroidUtilCode** : [https://github.com/Blankj/AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)
> * **android-common** : [https://github.com/litesuits/android-common](https://github.com/litesuits/android-common)
> * **Lazy** : [https://github.com/l123456789jy/Lazy](https://github.com/l123456789jy/Lazy)
