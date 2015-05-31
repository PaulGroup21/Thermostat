package com.group21.thermostat;

import java.io.Serializable;

/**
 * Created by AntonK on 28.05.2015.
 */
public class TimeSection implements Serializable {
    public int left;
    public int right;

    public TimeSection(int sectionStart, int sectionFinish) {
        left = sectionStart;
        right = sectionFinish;
    }

    public boolean intersects(TimeSection t) {
        if (left >= t.left && left <= t.right ||
                right >= t.left && right <= t.right ||
                t.left >= left && t.left <= right ||
                t.right >= left && t.right <= right) {
            return true;
        }
        return false;
    }

    public String getHourStart() {
        int temp = left;
        int res = 0;
        while (temp >= 60) {
            res++;
            temp -= 60;
        }
        if (res == 0) {
            return res + "0";
        }
        return res + "";
    }

    public String getHourEnd() {
        int temp = right;
        int res = 0;
        while (temp >= 60) {
            res++;
            temp -= 60;
        }
        if (res == 0) {
            return res + "0";
        }
        return res + "";
    }

    public String getMinutesStart() {
        int temp = left;
        while (temp >= 60) {
            temp -= 60;
        }
        if (temp < 10) {
            return "0" + temp;
        }
        return temp + "";
    }

    public String getMinutesEnd() {
        int temp = right;
        while (temp >= 60) {
            temp -= 60;
        }
        if (temp < 10) {
            return "0" + temp;
        }
        return temp + "";
    }

    public boolean intersects(int t) {
        if (t >= left && t <= right) {
            return true;
        }
        return false;
    }

    /*
    public static void main(String[] args) {
        Schedule s = new Schedule();
        System.out.println(s.add(10, 15, 12, 45));
        System.out.println(s.add(13,30,15,47));
        System.out.println(s.add(8,00,13,00));
        System.out.println();
        System.out.println(s.shouldSwitchToDayTemperature(12,0));
        s.remove(0);
        System.out.println(s.shouldSwitchToDayTemperature(12,0));
    }
    */
}
