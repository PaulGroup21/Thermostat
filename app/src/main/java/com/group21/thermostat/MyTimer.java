package com.group21.thermostat;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by AntonK on 28.05.2015.
 */
public class MyTimer {

    private Calendar c;
    boolean day;

    public MyTimer() {
        c = new GregorianCalendar();
        day = false;
    }

    public void update() {
        c.add(Calendar.MINUTE,1);
    }

    public int getTimeInMinutes() {
        return c.get(Calendar.HOUR_OF_DAY)*60 + c.get(Calendar.MINUTE);
    }

    public boolean isDay() {
        return day;
    }

    public void switchMode() {
        day = !day;
    }

    public int getHour() {
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute() {
        return c.get(Calendar.MINUTE);
    }

    public int getDay() {
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public String getStringDay() {
        int day = c.get(Calendar.DAY_OF_WEEK);
        String formatedDay ="";
        switch(day) {
            case 1:
                formatedDay = "Sunday";
                break;
            case 2:
                formatedDay = "Monday";
                break;
            case 3:
                formatedDay = "Tuesday";
                break;
            case 4:
                formatedDay = "Wednesday";
                break;
            case 5:
                formatedDay = "Thursday";
                break;
            case 6:
                formatedDay = "Friday";
                break;
            case 7:
                formatedDay = "Saturday";
                break;

        }
        return formatedDay;
    }

    @Override
    public String toString() {
        return getStringDay() + " " + getHour() + ":" + (getMinute()>9?getMinute():"0"+getMinute());
    }

    /*
    public static void main(String[] args) {
        Timer t = new Timer();
        System.out.println(t);
        for (int i = 0 ; i < 100; i ++) {
            t.update();
        }
         System.out.println(t);
    }*/
}