package com.group21.thermostat;

import java.io.Serializable;

/**
 * Created by AntonK on 28.05.2015.
 */
public class WeekSchedule implements Serializable {
    final static String fileName = "thermostat.dat";
    float tempDay;
    float tempNight;
    float tempPermanent;
    boolean temporary;
    boolean permanent;
    boolean changed;

    Schedule[] week;

    public WeekSchedule() {
        week = new Schedule[7];
        for (int i = 0; i < 7; i++) {
            week[i] = new Schedule();
        }

        this.tempDay = 15;
        this.tempNight = 25;
        this.permanent = false;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public float getTempDay() {
        return tempDay;
    }

    public float getTempNight() {
        return tempNight;
    }

    public float getTempPermanent() {
        return tempPermanent;
    }

    public void setTempPermanent(float temp) {
        tempPermanent = temp;
    }

    public void setTempDay(float temp) {
        tempDay = temp;
    }

    public void setTempNight(float temp) {
        tempNight = temp;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean perm) {
        permanent = perm;
    }

    public boolean add(int dayOfWeek, int h1, int m1, int h2, int m2) {
        boolean result = week[dayOfWeek - 1].add(h1, m1, h2, m2);
        return result;
    }

    public void remove(int dayOfWeek, int num) {
        week[dayOfWeek - 1].remove(num);
    }

    public boolean shouldSwitchToDayTemperature(int dayOfWeek, int h, int m) {
        return week[dayOfWeek - 1].shouldSwitchToDayTemperature(h, m);
    }

}
