package com.test.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_recycler_view_layout)
public class RecyclerViewActivity extends BaseActivity
{
    private final static String TAG = "RecyclerViewActivity";

    @ViewInject(R.id.recyclerview)
    private RecyclerView mRecyclerView;

    private RecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            list.add(i + "   " + System.currentTimeMillis());
        }
        mRecyclerAdapter = new RecyclerAdapter(this, list);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }
}
