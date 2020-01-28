package com.test.refreshandloadmore;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_refresh_and_load_more)
public class RefreshAndLoadMoreActivity extends Activity {
    @ViewInject(R.id.load_more_list)
    private LoadMoreListView mLoadMoreListView;
    @ViewInject(R.id.refresh_and_load_more)
    private RefreshAndLoadMoreView mRefreshAndLoadMoreView;

    private ViewHandler viewHandler;
    private int page = 0;
    private List<String> dataList = new ArrayList<>();
    private List<String> tempDataList = new ArrayList<>();
    private StringAdapter stringAdapter;

    private final int COMPLETED = 1001;
    private final int REFRESH = 1002;
    private final int HAS_NO_MORE = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        if(null == viewHandler){
            viewHandler = new ViewHandler();
        }
        initListView();
        loadData((byte) 0);
    }

    private void initListView() {
        if(null != mRefreshAndLoadMoreView && null != mLoadMoreListView) {
            mRefreshAndLoadMoreView.setLoadMoreListView(mLoadMoreListView);
            mLoadMoreListView.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
            //设置下拉刷新监听
            mRefreshAndLoadMoreView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadData((byte) (0));
                }
            });
            //设置加载监听
            mLoadMoreListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    loadData((byte) (++page));
                }
            });
            if (null == stringAdapter) {
                stringAdapter = new StringAdapter(this, dataList);
                mLoadMoreListView.setAdapter(stringAdapter);
            }
        }
    }

    private void loadData(final byte temppage){
        if(null != tempDataList) {
            if (0 == temppage) {
                tempDataList.clear();
                tempDataList.add("" + System.currentTimeMillis());
                tempDataList.add("" + System.currentTimeMillis());
                tempDataList.add("" + System.currentTimeMillis());
                tempDataList.add("" + System.currentTimeMillis());
                tempDataList.add("" + System.currentTimeMillis());
            } else {
                tempDataList.add("" + System.currentTimeMillis());
                tempDataList.add("" + System.currentTimeMillis());
                tempDataList.add("" + System.currentTimeMillis());
                tempDataList.add("" + System.currentTimeMillis());
                tempDataList.add("" + System.currentTimeMillis());
            }
            setComplete();
            if(null != viewHandler){
                viewHandler.sendEmptyMessage(REFRESH);
            }
            if(tempDataList.size() > 19){
                if(null != viewHandler){
                    viewHandler.sendEmptyMessage(HAS_NO_MORE);
                }
            }
        }

    }

    private void setComplete(){
        if(null != viewHandler){
            viewHandler.sendEmptyMessage(COMPLETED);
        }
    }

    class ViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case COMPLETED:
                    if(!isFinishing() && !isDestroyed()){
                        if(null != mRefreshAndLoadMoreView && null != mLoadMoreListView) {
                            //当加载完成之后设置此时不在刷新状态
                            mRefreshAndLoadMoreView.setRefreshing(false);
                            mLoadMoreListView.onLoadComplete();
                        }
                    }
                    break;
                case HAS_NO_MORE:
                    if(null != mLoadMoreListView) {
                        mLoadMoreListView.setHaveMoreData(false);
                    }
                    break;
                case REFRESH:
                    if(null != dataList && null != tempDataList){
                        dataList.clear();
                        dataList.addAll(tempDataList);
                    }
                    if(null != stringAdapter){
                        stringAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }
}
