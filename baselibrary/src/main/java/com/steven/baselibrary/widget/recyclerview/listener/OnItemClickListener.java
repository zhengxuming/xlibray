package com.steven.baselibrary.widget.recyclerview.listener;

import android.view.View;

import com.steven.baselibrary.widget.recyclerview.BaseQuickAdapter;


/**
 * Created by AllenCoder on 2016/8/03.
 *
 *
 * A convenience class to extend when you only want to OnItemClickListener for a subset
 * of all the SimpleClickListener. This implements all methods in the
 * {@link SimpleClickListener}
 */
public abstract  class OnItemClickListener<ADT,AD> extends SimpleClickListener {


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        onSimpleItemClick((AD)adapter,view,(ADT)adapter.getItem(position),position);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
    public abstract void onSimpleItemClick(AD adapter, View view, ADT item, int position);
}
