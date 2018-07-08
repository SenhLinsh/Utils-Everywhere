package com.linsh.utilseverywhere.tools;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

import com.linsh.utilseverywhere.ContextUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/01/19
 *    desc   :
 * </pre>
 */
public class SpannableText extends SpannableString {

    private CharSequence source;

    public SpannableText(CharSequence source) {
        super(source);
    }

    public SpannableText setTextSize(int size) {
        setSpan(new AbsoluteSizeSpan(size));
        return this;
    }

    public SpannableText setTextSize(int size, int start, int end) {
        setSpan(new AbsoluteSizeSpan(size), start, end);
        return this;
    }

    public SpannableText setTextSizeDp(int size) {
        setSpan(new AbsoluteSizeSpan(size, true));
        return this;
    }

    public SpannableText setTextSizeDp(int size, int start, int end) {
        setSpan(new AbsoluteSizeSpan(size, true), start, end);
        return this;
    }

    public SpannableText setTextSizeSp(int size) {
        setSpan(new AbsoluteSizeSpan(sp2px(size)));
        return this;
    }

    public SpannableText setTextSizeSp(int size, int start, int end) {
        setSpan(new AbsoluteSizeSpan(sp2px(size)), start, end);
        return this;
    }

    public SpannableText setTextColor(int color) {
        setSpan(new ForegroundColorSpan(color));
        return this;
    }

    public SpannableText setTextColor(int color, int start, int end) {
        setSpan(new ForegroundColorSpan(color), start, end);
        return this;
    }

    public SpannableText setBackgroundColor(int color) {
        setSpan(new BackgroundColorSpan(color));
        return this;
    }

    public SpannableText setBackgroundColor(int color, int start, int end) {
        setSpan(new BackgroundColorSpan(color), start, end);
        return this;
    }

    public SpannableText setSpan(Object what) {
        setSpan(what, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableText setSpan(Object what, int flags) {
        super.setSpan(what, 0, length(), flags);
        return this;
    }

    public SpannableText setSpan(Object what, int start, int end) {
        super.setSpan(what, start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    private static int sp2px(float sp) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

    private static Context getContext() {
        return ContextUtils.get();
    }
}
