package com.test.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_bezier)
public class BezierActivity extends Activity {

    @ViewInject(R.id.layout_bezierview)
    private LinearLayout bezierViewLayout;
    @ViewInject(R.id.bezierview)
    private BezierView bezierView2;

    private List<BezierView.Point> pointList;
    private BezierView bezierView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        initView();
    }

    private void initView(){
        bezierView = new BezierView(this);
        pointList = new ArrayList<>();
        pointList.add(bezierView.new Point(5,5));
        pointList.add(bezierView.new Point(50,50));
        pointList.add(bezierView.new Point(100,99));
        pointList.add(bezierView.new Point(150,35));
        pointList.add(bezierView.new Point(200,77));
        pointList.add(bezierView.new Point(250,44));
        bezierView.setPointList(pointList);
        bezierViewLayout.addView(bezierView);

        bezierView2.setPointList(pointList);
        bezierView2.invalidate();
    }
}
