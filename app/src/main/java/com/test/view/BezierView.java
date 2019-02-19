package com.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by jix on 2019/2/19.
 */

public class BezierView extends View {
    private Context mContext;
    private List<Point> pointList;
    private Paint mainPaint;
    private int mainColor;

    public BezierView(Context context) {
        super(context);
        init();
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    private void init(){
        mainColor = Color.parseColor("#2C2255");

        mainPaint = new Paint();
        mainPaint.setColor(mainColor);
        mainPaint.setAntiAlias(true);
        mainPaint.setStyle(Paint.Style.STROKE);

        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n = 1 / 0;
        if(null != canvas){
            if(null != pointList && pointList.size() > 0){
                Path path = new Path();
                path.moveTo(pointList.get(0).getX(), pointList.get(0).getY());
                for(int i = 1; i < pointList.size(); i++){
                    Point point = pointList.get(i);
                    Point prePoint = pointList.get(i - 1);
                    path.cubicTo(prePoint.getX() + (point.getX() - prePoint.getX()) / 2, prePoint.getY(),
                            prePoint.getX() + (point.getX() - prePoint.getX()) / 2, point.getY(),
                            point.getX(), point.getY());
                }
                canvas.drawPath(path, mainPaint);
            }
        }
    }

    public class Point{
        private int x;
        private int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
