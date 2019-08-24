package com.linsh.utilseverywhere;

import android.graphics.Color;

import androidx.annotation.ColorInt;

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

    private ColorUtils() {
    }

    public static int compositeColors(@ColorInt int foreground, @ColorInt int background) {
        return androidx.core.graphics.ColorUtils.compositeColors(foreground, background);
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

    /**
     * 平衡颜色
     * <p>
     * 根据基准颜色, 计算出能够平均分配色环的剩余颜色, 保证明度值一致颜色不同
     *
     * @param baseColor 基准颜色
     * @param num       总颜色数
     * @return 平均分配好的颜色, 基准颜色的 index 为 0, 其余颜色按顺时针排列
     */
    public static int[] getBalancedColors(int baseColor, int num) {
        if (num < 0) {
            throw new IllegalArgumentException("num can't be negative");
        }
        if (num == 0) {
            return new int[0];
        }
        int alpha = Color.alpha(baseColor);
        float[] rgb = new float[]{Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor)};
        float width = (Math.abs(rgb[0] - rgb[1]) + Math.abs(rgb[1] - rgb[2]) +
                Math.abs(rgb[2] - rgb[0])) / 2;
        float total = width * 6;

        int[] res = new int[num];
        res[0] = baseColor;
        // r == g == b, 灰色, 剩余颜色均为该色值
        if (total == 0) {
            for (int i = 1; i < res.length; i++) {
                res[i] = baseColor;
            }
            return res;
        }

        int midIndex = 0;
        if ((rgb[0] - rgb[1]) * (rgb[1] - rgb[2]) > 0
                || rgb[0] == rgb[1]) {
            midIndex = 1;
        } else if ((rgb[1] - rgb[2]) * (rgb[2] - rgb[0]) > 0
                || rgb[1] == rgb[2]) {
            midIndex = 2;
        }
        float aver = total * 1f / num;
        for (int i = 1; i < res.length; i++) {
            float cur = aver;
            while (cur > 0) {
                float diff = rgb[(midIndex + 2) % 3] - rgb[midIndex % 3];
                int sign = diff > 0 ? 1 : -1;
                if (diff * sign > cur) {
                    rgb[midIndex % 3] += cur * sign;
                    cur = 0;
                } else {
                    rgb[midIndex % 3] += diff;
                    midIndex += 2;
                    cur -= diff * sign;
                }
                if (cur == 0) {
                    res[i] = Color.argb(alpha, Math.round(rgb[0]),
                            Math.round(rgb[1]), Math.round(rgb[2]));
                }
            }
        }
        return res;
    }
}
