package com.steven.baselibrary.widget.recyclerview;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steven.baselibrary.R;

import java.util.List;

/**
 * Created by Steven on 2017/7/12.
 */

public abstract class BaseBindAdapter<T> extends BaseQuickAdapter<T, BaseBindHolder> {

    private boolean isNested = false;

    public BaseBindAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        this(layoutResId, data, false);
    }

    /**
     *
     * @param layoutResId
     * @param data
     * @param isNested 是否被recyclerView嵌套
     */
    public BaseBindAdapter(@LayoutRes int layoutResId, @Nullable List<T> data, boolean isNested) {
        super(layoutResId, data);
        this.isNested = isNested;
    }

    public BaseBindAdapter(@Nullable List<T> data) {
        super(data);
    }

    public BaseBindAdapter(@Nullable List<T> data, boolean isNested) {
        super(data);
        this.isNested = isNested;
    }

    public BaseBindAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseBindHolder holder, T item) {
        ViewDataBinding binding = holder.getBinding();
        convert(holder, binding, item);
        binding.executePendingBindings();
    }

    protected abstract void convert(BaseBindHolder holder, ViewDataBinding binding, T item);

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        //这里第三个参数有点特别，当recyclerView嵌套recyclerView时，必须为null，其他情况下则必须为parent，否则宽度无法铺满，Fuck ni da ye！！！
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, isNested ? null : parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, null);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    @BindingAdapter("android:drawableRight")
    public static void setDrawableRight(TextView view, int resId) {
        if (resId == 0) {
            view.setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable drawable = ContextCompat.getDrawable(view.getContext(), resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        view.setCompoundDrawables(null, null, drawable, null);
    }

}
