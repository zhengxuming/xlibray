package com.steven.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.orhanobut.logger.Logger;
import com.steven.baselibrary.R;

/**
 * Created by Steven on 2017/11/22.
 */

public class CustomEditView extends android.support.v7.widget.AppCompatEditText {

    public static final int CET_TOGGLE_VISIABLE = 1001;
    public static final int CET_CLEAR_TEXT = 1002;

    private Rect touchArea;
    private int clickType = CET_CLEAR_TEXT;

    public CustomEditView(Context context) {
        this(context, null);
    }

    public CustomEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.CustomEditView, defStyleAttr, 0);
        clickType = type.getInt(R.styleable.CustomEditView_click_type, CET_CLEAR_TEXT);
        type.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Drawable drawableRight = getCompoundDrawables()[2];
        if (null == drawableRight) return;
        int drawableWidth = drawableRight.getIntrinsicWidth();

        touchArea = new Rect();
        touchArea.left = getRight() - drawableWidth - getPaddingRight() * 2;
        touchArea.top = 0;
        touchArea.right = getRight();
        touchArea.bottom = getBottom() - getTop();

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int oldX = 0;
        int oldY = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = (int) event.getX();
                oldY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();

                Logger.t("frag").i("x----->%s----y---->%s", x, y);

                if (touchArea.contains(x, y) && touchArea.contains(oldX, oldY)) {

                    Logger.t("frag").i("xy inside");

                    switch (clickType) {
                        case CET_TOGGLE_VISIABLE:
                            if (getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                                setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            } else {
                                setTransformationMethod(PasswordTransformationMethod.getInstance());
                            }
                            break;
                        case CET_CLEAR_TEXT:
                            setText("");
                            break;
                    }

                } else {
                    Logger.t("frag").i("xy out");
                }

                break;
        }

        return true;
    }
}
