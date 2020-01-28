package com.test.circlepic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.mplanet.testhandler.R;

/**
 * Created by jix on 2017/10/30.
 * @brief 圆角layout
 */

public class RoundLayout extends RelativeLayout {
    private float roundLayoutRadius = 14f;
    private Path roundPath;
    private RectF rectF;
    private Paint mBgPaint;

    public RoundLayout(Context context) {
        this(context, null);
    }

    public RoundLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundLayout);
        roundLayoutRadius = typedArray.getDimensionPixelSize(R.styleable.RoundLayout_roundLayoutRadius, (int) roundLayoutRadius);
        typedArray.recycle();

        init();
    }

    private void init() {
        setWillNotDraw(false);//如果你继承的是ViewGroup,注意此行,否则draw方法是不会回调的;
        roundPath = new Path();
        rectF = new RectF();
        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private void setRoundPath() {
        //添加一个圆角矩形到path中, 如果要实现任意形状的View, 只需要手动添加path就行
        roundPath.addRoundRect(rectF, roundLayoutRadius, roundLayoutRadius, Path.Direction.CW);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        rectF.set(0f, 0f, getMeasuredWidth(), getMeasuredHeight());
        setRoundPath();
    }

    @Override
    public void draw(Canvas canvas) {
        if (roundLayoutRadius > 0f) {
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
            canvas.clipPath(roundPath);
        }
        super.draw(canvas);
    }




    public void setRoundLayoutRadius(float roundLayoutRadius) {
        this.roundLayoutRadius = roundLayoutRadius;
        setRoundPath();
        postInvalidate();
    }
}
