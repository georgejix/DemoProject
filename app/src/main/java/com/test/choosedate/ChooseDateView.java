package com.test.choosedate;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mplanet.testhandler.R;
import com.test.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by jix on 2019/2/14.
 */

public class ChooseDateView extends View{
    private final String TAG = "ChooseDateView";

    private Context mContext;
    //private int mainColor = getResources().getColor(R.color.black);
    //private float mainTextSize = getResources().getDimension(R.dimen.mainTextSize);
    private int mainTextColor, hightlightTextColor, mainBgColor, hightlightBgColor;
    private float mainTextSize;
    private int perLineHeight;
    private Paint mainTextPaint, hightlightTextPaint, mainBgPaint, hightlightBgPaint;
    private List<Day> dayList = new ArrayList<>();
    private Object lock = new Object();
    private int firstDayOfWeek = -1;
    //view总高度，宽度
    private float totalHeight = -1;
    private float totalWidth = -1;
    //view的行数和列数
    private final int rows = 6;
    private final int cols = 7;
    /**
     * 开始时间，结束时间
     */
    private Day startTime;
    private Day endTime;

    private ChooseDateView(Context context) {
        super(context);
        init();
    }

    public ChooseDateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if(null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChooseDate);
            mainTextColor = typedArray.getColor(R.styleable.ChooseDate_mainTextColor, getResources().getColor(R.color.black));
            hightlightTextColor = typedArray.getColor(R.styleable.ChooseDate_hightlightTextColor, getResources().getColor(R.color.white));
            mainBgColor = typedArray.getColor(R.styleable.ChooseDate_mainBgColor, getResources().getColor(R.color.grey));
            hightlightBgColor = typedArray.getColor(R.styleable.ChooseDate_hightlightBgColor, getResources().getColor(R.color.red));
            mainTextSize = typedArray.getDimension(R.styleable.ChooseDate_mainTextSize, getResources().getDimension(R.dimen.mainTextSize));
            perLineHeight = typedArray.getDimensionPixelSize(R.styleable.ChooseDate_perLineHeight, getResources().getDimensionPixelSize(R.dimen.perLineHeight));
        }else{
            mainTextColor = getResources().getColor(R.color.black);
            hightlightTextColor = getResources().getColor(R.color.white);
            mainBgColor = getResources().getColor(R.color.grey);
            hightlightBgColor = getResources().getColor(R.color.red);
            mainTextSize = getResources().getDimension(R.dimen.mainTextSize);
            perLineHeight = getResources().getDimensionPixelSize(R.dimen.perLineHeight);
        }
        init();
    }

    private void init(){
        /*mainPaint = new Paint();
        // 设置画笔为抗锯齿
        mainPaint.setAntiAlias(true);
        mainPaint.setColor(mainColor);
        *//* 画笔样式分三种： 1.Paint.Style.STROKE：描边 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         *//*
        mainPaint.setStyle(Paint.Style.STROKE);
        *//* 设置描边的粗细，单位：像素px 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
         *//*
        //mainPaint.setStrokeWidth(roundWidth);*/


        mainTextPaint = new Paint();
        mainTextPaint.setAntiAlias(true);
        mainTextPaint.setColor(mainTextColor);
        mainTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mainTextPaint.setTextSize(mainTextSize);

        hightlightTextPaint = new Paint();
        hightlightTextPaint.setAntiAlias(true);
        hightlightTextPaint.setColor(hightlightTextColor);
        hightlightTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        hightlightTextPaint.setTextSize(mainTextSize);

        mainBgPaint = new Paint();
        mainBgPaint.setAntiAlias(true);
        mainBgPaint.setColor(mainBgColor);
        mainBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        hightlightBgPaint = new Paint();
        hightlightBgPaint.setAntiAlias(true);
        hightlightBgPaint.setColor(hightlightBgColor);
        hightlightBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //通过xml方式添加view，xml中设置了width和height，在此处修改是不会生效的
        /*setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,perLineHeight * 6));*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (lock) {
            if (-1 == totalHeight || -1 == totalWidth) {
                totalHeight = getHeight();
                totalWidth = getWidth();
            }
            if (totalHeight > 0 && totalWidth > 0) {
                float perWidth = totalWidth / cols;
                float perHeight = totalHeight / rows;
                if (null != canvas && null != dayList && dayList.size() > 0) {
                    int row, col;
                    row = firstRow;
                    col = firstCol;
                    Rect rect = new Rect();

                    //draw bg
                    //开始，结束时间的day，0表示startDay在上个月 或者 endday在下个月
                    int startDay = -1;
                    int endDay = -1;
                    //找出开始时间和结束时间的day
                    if(null != startTime) {
                        if (isSameMonth(startTime, dayList.get(0))) {
                            startDay = startTime.getDay();
                        }else{
                            if(isDayAsc(startTime, dayList.get(0))){
                                startDay = 0;
                            }
                        }
                    }
                    if(null != endTime){
                        if (isSameMonth(endTime, dayList.get(0))) {
                            endDay = endTime.getDay();
                        }else{
                            if(isDayAsc(dayList.get(dayList.size() - 1), endTime)){
                                endDay = 0;
                            }
                        }

                    }

                    //画中间时间段的选中底色
                    if(-1 < startDay && -1 < endDay){
                        int startCol = (col + startDay - 1) % cols;
                        int startRow = (col + startDay - 1) / cols;
                        int endCol = (col + endDay - 1) % cols;
                        int endRow = (col + endDay - 1) / cols;
                        if(0 == startDay){
                            startCol = col;
                            startRow = row;
                        }
                        if(0 == endDay){
                            endCol = (col + dayList.get(dayList.size() - 1).getDay() - 1) % cols;
                            endRow = (col + dayList.get(dayList.size() - 1).getDay() - 1) / cols;
                        }

                        if(startRow == endRow){
                            //Log.d(TAG, "from(" + startRow + "," + startCol + "),to(" + endRow + "," + endCol + ")");
                            float left = startCol * perWidth + perWidth / 2 - perHeight * 2 / 5;
                            float top = startRow * perHeight + perHeight / 2 - perHeight * 2 / 5;
                            float right = endCol * perWidth + perWidth / 2 + perHeight * 2 / 5;
                            float bottom = endRow * perHeight + perHeight / 2 + perHeight * 2 / 5;
                            canvas.drawRoundRect(new RectF(left, top, right, bottom), perHeight * 2 / 5, perHeight * 2 / 5, mainBgPaint);
                        }else{
                            int r = startRow;
                            while(r <= endRow){
                                if(r == startRow){
                                    //Log.d(TAG, "from(" + startRow + "," + startCol + "),to(" + r + "," + (cols - 1) + ")");
                                    float left = startCol * perWidth + perWidth / 2 - perHeight * 2 / 5;
                                    float top = startRow * perHeight + perHeight / 2 - perHeight * 2 / 5;
                                    float right = (cols - 1) * perWidth + perWidth / 2 + perHeight * 2 / 5;
                                    float bottom = startRow * perHeight + perHeight / 2 + perHeight * 2 / 5;
                                    canvas.drawRoundRect(new RectF(left, top, right, bottom), perHeight * 2 / 5, perHeight * 2 / 5, mainBgPaint);
                                }else if(r < endRow){
                                    //Log.d(TAG, "from(" + r + "," + 0 + "),to(" + r + "," + (cols - 1) + ")");
                                    float left = 0 * perWidth + perWidth / 2 - perHeight * 2 / 5;
                                    float top = r * perHeight + perHeight / 2 - perHeight * 2 / 5;
                                    float right = (cols - 1) * perWidth + perWidth / 2 + perHeight * 2 / 5;
                                    float bottom = r * perHeight + perHeight / 2 + perHeight * 2 / 5;
                                    canvas.drawRoundRect(new RectF(left, top, right, bottom), perHeight * 2 / 5, perHeight * 2 / 5, mainBgPaint);
                                }else if(r == endRow){
                                    //Log.d(TAG, "from(" + r + "," + 0 + "),to(" + r + "," + endCol + ")");
                                    float left = 0 * perWidth + perWidth / 2 - perHeight * 2 / 5;
                                    float top = r * perHeight + perHeight / 2 - perHeight * 2 / 5;
                                    float right = endCol * perWidth + perWidth / 2 + perHeight * 2 / 5;
                                    float bottom = r * perHeight + perHeight / 2 + perHeight * 2 / 5;
                                    canvas.drawRoundRect(new RectF(left, top, right, bottom), perHeight * 2 / 5, perHeight * 2 / 5, mainBgPaint);
                                }
                                r++;
                            }
                        }
                    }

                    //画开始时间文字选中底色
                    if (0 < startDay) {
                        //开始点所在方格，的左上角坐标
                        float startX = ((int) (col + startDay - 1) % cols) * perWidth;
                        float startY = ((int) (col + startDay - 1) / cols) * perHeight;
                        canvas.drawCircle(startX + perWidth / 2, startY + perHeight / 2, perHeight * 2 / 5, hightlightBgPaint);
                    }
                    //画结束时间文字选中底色
                    if(0 < endDay) {
                        //结束点所在方格，的左上角坐标
                        float endX = ((int)(col + endDay - 1)) % cols * perWidth;
                        float endY = ((int)(col + endDay - 1)) / cols * perHeight;
                        canvas.drawCircle(endX + perWidth / 2, endY + perHeight / 2, perHeight * 2/ 5, hightlightBgPaint);
                    }

                    for (Day day : dayList) {
                        if(null != day) {
                            String text = day.getDay() + "";
                            mainTextPaint.getTextBounds(text, 0, text.length(), rect);
                            //使用measureText可以让文字居中,而用rect.width()则不行
                            if(day.isStartTime() || day.isEndTime()){
                                canvas.drawText(day.getDay() + "", perWidth * col + perWidth / 2 - hightlightTextPaint.measureText(text) / 2,
                                        perHeight * row + perHeight / 2 + rect.height() / 2, hightlightTextPaint);
                            }else {
                                canvas.drawText(day.getDay() + "", perWidth * col + perWidth / 2 - hightlightTextPaint.measureText(text) / 2,
                                        perHeight * row + perHeight / 2 + rect.height() / 2, mainTextPaint);
                            }
                            col++;
                            if (col >= 7) {
                                col %= 7;
                                row++;
                            }
                        }
                    }
                }
            }
        }
    }

    //设置calendar，用于刷新月份
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
                    calculator();
                }
            }
        }
    }

    //第一天所在行列,从0开始
    private int firstRow, firstCol;
    private void calculator(){
        if(null != dayList && dayList.size() > 0) {
            firstRow = 0;
            firstCol = Math.abs(dayList.get(0).getDayOfWeek() - firstDayOfWeek);
        }else{
            firstRow = 0;
            firstCol = 0;
        }
    }

    //通过坐标，返回该坐标对应的数据在list中的index，从0开始
    private int getSelectedIndex(int x, int y){
        int index = -1;
        if(null != dayList && dayList.size() > 0){
            if (totalHeight > 0 && totalWidth > 0) {
                float perWidth = totalWidth / cols;
                float perHeight = totalHeight / rows;
                int row = 0;
                int col = 0;
                row = (int) (y / perHeight);
                col = (int) (x / perWidth + (x % perWidth > 0 ? 0 : -1));
                index = (row - firstRow) * cols + (col - firstCol);
                if(index < -1){
                    index = -1;
                }
                if(index >= dayList.size()){
                    index = -1;
                }
            }
        }
        return index;
    }

    private int selectedIndex = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                selectedIndex = getSelectedIndex((int)event.getX(), (int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                int index = getSelectedIndex((int)event.getX(), (int)event.getY());
                if(selectedIndex == index){
                    if(null != onDaySelectedListener){
                        onDaySelectedListener.onClick(selectedIndex);
                    }
                }else{
                    selectedIndex = -1;
                }
                break;
        }
        return true;
    }

    //用于点击事件监听
    private OnDaySelectedListener onDaySelectedListener;
    interface OnDaySelectedListener{
        abstract void onClick(int index);
    }

    public void setOnDaySelectedListener(OnDaySelectedListener onDaySelectedListener) {
        this.onDaySelectedListener = onDaySelectedListener;
    }

    public OnDaySelectedListener getOnDaySelectedListener() {
        return onDaySelectedListener;
    }

    //用于打印错误
    private ErrorListener errorListener;
    interface ErrorListener{
        abstract void error(String s);
    }

    public void setErrorListener(ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    public ErrorListener getErrorListener() {
        return errorListener;
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public Day getStartTime() {
        return startTime;
    }

    private int maxRange = -1;

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    //设置开始时间：1，判断是否符合规则 2，刷新list中所有时间的选中状态
    public void setStartTime(int index) {
        synchronized (lock){
            if(null != endTime && null != dayList && dayList.size() > 0 && dayList.size() > index){
                if(!isDayAsc(dayList.get(index), endTime)){
                    if(null != errorListener && null != mContext){
                        errorListener.error(mContext.getResources().getString(R.string.choosedate_error1));
                    }
                    return;
                }
                if(-1 != maxRange){
                    if(!isInRange(dayList.get(index), endTime, maxRange)){
                        if(null != errorListener && null != mContext){
                            errorListener.error(mContext.getResources().getString(R.string.choosedate_error2) + maxRange +
                                    mContext.getResources().getString(R.string.choosedate_error3));
                        }
                        return;
                    }
                }
            }
            if (null != this.startTime) {
                this.startTime.setStartTime(false);
            }
            if (null != dayList && -1 != index && dayList.size() > 0 && dayList.size() > index) {
                this.startTime = dayList.get(index);
                this.startTime.setStartTime(true);
                for (Day day : dayList) {
                    if (null != day) {
                        if (isDaySelected(day)) {
                            day.setSelected(true);
                        } else {
                            day.setSelected(false);
                        }
                    }
                }
            }
        }
    }

    public Day getEndTime() {
        return endTime;
    }

    //设置结束时间：1，判断是否符合规则 2，刷新list中所有时间的选中状态
    public void setEndTime(int index) {
        synchronized (lock){
            if(null != startTime && null != dayList && dayList.size() > 0 && dayList.size() > index){
                if (!isDayAsc(startTime, dayList.get(index))) {
                    if(null != errorListener && null != mContext){
                        errorListener.error(mContext.getResources().getString(R.string.choosedate_error4));
                    }
                    return;
                }
                if(-1 != maxRange){
                    if(!isInRange(startTime, dayList.get(index), maxRange)){
                        if(null != errorListener && null != mContext){
                            errorListener.error(mContext.getResources().getString(R.string.choosedate_error2) + maxRange +
                                    mContext.getResources().getString(R.string.choosedate_error3));
                        }
                        return;
                    }
                }
            }
            if (null != this.endTime) {
                this.endTime.setEndTime(false);
            }
            if (null != dayList && -1 != index && dayList.size() > 0 && dayList.size() > index) {
                this.endTime = dayList.get(index);
                this.endTime.setEndTime(true);
                for (Day day : dayList) {
                    if (null != day) {
                        if (isDaySelected(day)) {
                            day.setSelected(true);
                        } else {
                            day.setSelected(false);
                        }
                    }
                }
            }
        }
    }

    //判断传入时间，是否在开始时间和结束时间之间
    private boolean isDaySelected(Day day){
        boolean selected = false;
        if(null != day && null != startTime && null != endTime){
            Calendar c = Calendar.getInstance(Locale.CHINA);
            c.set(startTime.getYear(), startTime.getMonth(), startTime.getDay());
            long startTimeStamp = c.getTimeInMillis();
            c.set(endTime.getYear(), endTime.getMonth(), endTime.getDay());
            long endTimeStamp = c.getTimeInMillis();
            c.set(day.getYear(), day.getMonth(), day.getDay());
            long dayTimeStamp = c.getTimeInMillis();
            if(startTimeStamp <= dayTimeStamp && dayTimeStamp <= endTimeStamp){
                selected = true;
            }
        }
        return selected;
    }

    //判断两个时间是否同一天
    private boolean isSameMonth(Day d1, Day d2){
        if(null != d1 && null != d2){
            if(d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth()){
                return true;
            }
        }
        return false;
    }

    //判断两个时间，是否正序（后面的 晚于/等于 前一个）
    private boolean isDayAsc(Day d1, Day d2){
        boolean isAsc = false;
        if(null != d1 && null != d2){
            Calendar c = Calendar.getInstance(Locale.CHINA);
            c.set(d1.getYear(), d1.getMonth(), d1.getDay());
            long d1TimeStamp = c.getTimeInMillis();
            c.set(d2.getYear(), d2.getMonth(), d2.getDay());
            long d2TimeStamp = c.getTimeInMillis();
            if(d1TimeStamp <= d2TimeStamp){
                isAsc = true;
            }
        }
        return isAsc;
    }

    private final double onDayInMillis = 24 * 60 * 60 * 1000.0;
    private boolean isInRange(Day d1, Day d2, int range){
        boolean isInRange = false;
        if(null != d1 && null != d2){
            Calendar c = Calendar.getInstance(Locale.CHINA);
            c.set(d1.getYear(), d1.getMonth(), d1.getDay());
            long d1TimeStamp = c.getTimeInMillis();
            c.set(d2.getYear(), d2.getMonth(), d2.getDay());
            long d2TimeStamp = c.getTimeInMillis();
            if(Math.abs(d1TimeStamp - d2TimeStamp) / onDayInMillis  <= (range - 1)){
                isInRange = true;
            }
        }
        return isInRange;
    }
}
