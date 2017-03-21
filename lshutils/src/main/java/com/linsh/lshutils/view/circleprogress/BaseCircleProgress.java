package com.linsh.lshutils.view.circleprogress;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.linsh.lshutils.utils.LshArrayUtils;

/**
 * Created by Senh Linsh on 17/3/17.
 */

abstract class BaseCircleProgress extends View {

    public static final int DEFAULT_COLOR = 0x3F51B5;
    public static final long DEFAULT_DURATION = 500;

    protected Paint mPaint;
    protected RectF mRectF = new RectF();

    protected int mStrokeWidth = 20;
    protected int[] mCurrentProgresses;
    protected int[] mStartProgresses;
    protected int[] mAnimatingValues;
    protected int[] mProgressColors;
    protected int mEmptyColor;
    protected int mStartAngle;
    protected ValueAnimator mProgressAnimator;

    public BaseCircleProgress(Context context) {
        super(context);
        init();
    }

    public BaseCircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        mPaint = new Paint();
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        setUpAnimation();
    }

    public void setProgressWidth(int width) {
        mStrokeWidth = width;
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    protected void setProgress(int[] progress) {
        mCurrentProgresses = progress;
        invalidate();
    }

    protected void setProgressColor(int[] colors) {
        mProgressColors = colors;
    }

    public void setEmptyColor(int color) {
        mEmptyColor = color;
    }

    public void setStartAngle(int startAngle) {
        this.mStartAngle = startAngle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float startAngle = this.mStartAngle - 90;
        float endAngle = this.mStartAngle + 270;

        if (mCurrentProgresses == null || mCurrentProgresses.length == 0) {
            mPaint.setColor(mEmptyColor);
            canvas.drawArc(mRectF, 0, 360, false, mPaint);
            return;
        }

        onDrawProgress(canvas, startAngle, endAngle);
    }

    protected abstract void onDrawProgress(Canvas canvas, float startAngle, float endAngle);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mRectF.set(mStrokeWidth / 2f, mStrokeWidth / 2f, width - mStrokeWidth / 2f, height - mStrokeWidth / 2f);
    }

    protected void setProgressWithAnimation(int[] progresses) {
        setProgressWithAnimation(progresses, DEFAULT_DURATION);
    }

    protected void setProgressWithAnimation(int[] progresses, long duration) {
        if (mProgressAnimator == null) {
            setUpAnimation();
        }

        if (mProgressAnimator.isRunning()) {
            mProgressAnimator.cancel();
        }

        if (mCurrentProgresses == null || mCurrentProgresses.length < progresses.length) {
            mCurrentProgresses = LshArrayUtils.getCopy(mCurrentProgresses, progresses.length);
        }

        mStartProgresses = LshArrayUtils.getCopy(mCurrentProgresses);
        mAnimatingValues = new int[mCurrentProgresses.length];
        for (int i = 0; i < mAnimatingValues.length; i++) {
            mAnimatingValues[i] = (i < progresses.length ? progresses[i] : 0) - this.mCurrentProgresses[i];
        }

        mProgressAnimator.setDuration(duration);
        mProgressAnimator.start();
    }

    private void setUpAnimation() {
        if (mProgressAnimator != null) {
            return;
        }

        mProgressAnimator = ValueAnimator.ofFloat(0, 1);
        mProgressAnimator.setDuration(DEFAULT_DURATION);
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                for (int i = 0; i < mCurrentProgresses.length; i++) {
                    mCurrentProgresses[i] = (int) (mStartProgresses[i] + mAnimatingValues[i] * (float) animation.getAnimatedValue());
                }
                invalidate();
            }
        });
        mProgressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatingValues = null;
                mStartProgresses = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mAnimatingValues = null;
                mStartProgresses = null;
            }
        });
    }

    private void tearDownAnimation() {
        mProgressAnimator.cancel();
        mProgressAnimator.removeAllUpdateListeners();
        mProgressAnimator.removeAllListeners();
        mProgressAnimator = null;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setUpAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        tearDownAnimation();
    }
}
