package com.test.choosedate;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mplanet.testhandler.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@ContentView(R.layout.activity_choose_date)
public class ChooseDateActivity extends Activity {
    private final String TAG = "ChooseDateActivity";

    @ViewInject(R.id.textview_starttime)
    private TextView starttimeText;
    @ViewInject(R.id.textview_endtime)
    private TextView endtimeText;
    @ViewInject(R.id.textview_current_month)
    private TextView currentMonthText;
    @ViewInject(R.id.textview_week_first)
    private TextView firstInWeekText;
    @ViewInject(R.id.textview_week_second)
    private TextView secondInWeekText;
    @ViewInject(R.id.textview_week_third)
    private TextView thirdInWeekText;
    @ViewInject(R.id.textview_week_fourth)
    private TextView fourthInWeekText;
    @ViewInject(R.id.textview_week_fifth)
    private TextView fifthInWeekText;
    @ViewInject(R.id.textview_week_sixth)
    private TextView sixthInWeekText;
    @ViewInject(R.id.textview_week_seventh)
    private TextView seventhInWeekText;
    @ViewInject(R.id.chooseDateView)
    private ChooseDateView chooseDateView;
    @ViewInject(R.id.view_starttime)
    private View startTimeLine;
    @ViewInject(R.id.view_endtime)
    private View endTimeLine;

    private Calendar calendar;
    private SimpleDateFormat sdf;
    private int firstDayOfWeek = -1;
    private String dayOfWeek[] = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private boolean startTimeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        initView();
    }

    private void initView(){
        calendar = Calendar.getInstance(Locale.CHINA);
        //calendar = Calendar.getInstance(Locale.FRENCH);
        sdf = new SimpleDateFormat("yyyy年MM月");
        if(null != sdf && null != calendar) {
            currentMonthText.setText(sdf.format(calendar.getTime()));
        }
        if(null != calendar){
            firstDayOfWeek = calendar.getFirstDayOfWeek();
        }
        if(-1 != firstDayOfWeek){
            firstInWeekText.setText(dayOfWeek[(firstDayOfWeek - 1) % 7]);
            secondInWeekText.setText(dayOfWeek[(firstDayOfWeek + 1 - 1) % 7]);
            thirdInWeekText.setText(dayOfWeek[(firstDayOfWeek + 2 - 1) % 7]);
            fourthInWeekText.setText(dayOfWeek[(firstDayOfWeek + 3 - 1) % 7]);
            fifthInWeekText.setText(dayOfWeek[(firstDayOfWeek + 4 - 1) % 7]);
            sixthInWeekText.setText(dayOfWeek[(firstDayOfWeek + 5 - 1) % 7]);
            seventhInWeekText.setText(dayOfWeek[(firstDayOfWeek + 6 - 1) % 7]);
        }
        if(null != chooseDateView && null != calendar){
            chooseDateView.setCalendar((Calendar) calendar.clone());
            chooseDateView.setOnDaySelectedListener(new ChooseDateView.OnDaySelectedListener() {
                @Override
                public void onClick(int index) {
                    if(-1 != index && null != chooseDateView && null != chooseDateView.getDayList() && chooseDateView.getDayList().size() > 0 &&
                            chooseDateView.getDayList().size() > index){
                        if(startTimeSelected){
                            chooseDateView.setStartTime(index);
                            if(null != chooseDateView.getStartTime()) {
                                starttimeText.setText((chooseDateView.getStartTime().getMonth() + 1) + "月" +
                                        chooseDateView.getStartTime().getDay() + "日");
                            }
                        }else{
                            chooseDateView.setEndTime(index);
                            if(null != chooseDateView.getEndTime()) {
                                endtimeText.setText((chooseDateView.getEndTime().getMonth() + 1) + "月" +
                                        chooseDateView.getEndTime().getDay() + "日");
                            }
                        }
                        chooseDateView.invalidate();
                    }
                }
            });
            chooseDateView.setErrorListener(new ChooseDateView.ErrorListener() {
                @Override
                public void error(String s) {
                    if(null != s) {
                        Toast.makeText(ChooseDateActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            chooseDateView.setMaxRange(31);
        }
        setSelected(true);
    }

    @OnClick(value = {R.id.layout_starttime, R.id.layout_endtime, R.id.textview_close, R.id.textview_pre_month, R.id.textview_next_month})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.layout_starttime:
                setSelected(true);
                break;
            case R.id.layout_endtime:
                setSelected(false);
                break;
            case R.id.textview_close:
                Toast.makeText(ChooseDateActivity.this, "close", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textview_pre_month:
                changeMonth(-1);
                break;
            case R.id.textview_next_month:
                changeMonth(1);
                break;
        }
    }

    private void setSelected(boolean startTimeSelected){
        synchronized(this){
            this.startTimeSelected = startTimeSelected;
            if(startTimeSelected){
                startTimeLine.setBackgroundColor(getResources().getColor(R.color.red));
                endTimeLine.setBackgroundColor(getResources().getColor(R.color.white));
            }else{
                startTimeLine.setBackgroundColor(getResources().getColor(R.color.white));
                endTimeLine.setBackgroundColor(getResources().getColor(R.color.red));
            }
        }
    }

    private void changeMonth(int i){
        if(null != calendar) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + i);
        }
        if(null != sdf && null != calendar) {
            currentMonthText.setText(sdf.format(calendar.getTime()));
        }
        if(null != chooseDateView && null != calendar){
            chooseDateView.setCalendar((Calendar) calendar.clone());
            chooseDateView.invalidate();
        }
    }
}
