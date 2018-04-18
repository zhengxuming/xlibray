package com.zxm.xlibray.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.steven.baselibrary.utils.ScreenUtils;
import com.steven.baselibrary.widget.MyToast;
import com.steven.baselibrary.widget.TitleBar;
import com.steven.baselibrary.widget.recyclerview.BaseQuickAdapter;
import com.steven.baselibrary.widget.recyclerview.WrapContentLinearLayoutManager;
import com.steven.baselibrary.widget.recyclerview.listener.OnItemChildClickListener;
import com.steven.baselibrary.widget.recyclerview.listener.OnItemChildLongClickListener;
import com.steven.baselibrary.widget.recyclerview.listener.OnItemClickListener;
import com.steven.baselibrary.widget.recyclerview.listener.OnItemLongClickListener;
import com.steven.baselibrary.widget.recyclerview.recyclerviewflexibledivider.GridSpacingItemDecoration;
import com.steven.baselibrary.widget.recyclerview.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zxm.xlibray.R;

import java.util.List;

/**
 * Created by Steven on 2017/7/12.
 */

public abstract class ListBaseFragment<ADT, AD extends BaseQuickAdapter> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public AD adapter;
    private View viewLoading;
    private View viewNoData;
    private View viewErro;
    public int currentPage = 1;
    protected int perPageSize = 10;
    public RecyclerView rv_list;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    int customEmptyViewLayout = com.steven.baselibrary.R.layout.empty_view;
    private String emptyMessage;
    private ViewGroup emptyView;
    /**
     * 是否显示没有更多数据View
     */
    private boolean isShowEnd = true;

    @Override
    public void initViews(View view) {
        rv_list = view.findViewById(com.steven.baselibrary.R.id.recycle_view);
        mSwipeRefreshLayout = view.findViewById(com.steven.baselibrary.R.id.refresh_view);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        viewLoading = inflater.inflate(com.steven.baselibrary.R.layout.loading_view, null);
        viewNoData = inflater.inflate(com.steven.baselibrary.R.layout.empty_view, null);
        viewErro = inflater.inflate(com.steven.baselibrary.R.layout.error_view, null);
        viewNoData.setOnClickListener(v -> {
            setViewLoading();
            getListData();
        });

    }

    public void initListView(AD madapter, RecyclerView.LayoutManager manager) {
        this.adapter = madapter;
        rv_list.setLayoutManager(manager);
        rv_list.setAdapter(adapter);
        setOnRefreshListener(this);
        this.adapter.setOnLoadMoreListener(this,rv_list);
        adapter.setEmptyView(viewLoading);
    }

    public void setViewLoading() {
        this.adapter.setEmptyView(viewLoading);
    }

    /**
     * 设置自定义EmptyView
     *
     * @param customEmptyViewProvider
     */
    public void setCustomEmptyViewProvider(CustomEmptyViewProvider customEmptyViewProvider) {
        this.customEmptyViewLayout = customEmptyViewProvider.getCustomEmptyViewLayout();
    }


    @Override
    public void onRefresh() {
        currentPage = 1;
        getListData();
        this.adapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {
        currentPage++;
        getListData();
    }

    public abstract void getListData();

    public interface CustomEmptyViewProvider {
        int getCustomEmptyViewLayout();
    }

    /**
     * 列表没有文字的时候显示的提示内容
     */
    public void setEmptyMessage(String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }


    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        if (hasRecyclerView())
            rv_list.addItemDecoration(itemDecoration);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.rv_list = recyclerView;
    }

    public void setAdapter(AD adapter) {
        this.adapter = adapter;
        setAdapter(adapter, null);

    }

    /**
     * @param adapter      RecyclerBaseAdapter
     * @param emptyMessage 没有数据的时候显示的文字消息
     */
    public void setAdapter(AD adapter, String emptyMessage) {
        if (hasRecyclerView()) {
            if (emptyMessage != null) {
                this.emptyMessage = emptyMessage;
                emptyView = getEmptyView();
            } else if (emptyView == null) {
                emptyView = getEmptyView();
            }
            adapter.bindToRecyclerView(rv_list);
            rv_list.setLayoutManager(new WrapContentLinearLayoutManager(rv_list.getContext(), LinearLayoutManager.VERTICAL, false));
            //      添加动画
            rv_list.setItemAnimator(new DefaultItemAnimator());
            this.adapter = adapter;
            rv_list.setAdapter(adapter);
        }

    }

    private boolean hasRecyclerView() {
        if (rv_list != null) {
            return true;
        }
        MyToast.getInstance().showError(getActivity(),"Not found RecyclerView");
        return false;
    }

    public void setShowEnd(boolean showEnd) {
        isShowEnd = showEnd;
    }


    /**
     * 设置下拉刷新监听
     *
     * @param onRefreshListener
     */
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_orange_light);
            mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        }
    }

    /**
     * @param dividerHeight -2 使用默认高度和颜色
     * @param dividerColor  -1 使用透明颜色
     * @return
     */
    public RecyclerView.ItemDecoration getItemDecoration(int dividerHeight, int dividerColor) {
        if (!hasRecyclerView())
            return null;
        RecyclerView.ItemDecoration itemDecoration = null;
        if (dividerHeight != -2) {//如果不是默认
            if (dividerColor == -1)
                dividerColor = com.steven.baselibrary.R.color.transparent;
            if (dividerHeight != -1)
                itemDecoration = new HorizontalDividerItemDecoration.Builder(rv_list.getContext()).size(ScreenUtils.dp2px(getActivity(),dividerHeight)).color(rv_list.getContext().getResources().getColor(dividerColor)).build();
        } else {
            itemDecoration = new HorizontalDividerItemDecoration.Builder(getActivity()).size(ScreenUtils.dp2px(getActivity(),1)).color(rv_list.getContext().getResources().getColor(com.steven.baselibrary.R.color.gray_listLine)).build();
        }
        return itemDecoration;

    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return getItemDecoration(-2, -1);
    }

    /**
     * 设置表格布局横向item个数
     *
     * @param count
     */
    public void setGridLayoutCount(int count) {
        if (hasRecyclerView()) {
            rv_list.setLayoutManager(new GridLayoutManager(rv_list.getContext(), count));
        }

    }

    /**
     * 设置表格布局横向item个数
     *
     * @param count   横向显示个数
     * @param spacing 控件间距
     */
    public void setGridLayoutCount(int count, int spacing) {
        if (hasRecyclerView()) {
            rv_list.setLayoutManager(new GridLayoutManager(rv_list.getContext(), count));
            rv_list.addItemDecoration(new GridSpacingItemDecoration(getActivity(),count, spacing, false));
        }

    }

    /***
     * 设置当前页数
     *
     * @param currentPage
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setListData(List<ADT> newData) {
        setListData(newData, true);
    }

    public void setListData(List<ADT> newData, boolean isShowEmptyView) {
        if (!hasRecyclerView())
            return;
        if (newData == null) {
            if (mSwipeRefreshLayout != null)
                mSwipeRefreshLayout.setRefreshing(false);
            if (isShowEmptyView)
                addEmptyView();
            adapter.setNewData(newData);
            adapter.notifyDataSetChanged();
            return;
        }
        if (currentPage == 1) {
            if (newData.size() == 0) {
                if (isShowEmptyView)
                    addEmptyView();
                adapter.getData().clear();
                adapter.notifyDataSetChanged();
                adapter.removeAllFooterView();
            } else if (newData.size() < perPageSize) {
                setLoadMoreEnable(false);
                adapter.setNewData(newData);
                toEnd();
            } else {
                setLoadMoreEnable(true);
                adapter.setNewData(newData);
            }
        } else {
            if (newData.size() == 0) {
                setLoadMoreEnable(false);
            } else if (newData.size() < perPageSize) {
                setLoadMoreEnable(false);
                adapter.addData(newData);
                toEnd();
            } else {
                setLoadMoreEnable(true);
                adapter.addData(newData);
                adapter.loadMoreComplete();
            }
        }
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
    }


    private void addEmptyView() {
        adapter.setEmptyView(viewNoData);
    }

    public void toEnd() {
        if (adapter != null) {
            adapter.loadMoreEnd(isShowEnd);
        }
    }

    protected ViewGroup getEmptyView() {
        if (emptyView == null) {
            emptyView = (ViewGroup) LayoutInflater.from(rv_list.getContext()).inflate(customEmptyViewLayout, (ViewGroup) rv_list.getParent(), false);
            if (emptyMessage != null) {
                View v_emptyText = emptyView.findViewById(com.steven.baselibrary.R.id.tv_empty);
                if (v_emptyText != null && v_emptyText instanceof TextView)
                    ((TextView) v_emptyText).setText(emptyMessage);
            }
        }
        return emptyView;
    }

    public void onFailed() {
        onFailed("暂无数据");
    }

    public void onFailed(String text) {
        if (mSwipeRefreshLayout != null)
            mSwipeRefreshLayout.setRefreshing(false);
        if (adapter != null) {
            ((TextView) viewErro.findViewById(R.id.empty_error_tv)).setText(text);
            adapter.setEmptyView(viewErro);
            adapter.loadMoreFail();
        }
    }

    public void setLoadMoreEnable(boolean isShowEnd) {
        this.isShowEnd = isShowEnd;
        adapter.setEnableLoadMore(isShowEnd);
    }

    public void addHeaderView(View header) {
        if (!hasRecyclerView())
            return;
        adapter.removeAllHeaderView();
        adapter.addHeaderView(header);
        adapter.setHeaderAndEmpty(true);
    }

    public void setPerPageSize(int perPageSize) {
        this.perPageSize = perPageSize;
    }

    public void setOnItemClickListener(final OnItemClickListener<ADT, AD> listener) {
        if (!hasRecyclerView())
            return;
        adapter.setOnItemClickListener((adapter, view, position) -> listener.onSimpleItemClick((AD) adapter, view, (ADT) adapter.getItem(position), position));
    }

    public void setOnItemLongClickListener(final OnItemLongClickListener<ADT, AD> listener) {
        if (!hasRecyclerView())
            return;
        adapter.setOnItemLongClickListener((adapter, view, position) -> listener.onSimpleItemLongClick((AD) adapter, view, (ADT) adapter.getItem(position), position));
    }

    public void setOnItemChildClickListener(final OnItemChildClickListener<ADT, AD> listener) {
        if (!hasRecyclerView())
            return;
        adapter.setOnItemChildClickListener((adapter, view, position) -> listener.onSimpleItemChildClick((AD) adapter, view, (ADT) adapter.getItem(position), position));
    }

    public void setOnItemChildLongClickListener(final OnItemChildLongClickListener<ADT, AD> listener) {
        if (!hasRecyclerView())
            return;
        adapter.setOnItemChildLongClickListener((adapter, view, position) -> listener.onSimpleItemChildLongClick((AD) adapter, view, (ADT) adapter.getItem(position), position));
    }

    public RecyclerView findView(Activity activity) {
        View rootView = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        findChildView(rootView);
        return null;
    }

    public RecyclerView findView(Fragment fragment) {
        findChildView(fragment.getView());
        return null;
    }

    public RecyclerView findView(View view) {
        findChildView(view);
        return null;
    }

    private void findChildView(View v) {
        if (v instanceof ViewGroup) { //如果是ViewGroup，遍历下面的子view
            if (v instanceof SwipeRefreshLayout) {
                SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) v;
                this.mSwipeRefreshLayout = refreshLayout;
                View v1 = refreshLayout.getChildAt(1);
                if (v1 instanceof RecyclerView) {
                    rv_list = (RecyclerView) v1;
                    return;
                }
            } else if (v instanceof RecyclerView) {
                rv_list = (RecyclerView) v;
                return;
            } else {
                ViewGroup childViewGroup = (ViewGroup) v;
                int childCount = childViewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childLv1 = childViewGroup.getChildAt(i);
                    if (childLv1 instanceof LinearLayout || childLv1 instanceof RelativeLayout || childLv1 instanceof FrameLayout) {
                        if (childLv1 instanceof TitleBar)//不查找TitleBar里面的view
                            continue;
                        findChildView(childLv1);
                        continue;
                    }
                    if (childLv1 instanceof SwipeRefreshLayout) {
                        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) childLv1;
                        this.mSwipeRefreshLayout = refreshLayout;
                        View v1 = refreshLayout.getChildAt(1);
                        if (v1 instanceof RecyclerView) {
                            rv_list = (RecyclerView) v1;
                            return;
                        }
                    } else if (childLv1 instanceof RecyclerView) {
                        rv_list = (RecyclerView) childLv1;
                        return;
                    }


                }
            }
        }
    }

    public RecyclerView getRecyclerView() {
        return rv_list;
    }
}
