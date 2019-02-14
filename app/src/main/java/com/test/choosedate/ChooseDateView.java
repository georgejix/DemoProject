package com.test.choosedate;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.mplanet.testhandler.R;
import com.test.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jix on 2019/2/14.
 */

public class ChooseDateView extends View{
    private final String TAG = "ChooseDateView";

    private Context mContext;
    private int mainColor;
    //private int mainColor = getResources().getColor(R.color.black);
    private float mainTextSize;
    //private float mainTextSize = getResources().getDimension(R.dimen.mainTextSize);
    private int mainHightlightColor;
    private int perLineHeight;
    //mainPaint 空心图形画笔, mainHighlightPaint 高亮空心图形画笔
    //mainTextPaint 实心文字画笔, mainHightlightTextPaint 高亮实心文字画笔
    private Paint mainPaint, mainHighlightPaint, mainTextPaint, mainHightlightTextPaint;
    private List<Day> dayList = new ArrayList<>();
    private Object lock = new Object();
    private int firstDayOfWeek = -1;
    private int totalHeight = -1;
    private int totalWidth = -1;

    private ChooseDateView(Context context) {
        super(context);
        init();
    }

    public ChooseDateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if(null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChooseDate);
            mainColor = typedArray.getColor(R.styleable.ChooseDate_mainColor, getResources().getColor(R.color.black));
            mainHightlightColor = typedArray.getColor(R.styleable.ChooseDate_mainHightlightColor, getResources().getColor(R.color.red));
            mainTextSize = typedArray.getDimension(R.styleable.ChooseDate_mainTextSize, getResources().getDimension(R.dimen.mainTextSize));
            perLineHeight = typedArray.getDimensionPixelSize(R.styleable.ChooseDate_perLineHeight, getResources().getDimensionPixelSize(R.dimen.perLineHeight));
        }else{
            mainColor = getResources().getColor(R.color.black);
            mainHightlightColor = getResources().getColor(R.color.red);
            mainTextSize = getResources().getDimension(R.dimen.mainTextSize);
            perLineHeight = getResources().getDimensionPixelSize(R.dimen.perLineHeight);
        }
        init();
    }

    private void init(){
        mainPaint = new Paint();
        // 设置画笔为抗锯齿
        mainPaint.setAntiAlias(true);
        mainPaint.setColor(mainColor);
        /* 画笔样式分三种： 1.Paint.Style.STROKE：描边 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         */
        mainPaint.setStyle(Paint.Style.STROKE);
        /* 设置描边的粗细，单位：像素px 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
         */
        //mainPaint.setStrokeWidth(roundWidth);

        mainHighlightPaint = new Paint();
        mainHighlightPaint.setAntiAlias(true);
        mainHighlightPaint.setColor(mainHightlightColor);
        mainHighlightPaint.setStyle(Paint.Style.STROKE);

        mainTextPaint = new Paint();
        mainTextPaint.setAntiAlias(true);
        mainTextPaint.setColor(mainColor);
        mainTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mainTextPaint.setTextSize(mainTextSize);

        mainHightlightTextPaint = new Paint();
        mainHightlightTextPaint.setAntiAlias(true);
        mainHightlightTextPaint.setColor(mainHightlightColor);
        mainHightlightTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mainHightlightTextPaint.setTextSize(mainTextSize);

        //通过xml方式添加view，xml中设置了width和height，在此处修改是不会生效的
        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,perLineHeight * 6));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(-1 == totalHeight || -1 == totalWidth) {
            totalHeight = getHeight();
            totalWidth = getWidth();
        }
        if(totalHeight > 0 && totalWidth > 0){
            int perWidth = totalWidth / 7;
            int perHeight = totalHeight / 6;
            if(null != canvas && null != dayList){
                int row, col;
                row = 0;
                col = Math.abs(dayList.get(0).getDayOfWeek() - firstDayOfWeek);
                for (Day day : dayList) {
                    Log.d(TAG, (perWidth * col + perWidth / 2 - mainTextPaint.measureText(day.getDay() + "") / 2) + "," + (perHeight * row));
                    canvas.drawText(day.getDay() + "", perWidth * col + perWidth / 2 - mainTextPaint.measureText(day.getDay() + "") / 2, perHeight * row, mainTextPaint);
                    col++;
                    if(col >= 7){
                        col %= 7;
                        row++;
                    }
                }
            }
        }
    }

    public void setCalendar(Calendar calendar){
        if(null != calendar){
            if(-1 == firstDayOfWeek){
                firstDayOfWeek = calendar.getFirstDayOfWeek();
            }
            synchronized (lock) {
                int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
                int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                if (null != dayList) {
                    dayList.clear();
                    Day day;
                    for (int i = minDay; i <= maxDay; i++) {
                        calendar.set(Calendar.DAY_OF_MONTH, i);
                        day = new Day(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                                calendar.get(Calendar.DAY_OF_WEEK), false);
                        dayList.add(day);
                    }
                }
            }
        }
    }

    public List<Day> getDayList() {
        return dayList;
    }
}
