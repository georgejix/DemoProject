package com.test.loading;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LoadingView extends View
{
    private final static String TAG = "LoadingView";

    public static final float SCALE = 1.0f;

    public static final int ALPHA = 255;

    float[] scaleFloats = new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE};

    int[] alphas = new int[]{ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA};

    List<Animator> animators = new ArrayList<>();

    private Paint mPaint = new Paint();

    public LoadingView(Context context)
    {
        super(context);
        initAnimator();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        float radius = getWidth() / 10;
        for (int i = 0; i < 8; i++)
        {
            canvas.save();
            Point point = circleAt(getWidth(), getHeight(), getWidth() / 2 - radius, i * (Math.PI / 4));
            canvas.translate(point.x, point.y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            mPaint.setAlpha(alphas[i]);
            canvas.drawCircle(0, 0, radius, mPaint);
            canvas.restore();
        }
    }

    Point circleAt(int width, int height, float radius, double angle)
    {
        float x = (float) (width / 2 + radius * (Math.cos(angle)));
        float y = (float) (height / 2 + radius * (Math.sin(angle)));
        return new Point(x, y);
    }

    final class Point
    {
        public float x;

        public float y;

        public Point(float x, float y)
        {
            this.x = x;
            this.y = y;
        }
    }

    private void initAnimator()
    {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        int[] delays = {0, 120, 240, 360, 480, 600, 720, 780, 840};
        for (int i = 0; i < 8; i++)
        {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.4f, 1);
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(animation ->
            {
                scaleFloats[index] = (float) animation.getAnimatedValue();
                postInvalidate();
            });

            ValueAnimator alphaAnim = ValueAnimator.ofInt(255, 77, 255);
            alphaAnim.setDuration(1000);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.setStartDelay(delays[i]);
            alphaAnim.addUpdateListener(animation ->
                    {
                        alphas[index] = (int) animation.getAnimatedValue();
                        postInvalidate();
                    }
            );
            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
        startAnimator();
    }

    private void startAnimator()
    {
        if (null == animators)
        {
            return;
        }
        for (Animator animator : animators)
        {
            if (null != animator && !animator.isStarted())
            {
                animator.start();
            }
        }
    }

    private void stopAnimator()
    {
        if (null == animators)
        {
            return;
        }
        for (Animator animator : animators)
        {
            if (null != animator && animator.isStarted())
            {
                animator.cancel();
            }
        }
        animators.clear();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        stopAnimator();
    }
}
