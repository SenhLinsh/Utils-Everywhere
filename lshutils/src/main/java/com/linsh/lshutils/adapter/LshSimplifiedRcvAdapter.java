package com.linsh.lshutils.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2017/11/14
 *    desc   : 简化的 RecyclerView Adapter 基类, 可直接使用内部类创建继承的子类实现 Adapter 功能
 * </pre>
 */
public abstract class LshSimplifiedRcvAdapter<T> extends RecyclerView.Adapter<LshSimplifiedRcvAdapter.LshSimplifiedViewHolder> {

    private List<T> list;
    private int layoutId;

    public LshSimplifiedRcvAdapter(int layoutId, List<T> list) {
        this.layoutId = layoutId;
        this.list = list;
    }

    @Override
    public LshSimplifiedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        LshSimplifiedViewHolder viewHolder = new LshSimplifiedViewHolder(itemView);
        if (mOnItemClickListener != null) {
            viewHolder.setOnItemClickListener(new OnItemClickListener<Void>() {
                @Override
                public void onItemClick(LshSimplifiedViewHolder viewHolder, Void data, int position) {
                    mOnItemClickListener.onItemClick(viewHolder, list.get(position), position);
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LshSimplifiedViewHolder holder, int position) {
        T data = list.get(position);
        onBindViewHolder(holder, data, position);
    }

    protected abstract void onBindViewHolder(LshSimplifiedViewHolder holder, T data, int position);

    public void setData(List<T> list) {
        this.list = list;
    }

    public List<T> getData() {
        return list;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private OnItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(LshSimplifiedViewHolder viewHolder, T data, int position);
    }

    public static class LshSimplifiedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnItemClickListener<Void> mOnItemClickListener;
        private SparseArray<View> mViews;
        private int curItemPosition = -1;

        public LshSimplifiedViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
            itemView.setOnClickListener(this);
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public LshSimplifiedViewHolder setText(int viewId, String text) {
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }

        public LshSimplifiedViewHolder setImageResource(int viewId, int resId) {
            ImageView view = getView(viewId);
            view.setImageResource(resId);
            return this;
        }

        public LshSimplifiedViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
            ImageView view = getView(viewId);
            view.setImageBitmap(bitmap);
            return this;
        }

        public LshSimplifiedViewHolder setImageDrawable(int viewId, Drawable drawable) {
            ImageView view = getView(viewId);
            view.setImageDrawable(drawable);
            return this;
        }

        public LshSimplifiedViewHolder setBackgroundColor(int viewId, int color) {
            View view = getView(viewId);
            view.setBackgroundColor(color);
            return this;
        }

        public LshSimplifiedViewHolder setBackgroundRes(int viewId, int backgroundRes) {
            View view = getView(viewId);
            view.setBackgroundResource(backgroundRes);
            return this;
        }

        public LshSimplifiedViewHolder setTextColor(int viewId, int textColor) {
            TextView view = getView(viewId);
            view.setTextColor(textColor);
            return this;
        }

        public LshSimplifiedViewHolder setTextColorRes(int viewId, int textColorRes) {
            TextView view = getView(viewId);
            view.setTextColor(view.getContext().getResources().getColor(textColorRes));
            return this;
        }

        @SuppressLint("NewApi")
        public LshSimplifiedViewHolder setAlpha(int viewId, float value) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                getView(viewId).setAlpha(value);
            } else {
                // Pre-honeycomb hack to set Alpha value
                AlphaAnimation alpha = new AlphaAnimation(value, value);
                alpha.setDuration(0);
                alpha.setFillAfter(true);
                getView(viewId).startAnimation(alpha);
            }
            return this;
        }

        public LshSimplifiedViewHolder setVisible(int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
            return this;
        }

        public LshSimplifiedViewHolder linkify(int viewId) {
            TextView view = getView(viewId);
            Linkify.addLinks(view, Linkify.ALL);
            return this;
        }

        public LshSimplifiedViewHolder setTypeface(Typeface typeface, int... viewIds) {
            for (int viewId : viewIds) {
                TextView view = getView(viewId);
                view.setTypeface(typeface);
                view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
            }
            return this;
        }

        public LshSimplifiedViewHolder setProgress(int viewId, int progress) {
            ProgressBar view = getView(viewId);
            view.setProgress(progress);
            return this;
        }

        public LshSimplifiedViewHolder setProgress(int viewId, int progress, int max) {
            ProgressBar view = getView(viewId);
            view.setMax(max);
            view.setProgress(progress);
            return this;
        }

        public LshSimplifiedViewHolder setMax(int viewId, int max) {
            ProgressBar view = getView(viewId);
            view.setMax(max);
            return this;
        }

        public LshSimplifiedViewHolder setRating(int viewId, float rating) {
            RatingBar view = getView(viewId);
            view.setRating(rating);
            return this;
        }

        public LshSimplifiedViewHolder setRating(int viewId, float rating, int max) {
            RatingBar view = getView(viewId);
            view.setMax(max);
            view.setRating(rating);
            return this;
        }

        public LshSimplifiedViewHolder setTag(int viewId, Object tag) {
            View view = getView(viewId);
            view.setTag(tag);
            return this;
        }

        public LshSimplifiedViewHolder setTag(int viewId, int key, Object tag) {
            View view = getView(viewId);
            view.setTag(key, tag);
            return this;
        }

        public LshSimplifiedViewHolder setChecked(int viewId, boolean checked) {
            Checkable view = (Checkable) getView(viewId);
            view.setChecked(checked);
            return this;
        }

        public void setOnItemClickListener(OnItemClickListener<Void> listener) {
            mOnItemClickListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(this, null, getAdapterPosition());
            }
        }
    }
}
