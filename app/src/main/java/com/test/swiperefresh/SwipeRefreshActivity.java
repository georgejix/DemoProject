package com.test.swiperefresh;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;
import com.test.refreshandloadmore.StringAdapter;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_swipe_refresh)
public class SwipeRefreshActivity extends Activity {

    @ViewInject(R.id.listview)
    private ListView listView;
    @ViewInject(R.id.swipe_container)
    private SwipeRefreshLayout swipeLayout;

    private ViewHandler viewHandler;
    private StringAdapter stringAdapter;
    private List<String> dataList = new ArrayList<>();
    private List<String> tempDataList = new ArrayList<>();

    private final int REFRESH = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        if(null == viewHandler){
            viewHandler = new ViewHandler();
        }
        initSwipeLayout();
        if (null == stringAdapter) {
            stringAdapter = new StringAdapter(this, dataList);
            if(null != listView) {
                listView.setAdapter(stringAdapter);
            }
        }
        getData();
    }

    private void initSwipeLayout() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                getData();
            }
        });
        swipeLayout.setColorSchemeResources(R.color.refresh_color_1,
                R.color.refresh_color_2, R.color.refresh_color_3,
                R.color.refresh_color_4);
    }

    private void getData(){
        if(null != tempDataList) {
            tempDataList.clear();
            tempDataList.add("" + System.currentTimeMillis());
            tempDataList.add("" + System.currentTimeMillis());
            tempDataList.add("" + System.currentTimeMillis());
            tempDataList.add("" + System.currentTimeMillis());
            tempDataList.add("" + System.currentTimeMillis());
            if(null != viewHandler){
                viewHandler.sendEmptyMessage(REFRESH);
            }
        }
    }

    class ViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case REFRESH:
                    swipeLayout.setRefreshing(false);
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
