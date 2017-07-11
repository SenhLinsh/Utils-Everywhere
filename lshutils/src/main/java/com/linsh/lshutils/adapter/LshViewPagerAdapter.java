package com.linsh.lshutils.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Senh Linsh on 17/7/11.
 */

public abstract class LshViewPagerAdapter<T> extends PagerAdapter {

    private List<T> data;

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(container, position);
        container.addView(view);
        return view;
    }

    protected abstract View getView(ViewGroup container, int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }
}
