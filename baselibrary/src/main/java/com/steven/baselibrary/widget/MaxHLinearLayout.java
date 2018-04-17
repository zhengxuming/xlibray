package com.steven.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.steven.baselibrary.R;

/**
 * Created by Steven on 2017/7/29.
 */

public class MaxHLinearLayout extends LinearLayout {

    /**
     * 最大高度
     */
    private int maxHeight;

    public MaxHLinearLayout(Context context) {
        this(context, null);
    }

    public MaxHLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaxHLinearLayout);
        maxHeight = a.getDimensionPixelSize(R.styleable.MaxHLinearLayout_maxHeight, 0);
        a.recycle();
    }

    public int getListViewHeight() {
        return maxHeight;
    }

    public void setListViewHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(maxHeight > 0 ? maxHeight : (Integer.MAX_VALUE >> 2), MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
