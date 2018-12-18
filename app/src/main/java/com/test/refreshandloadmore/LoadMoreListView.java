package com.test.refreshandloadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mplanet.testhandler.R;

public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener {
    private View rooterView;
    private boolean isHaveMoreData = true;// 是否有更多数据(默认为有)
    private ProgressBar progressBar;
    private TextView tipContext;

    private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
    private boolean isLoading = false;// 是否正在加载

    private OnLoadMoreListener mOnLoadMoreListener;
    private View foot_line;
    private View footLayout;

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //动态载入底部布局
        rooterView = LayoutInflater.from(context).inflate(
                R.layout.pull_to_load_footer, null);
        progressBar = (ProgressBar) rooterView.findViewById(R.id.footer_progressbar);
        tipContext = (TextView) rooterView.findViewById(R.id.footer_hint_textview);
        foot_line = rooterView.findViewById(R.id.foot_line);
        footLayout = rooterView.findViewById(R.id.layout_foot);
        //向listView的底部添加布局(此时当给listView设置Item点击事件的时候，默认不触发这个添加的布局的点击事件)
        addFooterView(rooterView, null, false);
        setOnScrollListener(this);
    }

    public void setRefreshAndLoadMoreView(RefreshAndLoadMoreView mRefreshAndLoadMoreView) {
        this.mRefreshAndLoadMoreView = mRefreshAndLoadMoreView;
    }

    /**
     * 设置是否还有更多数据
     *
     * @param isHaveMoreData
     */
    public void setHaveMoreData(boolean isHaveMoreData) {
        this.isHaveMoreData = isHaveMoreData;
        if (!isHaveMoreData) {
            tipContext.setText(getResources().getString(R.string.load_completed));
            progressBar.setVisibility(View.GONE);
        } else {
            tipContext.setText(getResources().getString(R.string.loading));
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载完成
     */
    public void onLoadComplete() {
        isLoading = false;
    }

    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            if (view.getLastVisiblePosition() == view.getCount() - 1 && (mRefreshAndLoadMoreView != null &&
                    !mRefreshAndLoadMoreView.isRefreshing()) && !isLoading && mOnLoadMoreListener != null && isHaveMoreData) {
                isLoading = true;
                mOnLoadMoreListener.onLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /**
     * 加载更多的监听
     */
    public static interface OnLoadMoreListener {
        public void onLoadMore();
    }

    /**
     * 设置加载监听
     *
     * @param mOnLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void setFootViewVisibility(Boolean visiable){
        if(null != foot_line && !visiable){
            foot_line.setVisibility(View.GONE);
        }else if(null != foot_line && visiable){
            foot_line.setVisibility(View.VISIBLE);
        }
    }

    public void setFootLayoutBackground(int color){
        if(null != footLayout){
            footLayout.setBackgroundColor(color);
        }
    }
}
