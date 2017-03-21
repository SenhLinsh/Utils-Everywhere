package com.linsh.lshutils.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.linsh.lshutils.R;

/**
 * Created by Senh Linsh on 17/3/20.
 */

public class DashedLine extends View {
    private Paint mPaint;
    private Path path;
    private int mOrientation;
    private int mDashColor;
    private DashPathEffect mDashPathEffect;

    public DashedLine(Context context) {
        super(context);
        init();
    }

    public DashedLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
        init();
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DashedLine);
        mDashColor = typedArray.getColor(R.styleable.DashedLine_dashColor, Color.TRANSPARENT);
        mOrientation = typedArray.getInt(R.styleable.DashedLine_dashOrientation, 0);
        int mDashGap = typedArray.getDimensionPixelSize(R.styleable.DashedLine_dashGap, 0);
        int mDashWidth = typedArray.getDimensionPixelSize(R.styleable.DashedLine_dashWidth, 1);
        mDashPathEffect = new DashPathEffect(new float[]{mDashWidth, mDashGap}, 0);
        typedArray.recycle();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        path = new Path();
    }

    public void setDashColor(int color) {
        mDashColor = color;
    }

    public void setOrientation(Orientation orientation) {
        switch (orientation) {
            case HORIZONTAL:
                mOrientation = 0;
                break;
            case VERTICAL:
                mOrientation = 1;
                break;
        }
    }

    public void setDashEffect(int dashWidth, int dashGap) {
        mDashPathEffect = new DashPathEffect(new float[]{dashWidth, dashGap}, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        mPaint.setColor(mDashColor);

        mPaint.setPathEffect(mDashPathEffect);

        path.rewind();
        if (mOrientation == 1) { /// 垂直
            mPaint.setStrokeWidth(width);
            path.moveTo(width / 2, 0);
            path.lineTo(width / 2, height);
        } else { /// 水平
            mPaint.setStrokeWidth(height);
            path.moveTo(0, height / 2);
            path.lineTo(width, height / 2);
        }

        canvas.drawPath(path, mPaint);
    }

    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
}
