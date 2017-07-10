package com.linsh.lshutils.view.albumview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.linsh.lshutils.R;
import com.linsh.lshutils.module.Image;
import com.linsh.lshutils.utils.LshScreenUtils;
import com.linsh.lshutils.utils.LshUnitConverseUtils;

import java.util.List;

/**
 * Created by Senh Linsh on 17/6/26.
 */

public abstract class BaseAlbumView<T extends Image> extends GridView {

    protected int itemSize;
    protected CustomImageSelectAdapter mAdapter;

    public BaseAlbumView(Context context) {
        super(context);
        initParent();
    }

    public BaseAlbumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParent();
    }

    public BaseAlbumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParent();
    }

    protected void initParent() {
        int suggestSize = LshUnitConverseUtils.dp2px(120);
        int widthPixels = LshScreenUtils.getScreenWidth();
        int num = Math.round(widthPixels * 1f / suggestSize);
        itemSize = widthPixels / (num == 0 ? 1 : num);
        setNumColumns(num == 0 ? 1 : num);

        mAdapter = new CustomImageSelectAdapter();
        setAdapter(mAdapter);
    }

    public void setPhotos(List<? extends T> images) {
        mAdapter.setData(images);
    }

    public List<? extends T> getPhotos() {
        return mAdapter.getData();
    }

    protected abstract void setView(T t, ViewHolder viewHolder, int position);

    protected class CustomImageSelectAdapter extends BaseAdapter {

        private List<? extends T> photos;

        @Override
        public int getCount() {
            return photos == null ? 0 : photos.size();
        }

        @Override
        public T getItem(int position) {
            return photos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setData(List<? extends T> photos) {
            this.photos = photos;
        }

        public List<? extends T> getData() {
            return photos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo_select, null);
                viewHolder = new ViewHolder();
                viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.iv_photo_select);
                viewHolder.vMask = convertView.findViewById(R.id.v_mask);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.ivPhoto.getLayoutParams().width = itemSize;
            viewHolder.ivPhoto.getLayoutParams().height = itemSize;
            viewHolder.vMask.getLayoutParams().width = itemSize;
            viewHolder.vMask.getLayoutParams().height = itemSize;

            setView(photos.get(position), viewHolder, position);
            return convertView;
        }
    }

    protected static class ViewHolder {
        ImageView ivPhoto;
        View vMask;
    }

}
