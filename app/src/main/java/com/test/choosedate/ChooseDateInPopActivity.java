package com.test.choosedate;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@ContentView(R.layout.activity_choose_date_in_pop)
public class ChooseDateInPopActivity extends Activity {

    @ViewInject(R.id.btn_choosedate)
    private Button chooseDateBtn;

    private Calendar calendar;
    private SimpleDateFormat sdf;
    private int firstDayOfWeek = -1;
    private String dayOfWeek[] = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private boolean startTimeSelected;
    private final int maxRange = 31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
    }

    public void showChooseDate(View view){
        showPop();
    }

    private View chooseDatePop;
    private PopupWindow chooseDatePopupWindow;
    private PopupWindowListener popClickListener;
    private ViewHolder viewHolder;

    private void initChooseDatePop(){
        if(null == calendar) {
            calendar = Calendar.getInstance(Locale.CHINA);
        }
        //calendar = Calendar.getInstance(Locale.FRENCH);
        if(null == sdf) {
            sdf = new SimpleDateFormat(getResources().getString(R.string.choosedate_format));
        }
        if(null != sdf && null != calendar && null != viewHolder && null != viewHolder.currentMonthText) {
            viewHolder.currentMonthText.setText(sdf.format(calendar.getTime()));
        }
        if(null != calendar){
            firstDayOfWeek = calendar.getFirstDayOfWeek();
        }
        if(-1 != firstDayOfWeek && null != viewHolder && null != viewHolder.firstInWeekText.getText() &&
                 viewHolder.firstInWeekText.getText().toString().isEmpty()){
            if(null != viewHolder.firstInWeekText)
            viewHolder.firstInWeekText.setText(dayOfWeek[(firstDayOfWeek - 1) % 7]);
            if(null != viewHolder.secondInWeekText)
            viewHolder.secondInWeekText.setText(dayOfWeek[(firstDayOfWeek + 1 - 1) % 7]);
            if(null != viewHolder.thirdInWeekText)
            viewHolder.thirdInWeekText.setText(dayOfWeek[(firstDayOfWeek + 2 - 1) % 7]);
            if(null != viewHolder.fourthInWeekText)
            viewHolder.fourthInWeekText.setText(dayOfWeek[(firstDayOfWeek + 3 - 1) % 7]);
            if(null != viewHolder.fifthInWeekText)
            viewHolder.fifthInWeekText.setText(dayOfWeek[(firstDayOfWeek + 4 - 1) % 7]);
            if(null != viewHolder.sixthInWeekText)
            viewHolder.sixthInWeekText.setText(dayOfWeek[(firstDayOfWeek + 5 - 1) % 7]);
            if(null != viewHolder.seventhInWeekText)
            viewHolder.seventhInWeekText.setText(dayOfWeek[(firstDayOfWeek + 6 - 1) % 7]);
        }
        if(null != viewHolder && null != viewHolder.chooseDateView && null != calendar){
            viewHolder.chooseDateView.setCalendar((Calendar) calendar.clone());
            if(null == viewHolder.chooseDateView.getOnDaySelectedListener()) {
                viewHolder.chooseDateView.setOnDaySelectedListener(new ChooseDateView.OnDaySelectedListener() {
                    @Override
                    public void onClick(int index) {
                        if (-1 != index && null != viewHolder && null != viewHolder.chooseDateView && null != viewHolder.chooseDateView.getDayList() &&
                                viewHolder.chooseDateView.getDayList().size() > 0 &&
                                viewHolder.chooseDateView.getDayList().size() > index) {
                            if (startTimeSelected) {
                                viewHolder.chooseDateView.setStartTime(index);
                                if (null != viewHolder.chooseDateView.getStartTime() && null != viewHolder.starttimeText) {
                                    viewHolder.starttimeText.setText((viewHolder.chooseDateView.getStartTime().getMonth() + 1) +
                                            getResources().getString(R.string.choosedate_month) +
                                            viewHolder.chooseDateView.getStartTime().getDay() +
                                            getResources().getString(R.string.choosedate_day));
                                }
                            } else {
                                viewHolder.chooseDateView.setEndTime(index);
                                if (null != viewHolder.chooseDateView.getEndTime() && null != viewHolder.endtimeText) {
                                    viewHolder.endtimeText.setText((viewHolder.chooseDateView.getEndTime().getMonth() + 1) +
                                            getResources().getString(R.string.choosedate_month) +
                                            viewHolder.chooseDateView.getEndTime().getDay() +
                                            getResources().getString(R.string.choosedate_day));
                                }
                            }
                            viewHolder.chooseDateView.invalidate();
                        }
                    }
                });
            }
            if(null == viewHolder.chooseDateView.getErrorListener()) {
                viewHolder.chooseDateView.setErrorListener(new ChooseDateView.ErrorListener() {
                    @Override
                    public void error(String s) {
                        if (null != s) {
                            Toast.makeText(ChooseDateInPopActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            viewHolder.chooseDateView.setMaxRange(maxRange);
        }
        setSelected(true);
    }

    private void setSelected(boolean startTimeSelected){
        synchronized(this){
            this.startTimeSelected = startTimeSelected;
            if(null != viewHolder && null != viewHolder.startTimeLine && null != viewHolder.endTimeLine)
            if(startTimeSelected){
                viewHolder.startTimeLine.setBackgroundColor(getResources().getColor(R.color.yellow));
                viewHolder.endTimeLine.setBackgroundColor(getResources().getColor(R.color.white));
            }else{
                viewHolder.startTimeLine.setBackgroundColor(getResources().getColor(R.color.white));
                viewHolder.endTimeLine.setBackgroundColor(getResources().getColor(R.color.yellow));
            }
        }
    }

    private void changeMonth(int i){
        if(null != calendar) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + i);
        }
        if(null != sdf && null != calendar && null != viewHolder && null != viewHolder.currentMonthText) {
            viewHolder.currentMonthText.setText(sdf.format(calendar.getTime()));
        }
        if(null != viewHolder && null != viewHolder.chooseDateView && null != calendar){
            viewHolder.chooseDateView.setCalendar((Calendar) calendar.clone());
            viewHolder.chooseDateView.invalidate();
        }
    }

    private void showPop(){
        if(null == chooseDatePop){
            chooseDatePop = LayoutInflater.from(ChooseDateInPopActivity.this).inflate(R.layout.pop_choose_date, null);
            if(null == popClickListener){
                popClickListener = new PopupWindowListener();
            }
            viewHolder = new ViewHolder();
            if(null != viewHolder){
                viewHolder.starttimeText = chooseDatePop.findViewById(R.id.textview_starttime);
                viewHolder.endtimeText = chooseDatePop.findViewById(R.id.textview_endtime);
                viewHolder.currentMonthText = chooseDatePop.findViewById(R.id.textview_current_month);
                viewHolder.chooseDateView = chooseDatePop.findViewById(R.id.chooseDateView);
                viewHolder.startTimeLine = chooseDatePop.findViewById(R.id.view_starttime);
                viewHolder.endTimeLine = chooseDatePop.findViewById(R.id.view_endtime);

                viewHolder.firstInWeekText = chooseDatePop.findViewById(R.id.textview_week_first);
                viewHolder.secondInWeekText = chooseDatePop.findViewById(R.id.textview_week_second);
                viewHolder.thirdInWeekText = chooseDatePop.findViewById(R.id.textview_week_third);
                viewHolder.fourthInWeekText = chooseDatePop.findViewById(R.id.textview_week_fourth);
                viewHolder.fifthInWeekText = chooseDatePop.findViewById(R.id.textview_week_fifth);
                viewHolder.sixthInWeekText = chooseDatePop.findViewById(R.id.textview_week_sixth);
                viewHolder.seventhInWeekText = chooseDatePop.findViewById(R.id.textview_week_seventh);

                viewHolder.layout_starttime = chooseDatePop.findViewById(R.id.layout_starttime);
                viewHolder.layout_endtime = chooseDatePop.findViewById(R.id.layout_endtime);
                viewHolder.textview_close = chooseDatePop.findViewById(R.id.textview_close);
                viewHolder.textview_pre_month = chooseDatePop.findViewById(R.id.textview_pre_month);
                viewHolder.textview_next_month = chooseDatePop.findViewById(R.id.textview_next_month);
                viewHolder.textview_cancel = chooseDatePop.findViewById(R.id.textview_cancel);
                viewHolder.textview_save = chooseDatePop.findViewById(R.id.textview_save);

            }
            if(null != popClickListener) {
                if (null != viewHolder.layout_starttime) {
                    viewHolder.layout_starttime.setOnClickListener(popClickListener);
                }
                if (null != viewHolder.layout_endtime) {
                    viewHolder.layout_endtime.setOnClickListener(popClickListener);
                }
                if (null != viewHolder.textview_close) {
                    viewHolder.textview_close.setOnClickListener(popClickListener);
                }
                if (null != viewHolder.textview_pre_month) {
                    viewHolder.textview_pre_month.setOnClickListener(popClickListener);
                }
                if (null != viewHolder.textview_next_month) {
                    viewHolder.textview_next_month.setOnClickListener(popClickListener);
                }
                if (null != viewHolder.textview_cancel) {
                    viewHolder.textview_cancel.setOnClickListener(popClickListener);
                }
                if (null != viewHolder.textview_save) {
                    viewHolder.textview_save.setOnClickListener(popClickListener);
                }
            }
        }
        initChooseDatePop();
        if(null == chooseDatePopupWindow) {
            chooseDatePopupWindow = new PopupWindow(chooseDatePop, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            chooseDatePopupWindow.setAnimationStyle(R.style.animation_in_and_out);
            // 设置PopupWindow的背景
            //popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // 设置PopupWindow是否能响应外部点击事件
            chooseDatePopupWindow.setOutsideTouchable(false);
            // 设置PopupWindow是否能响应点击事件
            chooseDatePopupWindow.setTouchable(true);
            chooseDatePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1f);
                }
            });
            chooseDatePopupWindow.setFocusable(true);
            chooseDatePopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        if(!chooseDatePopupWindow.isShowing()){
            setBackgroundAlpha(0.7f);
            chooseDatePopupWindow.showAtLocation(chooseDateBtn, Gravity.BOTTOM, 0, 0);
            //popupWindow.showAsDropDown(titleBarView.getRight1View(), 0, 0);
        }
    }

    class ViewHolder{
        private TextView starttimeText;
        private TextView endtimeText;
        private TextView currentMonthText;
        private TextView firstInWeekText;
        private TextView secondInWeekText;
        private TextView thirdInWeekText;
        private TextView fourthInWeekText;
        private TextView fifthInWeekText;
        private TextView sixthInWeekText;
        private TextView seventhInWeekText;
        private ChooseDateView chooseDateView;
        private View startTimeLine;
        private View endTimeLine;

        private View layout_starttime;
        private View layout_endtime;
        private View textview_close;
        private View textview_pre_month;
        private View textview_next_month;
        private View textview_cancel;
        private View textview_save;
    }

    class PopupWindowListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layout_starttime:
                    setSelected(true);
                    break;
                case R.id.layout_endtime:
                    setSelected(false);
                    break;
                case R.id.textview_close:
                    dimissPop();
                    break;
                case R.id.textview_pre_month:
                    changeMonth(-1);
                    break;
                case R.id.textview_next_month:
                    changeMonth(1);
                    break;
                case R.id.textview_cancel:
                    dimissPop();
                    break;
                case R.id.textview_save:
                    dimissPop();
                    if(null != viewHolder && null != viewHolder.chooseDateView && null != viewHolder.chooseDateView.getStartTime() &&
                            null != viewHolder.chooseDateView.getEndTime()){
                        Toast.makeText(ChooseDateInPopActivity.this, viewHolder.chooseDateView.getStartTime().toString() + "-" +
                                viewHolder.chooseDateView.getEndTime().toString(), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private void dimissPop(){
        if(null != chooseDatePopupWindow && chooseDatePopupWindow.isShowing()){
            chooseDatePopupWindow.dismiss();
        }
    }

    private void setBackgroundAlpha(float bgAlpha){
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = bgAlpha;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(layoutParams);
    }

    //关闭popupWindow.setOutsideTouchable，重写此方法，否则pop显示情况下点击+，pop不会消失
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (chooseDatePopupWindow != null && chooseDatePopupWindow.isShowing()) {
            chooseDatePopupWindow.dismiss();
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (chooseDatePopupWindow != null && chooseDatePopupWindow.isShowing()) {
                chooseDatePopupWindow.dismiss();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
