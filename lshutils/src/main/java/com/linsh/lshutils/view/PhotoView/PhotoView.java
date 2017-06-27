package com.linsh.lshutils.view.PhotoView;

import android.content.Context;
import android.util.AttributeSet;

import com.linsh.lshutils.module.Image;

/**
 * Created by Senh Linsh on 17/6/26.
 */

public class PhotoView extends BasePhotoView<Image> {

    public PhotoView(Context context) {
        super(context);
    }

    public PhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void setView(Image image, ViewHolder viewHolder, int position) {
        image.setImage(viewHolder.ivPhoto);
    }
}
