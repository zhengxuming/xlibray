package com.steven.baselibrary.widget.recyclerview.listener;

import android.view.View;

import com.steven.baselibrary.widget.recyclerview.BaseQuickAdapter;


/**
 * create by: allen on 16/8/3.
 */

public abstract class OnItemLongClickListener<ADT,AD> extends SimpleClickListener {




    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        onSimpleItemLongClick( (AD) adapter,  view,(ADT)adapter.getItem(position),  position);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
    }

    public abstract boolean onSimpleItemLongClick(AD adapter, View view, ADT item, int position);
}
