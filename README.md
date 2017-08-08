# LshUtils

[![Release](https://jitpack.io/v/com.github.SenhLinsh/LshUtils.svg)](https://jitpack.io/#com.github.SenhLinsh/LshUtils)

个人收集的常用工具类

## 添加依赖
1. 在项目根目录的build.gradle中添加自定义仓库
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. 在module里面添加依赖
```
dependencies {
    compile 'com.github.SenhLinsh:LshUtils:1.2.0'
}
 ```

## 简介
### 自己收集整理并在工作之中抽取完善的个人工具类 (utils文件夹下)

####Basic
utils/basic文件夹下的Utils是其他Utils经常引用的基本Utils, 如果你选择复制, 别忘了把里面的几个Utils复制过去哦, 你会发现里面几个Utils大有作用.</br>
注意: LshApplicationUtils是非常关键的一个Utils, 因为其他Utils需要使用context的时候, 很多都是直接拿了它的context, 记得在程序入口Application中初始化LshApplicationUtils哦~

* LshApplicationUtils: 用于随时获取Application的Context,Handler等, 以便在一些无法或不方便获取的地方直接获取, 简单方便.
什么? 还不知道直接获取Context和Handler的方便之处? 尝试一下吧, 你就知道
* LshLogUtils: 封装度较高的LogUtils, 可以设置方便快速过滤的tag使用默认或者设置好的tag加上调用log地方的类名. 打log的时候可以免输入tag或者输入伪tag, 方便快捷.
还可以选择将log打印至外部存储, 以便随时获取log或奔溃日志.
* LshSharedPreferenceUtils: 简单方便的SharedPreferenceUtils
* LshToastUtils: 还在使用Toast.makeText(context, text, duration).show()? LshToastUtils帮你用一句代码一个参数搞定.
并且重复调用的时候直接修改当前Toast的文字, 而不是需要等待几秒后重新弹出一个Toast的尴尬体验

###Utils
utils文件夹下的Utils, 大都是我的心血总结, 可以挑自己喜欢的直接拿走(注意里面的Utils很有可能已经引用有basic文件夹下的Utils), 注意检查.
当然, 直接全部打包带走是最好的, 如果你喜欢的话.

* LshActivityUtils: 厌烦了Activity跳转时携带参数的麻烦? LshActivityUtils带你玩转跳转, 不需要自己写Extra的key, 可以直接根据参数类型帮你找到你需要的Extra,
参数多的时候再给一个index编号, 就能完美跳过写Extra key的烦恼. 而且还可以直接传入JsonBean对象哦, 工具类直接帮你转换格式, 方便不方便?
使用链式编程, 也能大大帮你简洁代码的书写~

* LshPropertiesFileUtils: 想存储一些简短的配置或信息到外部存储? LshPropertiesFileUtils帮你包办, 懒癌专用

* LshXmlCreater: 想用代码生成一些shape,border或者颜色背景选择器但是又苦于记不住怎么写, 那么它能帮你大大节省很多时间

其他的就不多介绍了, 随着日常开发, 工具类正在完善和壮大, 我相信总有一天你们会喜欢的

### 除了工具类, 我还抽取了一些常用的类(其他分类文件夹下), 如:
* 简单实用的自定义控件:
[NoPaddingTextView](https://github.com/SenhLinsh/LshUtils/blob/master/lshutils/src/main/java/com/linsh/lshutils/view/NoPaddingTextView.java)
(在另一个项目中有详细的介绍 [详戳 NoPaddingTextView](https://github.com/SenhLinsh/NoPaddingTextView))

### 引用摘抄了优秀开源的工具类 (others文件夹下)
整理自己的工具类之前, 我也是在GitHub上面去找别人用的工具类拿来用. 但别人的始终是别人的, 你没有自己去动手完善过始终不能很好地去运用.
至今的工具类里面还保留着一些直接引用的别人的工具类, 在此表示感谢, 喜欢收集工具类的可以戳戳:

* [https://github.com/Blankj/AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)
* [https://github.com/litesuits/android-common](https://github.com/litesuits/android-common)
* [https://github.com/l123456789jy/Lazy](https://github.com/l123456789jy/Lazy)
