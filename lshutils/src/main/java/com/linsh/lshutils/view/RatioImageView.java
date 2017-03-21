package com.linsh.lshutils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.linsh.lshutils.R;

/**
 * 按照图片原始比例自动匹配大小的ImageView<br>
 * 固定ImageView的宽或者高其中一个，RatioImageView会根据图片的比例来控制另一个的长度<br>
 * 布局文件中的两个自定义属性：
 * <ul>dafaultRatio  默认比例，即在没有显示图片时，不定边/定边的比例，默认值为0</ul>
 * <ul>decidedRatio  固定比例，不定边/定边的比例，设置此固定值，RatioImageView不会再根据图片的比例去控制不定边的值</ul>
 * dafaultRatio、decidedRatio一般可以不指定<br>
 * 注意：如果两边都确定或者两边都不确定，RatioImageView的设置将会失效<br>
 * 需要在attrs.xml中配置自定义属性
 *
 * @author Senh Linsh
 */
public class RatioImageView extends ImageView {

    private float dafaultRatio;
    private boolean isDecided;
    private int state;
    private static final int STATE_NO_NEED = 0;
    private static final int STATE_NEED_WIDTH = 1;
    private static final int STATE_NEED_HEIGHT = 2;
    private int maxWidthSize;
    private int maxHeightSize;

    public RatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 获取自定义属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RatioImageView);

        dafaultRatio = typedArray.getFloat(
                R.styleable.RatioImageView_dafaultRatio, 0);
        float decidedRatio = typedArray.getFloat(
                R.styleable.RatioImageView_decidedRatio, 0);
        if (decidedRatio > 0) {
            dafaultRatio = decidedRatio;
            isDecided = true;
        }
        typedArray.recycle();
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioImageView(Context context) {
        this(context, null);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        // 没有确定宽高才动态设置
        if (drawable != null && !isDecided) {
            // 获取图片宽高
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            // 如果获取的宽高不为零，计算宽高比
            if (width != 0 && height != 0) {
                float ratio = width * 1f / height;

                // 动态设置不固定的边的长度
                if (state == STATE_NEED_HEIGHT) {
                    // 动态设置高
                    LayoutParams layoutParams = getLayoutParams();
                    layoutParams.height = (int) (maxWidthSize / ratio + 0.5)
                            + getPaddingBottom() + getPaddingTop();
                    setLayoutParams(layoutParams);
                } else if (state == STATE_NEED_WIDTH) {
                    // 动态设置宽
                    LayoutParams layoutParams = getLayoutParams();
                    layoutParams.width = (int) (maxHeightSize * ratio + 0.5)
                            + getPaddingBottom() + getPaddingTop();
                    setLayoutParams(layoutParams);
                }
            }
        }
        super.setImageDrawable(drawable);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        // 不用设置，因为父类会走setImageDrawable()方法
        // setImageDrawable(new BitmapDrawable(mContext.getResources(), bm));
        super.setImageBitmap(bm);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // 父View限制ImageView最大的宽高
        maxWidthSize = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
                - getPaddingRight();
        maxHeightSize = MeasureSpec.getSize(heightMeasureSpec)
                - getPaddingBottom() - getPaddingTop();

        // 判断是否固定其中一项求另一项
        if (MeasureSpec.EXACTLY == widthMode
                && MeasureSpec.EXACTLY != heightMode) {
            state = STATE_NEED_HEIGHT;
            // 如果宽精确，高不精确，则动态初始化设置高
            int height = (int) (maxWidthSize * dafaultRatio + 0.5);
            int totalHeight = height + getPaddingBottom() + getPaddingTop();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight,
                    MeasureSpec.EXACTLY);
        } else if (MeasureSpec.EXACTLY == heightMode
                && MeasureSpec.EXACTLY != widthMode) {
            state = STATE_NEED_WIDTH;
            // 如果高精确，宽不精确，则动态初始化设置高
            int width = (int) (maxHeightSize * dafaultRatio + 0.5);
            int totalWidth = width + getPaddingBottom() + getPaddingTop();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(totalWidth,
                    MeasureSpec.EXACTLY);
        } else {
            state = STATE_NO_NEED;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
