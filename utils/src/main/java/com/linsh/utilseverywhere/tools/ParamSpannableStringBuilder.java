package com.linsh.utilseverywhere.tools;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/01/13
 *    desc   : 可设置参数的 SpannableString
 *             为了避免重复创建 Builder 而消耗资源, 在给 Builder 添加文本时可以将其设置为参数, 以后
 *             每次都给对该参数所对应的字符串进行替换文字或者设置新样式.
 * </pre>
 */
public class ParamSpannableStringBuilder {

    private SpannableStringBuilder mBuilder;
    private Param[] mParams;

    /**
     * @param paramNum 参数个数
     */
    public ParamSpannableStringBuilder(int paramNum) {
        mBuilder = new SpannableStringBuilder();
        mParams = new Param[paramNum];
    }

    /**
     * 添加文本
     *
     * @param text  文本
     * @param spans Span
     */
    public ParamSpannableStringBuilder addText(String text, Object... spans) {
        int start = mBuilder.length();
        mBuilder.append(text);
        int end = mBuilder.length();
        setSpan(start, end, spans);
        return this;
    }

    /**
     * 添加文本, 并将该文本作为参数, 方便进行动态设置
     *
     * @param text       文本
     * @param paramIndex 参数索引
     * @param spans      Span
     * @return 该文本所对应的参数
     */
    public ParamSpannableStringBuilder addTextAsParam(String text, int paramIndex, Object... spans) {
        int start = mBuilder.length();
        mBuilder.append(text);
        int end = mBuilder.length();
        setSpan(start, end, spans);
        if (paramIndex < mParams.length) {
            mParams[paramIndex] = new Param(start, end);
        }
        return this;
    }

    /**
     * 添加文本, 并将该文本中指定的内容作为参数, 方便进行动态设置
     *
     * @param text       文本
     * @param paramStart 参数开始索引
     * @param paramEnd   参数结束索引
     * @param paramIndex 参数索引
     * @param spans      Span
     */
    public ParamSpannableStringBuilder addTextAsParam(String text, int paramStart, int paramEnd, int paramIndex, Object... spans) {
        int start = mBuilder.length();
        mBuilder.append(text);
        int end = mBuilder.length();
        setSpan(start, end, spans);
        if (paramIndex < mParams.length) {
            mParams[paramIndex] = new Param(start + paramStart, start + paramEnd);
        }
        return this;
    }

    /**
     * 设置 Span
     *
     * @param start 开始索引
     * @param end   结束索引
     * @param spans Span
     */
    public ParamSpannableStringBuilder setSpan(int start, int end, Object... spans) {
        for (Object span : spans) {
            mBuilder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return this;
    }

    /**
     * 设置参数
     *
     * @param paramIndex 参数索引
     * @param spans      Span
     */
    public ParamSpannableStringBuilder setParam(int paramIndex, Object... spans) {
        if (paramIndex < mParams.length) {
            Param param = mParams[paramIndex];
            if (param != null) {
                setSpan(param.start, param.end, spans);
            }
        }
        return this;
    }

    /**
     * 设置参数
     *
     * @param paramIndex 参数索引
     * @param text       文本
     * @param spans      Span
     */
    public ParamSpannableStringBuilder setParam(int paramIndex, String text, Object... spans) {
        if (paramIndex < mParams.length) {
            Param param = mParams[paramIndex];
            if (param != null) {
                mBuilder.replace(param.start, param.end, text);
                int lengthDiff = text.length() - (param.end - param.start);
                param.end = param.end + lengthDiff;
                setSpan(param.start, param.end, spans);
                // 更新往后的 Param 位置
                if (paramIndex < mParams.length - 1 && lengthDiff != 0) {
                    for (int i = paramIndex + 1; i < mParams.length; i++) {
                        mParams[i].start += lengthDiff;
                        mParams[i].end += lengthDiff;
                    }
                }
            }
        }
        return this;
    }

    /**
     * 获取 SpannableString
     *
     * @return SpannableStringBuilder
     */
    public SpannableStringBuilder getText() {
        return mBuilder;
    }

    public static class Param {
        private int start;
        private int end;

        private Param(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
