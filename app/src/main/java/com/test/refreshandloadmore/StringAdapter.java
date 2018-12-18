package com.test.refreshandloadmore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mplanet.testhandler.R;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class StringAdapter extends BaseAdapter {
    private Context context;
    private List<String> dataList;

    public StringAdapter(){

    }

    public StringAdapter(Context context, List<String> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return null == dataList ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null != context) {
            if(null == convertView) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_stringadapter, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if(position >= 0 && null != dataList && position <dataList.size()){
                if(null != dataList.get(position)) {
                    viewHolder.textView.setText(dataList.get(position));
                }
            }
        }
        if(null == convertView){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_stringadapter, null);
        }
        return convertView;
    }

    class ViewHolder{
        private TextView textView;
    }
}
