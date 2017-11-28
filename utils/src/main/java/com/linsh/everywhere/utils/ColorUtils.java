package com.linsh.everywhere.utils;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/09
 *    desc   : 工具类: 颜色相关
 *             如 解析颜色 / 覆盖颜色 / 抽取颜色 / 混合颜色 等
 * </pre>
 */
public class ColorUtils {

    public static int compositeColors(@ColorInt int foreground, @ColorInt int background) {
        return android.support.v4.graphics.ColorUtils.compositeColors(foreground, background);
    }

    /**
     * 覆盖颜色, 在背景颜色上覆盖一层颜色(有透明度)后得出的混合颜色
     * <p>注意: 覆盖的颜色需要有一定的透明度, 如果覆盖的颜色不透明, 得到的颜色为覆盖的颜色
     *
     * @param baseColor  基础颜色(背景颜色)
     * @param coverColor 覆盖颜色(前景颜色)
     * @return
     */
    public static int coverColor(@ColorInt int baseColor, @ColorInt int coverColor) {
        int fgAlpha = Color.alpha(coverColor);
        int bgAlpha = Color.alpha(baseColor);
        int a = blendColorNormalFormula(255, bgAlpha, fgAlpha, bgAlpha);
        int r = blendColorNormalFormula(Color.red(coverColor), Color.red(baseColor), fgAlpha, bgAlpha);
        int g = blendColorNormalFormula(Color.green(coverColor), Color.green(baseColor), fgAlpha, bgAlpha);
        int b = blendColorNormalFormula(Color.blue(coverColor), Color.blue(baseColor), fgAlpha, bgAlpha);
        return Color.argb(a, r, g, b);
    }

    /**
     * 抽取颜色 (覆盖颜色的相反操作), 用于猜测何种颜色覆盖上需要抽取的颜色会得出基准颜色
     * <p>注意:
     * <br>1.抽取的颜色需要有一定的透明度
     * <br>2.可能无法准确得出抽取后的颜色 (某个颜色范围内覆盖上需要抽取的颜色都可以得到基准颜色, 默认取颜色值最低的数值)
     * <br>3.可能无法得出抽取后的颜色 (任何颜色覆盖上需要抽取的颜色都无法得到基准颜色, 会报错)
     *
     * @param baseColor      基础颜色
     * @param extractedColor 需要抽取掉的颜色
     * @return 抽取后得到的颜色
     */
    public static int extractColor(@ColorInt int baseColor, @ColorInt int extractedColor) {
        int fgAlpha = Color.alpha(extractedColor);
        int mixedAlpha = Color.alpha(baseColor);
        int bgAlpha = (int) Math.round(Math.sqrt((mixedAlpha - fgAlpha) / (255 - fgAlpha)) * 255);
        int bgRed = (int) ((Color.red(baseColor) * 255 - Color.red(extractedColor) * fgAlpha) * 255F / ((255 - fgAlpha) * bgAlpha));
        int bgGreed = (int) ((Color.green(baseColor) * 255 - Color.green(extractedColor) * fgAlpha) * 255F / ((255 - fgAlpha) * bgAlpha));
        int bgBlue = (int) ((Color.blue(baseColor) * 255 - Color.blue(extractedColor) * fgAlpha) * 255F / ((255 - fgAlpha) * bgAlpha));
        return Color.argb(bgAlpha, bgRed, bgGreed, bgBlue);
    }

    /**
     * 颜色混合模式中正常模式的计算公式
     * 公式: 最终色 = 基色 * a% + 混合色 * (1 - a%)
     */
    private static int blendColorNormalFormula(int fgArgb, int bgArgb, int fgAlpha, int bgAlpha) {
        int mix = (int) (fgArgb * fgAlpha / 255f + (1 - fgAlpha / 255f) * bgArgb * bgAlpha / 255f);
        return mix > 255 ? 255 : mix;
    }

    /**
     * 通过颜色的 int 色值获取颜色对应的十六进制字符串表示
     *
     * @param color 颜色色值
     * @return 十六进制表示的字符串颜色
     */
    public static String getColorString(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        StringBuilder builder = new StringBuilder("#");
        char[] chars = "0123456789ABCDEF".toCharArray();
        if (alpha < 255) {
            builder.append(chars[alpha / 16]).append(chars[alpha % 16]);
        }
        builder.append(chars[red / 16]).append(chars[red % 16]);
        builder.append(chars[green / 16]).append(chars[green % 16]);
        builder.append(chars[blue / 16]).append(chars[blue % 16]);
        return builder.toString();
    }

    // TODO: 17/11/9 添加解析颜色的方法
//    public static int parseColor(String color) {
//        return 0;
//    }
}
