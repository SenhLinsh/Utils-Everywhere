package com.linsh.lshutils.view.circleprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

/**
 * Created by Senh Linsh on 17/3/17.
 */

public class CircleProgress extends BaseCircleProgress {

    public CircleProgress(Context context) {
        super(context);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        if (isInEditMode()) {
            setProgressWidth(20);
            setProgress(50);
            setProgressColor(Color.BLUE);
            setEmptyColor(0x11000000);
        }
    }

    @Override
    protected void onDrawProgress(Canvas canvas, float startAngle, float endAngle) {

        float sweepAngle = mCurrentProgresses[0] * 3.6f;
        if (mProgressColors != null && 0 < mProgressColors.length) {
            mPaint.setColor(mProgressColors[0]);
        } else {
            mPaint.setColor(DEFAULT_COLOR);
        }
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, mPaint);

        if (Math.round(sweepAngle + startAngle) < endAngle) {
            mPaint.setColor(mEmptyColor);
            canvas.drawArc(mRectF, sweepAngle + startAngle, endAngle - sweepAngle - startAngle, false, mPaint);
        }
    }

    public void setProgress(int progress) {
        setProgress(new int[]{progress});
    }

    public void setProgressColor(int colors) {
        setProgressColor(new int[]{colors});
    }

    public void setProgressWithAnimation(int progresses) {
        setProgressWithAnimation(new int[]{progresses});
    }

    public void setProgressWithAnimation(int progresses, long duration) {
        setProgressWithAnimation(new int[]{progresses}, duration);
    }
}
