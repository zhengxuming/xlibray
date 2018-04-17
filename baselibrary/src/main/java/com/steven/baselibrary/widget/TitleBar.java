package com.steven.baselibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.steven.baselibrary.R;
import com.steven.baselibrary.utils.StringUtils;

/**
 * 通用标题栏
 * Created by Steven on 2017/7/11.
 */

public class TitleBar extends RelativeLayout implements View.OnClickListener {

    private ImageView ivBack;
    private TextView tvTitleText;
    private TextView tvTitleEnd;
    private ImageView ivTitleEnd;
    private TextView tvTitleNum;
    private int DEFAULT_TEXT_COLOR = 0;
    private Context context;
    private OnTitleEndClickListener endListener;
    private boolean showLeft = true;
    private boolean noBack = false;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs, defStyleAttr);
    }

    private void initViews(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.title_bar_layout, this, true);
        ivBack = (ImageView) rootView.findViewById(R.id.iv_back);
        tvTitleText = (TextView) rootView.findViewById(R.id.tv_title_text);
        tvTitleEnd = (TextView) rootView.findViewById(R.id.tv_title_end);
        ivTitleEnd = (ImageView) rootView.findViewById(R.id.iv_title_end);
        tvTitleNum= (TextView) rootView.findViewById(R.id.title_num_tv);
        ivBack.setOnClickListener(this);
        tvTitleEnd.setOnClickListener(this);
        ivTitleEnd.setOnClickListener(this);

        DEFAULT_TEXT_COLOR = getResources().getColor(R.color.white);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0);
        tvTitleText.setText(typedArray.getString(R.styleable.TitleBar_titleText));
        tvTitleText.setTextColor(typedArray.getColor(R.styleable.TitleBar_titleTextColorR, DEFAULT_TEXT_COLOR));
        String endText = typedArray.getString(R.styleable.TitleBar_endText);
        if (!TextUtils.isEmpty(endText)) {
            tvTitleEnd.setVisibility(VISIBLE);
            tvTitleEnd.setText(endText);
            tvTitleEnd.setTextColor(typedArray.getColor(R.styleable.TitleBar_endTextColorR, DEFAULT_TEXT_COLOR));
        } else {
            tvTitleEnd.setVisibility(GONE);
        }
        Drawable drawable = typedArray.getDrawable(R.styleable.TitleBar_endDrawable);
        if (null != drawable) {
            ivTitleEnd.setVisibility(VISIBLE);
            ivTitleEnd.setImageDrawable(drawable);
        } else {
            ivTitleEnd.setVisibility(GONE);
        }
        Drawable drawableStart = typedArray.getDrawable(R.styleable.TitleBar_startDrawable);
        if (null != drawableStart) {
            ivBack.setImageDrawable(drawableStart);
        }
        showLeft = typedArray.getBoolean(R.styleable.TitleBar_showLeft, true);
        ivBack.setVisibility(showLeft ? VISIBLE : GONE);

        typedArray.recycle();
    }

    /**
     * 设置title左边的图标
     * @param res
     */
    public void setTitleStartDrawable(int res) {
        ivBack.setImageResource(res);
    }

    /**
     * 设置标题栏右边的提示数字
     * @param num
     */
    public void setTitleNum(String num){
        if (StringUtils.isEmpty(num)||num.equals("0")){
            tvTitleNum.setVisibility(GONE);
            return;
        }
        tvTitleNum.setVisibility(VISIBLE);
        if (Integer.valueOf(num)>99){
            tvTitleNum.setText("99+");
        }else{
            tvTitleNum.setText(num);
        }
    }

    /**
     * title text
     * @param text
     */
    public void setTitleText(String text) {
        if (!TextUtils.isEmpty(text)) {
            tvTitleText.setText(text);
        }
    }

    /**
     * title右边的文字
     * @param text
     */
    public void setTitleEndText(String text) {
        if (!TextUtils.isEmpty(text)) {
            tvTitleEnd.setText(text);
            tvTitleEnd.setVisibility(VISIBLE);
        }
    }

    /**
     * title右边的图标
     * @param res
     */
    public void setTitleEndDrawable(int res) {
        ivTitleEnd.setVisibility(VISIBLE);
        ivTitleEnd.setImageResource(res);
    }

    /**
     * 是否显示title右边的文字
     * @param show
     */
    public void showTitleEndText(boolean show) {
        tvTitleEnd.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * 是否显示title右边的图标
     * @param show
     */
    public void showTitleEndImg(boolean show) {
        ivTitleEnd.setVisibility(show ? VISIBLE : GONE);
    }

    public interface OnTitleEndClickListener {
        void onTitleEndClick(View view);
    }

    public void setOnTitleEndClickListener(OnTitleEndClickListener endListener) {
        this.endListener = endListener;
    }

    /**
     * 不做返回事件
     *
     * @param noBack
     */
    public void setNoBack(boolean noBack) {
        this.noBack = noBack;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            if (noBack) {
                if (null != endListener) {
                    endListener.onTitleEndClick(view);
                }
            } else {
                ((Activity) context).finish();
            }
        } else if (id == R.id.iv_title_end || id == R.id.tv_title_end) {
            if (null != endListener) {
                endListener.onTitleEndClick(view);
            }
        }
    }
}
