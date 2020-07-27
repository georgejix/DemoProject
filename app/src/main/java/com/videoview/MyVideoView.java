package com.videoview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.VideoView;

public class MyVideoView extends VideoView
{

    public MyVideoView(Context context)
    {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

}
