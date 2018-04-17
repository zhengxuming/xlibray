package com.steven.baselibrary.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.WindowManager;


/**
 * 工具类
 * Created by hzwangchenyan on 2016/1/6.
 */
public class ScreenUtils {

    public static int getScreenWidth(Context sContext) {
        WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
        if (null == wm) return -1;
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context sContext) {
        WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
        if (null == wm) return -1;
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取状态栏高度
     */
    public static int getSystemBarHeight(@Nullable Context sContext) {
        if (sContext == null) return 0;
        int result = 0;
        int resourceId = sContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = sContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int dp2px(@Nullable Context sContext, float dpValue) {
        if (sContext == null) return 0;

        final float scale = sContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(@Nullable Context sContext, float pxValue) {
        if (sContext == null) return 0;
        final float scale = sContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(@Nullable Context sContext, float spValue) {
        if (sContext == null) return 0;
        final float fontScale = sContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
