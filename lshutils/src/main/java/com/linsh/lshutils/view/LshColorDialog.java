package com.linsh.lshutils.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.linsh.lshutils.R;
import com.linsh.lshutils.adapter.LshRecyclerViewAdapter;
import com.linsh.lshutils.utils.LshArrayUtils;
import com.linsh.lshutils.utils.LshXmlCreater;

import java.util.List;

/**
 * Created by Senh Linsh on 17/3/7.
 * <p>
 * 自己开发的提示窗口, 可支持文字/输入框/单选/列表
 */

public class LshColorDialog extends Dialog {

    private LshColorDialog.BaseDialogBuilder mBuilder;
    private static int bgColor = 0xFF00809C;

    public LshColorDialog(Context context) {
        super(context, R.style.lsh_color_dialog);
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_lsh_color);
        mBuilder.initView(this);
    }

    public LshColorDialog.TextDialogBuilder buildText() {
        LshColorDialog.TextDialogBuilder textDialogBuilder = new LshColorDialog.TextDialogBuilder();
        mBuilder = textDialogBuilder;
        return textDialogBuilder;
    }

    public LshColorDialog.ListDialogBuilder buildList() {
        LshColorDialog.ListDialogBuilder listDialogBuilder = new LshColorDialog.ListDialogBuilder();
        mBuilder = listDialogBuilder;
        return listDialogBuilder;
    }

    public LshColorDialog.InputDialogBuilder buildInput() {
        LshColorDialog.InputDialogBuilder inputDialogBuilder = new LshColorDialog.InputDialogBuilder();
        mBuilder = inputDialogBuilder;
        return inputDialogBuilder;
    }

    private abstract class BaseDialogBuilder<T extends LshColorDialog.BaseDialogBuilder> implements LshColorDialog.BaseDialogInterface {
        private String title;
        protected int color = LshColorDialog.bgColor;

        @Override
        public T setTitle(String title) {
            this.title = title;
            return (T) this;
        }

        public T setBgColor(int color) {
            this.color = color;
            return (T) this;
        }

        @Override
        public LshColorDialog show() {
            LshColorDialog.this.show();
            return LshColorDialog.this;
        }

        protected void initView(LshColorDialog dialog) {
            // 设置标题
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_dialog_lsh_title);
            if (isEmpty(title)) {
                tvTitle.setVisibility(View.GONE);
            } else {
                tvTitle.setText(title);
            }
            // 设置背景
            View bgContentLayout = dialog.findViewById(R.id.ll_dialog_lsh_bg_layout);
            setBgContent(bgContentLayout, color);
        }

        // 根据子类需要添加不同布局
        protected void addView(LshColorDialog dialog, View childView) {
            FrameLayout layout = (FrameLayout) dialog.findViewById(R.id.fl_dialog_lsh_content);
            layout.addView(childView);
        }

        // 设置确认取消按钮是否可见
        protected void setBtnLayoutVisible() {
            findViewById(R.id.ll_dialog_lsh_btn_layout).setVisibility(View.VISIBLE);
        }

        // 设置窗口宽度占屏幕短边的百分比
        protected void setDialogWidth(LshColorDialog dialog, float shortSidePercent) {
            ViewGroup rootView = (ViewGroup) dialog.findViewById(R.id.ll_dialog_lsh_root);
            ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
            layoutParams.width = (int) (getScreenShortSide() * shortSidePercent);
        }
    }

    private abstract class BtnDialogBuilder<T extends BtnDialogBuilder, P extends OnBasePositiveListener, N extends OnBaseNegativeListener>
            extends BaseDialogBuilder<T> implements BtnDialogInterface<T, P, N> {
        private String positiveText;
        private String negativeText;
        private P mOnPositiveListener;
        private N mOnNegativeListener;
        private boolean positiveBtnVisible;
        private boolean negativeBtnVisible;

        @Override
        public T setPositiveButton(String positiveText, P listener) {
            this.positiveText = positiveText;
            mOnPositiveListener = listener;
            positiveBtnVisible = true;
            return (T) this;
        }

        @Override
        public T setNegativeButton(String negativeText, N listener) {
            this.negativeText = negativeText;
            mOnNegativeListener = listener;
            negativeBtnVisible = true;
            return (T) this;
        }

        @Override
        protected void initView(final LshColorDialog dialog) {
            super.initView(dialog);
            // 判断是否需要确认取消按钮
            if (!positiveBtnVisible && !negativeBtnVisible) {
                return;
            }
            setBtnLayoutVisible();

            // 设置确认取消按钮
            TextView tvPositive = (TextView) dialog.findViewById(R.id.tv_dialog_lsh_positive);
            TextView tvNegative = (TextView) dialog.findViewById(R.id.tv_dialog_lsh_negative);
            if (positiveBtnVisible && negativeBtnVisible) {
                setBgLeftBtn(tvPositive);
                setBgRightBtn(tvNegative);
            } else {
                dialog.findViewById(R.id.v_dialog_lsh_divider).setVisibility(View.GONE);
                if (positiveBtnVisible) {
                    setBgCenterBtn(tvPositive);
                } else {
                    setBgCenterBtn(tvNegative);
                }
            }
            if (positiveBtnVisible) {
                if (!isEmpty(positiveText)) {
                    tvPositive.setText(positiveText);
                } else {
                    tvPositive.setText("确认");
                }
                tvPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnPositiveListener != null) {
                            onPositiveClick(mOnPositiveListener);
                        } else {
                            LshColorDialog.this.dismiss();
                        }
                    }
                });
            } else {
                tvPositive.setVisibility(View.GONE);
            }
            if (negativeBtnVisible) {
                if (!isEmpty(negativeText)) {
                    tvNegative.setText(negativeText);
                } else {
                    tvNegative.setText("取消");
                }
                tvNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnNegativeListener != null) {
                            onNegativeClick(mOnNegativeListener);
                        } else {
                            LshColorDialog.this.dismiss();
                        }
                    }
                });
            } else {
                tvNegative.setVisibility(View.GONE);
            }
        }

        protected abstract void onPositiveClick(P onPositiveListener);

        protected abstract void onNegativeClick(N onNegativeListener);
    }

    public class TextDialogBuilder extends BtnDialogBuilder<TextDialogBuilder, OnPositiveListener, OnNegativeListener> implements TextDialogInterface {
        private String content;

        @Override
        public TextDialogBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        @Override
        protected void initView(LshColorDialog dialog) {
            super.initView(dialog);
            // 生成TextView
            TextView textView = new TextView(dialog.getContext());
            textView.setText(content == null ? "" : content);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            addView(dialog, textView);
        }

        @Override
        protected void onPositiveClick(OnPositiveListener onPositiveListener) {
            onPositiveListener.onClick(LshColorDialog.this);
        }

        @Override
        protected void onNegativeClick(OnNegativeListener onNegativeListener) {
            onNegativeListener.onClick(LshColorDialog.this);
        }
    }

    public class ListDialogBuilder extends BtnDialogBuilder<ListDialogBuilder, OnListPositiveListener<String>, OnListNegativeListener<String>>
            implements ListDialogInterface<ListDialogBuilder, String> {

        private List<String> list;
        private LshColorDialog.OnItemClickListener mOnItemClickListener;
        CustomListDialogAdapter.ListDialogAdapterListener mListDialogAdapterListener;
        private int layoutId;
        private int curClickedItem = -1;

        @Override
        public LshColorDialog.ListDialogBuilder setList(List<String> list) {
            this.list = list;
            return this;
        }

        public LshColorDialog.ListDialogBuilder setList(String[] list) {
            this.list = LshArrayUtils.toList(list);
            return this;
        }

        public LshColorDialog.ListDialogBuilder setCustomItem(int layoutId, CustomListDialogAdapter.ListDialogAdapterListener listener) {
            this.layoutId = layoutId;
            mListDialogAdapterListener = listener;
            return this;
        }

        @Override
        public LshColorDialog.ListDialogBuilder setOnItemClickListener(LshColorDialog.OnItemClickListener listener) {
            mOnItemClickListener = listener;
            return this;
        }

        @Override
        protected void initView(LshColorDialog dialog) {
            super.initView(dialog);
            // 生成RecyclerView
            RecyclerView recyclerView = new RecyclerView(dialog.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            recyclerView.setLayoutParams(params);
            recyclerView.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
            if (layoutId != 0) {
                CustomListDialogAdapter<String> adapter = new CustomListDialogAdapter<>(recyclerView, layoutId, list, mListDialogAdapterListener);
                if (mOnItemClickListener != null) {
                    adapter.setOnItemClickListener(new LshRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(LshRecyclerViewAdapter.LshViewHolder viewHolder, int position) {
                            curClickedItem = position;
                            mOnItemClickListener.onClick(LshColorDialog.this, viewHolder, position);
                        }
                    });
                }
                recyclerView.setAdapter(adapter);
            } else {
                recyclerView.setAdapter(new LshColorDialog.ListDialogAdapter(list, new OnItemClickListener() {
                    @Override
                    public void onClick(LshColorDialog dialog, LshRecyclerViewAdapter.LshViewHolder viewHolder, int item) {
                        curClickedItem = item;
                        mOnItemClickListener.onClick(dialog, viewHolder, item);
                    }
                }));
            }
            addView(dialog, recyclerView);
        }

        @Override
        protected void onPositiveClick(OnListPositiveListener<String> onPositiveListener) {
            String data = curClickedItem < 0 ? null : list.get(curClickedItem);
            onPositiveListener.onClick(LshColorDialog.this, data, curClickedItem);
        }

        @Override
        protected void onNegativeClick(OnListNegativeListener<String> onNegativeListener) {
            String data = curClickedItem < 0 ? null : list.get(curClickedItem);
            onNegativeListener.onClick(LshColorDialog.this, data, curClickedItem);
        }
    }

    public static class CustomListDialogAdapter<T> extends LshRecyclerViewAdapter<T> {
        private ListDialogAdapterListener mListDialogAdapterListener;

        public CustomListDialogAdapter(RecyclerView recyclerView, int layoutId, List list, ListDialogAdapterListener listener) {
            super(recyclerView, layoutId, list);
            mListDialogAdapterListener = listener;
        }

        @Override
        public void onBindViewHolder(LshViewHolder holder, int position) {
            if (mListDialogAdapterListener != null) {
                mListDialogAdapterListener.onBindViewHolder(holder, position);
            }
        }

        public interface ListDialogAdapterListener {
            void onBindViewHolder(LshViewHolder holder, int position);
        }
    }

    private class ListDialogAdapter extends RecyclerView.Adapter implements View.OnClickListener {
        private List<String> data;
        private LshColorDialog.OnItemClickListener mOnItemClickListener;

        public ListDialogAdapter(List<String> list, LshColorDialog.OnItemClickListener listener) {
            data = list;
            mOnItemClickListener = listener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 生成LinearLayout布局
            LinearLayout linearLayout = new LinearLayout(parent.getContext());
            linearLayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(dp2px(10), 0, dp2px(10), 0);
            // 添加TextView
            TextView textView = new TextView(parent.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            int dp5 = dp2px(5);
            textView.setPadding(dp5, dp5, dp5, dp5);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            // 添加分割线
            View line = new View(parent.getContext());
            line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            line.setBackgroundColor(Color.parseColor("#D9D9D9"));
            // 添加到布局
            linearLayout.addView(textView);
            linearLayout.addView(line);
            return new RecyclerView.ViewHolder(linearLayout) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            LinearLayout layout = (LinearLayout) holder.itemView;
            TextView textView = (TextView) layout.getChildAt(0);
            textView.setText(data.get(position));
            layout.setTag(position);
            layout.setOnClickListener(this);
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                int position = (int) v.getTag();
                mOnItemClickListener.onClick(LshColorDialog.this, null, position);
            }
        }
    }

    public class InputDialogBuilder extends BtnDialogBuilder<InputDialogBuilder, OnInputPositiveListener, OnInputNegativeListener>
            implements LshColorDialog.InputDialogInterface {

        private String hint;
        private EditText mEditText;

        @Override
        public LshColorDialog.InputDialogBuilder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        @Override
        protected void initView(LshColorDialog dialog) {
            super.initView(dialog);
            // 添加输入框
            mEditText = new EditText(dialog.getContext());
            if (!isEmpty(hint)) {
                mEditText.setHint(hint);
            }
            mEditText.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            addView(dialog, mEditText);
        }

        @Override
        protected void onPositiveClick(OnInputPositiveListener onPositiveListener) {
            onPositiveListener.onClick(LshColorDialog.this, mEditText.getText().toString());
        }

        @Override
        protected void onNegativeClick(OnInputNegativeListener onNegativeListener) {
            onNegativeListener.onClick(LshColorDialog.this, mEditText.getText().toString());
        }
    }

    private interface BaseDialogInterface<T extends LshColorDialog.BaseDialogBuilder> {
        T setTitle(String title);

        LshColorDialog show();
    }

    private interface BtnDialogInterface<T extends BtnDialogBuilder, P extends OnBasePositiveListener, N extends OnBaseNegativeListener> {
        T setPositiveButton(String positiveText, P listener);

        T setNegativeButton(String negativeText, N listener);
    }

    private interface TextDialogInterface<T extends LshColorDialog.TextDialogBuilder> {
        T setContent(String content);
    }

    private interface ListDialogInterface<T extends LshColorDialog.ListDialogBuilder, S> {
        T setList(List<S> list);

        T setOnItemClickListener(LshColorDialog.OnItemClickListener listener);
    }

    private interface InputDialogInterface<T extends LshColorDialog.InputDialogBuilder> {
        T setHint(String hint);

        T setPositiveButton(String positiveText, LshColorDialog.OnInputPositiveListener listener);

        T setNegativeButton(String negativeText, LshColorDialog.OnInputNegativeListener listener);
    }

    public interface OnBasePositiveListener {
    }

    public interface OnBaseNegativeListener {
    }

    public interface OnPositiveListener extends OnBasePositiveListener {
        void onClick(LshColorDialog dialog);
    }

    public interface OnNegativeListener extends OnBaseNegativeListener {
        void onClick(LshColorDialog dialog);
    }

    public interface OnListPositiveListener<T> extends OnBasePositiveListener {
        void onClick(LshColorDialog dialog, T t, int index);
    }

    public interface OnListNegativeListener<T> extends OnBaseNegativeListener {
        void onClick(LshColorDialog dialog, T t, int index);
    }

    private interface OnInputPositiveListener extends OnBasePositiveListener {
        void onClick(LshColorDialog dialog, String inputText);
    }

    private interface OnInputNegativeListener extends OnBaseNegativeListener {
        void onClick(LshColorDialog dialog, String inputText);
    }

    public interface OnItemClickListener {
        void onClick(LshColorDialog dialog, LshRecyclerViewAdapter.LshViewHolder viewHolder, int item);
    }

    //================================================ 工具方法 ================================================//
    private int dp2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getScreenShortSide() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return Math.min(outMetrics.widthPixels, outMetrics.heightPixels);
    }

    private boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    private void setBgContent(View view, int color) {
        int dp10 = dp2px(10);
        GradientDrawable bgContent = LshXmlCreater.createRectangleCorner(new float[]{dp10, dp10, dp10, dp10, 0, 0, 0, 0}, color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(bgContent);
        } else {
            view.setBackgroundDrawable(bgContent);
        }
    }

    private void setBgBtn(View view, float[] radii) {
        GradientDrawable bgContentWhite = LshXmlCreater.createRectangleCorner(radii, Color.WHITE);
        GradientDrawable bgContentGray = LshXmlCreater.createRectangleCorner(radii, 0xFFEDEDF3);
        StateListDrawable pressedSelector = LshXmlCreater.createPressedSelector(bgContentGray, bgContentWhite);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(pressedSelector);
        } else {
            view.setBackgroundDrawable(pressedSelector);
        }
    }

    private void setBgCenterBtn(View view) {
        int dp10 = dp2px(10);
        setBgBtn(view, new float[]{0, 0, 0, 0, dp10, dp10, dp10, dp10});
    }

    private void setBgLeftBtn(View view) {
        int dp10 = dp2px(10);
        setBgBtn(view, new float[]{0, 0, 0, 0, 0, 0, dp10, dp10});
    }

    private void setBgRightBtn(View view) {
        int dp10 = dp2px(10);
        setBgBtn(view, new float[]{0, 0, 0, 0, dp10, dp10, 0, 0});
    }

}
