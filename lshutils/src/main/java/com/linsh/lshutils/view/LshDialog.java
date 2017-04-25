package com.linsh.lshutils.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.linsh.lshutils.R;

import java.util.List;

/**
 * Created by linsh on 17/1/31.
 * <p>
 * 自己开发的提示窗口, 可支持文字/输入框/单选/列表
 */
public class LshDialog extends Dialog {
    private BaseDialogBuilder mBuilder;

    public LshDialog(Context context) {
        super(context);
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lsh);
        mBuilder.initView(this);
    }

    public TextDialogBuilder buildText() {
        TextDialogBuilder textDialogBuilder = new TextDialogBuilder();
        mBuilder = textDialogBuilder;
        return textDialogBuilder;
    }

    public ListDialogBuilder buildList() {
        ListDialogBuilder listDialogBuilder = new ListDialogBuilder();
        mBuilder = listDialogBuilder;
        return listDialogBuilder;
    }

    public SelectDialogBuilder buildSelect() {
        SelectDialogBuilder selectDialogBuilder = new SelectDialogBuilder();
        mBuilder = selectDialogBuilder;
        return selectDialogBuilder;
    }

    public InputDialogBuilder buildInput() {
        InputDialogBuilder inputDialogBuilder = new InputDialogBuilder();
        mBuilder = inputDialogBuilder;
        return inputDialogBuilder;
    }

    private abstract class BaseDialogBuilder<T extends BaseDialogBuilder> implements BaseDialogInterface {
        private String title;

        @Override
        public T setTitle(String title) {
            this.title = title;
            return (T) this;
        }

        @Override
        public LshDialog show() {
            LshDialog.this.show();
            return LshDialog.this;
        }

        protected void initView(LshDialog dialog) {
            // 设置标题
            TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_dialog_lsh_title);
            if (isEmpty(title)) {
                tvTitle.setVisibility(View.GONE);
            } else {
                tvTitle.setText(title);
            }
        }

        // 根据子类需要添加不同布局
        protected void addView(LshDialog dialog, View childView) {
            FrameLayout layout = (FrameLayout) dialog.findViewById(R.id.fl_dialog_lsh_content);
            layout.addView(childView);
        }

        // 设置确认取消按钮是否可见
        protected void setBtnLayoutVisible() {
            findViewById(R.id.ll_dialog_lsh_btn_layout).setVisibility(View.VISIBLE);
        }

        // 设置窗口宽度占屏幕短边的百分比
        protected void setDialogWidth(LshDialog dialog, float shortSidePercent) {
            ViewGroup rootView = (ViewGroup) dialog.findViewById(R.id.ll_dialog_lsh_root);
            ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
            layoutParams.width = (int) (getScreenShortSide() * shortSidePercent);
        }
    }

    private class BtnDialogBuilder<T extends BtnDialogBuilder> extends BaseDialogBuilder<T> implements BtnDialogInterface {
        private String positiveText;
        private String negativeText;
        private OnPositiveListener mOnPositiveListener;
        private OnNegativeListener mOnNegativeListener;
        private boolean positiveBtnVisible;
        private boolean negativeBtnVisible;

        @Override
        public T setPositiveButton(String positiveText, OnPositiveListener listener) {
            this.positiveText = positiveText;
            mOnPositiveListener = listener;
            positiveBtnVisible = true;
            return (T) this;
        }

        @Override
        public T setNegativeButton(String negativeText, OnNegativeListener listener) {
            this.negativeText = negativeText;
            mOnNegativeListener = listener;
            negativeBtnVisible = true;
            return (T) this;
        }

        @Override
        protected void initView(final LshDialog dialog) {
            super.initView(dialog);
            // 设置布局宽度为屏幕短边的3/4
            setDialogWidth(dialog, 0.75F);
            // 判断是否需要确认取消按钮
            if (!positiveBtnVisible && !negativeBtnVisible) return;
            setBtnLayoutVisible();
            // 设置确认取消按钮
            TextView tvPositive = (TextView) dialog.findViewById(R.id.tv_dialog_lsh_positive);
            TextView tvNegative = (TextView) dialog.findViewById(R.id.tv_dialog_lsh_negative);
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
                            mOnPositiveListener.onClick(LshDialog.this);
                        } else {
                            LshDialog.this.dismiss();
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
                            mOnNegativeListener.onClick(LshDialog.this);
                        } else {
                            LshDialog.this.dismiss();
                        }
                    }
                });
            } else {
                tvNegative.setVisibility(View.GONE);
            }
        }
    }

    private class NoBtnDialogBuilder<T extends NoBtnDialogBuilder> extends BaseDialogBuilder<T> {
        @Override
        protected void initView(LshDialog dialog) {
            super.initView(dialog);
            // 设置布局宽度为屏幕短边的3/5
            setDialogWidth(dialog, 0.6F);
        }
    }

    public class TextDialogBuilder extends BtnDialogBuilder<TextDialogBuilder> implements TextDialogInterface {
        private String content;

        @Override
        public TextDialogBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        @Override
        protected void initView(LshDialog dialog) {
            super.initView(dialog);
            // 生成TextView
            TextView textView = new TextView(dialog.getContext());
            textView.setText(content == null ? "" : content);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            addView(dialog, textView);
        }
    }

    public class ListDialogBuilder extends NoBtnDialogBuilder<ListDialogBuilder> implements ListDialogInterface<ListDialogBuilder, String> {
        private List<String> list;
        private OnItemClickListener mOnItemClickListener;


        @Override
        public ListDialogBuilder setList(List<String> list) {
            this.list = list;
            return this;
        }

        @Override
        public ListDialogBuilder setOnItemClickListener(OnItemClickListener listener) {
            mOnItemClickListener = listener;
            return this;
        }

        @Override
        protected void initView(LshDialog dialog) {
            super.initView(dialog);
            // 生成RecyclerView
            RecyclerView recyclerView = new RecyclerView(dialog.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            recyclerView.setLayoutParams(params);
            recyclerView.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
            recyclerView.setAdapter(new ListDialogAdapter(list, mOnItemClickListener));
            addView(dialog, recyclerView);
        }
    }

    private class ListDialogAdapter extends RecyclerView.Adapter implements View.OnClickListener {
        private List<String> data;
        private OnItemClickListener mOnItemClickListener;

        public ListDialogAdapter(List<String> list, OnItemClickListener listener) {
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
                mOnItemClickListener.onClick(LshDialog.this, position);
            }
        }
    }

    public class SelectDialogBuilder extends NoBtnDialogBuilder<SelectDialogBuilder> implements SelectDialogInterface {
        private String[] items;
        private OnItemClickListener mOnItemClickListener;

        @Override
        public SelectDialogBuilder setItem(String[] items) {
            this.items = items;
            return this;
        }

        @Override
        public SelectDialogBuilder setOnItemClickListener(OnItemClickListener listener) {
            mOnItemClickListener = listener;
            return this;
        }

        @Override
        protected void initView(LshDialog dialog) {
            super.initView(dialog);
            // 生成RadioGroup
            RadioGroup radioGroup = new RadioGroup(dialog.getContext());
            radioGroup.setPadding(dp2px(10), 0, dp2px(10), 0);
            // 添加RadioButton
            if (items != null && items.length > 0) {
                for (int i = 0; i < items.length; i++) {
                    // 设置RadioButton的参数
                    RadioButton radioButton = new RadioButton(dialog.getContext());
                    radioButton.setText(items[i]);
                    radioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    radioButton.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    radioGroup.addView(radioButton);
                    if (mOnItemClickListener != null) {
                        final int finalI = i;
                        radioButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnItemClickListener.onClick(LshDialog.this, finalI);
                            }
                        });
                    }
                    // 添加分割线
                    if (i != items.length - 1) {
                        View line = new View(dialog.getContext());
                        line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                        line.setBackgroundColor(Color.parseColor("#D9D9D9"));
                        radioGroup.addView(line);
                    }
                }
            }
            addView(dialog, radioGroup);
        }
    }

    public class InputDialogBuilder extends BaseDialogBuilder<InputDialogBuilder> implements InputDialogInterface {
        private String hint;
        private String positiveText;
        private String negativeText;
        private OnInputPositiveListener mOnInputPositiveListener;
        private OnInputNegativeListener mOnInputNegativeListener;
        private boolean positiveBtnVisible;
        private boolean negativeBtnVisible;

        @Override
        public InputDialogBuilder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        @Override
        public InputDialogBuilder setPositiveButton(String positiveText, OnInputPositiveListener listener) {
            this.positiveText = positiveText;
            mOnInputPositiveListener = listener;
            positiveBtnVisible = true;
            return this;
        }

        @Override
        public InputDialogBuilder setNegativeButton(String negativeText, OnInputNegativeListener listener) {
            this.negativeText = negativeText;
            mOnInputNegativeListener = listener;
            negativeBtnVisible = true;
            return this;
        }

        @Override
        protected void initView(LshDialog dialog) {
            super.initView(dialog);
            // 添加输入框
            final EditText editText = new EditText(dialog.getContext());
            if (!isEmpty(hint)) {
                editText.setHint(hint);
            }
            editText.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            addView(dialog, editText);

            // 是否显示确认取消按钮
            if (!positiveBtnVisible && !negativeBtnVisible) return;
            setBtnLayoutVisible();
            // 设置确认取消按钮文字和点击监听
            TextView tvPositive = (TextView) dialog.findViewById(R.id.tv_dialog_lsh_positive);
            TextView tvNegative = (TextView) dialog.findViewById(R.id.tv_dialog_lsh_negative);
            if (positiveBtnVisible) {
                if (!isEmpty(positiveText)) {
                    tvPositive.setText(positiveText);
                } else {
                    tvPositive.setText("确认");
                }
                tvPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnInputPositiveListener != null) {
                            mOnInputPositiveListener.onClick(LshDialog.this, editText.getText().toString());
                        } else {
                            LshDialog.this.dismiss();
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
                        if (mOnInputNegativeListener != null) {
                            mOnInputNegativeListener.onClick(LshDialog.this, editText.getText().toString());
                        } else {
                            LshDialog.this.dismiss();
                        }
                    }
                });
            } else {
                tvNegative.setVisibility(View.GONE);
            }
        }
    }

    private interface BaseDialogInterface<T extends BaseDialogBuilder> {
        T setTitle(String title);

        LshDialog show();
    }

    private interface BtnDialogInterface<T extends BtnDialogBuilder> {
        T setPositiveButton(String positiveText, OnPositiveListener listener);

        T setNegativeButton(String negativeText, OnNegativeListener listener);
    }

    private interface TextDialogInterface<T extends TextDialogBuilder> extends BtnDialogInterface {
        T setContent(String content);
    }

    private interface ListDialogInterface<T extends ListDialogBuilder, S> extends BaseDialogInterface {
        T setList(List<S> list);

        T setOnItemClickListener(OnItemClickListener listener);
    }

    private interface SelectDialogInterface<T extends SelectDialogBuilder> extends BaseDialogInterface {
        T setItem(String[] items);

        T setOnItemClickListener(OnItemClickListener listener);
    }

    private interface InputDialogInterface<T extends InputDialogBuilder> extends BaseDialogInterface {
        T setHint(String hint);

        T setPositiveButton(String positiveText, OnInputPositiveListener listener);

        T setNegativeButton(String negativeText, OnInputNegativeListener listener);
    }

    public interface OnPositiveListener {
        void onClick(LshDialog dialog);
    }

    public interface OnNegativeListener {
        void onClick(LshDialog dialog);
    }

    private interface OnInputPositiveListener {
        void onClick(LshDialog dialog, String inputText);
    }

    private interface OnInputNegativeListener {
        void onClick(LshDialog dialog, String inputText);
    }

    private interface OnItemClickListener {
        void onClick(LshDialog dialog, int item);
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
}
