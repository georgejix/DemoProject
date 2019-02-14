package com.test.choosedate;

/**
 * Created by jix on 2019/2/14.
 */

public class Day {
    //month： 0-11
    private int year, month, day, dayOfWeek;
    private boolean isSelected;

    public Day(int year, int month, int day, int dayOfWeek, boolean isSelected){
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.isSelected = isSelected;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return year + "年" + (month + 1) + "月" + day + "日" + "," + dayOfWeek;
    }
}
