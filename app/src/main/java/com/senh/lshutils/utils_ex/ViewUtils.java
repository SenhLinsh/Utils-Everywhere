package com.senh.lshutils.utils_ex;

import android.graphics.Point;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 视图工具箱
 */
public class ViewUtils {

    private ViewUtils() {
        throw new AssertionError();
    }

    /**
     * 根据所有条目计算ListView的高
     */
    public static int getListViewHeightBasedOnChildren(ListView view) {
        int height = getAbsListViewHeightBasedOnChildren(view);
        ListAdapter adapter;
        int adapterCount;
        if (view != null && (adapter = view.getAdapter()) != null &&
                (adapterCount = adapter.getCount()) > 0) {
            height += view.getDividerHeight() * (adapterCount - 1);
        }
        return height;
    }

    /**
     * 根据所有条目计算AbsListView的高
     */
    public static int getAbsListViewHeightBasedOnChildren(AbsListView view) {
        ListAdapter adapter;
        if (view == null || (adapter = view.getAdapter()) == null) {
            return 0;
        }

        int height = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i, null, view);
            if (item instanceof ViewGroup) {
                item.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
            }
            item.measure(0, 0);
            height += item.getMeasuredHeight();
        }
        height += view.getPaddingTop() + view.getPaddingBottom();
        return height;
    }


    /**
     * 获取View的高
     */
    public static void setViewHeight(View view, int height) {
        if (view == null) {
            return;
        }

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
    }


    //*****设置外边距相关函数*******************************************************************************

    /**
     * 设置View的左侧外边距
     */
    public static void setMarginLeft(View view, int left) {
        setMargins(view, left, 0, 0, 0);
    }

    /**
     * 设置View的顶部外边距
     */
    public static void setMarginTop(View view, int top) {
        setMargins(view, 0, top, 0, 0);
    }

    /**
     * 设置View的右侧外边距
     */
    public static void setMarginRight(View view, int right) {
        setMargins(view, 0, 0, right, 0);
    }

    /**
     * 设置View的底部外边距
     */
    public static void setMarginBottom(View view, int bottom) {
        setMargins(view, 0, 0, 0, bottom);
    }

    /**
     * 设置View的外边距(Margins)
     */
    public static void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view
                    .getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();       //请求重绘
        }
    }

    /**
     * 将给定视图的高度增加一点
     *
     * @param view            给定的视图
     * @param increasedAmount 增加多少
     */
    public static void addViewHeight(View view, int increasedAmount) {
        ViewGroup.LayoutParams headerLayoutParams
                = (ViewGroup.LayoutParams) view.getLayoutParams();
        headerLayoutParams.height += increasedAmount;
        view.setLayoutParams(headerLayoutParams);
    }

    /**
     * 设置给定视图的宽度
     */
    public static void setViewWidth(View view, int newWidth) {
        ViewGroup.LayoutParams headerLayoutParams
                = (ViewGroup.LayoutParams) view.getLayoutParams();
        headerLayoutParams.width = newWidth;
        view.setLayoutParams(headerLayoutParams);
    }


    /**
     * 将给定视图的宽度增加一点
     *
     * @param view            给定的视图
     * @param increasedAmount 增加多少
     */
    public static void addViewWidth(View view, int increasedAmount) {
        ViewGroup.LayoutParams headerLayoutParams
                = (ViewGroup.LayoutParams) view.getLayoutParams();
        headerLayoutParams.width += increasedAmount;
        view.setLayoutParams(headerLayoutParams);
    }

    /**
     * 设置输入框的光标到末尾
     */
    public static final void setEditTextSelectionToEnd(EditText editText) {
        Editable editable = editText.getEditableText();
        Selection.setSelection((Spannable) editable,
                editable.toString().length());
    }

    /**
     * 执行测量，执行完成之后只需调用View的getMeasuredXXX()方法即可获取测量结果
     */
    public static final View measure(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight,
                    View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
        return view;
    }

    /**
     * 获取给定视图的测量高度
     */
    public static final int getMeasuredHeight(View view) {
        return measure(view).getMeasuredHeight();
    }

    /**
     * 获取给定视图的测量宽度
     */
    public static final int getMeasuredWidth(View view) {
        return measure(view).getMeasuredWidth();
    }

    /**
     * 缩放视图
     */
    public static void zoomView(View view, float scale) {
        zoomView(view, scale, scale,
                new Point(view.getWidth(), view.getHeight()));
    }

    /**
     * 缩放视图
     */
    public static void zoomView(View view, float scaleX, float scaleY) {
        zoomView(view, scaleX, scaleY,
                new Point(view.getWidth(), view.getHeight()));
    }

    /**
     * 缩放视图
     */
    public static void zoomView(View view, float scaleX, float scaleY, Point originalSize) {
        int width = (int) (originalSize.x * scaleX);
        int height = (int) (originalSize.y * scaleY);
        ViewGroup.LayoutParams viewGroupParams = view.getLayoutParams();
        if (viewGroupParams != null) {
            viewGroupParams.width = width;
            viewGroupParams.height = height;
        } else {
            viewGroupParams = new ViewGroup.LayoutParams(width, height);
        }
        view.setLayoutParams(viewGroupParams);
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * 给View控件设置一个id，放心吧，这个id不会和R文件里的冲突的
     */
    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
