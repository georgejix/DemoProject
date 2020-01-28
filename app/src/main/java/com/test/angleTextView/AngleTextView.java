package com.test.angleTextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.mplanet.testhandler.R;

public class AngleTextView extends View
{
    private final static String TAG = "AngleTextView";

    private Context mContext;

    private int mDegree;

    private double mAngle;

    private String mText;

    private float mTextSize, mTextMarginTop, mTextMarginBottom, mPaddingTop;

    private int mTextColor, mBgColor;

    private Paint mTextPaint, mBgPaint;


    private float textW, textH;

    public AngleTextView(Context context)
    {
        super(context);
    }

    public AngleTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AngleTextView);
        mDegree = typedArray.getInteger(R.styleable.AngleTextView_AngleTextViewAngle, 45);
        mAngle = mDegree * Math.PI / 180;
        mText = typedArray.getString(R.styleable.AngleTextView_AngleTextViewText);
        mTextSize = typedArray.getDimension(R.styleable.AngleTextView_AngleTextViewTextSize, 11);
        mTextMarginTop = typedArray.getDimension(R.styleable.AngleTextView_AngleTextViewTextMarginTop, 0);
        mTextMarginBottom = typedArray.getDimension(R.styleable.AngleTextView_AngleTextViewTextMarginBottom, 0);
        mPaddingTop = typedArray.getDimension(R.styleable.AngleTextView_AngleTextViewPaddingTop, 0);
        mTextColor = typedArray.getColor(R.styleable.AngleTextView_AngleTextViewTextColor, mContext.getResources().getColor(R.color.white));
        mBgColor = typedArray.getColor(R.styleable.AngleTextView_AngleTextViewBgColor, mContext.getResources().getColor(R.color.black));
        typedArray.recycle();
        init();
    }

    private void init()
    {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStyle(Paint.Style.STROKE);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStyle(Paint.Style.FILL);
        setWidthAndHeight();
    }

    private void setWidthAndHeight()
    {
        if (null == mText)
        {
            return;
        }
        Rect rect = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), rect);
        textW = mTextPaint.measureText(mText);
        textH = rect.height();

        double y = textW * Math.sin(mAngle) + (mTextMarginTop + textH + mTextMarginBottom) / Math.cos(mAngle);
        double x = y / Math.tan(mAngle);

        ViewGroup.LayoutParams params = getLayoutParams();
        if (null == params)
        {
            return;
        }
        if (TextUtils.isEmpty(mText))
        {
            params.width = 0;
            params.height = 0;
        }
        else
        {
            params.width = (int) x;
            params.height = (int) y;
        }
        setLayoutParams(params);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (0 == getWidth() || TextUtils.isEmpty(mText))
        {
            return;
        }
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo((float) (getWidth() - mPaddingTop / Math.sin(mAngle)), 0);
        path.lineTo(getWidth(), (float) (mPaddingTop / Math.cos(mAngle)));
        path.lineTo(getWidth(), getHeight());
        path.close();
        canvas.drawPath(path, mBgPaint);

        canvas.save();
        canvas.rotate((float) (mDegree), 0, 0);
        canvas.drawText(mText, (float) ((mTextMarginBottom + textH + mTextMarginTop) / Math.tan(mAngle)), 0 - mTextMarginBottom, mTextPaint);
        canvas.restore();
    }

    public void setTextColor(int textColor)
    {
        this.mTextColor = textColor;
        if (null != mTextPaint)
        {
            mTextPaint.setColor(mTextColor);
        }
    }

    public void setBgColor(int bgColor)
    {
        this.mBgColor = bgColor;
        if (null != mBgPaint)
        {
            mBgPaint.setColor(mBgColor);
        }
    }

    public void setText(String str)
    {
        mText = str;
        setWidthAndHeight();
        invalidate();
    }

    public String getText()
    {
        return mText;
    }
}
