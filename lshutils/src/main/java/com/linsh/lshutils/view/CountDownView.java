package com.linsh.lshutils.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.linsh.lshutils.utils.LshXmlCreater;

/**
 * Created by Senh Linsh on 16/12/21.
 */
public class CountDownView extends TextView {
    // 样式
    private int roundRadius = 1000;
    private int strokeWidth = 2;
    private int enabledColor = Color.BLACK;
    private int disabledColor = Color.GRAY;
    private String enabledText = "获取验证码";
    private String repeatText = "重新获取";
    private String countDownPostfix = "s";
    // 逻辑
    private int time = 60;
    private Runnable countDownRun = new Runnable() {
        @Override
        public void run() {
            if (time < 0) {
                setEnabled(true);
                setText(repeatText);
            } else {
                setText(time + countDownPostfix);
                postDelayed(countDownRun, 1000);
            }
            time--;
        }
    };

    public CountDownView(Context context) {
        super(context);
        initView(context);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        setText(enabledText);
        setColor();
    }

    public void setColor(int enabledColor, int disabledColor) {
        this.enabledColor = enabledColor;
        this.disabledColor = disabledColor;
        setColor();
    }

    public void setText(String enabledText, String repeatText, String countDownPostfix) {
        this.enabledText = enabledText;
        this.repeatText = repeatText;
        this.countDownPostfix = countDownPostfix;
    }

    private void setColor() {
        // 设置字体颜色
        setTextColor(LshXmlCreater.createEnabledColorSelector(enabledColor, disabledColor));
        // 设置背景
        Drawable enabledDrawable = LshXmlCreater.createRectangleBorder(roundRadius, strokeWidth, enabledColor);
        Drawable disabledDrawable = LshXmlCreater.createRectangleBorder(roundRadius, strokeWidth, disabledColor);
        StateListDrawable background = LshXmlCreater.createEnabledSelector(enabledDrawable, disabledDrawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(background);
        } else {
            setBackgroundDrawable(background);
        }
    }

    public void startCountDown(int time) {
        this.time = time;
        setEnabled(false);
        post(countDownRun);
    }

    public void endCountDown() {
        time = 0;
        setEnabled(true);
        removeCallbacks(countDownRun);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(countDownRun);
    }
}
