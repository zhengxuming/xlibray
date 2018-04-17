package com.steven.baselibrary.widget.recyclerview;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.steven.baselibrary.R;

/**
 * Created by Steven on 2017/7/12.
 */

public class BaseBindHolder extends BaseViewHolder {
    public BaseBindHolder(View view) {
        super(view);
    }

    public ViewDataBinding getBinding() {
        return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
    }
}
