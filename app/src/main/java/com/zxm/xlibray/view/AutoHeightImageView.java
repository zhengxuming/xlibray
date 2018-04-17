package com.zxm.xlibray.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class AutoHeightImageView extends android.support.v7.widget.AppCompatImageView {

    public AutoHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHeightImageView(Context context) {
        super(context);
    }

    public AutoHeightImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Drawable d = getDrawable();

        if(d!=null){
            // ceil not round - avoid thin vertical gaps along the left/right edges
            int width = MeasureSpec.getSize(widthMeasureSpec);
            //高度根据使得图片的宽度充满屏幕计算而得
            int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            setMeasuredDimension(width, height);
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}