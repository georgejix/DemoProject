package com.videoview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView
{
    public MySurfaceView(Context context)
    {
        super(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
    }
}
