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
            return  true;
        }
        return false;
    }

    public boolean intersects(int t) {
        if(t >= left && t <= right ) {
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
