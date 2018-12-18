package com.test.circlepic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/12/13.
 */

public class RoundView extends ImageView {
    private final String TAG = "RoundView";

    int width;
    Path path;

    public RoundView(Context context) {
        super(context);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
    }

    public RoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        path.addCircle(width / 2, width / 2, width / 2, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(path);
        super.onDraw(canvas);
    }


}
