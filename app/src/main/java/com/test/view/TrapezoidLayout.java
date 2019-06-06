package com.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.mplanet.testhandler.R;

/**
 * Created by jix on 2019/3/27.
 */

public class TrapezoidLayout extends RelativeLayout{
    private final String TAG = "TrapezoidLayout";

    private int triangleHeight;
    private int orientation;
    private int backgroundColor = 0xFFFFFFFF;
    private Paint bgPaint;
    Path path;

    public TrapezoidLayout(Context context) {
        super(context);
    }

    public TrapezoidLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TrapesoidLayout);
        triangleHeight = typedArray.getDimensionPixelSize(R.styleable.TrapesoidLayout_triangleHeight, 0);
        orientation = typedArray.getInteger(R.styleable.TrapesoidLayout_orientation, 0);
        backgroundColor = typedArray.getColor(R.styleable.TrapesoidLayout_backgroundColor, backgroundColor);

        if(null == bgPaint){
            bgPaint = new Paint();
            bgPaint.setAntiAlias(true);
            bgPaint.setColor(backgroundColor);
            bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onmeasure:width=" + widthMeasureSpec + ",height=" + heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(triangleHeight > (bottom - top)){
            triangleHeight = 0;
        }
        //if(null == path){
            path = new Path();
            int width = right - left;
            int height = bottom - top;
            if(0 == orientation){
                path.moveTo(0, height);
                path.lineTo(width, height);
                path.lineTo(width, 0);
                path.lineTo(0, 0 + triangleHeight);
                path.lineTo(0, height);
            }else if(1 == orientation){
                path.moveTo(0, height);
                path.lineTo(width, height);
                path.lineTo(width, 0 + triangleHeight);
                path.lineTo(0, 0);
                path.lineTo(0, height);
            }

        //}

        Log.d(TAG, "onLayout:" + left + "," + top + "," + right + "," + bottom);
        Log.d(TAG, "onLayout:width=" + getMeasuredWidth() + ",height=" + getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(null != path) {
            canvas.drawPath(path, bgPaint);
            canvas.clipPath(path);
        }
    }
}
