package com.zxm.xlibray.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义可禁止滑动的viewpager
 * Created by zxm on 2017/6/12.
 */

public class CustomViewpager extends ViewPager {

    private boolean canScroll = true;

    public CustomViewpager(Context context) {
        super(context);
    }

    public CustomViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canScroll ? super.onTouchEvent(ev) : false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canScroll ? super.onInterceptTouchEvent(ev) : false;
    }
}
